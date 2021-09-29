package by.rozmysl.bookingServlet.db;

import java.sql.*;
import java.util.*;

/**
 * Provides a service for working with a database.
 */
public class ConnectionSource {
    private final Connection con;

    public ConnectionSource(Connection con) {
        this.con = con;
    }

    /**
     * Makes changes to the database.
     *
     * @param sql the wording of the request to the database
     * @throws SQLException if there was an error accessing the database
     */
    public void update(String sql) throws SQLException {
        con.prepareStatement(sql).executeUpdate();
    }

    /**
     * Saves information to a database with automatic key generation.
     *
     * @param sql the wording of the request to the database
     * @throws SQLException if there was an error accessing the database
     */
    public void saveWithGeneratedKeys(String sql) throws SQLException {
        PreparedStatement preparedStatement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.executeUpdate();
        ResultSet tableKeys = preparedStatement.getGeneratedKeys();
        tableKeys.next();
        tableKeys.getInt(1);
    }

    /**
     * Gets information from the database.
     *
     * @param sql the wording of the request to the database
     * @return result set
     * @throws SQLException if there was an error accessing the database
     */
    public Set<Map<String, String>> get(String sql) throws SQLException {
        Set<Map<String, String>> result = new LinkedHashSet<>();
        final ResultSet rs = con.prepareStatement(sql).executeQuery();
        final ResultSetMetaData meta = rs.getMetaData();
        while (rs.next()) {
            Map<String, String> data = new HashMap<>();
            for (int i = 1; i <= meta.getColumnCount(); i++) {
                String key = meta.getColumnName(i);
                data.put(key, rs.getString(key));
            }
            result.add(data);
        }
        return result;
    }

    /**
     * Gets the number of rows in the database.
     *
     * @param sql the wording of the request to the database
     * @return number of rows
     * @throws SQLException if there was an error accessing the database
     */
    public int countRows(String sql) throws SQLException {
        final ResultSet rs = con.prepareStatement(sql).executeQuery();
        rs.next();
        return rs.getInt("count");
    }
}
