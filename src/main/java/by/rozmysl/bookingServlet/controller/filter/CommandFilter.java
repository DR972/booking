package by.rozmysl.bookingServlet.controller.filter;

import by.rozmysl.bookingServlet.controller.command.CommandType;
import by.rozmysl.bookingServlet.controller.command.PageAddress;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;

@WebFilter(urlPatterns = {"/*"})
public class CommandFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        if (request.getPathInfo() != null) {
            String commandType = CommandType.convert(request.getPathInfo());
            if (!Arrays.asList(CommandType.values()).toString().contains(commandType)) {
                request.getServletContext().getRequestDispatcher(PageAddress.PAGE_DOES_NOT_EXIST).forward(request, resp);
                return;
            }
        }
        chain.doFilter(request, resp);
    }

    @Override
    public void destroy() {
    }
}
