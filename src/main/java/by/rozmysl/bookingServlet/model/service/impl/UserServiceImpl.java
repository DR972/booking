package by.rozmysl.bookingServlet.model.service.impl;

import by.rozmysl.bookingServlet.exception.DaoException;
import by.rozmysl.bookingServlet.exception.MailException;
import by.rozmysl.bookingServlet.exception.ServiceException;
import by.rozmysl.bookingServlet.model.dao.*;
import by.rozmysl.bookingServlet.model.db.ConnectionPool;
import by.rozmysl.bookingServlet.model.db.EntityTransaction;
import by.rozmysl.bookingServlet.model.db.ProxyConnection;
import by.rozmysl.bookingServlet.model.service.Letter;
import by.rozmysl.bookingServlet.model.entity.user.User;
import by.rozmysl.bookingServlet.model.service.ServiceFactory;
import by.rozmysl.bookingServlet.model.service.UserService;
import by.rozmysl.bookingServlet.model.service.validator.UserValidator;
import by.rozmysl.bookingServlet.model.service.validator.PasswordEncryptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.UUID;

import static by.rozmysl.bookingServlet.model.LoggerMessage.*;
import static by.rozmysl.bookingServlet.model.dao.SqlQuery.*;

public class UserServiceImpl implements UserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
    private static final String ACTIVATION_CODE = "Activation code";
    private static final String USER = "USER";
    private static final String ADMIN = "ADMIN";
    private static final String LOGIN_ERROR = "message.loginError";
    private static final String ACTIVE_FALSE = "message.activeFalse";
    private static final String USER_BANNED = "message.banned";

    private final UserDao userDao;
    private final RoleDao roleDao;

    public UserServiceImpl() {
        final DaoFactory daoFactory = DaoFactory.getInstance();
        userDao = daoFactory.getUserDao();
        roleDao = daoFactory.getRoleDao();
    }

    @Override
    public User findById(String username) throws ServiceException {
        try {
            return userDao.findEntity(USER_FIND_BY_ID, MESSAGE_USER_FIND_BY_ID, username);
//            return userDao.findById(username);
        } catch (DaoException e) {
            LOGGER.error(MESSAGE_USER_FIND_BY_ID, e);
            throw new ServiceException(MESSAGE_USER_FIND_BY_ID, e);
        }
    }

    @Override
    public List<User> findAll(int pageNumber, int rows) throws ServiceException {
        try {
            return userDao.findListEntities(USER_FIND_ALL, MESSAGE_USER_FIND_ALL, rows, pageNumber, rows);
//            return userDao.findAll(pageNumber, rows);
        } catch (DaoException e) {
            LOGGER.error(MESSAGE_USER_FIND_ALL, e);
            throw new ServiceException(MESSAGE_USER_FIND_ALL, e);
        }
    }

    @Override
    public void save(User user, String language, ServiceFactory service) throws ServiceException {
        user.setActivationCode(UUID.randomUUID().toString());
        EntityTransaction transaction = new EntityTransaction(userDao, roleDao);
        try {
            transaction.begin();
            service.getMailSender().sendMail(user.getEmail(), ACTIVATION_CODE, new Letter().createMessage(user, language));

            userDao.updateEntity(USER_SAVE, MESSAGE_USER_SAVE,
                    user.getId(),
                    user.getLastname(),
                    user.getFirstname(),
                    user.getEmail(),
                    PasswordEncryptor.getSaltedHash(user.getPassword()),
                    user.isActive(),
                    user.getActivationCode(),
                    user.isBanned());

            roleDao.updateEntity(USER_ROLE_SAVE, MESSAGE_USER_ROLE_SAVE,
                    user.getId(),
                    USER);
//            userDao.save(user, language);
//            roleDao.save(new UserRole(user.getId(), USER));
            transaction.commit();
        } catch (DaoException | MailException e) {
            transaction.rollback();
            LOGGER.error(MESSAGE_USER_SAVE, e);
            throw new ServiceException(MESSAGE_USER_SAVE, e);
        } finally {
            transaction.end();
        }
    }

    @Override
    public void delete(String username) throws ServiceException {
        EntityTransaction transaction = new EntityTransaction(userDao, roleDao);
        try {
            transaction.begin();
            roleDao.updateEntity(USER_ROLE_DELETE, MESSAGE_USER_ROLE_DELETE, username);
            userDao.updateEntity(USER_DELETE, MESSAGE_USER_DELETE, username);
            transaction.commit();
        } catch (DaoException e) {
            transaction.rollback();
            LOGGER.error(MESSAGE_USER_DELETE, e);
            throw new ServiceException(MESSAGE_USER_DELETE, e);
        } finally {
            transaction.end();
        }
    }

    @Override
    public int countUsersPages(int rows) throws ServiceException {
        try {
            return (int) Math.ceil((float) userDao.countEntitiesRows(USER_FIND_ROWS_COUNT, MESSAGE_COUNT_USERS_ROWS) / rows);
//            return userDao.countUsersPages(rows);
        } catch (DaoException e) {
            LOGGER.error(MESSAGE_COUNT_USERS_PAGES, e);
            throw new ServiceException(MESSAGE_COUNT_USERS_PAGES, e);
        }
    }

    @Override
    public String validateUser(User user) throws ServiceException {
        return new UserValidator().allValidate(user, this);
    }


    @Override
    public boolean activateUser(String code) throws ServiceException {
        User user = findUserByActivationCode(code);
        if (user == null) {
            return false;
        }
        try (final ProxyConnection connection = ConnectionPool.getInstance().getConnectionFromPool()) {
            userDao.setConnection(connection);
            userDao.updateEntity(USER_ACTIVATE, MESSAGE_USER_ACTIVATE, user.getId());
//            userDao.activateUser(user.getId());
        } catch (DaoException e) {
            LOGGER.error(MESSAGE_USER_ACTIVATE, e);
            throw new ServiceException(MESSAGE_USER_ACTIVATE, e);
        }
        return true;
    }

    @Override
    public User findUserByActivationCode(String code) throws ServiceException {
        try {
            return userDao.findEntity(USER_FIND_BY_ACTIVATION_CODE, MESSAGE_USER_FIND_BY_ACTIVATION_CODE, code);
//            return userDao.findUserByActivationCode(code);
        } catch (DaoException e) {
            LOGGER.error(MESSAGE_USER_FIND_BY_ACTIVATION_CODE, e);
            throw new ServiceException(MESSAGE_USER_FIND_BY_ACTIVATION_CODE, e);
        }
    }

    @Override
    public String authenticateUser(User user, String password) {
        return user == null || !PasswordEncryptor.check(password, user.getPassword()) ? LOGIN_ERROR : user.isBanned() ? USER_BANNED : !user.isActive() ? ACTIVE_FALSE : null;
    }

    @Override
    public void changeListUserRoles(String username) throws ServiceException {
        try (final ProxyConnection connection = ConnectionPool.getInstance().getConnectionFromPool()) {
            roleDao.setConnection(connection);
            User user = findById(username);
            if (user.getRoles().contains(ADMIN)) {
                roleDao.updateEntity(USER_ROLE_DELETE_ADMIN, MESSAGE_USER_ROLE_DELETE_ADMIN, user.getId());
//                roleDao.deleteUserRoleAdmin(user.getId());
            } else {
                roleDao.updateEntity(USER_ROLE_SAVE, MESSAGE_USER_ROLE_SAVE,
                        user.getId(),
                        ADMIN);
//                roleDao.save(new UserRole(user.getId(), ADMIN));
            }
        } catch (DaoException e) {
            LOGGER.error(MESSAGE_USER_ROLE_DELETE_ADMIN, e);
            throw new ServiceException(MESSAGE_USER_ROLE_DELETE_ADMIN, e);
        }
    }

    @Override
    public void changeAccountLock(String username) throws ServiceException {
        try (final ProxyConnection connection = ConnectionPool.getInstance().getConnectionFromPool()) {
            userDao.setConnection(connection);
            User user = findById(username);
            userDao.updateEntity(USER_CHANGE_ACCOUNT_LOCK, MESSAGE_USER_CHANGE_ACCOUNT_LOCK,
                    !user.isBanned(),
                    user.getId());
//            userDao.changeAccountLock(user.getId(), !user.isBanned());
        } catch (DaoException e) {
            LOGGER.error(MESSAGE_USER_CHANGE_ACCOUNT_LOCK, e);
            throw new ServiceException(MESSAGE_USER_CHANGE_ACCOUNT_LOCK, e);
        }
    }
}
