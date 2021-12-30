package by.rozmysl.bookingServlet.controller.command.impl.admin;

import by.rozmysl.bookingServlet.controller.command.Command;
import by.rozmysl.bookingServlet.controller.command.PageAddress;
import by.rozmysl.bookingServlet.controller.command.PageGuide;
import by.rozmysl.bookingServlet.controller.command.TransferMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * Provides service to initialize actions on the ToAdminPageCommand.
 */
public class ToAdminPageCommand implements Command {

    /**
     * Executes actions on request content.
     *
     * @param req request content
     * @return page to go
     */
    @Override
    public PageGuide execute(HttpServletRequest req) {
        return new PageGuide(PageAddress.ADMIN, TransferMethod.FORWARD);
    }
}