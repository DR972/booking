package by.rozmysl.booking.model.service;

import by.rozmysl.booking.exception.ServiceException;
import by.rozmysl.booking.model.entity.user.User;

/**
 * Provides logic for working with data sent to the `User` table DAO.
 */
public interface UserService extends Service<User, String> {

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
     * Provides User validation.
     *
     * @param user User user
     * @return validation result
     * @throws ServiceException if the operation failed
     */
    String validateUser(User user) throws ServiceException;

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
}
