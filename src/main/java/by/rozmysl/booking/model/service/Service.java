package by.rozmysl.booking.model.service;

import by.rozmysl.booking.exception.ServiceException;
import by.rozmysl.booking.model.ModelManager;
import by.rozmysl.booking.model.entity.Entity;

import java.util.List;

public interface Service<T extends Entity<ID>, ID> {

    /**
     * Provides logic for searching for a T object by parameters.
     *
     * @param cls          Class<T>
     * @param modelManager ModelManager
     * @param params       Object parameters
     * @return T object
     * @throws ServiceException if there was an error accessing the database
     */
    T findEntity(Class<T> cls, ModelManager modelManager, Object... params) throws ServiceException;

    /**
     * Provides logic for searching for all T objects by various parameters.
     *
     * @param modelManager ModelManager
     * @param params       Object parameters
     * @return list of T objects
     * @throws ServiceException if there was an error accessing the database
     */
    List<T> findListEntities(ModelManager modelManager, Object... params) throws ServiceException;

    /**
     * Provides logic for updating operations (save, update, delete) on the object T by various parameters.
     *
     * @param modelManager ModelManager
     * @param params       Object parameters
     * @throws ServiceException if there was an error accessing the database
     */
    void updateEntity(ModelManager modelManager, Object... params) throws ServiceException;

    /**
     * Provides logic for saving the T object with automatic key generation.
     *
     * @param modelManager ModelManager
     * @param params       Object parameters
     * @throws ServiceException if there was an error accessing the database
     */
    void saveWithGeneratedKeys(ModelManager modelManager, Object... params) throws ServiceException;

    /**
     * Provides logic for counting the number of pages of all T objects.
     *
     * @param modelManager ModelManager
     * @return number of rows
     * @throws ServiceException if there was an error accessing the database
     */
    int countNumberEntityRows(ModelManager modelManager, int rows) throws ServiceException;
}
