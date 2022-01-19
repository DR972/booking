package by.rozmysl.booking.model.service.impl;

import by.rozmysl.booking.exception.DaoException;
import by.rozmysl.booking.exception.MailException;
import by.rozmysl.booking.exception.ServiceException;
import by.rozmysl.booking.model.service.Letter;
import by.rozmysl.booking.model.dao.BookingDao;
import by.rozmysl.booking.model.dao.DaoFactory;
import by.rozmysl.booking.model.entity.hotel.StatusReservation;
import by.rozmysl.booking.model.entity.user.Booking;
import by.rozmysl.booking.model.service.BookingService;
import by.rozmysl.booking.model.service.ServiceFactory;
import by.rozmysl.booking.model.service.validator.BookingValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import static by.rozmysl.booking.model.util.LoggerMessageError.*;
import static by.rozmysl.booking.model.util.SqlQuery.*;

/**
 * Provides logic for working with data sent to the `Booking` table DAO.
 */
public class BookingServiceImpl implements BookingService {
    private static final Logger LOGGER = LoggerFactory.getLogger(BookingServiceImpl.class);
    private static final String MAIL_MESSAGE = "Счет во вложении";
    private static final String ADDITIONALSERVICES_TYPE_TRANSFER = "трансфер";
    private static final String ADDITIONALSERVICES_TYPE_PARKING = "стоянка";

    private final BookingDao bookingDao;

    /**
     * The constructor creates a new object BookingServiceImpl without property.
     */
    public BookingServiceImpl() {
        bookingDao = DaoFactory.getInstance().getBookingDao();
    }

    /**
     * Provides logic for searching for a Booking object by id
     *
     * @param number id of the Booking object
     * @return Booking object
     * @throws ServiceException if the operation failed
     */
    @Override
    public Booking findById(Long number) throws ServiceException {
        try {
            return bookingDao.findEntity(BOOKING_FIND_BY_ID, MESSAGE_BOOKING_FIND_BY_ID, number);
        } catch (DaoException e) {
            LOGGER.error(MESSAGE_BOOKING_FIND_BY_ID, e);
            throw new ServiceException(MESSAGE_BOOKING_FIND_BY_ID, e);
        }
    }

    /**
     * Provides logic for searching for all Booking objects.
     *
     * @param pageNumber number of the page to return
     * @param rows       number of rows per page
     * @return list of Booking objects
     * @throws ServiceException if the operation failed
     */
    @Override
    public List<Booking> findAll(int pageNumber, int rows) throws ServiceException {
        try {
            return bookingDao.findListEntities(BOOKING_FIND_ALL, MESSAGE_BOOKING_FIND_ALL, rows, pageNumber, rows);
        } catch (DaoException e) {
            LOGGER.error(MESSAGE_BOOKING_FIND_ALL, e);
            throw new ServiceException(MESSAGE_BOOKING_FIND_ALL, e);
        }
    }

    /**
     * Provides logic for saving the Booking in the `BOOKING` table
     *
     * @param booking Booking
     * @throws ServiceException if the operation failed
     */
    @Override
    public void save(Booking booking) throws ServiceException {
        booking.setAmount(calculateAmount(booking));
        try {
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
        } catch (DaoException e) {
            LOGGER.error(MESSAGE_BOOKING_SAVE, e);
            throw new ServiceException(MESSAGE_BOOKING_SAVE, e);
        }
    }

    /**
     * Provides logic for deleting the Booking in the `BOOKING` table
     *
     * @param number id of the Booking object
     * @throws ServiceException if the operation failed
     */
    @Override
    public void delete(Long number) throws ServiceException {
        try {
            new Letter().deleteInvoice("src/main/resources/static/" + number + ".pdf");
            bookingDao.updateEntity(BOOKING_DELETE, MESSAGE_BOOKING_DELETE, number);
        } catch (DaoException e) {
            LOGGER.error(MESSAGE_BOOKING_DELETE, e);
            throw new ServiceException(MESSAGE_BOOKING_DELETE, e);
        }
    }

    /**
     * Provides logic for counting the number of pages of all Booking objects.
     *
     * @param rows number of rows per page
     * @return number of pages
     * @throws ServiceException if the operation failed
     */
    @Override
    public int countNumberBookingPages(int rows) throws ServiceException {
        try {
            return (int) Math.ceil((float) bookingDao.countNumberEntityRows(BOOKING_FIND_ROWS_COUNT, MESSAGE_COUNT_BOOKINGS_ROWS) / rows);
        } catch (DaoException e) {
            LOGGER.error(MESSAGE_COUNT_BOOKINGS_PAGES, e);
            throw new ServiceException(MESSAGE_COUNT_BOOKINGS_PAGES, e);
        }
    }

    /**
     * Provides booking validation.
     *
     * @param arrival arrival date
     * @param days    number of days
     * @param persons number of persons
     * @return validation result
     */
    @Override
    public String validateBooking(String arrival, String days, String persons) {
        return new BookingValidator().allValidate(arrival, days, persons);
    }

    /**
     * Provides logic for searching for all Bookings between two dates.
     *
     * @param arrival   arrival date
     * @param departure departure date
     * @return list of Booking objects
     * @throws ServiceException if the operation failed
     */
    @Override
    public List<Booking> findAllBookingsBetweenTwoDates(LocalDate arrival, LocalDate departure) throws ServiceException {
        try {
            return bookingDao.findListEntities(BOOKING_FIND_ALL_BOOKINGS_BETWEEN_TWO_DATES,
                    MESSAGE_BOOKING_FIND_ALL_BOOKINGS_BETWEEN_TWO_DATES, departure, arrival);
        } catch (DaoException e) {
            LOGGER.error(MESSAGE_BOOKING_FIND_ALL_BOOKINGS_BETWEEN_TWO_DATES, e);
            throw new ServiceException(MESSAGE_BOOKING_FIND_ALL_BOOKINGS_BETWEEN_TWO_DATES, e);
        }
    }

    /**
     * Provides logic for searching for all Bookings made by the username.
     *
     * @param username id of the User
     * @return list of Booking objects
     * @throws ServiceException if the operation failed
     */
    @Override
    public List<Booking> findAllBookingsByUser(String username) throws ServiceException {
        try {
            return bookingDao.findListEntities(BOOKING_FIND_ALL_BOOKINGS_BY_USER, MESSAGE_BOOKING_FIND_ALL_BOOKINGS_BY_USER, username);
        } catch (DaoException e) {
            LOGGER.error(MESSAGE_BOOKING_FIND_ALL_BOOKINGS_BY_USER, e);
            throw new ServiceException(MESSAGE_BOOKING_FIND_ALL_BOOKINGS_BY_USER, e);
        }
    }

    /**
     * Provides logic for changing the Room in the User's Booking.
     *
     * @param number     id of the Booking
     * @param roomNumber id of the Room
     * @param service    ServiceFactory service
     * @throws ServiceException if the operation failed
     */
    @Override
    public void changeRoom(long number, int roomNumber, ServiceFactory service) throws ServiceException {
        Booking booking = getAllBookingInfo(number, service);
        booking.setRoom(service.getRoomService().findById(roomNumber));
        try {
            bookingDao.updateEntity(BOOKING_CHANGE_ROOM, MESSAGE_BOOKING_CHANGE_ROOM,
                    roomNumber,
                    calculateAmount(booking),
                    number);
        } catch (DaoException e) {
            LOGGER.error(MESSAGE_BOOKING_CHANGE_ROOM, e);
            throw new ServiceException(MESSAGE_BOOKING_CHANGE_ROOM, e);
        }
    }

    /**
     * Provides logic for changing the Booking Status.
     *
     * @param number  id of the Booking
     * @param status  Booking status
     * @param service ServiceFactory service
     * @throws ServiceException if the operation failed
     */
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

        try {
            bookingDao.updateEntity(BOOKING_CHANGE_BOOKING_STATUS, MESSAGE_BOOKING_CHANGE_BOOKING_STATUS,
                    status,
                    number);
        } catch (DaoException e) {
            LOGGER.error(MESSAGE_BOOKING_CHANGE_BOOKING_STATUS, e);
            throw new ServiceException(MESSAGE_BOOKING_CHANGE_BOOKING_STATUS, e);
        }
    }

    /**
     * Gets all booking information.
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
