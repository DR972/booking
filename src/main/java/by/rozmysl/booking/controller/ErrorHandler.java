package by.rozmysl.booking.controller;

import by.rozmysl.booking.controller.command.PageAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static by.rozmysl.booking.controller.command.RequestAttribute.*;
import static by.rozmysl.booking.controller.command.RequestParameter.*;

/**
 * Provides service for working with standard errors.
 */
@WebServlet(urlPatterns = "/ErrorHandler")
public class ErrorHandler extends HttpServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(ErrorHandler.class);
    public static final String UNKNOWN = "Unknown";
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processError(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processError(req, resp);
    }

    /**
     * Process GET or POST request. Invoke error page.
     *
     * @param req  request content
     * @param resp response content
     * @throws ServletException if action cannot execute
     * @throws IOException if action cannot execute
     */
    private void processError(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        Throwable throwable = (Throwable) req.getAttribute(JAVAX_SERVLET_ERROR_EXCEPTION);
        Integer statusCode = (Integer) req.getAttribute(JAVAX_SERVLET_ERROR_STATUS_CODE);
        String servletName = (String) req.getAttribute(JAVAX_SERVLET_ERROR_SERVLET_NAME);

        if (servletName == null) {
            servletName = UNKNOWN;
        }
        String requestUri = (String) req.getAttribute(JAVAX_SERVLET_ERROR_REQUEST_URI);
        req.setAttribute(CODE, statusCode);
        req.setAttribute(URI, requestUri);

        if (statusCode != 500) {
            LOGGER.error("Status: " + statusCode + ", uri: " + requestUri);
            getServletContext().getRequestDispatcher(PageAddress.ERROR).forward(req, resp);
        } else {
            req.setAttribute(SERVLET_NAME, servletName);
            req.setAttribute(NAME, throwable.getClass().getName());
            req.setAttribute(MESSAGE, throwable.getMessage());
            LOGGER.error("Status: " + statusCode + ", Servlet: " + servletName + ", Exception: "
                    + throwable.getClass().getName() + ", uri: " + requestUri + ", message: " + throwable.getMessage());
            getServletContext().getRequestDispatcher(PageAddress.ERROR_500).forward(req, resp);
        }
    }
}
