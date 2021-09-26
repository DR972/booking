package by.rozmysl.bookingServlet.action.admin;

import by.rozmysl.bookingServlet.action.Action;

import javax.servlet.http.HttpServletRequest;

/**
 * Provides service to initialize actions on the AdminPage.
 */
public class AdminPage implements Action {

    /**
     * Executes actions on request content.
     *
     * @param req request content
     * @return page to go
     */
    @Override
    public String execute(HttpServletRequest req) {
        return String.format("forward:%s", "/WEB-INF/views/admin/admin.jsp");
    }
}