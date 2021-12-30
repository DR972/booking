package by.rozmysl.bookingServlet.model.dao.Impl;

import by.rozmysl.bookingServlet.exception.DaoException;
import by.rozmysl.bookingServlet.model.dao.UserDao;
import by.rozmysl.bookingServlet.model.entity.user.User;
import by.rozmysl.bookingServlet.model.service.validator.PasswordEncryptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.*;

import static by.rozmysl.bookingServlet.model.dao.SqlQuery.*;

/**
 * Provides the base model implementation for `USER` table DAO with the <b>ProxyConnection</b> properties.
 */
public class UserDaoImpl implements UserDao {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserDaoImpl.class);
    private final Connection connection;

    /**
     * The constructor creates a new object UserDaoImpl with the <b>connection</b> property
     */
    public UserDaoImpl(Connection connection) {
        this.connection = connection;
    }

    /**
     * Counts the number of pages in the 'USER` table
     *
     * @param rows number of rows per page
     * @return number of pages
     * @throws DaoException if there was an error accessing the database
     */
    @Override
    public int countUsersPages(int rows) throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(USER_FIND_ROWS_COUNT)) {
            final ResultSet rs = preparedStatement.executeQuery();
            rs.next();
            return (int) Math.ceil((float) rs.getInt(USER_ROWS_COUNT) / rows);
        } catch (SQLException e) {
            LOGGER.error("'CountUsersPages' query has been failed", e);
            throw new DaoException("'CountUsersPages' query has been failed", e);
        }
    }

    /**
     * Searches for the User in the `USER` table by id
     *
     * @param username User's id
     * @return User object
     * @throws DaoException if there was an error accessing the database
     */
    @Override
    public User findById(String username) throws DaoException {
        User user = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(USER_FIND_BY_ID)) {
            preparedStatement.setString(1, username);
            final ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                if (user == null) {
                    user = buildUser(rs);
                } else {
                    user.getRoles().add(rs.getString(ROLE_COLUMN_NAME));
                }
            }
        } catch (SQLException e) {
            LOGGER.error("'User findById' query has been failed", e);
            throw new DaoException("'User findById' query has been failed", e);
        }
        return user;
    }

    /**
     * Searches for all Users in the `USER` table
     *
     * @param page number of the page to return
     * @param rows number of rows per page
     * @return list of User objects
     * @throws DaoException if there was an error accessing the database
     */
    @Override
    public List<User> findAll(int page, int rows) throws DaoException {
        List<User> users = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(USER_FIND_ALL)) {
            preparedStatement.setInt(1, rows);
            preparedStatement.setInt(2, page);
            preparedStatement.setInt(3, rows);
            final ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                String username = rs.getString(USER_COLUMN_USERNAME);
                if (users.stream().noneMatch(u->u.getUsername().equals(username))) {
                    users.add(buildUser(rs));
                } else {
                    users.stream().filter(u -> u.getUsername().equals(username))
                            .findFirst().get().getRoles().add(rs.getString(ROLE_COLUMN_NAME));
                }
            }
        } catch (SQLException e) {
            LOGGER.error("'User findAll' query has been failed", e);
            throw new DaoException("'User findAll' query has been failed", e);
        }
        return users;
    }

    /**
     * Saves the User in the `USER` table
     *
     * @param user User
     * @return User object
     */
    @Override
    public User save(User user) {
        return user;
    }

    /**
     * Saves the User in the `USER` table
     *
     * @param user     User
     * @param language User's language
     * @return User object
     * @throws DaoException if there was an error accessing the database
     */
    @Override
    public User save(User user, String language) throws DaoException {
        try(PreparedStatement preparedStatement = connection.prepareStatement(USER_SAVE)) {
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getLastname());
            preparedStatement.setString(3, user.getFirstname());
            preparedStatement.setString(4, user.getEmail());
            preparedStatement.setString(5, PasswordEncryptor.getSaltedHash(user.getPassword()));
            preparedStatement.setBoolean(6, user.getActive());
            preparedStatement.setString(7, user.getActivationCode());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("'Save User' query has been failed", e);
            throw new DaoException("'Save User' query has been failed", e);
        }
        return user;
    }

    /**
     * Deletes the User in the `USER` table
     *
     * @param username id of the User
     * @throws DaoException if there was an error accessing the database
     */
    @Override
    public void delete(String username) throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(USER_DELETE)){
            preparedStatement.setString(1, username);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("'Delete User' query has been failed", e);
            throw new DaoException("'Delete User' query has been failed", e);
        }
    }

    /**
     * Tries to activate the User. If successful, it saves the result in the `USER` table
     *
     * @param code User's activation code
     * @return verification result
     * @throws DaoException if there was an error accessing the database
     */
    @Override
    public boolean activateUser(String code, User user) throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(USER_ACTIVATE)){
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.executeUpdate();
            LOGGER.info("The user '" + user.getUsername() + "' is activated.");
            return true;
        } catch (SQLException e) {
            LOGGER.error("'activateUser' query has been failed", e);
            throw new DaoException("'activateUser' query has been failed", e);
        }
    }

    /**
     * Searches for the User in the `USER` table by activation code
     *
     * @param code User's activation code
     * @return User object
     * @throws DaoException if there was an error accessing the database
     */
    @Override
    public User findUserByActivationCode(String code) throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(USER_FIND_BY_ACTIVATION_CODE)){
            preparedStatement.setString(1, code);
            final ResultSet rs = preparedStatement.executeQuery();
            rs.next();
            return buildUser(rs);
        } catch (SQLException e) {
            LOGGER.error("'findUserByActivationCode' query has been failed", e);
            throw new DaoException("'findUserByActivationCode' query has been failed", e);
        }
    }

    private User buildUser(ResultSet rs) throws SQLException {
        return new User(
                rs.getString(USER_COLUMN_USERNAME),
                rs.getString(USER_COLUMN_LAST_NAME),
                rs.getString(USER_COLUMN_FIRST_NAME),
                rs.getString(USER_COLUMN_PASSWORD),
                rs.getBoolean(USER_COLUMN_ACTIVE),
                rs.getString(USER_COLUMN_EMAIL),
                rs.getString(USER_COLUMN_ACTIVATIONCODE),
                rs.getString(ROLE_COLUMN_NAME));
    }
}
