package by.rozmysl.booking.controller.security;

import java.security.Principal;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * An extension for the HTTPServletRequest that overrides the getUserPrincipal() and isUserInRole().
 * If the user or roles are null on this wrapper, the parent request is consulted.
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
        return roles == null ? this.realRequest.isUserInRole(role) : roles.contains(role);
    }

    /**
     * Gets the value of the username property
     *
     * @return a value of the username
     */
    @Override
    public Principal getUserPrincipal() {
        return this.username == null ? realRequest.getUserPrincipal() : (() -> username);
    }
}
