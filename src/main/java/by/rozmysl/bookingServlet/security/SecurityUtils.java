package by.rozmysl.bookingServlet.security;

import by.rozmysl.bookingServlet.utils.UrlPatternUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * Checks the user's rights to access the page.
 */
public class SecurityUtils {

    /**
     * Checks whether this 'request' requires a login or not.
     *
     * @param req HttpServletRequest
     * @return verification result
     */
    public static boolean isSecurityPage(HttpServletRequest req) {
        return SecurityConfig.getAllAppRoles().stream()
                .anyMatch(r -> SecurityConfig.getUrlPatternsForRole(r).contains(UrlPatternUtils.getUrlPattern(req)));
    }

    /**
     * Checks whether the given 'request' has a suitable role or not.
     *
     * @param req HttpServletRequest
     * @return verification result
     */
    public static boolean hasPermission(HttpServletRequest req) {
        return SecurityConfig.getAllAppRoles().stream().filter(req::isUserInRole)
                .anyMatch(r -> SecurityConfig.getUrlPatternsForRole(r).contains(UrlPatternUtils.getUrlPattern(req)));
    }
}
