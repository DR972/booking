package by.rozmysl.bookingServlet.action.user;

import by.rozmysl.bookingServlet.action.Action;

import javax.servlet.http.HttpServletRequest;

/**
 * Provides service to initialize actions on the UserPage.
 */
public class UserPage implements Action {

    /**
     * Executes actions on request content.
     *
     * @param req request content
     * @return page to go
     */
    @Override
    public String execute(HttpServletRequest req) {
        return String.format("forward:%s", "/WEB-INF/views/user/user.jsp");
    }
}
