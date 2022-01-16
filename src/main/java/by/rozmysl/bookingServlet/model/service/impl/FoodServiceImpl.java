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
import java.util.List;

import static by.rozmysl.bookingServlet.model.util.LoggerMessageError.*;
import static by.rozmysl.bookingServlet.model.util.SqlQuery.*;

/**
 * Provides logic for working with data sent to the `Food` table DAO.
 */
public class FoodServiceImpl implements FoodService {
    private static final Logger LOGGER = LoggerFactory.getLogger(FoodServiceImpl.class);
    private final FoodDao foodDao;

    /**
     * The constructor creates a new object FoodServiceImpl without property.
     */
    public FoodServiceImpl() {
        foodDao = DaoFactory.getInstance().getFoodDao();
    }

    /**
     * Provides logic for searching for a Food object by id
     *
     * @param type id of the Food object
     * @return Food object
     * @throws ServiceException if the operation failed
     */
    @Override
    public Food findById(String type) throws ServiceException {
        try {
            return foodDao.findEntity(FOOD_FIND_BY_ID, MESSAGE_FOOD_FIND_BY_ID, type);
        } catch (DaoException e) {
            LOGGER.error(MESSAGE_FOOD_FIND_BY_ID, e);
            throw new ServiceException(MESSAGE_FOOD_FIND_BY_ID, e);
        }
    }

    /**
     * Provides logic for searching for all Food objects.
     *
     * @param pageNumber number of the page to return
     * @param rows       number of rows per page
     * @return list of Food objects
     * @throws ServiceException if the operation failed
     */
    @Override
    public List<Food> findAll(int pageNumber, int rows) throws ServiceException {
        try {
            return foodDao.findListEntities(FOOD_FIND_ALL, MESSAGE_FOOD_FIND_ALL, rows, pageNumber, rows);
        } catch (DaoException e) {
            LOGGER.error(MESSAGE_FOOD_FIND_ALL, e);
            throw new ServiceException(MESSAGE_FOOD_FIND_ALL, e);
        }
    }

    /**
     * Provides logic for changing the price of the Food.
     *
     * @param type  id of the Food
     * @param price new price
     * @throws ServiceException if the operation failed
     */
    @Override
    public void changeFoodPrice(String type, BigDecimal price) throws ServiceException {
        try {
            foodDao.updateEntity(FOOD_CHANGE_FOOD_PRICE, MESSAGE_FOOD_CHANGE_FOOD_PRICE,
                    price,
                    type);
        } catch (DaoException e) {
            LOGGER.error(MESSAGE_FOOD_CHANGE_FOOD_PRICE, e);
            throw new ServiceException(MESSAGE_FOOD_CHANGE_FOOD_PRICE, e);
        }
    }
}
