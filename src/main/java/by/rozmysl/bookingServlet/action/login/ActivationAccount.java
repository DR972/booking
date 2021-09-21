package by.rozmysl.bookingServlet.action.login;

import by.rozmysl.bookingServlet.action.Action;
import by.rozmysl.bookingServlet.dao.DaoFactory;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;

/**
 * Provides service to initialize actions on the ActivationAccount.
 */
public class ActivationAccount implements Action {

    /**
     * Executes actions on request content.
     *
     * @param req request content
     * @return page to go
     * @throws SQLException if there was an error accessing the database
     */
    @Override
    public String execute(HttpServletRequest req) throws SQLException {
        System.out.println(req.getPathInfo().substring(16));
        if (new DaoFactory().userDao().activateUser(req.getPathInfo().substring(12)))
            req.setAttribute("messageActive", "message.activeTrue");
        else req.setAttribute("messageActive", "message.activeError");
        return String.format("forward:%s", "/WEB-INF/views/anonymous/login.jsp");
    }
}