package by.rozmysl.bookingServlet.entity.user;

import java.util.StringJoiner;

/**
 * It is used to store UserRole objects with the <b>username</b>, <b>role</b> properties
 */
public class UserRole {
    private String username;
    private String role;

    /**
     * The constructor creates a new object UserRole with the <b>username</b>, <b>role</b> properties
     *
     * @param username role id
     * @param role     role
     */
    public UserRole(String username, String role) {
        this.username = username;
        this.role = role;
    }

    /**
     * Gets the value of the username property
     *
     * @return a value of the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * The method sets the value of the username property
     *
     * @param username username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets the value of the role property
     *
     * @return a value of the role
     */
    public String getRole() {
        return role;
    }

    /**
     * The method sets the value of the role property
     *
     * @param role role
     */
    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", UserRole.class.getSimpleName() + "[", "]")
                .add("username='" + username + "'")
                .add("role='" + role + "'")
                .toString();
    }
}