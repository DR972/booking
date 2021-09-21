package by.rozmysl.bookingServlet.action.user;

import by.rozmysl.bookingServlet.action.Action;
import by.rozmysl.bookingServlet.dao.DaoFactory;
import by.rozmysl.bookingServlet.entity.user.Booking;
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
        String resultValidate = new BookingValidator().allValidate(req);
        if (!resultValidate.equals("Ok")) {
            req.setAttribute("errorValidate", resultValidate);
            return String.format("forward:%s", "/WEB-INF/views/user/bookingDetails.jsp");
        }
        DaoFactory dao = new DaoFactory();
        Booking booking = new Booking(Integer.parseInt(req.getParameter("persons")), LocalDate.parse(req.getParameter("arrival")),
                LocalDate.parse(req.getParameter("arrival")).plusDays(Integer.parseInt(req.getParameter("days"))),
                Integer.parseInt(req.getParameter("days")), dao.foodDao().getById(req.getParameter("food")),
                dao.servicesDao().getById(req.getParameter("service")));
        if (dao.roomDao().findAllTypesFreeRoomsBetweenTwoDatesWithGreaterOrEqualSleeps
                (booking.getArrival(), booking.getDeparture(), booking.getPersons()).size() == 0) {
            req.setAttribute("noAvailable", "message.noAvailable");
        }
        req.setAttribute("booking", booking);
        return String.format("forward:%s", "/WEB-INF/views/user/booking.jsp");
    }
}