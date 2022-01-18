package by.rozmysl.booking.controller.command.impl.login;

import by.rozmysl.booking.controller.command.*;
import by.rozmysl.booking.controller.util.AppUtil;
import by.rozmysl.booking.exception.CommandException;
import by.rozmysl.booking.exception.ServiceException;
import by.rozmysl.booking.model.entity.user.User;
import by.rozmysl.booking.model.service.ServiceFactory;
import by.rozmysl.booking.model.service.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

import static by.rozmysl.booking.controller.command.RequestAttribute.LOGIN_ERROR;
import static by.rozmysl.booking.controller.command.RequestParameter.*;

/**
 * Provides service to initialize actions on the LoginCommand.
 */
public class LoginCommand implements Command {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoginCommand.class);
    private static final String ADMIN = "ADMIN";

    /**
     * Executes actions on request content.
     *
     * @param req request content
     * @return page to go
     * @throws CommandException if the operation failed
     */
    @Override
    public PageGuide execute(HttpServletRequest req) throws CommandException {
        if (req.getParameter(ACTION) == null) {
            return new PageGuide(PageAddress.LOGIN, TransferMethod.FORWARD);
        }

        UserService userService = ServiceFactory.getInstance().getUserService();
        User user;
        try {
            user = userService.findById(req.getParameter(USERNAME));
        } catch (ServiceException e) {
            LOGGER.error(e.getMessage(), e);
            throw new CommandException(e.getMessage(), e);
        }
        String authentication = userService.authenticateUser(user, req.getParameter(PASSWORD));
        if (authentication != null) {
            req.setAttribute(LOGIN_ERROR, authentication);
            return new PageGuide(PageAddress.LOGIN, TransferMethod.FORWARD);
        }

        AppUtil.saveLoggedUser(req.getSession(), user);
        LOGGER.info("The user '" + user.getId() + "' is logged in.");

        int redirectId = -1;
        if (!req.getParameter(REDIRECT_ID).isEmpty()) {
            redirectId = Integer.parseInt(req.getParameter(REDIRECT_ID));
        }

        String requestUri = AppUtil.getRedirectAfterLoginUrl(redirectId);
        if (requestUri != null) {
            return new PageGuide(requestUri, TransferMethod.REDIRECT);
        } else {
            return user.getRoles().contains(ADMIN) ? new PageGuide(PageAddress.ADMIN_REDIRECT, TransferMethod.REDIRECT)
                    : new PageGuide(PageAddress.USER_REDIRECT, TransferMethod.REDIRECT);
        }
    }
}
