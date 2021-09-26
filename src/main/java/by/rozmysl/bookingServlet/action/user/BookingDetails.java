package by.rozmysl.bookingServlet.action.user;

import by.rozmysl.bookingServlet.action.Action;
import by.rozmysl.bookingServlet.dao.DaoFactory;
import by.rozmysl.bookingServlet.db.ConnectionSource;
import by.rozmysl.bookingServlet.entity.user.Booking;
import by.rozmysl.bookingServlet.exception.BadCredentialsException;
import by.rozmysl.bookingServlet.validator.BookingValidator;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.time.LocalDate;

/**
 * Provides service to initialize actions on the BookingDetails.
 */
public class BookingDetails implements Action {

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
        req.setAttribute("foodDao", dao.foodDao(con));
        req.setAttribute("servicesDao", dao.servicesDao(con));
        if (req.getParameter("action") == null) return String.format("forward:%s", "/WEB-INF/views/user/bookingDetails.jsp");

        try {
            new BookingValidator().allValidate(req);
        } catch (BadCredentialsException e) {
            req.setAttribute("errorValidate", e.getMessage());
            return String.format("forward:%s", "/WEB-INF/views/user/bookingDetails.jsp");
        }
        Booking booking = new Booking(Integer.parseInt(req.getParameter("persons")), LocalDate.parse(req.getParameter("arrival")),
                LocalDate.parse(req.getParameter("arrival")).plusDays(Integer.parseInt(req.getParameter("days"))),
                Integer.parseInt(req.getParameter("days")), dao.foodDao(con).getById(req.getParameter("food")),
                dao.servicesDao(con).getById(req.getParameter("service")));
        if (dao.roomDao(con).findAllTypesFreeRoomsBetweenTwoDatesWithGreaterOrEqualSleeps
                (booking.getArrival(), booking.getDeparture(), booking.getPersons()).size() == 0) {
            req.setAttribute("noAvailable", "message.noAvailable");
        }
        req.setAttribute("booking", booking);
        req.setAttribute("roomDao", dao.roomDao(con));
        return String.format("forward:%s", "/WEB-INF/views/user/booking.jsp");
    }
}