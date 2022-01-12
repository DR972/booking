package by.rozmysl.bookingServlet.controller.command.impl.login;

import by.rozmysl.bookingServlet.controller.command.*;
import by.rozmysl.bookingServlet.exception.ServiceException;
import by.rozmysl.bookingServlet.model.service.ServiceFactory;

import javax.servlet.http.HttpServletRequest;

import static by.rozmysl.bookingServlet.controller.command.RequestAttribute.*;

/**
 * Provides service to initialize actions on the ToPriceCommand.
 */
public class ToPriceCommand implements Command {
    private static final int DEFAULT_PAGE_NUMBER = 0;
    private static final int DEFAULT_NUMBER_ROWS = 5;

    /**
     * Executes actions on request content.
     *
     * @param req request content
     * @return page to go
     * @throws ServiceException if there was an error accessing the service
     */
    @Override
    public PageGuide execute(HttpServletRequest req) throws ServiceException {
        ServiceFactory service = ServiceFactory.getInstance();
        req.setAttribute(ALL_ROOMS_BY_TYPES_AND_SLEEPS, service.getRoomService().findAllRoomsByTypesAndSleeps());
        req.setAttribute(ALL_FOOD, service.getFoodService().findAll(DEFAULT_PAGE_NUMBER, DEFAULT_NUMBER_ROWS));
        req.setAttribute(ALL_SERVICES, service.getServicesService().findAll(DEFAULT_PAGE_NUMBER, DEFAULT_NUMBER_ROWS));
        return new PageGuide(PageAddress.PRICE, TransferMethod.FORWARD);
    }
}