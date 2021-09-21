package by.rozmysl.bookingServlet.controller;

import by.rozmysl.bookingServlet.action.Action;
import by.rozmysl.bookingServlet.action.admin.*;
import by.rozmysl.bookingServlet.action.login.*;
import by.rozmysl.bookingServlet.action.user.*;
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
import java.util.HashMap;
import java.util.Map;

/**
 * Provides service for working with standard requests  with the <b>actionMap</b> properties.
 */
@WebServlet(name = "MainController", urlPatterns = {"/admin/*", "/user/*", "/anonymous/*"})
public class MainController extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(MainController.class);
    private static final long serialVersionUID = 1L;
    private final Map<String, Action> actionMap = new HashMap<>();

    /**
     * Initializes all classes of actions
     */
    @Override
    public void init() {
        actionMap.put("login", new Login());
        actionMap.put("activation", new ActivationAccount());
        actionMap.put("logout", new Logout());
        actionMap.put("registration", new Registration());
        actionMap.put("bookingDetails", new BookingDetails());
        actionMap.put("booking", new BookingPage());
        actionMap.put("userBookings", new UserBookings());
        actionMap.put("allUsers", new AllUsers());
        actionMap.put("allBookings", new AllBookings());
        actionMap.put("addRoom", new newRoom());
        actionMap.put("allRooms", new AllRooms());
        actionMap.put("freeRooms", new FreeRooms());
        actionMap.put("changeRoomParameters", new RoomParameters());
        actionMap.put("changeServicesPrice", new ServicesPrice());
    }

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
        String actionKey = req.getParameter("action");
        if (req.getPathInfo().equals("/logout")) actionKey = "logout";
        if (actionKey == null && req.getPathInfo().startsWith("/activation")) actionKey = "activation";
        if (actionKey == null)
            getServletContext().getRequestDispatcher("/WEB-INF/views" + req.getRequestURI() + ".jsp").forward(req, resp);
        else {
            try {
                Action action = actionMap.get(actionKey);
                String view = action.execute(req);
                if (view.startsWith("redirect")) resp.sendRedirect(view.substring(9));
                else getServletContext().getRequestDispatcher(view.substring(8)).forward(req, resp);
            } catch (SQLException | MessagingException e) {
                logger.error(String.valueOf(e));
                throw new ServletException(e);
            }
        }
    }
}
