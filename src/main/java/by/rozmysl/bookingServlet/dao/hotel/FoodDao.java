package by.rozmysl.bookingServlet.dao.hotel;

import by.rozmysl.bookingServlet.dao.Dao;
import by.rozmysl.bookingServlet.entity.hotel.Food;

import java.sql.SQLException;

/**
 * Provides the base model DAO interface for `FOOD` table.
 */
public interface FoodDao extends Dao<Food, String> {

    /**
     * Changes the price of the Food in the `FOOD` table
     *
     * @param type  id Food
     * @param price new price
     * @throws SQLException if there was an error accessing the database
     */
    void changeFoodPrice(String type, double price) throws SQLException;
}
