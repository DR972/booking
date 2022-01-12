package by.rozmysl.bookingServlet.model.dao.impl;

import by.rozmysl.bookingServlet.exception.DaoException;
import by.rozmysl.bookingServlet.model.dao.Dao;
import by.rozmysl.bookingServlet.model.dao.StatementConsumer;
import by.rozmysl.bookingServlet.model.db.ConnectionPool;
import by.rozmysl.bookingServlet.model.db.ProxyConnection;
import by.rozmysl.bookingServlet.model.entity.Entity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.*;

import static by.rozmysl.bookingServlet.model.dao.ColumnName.*;

public abstract class DaoImpl<T extends Entity<ID>, ID> implements Dao<T, ID> {
    private static final Logger LOGGER = LoggerFactory.getLogger(DaoImpl.class);
    protected ProxyConnection connection;

    @Override
    public void setConnection(ProxyConnection connection) {
        this.connection = connection;
    }

    @Override
    public T findEntity(String sql, String message, Object... params) throws DaoException {
        List<T> t = getResultSet(sql, message, statement -> setStatement(statement, params));
        return t.size() != 0 ? t.get(0) : null;
    }

    @Override
    public List<T> findListEntities(String sql, String message, Object... params) throws DaoException {
        return getResultSet(sql, message, statement -> setStatement(statement, params));
    }

    @Override
    public void updateEntity(String sql, String message, Object... params) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            setStatement(statement, params);
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error(message, e);
            throw new DaoException(message, e);
        }
    }

    @Override
    public int countEntitiesRows(String sql, String message) throws DaoException {
        try (final Connection connection = ConnectionPool.getInstance().getConnectionFromPool();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             final ResultSet resultSet = preparedStatement.executeQuery()) {
            resultSet.next();
            return resultSet.getInt(ROWS_COUNT);
        } catch (SQLException e) {
            LOGGER.error(message, e);
            throw new DaoException(message, e);
        }
    }

    /**
     * Saves information to a database with automatic key generation.
     * <p>
     * //     * @param statementConsumer StatementConsumer
     *
     * @param sql     the wording of the request to the database
     * @param message in case of an error
     * @throws DaoException if there was an error accessing the database
     */
    public void saveWithGeneratedKeys(String sql, String message, Object... params) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            setStatement(statement, params);
            statement.executeUpdate();
            try (final ResultSet tableKeys = statement.getGeneratedKeys()) {
                tableKeys.next();
                tableKeys.getInt(1);
            }
        } catch (SQLException e) {
            LOGGER.error(message, e);
            throw new DaoException(message, e);
        }
    }

    abstract T buildEntity(ResultSet resultSet) throws SQLException;

    public List<T> getResultSet(String sql, String message, StatementConsumer statementConsumer) throws DaoException {
        List<T> t = new ArrayList<>();
        try (final Connection connection = ConnectionPool.getInstance().getConnectionFromPool();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            statementConsumer.accept(preparedStatement);
            try (final ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    t.add(buildEntity(resultSet));
                }
            }
        } catch (SQLException e) {
            LOGGER.error(message, e);
            throw new DaoException(message, e);
        }
        return t;
    }

    private void setStatement(PreparedStatement statement, Object... params) throws SQLException {
        for (int i = 0; i < params.length; i++) {
            statement.setObject(i + 1, params[i]);
        }
    }


//    /**
//     * Get a list of query results from the 'T' table
//     *
//     * @param sql               the wording of the request to the database
//     * @param message           in case of an error
//     * @param statementConsumer StatementConsumer
//     * @return list of T objects
//     * @throws DaoException if there was an error accessing the database
//     */
//    abstract List<T> getResultSet(String sql, String message, StatementConsumer statementConsumer) throws DaoException;

    //    /**
//     * Gets information from the database.
//     *
//     * @param statementConsumer StatementConsumer
//     * @param sql               the wording of the request to the database
//     * @param message           in case of an error
//     * @return result set
//     * @throws DaoException if there was an error accessing the database
//     */
//    public Set<Map<String, String>> get(String sql, String message, StatementConsumer statementConsumer) throws DaoException {
//        Set<Map<String, String>> result = new LinkedHashSet<>();
//        try (final Connection connection = ConnectionPool.getInstance().getConnectionFromPool();
//             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
//            statementConsumer.accept(preparedStatement);
//            try (final ResultSet resultSet = preparedStatement.executeQuery()) {
//                final ResultSetMetaData meta = resultSet.getMetaData();
//                while (resultSet.next()) {
//                    Map<String, String> data = new HashMap<>();
//                    for (int i = 1; i <= meta.getColumnCount(); i++) {
//                        String key = meta.getColumnName(i);
//                        data.put(key, resultSet.getString(key));
//                    }
//                    result.add(data);
//                }
//            }
//        } catch (SQLException e) {
//            LOGGER.error(message, e);
//            throw new DaoException(message, e);
//        }
//        return result;
//    }
//    private final String queryFindById;
//    private final String messageForQueryFindById;
//    private final String queryFindAll;
//    private final String messageForQueryFindAll;
//    private final String queryDelete;
//    private final String messageForQueryDelete;

//    /**
//     * Constructs dao with basic queries.
//     *
//     * @param queryFindById           queryFindById
//     * @param messageForQueryFindById messageForQueryFindById
//     * @param queryFindAll            queryFindAll
//     * @param messageForQueryFindAll  messageForQueryFindAll
//     * @param queryDelete             queryDelete
//     * @param messageForQueryDelete   messageForQueryDelete
//     */
//    DaoImpl(String queryFindById, String messageForQueryFindById, String queryFindAll, String messageForQueryFindAll,
//            String queryDelete, String messageForQueryDelete) {
//        this.queryFindById = queryFindById;
//        this.messageForQueryFindById = messageForQueryFindById;
//        this.queryFindAll = queryFindAll;
//        this.messageForQueryFindAll = messageForQueryFindAll;
//        this.queryDelete = queryDelete;
//        this.messageForQueryDelete = messageForQueryDelete;
//    }

//    /**
//     * Searches for the T object in the `T` table by id
//     *
//     * @param id of the T object
//     * @return T object
//     * @throws DaoException if there was an error accessing the database
//     */
//    @Override
//    public T findById(ID id) throws DaoException {
//        List<T> t = getResultSet(queryFindById, messageForQueryFindById, statement -> statement.setObject(1, id));
//        return t.size() != 0 ? t.get(0) : null;
//    }

//    /**
//     * Searches for all T objects in the `T` table
//     *
//     * @param pageNumber number of the pageNumber to return
//     * @param rows       number of rows per pageNumber
//     * @return list of T objects
//     * @throws DaoException if there was an error accessing the database
//     */
//    @Override
//    public List<T> findAll(int pageNumber, int rows) throws DaoException {
//        return getResultSet(queryFindAll, messageForQueryFindAll, statement -> {
//            statement.setInt(1, rows);
//            statement.setInt(2, pageNumber);
//            statement.setInt(3, rows);
//        });
//    }

//    /**
//     * Saves the T object in the `T` table
//     *
//     * @param t id of the T object
//     * @throws DaoException if there was an error accessing the database
//     */
//    @Override
//    public void save(T t) throws DaoException {
//    }
//
//    /**
//     * Deletes the T object in the `T` table
//     *
//     * @param id id of the T object
//     * @throws DaoException if there was an error accessing the database
//     */
//    @Override
//    public void delete(ID id) throws DaoException {
//        update(connection, queryDelete, messageForQueryDelete, statement -> statement.setObject(1, id));
//    }

//
//    /**
//     * Makes changes to the database.
//     *
//     * @param statementConsumer StatementConsumer
//     * @param sql               the wording of the request to the database
//     * @param message           in case of an error
//     * @throws DaoException if there was an error accessing the database
//     */
//    public void update(Connection connection, String sql, String message, StatementConsumer statementConsumer) throws DaoException {
//        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
//            statementConsumer.accept(preparedStatement);
//            preparedStatement.executeUpdate();
//        } catch (SQLException e) {
//            LOGGER.error(message, e);
//            throw new DaoException(message, e);
//        }
//    }


//    public void saveWithGeneratedKeys(Connection connection, String sql, String message, StatementConsumer statementConsumer) throws DaoException {
//        try (PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
//            statementConsumer.accept(preparedStatement);
//            preparedStatement.executeUpdate();
//            try (final ResultSet tableKeys = preparedStatement.getGeneratedKeys()) {
//                tableKeys.next();
//                tableKeys.getInt(1);
//            }
//        } catch (SQLException e) {
//            LOGGER.error(message, e);
//            throw new DaoException(message, e);
//        }

//    /**
//     * Gets the number of rows in the database.
//     *
//     * @param sql     the wording of the request to the database
//     * @param message in case of an error
//     * @return number of rows
//     * @throws DaoException if there was an error accessing the database
//     */
//    public int countRows(String sql, String message) throws DaoException {
//        try (final Connection connection = ConnectionPool.getInstance().getConnectionFromPool();
//             PreparedStatement preparedStatement = connection.prepareStatement(sql);
//             final ResultSet resultSet = preparedStatement.executeQuery()) {
//            resultSet.next();
//            return resultSet.getInt(ROWS_COUNT);
//        } catch (SQLException e) {
//            LOGGER.error(message, e);
//            throw new DaoException(message, e);
//        }
//    }
}
