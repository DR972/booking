package by.rozmysl.booking.model.entity.user;

import by.rozmysl.booking.model.entity.Entity;

import java.util.*;

/**
 * It is used to store User objects with the <b>lastname</b>, <b>firstname</b>, <b>password</b>,
 * <b>passwordConfirm</b>, <b>active</b>, <b>email</b>, <b>activationCode</b>, <b>roles</b> properties
 */
public class User extends Entity<String> {
    private String lastname;
    private String firstname;
    private String password;
    private String passwordConfirm;
    private boolean active;
    private String email;
    private String activationCode;
    private boolean banned = false;
    private Set<String> roles;

    /**
     * The constructor creates a new object User without parameters
     */
    public User() {
    }

    /**
     * The constructor creates a new object User with the <b>username</b> properties
     *
     * @param username id of the User
     */
    public User(String username) {
        super(username);
    }

    /**
     * The constructor creates a new object User with the <b>username</b>, <b>lastname</b>, <b>firstname</b>,
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
        super(username);
        this.lastname = lastname;
        this.firstname = firstname;
        this.password = password;
        this.passwordConfirm = passwordConfirm;
        this.email = email;
    }

    /**
     * The constructor creates a new object Booking with the <b>username</b>, <b>lastname</b>, <b>firstname</b>,
     * <b>password</b>, <b>active</b>, <b>email</b>, <b>activationCode</b>,, <b>isBanned</b>, <b>roles</b> properties
     *
     * @param username       id of the User
     * @param lastname       lastname
     * @param firstname      firstname
     * @param password       password
     * @param active         active
     * @param email          email
     * @param activationCode activationCode
     */
    public User(String username, String lastname, String firstname, String password, boolean active, String email,
                String activationCode, boolean banned, Set<String> roles) {
        super(username);
        this.lastname = lastname;
        this.firstname = firstname;
        this.password = password;
        this.active = active;
        this.email = email;
        this.activationCode = activationCode;
        this.banned = banned;
        this.roles = roles;
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
     * Gets the value of the isActive property
     *
     * @return <code>true</code> if user is active;
     * <code>false</code> otherwise
     */
    public boolean isActive() {
        return active;
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
     * Gets the value of the isBanned property
     *
     * @return <code>true</code> if user is banned;
     * <code>false</code> otherwise
     */
    public boolean isBanned() {
        return banned;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        if (!super.equals(o)) return false;

        User user = (User) o;

        if (active != user.active) return false;
        if (banned != user.banned) return false;
        if (!Objects.equals(lastname, user.lastname)) return false;
        if (!Objects.equals(firstname, user.firstname)) return false;
        if (!Objects.equals(password, user.password)) return false;
        if (!Objects.equals(passwordConfirm, user.passwordConfirm)) return false;
        if (!Objects.equals(email, user.email)) return false;
        if (!Objects.equals(activationCode, user.activationCode)) return false;
        return Objects.equals(roles, user.roles);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (lastname != null ? lastname.hashCode() : 0);
        result = 31 * result + (firstname != null ? firstname.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (passwordConfirm != null ? passwordConfirm.hashCode() : 0);
        result = 31 * result + (active ? 1 : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (activationCode != null ? activationCode.hashCode() : 0);
        result = 31 * result + (banned ? 1 : 0);
        result = 31 * result + (roles != null ? roles.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", User.class.getSimpleName() + "[", "]")
                .add("username=" + id)
                .add("lastname='" + lastname + "'")
                .add("firstname='" + firstname + "'")
                .add("password='" + password + "'")
                .add("passwordConfirm='" + passwordConfirm + "'")
                .add("active=" + active)
                .add("email='" + email + "'")
                .add("activationCode='" + activationCode + "'")
                .add("banned=" + banned)
                .add("roles=" + roles)
                .toString();
    }
}