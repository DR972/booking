package by.rozmysl.bookingServlet.model.dao;

import by.rozmysl.bookingServlet.exception.DaoException;
import by.rozmysl.bookingServlet.model.entity.hotel.Food;

import java.math.BigDecimal;

/**
 * Provides the base model DAO interface for `FOOD` table.
 */
public interface FoodDao extends Dao<Food, String> {

    /**
     * Changes the price of the Food in the `FOOD` table
     *
     * @param type  id Food
     * @param price new price
     * @throws DaoException if there was an error accessing the database
     */
    void changeFoodPrice(String type, BigDecimal price) throws DaoException;
}
