package by.rozmysl.bookingServlet.model.dao.impl;

import by.rozmysl.bookingServlet.exception.DaoException;
import by.rozmysl.bookingServlet.model.dao.StatementConsumer;
import by.rozmysl.bookingServlet.model.dao.UserDao;
import by.rozmysl.bookingServlet.model.entity.user.User;
import by.rozmysl.bookingServlet.model.service.validator.PasswordEncryptor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

import static by.rozmysl.bookingServlet.model.dao.ColumnName.ROLE_COLUMN_NAME;
import static by.rozmysl.bookingServlet.model.LoggerMessage.*;
import static by.rozmysl.bookingServlet.model.dao.SqlQuery.*;
import static by.rozmysl.bookingServlet.model.dao.ColumnName.*;

/**
 * Provides the base model implementation for `USER` table DAO with the <b>ProxyConnection</b> properties.
 */
public class UserDaoImpl extends DaoImpl<User, String> implements UserDao {

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
        return str == null ? new HashSet<>() : new HashSet<>(Arrays.asList(str.split(",")));
    }

    //    /**
//     * Get a list of query results from the 'User' table
//     *
//     * @param sql               the wording of the request to the database
//     * @param message           in case of an error
//     * @param statementConsumer StatementConsumer
//     * @return list of User objects
//     * @throws DaoException if there was an error accessing the database
//     */
//    @Override
//    public List<User> getResultSet(String sql, String message, StatementConsumer statementConsumer) throws DaoException {
//        return get(sql, message, statementConsumer).stream().map(u -> new User(
//                        u.get(USER_COLUMN_USERNAME),
//                        u.get(USER_COLUMN_LAST_NAME),
//                        u.get(USER_COLUMN_FIRST_NAME),
//                        u.get(USER_COLUMN_PASSWORD),
//                        Boolean.parseBoolean(u.get(USER_COLUMN_ACTIVE)),
//                        u.get(USER_COLUMN_EMAIL),
//                        u.get(USER_COLUMN_ACTIVATIONCODE),
//                        Boolean.parseBoolean(u.get(USER_COLUMN_BANNED)),
//                        getRoles(u.get(ROLE_COLUMN_NAME))))
//                .collect(Collectors.toList());
//    }

//
//    /**
//     * Constructs dao for `user` table.
//     */
//    public UserDaoImpl() {
//        super(USER_FIND_BY_ID, MESSAGE_USER_FIND_BY_ID, USER_FIND_ALL, MESSAGE_USER_FIND_ALL, USER_DELETE, MESSAGE_USER_DELETE);
//    }
//
//    /**
//     * Counts the number of pages in the 'USER` table
//     *
//     * @param rows number of rows per page
//     * @return number of pages
//     * @throws DaoException if there was an error accessing the database
//     */
//    @Override
//    public int countUsersPages(int rows) throws DaoException {
//        return (int) Math.ceil((float) countRows(USER_FIND_ROWS_COUNT, MESSAGE_COUNT_USERS_ROWS) / rows);
//    }
//
//    /**
//     * Saves the User in the `USER` table
//     *
//     * @param user     User
//     * @param language User's language
//     * @throws DaoException if there was an error accessing the database
//     */
//    @Override
//    public void save(User user, String language) throws DaoException {
//        update(connection, USER_SAVE, MESSAGE_USER_SAVE, statement -> {
//            statement.setString(1, user.getId());
//            statement.setString(2, user.getLastname());
//            statement.setString(3, user.getFirstname());
//            statement.setString(4, user.getEmail());
//            statement.setString(5, PasswordEncryptor.getSaltedHash(user.getPassword()));
//            statement.setBoolean(6, user.isActive());
//            statement.setString(7, user.getActivationCode());
//            statement.setBoolean(8, user.isBanned());
//        });
//    }
//
//    /**
//     * Tries to activate the User. If successful, it saves the result in the `USER` table
//     *
//     * @param username User username
//     * @throws DaoException if there was an error accessing the database
//     */
//    @Override
//    public void activateUser(String username) throws DaoException {
//        update(connection, USER_ACTIVATE, MESSAGE_USER_ACTIVATE, statement -> statement.setString(1, username));
//    }
//
//    /**
//     * Searches for the User in the `USER` table by activation code
//     *
//     * @param code User's activation code
//     * @return User object
//     * @throws DaoException if there was an error accessing the database
//     */
//    @Override
//    public User findUserByActivationCode(String code) throws DaoException {
//        List<User> services = getResultSet(USER_FIND_BY_ACTIVATION_CODE, MESSAGE_USER_FIND_BY_ACTIVATION_CODE,
//                statement -> statement.setString(1, code));
//        return services.size() != 0 ? services.get(0) : null;
//    }
//
//    @Override
//    public void changeAccountLock(String username, boolean banned) throws DaoException {
//        update(connection, USER_CHANGE_ACCOUNT_LOCK, MESSAGE_USER_CHANGE_ACCOUNT_LOCK, statement -> {
//            statement.setBoolean(1, banned);
//            statement.setString(2, username);
//        });
//    }


}
