package by.rozmysl.bookingServlet.controller;

import by.rozmysl.bookingServlet.controller.command.*;
import by.rozmysl.bookingServlet.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
        Command command = CommandType.chooseCommandType(req.getPathInfo()).getAction();
        try {
            PageGuide pageGuide = command.execute(req);
            if (TransferMethod.FORWARD.equals(pageGuide.getTransferMethod())) {
                req.getRequestDispatcher(pageGuide.getPageAddress()).forward(req, resp);
            } else {
                resp.sendRedirect(pageGuide.getPageAddress());
            }
        } catch (ServiceException e) {
            LOGGER.error(String.valueOf(e));
            throw new ServletException(e);
        }
    }
}
