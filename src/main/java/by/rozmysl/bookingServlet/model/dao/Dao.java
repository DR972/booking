package by.rozmysl.bookingServlet.model.dao;

import by.rozmysl.bookingServlet.exception.DaoException;
import by.rozmysl.bookingServlet.model.entity.Entity;

import java.sql.Connection;
import java.util.List;

/**
 * Provides the base model DAO interface.
 *
 * @param <T> indicates that for this instantiation of the DAO, will be used this type of Entity implementation
 */
public interface Dao<T extends Entity<ID>, ID> {

    /**
     * Sets connection to DAO.
     *
     * @param connection connection
     */
    void setConnection(Connection connection);

    /**
     * Searches for the object T in the table 'T' by various parameters.
     *
     * @param sql          the wording of the request to the database
     * @param errorMessage errorMessage in case of an error
     * @param params       Object parameters
     * @return T object
     * @throws DaoException if there was an error accessing the database
     */
    T findEntity(String sql, String errorMessage, Object... params) throws DaoException;

    /**
     * Searches for all T objects in the `T` by various parameters.
     *
     * @param sql          the wording of the request to the database
     * @param errorMessage message in case of an error
     * @param params       Object parameters
     * @return list of T objects
     * @throws DaoException if there was an error accessing the database
     */
    List<T> findListEntities(String sql, String errorMessage, Object... params) throws DaoException;

    /**
     * Performs various operations (save, update, delete) on the object T in the table 'T`.
     *
     * @param sql          the wording of the request to the database
     * @param errorMessage message in case of an error
     * @param params       Object parameters
     * @throws DaoException if there was an error accessing the database
     */
    void updateEntity(String sql, String errorMessage, Object... params) throws DaoException;

    /**
     * Performs various operations (save, update, delete) on the object T in the table 'T` using a transaction.
     *
     * @param sql          the wording of the request to the database
     * @param errorMessage message in case of an error
     * @param params       Object parameters
     * @throws DaoException if there was an error accessing the database
     */
    void updateEntityUsingTransaction(String sql, String errorMessage, Object... params) throws DaoException;

    /**
     * Gets the number of rows in the database.
     *
     * @param sql          the wording of the request to the database
     * @param errorMessage message in case of an error
     * @return number of rows
     * @throws DaoException if there was an error accessing the database
     */
    int countNumberEntityRows(String sql, String errorMessage) throws DaoException;
}