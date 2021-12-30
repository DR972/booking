package by.rozmysl.bookingServlet.model.dao;

import by.rozmysl.bookingServlet.exception.DaoException;

import java.util.List;

/**
 * Provides the base model DAO interface.
 *
 * @param <T> indicates that for this instantiation of the DAO, will be used this type of Entity implementation
 */
public interface Dao<T, ID> {

    /**
     * Searches for the T object in the `T` table by id
     *
     * @param id of the T object
     * @return T object
     * @throws DaoException if there was an error accessing the database
     */
    T findById(ID id) throws DaoException;

    /**
     * Searches for all T objects in the `T` table
     *
     * @param page number of the page to return
     * @param rows number of rows per page
     * @return list of T objects
     * @throws DaoException if there was an error accessing the database
     */
    List<T> findAll(int page, int rows) throws DaoException;

    /**
     * Saves the T object in the `T` table
     *
     * @param t id of the T object
     * @return T object
     * @throws DaoException if there was an error accessing the database
     */
    T save(T t) throws DaoException;

    /**
     * Deletes the T object in the `T` table
     *
     * @param Id id of the T object
     * @throws DaoException if there was an error accessing the database
     */
    void delete(ID Id) throws DaoException;
}