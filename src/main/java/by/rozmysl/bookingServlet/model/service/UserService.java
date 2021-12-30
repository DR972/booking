package by.rozmysl.bookingServlet.model.service;

import by.rozmysl.bookingServlet.exception.ServiceException;
import by.rozmysl.bookingServlet.model.entity.user.User;

import java.util.List;

public interface UserService {
    User findById(String username) throws ServiceException;
    List<User> findAll(int page, int rows) throws ServiceException;
    User save(User user, String language) throws ServiceException;
    void delete(String username) throws ServiceException;
    int countUsersPages(int rows) throws ServiceException;
    String validateUser(User user) throws ServiceException;
    boolean activateUser(String code) throws ServiceException;
    User findUserByActivationCode(String code) throws ServiceException;
    String authenticateUser(User user, String password);
}
