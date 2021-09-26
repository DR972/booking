package by.rozmysl.bookingServlet.dao.hotel;

import by.rozmysl.bookingServlet.dao.Dao;
import by.rozmysl.bookingServlet.entity.hotel.Room;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

/**
 * Provides the base model DAO interface for `ROOM` table.
 */
public interface RoomDao extends Dao<Room, Integer> {

    /**
     * Searches for all Room types in the `ROOM` table
     *
     * @return list of Room objects
     * @throws SQLException if there was an error accessing the database
     */
    List<Room> findAllTypesRooms() throws SQLException;

    /**
     * Searches for all Rooms by type and sleeps in the `ROOM` table
     *
     * @return list of Room objects
     * @throws SQLException if there was an error accessing the database
     */
    List<Room> findAllRoomsByTypesAndSleeps() throws SQLException;

    /**
     * Searches for the Room by type and sleeps in the `ROOM` table
     *
     * @param type   of the Room
     * @param sleeps in the Room
     * @return Room object
     * @throws SQLException if there was an error accessing the database
     */
    Room findRoomByTypeAndSleeping(String type, int sleeps) throws SQLException;

    /**
     * Searches for all free Rooms between two dates in the `ROOM` table
     *
     * @param first  date
     * @param second date
     * @return list of Room objects
     * @throws SQLException if there was an error accessing the database
     */
    List<Room> findAllFreeRoomsBetweenTwoDates(LocalDate first, LocalDate second) throws SQLException;

    /**
     * Searches for all free Room types between two dates in the `ROOM` table
     *
     * @param first  date
     * @param second date
     * @return list of Room objects
     * @throws SQLException if there was an error accessing the database
     */
    List<Room> findAllTypesFreeRoomsBetweenTwoDates(LocalDate first, LocalDate second) throws SQLException;

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
    List<Room> findAllFreeRoomsBetweenTwoDatesByTypesAndSleeps(LocalDate first, LocalDate second, String type, int sleeps)
            throws SQLException;

    /**
     * Searches for all free Room types between two dates with greater or equal sleeps in the `ROOM` table
     *
     * @param first  date
     * @param second date
     * @param sleeps in the Room
     * @return list of Room objects
     * @throws SQLException if there was an error accessing the database
     */
    List<Room> findAllTypesFreeRoomsBetweenTwoDatesWithGreaterOrEqualSleeps(LocalDate first, LocalDate second, int sleeps)
            throws SQLException;

    /**
     * Searches for all free Rooms between two dates with greater or equal sleeps in the `ROOM` table
     *
     * @param first  date
     * @param second date
     * @param sleeps in the Room
     * @return list of Room objects
     * @throws SQLException if there was an error accessing the database
     */
    List<Room> findAllFreeRoomsBetweenTwoDatesWithGreaterOrEqualSleeps(LocalDate first, LocalDate second, int sleeps)
            throws SQLException;

    /**
     * Changes the price of the Room in the `ROOM` table
     *
     * @param room  id of the Room object
     * @param price new price
     * @throws SQLException if there was an error accessing the database
     */
    void updatePrice(Room room, BigDecimal price) throws SQLException;

    /**
     * Changes the parameters of the Room in the `ROOM` table
     *
     * @param roomNumber id of the Room object
     * @param param      Room object
     * @throws SQLException if there was an error accessing the database
     */
    void updateParameters(int roomNumber, Room param) throws SQLException;
}
