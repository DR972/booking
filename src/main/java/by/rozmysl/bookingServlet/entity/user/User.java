package by.rozmysl.bookingServlet.entity.user;

import java.util.*;

/**
 * It is used to store User objects with the <b>username</b>, <b>lastname</b>, <b>firstname</b>, <b>password</b>,
 * <b>passwordConfirm</b>, <b>active</b>, <b>email</b>, <b>activationCode</b>, <b>roles</b> properties
 */
public class User {
    private String username;
    private String lastname;
    private String firstname;
    private String password;
    private String passwordConfirm;
    private boolean active;
    private String email;
    private String activationCode;
    private Set<String> roles;

    /**
     * The constructor creates a new object Booking without parameters
     */
    public User() {
    }

    /**
     * The constructor creates a new object Booking with the <b>username</b> properties
     *
     * @param username id of the User
     */
    public User(String username) {
        this.username = username;
    }

    /**
     * The constructor creates a new object Booking with the <b>username</b>, <b>lastname</b>, <b>firstname</b>,
     * * <b>password</b>, <b>email</b> properties
     *
     * @param username  id of the User
     * @param lastname  lastname
     * @param firstname firstname
     * @param password  password
     * @param email     email
     */
    public User(String username, String lastname, String firstname, String password, String email) {
        this.username = username;
        this.lastname = lastname;
        this.firstname = firstname;
        this.password = password;
        this.email = email;
    }

    /**
     * The constructor creates a new object Booking with the <b>username</b>, <b>lastname</b>, <b>firstname</b>,
     * <b>password</b>, <b>passwordConfirm</b>, <b>email</b> properties
     *
     * @param username        id of the User
     * @param lastname        lastname
     * @param firstname       firstname
     * @param password        password
     * @param passwordConfirm passwordConfirm
     * @param email           email
     */
    public User(String username, String lastname, String firstname, String password, String passwordConfirm, String email) {
        this.username = username;
        this.lastname = lastname;
        this.firstname = firstname;
        this.password = password;
        this.passwordConfirm = passwordConfirm;
        this.email = email;
    }

    /**
     * The constructor creates a new object Booking with the <b>username</b>, <b>lastname</b>, <b>firstname</b>,
     * <b>password</b>, <b>active</b>, <b>email</b>, <b>activationCode</b>, <b>roles</b> properties
     *
     * @param username       id of the User
     * @param lastname       lastname
     * @param firstname      firstname
     * @param password       password
     * @param active         active
     * @param email          email
     * @param activationCode activationCode
     * @param role           role
     */
    public User(String username, String lastname, String firstname, String password, boolean active, String email, String activationCode, String role) {
        this.username = username;
        this.lastname = lastname;
        this.firstname = firstname;
        this.password = password;
        this.active = active;
        this.email = email;
        this.activationCode = activationCode;
        roles = new HashSet<>();
        roles.add(role);
    }

    /**
     * The constructor creates a new object Booking with the <b>username</b>, <b>lastname</b>, <b>firstname</b>,
     * <b>password</b>, <b>active</b>, <b>email</b>, <b>activationCode</b> properties
     *
     * @param username       id of the User
     * @param lastname       lastname
     * @param firstname      firstname
     * @param password       password
     * @param active         active
     * @param email          email
     * @param activationCode activationCode
     */
    public User(String username, String lastname, String firstname, String password, boolean active, String email, String activationCode) {
        this.username = username;
        this.lastname = lastname;
        this.firstname = firstname;
        this.password = password;
        this.active = active;
        this.email = email;
        this.activationCode = activationCode;
    }

    /**
     * Gets the value of the password property
     *
     * @return a value of the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Gets the value of the active property
     *
     * @return a value of the active
     */
    public boolean getActive() {
        return active;
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
     * Gets the value of the lastname property
     *
     * @return a value of the lastname
     */
    public String getLastname() {
        return lastname;
    }

    /**
     * The method sets the value of the lastname property
     *
     * @param lastname lastname
     */
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    /**
     * Gets the value of the firstname property
     *
     * @return a value of the firstname
     */
    public String getFirstname() {
        return firstname;
    }

    /**
     * The method sets the value of the firstname property
     *
     * @param firstname firstname
     */
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    /**
     * The method sets the value of the password property
     *
     * @param password password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets the value of the passwordConfirm property
     *
     * @return a value of the passwordConfirm
     */
    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    /**
     * The method sets the value of the passwordConfirm property
     *
     * @param passwordConfirm passwordConfirm
     */
    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    /**
     * The method sets the value of the active property
     *
     * @param active active
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * Gets the value of the email property
     *
     * @return a value of the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * The method sets the value of the email property
     *
     * @param email email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the value of the activationCode property
     *
     * @return a value of the activationCode
     */
    public String getActivationCode() {
        return activationCode;
    }

    /**
     * The method sets the value of the activationCode property
     *
     * @param activationCode activationCode
     */
    public void setActivationCode(String activationCode) {
        this.activationCode = activationCode;
    }

    /**
     * Gets the value of the roles property
     *
     * @return a value of the roles
     */
    public Set<String> getRoles() {
        return roles;
    }

    /**
     * The method sets the value of the roles property
     *
     * @param roles roles
     */
    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", User.class.getSimpleName() + "[", "]")
                .add("username='" + username + "'")
                .add("lastname='" + lastname + "'")
                .add("firstname='" + firstname + "'")
                .add("password='" + password + "'")
                .add("passwordConfirm='" + passwordConfirm + "'")
                .add("active=" + active)
                .add("email='" + email + "'")
                .add("activationCode='" + activationCode + "'")
                .add("roles=" + roles)
                .toString();
    }
}