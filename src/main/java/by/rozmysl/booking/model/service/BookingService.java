package by.rozmysl.booking.model.service;

import by.rozmysl.booking.exception.ServiceException;
import by.rozmysl.booking.model.entity.user.Booking;

import java.time.LocalDate;
import java.util.List;

/**
 * Provides logic for working with data sent to the `Booking` table DAO.
 */
public interface BookingService {

    /**
     * Provides logic for searching for a Booking object by id
     *
     * @param number id of the Booking object
     * @return Booking object
     * @throws ServiceException if the operation failed
     */
    Booking findById(Long number) throws ServiceException;

    /**
     * Provides logic for searching for all Booking objects.
     *
     * @param pageNumber number of the page to return
     * @param rows       number of rows per page
     * @return list of Booking objects
     * @throws ServiceException if the operation failed
     */
    List<Booking> findAll(int pageNumber, int rows) throws ServiceException;

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
     * Provides logic for counting the number of pages of all Booking objects.
     *
     * @param rows       number of rows per page
     * @return number of pages
     * @throws ServiceException if the operation failed
     */
    int countNumberBookingPages(int rows) throws ServiceException;

    /**
     * Provides booking validation.
     *
     * @param booking Booking booking
     * @return validation result
     */
    String validateBooking(Booking booking);

    /**
     * Provides logic for searching for all Bookings between two dates.
     *
     * @param arrival   arrival date
     * @param departure departure date
     * @return list of Booking objects
     * @throws ServiceException if the operation failed
     */
    List<Booking> findAllBookingsBetweenTwoDates(LocalDate arrival, LocalDate departure) throws ServiceException;

    /**
     * Provides logic for searching for all Bookings made by the username.
     *
     * @param username   id of the User
     * @return list of Booking objects
     * @throws ServiceException if the operation failed
     */
    List<Booking> findAllBookingsByUser(String username) throws ServiceException;

    /**
     * Provides logic for changing the Room in the User's Booking.
     *
     * @param number     id of the Booking
     * @param roomNumber id of the Room
     * @param service    ServiceFactory service
     * @throws ServiceException if the operation failed
     */
    void changeRoom(long number, int roomNumber, ServiceFactory service) throws ServiceException;

    /**
     * Provides logic for changing the Booking Status.
     *
     * @param number  id of the Booking
     * @param status  Booking status
     * @param service ServiceFactory service
     * @throws ServiceException if the operation failed
     */
    void changeBookingStatus(long number, String status, ServiceFactory service) throws ServiceException;
}
