package by.rozmysl.bookingServlet.action.admin;

import by.rozmysl.bookingServlet.action.Action;
import by.rozmysl.bookingServlet.dao.DaoFactory;
import by.rozmysl.bookingServlet.db.ConnectionPool;
import by.rozmysl.bookingServlet.db.ConnectionSource;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.time.LocalDate;

/**
 * Provides service to initialize actions on the GetFreeRooms.
 */
public class GetFreeRooms implements Action {

    /**
     * Executes actions on request content.
     *
     * @param req request content
     * @return page to go
     */
    @Override
    public String execute(HttpServletRequest req) throws SQLException {
        final ConnectionSource con = ConnectionPool.getInstance().getConnectionFromPool();
        if (req.getParameter("from") != null && req.getParameter("to") != null) {
            if (LocalDate.parse(req.getParameter("from")).isBefore(LocalDate.parse(req.getParameter("to")))) {
                req.setAttribute("freeRooms", DaoFactory.getInstance().roomDao(con).findAllFreeRoomsBetweenTwoDates(
                        LocalDate.parse(req.getParameter("from")), LocalDate.parse(req.getParameter("to"))));
            } else req.setAttribute("dateError", "Неправильно указаны даты!");
        }
        ConnectionPool.getInstance().returnConnectionToPool(con);
        return String.format("forward:%s", "/WEB-INF/views/admin/freeRooms.jsp");
    }
}