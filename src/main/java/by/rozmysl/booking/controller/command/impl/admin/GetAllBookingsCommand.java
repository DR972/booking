package by.rozmysl.booking.controller.command.impl.admin;

import by.rozmysl.booking.controller.command.*;
import by.rozmysl.booking.exception.CommandException;
import by.rozmysl.booking.exception.ServiceException;
import by.rozmysl.booking.model.entity.hotel.Room;
import by.rozmysl.booking.model.entity.hotel.StatusReservation;
import by.rozmysl.booking.model.entity.user.Booking;
import by.rozmysl.booking.model.service.BookingService;
import by.rozmysl.booking.model.service.RoomService;
import by.rozmysl.booking.model.service.ServiceProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static by.rozmysl.booking.controller.command.RequestAttribute.ALL_BOOKINGS;
import static by.rozmysl.booking.controller.command.RequestAttribute.AVAILABLE_ROOMS;
import static by.rozmysl.booking.controller.command.RequestParameter.*;
import static by.rozmysl.booking.model.ModelTypeProvider.*;

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
     * @throws CommandException if the operation failed
     */
    @Override
    public PageGuide execute(HttpServletRequest req) throws CommandException {
        ServiceProvider service = ServiceProvider.getInstance();
        BookingService bookingService = service.getBookingService();
        RoomService roomService = service.getRoomService();

        try {
            if (req.getParameter(DELETE) != null && req.getParameter(DELETE).equals(DELETE)) {
                bookingService.delete(Long.parseLong(req.getParameter(BOOKING_NUMBER)));
                LOGGER.info("Booking # " + req.getParameter(BOOKING_NUMBER) + " was deleted by admin " + req.getUserPrincipal().getName());
            }

            if (req.getParameter(CHANGE_ROOM) != null && req.getParameter(CHANGE_ROOM).equals(CHANGE_ROOM)) {
                bookingService.changeRoom(Long.parseLong(req.getParameter(BOOKING_NUMBER)), Integer.parseInt(req.getParameter(ROOM_NUMBER)), service);
                LOGGER.info("In booking # " + req.getParameter(BOOKING_NUMBER) + ", the room number was changed to " +
                        req.getParameter(ROOM_NUMBER) + " by the admin " + req.getUserPrincipal().getName());
            }

            if (req.getParameter(CHANGE_STATUS) != null && req.getParameter(CHANGE_STATUS).equals(CHANGE_STATUS)) {
                bookingService.changeBookingStatus(Long.parseLong(req.getParameter(BOOKING_NUMBER)), req.getParameter(RequestParameter.STATUS), service);
                LOGGER.info("In booking # " + req.getParameter(BOOKING_NUMBER) + ", the status was changed to '" +
                        req.getParameter(RequestParameter.STATUS) + "'  by the admin " + req.getUserPrincipal().getName());
            }

            int pageNumber = SelectingPageParameters.getPageNumber(req);
            int rows = SelectingPageParameters.getNumberRows(req);
            req.setAttribute(RequestAttribute.COUNT_PAGES, bookingService.countNumberEntityRows(BOOKING_FIND_ROWS_COUNT, rows));
            List<Booking> allBookings = bookingService.findListEntities(BOOKING_FIND_ALL, rows, pageNumber, rows);
            Map<Long, List<Room>> availableRooms = new HashMap<>();
            for (Booking booking : allBookings) {
                availableRooms.put(booking.getId(), roomService.findListEntities(ROOM_FIND_ALL_FREE_ROOMS_BETWEEN_TWO_DATES_WITH_GREATER_OR_EQUAL_SLEEPS,
                        booking.getPersons(), booking.getDeparture(), booking.getArrival()));
            }

            req.setAttribute(AVAILABLE_ROOMS, availableRooms);
            req.setAttribute(ALL_BOOKINGS, allBookings);
            req.setAttribute(RequestAttribute.STATUS, StatusReservation.values());
        } catch (ServiceException e) {
            LOGGER.error(e.getMessage(), e);
            throw new CommandException(e.getMessage(), e);
        }
        return new PageGuide(PageAddress.ALL_BOOKINGS, TransferMethod.FORWARD);
    }
}