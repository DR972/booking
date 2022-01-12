package by.rozmysl.bookingServlet.model.db;

import by.rozmysl.bookingServlet.model.dao.Dao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

public class EntityTransaction implements AutoCloseable {
    private static final Logger LOGGER = LoggerFactory.getLogger(EntityTransaction.class);
    private final ProxyConnection connection = ConnectionPool.getInstance().getConnectionFromPool();

    /**
     * Constructs EntityTransaction with at least one dao.
     *
     * @param dao    Dao<T,ID>   dao
     * @param otherDaos Dao<T,ID> other dao
     */
    public EntityTransaction(Dao<?, ?> dao, Dao<?, ?>... otherDaos) {
        setConnection(dao, otherDaos);
    }

    /**
     * Begin transaction.
     */
    public void begin() {
        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            LOGGER.error("Problems with disabling autocommit", e);
        }
    }

    /**
     * End transaction.
     */
    public void end() {
        try {
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            LOGGER.error("Problems with enabling autocommit", e);
        }
        close();
    }

    /**
     * Commit.
     */
    public void commit() {
        try {
            connection.commit();
        } catch (SQLException e) {
            LOGGER.error("Commit has been failed", e);
        }
    }

    /**
     * Rollback.
     */
    public void rollback() {
        try {
            connection.rollback();
        } catch (SQLException e) {
            LOGGER.error("Rollback has been failed", e);
        }
    }

    @Override
    public void close() {
        connection.close();
    }

    /**
     * Sets connection to all dao.
     *
     * @param dao       dao
     * @param otherDaos other dao (optional)
     * @see Dao#setConnection(ProxyConnection)
     */
    private void setConnection(Dao<?, ?> dao, Dao<?, ?>[] otherDaos) {
        dao.setConnection(connection);
        for (Dao<?, ?> anotherDAO : otherDaos) {
            anotherDAO.setConnection(connection);
        }
    }
}
