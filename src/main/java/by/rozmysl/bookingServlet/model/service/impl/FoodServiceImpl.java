package by.rozmysl.bookingServlet.model.service.impl;

import by.rozmysl.bookingServlet.exception.DaoException;
import by.rozmysl.bookingServlet.exception.ServiceException;
import by.rozmysl.bookingServlet.model.dao.DaoFactory;
import by.rozmysl.bookingServlet.model.dao.FoodDao;
import by.rozmysl.bookingServlet.model.entity.hotel.Food;
import by.rozmysl.bookingServlet.model.service.FoodService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.List;

public class FoodServiceImpl implements FoodService {
    private static final Logger LOGGER = LoggerFactory.getLogger(FoodServiceImpl.class);
    private final FoodDao foodDao;

    public FoodServiceImpl(Connection connection) {
        foodDao = DaoFactory.getInstance().foodDao(connection);
    }

    @Override
    public Food findById(String type) throws ServiceException {
        try {
            return foodDao.findById(type);
        } catch (DaoException e) {
            LOGGER.error("'Food findById' query has been failed", e);
            throw new ServiceException("'Food findById' query has been failed", e);
        }
    }

    @Override
    public List<Food> findAll(int page, int rows) throws ServiceException {
        try {
            return foodDao.findAll(page, rows);
        } catch (DaoException e) {
            LOGGER.error("'Food findAll' query has been failed", e);
            throw new ServiceException("'Food findAll' query has been failed", e);
        }
    }

    @Override
    public void changeFoodPrice(String type, BigDecimal price) throws ServiceException {
        try {
            foodDao.changeFoodPrice(type, price);
        } catch (DaoException e) {
            LOGGER.error("'changeFoodPrice' query has been failed", e);
            throw new ServiceException("'changeFoodPrice' query has been failed", e);
        }
    }
}
