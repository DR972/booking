package by.rozmysl.booking.controller.command.impl.login;

import by.rozmysl.booking.controller.command.*;
import by.rozmysl.booking.exception.CommandException;
import by.rozmysl.booking.exception.ServiceException;
import by.rozmysl.booking.model.entity.user.User;
import by.rozmysl.booking.model.service.ServiceProvider;
import by.rozmysl.booking.model.service.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

import static by.rozmysl.booking.controller.command.RequestAttribute.*;
import static by.rozmysl.booking.controller.command.RequestParameter.*;

/**
 * Provides service to initialize actions on the RegistrationCommand.
 */
public class RegistrationCommand implements Command {
    private static final Logger LOGGER = LoggerFactory.getLogger(RegistrationCommand.class);

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
            return new PageGuide(PageAddress.REGISTRATION, TransferMethod.FORWARD);
        }

        ServiceProvider service = ServiceProvider.getInstance();
        UserService userService = service.getUserService();
        User user = new User(
                req.getParameter(USERNAME),
                req.getParameter(LASTNAME),
                req.getParameter(FIRSTNAME),
                req.getParameter(PASSWORD),
                req.getParameter(PASSWORD_CONFIRM),
                req.getParameter(EMAIL));

        try {
            String validate = userService.validateUser(user);
            if (validate != null) {
                req.setAttribute(ERROR_VALIDATE, validate);
                return new PageGuide(PageAddress.REGISTRATION, TransferMethod.FORWARD);
            }

            userService.save(user, req.getParameter(LANGUAGE), service);
            LOGGER.info("A new user has been registered - " + user.getId());
        } catch (ServiceException e) {
            LOGGER.error(e.getMessage(), e);
            throw new CommandException(e.getMessage(), e);
        }
        return new PageGuide(PageAddress.LOGIN, TransferMethod.FORWARD);
    }
}
