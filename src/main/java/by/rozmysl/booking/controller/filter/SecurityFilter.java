package by.rozmysl.booking.controller.filter;

import by.rozmysl.booking.controller.security.SecurityUtil;
import by.rozmysl.booking.model.entity.user.User;
import by.rozmysl.booking.controller.util.AppUtil;
import by.rozmysl.booking.controller.security.UserRoleRequestWrapper;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static by.rozmysl.booking.controller.command.PageAddress.ACCESS_DENIED;


/**
 * Performs the duty of checking all requests before allowing access to protected pages.
 */
@WebFilter(urlPatterns = {"/*"})
public class SecurityFilter implements Filter {
    private static final String LOGIN = "/login";
    private static final String PAGE_REDIRECT = "/anonymous/login?redirectId=";

    @Override
    public void init(FilterConfig filterConfig) {
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
        HttpServletResponse response = (HttpServletResponse) resp;

        User loggedUser = AppUtil.getLoggedUser(request.getSession());
        if (request.getServletPath().equals(LOGIN) && loggedUser == null) {
            chain.doFilter(request, response);
            return;
        }

        HttpServletRequest wrapRequest = request;
        if (loggedUser != null) {
            wrapRequest = new UserRoleRequestWrapper(loggedUser.getId(), loggedUser.getRoles(), request);
        }

        if (SecurityUtil.isSecurityPage(request)) {
            if (loggedUser == null) {
                int redirectId = AppUtil.saveRedirectAfterLoginUrl(request.getRequestURI());
                response.sendRedirect(wrapRequest.getContextPath() + PAGE_REDIRECT + redirectId);
                return;
            }
            if (!SecurityUtil.hasPermission(wrapRequest)) {
                request.getServletContext().getRequestDispatcher(ACCESS_DENIED).forward(request, response);
                return;
            }
        }
        chain.doFilter(wrapRequest, response);
    }

    @Override
    public void destroy() {
    }
}
