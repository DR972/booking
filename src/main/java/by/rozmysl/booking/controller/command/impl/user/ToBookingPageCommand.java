package by.rozmysl.booking.controller.command.impl.user;

import by.rozmysl.booking.controller.command.*;
import by.rozmysl.booking.exception.CommandException;
import by.rozmysl.booking.exception.ServiceException;
import by.rozmysl.booking.model.entity.hotel.Room;
import by.rozmysl.booking.model.entity.user.Booking;

import by.rozmysl.booking.model.service.RoomService;
import by.rozmysl.booking.model.service.ServiceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static by.rozmysl.booking.controller.command.RequestAttribute.*;
import static by.rozmysl.booking.controller.command.RequestParameter.*;
import static by.rozmysl.booking.controller.command.RequestParameter.ROOM_NUMBER;

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
     * @throws CommandException if the operation failed
     */
    @Override
    public PageGuide execute(HttpServletRequest req) throws CommandException {
        ServiceFactory service = ServiceFactory.getInstance();
        RoomService roomService = service.getRoomService();

        if (req.getSession().getAttribute(ACTION).equals(CONFIRMATION)) {
            return new PageGuide(PageAddress.CONFIRMATION, TransferMethod.FORWARD);
        }

        try {
            Booking booking = (Booking) req.getSession().getAttribute(BOOKING);
            Room room = roomService.findById(Integer.parseInt(req.getParameter(ROOM_NUMBER)));
            List<Room> suitableRooms = roomService.findAllFreeRoomsBetweenTwoDatesByTypesAndSleeps(booking.getArrival(),
                    booking.getDeparture(), room.getType(), room.getSleeps());

            if (suitableRooms.size() == 0) {
                req.setAttribute(MISSED, MESSAGE_MISSED);
                if (roomService.findAllTypesFreeRoomsBetweenTwoDatesWithGreaterOrEqualSleeps
                        (booking.getArrival(), booking.getDeparture(), booking.getPersons()).size() == 0) {
                    req.setAttribute(NO_AVAILABLE, MESSAGE_NO_AVAILABLE);
                }
                return new PageGuide(PageAddress.BOOKING, TransferMethod.FORWARD);

            } else {
                booking.setRoom(suitableRooms.get(0));
                booking.setUser(service.getUserService().findById(req.getUserPrincipal().getName()));
                service.getBookingService().save(booking);

                LOGGER.info("The booking was made by the user - " + booking.getUser().getId());
                req.getSession().setAttribute(BOOKING, booking);
                req.getSession().setAttribute(ACTION, CONFIRMATION);
                return new PageGuide(PageAddress.CONFIRMATION, TransferMethod.FORWARD);
            }
        } catch (ServiceException e) {
            LOGGER.error(e.getMessage(), e);
            throw new CommandException(e.getMessage(), e);
        }
    }
}