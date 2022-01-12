package by.rozmysl.bookingServlet.model.dao;

import by.rozmysl.bookingServlet.exception.DaoException;
import by.rozmysl.bookingServlet.model.db.ProxyConnection;
import by.rozmysl.bookingServlet.model.entity.Entity;
import by.rozmysl.bookingServlet.model.entity.hotel.Room;

import java.util.List;

/**
 * Provides the base model DAO interface.
 *
 * @param <T> indicates that for this instantiation of the DAO, will be used this type of Entity implementation
 */
public interface Dao<T extends Entity<ID>, ID> {

    /**
     * Sets connection to DAO.
     *
     * @param connection connection
     */
    void setConnection(ProxyConnection  connection);

    T findEntity(String sql, String message, Object ... params) throws DaoException;

    List<T> findListEntities(String sql, String message, Object ... params) throws DaoException;

    void updateEntity(String sql, String message, Object ... params) throws DaoException;

    int countEntitiesRows(String sql, String message) throws DaoException;

//
//    /**
//     * Searches for the T object in the `T` table by id
//     *
//     * @param id of the T object
//     * @return T object
//     * @throws DaoException if there was an error accessing the database
//     */
//    T findById(ID id) throws DaoException;

//    /**
//     * Searches for all T objects in the `T` table
//     *
//     * @param pageNumber number of the pageNumber to return
//     * @param rows       number of rows per pageNumber
//     * @return list of T objects
//     * @throws DaoException if there was an error accessing the database
//     */
//    List<T> findAll(int pageNumber, int rows) throws DaoException;



//    /**
//     * Saves the T object in the `T` table
//     *
//     * @param t id of the T object
//     * @throws DaoException if there was an error accessing the database
//     */
//    void save(T t) throws DaoException;
//
//    /**
//     * Deletes the T object in the `T` table
//     *
//     * @param id id of the T object
//     * @throws DaoException if there was an error accessing the database
//     */
//    void delete(ID id) throws DaoException;
}