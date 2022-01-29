package by.rozmysl.booking.controller.command.impl.admin;

import by.rozmysl.booking.controller.command.*;
import by.rozmysl.booking.exception.CommandException;
import by.rozmysl.booking.exception.ServiceException;
import by.rozmysl.booking.model.entity.hotel.Room;

import by.rozmysl.booking.model.service.RoomService;
import by.rozmysl.booking.model.service.ServiceProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

import static by.rozmysl.booking.controller.command.RequestAttribute.*;
import static by.rozmysl.booking.controller.command.RequestParameter.*;
import static by.rozmysl.booking.controller.command.RequestParameter.PRICE;
import static by.rozmysl.booking.controller.command.RequestParameter.SLEEPS;
import static by.rozmysl.booking.controller.command.RequestParameter.TYPE;
import static by.rozmysl.booking.model.ModelTypeProvider.*;

/**
 * Provides service to initialize actions on the CreateNewRoomCommand.
 */
public class CreateNewRoomCommand implements Command {
    private static final Logger LOGGER = LoggerFactory.getLogger(CreateNewRoomCommand.class);

    /**
     * Executes actions on request content.
     *
     * @param req request content
     * @return page to go
     * @throws CommandException if the operation failed
     */
    @Override
    public PageGuide execute(HttpServletRequest req) throws CommandException {
        RoomService roomService = ServiceProvider.getInstance().getRoomService();
        try {
            req.setAttribute(ALL_TYPES_ROOMS, roomService.findListEntities(ROOM_FIND_ALL_TYPES_ROOMS));

            if (req.getParameter(ACTION) == null) {
                return new PageGuide(PageAddress.ADD_ROOM, TransferMethod.FORWARD);
            }

            if (roomService.findEntity(Room.class, ROOM_FIND_BY_ID, Integer.parseInt(req.getParameter(RequestParameter.ROOM_NUMBER))) != null) {
                req.setAttribute(ERROR_ROOM_NUMBER, ERROR_ROOM_NUMBER_RU);
                return new PageGuide(PageAddress.ADD_ROOM, TransferMethod.FORWARD);
            }

            Room room = roomService.findEntity(Room.class, ROOM_FIND_ROOM_BY_TYPE_AND_SLEEPING, req.getParameter(TYPE), Integer.parseInt(req.getParameter(SLEEPS)));
            if (room == null && req.getParameter(PRICE) == null) {
                req.setAttribute(RequestAttribute.ROOM_NUMBER, req.getParameter(RequestParameter.ROOM_NUMBER));
                req.setAttribute(RequestAttribute.TYPE, req.getParameter(RequestParameter.TYPE));
                req.setAttribute(RequestAttribute.SLEEPS, Integer.parseInt(req.getParameter(RequestParameter.SLEEPS)));
                req.setAttribute(NEW_ROOM, null);
                return new PageGuide(PageAddress.ADD_ROOM, TransferMethod.FORWARD);
            }

            if (room != null) {
                roomService.updateEntity(ROOM_SAVE, Integer.parseInt(req.getParameter(RequestParameter.ROOM_NUMBER)), room.getType(),
                        room.getSleeps(), room.getPrice());
                LOGGER.info("Admin '" + req.getUserPrincipal().getName() + "' created a new room - " + room);
            }

            if (req.getParameter(PRICE) != null) {
                roomService.updateEntity(ROOM_SAVE, Integer.parseInt(req.getParameter(RequestParameter.ROOM_NUMBER)),
                        req.getParameter(TYPE), Integer.parseInt(req.getParameter(SLEEPS)), new BigDecimal(req.getParameter(PRICE)));
                LOGGER.info("Admin '" + req.getUserPrincipal().getName() + "' created a new room - " + room);
            }
        } catch (ServiceException e) {
            LOGGER.error(e.getMessage(), e);
            throw new CommandException(e.getMessage(), e);
        }
        return new PageGuide(PageAddress.ADMIN, TransferMethod.FORWARD);
    }
}