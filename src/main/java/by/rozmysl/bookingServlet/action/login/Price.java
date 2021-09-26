package by.rozmysl.bookingServlet.action.login;

import by.rozmysl.bookingServlet.action.Action;
import by.rozmysl.bookingServlet.dao.DaoFactory;
import by.rozmysl.bookingServlet.db.ConnectionSource;

import javax.servlet.http.HttpServletRequest;

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
    public String execute(HttpServletRequest req) {
        DaoFactory dao = new DaoFactory();
        final ConnectionSource con = new ConnectionSource();
        req.setAttribute("roomDao", dao.roomDao(con));
        req.setAttribute("foodDao", dao.foodDao(con));
        req.setAttribute("servicesDao", dao.servicesDao(con));
        return String.format("forward:%s", "/WEB-INF/views/anonymous/price.jsp");
    }
}