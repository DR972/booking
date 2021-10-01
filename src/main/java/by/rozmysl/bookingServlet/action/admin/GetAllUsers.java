package by.rozmysl.bookingServlet.action.admin;

import by.rozmysl.bookingServlet.action.Action;
import by.rozmysl.bookingServlet.dao.DaoFactory;
import by.rozmysl.bookingServlet.dao.user.UserDao;
import by.rozmysl.bookingServlet.db.ConnectionPool;
import by.rozmysl.bookingServlet.db.ConnectionSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;

/**
 * Provides service to initialize actions on the GetAllUsers.
 */
public class GetAllUsers implements Action {
    private static final Logger LOGGER = LoggerFactory.getLogger(GetAllUsers.class);

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
        final ConnectionSource con = ConnectionPool.getInstance().getConnectionFromPool();
        try {
            UserDao userDao = dao.userDao(con);
            if (req.getParameter("delete") != null && req.getParameter("delete").equals("delete")) {
                String username = req.getParameter("userId");
                if (!dao.bookingDao(con).findAllByUser(username).isEmpty()) {
                    req.setAttribute("errorDeleteUser", "Нельзя удалить пользователя, имеющего бронирования!");
                } else {
                    dao.roleDao(con).delete(username);
                    userDao.delete(username);
                    LOGGER.info("The user '" + username + "' was deleted by the admin " + req.getUserPrincipal().getName());
                }
            }
            int page = 0;
            int rows = 10;
            if (req.getParameter("rows") != null) rows = Integer.parseInt(req.getParameter("rows"));
            if (req.getParameter("page") != null) page = Integer.parseInt(req.getParameter("page"));
            req.setAttribute("rows", rows);
            req.setAttribute("page", page);
            req.setAttribute("countPages", userDao.countUsersPages(rows));
            req.setAttribute("allUsers", userDao.getAll(page, rows));
        } finally {
            ConnectionPool.getInstance().returnConnectionToPool(con);
        }
        return String.format("forward:%s", "/WEB-INF/views/admin/allUsers.jsp");
    }
}