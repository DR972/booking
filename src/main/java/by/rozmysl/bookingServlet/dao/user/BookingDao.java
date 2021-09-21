package by.rozmysl.bookingServlet.dao.user;

import by.rozmysl.bookingServlet.dao.Dao;
import by.rozmysl.bookingServlet.entity.user.Booking;

import javax.mail.MessagingException;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

/**
 * Provides the base model DAO interface for `BOOKING` table.
 */
public interface BookingDao extends Dao<Booking, Long> {

    /**
     * Counts the number of pages in the 'BOOKING` table
     *
     * @param rows number of rows per page
     * @return number of pages
     * @throws SQLException if there was an error accessing the database
     */
    int countBookingsPages(int rows) throws SQLException;

    /**
     * Searches for all Bookings between two dates in the `BOOKING` table
     *
     * @param arrival   arrival date
     * @param departure departure date
     * @return list of Booking objects
     * @throws SQLException if there was an error accessing the database
     */
    List<Booking> findAllBookingsBetweenTwoDates(LocalDate departure, LocalDate arrival) throws SQLException;

    /**
     * Search for all Bookings made by the user in the `BOOKING` table
     *
     * @param user username
     * @return list of Booking objects
     * @throws SQLException if there was an error accessing the database
     */
    List<Booking> findAllByUser(String user) throws SQLException;

    /**
     * Changes the Room in the User's Booking in the `BOOKING` table
     *
     * @param bookingId id of the Booking
     * @param roomId    id of the Room
     * @throws SQLException if there was an error accessing the database
     */
    void changeRoom(long bookingId, int roomId) throws SQLException;

    /**
     * Changes the User's Booking status in the `BOOKING` table
     *
     * @param bookingId id of the Booking
     * @param status    booking status
     * @throws SQLException       if there was an error accessing the database
     * @throws MessagingException if the message cannot be created
     * @throws IOException        if the letter cannot be created
     */
    void changeStatusBooking(long bookingId, String status) throws IOException, MessagingException, SQLException;
}