package by.rozmysl.bookingServlet.controller.command.impl.login;

import by.rozmysl.bookingServlet.controller.command.*;
import by.rozmysl.bookingServlet.model.db.ConnectionPool;
import by.rozmysl.bookingServlet.exception.ServiceException;
import by.rozmysl.bookingServlet.model.entity.user.User;
import by.rozmysl.bookingServlet.model.service.ServiceFactory;
import by.rozmysl.bookingServlet.model.service.UserService;
import by.rozmysl.bookingServlet.controller.util.AppUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

import java.sql.Connection;

import static by.rozmysl.bookingServlet.controller.command.RequestAttribute.LOGIN_ERROR;
import static by.rozmysl.bookingServlet.controller.command.RequestParameter.*;

/**
 * Provides service to initialize actions on the LoginCommand.
 */
public class LoginCommand implements Command {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoginCommand.class);
    public static final String ADMIN = "ADMIN";

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

            if (req.getParameter(ACTION) == null) {
                return new PageGuide(PageAddress.LOGIN, TransferMethod.FORWARD);
            }
            UserService userService = ServiceFactory.getInstance().userService(connection);
            User user = userService.findById(req.getParameter(USERNAME));
            String authentication = userService.authenticateUser(user, req.getParameter(PASSWORD));
            if (authentication != null) {
                req.setAttribute(LOGIN_ERROR, authentication);
                return new PageGuide(PageAddress.LOGIN, TransferMethod.FORWARD);
            }
            AppUtils.saveLoggedUser(req.getSession(), user);
            LOGGER.info("The user '" + user.getUsername() + "' is logged in.");
            int redirectId = -1;
            if (!req.getParameter(REDIRECT_ID).isEmpty()) {
                redirectId = Integer.parseInt(req.getParameter(REDIRECT_ID));
            }
            String requestUri = AppUtils.getRedirectAfterLoginUrl(redirectId);
            if (requestUri != null) {
                return new PageGuide(requestUri, TransferMethod.REDIRECT);
            } else {
                if (user.getRoles().contains(ADMIN)) {
                    return new PageGuide(PageAddress.ADMIN_REDIRECT, TransferMethod.REDIRECT);
                } else {
                    return new PageGuide(PageAddress.USER_REDIRECT, TransferMethod.REDIRECT);
                }
            }
        } finally {
            ConnectionPool.getInstance().returnConnectionToPool(connection);
        }
    }
}
