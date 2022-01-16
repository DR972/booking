package by.rozmysl.booking.controller.command.impl.admin;

import by.rozmysl.booking.controller.command.*;
import by.rozmysl.booking.exception.CommandException;
import by.rozmysl.booking.exception.ServiceException;
import by.rozmysl.booking.model.service.RoomService;
import by.rozmysl.booking.model.service.ServiceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

import static by.rozmysl.booking.controller.command.RequestAttribute.ALL_ROOMS;
import static by.rozmysl.booking.controller.command.RequestAttribute.ALL_ROOMS_BY_TYPES_AND_SLEEPS;
import static by.rozmysl.booking.controller.command.RequestParameter.*;
import static by.rozmysl.booking.controller.command.RequestParameter.CHANGE_ROOM_PRICE;

/**
 * Provides service to initialize actions on the ChangeRoomParametersCommand.
 */
public class ChangeRoomParametersCommand implements Command {
    private static final Logger LOGGER = LoggerFactory.getLogger(ChangeRoomParametersCommand.class);
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

            req.setAttribute(ALL_ROOMS, roomService.findAll(DEFAULT_PAGE_NUMBER, DEFAULT_NUMBER_ROWS));
            req.setAttribute(ALL_ROOMS_BY_TYPES_AND_SLEEPS, roomService.findAllRoomsByTypesAndSleeps());
        } catch (ServiceException e) {
            LOGGER.error(e.getMessage(), e);
            throw new CommandException(e.getMessage(), e);
        }
        return new PageGuide(PageAddress.CHANGE_ROOM_PARAMETERS, TransferMethod.FORWARD);
    }
}