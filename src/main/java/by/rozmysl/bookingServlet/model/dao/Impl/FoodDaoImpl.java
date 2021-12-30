package by.rozmysl.bookingServlet.model.dao.Impl;

import by.rozmysl.bookingServlet.exception.DaoException;
import by.rozmysl.bookingServlet.model.dao.FoodDao;
import by.rozmysl.bookingServlet.model.entity.hotel.Food;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import static by.rozmysl.bookingServlet.model.dao.SqlQuery.*;

/**
 * Provides the base model implementation for `FOOD` table DAO with the <b>ProxyConnection</b> properties.
 */
public class FoodDaoImpl implements FoodDao {
    private static final Logger LOGGER = LoggerFactory.getLogger(FoodDaoImpl.class);
    private final Connection connection;

    /**
     * The constructor creates a new object FoodDaoImpl with the <b>connection</b> property
     */
    public FoodDaoImpl(Connection connection) {
        this.connection = connection;
    }

    /**
     * Searches for the Food in the `FOOD` table by id
     *
     * @param type id of the Food
     * @return Food object
     * @throws DaoException if there was an error accessing the database
     */
    @Override
    public Food findById(String type) throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(FOOD_FIND_BY_ID)) {
            preparedStatement.setString(1, type);
            final ResultSet rs = preparedStatement.executeQuery();
            rs.next();
            return buildFood(rs);
        } catch (SQLException e) {
            LOGGER.error("'Food findById' query has been failed", e);
            throw new DaoException("'Food findById' query has been failed", e);
        }
    }

    /**
     * Searches for all Foods in the `FOOD` table
     *
     * @param page number of the page to return
     * @param rows number of rows per page
     * @return list of Food objects
     * @throws DaoException if there was an error accessing the database
     */
    @Override
    public List<Food> findAll(int page, int rows) throws DaoException {
        List<Food> foods = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(FOOD_FIND_ALL)) {
            final ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                foods.add(buildFood(rs));
            }
        } catch (SQLException e) {
            LOGGER.error("'Food findAll' query has been failed", e);
            throw new DaoException("'Food findAll' query has been failed", e);
        }
        return foods;
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
     * @throws DaoException if there was an error accessing the database
     */
    @Override
    public void changeFoodPrice(String type, BigDecimal price) throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(FOOD_CHANGE_FOOD_PRICE)){
            preparedStatement.setBigDecimal(1, price);
            preparedStatement.setString(2, type);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("'changeFoodPrice' query has been failed", e);
            throw new DaoException("'changeFoodPrice' query has been failed", e);
        }
    }

    private Food buildFood(ResultSet rs) throws SQLException {
        return new Food(
                rs.getString(FOOD_COLUMN_TYPE),
                rs.getBigDecimal(FOOD_COLUMN_PRICE));
    }
}