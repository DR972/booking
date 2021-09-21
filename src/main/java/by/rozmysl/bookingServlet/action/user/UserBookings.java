package by.rozmysl.bookingServlet.action.user;

import by.rozmysl.bookingServlet.action.Action;
import by.rozmysl.bookingServlet.dao.DaoFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;

/**
 * Provides service to initialize actions on the UserBookings.
 */
public class UserBookings implements Action {
    private static final Logger logger = LoggerFactory.getLogger(UserBookings.class);

    /**
     * Executes actions on request content.
     *
     * @param req request content
     * @return page to go
     * @throws SQLException if there was an error accessing the database
     */
    @Override
    public String execute(HttpServletRequest req) throws SQLException {
        if (req.getParameter("delete") != null && req.getParameter("delete").equals("delete")) {
            new DaoFactory().bookingDao().delete(Long.parseLong(req.getParameter("bookingId")));
            logger.info("Booking # " + req.getParameter("bookingId") + " was canceled by the user.");
        }
        return String.format("forward:%s", "/WEB-INF/views/user/userBookings.jsp");
    }
}
