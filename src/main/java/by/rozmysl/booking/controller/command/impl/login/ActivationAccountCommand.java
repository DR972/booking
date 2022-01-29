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
import static by.rozmysl.booking.model.ModelTypeProvider.USER_ACTIVATE;
import static by.rozmysl.booking.model.ModelTypeProvider.USER_FIND_BY_ACTIVATION_CODE;

/**
 * Provides service to initialize actions on the ActivationAccountCommand.
 */
public class ActivationAccountCommand implements Command {
    private static final Logger LOGGER = LoggerFactory.getLogger(ActivationAccountCommand.class);

    /**
     * Executes actions on request content.
     *
     * @param req request content
     * @return page to go
     * @throws CommandException if the operation failed
     */
    @Override
    public PageGuide execute(HttpServletRequest req) throws CommandException {
        UserService userService = ServiceProvider.getInstance().getUserService();
        try {
            User user = userService.findEntity(User.class, USER_FIND_BY_ACTIVATION_CODE, req.getPathInfo().substring(11));
            if (user == null) {
                req.setAttribute(MESSAGE_ACTIVE, MESSAGE_ACTIVE_ERROR);
            } else {
                userService.updateEntity(USER_ACTIVATE, user.getId());
                req.setAttribute(MESSAGE_ACTIVE, MESSAGE_ACTIVE_TRUE);
            }
        } catch (ServiceException e) {
            LOGGER.error(e.getMessage(), e);
            throw new CommandException(e.getMessage(), e);
        }
        return new PageGuide(PageAddress.LOGIN, TransferMethod.FORWARD);
    }
}