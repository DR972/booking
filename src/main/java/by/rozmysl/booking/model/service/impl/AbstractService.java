package by.rozmysl.booking.model.service.impl;

import by.rozmysl.booking.exception.DaoException;
import by.rozmysl.booking.exception.ServiceException;
import by.rozmysl.booking.model.ModelTypeProvider;
import by.rozmysl.booking.model.entity.Entity;
import by.rozmysl.booking.model.service.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public abstract class AbstractService<T extends Entity<ID>, ID> implements Service<T, ID> {
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractService.class);

    /**
     * Provides logic for searching for a T object by parameters.
     *
     * @param cls      Class<T>
     * @param provider ModelTypeProvider
     * @param params   Object parameters
     * @return T object
     * @throws ServiceException if there was an error accessing the database
     */
    @Override
    public T findEntity(Class<T> cls, ModelTypeProvider provider, Object... params) throws ServiceException {
        try {
            return cls.cast(provider.getDao().findEntity(provider, params));
        } catch (DaoException e) {
            LOGGER.error(provider.getLoggerMessage(), e);
            throw new ServiceException(provider.getLoggerMessage(), e);
        }
    }

    /**
     * Provides logic for searching for all T objects by various parameters.
     *
     * @param provider ModelTypeProvider
     * @param params   Object parameters
     * @return list of T objects
     * @throws ServiceException if there was an error accessing the database
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<T> findListEntities(ModelTypeProvider provider, Object... params) throws ServiceException {
        try {
            return (List<T>) provider.getDao().findListEntities(provider, params);
        } catch (DaoException e) {
            LOGGER.error(provider.getLoggerMessage(), e);
            throw new ServiceException(provider.getLoggerMessage(), e);
        }
    }

    /**
     * Provides logic for updating operations (save, update, delete) on the object T by various parameters.
     *
     * @param provider ModelTypeProvider
     * @param params   Object parameters
     * @throws ServiceException if there was an error accessing the database
     */
    @Override
    public void updateEntity(ModelTypeProvider provider, Object... params) throws ServiceException {
        try {
            provider.getDao().updateEntity(provider, params);
        } catch (DaoException e) {
            LOGGER.error(provider.getLoggerMessage(), e);
            throw new ServiceException(provider.getLoggerMessage(), e);
        }
    }

    /**
     * Provides logic for saving the T object with automatic key generation.
     *
     * @param provider ModelTypeProvider
     * @param params   Object parameters
     * @throws ServiceException if there was an error accessing the database
     */
    @Override
    public void saveWithGeneratedKeys(ModelTypeProvider provider, Object... params) throws ServiceException {
        try {
            provider.getDao().saveWithGeneratedKeys(provider, params);
        } catch (DaoException e) {
            LOGGER.error(provider.getLoggerMessage(), e);
            throw new ServiceException(provider.getLoggerMessage(), e);
        }
    }

    /**
     * Provides logic for counting the number of pages of all T objects.
     *
     * @param provider ModelTypeProvider
     * @return number of rows
     * @throws ServiceException if there was an error accessing the database
     */
    @Override
    public int countNumberEntityRows(ModelTypeProvider provider, int rows) throws ServiceException {
        try {
            return (int) Math.ceil((float) provider.getDao().countNumberEntityRows(provider) / rows);
        } catch (DaoException e) {
            LOGGER.error(provider.getLoggerMessage(), e);
            throw new ServiceException(provider.getLoggerMessage(), e);
        }
    }
}
