package by.rozmysl.booking.model.service;

import by.rozmysl.booking.exception.ServiceException;
import by.rozmysl.booking.model.entity.user.Booking;

/**
 * Provides logic for working with data sent to the `Booking` table DAO.
 */
public interface BookingService extends Service<Booking, Long> {

    /**
     * Provides logic for saving the Booking in the `BOOKING` table
     *
     * @param booking Booking
     * @throws ServiceException if the operation failed
     */
    void save(Booking booking) throws ServiceException;

    /**
     * Provides logic for deleting the Booking in the `BOOKING` table
     *
     * @param number id of the Booking object
     * @throws ServiceException if the operation failed
     */
    void delete(Long number) throws ServiceException;

    /**
     * Provides booking validation.
     *
     * @param arrival arrival date
     * @param days    number of days
     * @param persons number of persons
     * @return validation result
     */
    String validateBooking(String arrival, String days, String persons);

    /**
     * Provides logic for changing the Room in the User's Booking.
     *
     * @param number     id of the Booking
     * @param roomNumber id of the Room
     * @param service    ServiceProvider service
     * @throws ServiceException if the operation failed
     */
    void changeRoom(long number, int roomNumber, ServiceProvider service) throws ServiceException;

    /**
     * Provides logic for changing the Booking Status.
     *
     * @param number  id of the Booking
     * @param status  Booking status
     * @param service ServiceProvider service
     * @throws ServiceException if the operation failed
     */
    void changeBookingStatus(long number, String status, ServiceProvider service) throws ServiceException;
}
