package by.rozmysl.bookingServlet.model.service.impl;

import by.rozmysl.bookingServlet.exception.DaoException;
import by.rozmysl.bookingServlet.exception.ServiceException;
import by.rozmysl.bookingServlet.model.dao.AdditionalServicesDao;
import by.rozmysl.bookingServlet.model.dao.DaoFactory;
import by.rozmysl.bookingServlet.model.db.ConnectionPool;
import by.rozmysl.bookingServlet.model.db.ProxyConnection;
import by.rozmysl.bookingServlet.model.entity.hotel.AdditionalServices;
import by.rozmysl.bookingServlet.model.service.AdditionalServicesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.List;

import static by.rozmysl.bookingServlet.model.LoggerMessage.*;
import static by.rozmysl.bookingServlet.model.dao.SqlQuery.*;

public class AdditionalServicesServiceImpl implements AdditionalServicesService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AdditionalServicesServiceImpl.class);
    private final AdditionalServicesDao servicesDao;

    public AdditionalServicesServiceImpl() {
        servicesDao = DaoFactory.getInstance().getServicesDao();
    }

    @Override
    public AdditionalServices findById(String type) throws ServiceException {
        try {
            return servicesDao.findEntity(ADDITIONALSERVICES_FIND_BY_ID, MESSAGE_ADDITIONALSERVICES_FIND_BY_ID, type);
//            return servicesDao.findById(type);
        } catch (DaoException e) {
            LOGGER.error(MESSAGE_ADDITIONALSERVICES_FIND_BY_ID, e);
            throw new ServiceException(MESSAGE_ADDITIONALSERVICES_FIND_BY_ID, e);
        }
    }

    @Override
    public List<AdditionalServices> findAll(int pageNumber, int rows) throws ServiceException {
        try {
            return servicesDao.findListEntities(ADDITIONALSERVICES_FIND_ALL, MESSAGE_ADDITIONALSERVICES_FIND_ALL, rows, pageNumber, rows);
//            return servicesDao.findAll(pageNumber, rows);
        } catch (DaoException e) {
            LOGGER.error(MESSAGE_ADDITIONALSERVICES_FIND_ALL, e);
            throw new ServiceException(MESSAGE_ADDITIONALSERVICES_FIND_ALL, e);
        }
    }

    @Override
    public void changeServicePrice(String type, BigDecimal price) throws ServiceException {
        try (final ProxyConnection connection = ConnectionPool.getInstance().getConnectionFromPool()) {
            servicesDao.setConnection(connection);
            servicesDao.updateEntity(ADDITIONALSERVICES_CHANGE_SERVICE_PRICE, MESSAGE_ADDITIONALSERVICES_CHANGE_SERVICE_PRICE,
                    price,
                    type);
//            servicesDao.changeServicePrice(type, price);
        } catch (DaoException e) {
            LOGGER.error(MESSAGE_ADDITIONALSERVICES_CHANGE_SERVICE_PRICE, e);
            throw new ServiceException(MESSAGE_ADDITIONALSERVICES_CHANGE_SERVICE_PRICE, e);
        }
    }
}
