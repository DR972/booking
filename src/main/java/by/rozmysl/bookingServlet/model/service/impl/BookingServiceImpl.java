package by.rozmysl.bookingServlet.model.service.impl;

import by.rozmysl.bookingServlet.exception.DaoException;
import by.rozmysl.bookingServlet.exception.MailException;
import by.rozmysl.bookingServlet.exception.ServiceException;
import by.rozmysl.bookingServlet.model.db.ConnectionPool;
import by.rozmysl.bookingServlet.model.db.ProxyConnection;
import by.rozmysl.bookingServlet.model.service.Letter;
import by.rozmysl.bookingServlet.model.dao.BookingDao;
import by.rozmysl.bookingServlet.model.dao.DaoFactory;
import by.rozmysl.bookingServlet.model.entity.hotel.StatusReservation;
import by.rozmysl.bookingServlet.model.entity.user.Booking;
import by.rozmysl.bookingServlet.model.service.BookingService;
import by.rozmysl.bookingServlet.model.service.ServiceFactory;
import by.rozmysl.bookingServlet.model.service.validator.BookingValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import static by.rozmysl.bookingServlet.model.LoggerMessage.*;
import static by.rozmysl.bookingServlet.model.dao.SqlQuery.*;

public class BookingServiceImpl implements BookingService {
    private static final Logger LOGGER = LoggerFactory.getLogger(BookingServiceImpl.class);
    public static final String MAIL_MESSAGE = "Счет во вложении";
    public static final String ADDITIONALSERVICES_TYPE_TRANSFER = "трансфер";
    public static final String ADDITIONALSERVICES_TYPE_PARKING = "стоянка";

    private final BookingDao bookingDao;

    public BookingServiceImpl() {
        bookingDao = DaoFactory.getInstance().getBookingDao();
    }

    @Override
    public Booking findById(Long number) throws ServiceException {
        try {
            return bookingDao.findEntity(BOOKING_FIND_BY_ID, MESSAGE_BOOKING_FIND_BY_ID, number);
//            return bookingDao.findById(number);
        } catch (DaoException e) {
            LOGGER.error(MESSAGE_BOOKING_FIND_BY_ID, e);
            throw new ServiceException(MESSAGE_BOOKING_FIND_BY_ID, e);
        }
    }

    @Override
    public List<Booking> findAll(int pageNumber, int rows) throws ServiceException {
        try {
            return bookingDao.findListEntities(BOOKING_FIND_ALL, MESSAGE_BOOKING_FIND_ALL, rows, pageNumber, rows);
//            return bookingDao.findAll(pageNumber, rows);
        } catch (DaoException e) {
            LOGGER.error(MESSAGE_BOOKING_FIND_ALL, e);
            throw new ServiceException(MESSAGE_BOOKING_FIND_ALL, e);
        }
    }

    @Override
    public void save(Booking booking) throws ServiceException {
        booking.setAmount(calculateAmount(booking));
        try (final ProxyConnection connection = ConnectionPool.getInstance().getConnectionFromPool()) {
            bookingDao.setConnection(connection);
            bookingDao.saveWithGeneratedKeys(BOOKING_SAVE, MESSAGE_BOOKING_SAVE,
                    booking.getUser().getId(),
                    booking.getRoom().getId(),
                    booking.getPersons(),
                    Date.valueOf(booking.getArrival()),
                    Date.valueOf(booking.getDeparture()),
                    booking.getFood().getId(),
                    booking.getDays(),
                    booking.getServices().getId(),
                    booking.getAmount(),
                    booking.getStatus());
//            bookingDao.save(booking);
        } catch (DaoException e) {
            LOGGER.error(MESSAGE_BOOKING_SAVE, e);
            throw new ServiceException(MESSAGE_BOOKING_SAVE, e);
        }
    }

    @Override
    public void delete(Long number) throws ServiceException {
        try (final ProxyConnection connection = ConnectionPool.getInstance().getConnectionFromPool()) {
            bookingDao.setConnection(connection);
            new Letter().deleteInvoice("src/main/resources/static/" + number + ".pdf");
            bookingDao.updateEntity(BOOKING_DELETE, MESSAGE_BOOKING_DELETE, number);
//            bookingDao.delete(number);
        } catch (DaoException e) {
            LOGGER.error(MESSAGE_BOOKING_DELETE, e);
            throw new ServiceException(MESSAGE_BOOKING_DELETE, e);
        }
    }

    @Override
    public int countBookingsPages(int rows) throws ServiceException {
        try {
            return (int) Math.ceil((float) bookingDao.countEntitiesRows(BOOKING_FIND_ROWS_COUNT, MESSAGE_COUNT_BOOKINGS_ROWS) / rows);
            //return bookingDao.countBookingsPages(rows);
        } catch (DaoException e) {
            LOGGER.error(MESSAGE_COUNT_BOOKINGS_PAGES, e);
            throw new ServiceException(MESSAGE_COUNT_BOOKINGS_PAGES, e);
        }
    }

    @Override
    public String validateBooking(Booking booking) {
        return new BookingValidator().allValidate(booking);
    }

    @Override
    public List<Booking> findAllBookingsBetweenTwoDates(LocalDate arrival, LocalDate departure) throws ServiceException {
        try {
            return bookingDao.findListEntities(BOOKING_FIND_ALL_BOOKINGS_BETWEEN_TWO_DATES,
                    MESSAGE_BOOKING_FIND_ALL_BOOKINGS_BETWEEN_TWO_DATES, departure, arrival);
//            return bookingDao.findAllBookingsBetweenTwoDates(arrival, departure);
        } catch (DaoException e) {
            LOGGER.error(MESSAGE_BOOKING_FIND_ALL_BOOKINGS_BETWEEN_TWO_DATES, e);
            throw new ServiceException(MESSAGE_BOOKING_FIND_ALL_BOOKINGS_BETWEEN_TWO_DATES, e);
        }
    }

    @Override
    public List<Booking> findAllBookingsByUser(String username) throws ServiceException {
        try {
            return bookingDao.findListEntities(BOOKING_FIND_ALL_BOOKINGS_BY_USER, MESSAGE_BOOKING_FIND_ALL_BOOKINGS_BY_USER, username);
//            return bookingDao.findAllBookingsByUser(username);
        } catch (DaoException e) {
            LOGGER.error(MESSAGE_BOOKING_FIND_ALL_BOOKINGS_BY_USER, e);
            throw new ServiceException(MESSAGE_BOOKING_FIND_ALL_BOOKINGS_BY_USER, e);
        }
    }

    @Override
    public void changeRoom(long number, int roomNumber, ServiceFactory service) throws ServiceException {
        Booking booking = getAllBookingInfo(number, service);
        booking.setRoom(service.getRoomService().findById(roomNumber));
        try (final ProxyConnection connection = ConnectionPool.getInstance().getConnectionFromPool()) {
            bookingDao.setConnection(connection);
            bookingDao.updateEntity(BOOKING_CHANGE_ROOM, MESSAGE_BOOKING_CHANGE_ROOM,
                    roomNumber,
                    calculateAmount(booking),
                    number);
//            bookingDao.changeRoom(number, roomNumber, calculateAmount(booking));
        } catch (DaoException e) {
            LOGGER.error(MESSAGE_BOOKING_CHANGE_ROOM, e);
            throw new ServiceException(MESSAGE_BOOKING_CHANGE_ROOM, e);
        }
    }

    @Override
    public void changeBookingStatus(long number, String status, ServiceFactory service) throws ServiceException {
        Booking booking = getAllBookingInfo(number, service);
        String filePath = "src/main/resources/static/" + number + ".pdf";

        if (status.equals(StatusReservation.INVOICE.getStatus())) {
            try {
                new Letter().createInvoice(booking, filePath);
                service.getMailSender().sendMailWithAttachment(service.getUserService()
                        .findById(booking.getUser().getId()).getEmail(), status, MAIL_MESSAGE, filePath);
            } catch (MailException e) {
                LOGGER.error(MESSAGE_BOOKING_INVOICE, e);
                throw new ServiceException(MESSAGE_BOOKING_INVOICE, e);
            }
        }

        if (status.equals(StatusReservation.CLOSED.getStatus())) {
            new Letter().deleteInvoice(filePath);
        }

        try (final ProxyConnection connection = ConnectionPool.getInstance().getConnectionFromPool()) {
            bookingDao.setConnection(connection);
            bookingDao.updateEntity(BOOKING_CHANGE_BOOKING_STATUS, MESSAGE_BOOKING_CHANGE_BOOKING_STATUS,
                    status,
                    number);
//            bookingDao.changeBookingStatus(number, status);
        } catch (DaoException e) {
            LOGGER.error(MESSAGE_BOOKING_CHANGE_BOOKING_STATUS, e);
            throw new ServiceException(MESSAGE_BOOKING_CHANGE_BOOKING_STATUS, e);
        }
    }

    /**
     * Calculates the booking amount
     *
     * @param number  id of the Booking
     * @param service ServiceFactory service
     * @return Booking Booking
     */
    private Booking getAllBookingInfo(long number, ServiceFactory service) throws ServiceException {
        Booking booking = findById(number);
        booking.setRoom(service.getRoomService().findById(booking.getRoom().getId()));
        booking.setFood(service.getFoodService().findById(booking.getFood().getId()));
        booking.setServices(service.getServicesService().findById(booking.getServices().getId()));
        return booking;
    }

    /**
     * Calculates the booking amount
     *
     * @param booking booking
     * @return booking amount
     */
    private BigDecimal calculateAmount(Booking booking) {
        BigDecimal service = BigDecimal.valueOf(0);
        if (booking.getServices().getId().equals(ADDITIONALSERVICES_TYPE_TRANSFER)) {
            service = booking.getServices().getPrice();
        }
        if (booking.getServices().getId().equals(ADDITIONALSERVICES_TYPE_PARKING)) {
            service = booking.getServices().getPrice().multiply(new BigDecimal(booking.getDays()));
        }
        return (booking.getFood().getPrice().multiply(new BigDecimal(booking.getPersons()))
                .add(booking.getRoom().getPrice()))
                .multiply(new BigDecimal(booking.getDays()))
                .add(service);
    }
}
