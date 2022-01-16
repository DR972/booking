package by.rozmysl.booking.model.service;

import by.rozmysl.booking.exception.ServiceException;
import by.rozmysl.booking.model.entity.hotel.Room;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * Provides logic for working with data sent to the `Room` table DAO.
 */
public interface RoomService {

    /**
     * Provides logic for searching for a Room object by id
     *
     * @param roomNumber id of the Room object
     * @return Room object
     * @throws ServiceException if the operation failed
     */
    Room findById(Integer roomNumber) throws ServiceException;

    /**
     * Provides logic for searching for all Room objects.
     *
     * @param pageNumber number of the page to return
     * @param rows       number of rows per page
     * @return list of Room objects
     * @throws ServiceException if the operation failed
     */
    List<Room> findAll(int pageNumber, int rows) throws ServiceException;

    /**
     * Provides logic for saving the Room in the `Room` table
     *
     * @param room Room
     * @throws ServiceException if the operation failed
     */
    void save(Room room) throws ServiceException;

    /**
     * Provides logic for deleting the Room in the `Room` table
     *
     * @param roomNumber id of the Room object
     * @throws ServiceException if the operation failed
     */
    void delete(Integer roomNumber) throws ServiceException;

    /**
     * Provides logic for updating the price of the Room.
     *
     * @param roomNumber id of the Room
     * @param price      new price
     * @throws ServiceException if the operation failed
     */
    void updatePrice(Room roomNumber, BigDecimal price) throws ServiceException;

    /**
     * Provides logic for updating parameters of the Room.
     *
     * @param roomNumber id of the Room
     * @param param      Room parameters
     * @throws ServiceException if the operation failed
     */
    void updateParameters(int roomNumber, Room param) throws ServiceException;

    /**
     * Provides logic for searching for all types Rooms.
     *
     * @return list of Room objects
     * @throws ServiceException if the operation failed
     */
    List<Room> findAllTypesRooms() throws ServiceException;

    /**
     * Provides logic for searching for all Rooms by types and sleeps.
     *
     * @return list of Room objects
     * @throws ServiceException if the operation failed
     */
    List<Room> findAllRoomsByTypesAndSleeps() throws ServiceException;

    /**
     * Provides logic for searching for a Room by type and sleeping.
     *
     * @param type   Room type
     * @param sleeps Room sleeps
     * @return Room object
     * @throws ServiceException if the operation failed
     */
    Room findRoomByTypeAndSleeping(String type, int sleeps) throws ServiceException;

    /**
     * Provides logic for searching for all free Rooms between two dates.
     *
     * @param first  date
     * @param second date
     * @return list of Room objects
     * @throws ServiceException if the operation failed
     */
    List<Room> findAllFreeRoomsBetweenTwoDates(LocalDate first, LocalDate second) throws ServiceException;

    /**
     * Provides logic for searching for all types free Rooms between two dates.
     *
     * @param first  date
     * @param second date
     * @return list of Room objects
     * @throws ServiceException if the operation failed
     */
    List<Room> findAllTypesFreeRoomsBetweenTwoDates(LocalDate first, LocalDate second) throws ServiceException;

    /**
     * Provides logic for searching for all types free Rooms between two dates by types and sleeps.
     *
     * @param first  date
     * @param second date
     * @param type   Room type
     * @param sleeps Room sleeps
     * @return list of Room objects
     * @throws ServiceException if the operation failed
     */
    List<Room> findAllFreeRoomsBetweenTwoDatesByTypesAndSleeps(LocalDate first, LocalDate second, String type, int sleeps) throws ServiceException;

    /**
     * Provides logic for searching for all types free Rooms between two dates with greater or equals sleeps.
     *
     * @param first  date
     * @param second date
     * @param sleeps Room sleeps
     * @return list of Room objects
     * @throws ServiceException if the operation failed
     */
    List<Room> findAllTypesFreeRoomsBetweenTwoDatesWithGreaterOrEqualSleeps(LocalDate first, LocalDate second, int sleeps) throws ServiceException;

    /**
     * Provides logic for searching for all free Rooms between two dates with greater or equals sleeps.
     *
     * @param first  date
     * @param second date
     * @param sleeps Room sleeps
     * @return list of Room objects
     * @throws ServiceException if the operation failed
     */
    List<Room> findAllFreeRoomsBetweenTwoDatesWithGreaterOrEqualSleeps(LocalDate first, LocalDate second, int sleeps) throws ServiceException;
}
