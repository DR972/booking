package by.rozmysl.bookingServlet.action.login;

import by.rozmysl.bookingServlet.authenticator.UserAuthentication;
import by.rozmysl.bookingServlet.action.Action;
import by.rozmysl.bookingServlet.dao.DaoFactory;
import by.rozmysl.bookingServlet.db.ConnectionPool;
import by.rozmysl.bookingServlet.db.ConnectionSource;
import by.rozmysl.bookingServlet.entity.user.User;
import by.rozmysl.bookingServlet.exception.BadCredentialsException;
import by.rozmysl.bookingServlet.utils.AppUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;

/**
 * Provides service to initialize actions on the Login.
 */
public class Login implements Action {
    private static final Logger LOGGER = LoggerFactory.getLogger(Login.class);

    /**
     * Executes actions on request content.
     *
     * @param req request content
     * @return page to go
     * @throws SQLException if there was an error accessing the database
     */
    @Override
    public String execute(HttpServletRequest req) throws SQLException {
        final ConnectionSource con = ConnectionPool.getInstance().getConnectionFromPool();
        if (req.getParameter("action") == null) return String.format("forward:%s", "/WEB-INF/views/anonymous/login.jsp");
        User user = DaoFactory.getInstance().userDao(con).getById(req.getParameter("username"));
        ConnectionPool.getInstance().returnConnectionToPool(con);
        try {
            new UserAuthentication().allAuthenticate(user, req);
        } catch (BadCredentialsException e) {
            req.setAttribute("loginError", e.getMessage());
            return String.format("forward:%s", "/WEB-INF/views/anonymous/login.jsp");
        }
        AppUtils.saveLoggedUser(req.getSession(), user);
        LOGGER.info("The user '" + user.getUsername() + "' is logged in.");
        int redirectId = -1;
        if (!req.getParameter("redirectId").isEmpty()) redirectId = Integer.parseInt(req.getParameter("redirectId"));
        String requestUri = AppUtils.getRedirectAfterLoginUrl(redirectId);
        if (requestUri != null) return String.format("redirect:%s", requestUri);
        else {
            if (user.getRoles().contains("ADMIN")) return String.format("redirect:%s", "/admin/admin");
            else return String.format("redirect:%s", "/user/user");
        }
    }
}
