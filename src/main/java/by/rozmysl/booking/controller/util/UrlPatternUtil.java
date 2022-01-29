package by.rozmysl.booking.controller.util;

import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;
import javax.servlet.http.HttpServletRequest;

import static by.rozmysl.booking.controller.command.PageAddress.MAIN;

/**
 * Provides a service for working with URLs.
 */
public final class UrlPatternUtil {
    private static final String PATH = "/*";

    /**
     * Private constructor without parameters.
     * Restrict instantiation of the class.
     */
    private UrlPatternUtil() {
    }

    /**
     * Maps the URL of the current request to the url mappings of the servlet.
     *
     * @param servletContext servletContext
     * @param urlPattern     urlPattern
     * @return verification result
     */
    private static boolean hasUrlPattern(ServletContext servletContext, String urlPattern) {
        Map<String, ? extends ServletRegistration> map = servletContext.getServletRegistrations();
        return map.keySet().stream().anyMatch(s -> map.get(s).getMappings().contains(urlPattern));
    }

    /**
     * Gets URLs
     *
     * @param req HttpServletRequest
     * @return url
     */
    public static String getUrlPattern(HttpServletRequest req) {
        ServletContext servletContext = req.getServletContext();
        String servletPath = req.getServletPath();

        if (req.getPathInfo() != null) {
            return servletPath + PATH;
        }
        if (hasUrlPattern(servletContext, servletPath)) {
            return servletPath;
        }

        int i = servletPath.lastIndexOf('.');
        if (i != -1) {
            String urlPattern = "*." + servletPath.substring(i + 1);
            if (hasUrlPattern(servletContext, urlPattern)) return urlPattern;
        }
        return MAIN;
    }
}
