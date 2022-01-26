package by.rozmysl.booking.controller.command.impl.login;

import by.rozmysl.booking.controller.command.*;
import by.rozmysl.booking.exception.CommandException;
import by.rozmysl.booking.exception.ServiceException;
import by.rozmysl.booking.model.service.ServiceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

import static by.rozmysl.booking.controller.command.RequestAttribute.*;
import static by.rozmysl.booking.model.ModelManager.*;

/**
 * Provides service to initialize actions on the ToPriceCommand.
 */
public class ToPriceCommand implements Command {
    private static final Logger LOGGER = LoggerFactory.getLogger(ToPriceCommand.class);
    private static final int DEFAULT_PAGE_NUMBER = 0;
    private static final int DEFAULT_NUMBER_ROWS = 5;

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
        try {
            req.setAttribute(ALL_ROOMS_BY_TYPES_AND_SLEEPS, service.getRoomService().findListEntities(ROOM_FIND_ALL_ROOMS_BY_TYPES_AND_SLEEPS));
            req.setAttribute(ALL_FOOD, service.getFoodService().findListEntities(FOOD_FIND_ALL, DEFAULT_NUMBER_ROWS, DEFAULT_PAGE_NUMBER, DEFAULT_NUMBER_ROWS));
            req.setAttribute(ALL_SERVICES, service.getServicesService().findListEntities(ADDITIONALSERVICES_FIND_ALL, DEFAULT_NUMBER_ROWS, DEFAULT_PAGE_NUMBER, DEFAULT_NUMBER_ROWS));
        } catch (ServiceException e) {
            LOGGER.error(e.getMessage(), e);
            throw new CommandException(e.getMessage(), e);
        }
        return new PageGuide(PageAddress.PRICE, TransferMethod.FORWARD);
    }
}