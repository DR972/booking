package by.rozmysl.booking.controller.command.impl.admin;

import by.rozmysl.booking.controller.command.*;
import by.rozmysl.booking.exception.CommandException;
import by.rozmysl.booking.exception.ServiceException;
import by.rozmysl.booking.model.service.RoomService;
import by.rozmysl.booking.model.service.ServiceProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Objects;

import static by.rozmysl.booking.controller.command.RequestAttribute.ALL_ROOMS;
import static by.rozmysl.booking.controller.command.RequestParameter.*;
import static by.rozmysl.booking.model.ModelTypeProvider.*;

/**
 * Provides service to initialize actions on the GetAllRoomsCommand.
 */
public class GetAllRoomsCommand implements Command {
    private static final Logger LOGGER = LoggerFactory.getLogger(GetAllRoomsCommand.class);
    private static final int DEFAULT_PAGE_NUMBER = 0;
    private static final int DEFAULT_NUMBER_ROWS = 100;

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
            if (Objects.equals(req.getParameter(CHANGE_ROOM_PRICE), CHANGE_ROOM_PRICE)) {
                roomService.updateEntity(ROOM_UPDATE_PRICE, new BigDecimal(req.getParameter(PRICE)),
                        req.getParameter(TYPE), Integer.parseInt(req.getParameter(SLEEPS)));
                LOGGER.info("For room # " + req.getParameter(ROOM_NUMBER) + ", the price was changed to '" +
                        req.getParameter(PRICE) + "' by admin " + req.getUserPrincipal().getName());
            }
            if (Objects.equals(req.getParameter(DELETE), DELETE)) {
                roomService.updateEntity(ROOM_DELETE, Integer.parseInt(req.getParameter(ROOM_NUMBER)));
                LOGGER.info("Room # " + req.getParameter(ROOM_NUMBER) + " was deleted by admin " + req.getUserPrincipal().getName());
            }

            req.setAttribute(ALL_ROOMS, roomService.findListEntities(ROOM_FIND_ALL, DEFAULT_NUMBER_ROWS, DEFAULT_PAGE_NUMBER, DEFAULT_NUMBER_ROWS));
        } catch (ServiceException e) {
            LOGGER.error(e.getMessage(), e);
            throw new CommandException(e.getMessage(), e);
        }
        return new PageGuide(PageAddress.ALL_ROOMS, TransferMethod.FORWARD);
    }
}