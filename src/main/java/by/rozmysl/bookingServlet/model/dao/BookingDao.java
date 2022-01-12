package by.rozmysl.bookingServlet.model.dao;

import by.rozmysl.bookingServlet.exception.DaoException;
import by.rozmysl.bookingServlet.model.entity.user.Booking;

import java.math.BigDecimal;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;

/**
 * Provides the base model DAO interface for `BOOKING` table.
 */
public interface BookingDao extends Dao<Booking, Long> {

    void saveWithGeneratedKeys(String sql, String message, Object... params) throws DaoException;

//
//    /**
//     * Counts the number of pages in the 'BOOKING` table
//     *
//     * @param rows number of rows per page
//     * @return number of pages
//     * @throws DaoException if there was an error accessing the database
//     */
//    int countBookingsPages(int rows) throws DaoException;

//    /**
//     * Searches for all Bookings between two dates in the `BOOKING` table
//     *
//     * @param arrival   arrival date
//     * @param departure departure date
//     * @return list of Booking objects
//     * @throws DaoException if there was an error accessing the database
//     */
//    List<Booking> findAllBookingsBetweenTwoDates(LocalDate departure, LocalDate arrival) throws DaoException;
//
//    /**
//     * Search for all Bookings made by the user in the `BOOKING` table
//     *
//     * @param user username
//     * @return list of Booking objects
//     * @throws DaoException if there was an error accessing the database
//     */
//    List<Booking> findAllBookingsByUser(String user) throws DaoException;

//    /**
//     * Changes the Room in the User's Booking in the `BOOKING` table
//     *
//     * @param bookingId id of the Booking
//     * @param roomId    id of the Room
//     * @param amount       Booking amount
//     * @throws DaoException if there was an error accessing the database
//     */
//    void changeRoom(long bookingId, int roomId, BigDecimal amount) throws DaoException;
//
//    /**
//     * Changes the User's Booking status in the `BOOKING` table
//     *
//     * @param bookingId id of the Booking
//     * @param status    booking status
//     * @throws DaoException       if there was an error accessing the database
//     */
//    void changeBookingStatus(long bookingId, String status) throws DaoException;
}