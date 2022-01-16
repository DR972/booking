package by.rozmysl.bookingServlet.model.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Provides a pool of connections to database. Singleton.
 */
public class ConnectionPool {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConnectionPool.class);
    private static final String DATABASE_PATH = "data/database.properties";
    private static final String DATABASE_DRIVER = "db.driver";
    private static final String DATABASE_URL = "db.url";
    private static final String DATABASE_USER = "db.user";
    private static final String DATABASE_PASSWORD = "db.password";
    private static final String DATABASE_POOL_SIZE = "db.poolSize";

    private static final ReentrantLock lock = new ReentrantLock();
    private static final AtomicBoolean isInitialized = new AtomicBoolean(false);
    private static int poolSize;
    private static ConnectionPool instance;
    private final BlockingQueue<ProxyConnection> availableConnections;
    private final BlockingQueue<ProxyConnection> busyConnections;
    private static final Properties properties = new Properties();

    /**
     * The constructor creates a new object ConnectionPool without parameters
     */
    private ConnectionPool() {
        try (InputStream inputStream = ConnectionPool.class.getClassLoader().getResourceAsStream(DATABASE_PATH)) {
            properties.load(inputStream);
            Class.forName(properties.getProperty(DATABASE_DRIVER));
            poolSize = Integer.parseInt(properties.getProperty(DATABASE_POOL_SIZE));
        } catch (IOException | NullPointerException | ClassNotFoundException e) {
            LOGGER.error(String.valueOf(e));
            throw new RuntimeException();
        }
        availableConnections = new ArrayBlockingQueue<>(poolSize);
        busyConnections = new ArrayBlockingQueue<>(poolSize);
        initializeConnectionPool();
    }

    /**
     * Returns ConnectionPool instance. Initialize instance if it doesn't.
     *
     * @return instance
     */
    public static ConnectionPool getInstance() {
        if (!isInitialized.get()) {
            lock.lock();
            try {
                if (instance == null) {
                    instance = new ConnectionPool();
                    isInitialized.set(true);
                    LOGGER.info("Connection pool initialized successfully.");
                }
            } finally {
                lock.unlock();
            }
        }
        return instance;
    }

    /**
     * Initializes the creation of the Connection Pool
     */
    private void initializeConnectionPool() {
        while (availableConnections.size() < poolSize) {
            try {
                availableConnections.add(new ProxyConnection(DriverManager.getConnection(properties.getProperty(DATABASE_URL),
                        properties.getProperty(DATABASE_USER), properties.getProperty(DATABASE_PASSWORD))));
            } catch (SQLException e) {
                LOGGER.error(String.valueOf(e));
                throw new RuntimeException();
            }
        }
    }

    /**
     * Gets the value of the connection property
     *
     * @return connection
     */
    public Connection getConnectionFromPool() {
        ProxyConnection connection = null;
        if (availableConnections.size() > 0) {
            try {
                connection = availableConnections.take();
                busyConnections.put(connection);
            } catch (InterruptedException e) {
                LOGGER.error("There are some problems with get connection", e);
            }
        }
        return connection;
    }

    /**
     * Returns connections to the pool
     *
     * @param connection connection
     */
    public void returnConnectionToPool(Connection connection) {
        if (connection != null && busyConnections.contains((ProxyConnection) connection)) {
            try {
                ProxyConnection proxy = (ProxyConnection) connection;
                busyConnections.remove(proxy);
                availableConnections.put(proxy);
            } catch (InterruptedException e) {
                LOGGER.error("Error while trying to check autocommit.");
            }
        } else {
            LOGGER.error("Connection pool doesn't contain transferred connection.");
        }
    }

    /**
     * Destroys ConnectionPool.
     */
    public void destroy() {
        if (isInitialized.get()) {
            lock.lock();
            try {
                if (instance != null) {
                    clearConnectionQueue(availableConnections);
                    clearConnectionQueue(busyConnections);
                    deregisterDriver();
                } else {
                    LOGGER.error("Attempt to destroy not initialized connection pool instance.");
                }
                instance = null;
                LOGGER.info("Connection pool destroyed successfully.");
            } catch (InterruptedException | SQLException e) {
                LOGGER.error("Can't destroy connection pool.", e);
                throw new RuntimeException();
            } finally {
                lock.unlock();
            }
        } else {
            LOGGER.error("Attempt to destroy not initialized connection pool.");
        }
    }

    /**
     * Clears connection queue and closes connections.
     *
     * @param connectionQueue connection queue
     * @throws InterruptedException if connection cannot be closed
     * @throws SQLException if connection cannot be closed
     */
    private void clearConnectionQueue(BlockingQueue<ProxyConnection> connectionQueue) throws SQLException, InterruptedException {
        while (!connectionQueue.isEmpty()) {
            connectionQueue.take().reallyClose();
        }
    }

    /**
     * Deregistering a Database Driver.
     */
    private void deregisterDriver() {
        DriverManager.getDrivers().asIterator().forEachRemaining(driver -> {
            try {
                DriverManager.deregisterDriver(driver);
            } catch (SQLException e) {
                LOGGER.error("There are some problems with deregister driver");
            }
            LOGGER.info("Deregistration of the driver was successful.");
        });
    }
}
