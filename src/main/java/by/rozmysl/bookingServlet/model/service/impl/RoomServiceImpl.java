package by.rozmysl.bookingServlet.model.service.impl;

import by.rozmysl.bookingServlet.exception.DaoException;
import by.rozmysl.bookingServlet.exception.ServiceException;
import by.rozmysl.bookingServlet.model.dao.DaoFactory;
import by.rozmysl.bookingServlet.model.dao.RoomDao;
import by.rozmysl.bookingServlet.model.entity.hotel.Room;
import by.rozmysl.bookingServlet.model.service.RoomService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static by.rozmysl.bookingServlet.model.util.LoggerMessageError.*;
import static by.rozmysl.bookingServlet.model.util.SqlQuery.*;

/**
 * Provides logic for working with data sent to the `Room` table DAO.
 */
public class RoomServiceImpl implements RoomService {
    private static final Logger LOGGER = LoggerFactory.getLogger(RoomServiceImpl.class);
    private final RoomDao roomDao;

    /**
     * The constructor creates a new object RoomServiceImpl without property.
     */
    public RoomServiceImpl() {
        roomDao = DaoFactory.getInstance().getRoomDao();
    }

    /**
     * Provides logic for searching for a Room object by id
     *
     * @param roomNumber id of the Room object
     * @return Room object
     * @throws ServiceException if the operation failed
     */
    @Override
    public Room findById(Integer roomNumber) throws ServiceException {
        try {
            return roomDao.findEntity(ROOM_FIND_BY_ID, MESSAGE_ROOM_FIND_BY_ID, roomNumber);
        } catch (DaoException e) {
            LOGGER.error(MESSAGE_ROOM_FIND_BY_ID, e);
            throw new ServiceException(MESSAGE_ROOM_FIND_BY_ID, e);
        }
    }

    /**
     * Provides logic for searching for all Room objects.
     *
     * @param pageNumber number of the page to return
     * @param rows       number of rows per page
     * @return list of Room objects
     * @throws ServiceException if the operation failed
     */
    @Override
    public List<Room> findAll(int pageNumber, int rows) throws ServiceException {
        try {
            return roomDao.findListEntities(ROOM_FIND_ALL, MESSAGE_ROOM_FIND_ALL, rows, pageNumber, rows);
        } catch (DaoException e) {
            LOGGER.error(MESSAGE_ROOM_FIND_ALL, e);
            throw new ServiceException(MESSAGE_ROOM_FIND_ALL, e);
        }
    }

    /**
     * Provides logic for saving the Room in the `Room` table
     *
     * @param room Room
     * @throws ServiceException if the operation failed
     */
    @Override
    public void save(Room room) throws ServiceException {
        try {
            roomDao.updateEntity(ROOM_SAVE, MESSAGE_ROOM_SAVE,
                    room.getId(),
                    room.getType(),
                    room.getSleeps(),
                    room.getPrice());
        } catch (DaoException e) {
            LOGGER.error(MESSAGE_ROOM_SAVE, e);
            throw new ServiceException(MESSAGE_ROOM_SAVE, e);
        }
    }

    /**
     * Provides logic for deleting the Room in the `Room` table
     *
     * @param roomNumber id of the Room object
     * @throws ServiceException if the operation failed
     */
    @Override
    public void delete(Integer roomNumber) throws ServiceException {
        try {
            roomDao.updateEntity(ROOM_DELETE, MESSAGE_ROOM_DELETE, roomNumber);
        } catch (DaoException e) {
            LOGGER.error(MESSAGE_ROOM_DELETE, e);
            throw new ServiceException(MESSAGE_ROOM_DELETE, e);
        }
    }

    /**
     * Provides logic for updating the price of the Room.
     *
     * @param roomNumber id of the Room
     * @param price      new price
     * @throws ServiceException if the operation failed
     */
    @Override
    public void updatePrice(Room roomNumber, BigDecimal price) throws ServiceException {
        try {
            roomDao.updateEntity(ROOM_UPDATE_PRICE, MESSAGE_ROOM_UPDATE_PRICE,
                    price,
                    roomNumber.getType(),
                    roomNumber.getSleeps());
        } catch (DaoException e) {
            LOGGER.error(MESSAGE_ROOM_UPDATE_PRICE, e);
            throw new ServiceException(MESSAGE_ROOM_UPDATE_PRICE, e);
        }
    }

    /**
     * Provides logic for updating parameters of the Room.
     *
     * @param roomNumber id of the Room
     * @param param      Room parameters
     * @throws ServiceException if the operation failed
     */
    @Override
    public void updateParameters(int roomNumber, Room param) throws ServiceException {
        try {
            roomDao.updateEntity(ROOM_UPDATE_PARAMETERS, MESSAGE_ROOM_UPDATE_PARAMETERS,
                    param.getType(),
                    param.getSleeps(),
                    param.getPrice(),
                    roomNumber);
        } catch (DaoException e) {
            LOGGER.error(MESSAGE_ROOM_UPDATE_PARAMETERS, e);
            throw new ServiceException(MESSAGE_ROOM_UPDATE_PARAMETERS, e);
        }
    }

    /**
     * Provides logic for searching for all types Rooms.
     *
     * @return list of Room objects
     * @throws ServiceException if the operation failed
     */
    @Override
    public List<Room> findAllTypesRooms() throws ServiceException {
        try {
            return roomDao.findListEntities(ROOM_FIND_ALL_TYPES_ROOMS, MESSAGE_ROOM_FIND_ALL_TYPES_ROOMS);
        } catch (DaoException e) {
            LOGGER.error(MESSAGE_ROOM_FIND_ALL_TYPES_ROOMS, e);
            throw new ServiceException(MESSAGE_ROOM_FIND_ALL_TYPES_ROOMS, e);
        }
    }

    /**
     * Provides logic for searching for all Rooms by types and sleeps.
     *
     * @return list of Room objects
     * @throws ServiceException if the operation failed
     */
    @Override
    public List<Room> findAllRoomsByTypesAndSleeps() throws ServiceException {
        try {
            return roomDao.findListEntities(ROOM_FIND_ALL_ROOMS_BY_TYPES_AND_SLEEPS, MESSAGE_ROOM_FIND_ALL_ROOMS_BY_TYPES_AND_SLEEPS);
        } catch (DaoException e) {
            LOGGER.error(MESSAGE_ROOM_FIND_ALL_ROOMS_BY_TYPES_AND_SLEEPS, e);
            throw new ServiceException(MESSAGE_ROOM_FIND_ALL_ROOMS_BY_TYPES_AND_SLEEPS, e);
        }
    }

    /**
     * Provides logic for searching for a Room by type and sleeping.
     *
     * @param type   Room type
     * @param sleeps Room sleeps
     * @return Room object
     * @throws ServiceException if the operation failed
     */
    @Override
    public Room findRoomByTypeAndSleeping(String type, int sleeps) throws ServiceException {
        try {
            return roomDao.findEntity(ROOM_FIND_ROOM_BY_TYPE_AND_SLEEPING, MESSAGE_ROOM_FIND_ROOM_BY_TYPE_AND_SLEEPING, type, sleeps);
        } catch (DaoException e) {
            LOGGER.error(MESSAGE_ROOM_FIND_ROOM_BY_TYPE_AND_SLEEPING, e);
            throw new ServiceException(MESSAGE_ROOM_FIND_ROOM_BY_TYPE_AND_SLEEPING, e);
        }
    }

    /**
     * Provides logic for searching for all free Rooms between two dates.
     *
     * @param first  date
     * @param second date
     * @return list of Room objects
     * @throws ServiceException if the operation failed
     */
    @Override
    public List<Room> findAllFreeRoomsBetweenTwoDates(LocalDate first, LocalDate second) throws ServiceException {
        try {
            return roomDao.findListEntities(ROOM_FIND_ALL_FREE_ROOMS_BETWEEN_TWO_DATES, MESSAGE_ROOM_FIND_ALL_FREE_ROOMS_BETWEEN_TWO_DATES, second, first);
        } catch (DaoException e) {
            LOGGER.error(MESSAGE_ROOM_FIND_ALL_FREE_ROOMS_BETWEEN_TWO_DATES, e);
            throw new ServiceException(MESSAGE_ROOM_FIND_ALL_FREE_ROOMS_BETWEEN_TWO_DATES, e);
        }
    }

    /**
     * Provides logic for searching for all types free Rooms between two dates.
     *
     * @param first  date
     * @param second date
     * @return list of Room objects
     * @throws ServiceException if the operation failed
     */
    @Override
    public List<Room> findAllTypesFreeRoomsBetweenTwoDates(LocalDate first, LocalDate second) throws ServiceException {
        try {
            return roomDao.findListEntities(ROOM_FIND_ALL_TYPES_FREE_ROOMS_BETWEEN_TWO_DATES,
                    MESSAGE_ROOM_FIND_ALL_TYPES_FREE_ROOMS_BETWEEN_TWO_DATES, second, first);
        } catch (DaoException e) {
            LOGGER.error(MESSAGE_ROOM_FIND_ALL_TYPES_FREE_ROOMS_BETWEEN_TWO_DATES, e);
            throw new ServiceException(MESSAGE_ROOM_FIND_ALL_TYPES_FREE_ROOMS_BETWEEN_TWO_DATES, e);
        }
    }

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
    @Override
    public List<Room> findAllFreeRoomsBetweenTwoDatesByTypesAndSleeps(LocalDate first, LocalDate second, String type, int sleeps) throws ServiceException {
        try {
            return roomDao.findListEntities(ROOM_FIND_ALL_FREE_ROOMS_BETWEEN_TWO_DATES_BY_TYPES_AND_SLEEPS,
                    MESSAGE_ROOM_FIND_ALL_FREE_ROOMS_BETWEEN_TWO_DATES_BY_TYPES_AND_SLEEPS, type, sleeps, second, first);
        } catch (DaoException e) {
            LOGGER.error(MESSAGE_ROOM_FIND_ALL_FREE_ROOMS_BETWEEN_TWO_DATES_BY_TYPES_AND_SLEEPS, e);
            throw new ServiceException(MESSAGE_ROOM_FIND_ALL_FREE_ROOMS_BETWEEN_TWO_DATES_BY_TYPES_AND_SLEEPS, e);
        }
    }

    /**
     * Provides logic for searching for all types free Rooms between two dates with greater or equals sleeps.
     *
     * @param first  date
     * @param second date
     * @param sleeps Room sleeps
     * @return list of Room objects
     * @throws ServiceException if the operation failed
     */
    @Override
    public List<Room> findAllTypesFreeRoomsBetweenTwoDatesWithGreaterOrEqualSleeps(LocalDate first, LocalDate second, int sleeps) throws ServiceException {
        try {
            return roomDao.findListEntities(ROOM_FIND_ALL_TYPES_FREE_ROOMS_BETWEEN_TWO_DATES_WITH_GREATER_OR_EQUAL_SLEEPS,
                    MESSAGE_ROOM_FIND_ALL_TYPES_FREE_ROOMS_BETWEEN_TWO_DATES_WITH_GREATER_OR_EQUAL_SLEEPS, sleeps, second, first);
        } catch (DaoException e) {
            LOGGER.error(MESSAGE_ROOM_FIND_ALL, e);
            throw new ServiceException(MESSAGE_ROOM_FIND_ALL, e);
        }
    }

    /**
     * Provides logic for searching for all free Rooms between two dates with greater or equals sleeps.
     *
     * @param first  date
     * @param second date
     * @param sleeps Room sleeps
     * @return list of Room objects
     * @throws ServiceException if the operation failed
     */
    @Override
    public List<Room> findAllFreeRoomsBetweenTwoDatesWithGreaterOrEqualSleeps(LocalDate first, LocalDate second, int sleeps) throws ServiceException {
        try {
            return roomDao.findListEntities(ROOM_FIND_ALL_FREE_ROOMS_BETWEEN_TWO_DATES_WITH_GREATER_OR_EQUAL_SLEEPS,
                    MESSAGE_ROOM_FIND_ALL_FREE_ROOMS_BETWEEN_TWO_DATES_WITH_GREATER_OR_EQUAL_SLEEPS, sleeps, second, first);
        } catch (DaoException e) {
            LOGGER.error(MESSAGE_ROOM_FIND_ALL_FREE_ROOMS_BETWEEN_TWO_DATES_WITH_GREATER_OR_EQUAL_SLEEPS, e);
            throw new ServiceException(MESSAGE_ROOM_FIND_ALL_FREE_ROOMS_BETWEEN_TWO_DATES_WITH_GREATER_OR_EQUAL_SLEEPS, e);
        }
    }
}