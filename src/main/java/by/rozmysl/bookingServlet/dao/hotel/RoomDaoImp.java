package by.rozmysl.bookingServlet.dao.hotel;

import by.rozmysl.bookingServlet.dao.TableConstant;
import by.rozmysl.bookingServlet.db.ConnectionSource;
import by.rozmysl.bookingServlet.entity.hotel.Room;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Provides the base model implementation for `ROOM` table DAO with the <b>ConnectionSource</b> properties.
 */
public class RoomDaoImp implements RoomDao {
    private final ConnectionSource con;

    /**
     * The constructor creates a new object RoomDaoImp with the <b>con</b> property
     */
    public RoomDaoImp(ConnectionSource con) {
        this.con = con;
    }

    /**
     * Searches for the Room in the `ROOM` table by id
     *
     * @param roomNumber id of the Room
     * @return Room object
     * @throws SQLException if there was an error accessing the database
     */
    @Override
    public Room findById(Integer roomNumber) throws SQLException {
//        List<Room> rooms = getResultSet("select * from ROOM where ROOMNUMBER = " + roomNumber);
        List<Room> rooms = getResultSet(TableConstant.ROOM_QUERY_FIND_BY_ID.replace("?roomNumber", String.valueOf(roomNumber)));
        return rooms.size() != 0 ? rooms.get(0) : null;
    }

    /**
     * Searches for all Rooms in the `ROOM` table
     *
     * @param page number of the page to return
     * @param rows number of rows per page
     * @return list of Room objects
     * @throws SQLException if there was an error accessing the database
     */
    @Override
    public List<Room> findAll(int page, int rows) throws SQLException {
//        return getResultSet("select * from ROOM");
        return getResultSet(TableConstant.ROOM_QUERY_FIND_ALL);
    }

    /**
     * Saves the Room in the `ROOM` table
     *
     * @param room Room
     * @return Room object
     * @throws SQLException if there was an error accessing the database
     */
    @Override
    public Room save(Room room) throws SQLException {
//        con.update("insert into ROOM(ROOMNUMBER, TYPE, SLEEPS, PRICE) VALUES(" + room.getRoomNumber() + ",'" +
//                room.getType() + "'," + room.getSleeps() + "," + room.getPrice() + ")");
        con.update(TableConstant.ROOM_QUERY_SAVE.replace("?, ?, ?, ?", room.getRoomNumber() + ",'" +
                room.getType() + "'," + room.getSleeps() + "," + room.getPrice()));
        return room;
    }

    /**
     * Deletes the Room in the `ROOM` table
     *
     * @param roomNumber id of the Room
     * @throws SQLException if there was an error accessing the database
     */
    @Override
    public void delete(Integer roomNumber) throws SQLException {
//        con.update("delete from ROOM where ROOMNUMBER = " + roomNumber);
        con.update(TableConstant.ROOM_QUERY_DELETE.replace("?number", String.valueOf(roomNumber)));
    }

    /**
     * Changes the price of the Room in the `ROOM` table
     *
     * @param room  Room
     * @param price new price
     * @throws SQLException if there was an error accessing the database
     */
    @Override
    public void updatePrice(Room room, BigDecimal price) throws SQLException {
//        con.update("update ROOM set PRICE =" + price + " where TYPE ='" + room.getType() + "' and SLEEPS =" + room.getSleeps());
        con.update(TableConstant.ROOM_QUERY_UPDATE_PRICE.replace("?price", String.valueOf(price))
                .replace("?type", room.getType()).replace("?sleeps", String.valueOf(room.getSleeps())));
    }

    /**
     * Changes the parameters of the Room in the `ROOM` table
     *
     * @param roomNumber id of the Room
     * @param param      Room object
     * @throws SQLException if there was an error accessing the database
     */
    @Override
    public void updateParameters(int roomNumber, Room param) throws SQLException {
//        con.update("update ROOM set TYPE = '" + param.getType() + "', SLEEPS = " + param.getSleeps() +
//                ", PRICE = " + param.getPrice() + " where ROOMNUMBER = " + roomNumber);
        con.update(TableConstant.ROOM_QUERY_UPDATE_PARAMETERS.replace("?type", param.getType())
                .replace("?sleeps", String.valueOf(param.getSleeps())).replace("?price", String.valueOf(param.getPrice()))
                .replace("?roomNumber", String.valueOf(roomNumber)));

    }

    /**
     * Searches for all Room types in the `ROOM` table
     *
     * @return list of Room objects
     * @throws SQLException if there was an error accessing the database
     */
    @Override
    public List<Room> findAllTypesRooms() throws SQLException {
//        return getResultSet("select min(ROOMNUMBER) as ROOMNUMBER, min(SLEEPS) as SLEEPS, min(PRICE) as PRICE, TYPE " +
//                "from ROOM GROUP BY TYPE order by PRICE");
        return getResultSet(TableConstant.ROOM_QUERY_FIND_ALL_TYPES_ROOMS);
    }

    /**
     * Searches for all Rooms by type and sleeps in the `ROOM` table
     *
     * @return list of Room objects
     * @throws SQLException if there was an error accessing the database
     */
    @Override
    public List<Room> findAllRoomsByTypesAndSleeps() throws SQLException {
//        return getResultSet("select min(ROOMNUMBER) as ROOMNUMBER, TYPE, SLEEPS, PRICE from ROOM GROUP BY TYPE, SLEEPS order by PRICE");
        return getResultSet(TableConstant.ROOM_QUERY_FIND_ALL_ROOMS_BY_TYPES_AND_SLEEPS);
    }

    /**
     * Searches for the Room by type and sleeps in the `ROOM` table
     *
     * @param type   of the Room
     * @param sleeps in the Room
     * @return Room object
     * @throws SQLException if there was an error accessing the database
     */
    @Override
    public Room findRoomByTypeAndSleeping(String type, int sleeps) throws SQLException {
//        List<Room> rooms = getResultSet("select min(ROOMNUMBER) as ROOMNUMBER, TYPE, SLEEPS, PRICE from ROOM where " +
//                "TYPE = '" + type + "' and SLEEPS = " + sleeps + " GROUP BY TYPE, SLEEPS");
        List<Room> rooms = getResultSet(TableConstant.ROOM_QUERY_FIND_ROOM_BY_TYPE_AND_SLEEPING
                .replace("?type", type).replace("?sleeps", String.valueOf(sleeps)));
        return rooms.size() != 0 ? rooms.get(0) : null;
    }

    /**
     * Searches for all free Rooms between two dates in the `ROOM` table
     *
     * @param first  date
     * @param second date
     * @return list of Room objects
     * @throws SQLException if there was an error accessing the database
     */
    @Override
    public List<Room> findAllFreeRoomsBetweenTwoDates(LocalDate first, LocalDate second) throws SQLException {
//        return getResultSet("select * from ROOM where ROOMNUMBER not in (" +
//                "select ROOM from BOOKING where ARRIVAL < '" + second + "' and DEPARTURE > '" + first + "')");
        return getResultSet(TableConstant.ROOM_QUERY_FIND_ALL_FREE_ROOMS_BETWEEN_TWO_DATES
                .replace("?second", String.valueOf(second)).replace("?first", String.valueOf(first)));
    }

    /**
     * Searches for all free Room types between two dates in the `ROOM` table
     *
     * @param first  date
     * @param second date
     * @return list of Room objects
     * @throws SQLException if there was an error accessing the database
     */
    @Override
    public List<Room> findAllTypesFreeRoomsBetweenTwoDates(LocalDate first, LocalDate second) throws SQLException {
//        return getResultSet("select min(ROOMNUMBER) as ROOMNUMBER, min(SLEEPS) as SLEEPS, min(PRICE) as PRICE, TYPE " +
//                "from ROOM where ROOMNUMBER not in (select ROOM from BOOKING where ARRIVAL < '" + second + "' " +
//                "and DEPARTURE > '" + first + "') GROUP BY TYPE order by PRICE");
        return getResultSet(TableConstant.ROOM_QUERY_FIND_ALL_TYPES_FREE_ROOMS_BETWEEN_TWO_DATES
                .replace("?second", String.valueOf(second)).replace("?first", String.valueOf(first)));
    }

    /**
     * Searches for all free Rooms between two dates by type and sleeps in the `ROOM` table
     *
     * @param first  date
     * @param second date
     * @param type   of the Room
     * @param sleeps in the Room
     * @return list of Room objects
     * @throws SQLException if there was an error accessing the database
     */
    @Override
    public List<Room> findAllFreeRoomsBetweenTwoDatesByTypesAndSleeps(LocalDate first, LocalDate second, String type, int sleeps)
            throws SQLException {
//        return getResultSet("select * from ROOM where TYPE = '" + type + "' and SLEEPS = " + sleeps + " and ROOMNUMBER not in " +
//                "(select ROOM from BOOKING where ARRIVAL < '" + second + "' and DEPARTURE > '" + first + "') ");
        return getResultSet(TableConstant.ROOM_QUERY_FIND_ALL_FREE_ROOMS_BETWEEN_TWO_DATES_BY_TYPES_AND_SLEEPS
                .replace("?type", type).replace("?sleeps", String.valueOf(sleeps))
                .replace("?second", String.valueOf(second)).replace("?first", String.valueOf(first)));
    }

    /**
     * Searches for all free Room types between two dates with greater or equal sleeps in the `ROOM` table
     *
     * @param first  date
     * @param second date
     * @param sleeps in the Room
     * @return list of Room objects
     * @throws SQLException if there was an error accessing the database
     */
    @Override
    public List<Room> findAllTypesFreeRoomsBetweenTwoDatesWithGreaterOrEqualSleeps(LocalDate first, LocalDate second, int sleeps)
            throws SQLException {
//        return getResultSet("select min(ROOMNUMBER) as ROOMNUMBER, SLEEPS, PRICE, TYPE from ROOM where SLEEPS >= "
//                + sleeps + " and ROOMNUMBER not in (select ROOM from BOOKING where ARRIVAL < '" + second +
//                "' and DEPARTURE > '" + first + "') " + " GROUP BY TYPE, SLEEPS order by PRICE");
        return getResultSet(TableConstant.ROOM_QUERY_FIND_ALL_TYPES_FREE_ROOMS_BETWEEN_TWO_DATES_WITH_GREATER_OR_EQUAL_SLEEPS
                .replace("?sleeps", String.valueOf(sleeps))
                .replace("?second", String.valueOf(second)).replace("?first", String.valueOf(first)));
    }

    /**
     * Searches for all free Rooms between two dates with greater or equal sleeps in the `ROOM` table
     *
     * @param first  date
     * @param second date
     * @param sleeps in the Room
     * @return list of Room objects
     * @throws SQLException if there was an error accessing the database
     */
    @Override
    public List<Room> findAllFreeRoomsBetweenTwoDatesWithGreaterOrEqualSleeps(LocalDate first, LocalDate second, int sleeps)
            throws SQLException {
//        return getResultSet("select * from ROOM where SLEEPS >= " + sleeps + " and ROOMNUMBER not in (select ROOM " +
//                "from BOOKING where ARRIVAL < '" + second + "' and DEPARTURE > '" + first + "') order by ROOMNUMBER");
        return getResultSet(TableConstant.ROOM_QUERY_FIND_ALL_FREE_ROOMS_BETWEEN_TWO_DATES_WITH_GREATER_OR_EQUAL_SLEEPS
                .replace("?sleeps", String.valueOf(sleeps))
                .replace("?second", String.valueOf(second)).replace("?first", String.valueOf(first)));
    }

    /**
     * Get a list of query results from the 'ROOM' table
     *
     * @param sql the wording of the request to the database
     * @return list of Room objects
     * @throws SQLException if there was an error accessing the database
     */
    private List<Room> getResultSet(String sql) throws SQLException {
        return con.get(sql).stream().map(d -> new Room(Integer.parseInt(d.get("ROOMNUMBER")), d.get("TYPE"),
                        Integer.parseInt(d.get("SLEEPS")), new BigDecimal(d.get("PRICE"))))
                .distinct().collect(Collectors.toList());
    }
}
