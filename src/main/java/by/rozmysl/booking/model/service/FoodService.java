package by.rozmysl.booking.model.service;

import by.rozmysl.booking.exception.ServiceException;
import by.rozmysl.booking.model.entity.hotel.Food;

import java.math.BigDecimal;
import java.util.List;

/**
 * Provides logic for working with data sent to the `Food` table DAO.
 */
public interface FoodService {

    /**
     * Provides logic for searching for a Food object by id
     *
     * @param type id of the Food object
     * @return Food object
     * @throws ServiceException if the operation failed
     */
    Food findById(String type) throws ServiceException;

    /**
     * Provides logic for searching for all Food objects.
     *
     * @param pageNumber number of the page to return
     * @param rows       number of rows per page
     * @return list of Food objects
     * @throws ServiceException if the operation failed
     */
    List<Food> findAll(int pageNumber, int rows) throws ServiceException;

    /**
     * Provides logic for changing the price of the Food.
     *
     * @param type  id of the Food
     * @param price new price
     * @throws ServiceException if the operation failed
     */
    void changeFoodPrice(String type, BigDecimal price) throws ServiceException;
}
