package by.rozmysl.booking.model.service.impl;

import by.rozmysl.booking.exception.DaoException;
import by.rozmysl.booking.exception.ServiceException;
import by.rozmysl.booking.model.ModelManager;
import by.rozmysl.booking.model.entity.Entity;
import by.rozmysl.booking.model.service.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public abstract class ServiceImpl<T extends Entity<ID>, ID> implements Service<T, ID> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceImpl.class);

    /**
     * Provides logic for searching for a T object by parameters.
     *
     * @param cls          Class<T>
     * @param modelManager ModelManager
     * @param params       Object parameters
     * @return T object
     * @throws ServiceException if there was an error accessing the database
     */
    @Override
    public T findEntity(Class<T> cls, ModelManager modelManager, Object... params) throws ServiceException {
        try {
            return cls.cast(modelManager.getDao().findEntity(modelManager, params));
        } catch (DaoException e) {
            LOGGER.error(modelManager.getLoggerMessage(), e);
            throw new ServiceException(modelManager.getLoggerMessage(), e);
        }
    }

    /**
     * Provides logic for searching for all T objects by various parameters.
     *
     * @param modelManager ModelManager
     * @param params       Object parameters
     * @return list of T objects
     * @throws ServiceException if there was an error accessing the database
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<T> findListEntities(ModelManager modelManager, Object... params) throws ServiceException {
        try {
            return (List<T>) modelManager.getDao().findListEntities(modelManager, params);
        } catch (DaoException e) {
            LOGGER.error(modelManager.getLoggerMessage(), e);
            throw new ServiceException(modelManager.getLoggerMessage(), e);
        }
    }

    /**
     * Provides logic for updating operations (save, update, delete) on the object T by various parameters.
     *
     * @param modelManager ModelManager
     * @param params       Object parameters
     * @throws ServiceException if there was an error accessing the database
     */
    @Override
    public void updateEntity(ModelManager modelManager, Object... params) throws ServiceException {
        try {
            modelManager.getDao().updateEntity(modelManager, params);
        } catch (DaoException e) {
            LOGGER.error(modelManager.getLoggerMessage(), e);
            throw new ServiceException(modelManager.getLoggerMessage(), e);
        }
    }

    /**
     * Provides logic for saving the T object with automatic key generation.
     *
     * @param modelManager ModelManager
     * @param params       Object parameters
     * @throws ServiceException if there was an error accessing the database
     */
    @Override
    public void saveWithGeneratedKeys(ModelManager modelManager, Object... params) throws ServiceException {
        try {
            modelManager.getDao().saveWithGeneratedKeys(modelManager, params);
        } catch (DaoException e) {
            LOGGER.error(modelManager.getLoggerMessage(), e);
            throw new ServiceException(modelManager.getLoggerMessage(), e);
        }
    }

    /**
     * Provides logic for counting the number of pages of all T objects.
     *
     * @param modelManager ModelManager
     * @return number of rows
     * @throws ServiceException if there was an error accessing the database
     */
    @Override
    public int countNumberEntityRows(ModelManager modelManager, int rows) throws ServiceException {
        try {
            return (int) Math.ceil((float) modelManager.getDao().countNumberEntityRows(modelManager) / rows);
        } catch (DaoException e) {
            LOGGER.error(modelManager.getLoggerMessage(), e);
            throw new ServiceException(modelManager.getLoggerMessage(), e);
        }
    }
}
