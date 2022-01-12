package by.rozmysl.bookingServlet.controller.command.impl.login;

import by.rozmysl.bookingServlet.controller.command.*;
import by.rozmysl.bookingServlet.exception.ServiceException;
import by.rozmysl.bookingServlet.model.service.ServiceFactory;

import javax.servlet.http.HttpServletRequest;

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
        try {
            if (ServiceFactory.getInstance().getUserService().activateUser(req.getPathInfo().substring(11))) {
                req.setAttribute(MESSAGE_ACTIVE, MESSAGE_ACTIVE_TRUE);
            } else {
                req.setAttribute(MESSAGE_ACTIVE, MESSAGE_ACTIVE_ERROR);
            }
        } catch (ServiceException e) {
            req.setAttribute(MESSAGE_ACTIVE, MESSAGE_ACTIVE_ERROR);
            throw new ServiceException("'ActivateUser' query has been failed", e);
        }
        return new PageGuide(PageAddress.LOGIN, TransferMethod.FORWARD);
    }
}