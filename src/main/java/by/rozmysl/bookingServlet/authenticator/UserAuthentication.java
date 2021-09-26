package by.rozmysl.bookingServlet.authenticator;

import by.rozmysl.bookingServlet.entity.user.User;
import by.rozmysl.bookingServlet.exception.BadCredentialsException;
import by.rozmysl.bookingServlet.security.PasswordAuthentication;

import javax.servlet.http.HttpServletRequest;

/**
 * Provides user authentication.
 */
public class UserAuthentication {

    /**
     * Executes all user authentication
     *
     * @param user user
     * @param req  request content
     */
    public void allAuthenticate(User user, HttpServletRequest req) throws BadCredentialsException {
        if (user == null || !PasswordAuthentication.check(req.getParameter("password"), user.getPassword())) {
            throw new BadCredentialsException("message.loginError");
        }
        if (!user.getActive()) throw new BadCredentialsException("message.activeFalse");
    }
}
