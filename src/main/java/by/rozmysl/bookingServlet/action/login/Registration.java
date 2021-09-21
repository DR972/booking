package by.rozmysl.bookingServlet.action.login;

import by.rozmysl.bookingServlet.action.Action;
import by.rozmysl.bookingServlet.dao.DaoFactory;
import by.rozmysl.bookingServlet.entity.user.User;
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
    private static final Logger logger = LoggerFactory.getLogger(Registration.class);

    /**
     * Executes actions on request content.
     *
     * @param req request content
     * @return page to go
     * @throws SQLException if there was an error accessing the database
     * @throws MessagingException if the message cannot be created
     */
    @Override
    public String execute(HttpServletRequest req) throws SQLException, MessagingException {
        User user = new User(req.getParameter("username"), req.getParameter("lastname"), req.getParameter("firstname"),
                req.getParameter("password"), req.getParameter("passwordConfirm"), req.getParameter("email"));
        String resultValidate = new UserValidator().allValidate(user);
        if (!resultValidate.equals("Ok")) {
            req.setAttribute("errorValidate", resultValidate);
            return String.format("forward:%s", "/WEB-INF/views/anonymous/registration.jsp");
        }
        new DaoFactory().userDao().save(user, req.getParameter("language"));
        logger.info("A new user has been registered - " + user.getUsername());
        return String.format("redirect:%s", "/anonymous/login");
    }
}
