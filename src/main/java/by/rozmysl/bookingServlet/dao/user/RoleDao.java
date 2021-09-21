package by.rozmysl.bookingServlet.dao.user;

import by.rozmysl.bookingServlet.dao.Dao;
import by.rozmysl.bookingServlet.entity.user.UserRole;

import java.sql.SQLException;
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
     * @throws SQLException if there was an error accessing the database
     */
    List<UserRole> findUserRoleByUser(String username) throws SQLException;
}
