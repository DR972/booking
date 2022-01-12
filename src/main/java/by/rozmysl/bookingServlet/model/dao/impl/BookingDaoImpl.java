package by.rozmysl.bookingServlet.model.dao.impl;

import by.rozmysl.bookingServlet.exception.DaoException;
import by.rozmysl.bookingServlet.model.dao.BookingDao;
import by.rozmysl.bookingServlet.model.dao.StatementConsumer;
import by.rozmysl.bookingServlet.model.entity.hotel.AdditionalServices;
import by.rozmysl.bookingServlet.model.entity.hotel.Food;
import by.rozmysl.bookingServlet.model.entity.hotel.Room;
import by.rozmysl.bookingServlet.model.entity.user.Booking;
import by.rozmysl.bookingServlet.model.entity.user.User;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static by.rozmysl.bookingServlet.model.LoggerMessage.*;
import static by.rozmysl.bookingServlet.model.dao.SqlQuery.*;
import static by.rozmysl.bookingServlet.model.dao.ColumnName.*;

/**
 * Provides the base model implementation for `BOOKING` table DAO with the <b>ProxyConnection</b> properties.
 */
public class BookingDaoImpl extends DaoImpl<Booking, Long> implements BookingDao {

    @Override
    public Booking buildEntity(ResultSet resultSet) throws SQLException {
//        if (resultSet.getRow()  == 0) {
//            return null;
//        }
        return new Booking(
                resultSet.getLong(BOOKING_COLUMN_NUMBER),
                new User(resultSet.getString(BOOKING_COLUMN_USER)),
                new Room(resultSet.getInt(BOOKING_COLUMN_ROOM), resultSet.getString(ROOM_COLUMN_TYPE), resultSet.getInt(ROOM_COLUMN_SLEEPS)),
                resultSet.getInt(BOOKING_COLUMN_PERSONS),
                LocalDate.parse(resultSet.getString(BOOKING_COLUMN_ARRIVAL)),
                LocalDate.parse(resultSet.getString(BOOKING_COLUMN_DEPARTURE)),
                resultSet.getInt(BOOKING_COLUMN_DAYS),
                new Food(resultSet.getString(BOOKING_COLUMN_FOOD)),
                new AdditionalServices(resultSet.getString(BOOKING_COLUMN_SERVICES)),
                resultSet.getBigDecimal(BOOKING_COLUMN_AMOUNT),
                resultSet.getString(BOOKING_COLUMN_STATUS));
    }

//    /**
//     * Get a list of query results from the 'BOOKING' table
//     *
//     * @param sql               the wording of the request to the database
//     * @param message           in case of an error
//     * @param statementConsumer StatementConsumer
//     * @return list of Booking objects
//     * @throws DaoException if there was an error accessing the database
//     */
//    @Override
//    public List<Booking> getResultSet(String sql, String message, StatementConsumer statementConsumer) throws DaoException {
//        return get(sql, message, statementConsumer).stream().map(b -> new Booking(
//                Long.parseLong(b.get(BOOKING_COLUMN_NUMBER)),
//                new User(b.get(BOOKING_COLUMN_USER)),
//                new Room(Integer.parseInt(b.get(BOOKING_COLUMN_ROOM)),
//                        b.get(ROOM_COLUMN_TYPE),
//                        Integer.parseInt(b.get(ROOM_COLUMN_SLEEPS))),
//                Integer.parseInt(b.get(BOOKING_COLUMN_PERSONS)),
//                LocalDate.parse(b.get(BOOKING_COLUMN_ARRIVAL)),
//                LocalDate.parse(b.get(BOOKING_COLUMN_DEPARTURE)),
//                Integer.parseInt(b.get(BOOKING_COLUMN_DAYS)),
//                new Food(b.get(BOOKING_COLUMN_FOOD)),
//                new AdditionalServices(b.get(BOOKING_COLUMN_SERVICES)),
//                new BigDecimal(b.get(BOOKING_COLUMN_AMOUNT)),
//                b.get(BOOKING_COLUMN_STATUS))).collect(Collectors.toList());
//    }
//    /**
//     * Constructs dao for `Booking` table.
//     */
//    public BookingDaoImpl() {
//        super(BOOKING_FIND_BY_ID, MESSAGE_BOOKING_FIND_BY_ID, BOOKING_FIND_ALL, MESSAGE_BOOKING_FIND_ALL, BOOKING_DELETE, MESSAGE_BOOKING_DELETE);
//    }
//
//    /**
//     * Counts the number of pages in the 'BOOKING` table
//     *
//     * @param rows number of rows per page
//     * @return number of pages
//     * @throws DaoException if there was an error accessing the database
//     */
//    @Override
//    public int countBookingsPages(int rows) throws DaoException {
//        return (int) Math.ceil((float) countRows(BOOKING_QUERY_FIND_ROWS_COUNT, MESSAGE_COUNT_BOOKINGS_PAGES) / rows);
//    }

//    /**
//     * Saves the Booking in the `BOOKING` table
//     *
//     * @param booking Booking
//     * @throws DaoException if there was an error accessing the database
//     */
//    @Override
//    public void save(Booking booking) throws DaoException {
//        saveWithGeneratedKeys(connection, BOOKING_SAVE, MESSAGE_BOOKING_SAVE, statement -> {
//            statement.setString(1, booking.getUser().getId());
//            statement.setInt(2, booking.getRoom().getId());
//            statement.setInt(3, booking.getPersons());
//            statement.setDate(4, Date.valueOf(booking.getArrival()));
//            statement.setDate(5, Date.valueOf(booking.getDeparture()));
//            statement.setString(6, booking.getFood().getId());
//            statement.setInt(7, booking.getDays());
//            statement.setString(8, booking.getServices().getId());
//            statement.setBigDecimal(9, booking.getAmount());
//            statement.setString(10, booking.getStatus());
//        });
//    }

//    /**
//     * Searches for all Bookings between two dates in the `BOOKING` table
//     *
//     * @param arrival   arrival date
//     * @param departure departure date
//     * @return list of Booking objects
//     * @throws DaoException if there was an error accessing the database
//     */
//    @Override
//    public List<Booking> findAllBookingsBetweenTwoDates(LocalDate arrival, LocalDate departure) throws DaoException {
//        return getResultSet(BOOKING_FIND_ALL_BOOKINGS_BETWEEN_TWO_DATES, MESSAGE_BOOKING_FIND_ALL_BOOKINGS_BETWEEN_TWO_DATES, statement -> {
//            statement.setDate(1, Date.valueOf(departure));
//            statement.setDate(2, Date.valueOf(arrival));
//        });
//    }

//    /**
//     * Search for all Bookings made by the username in the `BOOKING` table
//     *
//     * @param username username
//     * @return list of Booking objects
//     * @throws DaoException if there was an error accessing the database
//     */
//    @Override
//    public List<Booking> findAllBookingsByUser(String username) throws DaoException {
//        return getResultSet(BOOKING_FIND_ALL_BOOKINGS_BY_USER, MESSAGE_BOOKING_FIND_ALL_BOOKINGS_BY_USER,
//                statement -> statement.setString(1, username));
//    }
//
//    /**
//     * Changes the Room in the User's Booking in the `BOOKING` table
//     *
//     * @param number     id of the Booking
//     * @param roomNumber id of the Room
//     * @param amount     Booking amount
//     * @throws DaoException if there was an error accessing the database
//     */
//    @Override
//    public void changeRoom(long number, int roomNumber, BigDecimal amount) throws DaoException {
//        update(connection, BOOKING_CHANGE_ROOM, MESSAGE_BOOKING_CHANGE_ROOM, statement -> {
//            statement.setInt(1, roomNumber);
//            statement.setBigDecimal(2, amount);
//            statement.setLong(3, number);
//        });
//    }
//
//    /**
//     * Changes the User's Booking status in the `BOOKING` table
//     *
//     * @param number id of the Booking
//     * @param status booking status
//     * @throws DaoException if there was an error accessing the database
//     */
//    @Override
//    public void changeBookingStatus(long number, String status) throws DaoException {
//        update(connection, BOOKING_CHANGE_BOOKING_STATUS, MESSAGE_BOOKING_CHANGE_BOOKING_STATUS, statement -> {
//            statement.setString(1, status);
//            statement.setLong(2, number);
//        });
//    }
}
