package by.rozmysl.bookingServlet.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Provides a pool of connections to database. Singleton.
 */
public class JdbcConnectionPool {
    private static final Logger logger = LoggerFactory.getLogger(JdbcConnectionPool.class);
    private final List<Connection> availableConnections = new ArrayList<>();

    /**
     * The constructor creates a new object JdbcConnectionPool
     */
    public JdbcConnectionPool() {
        initializeConnectionPool();
    }

    /**
     * Initializes the creation of the Connection Pool
     */
    private void initializeConnectionPool() {
        while (!checkIfConnectionPoolIsFull()) {
            availableConnections.add(createNewConnectionForPool());
        }
        logger.info("Connection pool initialized successfully.");
    }

    /**
     * Checks whether the connection pool Is full
     *
     * @return verification result
     */
    private synchronized boolean checkIfConnectionPoolIsFull() {
        final int MAX_POOL_SIZE = Configuration.getInstance().DB_MAX_CONNECTIONS;
        return availableConnections.size() >= MAX_POOL_SIZE;
    }

    /**
     * Creates a new connection for the pool
     *
     * @return connection
     */
    private Connection createNewConnectionForPool() {
        Configuration config = Configuration.getInstance();
        try {
            Class.forName(config.DB_DRIVER);
            return DriverManager.getConnection(config.DB_URL, config.DB_USER_NAME, config.DB_PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            logger.error(String.valueOf(e));
        }
        return null;
    }

    /**
     * Gets the value of the connection property
     *
     * @return connection
     */
    public synchronized Connection getConnectionFromPool() {
        Connection connection = null;
        if (availableConnections.size() > 0) {
            connection = availableConnections.get(0);
            availableConnections.remove(0);
        }
        return connection;
    }

    /**
     * Returns connections to the pool
     *
     * @param connection connection
     */
    public synchronized void returnConnectionToPool(Connection connection) {
        availableConnections.add(connection);
    }
}
