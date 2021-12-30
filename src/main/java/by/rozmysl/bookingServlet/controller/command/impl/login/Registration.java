package by.rozmysl.bookingServlet.controller.command.impl.login;

import by.rozmysl.bookingServlet.controller.command.*;
import by.rozmysl.bookingServlet.model.db.ConnectionPool;
import by.rozmysl.bookingServlet.exception.ServiceException;
import by.rozmysl.bookingServlet.model.entity.user.User;
import by.rozmysl.bookingServlet.model.entity.user.UserRole;
import by.rozmysl.bookingServlet.model.service.ServiceFactory;
import by.rozmysl.bookingServlet.model.service.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

import java.sql.Connection;

import static by.rozmysl.bookingServlet.controller.command.RequestAttribute.ERROR_VALIDATE;
import static by.rozmysl.bookingServlet.controller.command.RequestParameter.*;

/**
 * Provides service to initialize actions on the Registration.
 */
public class Registration implements Command {
    private static final Logger LOGGER = LoggerFactory.getLogger(Registration.class);
    public static final String USER = "USER";

    /**
     * Executes actions on request content.
     *
     * @param req request content
     * @return page to go
     * @throws ServiceException if there was an error accessing the service
     */
    @Override
    public PageGuide execute(HttpServletRequest req) throws ServiceException {
        if (req.getParameter(ACTION) == null) {
            return new PageGuide(PageAddress.REGISTRATION, TransferMethod.FORWARD);
        }
        ServiceFactory service = ServiceFactory.getInstance();
        final Connection connection = ConnectionPool.getInstance().getConnectionFromPool();
        try {
            UserService userService = service.userService(connection);
            User user = new User(req.getParameter(USERNAME), req.getParameter(LASTNAME), req.getParameter(FIRSTNAME),
                    req.getParameter(PASSWORD), req.getParameter(PASSWORD_CONFIRM), req.getParameter(EMAIL));
            String validate = userService.validateUser(user);
            if (validate != null) {
                req.setAttribute(ERROR_VALIDATE, validate);
                ConnectionPool.getInstance().returnConnectionToPool(connection);
                return new PageGuide(PageAddress.REGISTRATION, TransferMethod.FORWARD);
            }
            userService.save(user, req.getParameter(LANGUAGE));
            service.roleService(connection).save(new UserRole(user.getUsername(), USER));
            LOGGER.info("A new user has been registered - " + user.getUsername());
        } finally {
            ConnectionPool.getInstance().returnConnectionToPool(connection);
        }
        return new PageGuide(PageAddress.LOGIN, TransferMethod.FORWARD);
    }
}
