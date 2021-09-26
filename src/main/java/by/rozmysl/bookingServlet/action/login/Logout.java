package by.rozmysl.bookingServlet.action.login;

import by.rozmysl.bookingServlet.action.Action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.servlet.http.HttpServletRequest;

/**
 * Provides service to initialize actions on the Logout.
 */
public class Logout implements Action {
    private static final Logger LOGGER = LoggerFactory.getLogger(Logout.class);

    /**
     * Executes actions on request content.
     *
     * @param req request content
     * @return page to go
     */
    @Override
    public String execute(HttpServletRequest req) {
        LOGGER.info("The user '" + req.getUserPrincipal().getName() + "' is logged out.");
        req.getSession().invalidate();
        return String.format("redirect:%s", "/");
    }
}
