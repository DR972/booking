package by.rozmysl.bookingServlet.security;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Defines the security configuration of the application.
 */
public final class SecurityConfig {

    public static final String ROLE_ADMIN = "ADMIN";
    public static final String ROLE_USER = "USER";

    private static final Map<String, List<String>> mapConfig = new HashMap<>();

    static {
        init();
    }

    /**
     * Initializes the configuration
     */
    private static void init() {
        List<String> urlPatternsUSER = new ArrayList<>();
        urlPatternsUSER.add("/user/*");
        mapConfig.put(ROLE_USER, urlPatternsUSER);
        List<String> urlPatternsADMIN = new ArrayList<>();
        urlPatternsADMIN.add("/admin/*");
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
