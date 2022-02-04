package by.rozmysl.booking.controller.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.IOException;
import java.util.Arrays;

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
     * @param servletRequest  the Servlet request
     * @param servletResponse the Servlet response
     * @param chain           the Filter chain
     * @throws IOException      Signals that an I/O exception has occurred.
     * @throws ServletException the servlet exception
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {

        chain.doFilter(new HttpServletRequestWrapper((HttpServletRequest) servletRequest) {
            private static final String REGEX_PATTERN = "<.*?>";

            /**
             * Gets the value of the Header property
             *
             * @param parameter Request parameter
             * @return an array of String objects containing all the values the given request parameter has
             */
            @Override
            public String[] getParameterValues(String parameter) {
                String[] values = super.getParameterValues(parameter);
                return values == null ? null : Arrays.stream(values).map(this::deleteStripXss).toArray(String[]::new);
            }

            /**
             * Gets the value of the Header property
             *
             * @param parameter Request parameter
             * @return a value of Request parameter
             */
            @Override
            public String getParameter(String parameter) {
                String value = super.getParameter(parameter);
                return value == null ? null : this.deleteStripXss(value);
            }

            /**
             * Gets the value of the Header property
             *
             * @param name Header name
             * @return a value of the specified request header as a String
             */
            @Override
            public String getHeader(String name) {
                String value = super.getHeader(name);
                return this.deleteStripXss(value);
            }

            /**
             * Deletes the inserted script.
             *
             * @param value Request value
             * @return a value after deleting the script as a string
             */
            private String deleteStripXss(String value) {
                return value == null ? null : value.replaceAll(REGEX_PATTERN, "");
            }
        }, servletResponse);
    }

    @Override
    public void destroy() {
    }
}
