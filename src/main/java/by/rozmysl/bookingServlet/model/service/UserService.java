package by.rozmysl.bookingServlet.model.service;

import by.rozmysl.bookingServlet.exception.ServiceException;
import by.rozmysl.bookingServlet.model.entity.user.User;

import java.util.List;

/**
 * Provides logic for working with data sent to the `User` table DAO.
 */
public interface UserService {

    /**
     * Provides logic for searching for a User object by id
     *
     * @param username id of the User object
     * @return User object
     * @throws ServiceException if the operation failed
     */
    User findById(String username) throws ServiceException;

    /**
     * Provides logic for searching for all User objects.
     *
     * @param pageNumber number of the page to return
     * @param rows       number of rows per page
     * @return list of User objects
     * @throws ServiceException if the operation failed
     */
    List<User> findAll(int pageNumber, int rows) throws ServiceException;

    /**
     * Provides logic for saving the User in the `User` table and saving the UserRole in the `UserRole` table
     *
     * @param user     User
     * @param language User language
     * @param service  ServiceFactory
     * @throws ServiceException if the operation failed
     */
    void save(User user, String language, ServiceFactory service) throws ServiceException;

    /**
     * Provides logic for deleting the User in the `User` table and deleting the UserRole in the `UserRole` table
     *
     * @param username User
     * @throws ServiceException if the operation failed
     */
    void delete(String username) throws ServiceException;

    /**
     * Provides logic for counting the number of pages of all User objects.
     *
     * @param rows number of rows per page
     * @return number of pages
     * @throws ServiceException if the operation failed
     */
    int countNumberUsersPages(int rows) throws ServiceException;

    /**
     * Provides User validation.
     *
     * @param user User user
     * @return validation result
     * @throws ServiceException if the operation failed
     */
    String validateUser(User user) throws ServiceException;

    /**
     * Provides User activation.
     *
     * @param code Activation code
     * @return activation result
     * @throws ServiceException if the operation failed
     */
    boolean activateUser(String code) throws ServiceException;

    /**
     * Provides logic for searching for a User object by activation code
     *
     * @param code Activation code
     * @return User object
     * @throws ServiceException if the operation failed
     */
    User findUserByActivationCode(String code) throws ServiceException;

    /**
     * Provides User authentication.
     *
     * @param user     User
     * @param password password
     * @return authentication result
     */
    String authenticateUser(User user, String password);

    /**
     * Provides logic for changing the list UserRoles.
     *
     * @param username id of the User
     * @throws ServiceException if the operation failed
     */
    void changeListUserRoles(String username) throws ServiceException;

    /**
     * Provides logic for changing the user account lock.
     *
     * @param username id of the User
     * @throws ServiceException if the operation failed
     */
    void changeUserAccountLock(String username) throws ServiceException;
}
