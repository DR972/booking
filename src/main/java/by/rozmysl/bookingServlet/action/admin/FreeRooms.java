package by.rozmysl.bookingServlet.action.admin;

import by.rozmysl.bookingServlet.action.Action;
import by.rozmysl.bookingServlet.dao.DaoFactory;
import by.rozmysl.bookingServlet.db.ConnectionSource;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;

/**
 * Provides service to initialize actions on the FreeRooms.
 */
public class FreeRooms implements Action {

    /**
     * Executes actions on request content.
     *
     * @param req request content
     * @return page to go
     */
    @Override
    public String execute(HttpServletRequest req) {
        req.setAttribute("roomDao", DaoFactory.getInstance().roomDao(new ConnectionSource()));
        if (req.getParameter("from") != null) {
            if (LocalDate.parse(req.getParameter("from")).isBefore(LocalDate.parse(req.getParameter("to")))) {
                req.setAttribute("from", LocalDate.parse(req.getParameter("from")));
                req.setAttribute("to", LocalDate.parse(req.getParameter("to")));
            } else req.setAttribute("dateError", "Неправильно указаны даты!");
        }
        return String.format("forward:%s", "/WEB-INF/views/admin/freeRooms.jsp");
    }
}