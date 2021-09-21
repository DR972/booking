package by.rozmysl.bookingServlet.authenticator;

import by.rozmysl.bookingServlet.entity.user.User;
import by.rozmysl.bookingServlet.security.PasswordAuthentication;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * Provides user authentication.
 */
public class UserAuthentication {

    /**
     * Executes all user authentication
     *
     * @param user user
     * @param req  request content
     * @return authentication result
     */
    public String allAuthenticate(User user, HttpServletRequest req) {
        if (user == null || !PasswordAuthentication.check(req.getParameter("password"), user.getPassword())) {
            return "message.loginError";
        }
        if (!Objects.requireNonNull(user).getActive()) return "message.activeFalse";
        return "Ok";
    }
}
