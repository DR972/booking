package by.rozmysl.booking.model.dao.impl;

import by.rozmysl.booking.model.dao.UserDao;
import by.rozmysl.booking.model.entity.user.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import static by.rozmysl.booking.model.dao.ColumnName.ROLE_COLUMN_NAME;
import static by.rozmysl.booking.model.dao.ColumnName.*;

/**
 * Provides the base model implementation for `User` table DAO.
 */
public class UserDaoImpl extends AbstractDao<User, String> implements UserDao {

    /**
     * Create User entity
     *
     * @param resultSet ResultSet
     * @return Booking object
     * @throws SQLException if there was an error accessing the database
     */
    @Override
    public User buildEntity(ResultSet resultSet) throws SQLException {
        return new User(
                resultSet.getString(USER_COLUMN_USERNAME),
                resultSet.getString(USER_COLUMN_LAST_NAME),
                resultSet.getString(USER_COLUMN_FIRST_NAME),
                resultSet.getString(USER_COLUMN_PASSWORD),
                resultSet.getBoolean(USER_COLUMN_ACTIVE),
                resultSet.getString(USER_COLUMN_EMAIL),
                resultSet.getString(USER_COLUMN_ACTIVATIONCODE),
                resultSet.getBoolean(USER_COLUMN_BANNED),
                getRoles(resultSet.getString(ROLE_COLUMN_NAME)));
    }

    /**
     * Converts a string to a Set<String> User Roles
     *
     * @param str String User Roles
     * @return list of User objects
     */
    private Set<String> getRoles(String str) {
        return str != null ? new HashSet<>(Arrays.asList(str.split(","))) : new HashSet<>();
    }
}
