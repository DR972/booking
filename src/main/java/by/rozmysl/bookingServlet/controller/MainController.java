package by.rozmysl.bookingServlet.controller;

import by.rozmysl.bookingServlet.action.Action;
import by.rozmysl.bookingServlet.action.ActionType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Provides service for working with standard requests  with the <b>actionMap</b> properties.
 */
@WebServlet(name = "MainController", urlPatterns = {"/admin/*", "/user/*", "/anonymous/*"})
public class MainController extends HttpServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(MainController.class);
    private static final long serialVersionUID = 1L;

    /**
     * Process GET or POST request. Invoke action class.
     *
     * @param req  request content
     * @param resp response content
     * @throws ServletException if action cannot execute
     * @throws IOException      if action cannot execute
     */
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String actionType = req.getPathInfo().replace("/", "");
        if (actionType.startsWith("activation")) actionType = "activation";

        Action action = ActionType.valueOf(actionType.replaceAll("([a-z])([A-Z]+)", "$1_$2").toUpperCase()).getAction();
        if (action == null) {
            getServletContext().getRequestDispatcher("/WEB-INF/views/error/pageDoesNotExist.jsp").forward(req, resp);
            return;
        }
        try {
            String view = action.execute(req);
            if (view.startsWith("redirect")) resp.sendRedirect(view.substring(9));
            else getServletContext().getRequestDispatcher(view.substring(8)).forward(req, resp);
        } catch (SQLException | MessagingException e) {
            LOGGER.error(String.valueOf(e));
            throw new ServletException(e);
        }
    }
}
