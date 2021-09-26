package by.rozmysl.bookingServlet.action.admin;

import by.rozmysl.bookingServlet.action.Action;
import by.rozmysl.bookingServlet.dao.DaoFactory;
import by.rozmysl.bookingServlet.dao.user.BookingDao;
import by.rozmysl.bookingServlet.db.ConnectionSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Provides service to initialize actions on the OperationsWithAllBookings.
 */
public class OperationsWithAllBookings implements Action {
    private static final Logger LOGGER = LoggerFactory.getLogger(OperationsWithAllBookings.class);

    /**
     * Executes actions on request content.
     *
     * @param req request content
     * @return page to go
     * @throws SQLException       if there was an error accessing the database
     * @throws MessagingException if the message cannot be created
     * @throws IOException        if the letter cannot be created
     */
    @Override
    public String execute(HttpServletRequest req) throws SQLException, MessagingException, IOException {
        DaoFactory dao = DaoFactory.getInstance();
        BookingDao bookingDao = dao.bookingDao(new ConnectionSource());
        req.setAttribute("bookingDao", bookingDao);
        req.setAttribute("roomDao", dao.roomDao(new ConnectionSource()));
        if (req.getParameter("delete") != null && req.getParameter("delete").equals("delete")) {
            bookingDao.delete(Long.parseLong(req.getParameter("bookingId")));
            LOGGER.info("Booking # " + req.getParameter("bookingId") + " was deleted by admin " + req.getUserPrincipal().getName());
        }
        if (req.getParameter("changeRoom") != null && req.getParameter("changeRoom").equals("changeRoom")) {
            bookingDao.changeRoom(Long.parseLong(req.getParameter("bookingId")), Integer.parseInt(req.getParameter("roomId")), dao);
            LOGGER.info("In booking # " + req.getParameter("bookingId") + ", the room number was changed to " +
                    req.getParameter("roomId") + " by the admin " + req.getUserPrincipal().getName());
        }
        if (req.getParameter("changeStatus") != null && req.getParameter("changeStatus").equals("changeStatus")) {
            bookingDao.changeStatusBooking(Long.parseLong(req.getParameter("bookingId")), req.getParameter("status"), dao);
            LOGGER.info("In booking # " + req.getParameter("bookingId") + ", the status was changed to '" +
                    req.getParameter("status") + "'  by the admin " + req.getUserPrincipal().getName());
        }
        if (req.getParameter("rows") != null) req.setAttribute("rows", Integer.parseInt(req.getParameter("rows")));
        if (req.getParameter("page") != null) req.setAttribute("page", Integer.parseInt(req.getParameter("page")));
        return String.format("forward:%s", "/WEB-INF/views/admin/allBookings.jsp");
    }
}