package by.rozmysl.bookingServlet.controller.command.impl.user;

import by.rozmysl.bookingServlet.controller.command.Command;
import by.rozmysl.bookingServlet.controller.command.PageAddress;
import by.rozmysl.bookingServlet.controller.command.PageGuide;
import by.rozmysl.bookingServlet.controller.command.TransferMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * Provides service to initialize actions on the ToUserPageCommand.
 */
public class ToUserPageCommand implements Command {

    /**
     * Executes actions on request content.
     *
     * @param req request content
     * @return page to go
     */
    @Override
    public PageGuide execute(HttpServletRequest req) {
        return new PageGuide(PageAddress.USER, TransferMethod.FORWARD);
    }
}
