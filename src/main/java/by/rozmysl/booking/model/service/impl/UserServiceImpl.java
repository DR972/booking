package by.rozmysl.booking.model.service.impl;

import by.rozmysl.booking.exception.DaoException;
import by.rozmysl.booking.exception.MailException;
import by.rozmysl.booking.exception.ServiceException;
import by.rozmysl.booking.model.dao.*;
import by.rozmysl.booking.model.db.EntityTransaction;
import by.rozmysl.booking.model.entity.user.User;
import by.rozmysl.booking.model.service.ServiceProvider;
import by.rozmysl.booking.model.service.UserService;
import by.rozmysl.booking.model.validator.UserValidator;
import by.rozmysl.booking.model.validator.PasswordEncryptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

import static by.rozmysl.booking.model.ModelTypeProvider.*;
import static by.rozmysl.booking.model.util.LoggerMessageError.*;

/**
 * Provides logic for working with data sent to the `User` table DAO.
 */
public class UserServiceImpl extends AbstractService<User, String> implements UserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
    private static final String ACTIVATION_CODE = "Activation code";
    private static final String USER = "USER";
    private static final String ADMIN = "ADMIN";
    private static final String LOGIN_ERROR = "message.loginError";
    private static final String ACTIVE_FALSE = "message.activeFalse";
    private static final String USER_BANNED = "message.banned";

    private final UserDao userDao;
    private final RoleDao roleDao;

    /**
     * The constructor creates a new object UserServiceImpl without property.
     */
    public UserServiceImpl() {
        final DaoProvider daoProvider = DaoProvider.getInstance();
        userDao = daoProvider.getUserDao();
        roleDao = daoProvider.getRoleDao();
    }

    /**
     * Provides logic for saving the User in the `User` table and saving the UserRole in the `UserRole` table
     *
     * @param user     User
     * @param language User language
     * @param service  ServiceProvider
     * @throws ServiceException if the operation failed
     */
    @Override
    public void save(User user, String language, ServiceProvider service) throws ServiceException {
        user.setActivationCode(UUID.randomUUID().toString());
        EntityTransaction transaction = new EntityTransaction(userDao, roleDao);
        try {
            service.getMailSender().sendMail(user.getEmail(), ACTIVATION_CODE, new LetterCreator().createMessage(user, language));
            transaction.begin();
            userDao.updateEntityUsingTransaction(USER_SAVE,
                    user.getId(),
                    user.getLastname(),
                    user.getFirstname(),
                    user.getEmail(),
                    PasswordEncryptor.getSaltedHash(user.getPassword()),
                    user.isActive(),
                    user.getActivationCode(),
                    user.isBanned());

            roleDao.updateEntityUsingTransaction(USER_ROLE_SAVE,
                    user.getId(),
                    USER);
            transaction.commit();
        } catch (DaoException | MailException e) {
            transaction.rollback();
            LOGGER.error(MESSAGE_USER_SAVE, e);
            throw new ServiceException(MESSAGE_USER_SAVE, e);
        } finally {
            transaction.end();
        }
    }

    /**
     * Provides logic for deleting the User in the `User` table and deleting the UserRole in the `UserRole` table
     *
     * @param username User
     * @throws ServiceException if the operation failed
     */
    @Override
    public void delete(String username) throws ServiceException {
        EntityTransaction transaction = new EntityTransaction(userDao, roleDao);
        try {
            transaction.begin();
            roleDao.updateEntityUsingTransaction(USER_ROLE_DELETE, username);
            userDao.updateEntityUsingTransaction(USER_DELETE, username);
            transaction.commit();
        } catch (DaoException e) {
            transaction.rollback();
            LOGGER.error(MESSAGE_USER_DELETE, e);
            throw new ServiceException(MESSAGE_USER_DELETE, e);
        } finally {
            transaction.end();
        }
    }

    /**
     * Provides User validation.
     *
     * @param user User user
     * @return validation result
     * @throws ServiceException if the operation failed
     */
    @Override
    public String validateUser(User user) throws ServiceException {
        return new UserValidator().allValidate(user, this);
    }

    /**
     * Provides User authentication.
     *
     * @param user     User
     * @param password password
     * @return authentication result
     */
    @Override
    public String authenticateUser(User user, String password) {
        return user == null || !PasswordEncryptor.check(password, user.getPassword()) ? LOGIN_ERROR : user.isBanned() ? USER_BANNED : !user.isActive() ? ACTIVE_FALSE : null;
    }

    /**
     * Provides logic for changing the list UserRoles.
     *
     * @param username id of the User
     * @throws ServiceException if the operation failed
     */

    @Override
    public void changeListUserRoles(String username) throws ServiceException {
        User user = findEntity(User.class, USER_FIND_BY_ID, username);
        if (user.getRoles().contains(ADMIN)) {
            updateEntity(USER_ROLE_DELETE_ADMIN, username);
        } else {
            updateEntity(USER_ROLE_SAVE, username, ADMIN);
        }
    }
}
