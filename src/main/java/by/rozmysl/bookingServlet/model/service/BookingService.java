package by.rozmysl.bookingServlet.model.service;

import by.rozmysl.bookingServlet.exception.ServiceException;
import by.rozmysl.bookingServlet.model.entity.user.Booking;

import java.time.LocalDate;
import java.util.List;

public interface BookingService {
    Booking findById(Long number) throws ServiceException;
    List<Booking> findAll(int page, int rows) throws ServiceException;
    Booking save(Booking booking) throws ServiceException;
    public void delete(Long number) throws ServiceException;
    int countBookingsPages(int rows) throws ServiceException;
    String validateBooking(Booking booking);
    List<Booking> findAllBookingsBetweenTwoDates(LocalDate arrival, LocalDate departure) throws ServiceException;
    List<Booking> findAllBookingsByUser(String user) throws ServiceException;
    void changeRoom(long number, int roomNumber, ServiceFactory service) throws ServiceException;
    void changeBookingStatus(long number, String status, ServiceFactory service) throws ServiceException;
}
