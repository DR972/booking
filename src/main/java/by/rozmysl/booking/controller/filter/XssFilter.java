package by.rozmysl.booking.controller.filter;

import by.rozmysl.booking.controller.security.XssRequestWrapper;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Performs the duty of checking all requests from XSS attacks.
 */
@WebFilter(urlPatterns = {"/*"})
public class XssFilter implements Filter {

    @Override
    public void init(FilterConfig config) throws ServletException {
    }

    /**
     * Do filter.
     *
     * @param req  the Servlet request
     * @param resp the Servlet response
     * @param chain     the Filter chain
     * @throws IOException      Signals that an I/O exception has occurred.
     * @throws ServletException the servlet exception
     */
    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        chain.doFilter(new XssRequestWrapper((HttpServletRequest) req), resp);
    }

    @Override
    public void destroy() {
    }
}
