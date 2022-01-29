package by.rozmysl.booking.model.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Represents an operation that accepts a single {@link PreparedStatement}
 * argument and returns no result.
 */
@FunctionalInterface
public interface StatementConsumer {

    /**
     * Performs this operation on given {@link PreparedStatement}.
     *
     * @param statement {@link PreparedStatement} argument
     * @throws SQLException if an error occurred during the operation
     */
    void accept(PreparedStatement statement) throws SQLException;

}