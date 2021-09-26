package by.rozmysl.bookingServlet.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Provides service for working with standard errors.
 */
@WebServlet(urlPatterns = "/ErrorHandler")
public class ErrorHandler extends HttpServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(ErrorHandler.class);
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
        Throwable throwable = (Throwable) req.getAttribute("javax.servlet.error.exception");
        Integer statusCode = (Integer) req.getAttribute("javax.servlet.error.status_code");
        String servletName = (String) req.getAttribute("javax.servlet.error.servlet_name");
        if (servletName == null) servletName = "Unknown";
        String requestUri = (String) req.getAttribute("javax.servlet.error.request_uri");
        req.setAttribute("code", statusCode);
        req.setAttribute("uri", requestUri);
        if (statusCode != 500) {
            LOGGER.error("Status: " + statusCode + ", uri: " + requestUri);
            getServletContext().getRequestDispatcher("/WEB-INF/views/error/error.jsp").forward(req, resp);
        } else {
            req.setAttribute("servletName", servletName);
            req.setAttribute("name", throwable.getClass().getName());
            req.setAttribute("message", throwable.getMessage());
            LOGGER.error("Status: " + statusCode + ", Servlet: " + servletName + ", Exception: "
                    + throwable.getClass().getName() + ", uri: " + requestUri + ", message: " + throwable.getMessage());
            getServletContext().getRequestDispatcher("/WEB-INF/views/error/error500.jsp").forward(req, resp);
        }
    }
}
