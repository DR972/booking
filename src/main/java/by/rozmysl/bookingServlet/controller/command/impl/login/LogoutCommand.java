package by.rozmysl.bookingServlet.controller.command.impl.login;

import by.rozmysl.bookingServlet.controller.command.Command;

import by.rozmysl.bookingServlet.controller.command.PageAddress;
import by.rozmysl.bookingServlet.controller.command.PageGuide;
import by.rozmysl.bookingServlet.controller.command.TransferMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.servlet.http.HttpServletRequest;

/**
 * Provides service to initialize actions on the LogoutCommand.
 */
public class LogoutCommand implements Command {
    private static final Logger LOGGER = LoggerFactory.getLogger(LogoutCommand.class);

    /**
     * Executes actions on request content.
     *
     * @param req request content
     * @return page to go
     */
    @Override
    public PageGuide execute(HttpServletRequest req) {
        LOGGER.info("The user '" + req.getUserPrincipal().getName() + "' is logged out.");
        req.getSession().invalidate();
        return new PageGuide(PageAddress.MAIN, TransferMethod.REDIRECT);
    }
}
