package by.rozmysl.bookingServlet.model.service.impl;

import by.rozmysl.bookingServlet.exception.DaoException;
import by.rozmysl.bookingServlet.exception.ServiceException;
import by.rozmysl.bookingServlet.model.dao.AdditionalServicesDao;
import by.rozmysl.bookingServlet.model.dao.DaoFactory;
import by.rozmysl.bookingServlet.model.entity.hotel.AdditionalServices;
import by.rozmysl.bookingServlet.model.service.AdditionalServicesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.List;

public class AdditionalServicesServiceImpl implements AdditionalServicesService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AdditionalServicesServiceImpl.class);
    private final AdditionalServicesDao servicesDao;

    public AdditionalServicesServiceImpl(Connection connection) {
        servicesDao = DaoFactory.getInstance().servicesDao(connection);
    }

    @Override
    public AdditionalServices findById(String type) throws ServiceException {
        try {
            return servicesDao.findById(type);
        } catch (DaoException e) {
            LOGGER.error("'AdditionalServices findById' query has been failed", e);
            throw new ServiceException("'AdditionalServices findById' query has been failed", e);
        }
    }

    @Override
    public List<AdditionalServices> findAll(int page, int rows) throws ServiceException {
        try {
            return servicesDao.findAll(page, rows);
        } catch (DaoException e) {
            LOGGER.error("'AdditionalServices findAll' query has been failed", e);
            throw new ServiceException("'AdditionalServices findAll' query has been failed", e);
        }
    }

    @Override
    public void changeServicePrice(String type, BigDecimal price) throws ServiceException {
        try {
            servicesDao.changeServicePrice(type, price);
        } catch (DaoException e) {
            LOGGER.error("'changeServicePrice' query has been failed", e);
            throw new ServiceException("'changeServicePrice' query has been failed", e);
        }
    }
}
