package by.rozmysl.bookingServlet.controller.command.impl.login;

import by.rozmysl.bookingServlet.controller.command.*;
import by.rozmysl.bookingServlet.model.db.ConnectionPool;
import by.rozmysl.bookingServlet.exception.ServiceException;
import by.rozmysl.bookingServlet.model.service.ServiceFactory;

import javax.servlet.http.HttpServletRequest;

import java.sql.Connection;

import static by.rozmysl.bookingServlet.controller.command.RequestAttribute.*;

/**
 * Provides service to initialize actions on the ToPriceCommand.
 */
public class ToPriceCommand implements Command {
    /**
     * Executes actions on request content.
     *
     * @param req request content
     * @return page to go
     * @throws ServiceException       if there was an error accessing the service
     */
    @Override
    public PageGuide execute(HttpServletRequest req) throws ServiceException {
        ServiceFactory service = ServiceFactory.getInstance();
        final Connection connection = ConnectionPool.getInstance().getConnectionFromPool();
        try {
            req.setAttribute(ALL_ROOMS_BY_TYPES_AND_SLEEPS, service.roomService(connection).findAllRoomsByTypesAndSleeps());
            req.setAttribute(ALL_FOOD, service.foodService(connection).findAll(0, 0));
            req.setAttribute(ALL_SERVICES, service.servicesService(connection).findAll(0, 0));
        } finally {
            ConnectionPool.getInstance().returnConnectionToPool(connection);
        }
        return new PageGuide(PageAddress.PRICE, TransferMethod.FORWARD);
    }
}