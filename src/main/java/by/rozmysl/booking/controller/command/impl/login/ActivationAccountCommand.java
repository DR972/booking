package by.rozmysl.booking.controller.command.impl.login;

import by.rozmysl.booking.controller.command.*;
import by.rozmysl.booking.exception.CommandException;
import by.rozmysl.booking.exception.ServiceException;
import by.rozmysl.booking.model.service.ServiceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

import static by.rozmysl.booking.controller.command.RequestAttribute.*;

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
        try {
            if (ServiceFactory.getInstance().getUserService().activateUser(req.getPathInfo().substring(11))) {
                req.setAttribute(MESSAGE_ACTIVE, MESSAGE_ACTIVE_TRUE);
            } else {
                req.setAttribute(MESSAGE_ACTIVE, MESSAGE_ACTIVE_ERROR);
            }
        } catch (ServiceException e) {
            LOGGER.error(e.getMessage(), e);
            throw new CommandException(e.getMessage(), e);
        }
        return new PageGuide(PageAddress.LOGIN, TransferMethod.FORWARD);
    }
}