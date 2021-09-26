package by.rozmysl.bookingServlet.action.user;

import by.rozmysl.bookingServlet.action.Action;
import by.rozmysl.bookingServlet.dao.DaoFactory;
import by.rozmysl.bookingServlet.dao.hotel.RoomDao;
import by.rozmysl.bookingServlet.db.ConnectionSource;
import by.rozmysl.bookingServlet.entity.hotel.Room;
import by.rozmysl.bookingServlet.entity.user.Booking;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

/**
 * Provides service to initialize actions on the BookingPage.
 */
public class BookingPage implements Action {
    private static final Logger LOGGER = LoggerFactory.getLogger(BookingPage.class);

    /**
     * Executes actions on request content.
     *
     * @param req request content
     * @return page to go
     * @throws SQLException if there was an error accessing the database
     */
    @Override
    public String execute(HttpServletRequest req) throws SQLException {
        DaoFactory dao = DaoFactory.getInstance();
        final ConnectionSource con = new ConnectionSource();
        RoomDao roomDao = dao.roomDao(con);
        Booking booking = new Booking(Integer.parseInt(req.getParameter("persons")), LocalDate.parse(req.getParameter("arrival")),
                LocalDate.parse(req.getParameter("arrival")).plusDays(Integer.parseInt(req.getParameter("days"))),
                Integer.parseInt(req.getParameter("days")), dao.foodDao(con).getById(req.getParameter("food")),
                dao.servicesDao(con).getById(req.getParameter("service")));
        Room room = roomDao.getById(Integer.parseInt(req.getParameter("roomId")));
        List<Room> suitableRooms = roomDao.findAllFreeRoomsBetweenTwoDatesByTypesAndSleeps(booking.getArrival(),
                booking.getDeparture(), room.getType(), room.getSleeps());
        if (suitableRooms.size() == 0) {
            req.setAttribute("booking", booking);
            req.setAttribute("missed", "message.missed");
            if (roomDao.findAllTypesFreeRoomsBetweenTwoDatesWithGreaterOrEqualSleeps
                    (booking.getArrival(), booking.getDeparture(), booking.getPersons()).size() == 0) {
                req.setAttribute("noAvailable", "message.noAvailable");
            }
            return String.format("forward:%s", "/WEB-INF/views/user/booking.jsp");
        } else {
            booking.setRoom(suitableRooms.get(0));
            booking.setUser(dao.userDao(con).getById(req.getUserPrincipal().getName()));
            req.setAttribute("booking", dao.bookingDao(con).save(booking));
            LOGGER.info("The booking was made by the user - " + booking.getUser().getUsername());
            return String.format("forward:%s", "/WEB-INF/views/user/confirmation.jsp");
        }
    }
}