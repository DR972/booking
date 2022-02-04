package by.rozmysl.booking.controller.filter;

import by.rozmysl.booking.controller.command.CommandType;
import by.rozmysl.booking.controller.command.PageAddress;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

/**
 * Performs the duty of checking all requests before allowing access to execute commands.
 */
@WebFilter(urlPatterns = {"/*"})
public class CommandFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    /**
     * Do filter.
     *
     * @param servletRequest  the Servlet request
     * @param servletResponse the Servlet response
     * @param chain           the Filter chain
     * @throws IOException      Signals that an I/O exception has occurred.
     * @throws ServletException the servlet exception
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        if (request.getRequestURI().equals("/")) {
            request.getServletContext().getRequestDispatcher(PageAddress.MAIN).forward(request, response);
            return;
        }

        if (request.getPathInfo() == null || !Arrays.asList(CommandType.values()).toString().contains(CommandType.convert(request.getPathInfo()))) {
            request.getServletContext().getRequestDispatcher(PageAddress.PAGE_DOES_NOT_EXIST).forward(request, response);
            return;
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}
