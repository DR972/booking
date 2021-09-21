package by.rozmysl.bookingServlet.action.user;

import by.rozmysl.bookingServlet.action.Action;
import by.rozmysl.bookingServlet.dao.DaoFactory;
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
    private static final Logger logger = LoggerFactory.getLogger(BookingPage.class);

    /**
     * Executes actions on request content.
     *
     * @param req request content
     * @return page to go
     * @throws SQLException if there was an error accessing the database
     */
    @Override
    public String execute(HttpServletRequest req) throws SQLException {
        DaoFactory dao = new DaoFactory();
        Booking booking = new Booking(Integer.parseInt(req.getParameter("persons")), LocalDate.parse(req.getParameter("arrival")),
                LocalDate.parse(req.getParameter("arrival")).plusDays(Integer.parseInt(req.getParameter("days"))),
                Integer.parseInt(req.getParameter("days")), dao.foodDao().getById(req.getParameter("food")),
                dao.servicesDao().getById(req.getParameter("service")));
        Room room = dao.roomDao().getById(Integer.parseInt(req.getParameter("roomId")));
        List<Room> suitableRooms = dao.roomDao().findAllFreeRoomsBetweenTwoDatesByTypesAndSleeps(booking.getArrival(),
                booking.getDeparture(), room.getType(), room.getSleeps());
        if (suitableRooms.size() == 0) {
            req.setAttribute("booking", booking);
            req.setAttribute("missed", "message.missed");
            if (dao.roomDao().findAllTypesFreeRoomsBetweenTwoDatesWithGreaterOrEqualSleeps
                    (booking.getArrival(), booking.getDeparture(), booking.getPersons()).size() == 0) {
                req.setAttribute("noAvailable", "message.noAvailable");
            }
            return String.format("forward:%s", "/WEB-INF/views/user/booking.jsp");
        } else {
            booking.setRoom(suitableRooms.get(0));
            booking.setUser(dao.userDao().getById(req.getUserPrincipal().getName()));
            req.setAttribute("booking", dao.bookingDao().save(booking));
            logger.info("The booking was made by the user - " + booking.getUser().getUsername());
            return String.format("forward:%s", "/WEB-INF/views/user/confirmation.jsp");
        }
    }
}