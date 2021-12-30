package by.rozmysl.bookingServlet.model.dao.Impl;

import by.rozmysl.bookingServlet.exception.DaoException;
import by.rozmysl.bookingServlet.model.dao.BookingDao;
import by.rozmysl.bookingServlet.model.entity.hotel.AdditionalServices;
import by.rozmysl.bookingServlet.model.entity.hotel.Food;
import by.rozmysl.bookingServlet.model.entity.hotel.Room;
import by.rozmysl.bookingServlet.model.entity.user.Booking;
import by.rozmysl.bookingServlet.model.entity.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

import static by.rozmysl.bookingServlet.model.dao.SqlQuery.*;

/**
 * Provides the base model implementation for `BOOKING` table DAO with the <b>ProxyConnection</b> properties.
 */
public class BookingDaoImpl implements BookingDao {
    private static final Logger LOGGER = LoggerFactory.getLogger(BookingDaoImpl.class);
    private final Connection connection;

    /**
     * The constructor creates a new object BookingDaoImpl with the <b>connection</b> property
     */
    public BookingDaoImpl(Connection connection) {
        this.connection = connection;
    }

    /**
     * Counts the number of pages in the 'BOOKING` table
     *
     * @param rows number of rows per page
     * @return number of pages
     * @throws DaoException if there was an error accessing the database
     */
    @Override
    public int countBookingsPages(int rows) throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(BOOKING_QUERY_FIND_ROWS_COUNT)) {
            final ResultSet rs = preparedStatement.executeQuery();
            rs.next();
            return (int) Math.ceil((float) rs.getInt(BOOKING_ROWS_COUNT) / rows);
        } catch (SQLException e) {
            LOGGER.error("'countBookingsPages' query has been failed", e);
            throw new DaoException("'countBookingsPages' query has been failed", e);
        }
    }

    /**
     * Searches for the Booking in the `BOOKING` table by id
     *
     * @param number id of the Room
     * @return Booking object
     * @throws DaoException if there was an error accessing the database
     */
    @Override
    public Booking findById(Long number) throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(BOOKING_FIND_BY_ID)) {
            preparedStatement.setLong(1, number);
            final ResultSet rs = preparedStatement.executeQuery();
            rs.next();
            return buildBooking(rs);
        } catch (SQLException e) {
            LOGGER.error("'Booking findById' query has been failed", e);
            throw new DaoException("'Booking findById' query has been failed", e);
        }
    }

    /**
     * Searches for all Bookings in the `BOOKING` table
     *
     * @param page number of the page to return
     * @param rows number of rows per page
     * @return list of Booking objects
     * @throws DaoException if there was an error accessing the database
     */
    @Override
    public List<Booking> findAll(int page, int rows) throws DaoException {
        List<Booking> bookings = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(BOOKING_FIND_ALL)) {
            preparedStatement.setInt(1, rows);
            preparedStatement.setInt(2, page);
            preparedStatement.setInt(3, rows);
            final ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                bookings.add(buildBooking(rs));
            }
        } catch (SQLException e) {
            LOGGER.error("'Booking findAll' query has been failed", e);
            throw new DaoException("'Booking findAll' query has been failed", e);
        }
        return bookings;
    }

    /**
     * Saves the Booking in the `BOOKING` table
     *
     * @param booking Booking
     * @return Booking object
     * @throws DaoException if there was an error accessing the database
     */
    @Override
    public Booking save(Booking booking) throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(BOOKING_SAVE, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, booking.getUser().getUsername());
            preparedStatement.setInt(2, booking.getRoom().getRoomNumber());
            preparedStatement.setInt(3, booking.getPersons());
            preparedStatement.setDate(4, Date.valueOf(booking.getArrival()));
            preparedStatement.setDate(5, Date.valueOf(booking.getDeparture()));
            preparedStatement.setString(6, booking.getFood().getType());
            preparedStatement.setInt(7, booking.getDays());
            preparedStatement.setString(8, booking.getServices().getType());
            preparedStatement.setBigDecimal(9, booking.getAmount());
            preparedStatement.setString(10, booking.getStatus());
            preparedStatement.executeUpdate();
            ResultSet tableKeys = preparedStatement.getGeneratedKeys();
            tableKeys.next();
            tableKeys.getInt(1);
        } catch (SQLException e) {
            LOGGER.error("'Booking save' query has been failed", e);
            throw new DaoException("'Booking save' query has been failed", e);
        }
        return booking;
    }

    /**
     * Deletes the Booking in the `BOOKING` table
     *
     * @param number id of the Booking
     * @throws DaoException if there was an error accessing the database
     */
    @Override
    public void delete(Long number) throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(BOOKING_DELETE)) {
            preparedStatement.setLong(1, number);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("'Booking delete' query has been failed", e);
            throw new DaoException("'Booking delete' query has been failed", e);
        }
    }

    /**
     * Searches for all Bookings between two dates in the `BOOKING` table
     *
     * @param arrival   arrival date
     * @param departure departure date
     * @return list of Booking objects
     * @throws DaoException if there was an error accessing the database
     */
    @Override
    public List<Booking> findAllBookingsBetweenTwoDates(LocalDate arrival, LocalDate departure) throws DaoException {
        List<Booking> bookings = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(BOOKING_FIND_ALL_BOOKINGS_BETWEEN_TWO_DATES)) {
            preparedStatement.setDate(1, Date.valueOf(departure));
            preparedStatement.setDate(2, Date.valueOf(arrival));
            final ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                bookings.add(buildBooking(rs));
            }
        } catch (SQLException e) {
            LOGGER.error("'findAllBookingsBetweenTwoDates' query has been failed", e);
            throw new DaoException("'findAllBookingsBetweenTwoDates' query has been failed", e);
        }
        return bookings;
    }

    /**
     * Search for all Bookings made by the user in the `BOOKING` table
     *
     * @param user username
     * @return list of Booking objects
     * @throws DaoException if there was an error accessing the database
     */
    @Override
    public List<Booking> findAllBookingsByUser(String user) throws DaoException {
        List<Booking> bookings = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(BOOKING_FIND_ALL_BOOKINGS_BY_USER)) {
            preparedStatement.setString(1, user);
            final ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                bookings.add(buildBooking(rs));
            }
        } catch (SQLException e) {
            LOGGER.error("'findAllBookingsByUser' query has been failed", e);
            throw new DaoException("'findAllBookingsByUser' query has been failed", e);
        }
        return bookings;
    }

    /**
     * Changes the Room in the User's Booking in the `BOOKING` table
     *
     * @param number     id of the Booking
     * @param roomNumber id of the Room
     * @param amount     Booking amount
     * @throws DaoException if there was an error accessing the database
     */
    @Override
    public void changeRoom(long number, int roomNumber, BigDecimal amount) throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(BOOKING_CHANGE_ROOM)) {
            preparedStatement.setInt(1, roomNumber);
            preparedStatement.setBigDecimal(2, amount);
            preparedStatement.setLong(3, number);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("'changeRoom' query has been failed", e);
            throw new DaoException("'changeRoom' query has been failed", e);
        }
    }

    /**
     * Changes the User's Booking status in the `BOOKING` table
     *
     * @param number id of the Booking
     * @param status booking status
     * @throws DaoException if there was an error accessing the database
     */
    @Override
    public void changeBookingStatus(long number, String status) throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(BOOKING_CHANGE_BOOKING_STATUS)) {
            preparedStatement.setString(1, status);
            preparedStatement.setLong(2, number);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("'changeBookingStatus' query has been failed", e);
            throw new DaoException("'changeBookingStatus' query has been failed", e);
        }
    }

    private Booking buildBooking(ResultSet rs) throws SQLException {
        return new Booking(
                rs.getLong(BOOKING_COLUMN_NUMBER),
                new User(rs.getString(BOOKING_COLUMN_USER)),
                new Room(rs.getInt(BOOKING_COLUMN_ROOM), rs.getString(ROOM_COLUMN_TYPE), rs.getInt(ROOM_COLUMN_SLEEPS)),
                rs.getInt(BOOKING_COLUMN_PERSONS),
                LocalDate.parse(rs.getString(BOOKING_COLUMN_ARRIVAL)),
                LocalDate.parse(rs.getString(BOOKING_COLUMN_DEPARTURE)),
                rs.getInt(BOOKING_COLUMN_DAYS),
                new Food(rs.getString(BOOKING_COLUMN_FOOD)),
                new AdditionalServices(rs.getString(BOOKING_COLUMN_SERVICES)),
                rs.getBigDecimal(BOOKING_COLUMN_AMOUNT),
                rs.getString(BOOKING_COLUMN_STATUS));
    }
}
