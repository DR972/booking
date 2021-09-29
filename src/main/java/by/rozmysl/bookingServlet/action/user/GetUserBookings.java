package by.rozmysl.bookingServlet.action.user;

import by.rozmysl.bookingServlet.action.Action;
import by.rozmysl.bookingServlet.dao.DaoFactory;
import by.rozmysl.bookingServlet.dao.user.BookingDao;
import by.rozmysl.bookingServlet.db.ConnectionPool;
import by.rozmysl.bookingServlet.db.ConnectionSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Provides service to initialize actions on the GetUserBookings.
 */
public class GetUserBookings implements Action {
    private static final Logger LOGGER = LoggerFactory.getLogger(GetUserBookings.class);

    /**
     * Executes actions on request content.
     *
     * @param req request content
     * @return page to go
     * @throws SQLException if there was an error accessing the database
     */
    @Override
    public String execute(HttpServletRequest req) throws SQLException, IOException {
        final ConnectionSource con = ConnectionPool.getInstance().getConnectionFromPool();
        BookingDao bookingDao = DaoFactory.getInstance().bookingDao(con);
        if (req.getParameter("delete") != null && req.getParameter("delete").equals("delete")) {
            bookingDao.delete(Long.parseLong(req.getParameter("bookingId")));
            LOGGER.info("Booking # " + req.getParameter("bookingId") + " was canceled by the user.");
        }
        req.setAttribute("booking", bookingDao.findAllByUser(req.getUserPrincipal().getName()));
        ConnectionPool.getInstance().returnConnectionToPool(con);
        return String.format("forward:%s", "/WEB-INF/views/user/userBookings.jsp");
    }
}
