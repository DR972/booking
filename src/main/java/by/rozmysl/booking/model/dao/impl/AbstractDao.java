package by.rozmysl.booking.model.dao.impl;

import by.rozmysl.booking.exception.DaoException;
import by.rozmysl.booking.model.ModelTypeProvider;
import by.rozmysl.booking.model.dao.Dao;
import by.rozmysl.booking.model.dao.StatementConsumer;
import by.rozmysl.booking.model.db.ConnectionPool;
import by.rozmysl.booking.model.entity.Entity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.*;

import static by.rozmysl.booking.model.dao.ColumnName.*;

/**
 * Provides the base model implementation for `T` table DAO.
 */
public abstract class AbstractDao<T extends Entity<ID>, ID> implements Dao<T, ID> {
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractDao.class);
    private Connection connection;

    /**
     * Sets connection to DAO.
     *
     * @param connection connection
     */
    @Override
    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    /**
     * Searches for the object T in the table 'T' by various parameters.
     *
     * @param provider ModelTypeProvider
     * @param params   Object parameters
     * @return T object
     * @throws DaoException if there was an error accessing the database
     */
    @Override
    public T findEntity(ModelTypeProvider provider, Object... params) throws DaoException {
        List<T> entities = getResultSet(provider, statement -> setStatement(statement, params));
        return !entities.isEmpty() ? entities.get(0) : null;
    }

    /**
     * Searches for all T objects in the `T` by various parameters.
     *
     * @param provider ModelTypeProvider
     * @param params   Object parameters
     * @return list of T objects
     * @throws DaoException if there was an error accessing the database
     */
    @Override
    public List<T> findListEntities(ModelTypeProvider provider, Object... params) throws DaoException {
        return getResultSet(provider, statement -> setStatement(statement, params));
    }

    /**
     * Performs various operations (save, update, delete) on the object T in the table 'T`.
     *
     * @param provider ModelTypeProvider
     * @param params   Object parameters
     * @throws DaoException if there was an error accessing the database
     */
    @Override
    public void updateEntity(ModelTypeProvider provider, Object... params) throws DaoException {
        try (final Connection connection = ConnectionPool.getInstance().getConnectionFromPool();
             PreparedStatement statement = connection.prepareStatement(provider.getQuery())) {
            setStatement(statement, params);
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error(provider.getLoggerMessage(), e);
            throw new DaoException(provider.getLoggerMessage(), e);
        }
    }

    /**
     * Performs various operations (save, update, delete) on the object T in the table 'T` using a transaction.
     *
     * @param provider ModelTypeProvider
     * @param params   Object parameters
     * @throws DaoException if there was an error accessing the database
     */
    @Override
    public void updateEntityUsingTransaction(ModelTypeProvider provider, Object... params) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(provider.getQuery())) {
            setStatement(statement, params);
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error(provider.getLoggerMessage(), e);
            throw new DaoException(provider.getLoggerMessage(), e);
        }
    }

    /**
     * Saves information to a database with automatic key generation.
     *
     * @param provider ModelTypeProvider
     * @param params   Object parameters
     * @throws DaoException if there was an error accessing the database
     */
    @Override
    public void saveWithGeneratedKeys(ModelTypeProvider provider, Object... params) throws DaoException {
        try (final Connection connection = ConnectionPool.getInstance().getConnectionFromPool();
             PreparedStatement statement = connection.prepareStatement(provider.getQuery(), Statement.RETURN_GENERATED_KEYS)) {
            setStatement(statement, params);
            statement.executeUpdate();
            try (final ResultSet tableKeys = statement.getGeneratedKeys()) {
                tableKeys.next();
                tableKeys.getInt(1);
            }
        } catch (SQLException e) {
            LOGGER.error(provider.getLoggerMessage(), e);
            throw new DaoException(provider.getLoggerMessage(), e);
        }
    }

    /**
     * Gets the number of rows in the database.
     *
     * @param provider ModelTypeProvider
     * @return number of rows
     * @throws DaoException if there was an error accessing the database
     */
    @Override
    public int countNumberEntityRows(ModelTypeProvider provider) throws DaoException {
        try (final Connection connection = ConnectionPool.getInstance().getConnectionFromPool();
             PreparedStatement preparedStatement = connection.prepareStatement(provider.getQuery());
             final ResultSet resultSet = preparedStatement.executeQuery()) {
            resultSet.next();
            return resultSet.getInt(ROWS_COUNT);
        } catch (SQLException e) {
            LOGGER.error(provider.getLoggerMessage(), e);
            throw new DaoException(provider.getLoggerMessage(), e);
        }
    }

    /**
     * Create T entity.
     *
     * @param resultSet ResultSet
     * @return T object
     * @throws SQLException if there was an error accessing the database
     */
    abstract T buildEntity(ResultSet resultSet) throws SQLException;

    /**
     * Get a list of query results from the 'T' table.
     *
     * @param provider          ModelTypeProvider
     * @param statementConsumer StatementConsumer
     * @return list of T objects
     * @throws DaoException if there was an error accessing the database
     */
    private List<T> getResultSet(ModelTypeProvider provider, StatementConsumer statementConsumer) throws DaoException {
        List<T> entities = new ArrayList<>();
        try (final Connection connection = ConnectionPool.getInstance().getConnectionFromPool();
             PreparedStatement preparedStatement = connection.prepareStatement(provider.getQuery())) {
            statementConsumer.accept(preparedStatement);
            try (final ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    entities.add(buildEntity(resultSet));
                }
            }
        } catch (SQLException e) {
            LOGGER.error(provider.getLoggerMessage(), e);
            throw new DaoException(provider.getLoggerMessage(), e);
        }
        return entities;
    }

    /**
     * Sets parameter values to the PreparedStatement object.
     *
     * @param statement PreparedStatement
     * @param params    Object parameters
     * @throws SQLException if there was an error accessing the database
     */
    private void setStatement(PreparedStatement statement, Object... params) throws SQLException {
        for (int i = 0; i < params.length; i++) {
            statement.setObject(i + 1, params[i]);
        }
    }
}
