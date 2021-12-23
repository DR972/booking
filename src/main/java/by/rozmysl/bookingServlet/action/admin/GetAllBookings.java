package by.rozmysl.bookingServlet.action.admin;

import by.rozmysl.bookingServlet.action.Action;
import by.rozmysl.bookingServlet.dao.DaoFactory;
import by.rozmysl.bookingServlet.dao.hotel.RoomDao;
import by.rozmysl.bookingServlet.dao.user.BookingDao;
import by.rozmysl.bookingServlet.db.ConnectionPool;
import by.rozmysl.bookingServlet.db.ConnectionSource;
import by.rozmysl.bookingServlet.entity.hotel.Room;
import by.rozmysl.bookingServlet.entity.hotel.StatusReservation;
import by.rozmysl.bookingServlet.entity.user.Booking;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Provides service to initialize actions on the GetAllBookings.
 */
public class GetAllBookings implements Action {
    private static final Logger LOGGER = LoggerFactory.getLogger(GetAllBookings.class);

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
        final ConnectionSource con = ConnectionPool.getInstance().getConnectionFromPool();
        try {
            BookingDao bookingDao = dao.bookingDao(con);
            RoomDao roomDao = dao.roomDao(con);
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
                bookingDao.changeBookingStatus(Long.parseLong(req.getParameter("bookingId")), req.getParameter("status"), dao);
                LOGGER.info("In booking # " + req.getParameter("bookingId") + ", the status was changed to '" +
                        req.getParameter("status") + "'  by the admin " + req.getUserPrincipal().getName());
            }
            int page = 0;
            int rows = 10;
            if (req.getParameter("rows") != null) rows = Integer.parseInt(req.getParameter("rows"));
            if (req.getParameter("page") != null) page = Integer.parseInt(req.getParameter("page"));
            req.setAttribute("rows", rows);
            req.setAttribute("page", page);
            req.setAttribute("countPages", bookingDao.countBookingsPages(rows));
            List<Booking> allBookings = bookingDao.findAll(page, rows);
            Map<Long, List<Room>> availableRooms = new HashMap<>();
            for (Booking booking : allBookings) {
                availableRooms.put(booking.getNumber(), roomDao.findAllFreeRoomsBetweenTwoDatesWithGreaterOrEqualSleeps
                        (booking.getArrival(), booking.getDeparture(), booking.getPersons()));
            }
            req.setAttribute("roomDao", dao.roomDao(con));
            req.setAttribute("availableRooms", availableRooms);
            req.setAttribute("allBookings", allBookings);
            req.setAttribute("status", StatusReservation.values());
        } finally {
            ConnectionPool.getInstance().returnConnectionToPool(con);
        }
        return String.format("forward:%s", "/WEB-INF/views/admin/allBookings.jsp");
    }
}