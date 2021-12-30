package by.rozmysl.bookingServlet.model.service;

import by.rozmysl.bookingServlet.exception.ServiceException;
import by.rozmysl.bookingServlet.model.entity.user.User;
import by.rozmysl.bookingServlet.model.entity.user.UserRole;

import java.sql.SQLException;
import java.util.List;

public interface RoleService {
    UserRole findById(String name) throws ServiceException;
    List<UserRole> findAll(int page, int rows) throws ServiceException;
    List<UserRole> findUserRoleByUser(String username) throws ServiceException;
    UserRole save(UserRole userRole) throws ServiceException;
    void delete(String username) throws ServiceException;
}
