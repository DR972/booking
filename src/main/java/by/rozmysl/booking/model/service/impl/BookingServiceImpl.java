package by.rozmysl.booking.model.service.impl;

import by.rozmysl.booking.exception.MailException;
import by.rozmysl.booking.exception.ServiceException;
import by.rozmysl.booking.model.entity.hotel.Room;
import by.rozmysl.booking.model.entity.user.User;
import by.rozmysl.booking.model.entity.hotel.StatusReservation;
import by.rozmysl.booking.model.entity.user.Booking;
import by.rozmysl.booking.model.service.BookingService;
import by.rozmysl.booking.model.service.ServiceFactory;
import by.rozmysl.booking.model.service.validator.BookingValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.sql.Date;

import static by.rozmysl.booking.model.ModelManager.*;
import static by.rozmysl.booking.model.util.LoggerMessageError.*;

/**
 * Provides logic for working with data sent to the `Booking` table DAO.
 */
public class BookingServiceImpl extends ServiceImpl<Booking, Long> implements BookingService {
    private static final Logger LOGGER = LoggerFactory.getLogger(BookingServiceImpl.class);
    private static final String MAIL_MESSAGE = "Счет во вложении";
    private static final String INVOICE_PATH = "src/main/resources/static/";
    private static final String ADDITIONALSERVICES_TYPE_TRANSFER = "трансфер";
    private static final String ADDITIONALSERVICES_TYPE_PARKING = "стоянка";

    /**
     * Provides logic for saving the Booking in the `BOOKING` table
     *
     * @param booking Booking
     * @throws ServiceException if the operation failed
     */
    @Override
    public void save(Booking booking) throws ServiceException {
        booking.setAmount(calculateAmount(booking));
        saveWithGeneratedKeys(BOOKING_SAVE,
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
    }

    /**
     * Provides logic for deleting the Booking in the `BOOKING` table
     *
     * @param number id of the Booking object
     * @throws ServiceException if the operation failed
     */
    @Override
    public void delete(Long number) throws ServiceException {
        new Letter().deleteInvoice(INVOICE_PATH + number + ".pdf");
        updateEntity(BOOKING_DELETE, number);
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
     * Provides logic for changing the Room in the User's Booking.
     *
     * @param number     id of the Booking
     * @param roomNumber id of the Room
     * @param service    ServiceFactory service
     * @throws ServiceException if the operation failed
     */
    @Override
    public void changeRoom(long number, int roomNumber, ServiceFactory service) throws ServiceException {
        Booking booking = findEntity(Booking.class, BOOKING_FIND_BY_ID, number);
        booking.setRoom(service.getRoomService().findEntity(Room.class, ROOM_FIND_BY_ID, roomNumber));
        updateEntity(BOOKING_CHANGE_ROOM, roomNumber, calculateAmount(booking), number);
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
        Booking booking = findEntity(Booking.class, BOOKING_FIND_BY_ID, number);
        String filePath = INVOICE_PATH + number + ".pdf";

        if (status.equals(StatusReservation.INVOICE.getStatus())) {
            try {
                new Letter().createInvoice(booking, filePath);
                service.getMailSender().sendMailWithAttachment(service.getUserService()
                        .findEntity(User.class, USER_FIND_BY_ID, booking.getUser().getId()).getEmail(), status, MAIL_MESSAGE, filePath);
            } catch (MailException e) {
                LOGGER.error(MESSAGE_BOOKING_INVOICE, e);
                throw new ServiceException(MESSAGE_BOOKING_INVOICE, e);
            }
        }

        if (status.equals(StatusReservation.CLOSED.getStatus())) {
            new Letter().deleteInvoice(filePath);
        }
        updateEntity(BOOKING_CHANGE_BOOKING_STATUS, status, number);
    }

    /**
     * Calculates the booking amount
     *
     * @param booking booking
     * @return booking amount
     */
    private BigDecimal calculateAmount(Booking booking) {
        System.out.println(booking);
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
