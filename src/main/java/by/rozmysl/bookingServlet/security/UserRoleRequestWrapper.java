package by.rozmysl.bookingServlet.security;

import java.security.Principal;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * Provides a service for determining the user's name and role in the session with the <b>username</b>, <b>roles</b>,
 * <b>realRequest</b> properties.
 */
public class UserRoleRequestWrapper extends HttpServletRequestWrapper {

    private final String username;
    private final Set<String> roles;
    private final HttpServletRequest realRequest;

    /**
     * The constructor creates a new object UserRoleRequestWrapper with the <b>username</b>, <b>roles</b>,
     * <b>realRequest</b> properties.
     *
     * @param username id of the User
     * @param roles    roles
     * @param request  realRequest
     */
    public UserRoleRequestWrapper(String username, Set<String> roles, HttpServletRequest request) {
        super(request);
        this.username = username;
        this.roles = roles;
        this.realRequest = request;
    }

    /**
     * Checks whether this role corresponds to the user
     *
     * @param role role
     * @return verification result
     */
    @Override
    public boolean isUserInRole(String role) {
        if (roles == null) return this.realRequest.isUserInRole(role);
        return roles.contains(role);
    }

    /**
     * Gets the value of the username property
     *
     * @return a value of the username
     */
    @Override
    public Principal getUserPrincipal() {
        if (this.username == null) return realRequest.getUserPrincipal();
        return () -> username;
    }
}
