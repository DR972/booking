package by.rozmysl.bookingServlet.model.dao.impl;

import by.rozmysl.bookingServlet.exception.DaoException;
import by.rozmysl.bookingServlet.model.dao.FoodDao;
import by.rozmysl.bookingServlet.model.dao.StatementConsumer;
import by.rozmysl.bookingServlet.model.entity.hotel.Food;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

import static by.rozmysl.bookingServlet.model.LoggerMessage.*;
import static by.rozmysl.bookingServlet.model.dao.SqlQuery.*;
import static by.rozmysl.bookingServlet.model.dao.ColumnName.*;

/**
 * Provides the base model implementation for `FOOD` table DAO with the <b>ProxyConnection</b> properties.
 */
public class FoodDaoImpl extends DaoImpl<Food, String> implements FoodDao {

    @Override
    public Food buildEntity(ResultSet resultSet) throws SQLException {
        return new Food(
                resultSet.getString(FOOD_COLUMN_TYPE),
                resultSet.getBigDecimal(FOOD_COLUMN_PRICE));
    }

//    /**
//     * Get a list of query results from the 'Food' table
//     *
//     * @param sql               the wording of the request to the database
//     * @param message           in case of an error
//     * @param statementConsumer StatementConsumer
//     * @return list of Food objects
//     * @throws DaoException if there was an error accessing the database
//     */
//    @Override
//    public List<Food> getResultSet(String sql, String message, StatementConsumer statementConsumer) throws DaoException {
//        return get(sql, message, statementConsumer).stream().map(f -> new Food(
//                f.get(FOOD_COLUMN_TYPE),
//                new BigDecimal(f.get(FOOD_COLUMN_PRICE)))).collect(Collectors.toList());
//    }



//
//    /**
//     * Constructs dao for `Food` table.
//     */
//    public FoodDaoImpl() {
//        super(FOOD_FIND_BY_ID, MESSAGE_FOOD_FIND_BY_ID, FOOD_FIND_ALL, MESSAGE_FOOD_FIND_ALL, "", "");
//    }
//
//    /**
//     * Changes the price of the Food in the `FOOD` table
//     *
//     * @param type  id of the Food
//     * @param price new price
//     * @throws DaoException if there was an error accessing the database
//     */
//    @Override
//    public void changeFoodPrice(String type, BigDecimal price) throws DaoException {
//        update(connection, FOOD_CHANGE_FOOD_PRICE, MESSAGE_FOOD_CHANGE_FOOD_PRICE,
//                statement -> {
//                    statement.setBigDecimal(1, price);
//                    statement.setString(2, type);
//                });
//    }
}