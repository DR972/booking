package by.rozmysl.booking.model.dao.impl;

import by.rozmysl.booking.model.dao.RoleDao;
import by.rozmysl.booking.model.entity.user.UserRole;

import java.sql.ResultSet;
import java.sql.SQLException;

import static by.rozmysl.booking.model.dao.ColumnName.*;

/**
 * Provides the base model implementation for `UserRole` table DAO.
 */
public class RoleDaoImpl extends AbstractDao<UserRole, String> implements RoleDao {

    /**
     * Create UserRole entity
     *
     * @param resultSet ResultSet
     * @return UserRole object
     * @throws SQLException if there was an error accessing the database
     */
    @Override
    public UserRole buildEntity(ResultSet resultSet) throws SQLException {
        return new UserRole(
                resultSet.getString(USER_ROLE_COLUMN_USER),
                resultSet.getString(USER_ROLE_COLUMN_ROLE));
    }
}
