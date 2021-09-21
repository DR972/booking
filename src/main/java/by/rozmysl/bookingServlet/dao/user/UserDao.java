package by.rozmysl.bookingServlet.dao.user;

import by.rozmysl.bookingServlet.dao.Dao;
import by.rozmysl.bookingServlet.entity.user.User;

import javax.mail.MessagingException;
import java.sql.SQLException;

/**
 * Provides the base model DAO interface for `USER` table.
 */
public interface UserDao extends Dao<User, String> {
    /**
     * Saves the User in the `USER` table
     *
     * @param user     User
     * @param language User's language
     * @return User object
     * @throws SQLException       if there was an error accessing the database
     * @throws MessagingException if the message cannot be created
     */
    User save(User user, String language) throws SQLException, MessagingException;

    /**
     * Counts the number of pages in the 'USER` table
     *
     * @param rows number of rows per page
     * @return number of pages
     * @throws SQLException if there was an error accessing the database
     */
    int countUsersPages(int rows) throws SQLException;

    /**
     * Tries to activate the User. If successful, it saves the result in the `USER` table
     *
     * @param code User's activation code
     * @return verification result
     * @throws SQLException if there was an error accessing the database
     */
    boolean activateUser(String code) throws SQLException;
}
