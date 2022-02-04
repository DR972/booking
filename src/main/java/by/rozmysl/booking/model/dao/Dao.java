package by.rozmysl.booking.model.dao;

import by.rozmysl.booking.exception.DaoException;
import by.rozmysl.booking.model.ModelTypeProvider;
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
     * @param provider ModelTypeProvider
     * @param params   Object parameters
     * @return T object
     * @throws DaoException if there was an error accessing the database
     */
    T findEntity(ModelTypeProvider provider, Object... params) throws DaoException;

    /**
     * Searches for all T objects in the `T` by various parameters.
     *
     * @param provider ModelTypeProvider
     * @param params   Object parameters
     * @return list of T objects
     * @throws DaoException if there was an error accessing the database
     */
    List<T> findListEntities(ModelTypeProvider provider, Object... params) throws DaoException;

    /**
     * Performs various operations (save, update, delete) on the object T in the table 'T`.
     *
     * @param provider ModelTypeProvider
     * @param params   Object parameters
     * @throws DaoException if there was an error accessing the database
     */
    void updateEntity(ModelTypeProvider provider, Object... params) throws DaoException;

    /**
     * Performs various operations (save, update, delete) on the object T in the table 'T` using a transaction.
     *
     * @param provider ModelTypeProvider
     * @param params   Object parameters
     * @throws DaoException if there was an error accessing the database
     */
    void updateEntityUsingTransaction(ModelTypeProvider provider, Object... params) throws DaoException;

    /**
     * Saves information to a database with automatic key generation.
     *
     * @param provider ModelTypeProvider
     * @param params   Object parameters
     * @throws DaoException if there was an error accessing the database
     */
    void saveWithGeneratedKeys(ModelTypeProvider provider, Object... params) throws DaoException;

    /**
     * Gets the number of rows in the database.
     *
     * @param provider ModelTypeProvider
     * @return number of rows
     * @throws DaoException if there was an error accessing the database
     */
    int countNumberEntityRows(ModelTypeProvider provider) throws DaoException;
}