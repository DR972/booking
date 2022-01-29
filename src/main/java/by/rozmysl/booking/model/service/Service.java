package by.rozmysl.booking.model.service;

import by.rozmysl.booking.exception.ServiceException;
import by.rozmysl.booking.model.ModelTypeProvider;
import by.rozmysl.booking.model.entity.Entity;

import java.util.List;

public interface Service<T extends Entity<ID>, ID> {

    /**
     * Provides logic for searching for a T object by parameters.
     *
     * @param cls      Class<T>
     * @param provider ModelTypeProvider
     * @param params   Object parameters
     * @return T object
     * @throws ServiceException if there was an error accessing the database
     */
    T findEntity(Class<T> cls, ModelTypeProvider provider, Object... params) throws ServiceException;

    /**
     * Provides logic for searching for all T objects by various parameters.
     *
     * @param provider ModelTypeProvider
     * @param params   Object parameters
     * @return list of T objects
     * @throws ServiceException if there was an error accessing the database
     */
    List<T> findListEntities(ModelTypeProvider provider, Object... params) throws ServiceException;

    /**
     * Provides logic for updating operations (save, update, delete) on the object T by various parameters.
     *
     * @param provider ModelTypeProvider
     * @param params   Object parameters
     * @throws ServiceException if there was an error accessing the database
     */
    void updateEntity(ModelTypeProvider provider, Object... params) throws ServiceException;

    /**
     * Provides logic for saving the T object with automatic key generation.
     *
     * @param provider ModelTypeProvider
     * @param params   Object parameters
     * @throws ServiceException if there was an error accessing the database
     */
    void saveWithGeneratedKeys(ModelTypeProvider provider, Object... params) throws ServiceException;

    /**
     * Provides logic for counting the number of pages of all T objects.
     *
     * @param provider ModelTypeProvider
     * @return number of rows
     * @throws ServiceException if there was an error accessing the database
     */
    int countNumberEntityRows(ModelTypeProvider provider, int rows) throws ServiceException;
}
