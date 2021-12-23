package by.rozmysl.bookingServlet.action.login;

import by.rozmysl.bookingServlet.action.Action;
import by.rozmysl.bookingServlet.dao.DaoFactory;
import by.rozmysl.bookingServlet.db.ConnectionPool;
import by.rozmysl.bookingServlet.db.ConnectionSource;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;

/**
 * Provides service to initialize actions on the Price.
 */
public class Price implements Action {
    /**
     * Executes actions on request content.
     *
     * @param req request content
     * @return page to go
     */
    @Override
    public String execute(HttpServletRequest req) throws SQLException {
        DaoFactory dao = DaoFactory.getInstance();
        final ConnectionSource con = ConnectionPool.getInstance().getConnectionFromPool();
        try {
            req.setAttribute("allRooms", dao.roomDao(con).findAllRoomsByTypesAndSleeps());
            req.setAttribute("allFood", dao.foodDao(con).findAll(0, 0));
            req.setAttribute("allServices", dao.servicesDao(con).findAll(0, 0));
        } finally {
            ConnectionPool.getInstance().returnConnectionToPool(con);
        }
        return String.format("forward:%s", "/WEB-INF/views/anonymous/price.jsp");
    }
}