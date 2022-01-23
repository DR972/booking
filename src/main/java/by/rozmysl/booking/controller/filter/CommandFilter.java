package by.rozmysl.booking.controller.filter;

import by.rozmysl.booking.controller.command.CommandType;
import by.rozmysl.booking.controller.command.PageAddress;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
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
     * @param req  the Servlet request
     * @param resp the Servlet response
     * @param chain     the Filter chain
     * @throws IOException      Signals that an I/O exception has occurred.
     * @throws ServletException the servlet exception
     */
    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        if (request.getRequestURI().equals("/")) {
            request.getServletContext().getRequestDispatcher(PageAddress.MAIN).forward(request, resp);
            return;
        }
        if (request.getPathInfo() == null || !Arrays.asList(CommandType.values()).toString().contains(CommandType.convert(request.getPathInfo()))) {
            request.getServletContext().getRequestDispatcher(PageAddress.PAGE_DOES_NOT_EXIST).forward(request, resp);
            return;
        }
        chain.doFilter(request, resp);
    }

    @Override
    public void destroy() {
    }
}
