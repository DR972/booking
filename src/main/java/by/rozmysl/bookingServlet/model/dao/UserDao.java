package by.rozmysl.bookingServlet.model.dao;

import by.rozmysl.bookingServlet.exception.DaoException;
import by.rozmysl.bookingServlet.model.entity.user.User;

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
     * @throws DaoException       if there was an error accessing the database
     */
    User save(User user, String language) throws DaoException;

    /**
     * Counts the number of pages in the 'USER` table
     *
     * @param rows number of rows per page
     * @return number of pages
     * @throws DaoException if there was an error accessing the database
     */
    int countUsersPages(int rows) throws DaoException;

    /**
     * Tries to activate the User. If successful, it saves the result in the `USER` table
     *
     * @param code User's activation code
     * @return verification result
     * @throws DaoException if there was an error accessing the database
     */
    boolean activateUser(String code, User user) throws DaoException;
    User findUserByActivationCode(String code) throws DaoException;
}