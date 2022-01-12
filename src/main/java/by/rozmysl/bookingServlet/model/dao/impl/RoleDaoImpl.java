package by.rozmysl.bookingServlet.model.dao.impl;

import by.rozmysl.bookingServlet.exception.DaoException;
import by.rozmysl.bookingServlet.model.dao.RoleDao;
import by.rozmysl.bookingServlet.model.dao.StatementConsumer;
import by.rozmysl.bookingServlet.model.entity.user.UserRole;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

import static by.rozmysl.bookingServlet.model.LoggerMessage.*;
import static by.rozmysl.bookingServlet.model.dao.SqlQuery.*;
import static by.rozmysl.bookingServlet.model.dao.ColumnName.*;

/**
 * Provides the base model implementation for `USER_ROLE` and `ROLE` table DAO with the <b>ProxyConnection</b> properties.
 */
public class RoleDaoImpl extends DaoImpl<UserRole, String> implements RoleDao {

    @Override
    public UserRole buildEntity(ResultSet resultSet) throws SQLException {
        return new UserRole(
                resultSet.getString(USER_ROLE_COLUMN_USER),
                resultSet.getString(USER_ROLE_COLUMN_ROLE));
    }

//    /**
//     * Get a list of query results from the 'UserRole' table
//     *
//     * @param sql               the wording of the request to the database
//     * @param message           in case of an error
//     * @param statementConsumer StatementConsumer
//     * @return list of UserRole objects
//     * @throws DaoException if there was an error accessing the database
//     */
//    @Override
//    public List<UserRole> getResultSet(String sql, String message, StatementConsumer statementConsumer) throws DaoException {
//        return get(sql, message, statementConsumer).stream().map(r -> new UserRole(
//                r.get(USER_ROLE_COLUMN_USER),
//                r.get(USER_ROLE_COLUMN_ROLE))).collect(Collectors.toList());
//    }
//
//    /**
//     * Constructs dao for `UserRole` table.
//     */
//    public RoleDaoImpl() {
//        super(USER_ROLE_FIND_BY_ID, MESSAGE_USER_ROLE_FIND_BY_ID, USER_ROLE_FIND_ALL, MESSAGE_USER_ROLE_FIND_ALL,
//                USER_ROLE_DELETE, MESSAGE_USER_ROLE_DELETE);
//    }

//    /**
//     * Saves the UserRole in the `USER_ROLE` table
//     *
//     * @param userRole UserRole
//     * @throws DaoException if there was an error accessing the database
//     */
//    @Override
//    public void save(UserRole userRole) throws DaoException {
//        update(connection, USER_ROLE_SAVE, MESSAGE_USER_ROLE_SAVE, statement -> {
//            statement.setString(1, userRole.getUsername());
//            statement.setString(2, userRole.getRole());
//        });
//    }
//
//    @Override
//    public void deleteUserRoleAdmin(String username) throws DaoException {
//        update(connection, USER_ROLE_DELETE_ADMIN, MESSAGE_USER_ROLE_DELETE_ADMIN, statement -> statement.setString(1, username));
//    }
}
