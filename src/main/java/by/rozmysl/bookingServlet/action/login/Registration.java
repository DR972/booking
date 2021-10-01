package by.rozmysl.bookingServlet.action.login;

import by.rozmysl.bookingServlet.action.Action;
import by.rozmysl.bookingServlet.dao.DaoFactory;
import by.rozmysl.bookingServlet.dao.user.UserDao;
import by.rozmysl.bookingServlet.db.ConnectionPool;
import by.rozmysl.bookingServlet.db.ConnectionSource;
import by.rozmysl.bookingServlet.entity.user.User;
import by.rozmysl.bookingServlet.entity.user.UserRole;
import by.rozmysl.bookingServlet.exception.BadCredentialsException;
import by.rozmysl.bookingServlet.validator.UserValidator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;

/**
 * Provides service to initialize actions on the Registration.
 */
public class Registration implements Action {
    private static final Logger LOGGER = LoggerFactory.getLogger(Registration.class);

    /**
     * Executes actions on request content.
     *
     * @param req request content
     * @return page to go
     * @throws SQLException       if there was an error accessing the database
     * @throws MessagingException if the message cannot be created
     */
    @Override
    public String execute(HttpServletRequest req) throws SQLException, MessagingException {
        if (req.getParameter("action") == null) return String.format("forward:%s", "/WEB-INF/views/anonymous/registration.jsp");
        DaoFactory dao = DaoFactory.getInstance();
        final ConnectionSource con = ConnectionPool.getInstance().getConnectionFromPool();
        try {
            UserDao userDao = dao.userDao(con);
            User user = new User(req.getParameter("username"), req.getParameter("lastname"), req.getParameter("firstname"),
                    req.getParameter("password"), req.getParameter("passwordConfirm"), req.getParameter("email"));
            try {
                new UserValidator().allValidate(user, userDao);
            } catch (BadCredentialsException e) {
                req.setAttribute("errorValidate", e.getMessage());
                ConnectionPool.getInstance().returnConnectionToPool(con);
                return String.format("forward:%s", "/WEB-INF/views/anonymous/registration.jsp");
            }
            userDao.save(user, req.getParameter("language"));
            dao.roleDao(con).save(new UserRole(user.getUsername(), "USER"));
            LOGGER.info("A new user has been registered - " + user.getUsername());
        } finally {
            ConnectionPool.getInstance().returnConnectionToPool(con);
        }
        return String.format("redirect:%s", "/anonymous/login");
    }
}
