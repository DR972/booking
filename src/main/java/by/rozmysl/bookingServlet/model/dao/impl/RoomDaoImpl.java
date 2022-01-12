package by.rozmysl.bookingServlet.model.dao.impl;

import by.rozmysl.bookingServlet.exception.DaoException;
import by.rozmysl.bookingServlet.model.dao.RoomDao;
import by.rozmysl.bookingServlet.model.dao.StatementConsumer;
import by.rozmysl.bookingServlet.model.entity.hotel.Room;

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
 * Provides the base model implementation for `ROOM` table DAO with the <b>ProxyConnection</b> properties.
 */
public class RoomDaoImpl extends DaoImpl<Room, Integer> implements RoomDao {

    @Override
    public Room buildEntity(ResultSet resultSet) throws SQLException {
//        if (resultSet.getRow()  == 0) {
//            return null;
//        }
        return new Room(
                resultSet.getInt(ROOM_COLUMN_ROOMNUMBER),
                resultSet.getString(ROOM_COLUMN_TYPE),
                resultSet.getInt(ROOM_COLUMN_SLEEPS),
                resultSet.getBigDecimal(ROOM_COLUMN_PRICE));
    }

//    /**
//     * Get a list of query results from the 'Room' table
//     *
//     * @param sql               the wording of the request to the database
//     * @param message           in case of an error
//     * @param statementConsumer StatementConsumer
//     * @return list of Room objects
//     * @throws DaoException if there was an error accessing the database
//     */
//    @Override
//    public List<Room> getResultSet(String sql, String message, StatementConsumer statementConsumer) throws DaoException {
//        return get(sql, message, statementConsumer).stream().map(r -> new Room(
//                Integer.parseInt(r.get(ROOM_COLUMN_ROOMNUMBER)),
//                r.get(ROOM_COLUMN_TYPE),
//                Integer.parseInt(r.get(ROOM_COLUMN_SLEEPS)),
//                new BigDecimal(r.get(ROOM_COLUMN_PRICE)))).collect(Collectors.toList());
//    }

//    /**
//     * Constructs dao for `Room` table.
//     */
//    public RoomDaoImpl() {
//        super(ROOM_FIND_BY_ID, MESSAGE_ROOM_FIND_BY_ID, ROOM_FIND_ALL, MESSAGE_ROOM_FIND_ALL, ROOM_DELETE, MESSAGE_ROOM_DELETE);
//    }

//    public List<Room> findListEntities(String sql, String message, Object ... params) throws DaoException {
//        System.out.println(params.length);
//        return getResultSet(sql, message, statement -> {
//            for (int i=0; i< params.length; i++) {
//                statement.setObject(i+1, params[i]);
//            }
//        });
//    }

//    /**
//     * Saves the Room in the `ROOM` table
//     *
//     * @param room Room
//     * @throws DaoException if there was an error accessing the database
//     */
//    @Override
//    public void save(Room room) throws DaoException {
//        update(connection, ROOM_SAVE, MESSAGE_ROOM_SAVE, statement -> {
//            statement.setInt(1, room.getId());
//            statement.setString(2, room.getType());
//            statement.setInt(3, room.getSleeps());
//            statement.setBigDecimal(4, room.getPrice());
//        });
//    }

//    /**
//     * Changes the price of the Room in the `ROOM` table
//     *
//     * @param room  Room
//     * @param price new price
//     * @throws DaoException if there was an error accessing the database
//     */
//    @Override
//    public void updatePrice(Room room, BigDecimal price) throws DaoException {
//        update(connection, ROOM_UPDATE_PRICE, MESSAGE_ROOM_UPDATE_PRICE, statement -> {
//            statement.setBigDecimal(1, price);
//            statement.setString(2, room.getType());
//            statement.setInt(3, room.getSleeps());
//        });
//    }
//
//    /**
//     * Changes the parameters of the Room in the `ROOM` table
//     *
//     * @param roomNumber id of the Room
//     * @param param      Room object
//     * @throws DaoException if there was an error accessing the database
//     */
//    @Override
//    public void updateParameters(int roomNumber, Room param) throws DaoException {
//        update(connection, ROOM_UPDATE_PARAMETERS, MESSAGE_ROOM_UPDATE_PARAMETERS, statement -> {
//            statement.setString(1, param.getType());
//            statement.setInt(2, param.getSleeps());
//            statement.setBigDecimal(3, param.getPrice());
//            statement.setInt(4, roomNumber);
//        });
//    }

//    /**
//     * Searches for all Room types in the `ROOM` table
//     *
//     * @return list of Room objects
//     * @throws DaoException if there was an error accessing the database
//     */
//    @Override
//    public List<Room> findAllTypesRooms() throws DaoException {
//        return getResultSet(ROOM_FIND_ALL_TYPES_ROOMS, MESSAGE_ROOM_FIND_ALL_TYPES_ROOMS, statement -> {
//        });
//    }

//    /**
//     * Searches for all Rooms by type and sleeps in the `ROOM` table
//     *
//     * @return list of Room objects
//     * @throws DaoException if there was an error accessing the database
//     */
//    @Override
//    public List<Room> findAllRoomsByTypesAndSleeps() throws DaoException {
//        return getResultSet(ROOM_FIND_ALL_ROOMS_BY_TYPES_AND_SLEEPS, MESSAGE_ROOM_FIND_ALL_ROOMS_BY_TYPES_AND_SLEEPS, statement -> {
//        });
//    }

//    /**
//     * Searches for the Room by type and sleeps in the `ROOM` table
//     *
//     * @param type   of the Room
//     * @param sleeps in the Room
//     * @return Room object
//     * @throws DaoException if there was an error accessing the database
//     */
//    @Override
//    public Room findRoomByTypeAndSleeping(String type, int sleeps) throws DaoException {
//        List<Room> services = getResultSet(ROOM_FIND_ROOM_BY_TYPE_AND_SLEEPING, MESSAGE_ROOM_FIND_ROOM_BY_TYPE_AND_SLEEPING, statement -> {
//            statement.setString(1, type);
//            statement.setInt(2, sleeps);
//        });
//        return services.size() != 0 ? services.get(0) : null;
//    }

//    /**
//     * Searches for all free Rooms between two dates in the `ROOM` table
//     *
//     * @param first  date
//     * @param second date
//     * @return list of Room objects
//     * @throws DaoException if there was an error accessing the database
//     */
//    @Override
//    public List<Room> findAllFreeRoomsBetweenTwoDates(LocalDate first, LocalDate second) throws DaoException {
//        return getResultSet(ROOM_FIND_ALL_FREE_ROOMS_BETWEEN_TWO_DATES, MESSAGE_ROOM_FIND_ALL_FREE_ROOMS_BETWEEN_TWO_DATES, statement -> {
//            statement.setDate(1, Date.valueOf(second));
//            statement.setDate(2, Date.valueOf(first));
//        });
//    }

//    /**
//     * Searches for all free Room types between two dates in the `ROOM` table
//     *
//     * @param first  date
//     * @param second date
//     * @return list of Room objects
//     * @throws DaoException if there was an error accessing the database
//     */
//    @Override
//    public List<Room> findAllTypesFreeRoomsBetweenTwoDates(LocalDate first, LocalDate second) throws DaoException {
//        return getResultSet(ROOM_FIND_ALL_TYPES_FREE_ROOMS_BETWEEN_TWO_DATES, MESSAGE_ROOM_FIND_ALL_TYPES_FREE_ROOMS_BETWEEN_TWO_DATES, statement -> {
//            statement.setDate(1, Date.valueOf(second));
//            statement.setDate(2, Date.valueOf(first));
//        });
//    }

//    /**
//     * Searches for all free Rooms between two dates by type and sleeps in the `ROOM` table
//     *
//     * @param first  date
//     * @param second date
//     * @param type   of the Room
//     * @param sleeps in the Room
//     * @return list of Room objects
//     * @throws DaoException if there was an error accessing the database
//     */
//    @Override
//    public List<Room> findAllFreeRoomsBetweenTwoDatesByTypesAndSleeps(LocalDate first, LocalDate second, String type, int sleeps) throws DaoException {
//        return getResultSet(ROOM_FIND_ALL_FREE_ROOMS_BETWEEN_TWO_DATES_BY_TYPES_AND_SLEEPS,
//                MESSAGE_ROOM_FIND_ALL_FREE_ROOMS_BETWEEN_TWO_DATES_BY_TYPES_AND_SLEEPS, statement -> {
//                    statement.setString(1, type);
//                    statement.setInt(2, sleeps);
//                    statement.setDate(3, Date.valueOf(second));
//                    statement.setDate(4, Date.valueOf(first));
//                });
//    }
//
//    /**
//     * Searches for all free Room types between two dates with greater or equal sleeps in the `ROOM` table
//     *
//     * @param first  date
//     * @param second date
//     * @param sleeps in the Room
//     * @return list of Room objects
//     * @throws DaoException if there was an error accessing the database
//     */
//    @Override
//    public List<Room> findAllTypesFreeRoomsBetweenTwoDatesWithGreaterOrEqualSleeps(LocalDate first, LocalDate second, int sleeps) throws DaoException {
//        return getResultSet(ROOM_FIND_ALL_TYPES_FREE_ROOMS_BETWEEN_TWO_DATES_WITH_GREATER_OR_EQUAL_SLEEPS,
//                MESSAGE_ROOM_FIND_ALL_TYPES_FREE_ROOMS_BETWEEN_TWO_DATES_WITH_GREATER_OR_EQUAL_SLEEPS, statement -> {
//                    statement.setInt(1, sleeps);
//                    statement.setDate(2, Date.valueOf(second));
//                    statement.setDate(3, Date.valueOf(first));
//                });
//    }

//    /**
//     * Searches for all free Rooms between two dates with greater or equal sleeps in the `ROOM` table
//     *
//     * @param first  date
//     * @param second date
//     * @param sleeps in the Room
//     * @return list of Room objects
//     * @throws DaoException if there was an error accessing the database
//     */
//    @Override
//    public List<Room> findAllFreeRoomsBetweenTwoDatesWithGreaterOrEqualSleeps(LocalDate first, LocalDate second, int sleeps) throws DaoException {
//        return getResultSet(ROOM_FIND_ALL_FREE_ROOMS_BETWEEN_TWO_DATES_WITH_GREATER_OR_EQUAL_SLEEPS,
//                MESSAGE_ROOM_FIND_ALL_FREE_ROOMS_BETWEEN_TWO_DATES_WITH_GREATER_OR_EQUAL_SLEEPS, statement -> {
//                    statement.setInt(1, sleeps);
//                    statement.setDate(2, Date.valueOf(second));
//                    statement.setDate(3, Date.valueOf(first));
//                });
//    }
}
