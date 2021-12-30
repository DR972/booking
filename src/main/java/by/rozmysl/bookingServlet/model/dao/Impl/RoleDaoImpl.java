package by.rozmysl.bookingServlet.model.dao.Impl;

import by.rozmysl.bookingServlet.exception.DaoException;
import by.rozmysl.bookingServlet.model.dao.SqlQuery;
import by.rozmysl.bookingServlet.model.dao.RoleDao;
import by.rozmysl.bookingServlet.model.entity.user.UserRole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static by.rozmysl.bookingServlet.model.dao.SqlQuery.*;

/**
 * Provides the base model implementation for `USER_ROLE` and `ROLE` table DAO with the <b>ProxyConnection</b> properties.
 */
public class RoleDaoImpl implements RoleDao {
    private static final Logger LOGGER = LoggerFactory.getLogger(RoleDaoImpl.class);
    private final Connection connection;

    /**
     * The constructor creates a new object RoleDaoImpl with the <b>connection</b> property
     */
    public RoleDaoImpl(Connection connection) {
        this.connection = connection;
    }

    /**
     * Searches for the UserRole in the `USER_ROLE` table by id
     *
     * @param name of Role
     * @return UserRole object
     * @throws DaoException if there was an error accessing the database
     */
    @Override
    public UserRole findById(String name) throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(USER_ROLE_FIND_BY_ROLE)){
            preparedStatement.setString(1, name);
            final ResultSet rs = preparedStatement.executeQuery();
            rs.next();
            return buildUserRole(rs);
        } catch (SQLException e) {
            LOGGER.error("'UserRole findById' query has been failed", e);
            throw new DaoException("'UserRole findById' query has been failed", e);
        }
    }

    /**
     * Searches for all User's UserRoles in the `USER_ROLE` table
     *
     * @param username User's id
     * @return list of UserRole objects
     * @throws DaoException if there was an error accessing the database
     */
    @Override
    public List<UserRole> findUserRoleByUser(String username) throws DaoException {
        List<UserRole> roles = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(USER_ROLE_FIND_BY_USER)){
            preparedStatement.setString(1, username);
            final ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                roles.add(buildUserRole(rs));
            }
            return roles;
        } catch (SQLException e) {
            LOGGER.error("'findUserRoleByUser' query has been failed", e);
            throw new DaoException("'findUserRoleByUser' query has been failed", e);
        }
    }

    /**
     * Searches for all UserRoles in the `USER_ROLE` table
     *
     * @param page number of the page to return
     * @param rows number of rows per page
     * @return list of UserRole objects
     * @throws DaoException if there was an error accessing the database
     */
    @Override
    public List<UserRole> findAll(int page, int rows) throws DaoException {
        List<UserRole> roles = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(USER_ROLE_FIND_ALL)){
            final ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                roles.add(buildUserRole(rs));
            }
            return roles;
        } catch (SQLException e) {
            LOGGER.error("'UserRole findAll' query has been failed", e);
            throw new DaoException("'findUserRoleByUser' query has been failed", e);
        }
    }

    /**
     * Saves the UserRole in the `USER_ROLE` table
     *
     * @param userRole UserRole
     * @return UserRole object
     * @throws DaoException if there was an error accessing the database
     */
    @Override
    public UserRole save(UserRole userRole) throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SqlQuery.USER_ROLE_SAVE)){
            preparedStatement.setString(1, userRole.getUsername());
            preparedStatement.setString(2, userRole.getRole());
            preparedStatement.executeUpdate();
            return userRole;
        } catch (SQLException e) {
            LOGGER.error("'UserRole save' query has been failed", e);
            throw new DaoException("'findUserRoleByUser' query has been failed", e);
        }
    }

    /**
     * Deletes the UserRole in the `USER_ROLE` table
     *
     * @param username of UserRole
     * @throws DaoException if there was an error accessing the database
     */
    @Override
    public void delete(String username) throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SqlQuery.USER_ROLE_DELETE)){
            preparedStatement.setString(1, username);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("'UserRole delete' query has been failed", e);
            throw new DaoException("'findUserRoleByUser' query has been failed", e);
        }
    }

    private UserRole buildUserRole(ResultSet rs) throws SQLException {
        return new UserRole(
                rs.getString(USER_ROLE_COLUMN_USER),
                rs.getString(USER_ROLE_COLUMN_ROLE));
    }
}
