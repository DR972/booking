package by.rozmysl.bookingServlet.model.service.impl;

import by.rozmysl.bookingServlet.exception.DaoException;
import by.rozmysl.bookingServlet.exception.ServiceException;
import by.rozmysl.bookingServlet.model.dao.DaoFactory;
import by.rozmysl.bookingServlet.model.dao.RoleDao;
import by.rozmysl.bookingServlet.model.entity.user.UserRole;
import by.rozmysl.bookingServlet.model.service.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.util.List;

public class RoleServiceImpl implements RoleService {
    private static final Logger LOGGER = LoggerFactory.getLogger(RoleServiceImpl.class);
    private final RoleDao roleDao;

    public RoleServiceImpl(Connection connection) {
        roleDao = DaoFactory.getInstance().roleDao(connection);
    }

    @Override
    public UserRole findById(String name) throws ServiceException {
        try {
            return roleDao.findById(name);
        } catch (DaoException e) {
            LOGGER.error("'UserRole findById' query has been failed", e);
            throw new ServiceException("'UserRole findById' query has been failed", e);
        }
    }

    @Override
    public List<UserRole> findAll(int page, int rows) throws ServiceException {
        try {
            return roleDao.findAll(page, rows);
        } catch (DaoException e) {
            LOGGER.error("'UserRole findAll' query has been failed", e);
            throw new ServiceException("'findUserRoleByUser' query has been failed", e);
        }
    }

    @Override
    public List<UserRole> findUserRoleByUser(String username) throws ServiceException {
        try {
            return roleDao.findUserRoleByUser(username);
        } catch (DaoException e) {
            LOGGER.error("'findUserRoleByUser' query has been failed", e);
            throw new ServiceException("'findUserRoleByUser' query has been failed", e);
        }
    }

    @Override
    public UserRole save(UserRole userRole) throws ServiceException {
        try {
            return roleDao.save(userRole);
        } catch (DaoException e) {
            LOGGER.error("'UserRole save' query has been failed", e);
            throw new ServiceException("'findUserRoleByUser' query has been failed", e);
        }
    }

    @Override
    public void delete(String username) throws ServiceException {
        try {
            roleDao.delete(username);
        } catch (DaoException e) {
            LOGGER.error("'UserRole delete' query has been failed", e);
            throw new ServiceException("'findUserRoleByUser' query has been failed", e);
        }
    }
}
