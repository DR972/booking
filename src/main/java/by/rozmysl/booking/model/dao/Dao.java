package by.rozmysl.booking.model.dao;

import by.rozmysl.booking.exception.DaoException;
import by.rozmysl.booking.model.ModelManager;
import by.rozmysl.booking.model.entity.Entity;

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
     * @param modelManager ModelManager
     * @param params       Object parameters
     * @return T object
     * @throws DaoException if there was an error accessing the database
     */
    T findEntity(ModelManager modelManager, Object... params) throws DaoException;

    /**
     * Searches for all T objects in the `T` by various parameters.
     *
     * @param modelManager ModelManager
     * @param params       Object parameters
     * @return list of T objects
     * @throws DaoException if there was an error accessing the database
     */
    List<T> findListEntities(ModelManager modelManager, Object... params) throws DaoException;

    /**
     * Performs various operations (save, update, delete) on the object T in the table 'T`.
     *
     * @param modelManager ModelManager
     * @param params       Object parameters
     * @throws DaoException if there was an error accessing the database
     */
    void updateEntity(ModelManager modelManager, Object... params) throws DaoException;

    /**
     * Performs various operations (save, update, delete) on the object T in the table 'T` using a transaction.
     *
     * @param modelManager ModelManager
     * @param params       Object parameters
     * @throws DaoException if there was an error accessing the database
     */
    void updateEntityUsingTransaction(ModelManager modelManager, Object... params) throws DaoException;

    /**
     * Saves information to a database with automatic key generation.
     *
     * @param modelManager ModelManager
     * @param params       Object parameters
     * @throws DaoException if there was an error accessing the database
     */
    void saveWithGeneratedKeys(ModelManager modelManager, Object... params) throws DaoException;

    /**
     * Gets the number of rows in the database.
     *
     * @param modelManager ModelManager
     * @return number of rows
     * @throws DaoException if there was an error accessing the database
     */
    int countNumberEntityRows(ModelManager modelManager) throws DaoException;
}