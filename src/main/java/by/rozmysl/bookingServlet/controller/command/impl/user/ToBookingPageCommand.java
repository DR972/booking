package by.rozmysl.bookingServlet.controller.command.impl.user;

import by.rozmysl.bookingServlet.controller.command.*;
import by.rozmysl.bookingServlet.model.db.ConnectionPool;
import by.rozmysl.bookingServlet.exception.ServiceException;
import by.rozmysl.bookingServlet.model.entity.hotel.Room;
import by.rozmysl.bookingServlet.model.entity.user.Booking;

import by.rozmysl.bookingServlet.model.service.RoomService;
import by.rozmysl.bookingServlet.model.service.ServiceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;

import static by.rozmysl.bookingServlet.controller.command.RequestAttribute.*;
import static by.rozmysl.bookingServlet.controller.command.RequestParameter.*;
import static by.rozmysl.bookingServlet.controller.command.RequestParameter.ROOM_NUMBER;

/**
 * Provides service to initialize actions on the ToBookingPageCommand.
 */
public class ToBookingPageCommand implements Command {
    private static final Logger LOGGER = LoggerFactory.getLogger(ToBookingPageCommand.class);

    /**
     * Executes actions on request content.
     *
     * @param req request content
     * @return page to go
     * @throws ServiceException if there was an error accessing the service
     */
    @Override
    public PageGuide execute(HttpServletRequest req) throws ServiceException {
        ServiceFactory service = ServiceFactory.getInstance();
        final Connection connection = ConnectionPool.getInstance().getConnectionFromPool();
        try {
            RoomService roomService = service.roomService(connection);
            Booking booking = new Booking(Integer.parseInt(req.getParameter(RequestParameter.PERSONS)), LocalDate.parse(req.getParameter(ARRIVAL)),
                    LocalDate.parse(req.getParameter(ARRIVAL)).plusDays(Integer.parseInt(req.getParameter(DAYS))),
                    Integer.parseInt(req.getParameter(DAYS)), service.foodService(connection).findById(req.getParameter(FOOD)),
                    service.servicesService(connection).findById(req.getParameter(SERVICE)));
            Room room = roomService.findById(Integer.parseInt(req.getParameter(ROOM_NUMBER)));
            List<Room> suitableRooms = roomService.findAllFreeRoomsBetweenTwoDatesByTypesAndSleeps(booking.getArrival(),
                    booking.getDeparture(), room.getType(), room.getSleeps());
            if (suitableRooms.size() == 0) {
                req.setAttribute(BOOKING, booking);
                req.setAttribute(MISSED, MESSAGE_MISSED);
                if (roomService.findAllTypesFreeRoomsBetweenTwoDatesWithGreaterOrEqualSleeps
                        (booking.getArrival(), booking.getDeparture(), booking.getPersons()).size() == 0) {
                    req.setAttribute(NO_AVAILABLE, MESSAGE_NO_AVAILABLE);
                }
                ConnectionPool.getInstance().returnConnectionToPool(connection);
                return new PageGuide(PageAddress.BOOKING, TransferMethod.FORWARD);
            } else {
                booking.setRoom(suitableRooms.get(0));
                booking.setUser(service.userService(connection).findById(req.getUserPrincipal().getName()));
                req.setAttribute(BOOKING, service.bookingService(connection).save(booking));
                LOGGER.info("The booking was made by the user - " + booking.getUser().getUsername());
                ConnectionPool.getInstance().returnConnectionToPool(connection);
                return new PageGuide(PageAddress.CONFIRMATION, TransferMethod.FORWARD);
            }
        } finally {
            ConnectionPool.getInstance().returnConnectionToPool(connection);
        }
    }
}