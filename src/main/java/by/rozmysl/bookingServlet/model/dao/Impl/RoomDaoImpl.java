package by.rozmysl.bookingServlet.model.dao.Impl;

import by.rozmysl.bookingServlet.exception.DaoException;
import by.rozmysl.bookingServlet.model.dao.RoomDao;
import by.rozmysl.bookingServlet.model.entity.hotel.Room;
import by.rozmysl.bookingServlet.model.service.impl.RoomServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

import static by.rozmysl.bookingServlet.model.dao.SqlQuery.*;

/**
 * Provides the base model implementation for `ROOM` table DAO with the <b>ProxyConnection</b> properties.
 */
public class RoomDaoImpl implements RoomDao {
    private static final Logger LOGGER = LoggerFactory.getLogger(RoomServiceImpl.class);
    private final Connection connection;

    /**
     * The constructor creates a new object RoomDaoImpl with the <b>connection</b> property
     */
    public RoomDaoImpl(Connection connection) {
        this.connection = connection;
    }

    /**
     * Searches for the Room in the `ROOM` table by id
     *
     * @param roomNumber id of the Room
     * @return Room object
     * @throws DaoException if there was an error accessing the database
     */
    @Override
    public Room findById(Integer roomNumber) throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(ROOM_FIND_BY_ID)) {
            preparedStatement.setInt(1, roomNumber);
            final ResultSet rs = preparedStatement.executeQuery();
            rs.next();
            return buildRoom(rs);
        } catch (SQLException e) {
            LOGGER.error("'Room findById' query has been failed", e);
            throw new DaoException("'Room findById' query has been failed", e);
        }
    }

    /**
     * Searches for all Rooms in the `ROOM` table
     *
     * @param page number of the page to return
     * @param rows number of rows per page
     * @return list of Room objects
     * @throws DaoException if there was an error accessing the database
     */
    @Override
    public List<Room> findAll(int page, int rows) throws DaoException {
        List<Room> rooms = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(ROOM_FIND_ALL)) {
            final ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                rooms.add(buildRoom(rs));
            }
        } catch (SQLException e) {
            LOGGER.error("'Room findAll' query has been failed", e);
            throw new DaoException("'Room findAll' query has been failed", e);
        }
        return rooms;
    }

    /**
     * Saves the Room in the `ROOM` table
     *
     * @param room Room
     * @return Room object
     * @throws DaoException if there was an error accessing the database
     */
    @Override
    public Room save(Room room) throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(ROOM_SAVE)) {
            preparedStatement.setInt(1, room.getRoomNumber());
            preparedStatement.setString(2, room.getType());
            preparedStatement.setInt(3, room.getSleeps());
            preparedStatement.setBigDecimal(4, room.getPrice());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("'Room save' query has been failed", e);
            throw new DaoException("'Room save' query has been failed", e);
        }
        return room;
    }

    /**
     * Deletes the Room in the `ROOM` table
     *
     * @param roomNumber id of the Room
     * @throws DaoException if there was an error accessing the database
     */
    @Override
    public void delete(Integer roomNumber) throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(ROOM_DELETE)) {
            preparedStatement.setLong(1, roomNumber);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("'Room delete' query has been failed", e);
            throw new DaoException("'Room delete' query has been failed", e);
        }
    }

    /**
     * Changes the price of the Room in the `ROOM` table
     *
     * @param room  Room
     * @param price new price
     * @throws DaoException if there was an error accessing the database
     */
    @Override
    public void updatePrice(Room room, BigDecimal price) throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(ROOM_UPDATE_PRICE)) {
            preparedStatement.setBigDecimal(1, price);
            preparedStatement.setString(2, room.getType());
            preparedStatement.setInt(3, room.getSleeps());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("'Room updatePrice' query has been failed", e);
            throw new DaoException("'Room updatePrice' query has been failed", e);
        }
    }

    /**
     * Changes the parameters of the Room in the `ROOM` table
     *
     * @param roomNumber id of the Room
     * @param param      Room object
     * @throws DaoException if there was an error accessing the database
     */
    @Override
    public void updateParameters(int roomNumber, Room param) throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(ROOM_UPDATE_PARAMETERS)) {
            preparedStatement.setString(1, param.getType());
            preparedStatement.setInt(2, param.getSleeps());
            preparedStatement.setBigDecimal(3, param.getPrice());
            preparedStatement.setInt(4, roomNumber);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("'Room updateParameters' query has been failed", e);
            throw new DaoException("'Room updateParameters' query has been failed", e);
        }
    }

    /**
     * Searches for all Room types in the `ROOM` table
     *
     * @return list of Room objects
     * @throws DaoException if there was an error accessing the database
     */
    @Override
    public List<Room> findAllTypesRooms() throws DaoException {
        List<Room> rooms = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(ROOM_FIND_ALL_TYPES_ROOMS)) {
            final ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                rooms.add(buildRoom(rs));
            }
        } catch (SQLException e) {
            LOGGER.error("'findAllTypesRooms' query has been failed", e);
            throw new DaoException("'findAllTypesRooms' query has been failed", e);
        }
        return rooms;
    }

    /**
     * Searches for all Rooms by type and sleeps in the `ROOM` table
     *
     * @return list of Room objects
     * @throws DaoException if there was an error accessing the database
     */
    @Override
    public List<Room> findAllRoomsByTypesAndSleeps() throws DaoException {
        List<Room> rooms = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(ROOM_FIND_ALL_ROOMS_BY_TYPES_AND_SLEEPS)) {
            final ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                rooms.add(buildRoom(rs));
            }
        } catch (SQLException e) {
            LOGGER.error("'findAllRoomsByTypesAndSleeps' query has been failed", e);
            throw new DaoException("'findAllRoomsByTypesAndSleeps' query has been failed", e);
        }
        return rooms;
    }

    /**
     * Searches for the Room by type and sleeps in the `ROOM` table
     *
     * @param type   of the Room
     * @param sleeps in the Room
     * @return Room object
     * @throws DaoException if there was an error accessing the database
     */
    @Override
    public Room findRoomByTypeAndSleeping(String type, int sleeps) throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(ROOM_FIND_ROOM_BY_TYPE_AND_SLEEPING)) {
            preparedStatement.setString(1, type);
            preparedStatement.setInt(2, sleeps);
            final ResultSet rs = preparedStatement.executeQuery();
            rs.next();
            return buildRoom(rs);
        } catch (SQLException e) {
            LOGGER.error("'findRoomByTypeAndSleeping' query has been failed", e);
            throw new DaoException("'findRoomByTypeAndSleeping' query has been failed", e);
        }
    }

    /**
     * Searches for all free Rooms between two dates in the `ROOM` table
     *
     * @param first  date
     * @param second date
     * @return list of Room objects
     * @throws DaoException if there was an error accessing the database
     */
    @Override
    public List<Room> findAllFreeRoomsBetweenTwoDates(LocalDate first, LocalDate second) throws DaoException {
        List<Room> rooms = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(ROOM_FIND_ALL_FREE_ROOMS_BETWEEN_TWO_DATES)) {
            preparedStatement.setDate(1, Date.valueOf(second));
            preparedStatement.setDate(2, Date.valueOf(first));
            final ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                rooms.add(buildRoom(rs));
            }
        } catch (SQLException e) {
            LOGGER.error("'findAllFreeRoomsBetweenTwoDates' query has been failed", e);
            throw new DaoException("'findAllFreeRoomsBetweenTwoDates' query has been failed", e);
        }
        return rooms;
    }

    /**
     * Searches for all free Room types between two dates in the `ROOM` table
     *
     * @param first  date
     * @param second date
     * @return list of Room objects
     * @throws DaoException if there was an error accessing the database
     */
    @Override
    public List<Room> findAllTypesFreeRoomsBetweenTwoDates(LocalDate first, LocalDate second) throws DaoException {
        List<Room> rooms = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(ROOM_FIND_ALL_TYPES_FREE_ROOMS_BETWEEN_TWO_DATES)) {
            preparedStatement.setDate(1, Date.valueOf(second));
            preparedStatement.setDate(2, Date.valueOf(first));
            final ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                rooms.add(buildRoom(rs));
            }
        } catch (SQLException e) {
            LOGGER.error("'findAllTypesFreeRoomsBetweenTwoDates' query has been failed", e);
            throw new DaoException("'findAllTypesFreeRoomsBetweenTwoDates' query has been failed", e);
        }
        return rooms;
    }

    /**
     * Searches for all free Rooms between two dates by type and sleeps in the `ROOM` table
     *
     * @param first  date
     * @param second date
     * @param type   of the Room
     * @param sleeps in the Room
     * @return list of Room objects
     * @throws DaoException if there was an error accessing the database
     */
    @Override
    public List<Room> findAllFreeRoomsBetweenTwoDatesByTypesAndSleeps(LocalDate first, LocalDate second, String type, int sleeps) throws DaoException {
        List<Room> rooms = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(ROOM_FIND_ALL_FREE_ROOMS_BETWEEN_TWO_DATES_BY_TYPES_AND_SLEEPS)){
            preparedStatement.setString(1, type);
            preparedStatement.setInt(2, sleeps);
            preparedStatement.setDate(3, Date.valueOf(second));
            preparedStatement.setDate(4, Date.valueOf(first));
            final ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                rooms.add(buildRoom(rs));
            }
        } catch (SQLException e) {
            LOGGER.error("'findAllFreeRoomsBetweenTwoDatesByTypesAndSleeps' query has been failed", e);
            throw new DaoException("'findAllFreeRoomsBetweenTwoDatesByTypesAndSleeps' query has been failed", e);
        }
        return rooms;
    }

    /**
     * Searches for all free Room types between two dates with greater or equal sleeps in the `ROOM` table
     *
     * @param first  date
     * @param second date
     * @param sleeps in the Room
     * @return list of Room objects
     * @throws DaoException if there was an error accessing the database
     */
    @Override
    public List<Room> findAllTypesFreeRoomsBetweenTwoDatesWithGreaterOrEqualSleeps(LocalDate first, LocalDate second, int sleeps) throws DaoException {
        List<Room> rooms = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(ROOM_FIND_ALL_TYPES_FREE_ROOMS_BETWEEN_TWO_DATES_WITH_GREATER_OR_EQUAL_SLEEPS)){
            preparedStatement.setInt(1, sleeps);
            preparedStatement.setDate(2, Date.valueOf(second));
            preparedStatement.setDate(3, Date.valueOf(first));
            final ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                rooms.add(buildRoom(rs));
            }
        } catch (SQLException e) {
            LOGGER.error("'findAllTypesFreeRoomsBetweenTwoDatesWithGreaterOrEqualSleeps' query has been failed", e);
            throw new DaoException("'findAllTypesFreeRoomsBetweenTwoDatesWithGreaterOrEqualSleeps' query has been failed", e);
        }
        return rooms;
    }

    /**
     * Searches for all free Rooms between two dates with greater or equal sleeps in the `ROOM` table
     *
     * @param first  date
     * @param second date
     * @param sleeps in the Room
     * @return list of Room objects
     * @throws DaoException if there was an error accessing the database
     */
    @Override
    public List<Room> findAllFreeRoomsBetweenTwoDatesWithGreaterOrEqualSleeps(LocalDate first, LocalDate second, int sleeps) throws DaoException {
        List<Room> rooms = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(ROOM_FIND_ALL_FREE_ROOMS_BETWEEN_TWO_DATES_WITH_GREATER_OR_EQUAL_SLEEPS)){
            preparedStatement.setInt(1, sleeps);
            preparedStatement.setDate(2, Date.valueOf(second));
            preparedStatement.setDate(3, Date.valueOf(first));
            final ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                rooms.add(buildRoom(rs));
            }
        } catch (SQLException e) {
            LOGGER.error("'findAllFreeRoomsBetweenTwoDatesWithGreaterOrEqualSleeps' query has been failed", e);
            throw new DaoException("'findAllFreeRoomsBetweenTwoDatesWithGreaterOrEqualSleeps' query has been failed", e);
        }
        return rooms;
    }

    private Room buildRoom(ResultSet rs) throws SQLException {
        if (rs.getRow()  == 0) {
            return null;
        }
        return new Room(
                rs.getInt(ROOM_COLUMN_ROOMNUMBER),
                rs.getString(ROOM_COLUMN_TYPE),
                rs.getInt(ROOM_COLUMN_SLEEPS),
                rs.getBigDecimal(ROOM_COLUMN_PRICE));
    }
}
