package by.rozmysl.bookingServlet.controller.command.impl.login;

import by.rozmysl.bookingServlet.controller.command.*;
import by.rozmysl.bookingServlet.model.db.ConnectionPool;
import by.rozmysl.bookingServlet.exception.ServiceException;
import by.rozmysl.bookingServlet.model.service.ServiceFactory;

import javax.servlet.http.HttpServletRequest;

import java.sql.Connection;

import static by.rozmysl.bookingServlet.controller.command.RequestAttribute.*;

/**
 * Provides service to initialize actions on the ActivationAccountCommand.
 */
public class ActivationAccountCommand implements Command {

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
            if (ServiceFactory.getInstance().userService(connection).activateUser(req.getPathInfo().substring(11))) {
                req.setAttribute(MESSAGE_ACTIVE, MESSAGE_ACTIVE_TRUE);
            } else {
                req.setAttribute(MESSAGE_ACTIVE, MESSAGE_ACTIVE_ERROR);
            }
        } finally {
            ConnectionPool.getInstance().returnConnectionToPool(connection);
        }
        return new PageGuide(PageAddress.LOGIN,TransferMethod.FORWARD);
    }
}