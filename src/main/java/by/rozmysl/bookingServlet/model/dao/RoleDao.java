package by.rozmysl.bookingServlet.model.dao;

import by.rozmysl.bookingServlet.exception.DaoException;
import by.rozmysl.bookingServlet.model.entity.user.UserRole;

import java.util.List;

/**
 * Provides the base model DAO interface for `USER_ROLE` table.
 */
public interface RoleDao extends Dao<UserRole, String> {

    /**
     * Searches for all User's UserRoles in the `USER_ROLE` table
     *
     * @param username User's id
     * @return list of UserRole objects
     * @throws DaoException if there was an error accessing the database
     */
    List<UserRole> findUserRoleByUser(String username) throws DaoException;
}
