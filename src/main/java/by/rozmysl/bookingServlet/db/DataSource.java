package by.rozmysl.bookingServlet.db;

import java.sql.Connection;

/**
 * Provides a service for working with a connection pool.
 */
public class DataSource {
    static JdbcConnectionPool pool = new JdbcConnectionPool();

    /**
     * Gets the value of the connection property
     *
     * @return connection
     */
    public static Connection getConnection() {
        return pool.getConnectionFromPool();
    }

    /**
     * Returns connections to the pool
     *
     * @param connection connection
     */
    public static void returnConnection(Connection connection) {
        pool.returnConnectionToPool(connection);
    }
}
