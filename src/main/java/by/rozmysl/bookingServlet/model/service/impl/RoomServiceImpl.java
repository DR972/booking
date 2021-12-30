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
import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;

public class RoomServiceImpl implements RoomService {
    private static final Logger LOGGER = LoggerFactory.getLogger(RoomServiceImpl.class);
    private final RoomDao roomDao;

    public RoomServiceImpl(Connection connection) {
        roomDao = DaoFactory.getInstance().roomDao(connection);
    }

    @Override
    public Room findById(Integer roomNumber) throws ServiceException {
        try {
            return roomDao.findById(roomNumber);
        } catch (DaoException e) {
            LOGGER.error("'Room findById' query has been failed", e);
            throw new ServiceException("'Room findById' query has been failed", e);
        }
    }

    @Override
    public List<Room> findAll(int page, int rows) throws ServiceException {
        try {
            return roomDao.findAll(page, rows);
        } catch (DaoException e) {
            LOGGER.error("'Room findAll' query has been failed", e);
            throw new ServiceException("'Room findAll' query has been failed", e);
        }
    }

    @Override
    public Room save(Room room) throws ServiceException {
        try {
            return roomDao.save(room);
        } catch (DaoException e) {
            LOGGER.error("'Room save' query has been failed", e);
            throw new ServiceException("'Room save' query has been failed", e);
        }
    }

    @Override
    public void delete(Integer roomNumber) throws ServiceException {
        try {
            roomDao.delete(roomNumber);
        } catch (DaoException e) {
            LOGGER.error("'Room delete' query has been failed", e);
            throw new ServiceException("'Room delete' query has been failed", e);
        }
    }

    @Override
    public void updatePrice(Room room, BigDecimal price) throws ServiceException {
        try {
            roomDao.updatePrice(room, price);
        } catch (DaoException e) {
            LOGGER.error("'Room updatePrice' query has been failed", e);
            throw new ServiceException("'Room updatePrice' query has been failed", e);
        }
    }

    @Override
    public void updateParameters(int roomNumber, Room param) throws ServiceException {
        try {
            roomDao.updateParameters(roomNumber, param);
        } catch (DaoException e) {
            LOGGER.error("'Room updateParameters' query has been failed", e);
            throw new ServiceException("'Room updateParameters' query has been failed", e);
        }
    }

    @Override
    public List<Room> findAllTypesRooms() throws ServiceException {
        try {
            return roomDao.findAllTypesRooms();
        } catch (DaoException e) {
            LOGGER.error("'findAllTypesRooms' query has been failed", e);
            throw new ServiceException("'findAllTypesRooms' query has been failed", e);
        }
    }

    @Override
    public List<Room> findAllRoomsByTypesAndSleeps() throws ServiceException {
        try {
            return roomDao.findAllRoomsByTypesAndSleeps();
        } catch (DaoException e) {
            LOGGER.error("'findAllRoomsByTypesAndSleeps' query has been failed", e);
            throw new ServiceException("'findAllRoomsByTypesAndSleeps' query has been failed", e);
        }
    }

    @Override
    public Room findRoomByTypeAndSleeping(String type, int sleeps) throws ServiceException {
        try {
            return roomDao.findRoomByTypeAndSleeping(type, sleeps);
        } catch (DaoException e) {
            LOGGER.error("'findRoomByTypeAndSleeping' query has been failed", e);
            throw new ServiceException("'findRoomByTypeAndSleeping' query has been failed", e);
        }
    }

    @Override
    public List<Room> findAllFreeRoomsBetweenTwoDates(LocalDate first, LocalDate second) throws ServiceException {
        try {
            return roomDao.findAllFreeRoomsBetweenTwoDates(first, second);
        } catch (DaoException e) {
            LOGGER.error("'findAllFreeRoomsBetweenTwoDates' query has been failed", e);
            throw new ServiceException("'findAllFreeRoomsBetweenTwoDates' query has been failed", e);
        }
    }

    @Override
    public List<Room> findAllTypesFreeRoomsBetweenTwoDates(LocalDate first, LocalDate second) throws ServiceException {
        try {
            return roomDao.findAllTypesFreeRoomsBetweenTwoDates(first, second);
        } catch (DaoException e) {
            LOGGER.error("'findAllTypesFreeRoomsBetweenTwoDates' query has been failed", e);
            throw new ServiceException("'findAllTypesFreeRoomsBetweenTwoDates' query has been failed", e);
        }
    }

    @Override
    public List<Room> findAllFreeRoomsBetweenTwoDatesByTypesAndSleeps(LocalDate first, LocalDate second, String type, int sleeps) throws ServiceException {
        try {
            return roomDao.findAllFreeRoomsBetweenTwoDatesByTypesAndSleeps(first, second, type, sleeps);
        } catch (DaoException e) {
            LOGGER.error("'findAllFreeRoomsBetweenTwoDatesByTypesAndSleeps' query has been failed", e);
            throw new ServiceException("'findAllFreeRoomsBetweenTwoDatesByTypesAndSleeps' query has been failed", e);
        }
    }

    @Override
    public List<Room> findAllTypesFreeRoomsBetweenTwoDatesWithGreaterOrEqualSleeps(LocalDate first, LocalDate second, int sleeps) throws ServiceException {
        try {
            return roomDao.findAllTypesFreeRoomsBetweenTwoDatesWithGreaterOrEqualSleeps(first, second, sleeps);
        } catch (DaoException e) {
            LOGGER.error("'findAllTypesFreeRoomsBetweenTwoDatesWithGreaterOrEqualSleeps' query has been failed", e);
            throw new ServiceException("'findAllTypesFreeRoomsBetweenTwoDatesWithGreaterOrEqualSleeps' query has been failed", e);
        }
    }

    @Override
    public List<Room> findAllFreeRoomsBetweenTwoDatesWithGreaterOrEqualSleeps(LocalDate first, LocalDate second, int sleeps) throws ServiceException {
        try {
            return roomDao.findAllFreeRoomsBetweenTwoDatesWithGreaterOrEqualSleeps(first, second, sleeps);
        } catch (DaoException e) {
            LOGGER.error("'findAllFreeRoomsBetweenTwoDatesWithGreaterOrEqualSleeps' query has been failed", e);
            throw new ServiceException("'findAllFreeRoomsBetweenTwoDatesWithGreaterOrEqualSleeps' query has been failed", e);
        }
    }
}
