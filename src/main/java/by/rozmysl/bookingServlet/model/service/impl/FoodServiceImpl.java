package by.rozmysl.bookingServlet.model.service.impl;

import by.rozmysl.bookingServlet.exception.DaoException;
import by.rozmysl.bookingServlet.exception.ServiceException;
import by.rozmysl.bookingServlet.model.dao.DaoFactory;
import by.rozmysl.bookingServlet.model.dao.FoodDao;
import by.rozmysl.bookingServlet.model.db.ConnectionPool;
import by.rozmysl.bookingServlet.model.db.ProxyConnection;
import by.rozmysl.bookingServlet.model.entity.hotel.Food;
import by.rozmysl.bookingServlet.model.service.FoodService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.List;

import static by.rozmysl.bookingServlet.model.LoggerMessage.*;
import static by.rozmysl.bookingServlet.model.dao.SqlQuery.*;

public class FoodServiceImpl implements FoodService {
    private static final Logger LOGGER = LoggerFactory.getLogger(FoodServiceImpl.class);
    private final FoodDao foodDao;

    public FoodServiceImpl() {
        foodDao = DaoFactory.getInstance().getFoodDao();
    }

    @Override
    public Food findById(String type) throws ServiceException {
        try {
            return foodDao.findEntity(FOOD_FIND_BY_ID, MESSAGE_FOOD_FIND_BY_ID, type);
//            return foodDao.findById(type);
        } catch (DaoException e) {
            LOGGER.error(MESSAGE_FOOD_FIND_BY_ID, e);
            throw new ServiceException(MESSAGE_FOOD_FIND_BY_ID, e);
        }
    }

    @Override
    public List<Food> findAll(int pageNumber, int rows) throws ServiceException {
        try {
            return foodDao.findListEntities(FOOD_FIND_ALL, MESSAGE_FOOD_FIND_ALL, rows, pageNumber, rows);
//            return foodDao.findAll(pageNumber, rows);
        } catch (DaoException e) {
            LOGGER.error(MESSAGE_FOOD_FIND_ALL, e);
            throw new ServiceException(MESSAGE_FOOD_FIND_ALL, e);
        }
    }

    @Override
    public void changeFoodPrice(String type, BigDecimal price) throws ServiceException {
        try (final ProxyConnection connection = ConnectionPool.getInstance().getConnectionFromPool()) {
            foodDao.setConnection(connection);
            foodDao.updateEntity(FOOD_CHANGE_FOOD_PRICE, MESSAGE_FOOD_CHANGE_FOOD_PRICE,
                    price,
                    type);
//            foodDao.changeFoodPrice(type, price);
        } catch (DaoException e) {
            LOGGER.error(MESSAGE_FOOD_CHANGE_FOOD_PRICE, e);
            throw new ServiceException(MESSAGE_FOOD_CHANGE_FOOD_PRICE, e);
        }
    }
}
