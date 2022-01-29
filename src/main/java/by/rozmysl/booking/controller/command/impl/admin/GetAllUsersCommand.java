package by.rozmysl.booking.controller.command.impl.admin;

import by.rozmysl.booking.controller.command.*;
import by.rozmysl.booking.exception.CommandException;
import by.rozmysl.booking.exception.ServiceException;
import by.rozmysl.booking.model.service.ServiceProvider;
import by.rozmysl.booking.model.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

import static by.rozmysl.booking.controller.command.RequestAttribute.*;
import static by.rozmysl.booking.controller.command.RequestParameter.*;
import static by.rozmysl.booking.model.ModelTypeProvider.*;

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
        ServiceProvider service = ServiceProvider.getInstance();
        UserService userService = ServiceProvider.getInstance().getUserService();

        try {
            if (req.getParameter(CHANGE_ACCOUNT_LOCK) != null && req.getParameter(CHANGE_ACCOUNT_LOCK).equals(CHANGE_ACCOUNT_LOCK)) {
                userService.updateEntity(USER_CHANGE_ACCOUNT_LOCK, !Boolean.parseBoolean(req.getParameter(BANNED)), req.getParameter(USERNAME));
            }

            if (req.getParameter(CHANGE_ROLE_LIST) != null && req.getParameter(CHANGE_ROLE_LIST).equals(CHANGE_ROLE_LIST)) {
                userService.changeListUserRoles(req.getParameter(USERNAME));
            }

            if (req.getParameter(DELETE) != null && req.getParameter(DELETE).equals(DELETE)) {
                String username = req.getParameter(USERNAME);
                if (!service.getBookingService().findListEntities(BOOKING_FIND_ALL_BOOKINGS_BY_USER, username).isEmpty()) {
                    req.setAttribute(ERROR_DELETE_USER, ERROR_DELETE_USER_RU);
                } else {
                    userService.delete(username);
                    LOGGER.info("The user '" + username + "' was deleted by the admin " + req.getUserPrincipal().getName());
                }
            }

            int pageNumber = SelectingPageParameters.getPageNumber(req);
            int rows = SelectingPageParameters.getNumberRows(req);
            req.setAttribute(RequestAttribute.COUNT_PAGES, userService.countNumberEntityRows(USER_FIND_ROWS_COUNT, rows));
            req.setAttribute(ALL_USERS, userService.findListEntities(USER_FIND_ALL, rows, pageNumber, rows));
        } catch (ServiceException e) {
            LOGGER.error(e.getMessage(), e);
            throw new CommandException(e.getMessage(), e);
        }
        return new PageGuide(PageAddress.ALL_USERS, TransferMethod.FORWARD);
    }
}