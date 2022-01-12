package by.rozmysl.bookingServlet.model.dao;

import by.rozmysl.bookingServlet.exception.DaoException;
import by.rozmysl.bookingServlet.model.entity.hotel.Room;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * Provides the base model DAO interface for `ROOM` table.
 */
public interface RoomDao extends Dao<Room, Integer> {

//    List<Room> findListEntities(String sql, String message, Object ... params) throws DaoException;

//    /**
//     * Searches for all Room types in the `ROOM` table
//     *
//     * @return list of Room objects
//     * @throws DaoException if there was an error accessing the database
//     */
//    List<Room> findAllTypesRooms() throws DaoException;
//
//    /**
//     * Searches for all Rooms by type and sleeps in the `ROOM` table
//     *
//     * @return list of Room objects
//     * @throws DaoException if there was an error accessing the database
//     */
//    List<Room> findAllRoomsByTypesAndSleeps() throws DaoException;
//
//    /**
//     * Searches for the Room by type and sleeps in the `ROOM` table
//     *
//     * @param type   of the Room
//     * @param sleeps in the Room
//     * @return Room object
//     * @throws DaoException if there was an error accessing the database
//     */
//    Room findRoomByTypeAndSleeping(String type, int sleeps) throws DaoException;

//    /**
//     * Searches for all free Rooms between two dates in the `ROOM` table
//     *
//     * @param first  date
//     * @param second date
//     * @return list of Room objects
//     * @throws DaoException if there was an error accessing the database
//     */
//    List<Room> findAllFreeRoomsBetweenTwoDates(LocalDate first, LocalDate second) throws DaoException;
//
//    /**
//     * Searches for all free Room types between two dates in the `ROOM` table
//     *
//     * @param first  date
//     * @param second date
//     * @return list of Room objects
//     * @throws DaoException if there was an error accessing the database
//     */
//    List<Room> findAllTypesFreeRoomsBetweenTwoDates(LocalDate first, LocalDate second) throws DaoException;
//
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
//    List<Room> findAllFreeRoomsBetweenTwoDatesByTypesAndSleeps(LocalDate first, LocalDate second, String type, int sleeps)
//            throws DaoException;
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
//    List<Room> findAllTypesFreeRoomsBetweenTwoDatesWithGreaterOrEqualSleeps(LocalDate first, LocalDate second, int sleeps)
//            throws DaoException;
//
//    /**
//     * Searches for all free Rooms between two dates with greater or equal sleeps in the `ROOM` table
//     *
//     * @param first  date
//     * @param second date
//     * @param sleeps in the Room
//     * @return list of Room objects
//     * @throws DaoException if there was an error accessing the database
//     */
//    List<Room> findAllFreeRoomsBetweenTwoDatesWithGreaterOrEqualSleeps(LocalDate first, LocalDate second, int sleeps)
//            throws DaoException;
//
//    /**
//     * Changes the price of the Room in the `ROOM` table
//     *
//     * @param room  id of the Room object
//     * @param price new price
//     * @throws DaoException if there was an error accessing the database
//     */
//    void updatePrice(Room room, BigDecimal price) throws DaoException;
//
//    /**
//     * Changes the parameters of the Room in the `ROOM` table
//     *
//     * @param roomNumber id of the Room object
//     * @param param      Room object
//     * @throws DaoException if there was an error accessing the database
//     */
//    void updateParameters(int roomNumber, Room param) throws DaoException;
}
