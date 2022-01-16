package by.rozmysl.booking.model.service.impl;

import by.rozmysl.booking.exception.DaoException;
import by.rozmysl.booking.exception.ServiceException;
import by.rozmysl.booking.model.dao.AdditionalServicesDao;
import by.rozmysl.booking.model.dao.DaoFactory;
import by.rozmysl.booking.model.entity.hotel.AdditionalServices;
import by.rozmysl.booking.model.service.AdditionalServicesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.List;

import static by.rozmysl.booking.model.util.LoggerMessageError.*;
import static by.rozmysl.booking.model.util.SqlQuery.*;

/**
 * Provides logic for working with data sent to the `AdditionalServices` table DAO.
 */
public class AdditionalServicesServiceImpl implements AdditionalServicesService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AdditionalServicesServiceImpl.class);
    private final AdditionalServicesDao servicesDao;

    /**
     * The constructor creates a new object AdditionalServicesServiceImpl without property.
     */
    public AdditionalServicesServiceImpl() {
        servicesDao = DaoFactory.getInstance().getServicesDao();
    }

    /**
     * Provides logic for searching for the AdditionalServices object by id
     *
     * @param type id of the AdditionalServices object
     * @return AdditionalServices object
     * @throws ServiceException if the operation failed
     */
    @Override
    public AdditionalServices findById(String type) throws ServiceException {
        try {
            return servicesDao.findEntity(ADDITIONALSERVICES_FIND_BY_ID, MESSAGE_ADDITIONALSERVICES_FIND_BY_ID, type);
        } catch (DaoException e) {
            LOGGER.error(MESSAGE_ADDITIONALSERVICES_FIND_BY_ID, e);
            throw new ServiceException(MESSAGE_ADDITIONALSERVICES_FIND_BY_ID, e);
        }
    }

    /**
     * Provides logic for searching for all AdditionalServices objects.
     *
     * @param pageNumber number of the page to return
     * @param rows       number of rows per page
     * @return list of AdditionalServices objects
     * @throws ServiceException if the operation failed
     */
    @Override
    public List<AdditionalServices> findAll(int pageNumber, int rows) throws ServiceException {
        try {
            return servicesDao.findListEntities(ADDITIONALSERVICES_FIND_ALL, MESSAGE_ADDITIONALSERVICES_FIND_ALL, rows, pageNumber, rows);
        } catch (DaoException e) {
            LOGGER.error(MESSAGE_ADDITIONALSERVICES_FIND_ALL, e);
            throw new ServiceException(MESSAGE_ADDITIONALSERVICES_FIND_ALL, e);
        }
    }

    /**
     * Provides logic for changing the price of the Additional Service.
     *
     * @param type  id of the Additional Service
     * @param price new price
     * @throws ServiceException if the operation failed
     */
    @Override
    public void changeServicePrice(String type, BigDecimal price) throws ServiceException {
        try {
            servicesDao.updateEntity(ADDITIONALSERVICES_CHANGE_SERVICE_PRICE, MESSAGE_ADDITIONALSERVICES_CHANGE_SERVICE_PRICE,
                    price,
                    type);
        } catch (DaoException e) {
            LOGGER.error(MESSAGE_ADDITIONALSERVICES_CHANGE_SERVICE_PRICE, e);
            throw new ServiceException(MESSAGE_ADDITIONALSERVICES_CHANGE_SERVICE_PRICE, e);
        }
    }
}
