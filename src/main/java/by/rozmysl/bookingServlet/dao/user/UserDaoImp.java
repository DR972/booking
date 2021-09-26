package by.rozmysl.bookingServlet.dao.user;

import by.rozmysl.bookingServlet.db.ConnectionSource;
import by.rozmysl.bookingServlet.entity.user.User;
import by.rozmysl.bookingServlet.mail.Letter;
import by.rozmysl.bookingServlet.mail.MailSender;
import by.rozmysl.bookingServlet.security.PasswordAuthentication;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.mail.MessagingException;
import java.sql.*;
import java.util.*;

/**
 * Provides the base model implementation for `USER` table DAO with the <b>ConnectionSource</b> properties.
 */
public class UserDaoImp implements UserDao {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserDaoImp.class);
    private final ConnectionSource con;

    /**
     * The constructor creates a new object UserDaoImp with the <b>con</b> property
     */
    public UserDaoImp(ConnectionSource con) {
        this.con = con;
    }

    /**
     * Counts the number of pages in the 'USER` table
     *
     * @param rows number of rows per page
     * @return number of pages
     * @throws SQLException if there was an error accessing the database
     */
    @Override
    public int countUsersPages(int rows) throws SQLException {
        return (int) Math.ceil((float) con.countRows("select COUNT(*) as count from USER") / rows);
    }

    /**
     * Searches for the User in the `USER` table by id
     *
     * @param username User's id
     * @return User object
     * @throws SQLException if there was an error accessing the database
     */
    @Override
    public User getById(String username) throws SQLException {
        List<User> users = getResultSet("select USER.*, ROLE.NAME from USER left join USER_ROLE on USERNAME = USER " +
                " left join ROLE on ROLE = NAME where USERNAME = '" + username + "'");
        return users.size() != 0 ? users.get(0) : null;
    }

    /**
     * Searches for all Users in the `USER` table
     *
     * @param page number of the page to return
     * @param rows number of rows per page
     * @return list of User objects
     * @throws SQLException if there was an error accessing the database
     */
    @Override
    public List<User> getAll(int page, int rows) throws SQLException {
        return getResultSet("select USER.*, ROLE.NAME from USER left join USER_ROLE on USERNAME = USER " +
                "left join ROLE on ROLE = NAME order by UPPER (USERNAME) OFFSET " + rows + "*" + page +
                " ROWS FETCH NEXT " + rows + " ROWS ONLY");
    }

    /**
     * Saves the User in the `USER` table
     *
     * @param user User
     * @return User object
     * @throws SQLException if there was an error accessing the database
     */
    @Override
    public User save(User user) throws SQLException {
        return user;
    }

    /**
     * Saves the User in the `USER` table
     *
     * @param user     User
     * @param language User's language
     * @return User object
     * @throws SQLException       if there was an error accessing the database
     * @throws MessagingException if the message cannot be created
     */
    @Override
    public User save(User user, String language) throws SQLException, MessagingException {
        user.setActivationCode(UUID.randomUUID().toString());
        new MailSender().sendMail(user.getEmail(), "Activation code", new Letter().createMessage(user, language));
        String sql = "insert into USER(USERNAME, LASTNAME, FIRSTNAME, EMAIL, PASSWORD, ACTIVE, ACTIVATIONCODE) VALUES('"
                + user.getUsername() + "','" + user.getLastname() + "','" + user.getFirstname()
                + "','" + user.getEmail() + "','" + PasswordAuthentication.getSaltedHash(user.getPassword()) + "','" +
                user.getActive() + "','" + user.getActivationCode() + "')";
        con.update(sql);
        return user;
    }

    /**
     * Deletes the User in the `USER` table
     *
     * @param username id of the User
     * @throws SQLException if there was an error accessing the database
     */
    @Override
    public void delete(String username) throws SQLException {
        con.update("delete from USER where USERNAME = '" + username + "'");
    }

    /**
     * Tries to activate the User. If successful, it saves the result in the `USER` table
     *
     * @param code User's activation code
     * @return verification result
     * @throws SQLException if there was an error accessing the database
     */
    @Override
    public boolean activateUser(String code) throws SQLException {
        User user = findUserByActivationCode(code);
        if (user == null) return false;
        con.update("update USER set ACTIVE = 'true', ACTIVATIONCODE = 'null' where USERNAME = '" + user.getUsername() + "'");
        LOGGER.info("The user '" + user.getUsername() + "' is activated.");
        return true;
    }

    /**
     * Get a list of query results from the 'USER' table
     *
     * @param sql the wording of the request to the database
     * @return list of User objects
     * @throws SQLException if there was an error accessing the database
     */
    private List<User> getResultSet(String sql) throws SQLException {
        List<User> users = new ArrayList<>();
        con.get(sql).forEach(d -> {
            if (users.stream().noneMatch(u -> u.getUsername().equals(d.get("USERNAME")))) {
                users.add(new User(d.get("USERNAME"), d.get("LASTNAME"), d.get("FIRSTNAME"), d.get("PASSWORD"),
                        Boolean.parseBoolean(d.get("ACTIVE")), d.get("EMAIL"), d.get("ACTIVATIONCODE"), d.get("NAME")));
            } else
                users.stream().filter(u -> u.getUsername().equals(d.get("USERNAME"))).findFirst().get().getRoles().add(d.get("NAME"));
        });
        return users;
    }

    /**
     * Searches for the User in the `USER` table by activation code
     *
     * @param code User's activation code
     * @return User object
     * @throws SQLException if there was an error accessing the database
     */
    private User findUserByActivationCode(String code) throws SQLException {
        List<User> users = getResultSet("select USER.*, ROLE.NAME as role from USER left join USER_ROLE on USERNAME = USER " +
                " left join ROLE on ROLE = NAME where ACTIVATIONCODE = '" + code + "'");
        return users.size() != 0 ? users.get(0) : null;
    }
}
