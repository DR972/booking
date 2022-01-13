package by.rozmysl.bookingServlet.controller.command.impl.login;

import by.rozmysl.bookingServlet.controller.command.*;
import by.rozmysl.bookingServlet.exception.CommandException;
import by.rozmysl.bookingServlet.exception.ServiceException;
import by.rozmysl.bookingServlet.model.service.ServiceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

import static by.rozmysl.bookingServlet.controller.command.RequestAttribute.*;

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