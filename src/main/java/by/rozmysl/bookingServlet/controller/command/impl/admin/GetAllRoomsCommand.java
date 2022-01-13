package by.rozmysl.bookingServlet.controller.command.impl.admin;

import by.rozmysl.bookingServlet.controller.command.*;
import by.rozmysl.bookingServlet.exception.CommandException;
import by.rozmysl.bookingServlet.exception.ServiceException;
import by.rozmysl.bookingServlet.model.service.RoomService;
import by.rozmysl.bookingServlet.model.service.ServiceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

import static by.rozmysl.bookingServlet.controller.command.RequestAttribute.ALL_ROOMS;
import static by.rozmysl.bookingServlet.controller.command.RequestParameter.*;

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
        RoomService roomService = ServiceFactory.getInstance().getRoomService();

        try {
            if (req.getParameter(CHANGE_ROOM_PRICE) != null && req.getParameter(CHANGE_ROOM_PRICE).equals(CHANGE_ROOM_PRICE)) {
                roomService.updatePrice(roomService.findById(Integer.parseInt(req.getParameter(ROOM_NUMBER))), new BigDecimal(req.getParameter(PRICE)));
                LOGGER.info("For room # " + req.getParameter(ROOM_NUMBER) + ", the price was changed to '" +
                        req.getParameter(PRICE) + "' by admin " + req.getUserPrincipal().getName());
            }
            if (req.getParameter(DELETE) != null && req.getParameter(DELETE).equals(DELETE)) {
                roomService.delete(Integer.parseInt(req.getParameter(ROOM_NUMBER)));
                LOGGER.info("Room # " + req.getParameter(ROOM_NUMBER) + " was deleted by admin " + req.getUserPrincipal().getName());
            }

            req.setAttribute(ALL_ROOMS, roomService.findAll(DEFAULT_PAGE_NUMBER, DEFAULT_NUMBER_ROWS));
        } catch (ServiceException e) {
            LOGGER.error(e.getMessage(), e);
            throw new CommandException(e.getMessage(), e);
        }
        return new PageGuide(PageAddress.ALL_ROOMS, TransferMethod.FORWARD);
    }
}