package by.rozmysl.bookingServlet.model.service.impl;

import by.rozmysl.bookingServlet.exception.DaoException;
import by.rozmysl.bookingServlet.exception.ServiceException;
import by.rozmysl.bookingServlet.model.dao.DaoFactory;
import by.rozmysl.bookingServlet.model.dao.RoomDao;
import by.rozmysl.bookingServlet.model.db.ConnectionPool;
import by.rozmysl.bookingServlet.model.db.ProxyConnection;
import by.rozmysl.bookingServlet.model.entity.hotel.Room;
import by.rozmysl.bookingServlet.model.service.RoomService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static by.rozmysl.bookingServlet.model.LoggerMessage.*;
import static by.rozmysl.bookingServlet.model.dao.SqlQuery.*;

public class RoomServiceImpl implements RoomService {
    private static final Logger LOGGER = LoggerFactory.getLogger(RoomServiceImpl.class);
    private final RoomDao roomDao;

    public RoomServiceImpl() {
        roomDao = DaoFactory.getInstance().getRoomDao();
    }

    @Override
    public Room findById(Integer roomNumber) throws ServiceException {
        try {
            return roomDao.findEntity(ROOM_FIND_BY_ID, MESSAGE_ROOM_FIND_BY_ID, roomNumber);
//            return roomDao.findById(roomNumber);
        } catch (DaoException e) {
            LOGGER.error(MESSAGE_ROOM_FIND_BY_ID, e);
            throw new ServiceException(MESSAGE_ROOM_FIND_BY_ID, e);
        }
    }

    @Override
    public List<Room> findAll(int pageNumber, int rows) throws ServiceException {
        try {
            return roomDao.findListEntities(ROOM_FIND_ALL, MESSAGE_ROOM_FIND_ALL, rows, pageNumber, rows);
//            return roomDao.findAll(pageNumber, rows);
        } catch (DaoException e) {
            LOGGER.error(MESSAGE_ROOM_FIND_ALL, e);
            throw new ServiceException(MESSAGE_ROOM_FIND_ALL, e);
        }
    }

    @Override
    public void save(Room room) throws ServiceException {
        try (final ProxyConnection connection = ConnectionPool.getInstance().getConnectionFromPool()) {
            roomDao.setConnection(connection);
            roomDao.updateEntity(ROOM_SAVE, MESSAGE_ROOM_SAVE,
                    room.getId(),
                    room.getType(),
                    room.getSleeps(),
                    room.getPrice());
//            roomDao.save(room);
        } catch (DaoException e) {
            LOGGER.error(MESSAGE_ROOM_SAVE, e);
            throw new ServiceException(MESSAGE_ROOM_SAVE, e);
        }
    }

    @Override
    public void delete(Integer roomNumber) throws ServiceException {
        try (final ProxyConnection connection = ConnectionPool.getInstance().getConnectionFromPool()) {
            roomDao.setConnection(connection);
            roomDao.updateEntity(ROOM_DELETE, MESSAGE_ROOM_DELETE, roomNumber);
//            roomDao.delete(roomNumber);
        } catch (DaoException e) {
            LOGGER.error(MESSAGE_ROOM_DELETE, e);
            throw new ServiceException(MESSAGE_ROOM_DELETE, e);
        }
    }

    @Override
    public void updatePrice(Room room, BigDecimal price) throws ServiceException {
        try (final ProxyConnection connection = ConnectionPool.getInstance().getConnectionFromPool()) {
            roomDao.setConnection(connection);
            roomDao.updateEntity(ROOM_UPDATE_PRICE, MESSAGE_ROOM_UPDATE_PRICE,
                    price,
                    room.getType(),
                    room.getSleeps());
//            roomDao.updatePrice(room, price);
        } catch (DaoException e) {
            LOGGER.error(MESSAGE_ROOM_UPDATE_PRICE, e);
            throw new ServiceException(MESSAGE_ROOM_UPDATE_PRICE, e);
        }
    }

    @Override
    public void updateParameters(int roomNumber, Room param) throws ServiceException {
        try (final ProxyConnection connection = ConnectionPool.getInstance().getConnectionFromPool()) {
            roomDao.setConnection(connection);
            roomDao.updateEntity(ROOM_UPDATE_PARAMETERS, MESSAGE_ROOM_UPDATE_PARAMETERS,
                    param.getType(),
                    param.getSleeps(),
                    param.getPrice(),
                    roomNumber);
//            roomDao.updateParameters(roomNumber, param);
        } catch (DaoException e) {
            LOGGER.error(MESSAGE_ROOM_UPDATE_PARAMETERS, e);
            throw new ServiceException(MESSAGE_ROOM_UPDATE_PARAMETERS, e);
        }
    }

    @Override
    public List<Room> findAllTypesRooms() throws ServiceException {
        try {
            return roomDao.findListEntities(ROOM_FIND_ALL_TYPES_ROOMS, MESSAGE_ROOM_FIND_ALL_TYPES_ROOMS);
//            return roomDao.findAllTypesRooms();
        } catch (DaoException e) {
            LOGGER.error(MESSAGE_ROOM_FIND_ALL_TYPES_ROOMS, e);
            throw new ServiceException(MESSAGE_ROOM_FIND_ALL_TYPES_ROOMS, e);
        }
    }

    @Override
    public List<Room> findAllRoomsByTypesAndSleeps() throws ServiceException {
        try {
            return roomDao.findListEntities(ROOM_FIND_ALL_ROOMS_BY_TYPES_AND_SLEEPS, MESSAGE_ROOM_FIND_ALL_ROOMS_BY_TYPES_AND_SLEEPS);
//            oomDao.findAllRoomsByTypesAndSleeps();
        } catch (DaoException e) {
            LOGGER.error(MESSAGE_ROOM_FIND_ALL_ROOMS_BY_TYPES_AND_SLEEPS, e);
            throw new ServiceException(MESSAGE_ROOM_FIND_ALL_ROOMS_BY_TYPES_AND_SLEEPS, e);
        }
    }

    @Override
    public Room findRoomByTypeAndSleeping(String type, int sleeps) throws ServiceException {
        try {
            return roomDao.findEntity(ROOM_FIND_ROOM_BY_TYPE_AND_SLEEPING, MESSAGE_ROOM_FIND_ROOM_BY_TYPE_AND_SLEEPING, type, sleeps);
//            return roomDao.findRoomByTypeAndSleeping(type, sleeps);
        } catch (DaoException e) {
            LOGGER.error(MESSAGE_ROOM_FIND_ROOM_BY_TYPE_AND_SLEEPING, e);
            throw new ServiceException(MESSAGE_ROOM_FIND_ROOM_BY_TYPE_AND_SLEEPING, e);
        }
    }

    @Override
    public List<Room> findAllFreeRoomsBetweenTwoDates(LocalDate first, LocalDate second) throws ServiceException {
        try {
            return roomDao.findListEntities(ROOM_FIND_ALL_FREE_ROOMS_BETWEEN_TWO_DATES, MESSAGE_ROOM_FIND_ALL_FREE_ROOMS_BETWEEN_TWO_DATES, second, first);
//            return roomDao.findAllFreeRoomsBetweenTwoDates(first, second);
        } catch (DaoException e) {
            LOGGER.error(MESSAGE_ROOM_FIND_ALL_FREE_ROOMS_BETWEEN_TWO_DATES, e);
            throw new ServiceException(MESSAGE_ROOM_FIND_ALL_FREE_ROOMS_BETWEEN_TWO_DATES, e);
        }
    }

    @Override
    public List<Room> findAllTypesFreeRoomsBetweenTwoDates(LocalDate first, LocalDate second) throws ServiceException {
        try {
            return roomDao.findListEntities(ROOM_FIND_ALL_TYPES_FREE_ROOMS_BETWEEN_TWO_DATES,
                    MESSAGE_ROOM_FIND_ALL_TYPES_FREE_ROOMS_BETWEEN_TWO_DATES, second, first);
//            return roomDao.findAllTypesFreeRoomsBetweenTwoDates(first, second);
        } catch (DaoException e) {
            LOGGER.error(MESSAGE_ROOM_FIND_ALL_TYPES_FREE_ROOMS_BETWEEN_TWO_DATES, e);
            throw new ServiceException(MESSAGE_ROOM_FIND_ALL_TYPES_FREE_ROOMS_BETWEEN_TWO_DATES, e);
        }
    }

    @Override
    public List<Room> findAllFreeRoomsBetweenTwoDatesByTypesAndSleeps(LocalDate first, LocalDate second, String type, int sleeps) throws ServiceException {
        try {
            return roomDao.findListEntities(ROOM_FIND_ALL_FREE_ROOMS_BETWEEN_TWO_DATES_BY_TYPES_AND_SLEEPS,
                    MESSAGE_ROOM_FIND_ALL_FREE_ROOMS_BETWEEN_TWO_DATES_BY_TYPES_AND_SLEEPS, type, sleeps, second, first);
//            return roomDao.findAllFreeRoomsBetweenTwoDatesByTypesAndSleeps(first, second, type, sleeps);
        } catch (DaoException e) {
            LOGGER.error(MESSAGE_ROOM_FIND_ALL_FREE_ROOMS_BETWEEN_TWO_DATES_BY_TYPES_AND_SLEEPS, e);
            throw new ServiceException(MESSAGE_ROOM_FIND_ALL_FREE_ROOMS_BETWEEN_TWO_DATES_BY_TYPES_AND_SLEEPS, e);
        }
    }

    @Override
    public List<Room> findAllTypesFreeRoomsBetweenTwoDatesWithGreaterOrEqualSleeps(LocalDate first, LocalDate second, int sleeps) throws ServiceException {
        try {
            return roomDao.findListEntities(ROOM_FIND_ALL_TYPES_FREE_ROOMS_BETWEEN_TWO_DATES_WITH_GREATER_OR_EQUAL_SLEEPS,
                    MESSAGE_ROOM_FIND_ALL_TYPES_FREE_ROOMS_BETWEEN_TWO_DATES_WITH_GREATER_OR_EQUAL_SLEEPS, sleeps, second, first);
//            return roomDao.findAllTypesFreeRoomsBetweenTwoDatesWithGreaterOrEqualSleeps(first, second, sleeps);
        } catch (DaoException e) {
            LOGGER.error(MESSAGE_ROOM_FIND_ALL, e);
            throw new ServiceException(MESSAGE_ROOM_FIND_ALL, e);
        }
    }

    @Override
    public List<Room> findAllFreeRoomsBetweenTwoDatesWithGreaterOrEqualSleeps(LocalDate first, LocalDate second, int sleeps) throws ServiceException {
        try {
            return roomDao.findListEntities(ROOM_FIND_ALL_FREE_ROOMS_BETWEEN_TWO_DATES_WITH_GREATER_OR_EQUAL_SLEEPS,
                    MESSAGE_ROOM_FIND_ALL_FREE_ROOMS_BETWEEN_TWO_DATES_WITH_GREATER_OR_EQUAL_SLEEPS, sleeps, second, first);
//            return roomDao.findAllFreeRoomsBetweenTwoDatesWithGreaterOrEqualSleeps(first, second, sleeps);
        } catch (DaoException e) {
            LOGGER.error(MESSAGE_ROOM_FIND_ALL_FREE_ROOMS_BETWEEN_TWO_DATES_WITH_GREATER_OR_EQUAL_SLEEPS, e);
            throw new ServiceException(MESSAGE_ROOM_FIND_ALL_FREE_ROOMS_BETWEEN_TWO_DATES_WITH_GREATER_OR_EQUAL_SLEEPS, e);
        }
    }
}
