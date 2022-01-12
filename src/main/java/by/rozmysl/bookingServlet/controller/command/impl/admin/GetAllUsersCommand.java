package by.rozmysl.bookingServlet.controller.command.impl.admin;

import by.rozmysl.bookingServlet.controller.command.*;
import by.rozmysl.bookingServlet.exception.ServiceException;
import by.rozmysl.bookingServlet.model.service.ServiceFactory;
import by.rozmysl.bookingServlet.model.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

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
        UserService userService = ServiceFactory.getInstance().getUserService();

        if (req.getParameter(CHANGE_ACCOUNT_LOCK) != null && req.getParameter(CHANGE_ACCOUNT_LOCK).equals(CHANGE_ACCOUNT_LOCK)) {
            userService.changeAccountLock(req.getParameter(USERNAME));
        }

        if (req.getParameter(CHANGE_ROLE_LIST) != null && req.getParameter(CHANGE_ROLE_LIST).equals(CHANGE_ROLE_LIST)) {
            userService.changeListUserRoles(req.getParameter(USERNAME));
        }

        if (req.getParameter(DELETE) != null && req.getParameter(DELETE).equals(DELETE)) {
            String username = req.getParameter(USERNAME);
            if (!service.getBookingService().findAllBookingsByUser(username).isEmpty()) {
                req.setAttribute(ERROR_DELETE_USER, ERROR_DELETE_USER_RU);
            } else {
                userService.delete(username);
                LOGGER.info("The user '" + username + "' was deleted by the admin " + req.getUserPrincipal().getName());
            }
        }

        int pageNumber = PageSelection.getPageNumber(req);
        int rows = PageSelection.getRows(req);
        req.setAttribute(COUNT_PAGES, userService.countUsersPages(rows));
        req.setAttribute(ALL_USERS, userService.findAll(pageNumber, rows));
        return new PageGuide(PageAddress.ALL_USERS, TransferMethod.FORWARD);
    }
}