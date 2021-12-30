package by.rozmysl.bookingServlet.model.service.impl;

import by.rozmysl.bookingServlet.exception.DaoException;
import by.rozmysl.bookingServlet.exception.MailException;
import by.rozmysl.bookingServlet.exception.ServiceException;
import by.rozmysl.bookingServlet.model.service.mail.Letter;
import by.rozmysl.bookingServlet.model.service.mail.MailSender;
import by.rozmysl.bookingServlet.model.dao.DaoFactory;
import by.rozmysl.bookingServlet.model.dao.UserDao;
import by.rozmysl.bookingServlet.model.entity.user.User;
import by.rozmysl.bookingServlet.model.service.UserService;
import by.rozmysl.bookingServlet.model.service.validator.UserValidator;
import by.rozmysl.bookingServlet.model.service.validator.PasswordEncryptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.mail.MessagingException;
import java.sql.Connection;
import java.util.List;
import java.util.UUID;

public class UserServiceImpl implements UserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
    public static final String ACTIVATION_CODE = "Activation code";
    private final UserDao userDao;

    public UserServiceImpl(Connection connection) {
        userDao = DaoFactory.getInstance().userDao(connection);
    }

    @Override
    public User findById(String username) throws ServiceException {
        try {
            return userDao.findById(username);
        } catch (DaoException e) {
            LOGGER.error("'User findById' query has been failed", e);
            throw new ServiceException("'User findById' query has been failed", e);
        }
    }

    @Override
    public List<User> findAll(int page, int rows) throws ServiceException {
        try {
            return userDao.findAll(page, rows);
        } catch (DaoException e) {
            LOGGER.error("'User findAll' query has been failed", e);
            throw new ServiceException("'User findAll' query has been failed", e);
        }
    }

    @Override
    public User save(User user, String language) throws ServiceException {
        user.setActivationCode(UUID.randomUUID().toString());
        try {
            MailSender.getInstance().sendMail(user.getEmail(), ACTIVATION_CODE, new Letter().createMessage(user, language));
            return userDao.save(user, language);
        } catch (DaoException |  MailException e) {
            LOGGER.error("'Save User' query has been failed", e);
            throw new ServiceException("'Save User' query has been failed", e);
        }
    }

    @Override
    public void delete(String username) throws ServiceException {
        try {
            userDao.delete(username);
        } catch (DaoException e) {
            LOGGER.error("'Delete User' query has been failed", e);
            throw new ServiceException("'Delete User' query has been failed", e);
        }
    }

    @Override
    public int countUsersPages(int rows) throws ServiceException {
        try {
            return userDao.countUsersPages(rows);
        } catch (DaoException e) {
            LOGGER.error("'CountUsersPages' query has been failed", e);
            throw new ServiceException("'CountUsersPages' query has been failed", e);
        }
    }

    @Override
    public String validateUser(User user) throws ServiceException {
        try {
            return new UserValidator().allValidate(user, this);
        } catch (ServiceException e) {
            LOGGER.error("'validateUser' query has been failed", e);
            throw new ServiceException("'activateUser' query has been failed", e);
        }
    }


    @Override
    public boolean activateUser(String code) throws ServiceException {
        User user = findUserByActivationCode(code);
        if (user == null) {
            return false;
        }
        try {
            return userDao.activateUser(code, user);
        } catch (DaoException e) {
            LOGGER.error("'activateUser' query has been failed", e);
            throw new ServiceException("'activateUser' query has been failed", e);
        }
    }

    @Override
    public User findUserByActivationCode(String code) throws ServiceException {
        try {
            return userDao.findUserByActivationCode(code);
        } catch (DaoException e) {
            LOGGER.error("'findUserByActivationCode' query has been failed", e);
            throw new ServiceException("'findUserByActivationCode' query has been failed", e);
        }
    }

    @Override
    public String authenticateUser(User user, String password) {
        if (user == null || !PasswordEncryptor.check(password, user.getPassword())) {
            return "message.loginError";
        }
        if (!user.getActive()) {
            return "message.activeFalse";
        }
        return null;
    }
}
