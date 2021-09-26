package by.rozmysl.bookingServlet.action.admin;

import by.rozmysl.bookingServlet.action.Action;
import by.rozmysl.bookingServlet.dao.DaoFactory;
import by.rozmysl.bookingServlet.dao.hotel.RoomDao;
import by.rozmysl.bookingServlet.db.ConnectionSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.sql.SQLException;

/**
 * Provides service to initialize actions on the OperationsWitAllRooms.
 */
public class OperationsWitAllRooms implements Action {
    private static final Logger LOGGER = LoggerFactory.getLogger(OperationsWitAllRooms.class);

    /**
     * Executes actions on request content.
     *
     * @param req request content
     * @return page to go
     * @throws SQLException if there was an error accessing the database
     */
    @Override
    public String execute(HttpServletRequest req) throws SQLException {
        RoomDao roomDao = DaoFactory.getInstance().roomDao(new ConnectionSource());
        req.setAttribute("roomDao", roomDao);
        if (req.getParameter("changePrice") != null && req.getParameter("changePrice").equals("changePrice")) {
            roomDao.updatePrice(roomDao.getById(Integer.parseInt(req.getParameter("roomId"))), new BigDecimal(req.getParameter("price")));
            LOGGER.info("For room # " + req.getParameter("roomId") + ", the price was changed to '" +
                    req.getParameter("price") + "' by admin " + req.getUserPrincipal().getName());
        }
        if (req.getParameter("delete") != null && req.getParameter("delete").equals("delete")) {
            roomDao.delete(Integer.parseInt(req.getParameter("roomId")));
            LOGGER.info("Room # " + req.getParameter("roomId") + " was deleted by admin " + req.getUserPrincipal().getName());
        }
        return String.format("forward:%s", "/WEB-INF/views/admin/allRooms.jsp");
    }
}