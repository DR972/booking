package by.rozmysl.bookingServlet.controller.command.impl.admin;

import by.rozmysl.bookingServlet.controller.command.*;
import by.rozmysl.bookingServlet.model.db.ConnectionPool;
import by.rozmysl.bookingServlet.exception.ServiceException;
import by.rozmysl.bookingServlet.model.service.ServiceFactory;
import by.rozmysl.bookingServlet.model.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

import java.sql.Connection;

import static by.rozmysl.bookingServlet.controller.command.RequestAttribute.*;
import static by.rozmysl.bookingServlet.controller.command.RequestParameter.*;

/**
 * Provides service to initialize actions on the GetAllUsersCommand.
 */
public class GetAllUsersCommand implements Command {
    private static final Logger LOGGER = LoggerFactory.getLogger(GetAllUsersCommand.class);

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
        final Connection connection = ConnectionPool.getInstance().getConnectionFromPool();
        try {
            UserService userService = service.userService(connection);
            if (req.getParameter(DELETE) != null && req.getParameter(DELETE).equals(DELETE)) {
                String username = req.getParameter(USERNAME);
                if (!service.bookingService(connection).findAllBookingsByUser(username).isEmpty()) {
                    req.setAttribute(ERROR_DELETE_USER, ERROR_DELETE_USER_RU);
                } else {
                    service.roleService(connection).delete(username);
                    userService.delete(username);
                    LOGGER.info("The user '" + username + "' was deleted by the admin " + req.getUserPrincipal().getName());
                }
            }
            int page = 0;
            int rows = 10;
            if (req.getParameter(RequestParameter.ROWS) != null) {
                rows = Integer.parseInt(req.getParameter(RequestParameter.ROWS));
            }
            if (req.getParameter(RequestParameter.PAGE) != null) {
                page = Integer.parseInt(req.getParameter(RequestParameter.PAGE));
            }
            req.setAttribute(RequestAttribute.ROWS, rows);
            req.setAttribute(RequestAttribute.PAGE, page);
            req.setAttribute(COUNT_PAGES, userService.countUsersPages(rows));
            req.setAttribute(ALL_USERS, userService.findAll(page, rows));
        } finally {
            ConnectionPool.getInstance().returnConnectionToPool(connection);
        }
        return new PageGuide(PageAddress.ALL_USERS, TransferMethod.FORWARD);
    }
}