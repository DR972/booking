package by.rozmysl.bookingServlet.dao.hotel;

import by.rozmysl.bookingServlet.db.ConnectionSource;
import by.rozmysl.bookingServlet.entity.hotel.Food;

import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Provides the base model implementation for `FOOD` table DAO with the <b>ConnectionSource</b> properties.
 */
public class FoodDaoImp implements FoodDao {
    private final ConnectionSource con = new ConnectionSource();

    /**
     * Searches for the Food in the `FOOD` table by id
     *
     * @param type id of the Food
     * @return Food object
     * @throws SQLException if there was an error accessing the database
     */
    @Override
    public Food getById(String type) throws SQLException {
        return getResultSet("select * from FOOD where TYPE = '" + type + "'").get(0);
    }

    /**
     * Searches for all Foods in the `FOOD` table
     *
     * @param page number of the page to return
     * @param rows number of rows per page
     * @return list of Food objects
     * @throws SQLException if there was an error accessing the database
     */
    @Override
    public List<Food> getAll(int page, int rows) throws SQLException {
        return getResultSet("select * from FOOD");
    }

    @Override
    public Food save(Food food) {
        return food;
    }

    @Override
    public void delete(String type) {
    }

    /**
     * Changes the price of the Food in the `FOOD` table
     *
     * @param type  id of the Food
     * @param price new price
     * @throws SQLException if there was an error accessing the database
     */
    @Override
    public void changeFoodPrice(String type, double price) throws SQLException {
        con.update("update FOOD set PRICE = " + price + " where TYPE = '" + type + "'");
    }

    /**
     * Get a list of query results from the 'FOOD' table
     *
     * @param sql the wording of the request to the database
     * @return list of Food objects
     * @throws SQLException if there was an error accessing the database
     */
    private List<Food> getResultSet(String sql) throws SQLException {
        return con.get(sql).stream().map(d -> new Food(d.get("TYPE"), Double.parseDouble(d.get("PRICE"))))
                .distinct().collect(Collectors.toList());
    }
}