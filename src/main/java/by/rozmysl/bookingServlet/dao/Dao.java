package by.rozmysl.bookingServlet.dao;

import java.sql.SQLException;
import java.util.List;

/**
 * Provides the base model DAO interface.
 *
 * @param <T> indicates that for this instantiation of the DAO, will be used this type of Entity implementation
 */
public interface Dao<T, Id> {

    /**
     * Searches for the T object in the `T` table by id
     *
     * @param Id of the T object
     * @return T object
     * @throws SQLException if there was an error accessing the database
     */
    T findById(Id Id) throws SQLException;

    /**
     * Searches for all T objects in the `T` table
     *
     * @param page number of the page to return
     * @param rows number of rows per page
     * @return list of T objects
     * @throws SQLException if there was an error accessing the database
     */
    List<T> findAll(int page, int rows) throws SQLException;

    /**
     * Saves the T object in the `T` table
     *
     * @param t id of the T object
     * @return T object
     * @throws SQLException if there was an error accessing the database
     */
    T save(T t) throws SQLException;

    /**
     * Deletes the T object in the `T` table
     *
     * @param Id id of the T object
     * @throws SQLException if there was an error accessing the database
     */
    void delete(Id Id) throws SQLException;
}