package by.rozmysl.booking.controller.command.impl.admin;

import by.rozmysl.booking.controller.command.*;
import by.rozmysl.booking.exception.CommandException;
import by.rozmysl.booking.exception.ServiceException;
import by.rozmysl.booking.model.service.ServiceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;

import static by.rozmysl.booking.controller.command.RequestAttribute.*;
import static by.rozmysl.booking.controller.command.RequestParameter.*;
import static by.rozmysl.booking.model.ModelManager.ROOM_FIND_ALL_FREE_ROOMS_BETWEEN_TWO_DATES;

/**
 * Provides service to initialize actions on the GetFreeRoomsCommand.
 */
public class GetFreeRoomsCommand implements Command {
    private static final Logger LOGGER = LoggerFactory.getLogger(GetFreeRoomsCommand.class);

    /**
     * Executes actions on request content.
     *
     * @param req request content
     * @return page to go
     * @throws CommandException if the operation failed
     */
    @Override
    public PageGuide execute(HttpServletRequest req) throws CommandException {
        try {
            if (req.getParameter(FROM) != null && req.getParameter(TO) != null) {
                if (LocalDate.parse(req.getParameter(FROM)).isBefore(LocalDate.parse(req.getParameter(TO)))) {
                    req.setAttribute(FREE_ROOMS, ServiceFactory.getInstance().getRoomService().findListEntities(ROOM_FIND_ALL_FREE_ROOMS_BETWEEN_TWO_DATES,
                            LocalDate.parse(req.getParameter(TO)), LocalDate.parse(req.getParameter(FROM))));
                } else {
                    req.setAttribute(DATE_ERROR, DATE_ERROR_RU);
                }
            }
        } catch (ServiceException e) {
            LOGGER.error(e.getMessage(), e);
            throw new CommandException(e.getMessage(), e);
        }
        return new PageGuide(PageAddress.FREE_ROOMS, TransferMethod.FORWARD);
    }
}