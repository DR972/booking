package by.rozmysl.bookingServlet.controller.util;

import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;
import javax.servlet.http.HttpServletRequest;

/**
 * Provides a service for working with URLs.
 */
public final class UrlPatternUtils {

    private UrlPatternUtils() {
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

        if (req.getPathInfo() != null) return servletPath + "/*";
        if (hasUrlPattern(servletContext, servletPath)) return servletPath;

        int i = servletPath.lastIndexOf('.');
        if (i != -1) {
            String urlPattern = "*." + servletPath.substring(i + 1);
            if (hasUrlPattern(servletContext, urlPattern)) return urlPattern;
        }
        return "/";
    }
}
