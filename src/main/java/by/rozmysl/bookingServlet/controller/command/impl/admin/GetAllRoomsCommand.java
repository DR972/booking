package by.rozmysl.bookingServlet.controller.command.impl.admin;

import by.rozmysl.bookingServlet.controller.command.*;
import by.rozmysl.bookingServlet.model.db.ConnectionPool;
import by.rozmysl.bookingServlet.exception.ServiceException;
import by.rozmysl.bookingServlet.model.service.RoomService;
import by.rozmysl.bookingServlet.model.service.ServiceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.sql.Connection;

import static by.rozmysl.bookingServlet.controller.command.RequestAttribute.ALL_ROOMS;
import static by.rozmysl.bookingServlet.controller.command.RequestParameter.*;

/**
 * Provides service to initialize actions on the GetAllRoomsCommand.
 */
public class GetAllRoomsCommand implements Command {
    private static final Logger LOGGER = LoggerFactory.getLogger(GetAllRoomsCommand.class);

    /**
     * Executes actions on request content.
     *
     * @param req request content
     * @return page to go
     * @throws ServiceException if there was an error accessing the service
     */
    @Override
    public PageGuide execute(HttpServletRequest req) throws ServiceException {
        final Connection connection = ConnectionPool.getInstance().getConnectionFromPool();
        try {
            RoomService roomService = ServiceFactory.getInstance().roomService(connection);
            if (req.getParameter(CHANGE_ROOM_PRICE) != null && req.getParameter(CHANGE_ROOM_PRICE).equals(CHANGE_ROOM_PRICE)) {
                roomService.updatePrice(roomService.findById(Integer.parseInt(req.getParameter(ROOM_NUMBER))), new BigDecimal(req.getParameter(PRICE)));
                LOGGER.info("For room # " + req.getParameter(ROOM_NUMBER) + ", the price was changed to '" +
                        req.getParameter(PRICE) + "' by admin " + req.getUserPrincipal().getName());
            }
            if (req.getParameter(DELETE) != null && req.getParameter(DELETE).equals(DELETE)) {
                roomService.delete(Integer.parseInt(req.getParameter(ROOM_NUMBER)));
                LOGGER.info("Room # " + req.getParameter(ROOM_NUMBER) + " was deleted by admin " + req.getUserPrincipal().getName());
            }
            req.setAttribute(ALL_ROOMS, roomService.findAll(0, 0));
        } finally {
            ConnectionPool.getInstance().returnConnectionToPool(connection);
        }
        return new PageGuide(PageAddress.ALL_ROOMS, TransferMethod.FORWARD);
    }
}