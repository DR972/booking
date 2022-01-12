package by.rozmysl.bookingServlet.controller.security;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Defines the security configuration of the application.
 */
public final class SecurityConfig {
    private static final String ROLE_ADMIN = "ADMIN";
    private static final String ROLE_USER = "USER";
    private static final String PATTERNS_ADMIN = "/admin/*";
    private static final String PATTERNS_USER = "/user/*";
    private static final Map<String, List<String>> mapConfig = new HashMap<>();

    static {
        init();
    }

    /**
     * Private constructor without parameters.
     * Restrict instantiation of the class.
     */
    private SecurityConfig() {
    }

    /**
     * Initializes the configuration
     */
    private static void init() {
        List<String> urlPatternsUSER = new ArrayList<>();
        urlPatternsUSER.add(PATTERNS_USER);
        mapConfig.put(ROLE_USER, urlPatternsUSER);
        List<String> urlPatternsADMIN = new ArrayList<>();
        urlPatternsADMIN.add(PATTERNS_ADMIN);
        mapConfig.put(ROLE_ADMIN, urlPatternsADMIN);
    }

    /**
     * Gets all application roles
     *
     * @return all roles
     */
    public static Set<String> getAllAppRoles() {
        return mapConfig.keySet();
    }

    /**
     * Gets url patterns for role
     *
     * @param role role
     * @return all url patterns for role
     */
    public static List<String> getUrlPatternsForRole(String role) {
        return mapConfig.get(role);
    }
}
