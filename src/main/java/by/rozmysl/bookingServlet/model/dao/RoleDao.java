package by.rozmysl.bookingServlet.model.dao;

import by.rozmysl.bookingServlet.exception.DaoException;
import by.rozmysl.bookingServlet.model.entity.user.UserRole;

/**
 * Provides the base model DAO interface for `USER_ROLE` table.
 */
public interface RoleDao extends Dao<UserRole, String> {
//
//    void deleteUserRoleAdmin(String username) throws DaoException;
}
