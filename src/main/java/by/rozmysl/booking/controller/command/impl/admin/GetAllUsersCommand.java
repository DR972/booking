package by.rozmysl.booking.controller.command.impl.admin;

import by.rozmysl.booking.controller.command.*;
import by.rozmysl.booking.exception.CommandException;
import by.rozmysl.booking.exception.ServiceException;
import by.rozmysl.booking.model.service.ServiceFactory;
import by.rozmysl.booking.model.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

import static by.rozmysl.booking.controller.command.RequestAttribute.*;
import static by.rozmysl.booking.controller.command.RequestParameter.*;

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
     * @throws CommandException if the operation failed
     */
    @Override
    public PageGuide execute(HttpServletRequest req) throws CommandException {
        ServiceFactory service = ServiceFactory.getInstance();
        UserService userService = ServiceFactory.getInstance().getUserService();

        try {
            if (req.getParameter(CHANGE_ACCOUNT_LOCK) != null && req.getParameter(CHANGE_ACCOUNT_LOCK).equals(CHANGE_ACCOUNT_LOCK)) {
                userService.changeUserAccountLock(req.getParameter(USERNAME));
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
            req.setAttribute(COUNT_PAGES, userService.countNumberUsersPages(rows));
            req.setAttribute(ALL_USERS, userService.findAll(pageNumber, rows));
        } catch (ServiceException e) {
            LOGGER.error(e.getMessage(), e);
            throw new CommandException(e.getMessage(), e);
        }
        return new PageGuide(PageAddress.ALL_USERS, TransferMethod.FORWARD);
    }
}