package by.rozmysl.bookingServlet.controller.command.impl.admin;

import by.rozmysl.bookingServlet.controller.command.*;
import by.rozmysl.bookingServlet.model.db.ConnectionPool;
import by.rozmysl.bookingServlet.exception.ServiceException;
import by.rozmysl.bookingServlet.model.entity.hotel.Room;
import by.rozmysl.bookingServlet.model.entity.hotel.StatusReservation;
import by.rozmysl.bookingServlet.model.entity.user.Booking;
import by.rozmysl.bookingServlet.model.service.BookingService;
import by.rozmysl.bookingServlet.model.service.RoomService;
import by.rozmysl.bookingServlet.model.service.ServiceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static by.rozmysl.bookingServlet.controller.command.RequestAttribute.ALL_BOOKINGS;
import static by.rozmysl.bookingServlet.controller.command.RequestAttribute.AVAILABLE_ROOMS;
import static by.rozmysl.bookingServlet.controller.command.RequestParameter.*;

/**
 * Provides service to initialize actions on the GetAllBookingsCommand.
 */
public class GetAllBookingsCommand implements Command {
    private static final Logger LOGGER = LoggerFactory.getLogger(GetAllBookingsCommand.class);

    /**
     * Executes actions on request content.
     *
     * @param req request content
     * @return page to go
     * @throws ServiceException       if there was an error accessing the service
     */
    @Override
    public PageGuide execute(HttpServletRequest req) throws ServiceException {
        ServiceFactory service = ServiceFactory.getInstance();
        final Connection connection = ConnectionPool.getInstance().getConnectionFromPool();
        try {
            BookingService bookingService = service.bookingService(connection);
            RoomService roomService = service.roomService(connection);
            if (req.getParameter(DELETE) != null && req.getParameter(DELETE).equals(DELETE)) {
                bookingService.delete(Long.parseLong(req.getParameter(BOOKING_NUMBER)));
                LOGGER.info("Booking # " + req.getParameter(BOOKING_NUMBER) + " was deleted by admin " + req.getUserPrincipal().getName());
            }
            if (req.getParameter(CHANGE_ROOM) != null && req.getParameter(CHANGE_ROOM).equals(CHANGE_ROOM)) {
                System.out.println(req.getParameter(BOOKING_NUMBER));
                System.out.println(req.getParameter(ROOM_NUMBER));
                bookingService.changeRoom(Long.parseLong(req.getParameter(BOOKING_NUMBER)), Integer.parseInt(req.getParameter(ROOM_NUMBER)), service);
                LOGGER.info("In booking # " + req.getParameter(BOOKING_NUMBER) + ", the room number was changed to " +
                        req.getParameter(ROOM_NUMBER) + " by the admin " + req.getUserPrincipal().getName());
            }
            if (req.getParameter(CHANGE_STATUS) != null && req.getParameter(CHANGE_STATUS).equals(CHANGE_STATUS)) {
                bookingService.changeBookingStatus(Long.parseLong(req.getParameter(BOOKING_NUMBER)), req.getParameter(RequestParameter.STATUS), service);
                LOGGER.info("In booking # " + req.getParameter(BOOKING_NUMBER) + ", the status was changed to '" +
                        req.getParameter(RequestParameter.STATUS) + "'  by the admin " + req.getUserPrincipal().getName());
            }
            int page = 0;
            int rows = 10;
            if (req.getParameter(RequestParameter.ROWS) != null) {
                rows = Integer.parseInt(req.getParameter(RequestParameter.ROWS));
            }
            if (req.getParameter(PAGE) != null) {
                page = Integer.parseInt(req.getParameter(RequestParameter.PAGE));
            }
            req.setAttribute(RequestAttribute.ROWS, rows);
            req.setAttribute(RequestAttribute.PAGE, page);
            req.setAttribute(RequestAttribute.COUNT_PAGES, bookingService.countBookingsPages(rows));
            List<Booking> allBookings = bookingService.findAll(page, rows);
            Map<Long, List<Room>> availableRooms = new HashMap<>();
            for (Booking booking : allBookings) {
                availableRooms.put(booking.getNumber(), roomService.findAllFreeRoomsBetweenTwoDatesWithGreaterOrEqualSleeps
                        (booking.getArrival(), booking.getDeparture(), booking.getPersons()));
            }
            req.setAttribute(AVAILABLE_ROOMS, availableRooms);
            req.setAttribute(ALL_BOOKINGS, allBookings);
            req.setAttribute(RequestAttribute.STATUS, StatusReservation.values());
        } finally {
            ConnectionPool.getInstance().returnConnectionToPool(connection);
        }
        return new PageGuide(PageAddress.ALL_BOOKINGS, TransferMethod.FORWARD);
    }
}