package by.rozmysl.booking.controller.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.Arrays;

/**
 * An extension for the HTTPServletRequest that overrides the getParameterValues(), getParameter() and getHeader().
 */
public class XssRequestWrapper extends HttpServletRequestWrapper {
    private static final String REGEX_PATTERN = "<.*?>";

    /**
     * Instantiates a new XssRequestWrapper.
     *
     * @param request the request
     */
    public XssRequestWrapper(HttpServletRequest request) {
        super(request);
    }

    @Override
    public String[] getParameterValues(String parameter) {
        String[] values = super.getParameterValues(parameter);
        return values == null ? null : Arrays.stream(values).map(this::deleteStripXss).toArray(String[]::new);
    }

    @Override
    public String getParameter(String parameter) {
        String value = super.getParameter(parameter);
        return value == null ? null : this.deleteStripXss(value);
    }

    @Override
    public String getHeader(String name) {
        String value = super.getHeader(name);
        return this.deleteStripXss(value);
    }

    private String deleteStripXss(String value) {
        return value == null ? null : value.replaceAll(REGEX_PATTERN, "");
    }
}
