package by.rozmysl.bookingServlet.model.service;

import by.rozmysl.bookingServlet.exception.ServiceException;
import by.rozmysl.bookingServlet.model.entity.user.User;

import java.util.List;

public interface UserService {

    User findById(String username) throws ServiceException;

    List<User> findAll(int pageNumber, int rows) throws ServiceException;

    void save(User user, String language, ServiceFactory service) throws ServiceException;

    void delete(String username) throws ServiceException;

    int countUsersPages(int rows) throws ServiceException;

    String validateUser(User user) throws ServiceException;

    boolean activateUser(String code) throws ServiceException;

    User findUserByActivationCode(String code) throws ServiceException;

    String authenticateUser(User user, String password);

    void changeListUserRoles(String username) throws ServiceException;

    void changeAccountLock(String username) throws ServiceException;
}
