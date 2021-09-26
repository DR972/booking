package by.rozmysl.bookingServlet.action.admin;

import by.rozmysl.bookingServlet.action.Action;
import by.rozmysl.bookingServlet.dao.DaoFactory;
import by.rozmysl.bookingServlet.db.ConnectionSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;

/**
 * Provides service to initialize actions on the OperationsWitAllUsers.
 */
public class OperationsWitAllUsers implements Action {
    private static final Logger LOGGER = LoggerFactory.getLogger(OperationsWitAllUsers.class);

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
        final ConnectionSource con = new ConnectionSource();
        req.setAttribute("userDao", dao.userDao(con));
        if (req.getParameter("delete") != null && req.getParameter("delete").equals("delete")) {
            String username = req.getParameter("userId");
            if (!dao.bookingDao(con).findAllByUser(username).isEmpty()) {
                req.setAttribute("errorDeleteUser", "Нельзя удалить пользователя, имеющего бронирования!");
            } else {
                dao.roleDao(con).delete(username);
                dao.userDao(con).delete(username);
                LOGGER.info("The user '" + username + "' was deleted by the admin " + req.getUserPrincipal().getName());
            }
        }
        if (req.getParameter("rows") != null) req.setAttribute("rows", Integer.parseInt(req.getParameter("rows")));
        if (req.getParameter("page") != null) req.setAttribute("page", Integer.parseInt(req.getParameter("page")));
        return String.format("forward:%s", "/WEB-INF/views/admin/allUsers.jsp");
    }
}