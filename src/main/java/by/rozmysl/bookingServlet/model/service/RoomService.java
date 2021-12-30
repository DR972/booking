package by.rozmysl.bookingServlet.model.service;

import by.rozmysl.bookingServlet.exception.ServiceException;
import by.rozmysl.bookingServlet.model.entity.hotel.Room;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface RoomService {
    Room findById(Integer roomNumber) throws ServiceException;
    List<Room> findAll(int page, int rows) throws ServiceException;
    Room save(Room room) throws ServiceException;
    void delete(Integer roomNumber) throws ServiceException;
    void updatePrice(Room room, BigDecimal price) throws ServiceException;
    void updateParameters(int roomNumber, Room param) throws ServiceException;
    List<Room> findAllTypesRooms() throws ServiceException;
    List<Room> findAllRoomsByTypesAndSleeps() throws ServiceException;
    Room findRoomByTypeAndSleeping(String type, int sleeps) throws ServiceException;
    List<Room> findAllFreeRoomsBetweenTwoDates(LocalDate first, LocalDate second) throws ServiceException;
    List<Room> findAllTypesFreeRoomsBetweenTwoDates(LocalDate first, LocalDate second) throws ServiceException;
    List<Room> findAllFreeRoomsBetweenTwoDatesByTypesAndSleeps(LocalDate first, LocalDate second, String type, int sleeps) throws ServiceException;
    List<Room> findAllTypesFreeRoomsBetweenTwoDatesWithGreaterOrEqualSleeps(LocalDate first, LocalDate second, int sleeps) throws ServiceException;
    List<Room> findAllFreeRoomsBetweenTwoDatesWithGreaterOrEqualSleeps(LocalDate first, LocalDate second, int sleeps) throws ServiceException;
}
