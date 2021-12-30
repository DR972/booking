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
import static by.rozmysl.bookingServlet.controller.command.RequestAttribute.ALL_ROOMS_BY_TYPES_AND_SLEEPS;
import static by.rozmysl.bookingServlet.controller.command.RequestParameter.*;
import static by.rozmysl.bookingServlet.controller.command.RequestParameter.CHANGE_ROOM_PRICE;

/**
 * Provides service to initialize actions on the ChangeRoomParametersCommand.
 */
public class ChangeRoomParametersCommand implements Command {
    private static final Logger LOGGER = LoggerFactory.getLogger(ChangeRoomParametersCommand.class);

    /**
     * Executes actions on request content.
     *
     * @param req request content
     * @return page to go
     * @throws ServiceException if there was an error accessing the database
     */
    @Override
    public PageGuide execute(HttpServletRequest req) throws ServiceException {
        final Connection connection = ConnectionPool.getInstance().getConnectionFromPool();
        try {
            RoomService roomService = ServiceFactory.getInstance().roomService(connection);
            if (req.getParameter(CHANGE_PARAMETERS) != null
                    && req.getParameter(CHANGE_PARAMETERS).equals(CHANGE_PARAMETERS)) {
                roomService.updateParameters(Integer.parseInt(req.getParameter(ROOM_NUMBER)),
                        roomService.findById(Integer.parseInt(req.getParameter(PARAMETER))));
                LOGGER.info("For room # " + req.getParameter(ROOM_NUMBER) + ", the parameters were changed to '" +
                        req.getParameter(PARAMETER) + "' by admin " + req.getUserPrincipal().getName());
            }
            if (req.getParameter(CHANGE_ROOM_PRICE) != null
                    && req.getParameter(CHANGE_ROOM_PRICE).equals(CHANGE_ROOM_PRICE)) {
                roomService.updatePrice(roomService.findById(Integer.parseInt(req.getParameter(ROOM_NUMBER))),
                        new BigDecimal(req.getParameter(PRICE)));
                LOGGER.info("For room # " + req.getParameter(ROOM_NUMBER) + ", the price was changed to '" +
                        req.getParameter(PRICE) + "' by admin " + req.getUserPrincipal().getName());
            }
            req.setAttribute(ALL_ROOMS, roomService.findAll(0, 0));
            req.setAttribute(ALL_ROOMS_BY_TYPES_AND_SLEEPS, roomService.findAllRoomsByTypesAndSleeps());
        } finally {
            ConnectionPool.getInstance().returnConnectionToPool(connection);
        }
        return new PageGuide(PageAddress.CHANGE_ROOM_PARAMETERS, TransferMethod.FORWARD);
    }
}