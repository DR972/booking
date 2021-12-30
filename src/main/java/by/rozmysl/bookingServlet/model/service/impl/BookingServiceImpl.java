package by.rozmysl.bookingServlet.model.service.impl;

import by.rozmysl.bookingServlet.exception.DaoException;
import by.rozmysl.bookingServlet.exception.MailException;
import by.rozmysl.bookingServlet.exception.ServiceException;
import by.rozmysl.bookingServlet.model.service.mail.Letter;
import by.rozmysl.bookingServlet.model.service.mail.MailSender;
import by.rozmysl.bookingServlet.model.dao.BookingDao;
import by.rozmysl.bookingServlet.model.dao.DaoFactory;
import by.rozmysl.bookingServlet.model.entity.hotel.StatusReservation;
import by.rozmysl.bookingServlet.model.entity.user.Booking;
import by.rozmysl.bookingServlet.model.service.BookingService;
import by.rozmysl.bookingServlet.model.service.ServiceFactory;
import by.rozmysl.bookingServlet.model.service.validator.BookingValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.mail.MessagingException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;

public class BookingServiceImpl implements BookingService {
    private static final Logger LOGGER = LoggerFactory.getLogger(BookingServiceImpl.class);
    public static final String MAIL_MESSAGE = "Счет во вложении";
    public static final String ADDITIONALSERVICES_TYPE_TRANSFER = "трансфер";
    public static final String ADDITIONALSERVICES_TYPE_PARKING = "стоянка";

    private final Connection connection;
    private final BookingDao bookingDao;

    public BookingServiceImpl(Connection connection) {
        this.connection = connection;
        bookingDao = DaoFactory.getInstance().bookingDao(connection);
    }

    @Override
    public Booking findById(Long number) throws ServiceException {
        try {
            return bookingDao.findById(number);
        } catch (DaoException e) {
            LOGGER.error("'Booking findById' query has been failed", e);
            throw new ServiceException("'Booking findById' query has been failed", e);
        }
    }

    @Override
    public List<Booking> findAll(int page, int rows) throws ServiceException {
        try {
            return bookingDao.findAll(page, rows);
        } catch (DaoException e) {
            LOGGER.error("'Booking findAll' query has been failed", e);
            throw new ServiceException("'Booking findAll' query has been failed", e);
        }
    }

    @Override
    public Booking save(Booking booking) throws ServiceException {
        booking.setAmount(calculateAmount(booking));
        try {
            return bookingDao.save(booking);
        } catch (DaoException e) {
            LOGGER.error("'Booking save' query has been failed", e);
            throw new ServiceException("'Booking save' query has been failed", e);
        }
    }

    @Override
    public void delete(Long number) throws ServiceException {
        try {
            new Letter().deleteInvoice("src/main/resources/static/" + number + ".pdf");
            bookingDao.delete(number);
        } catch (DaoException e) {
            LOGGER.error("'Booking delete' query has been failed", e);
            throw new ServiceException("'Booking delete' query has been failed", e);
        }
    }

    @Override
    public int countBookingsPages(int rows) throws ServiceException {
        try {
            return bookingDao.countBookingsPages(rows);
        } catch (DaoException e) {
            LOGGER.error("'countBookingsPages' query has been failed", e);
            throw new ServiceException("'countBookingsPages' query has been failed", e);
        }
    }

    @Override
    public String validateBooking(Booking booking) {
        return new BookingValidator().allValidate(booking);
    }

    @Override
    public List<Booking> findAllBookingsBetweenTwoDates(LocalDate arrival, LocalDate departure) throws ServiceException {
        try {
            return bookingDao.findAllBookingsBetweenTwoDates(arrival, departure);
        } catch (DaoException e) {
            LOGGER.error("'findAllBookingsBetweenTwoDates' query has been failed", e);
            throw new ServiceException("'findAllBookingsBetweenTwoDates' query has been failed", e);
        }
    }

    @Override
    public List<Booking> findAllBookingsByUser(String user) throws ServiceException {
        try {
            return bookingDao.findAllBookingsByUser(user);
        } catch (DaoException e) {
            LOGGER.error("'findAllBookingsByUser' query has been failed", e);
            throw new ServiceException("'findAllBookingsByUser' query has been failed", e);
        }
    }

    @Override
    public void changeRoom(long number, int roomNumber, ServiceFactory service) throws ServiceException {
        Booking booking = getAllBookingInfo(number, service);
        booking.setRoom(service.roomService(connection).findById(roomNumber));
        try {
            bookingDao.changeRoom(number, roomNumber, calculateAmount(booking));
        } catch (DaoException e) {
            LOGGER.error("'changeRoom' query has been failed", e);
            throw new ServiceException("'changeRoom' query has been failed", e);
        }
    }

    @Override
    public void changeBookingStatus(long number, String status, ServiceFactory service) throws ServiceException {
        Booking booking = getAllBookingInfo(number, service);
        String filePath = "src/main/resources/static/" + number + ".pdf";
        if (status.equals(StatusReservation.INVOICE.getStatus())) {
            try {
                new Letter().createInvoice(booking, filePath);
                MailSender.getInstance().sendMailWithAttachment(service.userService(connection).findById(booking.getUser().getUsername()).getEmail(),
                        status, MAIL_MESSAGE, filePath);
            } catch (MailException e) {
                LOGGER.error("'changeBookingStatus' query has been failed", e);
                throw new ServiceException("'changeBookingStatus' query has been failed", e);
            }

        }
        if (status.equals(StatusReservation.CLOSED.getStatus())) {
            new Letter().deleteInvoice(filePath);
        }
        try {
            bookingDao.changeBookingStatus(number, status);
        } catch (DaoException e) {
            LOGGER.error("'changeBookingStatus' query has been failed", e);
            throw new ServiceException("'changeBookingStatus' query has been failed", e);
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
        booking.setRoom(service.roomService(connection).findById(booking.getRoom().getRoomNumber()));
        booking.setFood(service.foodService(connection).findById(booking.getFood().getType()));
        booking.setServices(service.servicesService(connection).findById(booking.getServices().getType()));
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
        if (booking.getServices().getType().equals(ADDITIONALSERVICES_TYPE_TRANSFER)) {
            service = booking.getServices().getPrice();
        }
        if (booking.getServices().getType().equals(ADDITIONALSERVICES_TYPE_PARKING)) {
            service = booking.getServices().getPrice().multiply(new BigDecimal(booking.getDays()));
        }
        return (booking.getFood().getPrice().multiply(new BigDecimal(booking.getPersons()))
                .add(booking.getRoom().getPrice()))
                .multiply(new BigDecimal(booking.getDays()))
                .add(service);
    }
}
