package by.rozmysl.bookingServlet.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Provides a pool of connections to database. Singleton.
 */
public class ConnectionPool {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConnectionPool.class);
    private final List<Connection> availableConnections = new ArrayList<>();
    private static ConnectionPool instance;
    private static final Properties properties = new Properties();
    private static final AtomicBoolean isInitialized = new AtomicBoolean(false);

    /**
     * The constructor creates a new object ConnectionPool without parameters
     */
    private ConnectionPool() {
        try {
            properties.load(new FileInputStream("src/main/resources/data/database.properties"));
            Class.forName(properties.getProperty("db.driver"));
        } catch (IOException | ClassNotFoundException e) {
            LOGGER.error(String.valueOf(e));
        }
        initializeConnectionPool();
    }

    /**
     * Returns ConnectionPool instance. Initialize instance if it doesn't.
     *
     * @return instance
     */
    public static ConnectionPool getInstance() {
        if (!isInitialized.get()) {
            if (instance == null) {
                instance = new ConnectionPool();
                isInitialized.set(true);
                LOGGER.info("Connection pool initialized successfully.");
            }
        }
        return instance;
    }

    /**
     * Initializes the creation of the Connection Pool
     */
    private void initializeConnectionPool() {
        while (!checkIfConnectionPoolIsFull()) {
            try {
                availableConnections.add(DriverManager.getConnection(properties.getProperty("db.url"),
                        properties.getProperty("db.user"), properties.getProperty("db.password")));
            } catch (SQLException e) {
                LOGGER.error(String.valueOf(e));
            }
        }
    }

    /**
     * Checks whether the connection pool Is full
     *
     * @return verification result
     */
    private boolean checkIfConnectionPoolIsFull() {
        return availableConnections.size() >= Integer.parseInt(properties.getProperty("db.poolSize"));
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
