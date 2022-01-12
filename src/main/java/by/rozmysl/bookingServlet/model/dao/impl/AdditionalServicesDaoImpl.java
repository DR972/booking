package by.rozmysl.bookingServlet.model.dao.impl;

import by.rozmysl.bookingServlet.exception.DaoException;
import by.rozmysl.bookingServlet.model.dao.AdditionalServicesDao;
import by.rozmysl.bookingServlet.model.dao.StatementConsumer;
import by.rozmysl.bookingServlet.model.entity.hotel.AdditionalServices;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

import static by.rozmysl.bookingServlet.model.dao.ColumnName.*;
import static by.rozmysl.bookingServlet.model.LoggerMessage.*;
import static by.rozmysl.bookingServlet.model.dao.SqlQuery.*;

/**
 * Provides the base model implementation for `ADDITIONALSERVICES` table DAO with the <b>ProxyConnection</b> properties.
 */
public class AdditionalServicesDaoImpl extends DaoImpl<AdditionalServices, String> implements AdditionalServicesDao {

    @Override
    public AdditionalServices buildEntity(ResultSet resultSet) throws SQLException {
        return new AdditionalServices(
                resultSet.getString(ADDITIONALSERVICES_COLUMN_TYPE),
                resultSet.getBigDecimal(ADDITIONALSERVICES_COLUMN_PRICE));
    }

//    /**
//     * Get a list of query results from the 'AdditionalServices' table
//     *
//     * @param sql               the wording of the request to the database
//     * @param message           in case of an error
//     * @param statementConsumer StatementConsumer
//     * @return list of AdditionalServices objects
//     * @throws DaoException if there was an error accessing the database
//     */
//    @Override
//    public List<AdditionalServices> getResultSet(String sql, String message, StatementConsumer statementConsumer) throws DaoException {
//        return get(sql, message, statementConsumer).stream().map(a -> new AdditionalServices(
//                a.get(ADDITIONALSERVICES_COLUMN_TYPE),
//                new BigDecimal(a.get(ADDITIONALSERVICES_COLUMN_PRICE)))).collect(Collectors.toList());
//    }
//
//    /**
//     * Constructs dao for `AdditionalServices` table.
//     */
//    public AdditionalServicesDaoImpl() {
//        super(ADDITIONALSERVICES_FIND_BY_ID, MESSAGE_ADDITIONALSERVICES_FIND_BY_ID, ADDITIONALSERVICES_FIND_ALL,
//                MESSAGE_ADDITIONALSERVICES_FIND_ALL, "", "");
//    }
//
//    /**
//     * Changes the price of the Additional Service in the `ADDITIONALSERVICES` table
//     *
//     * @param type  id of the Additional Service
//     * @param price new price
//     * @throws DaoException if there was an error accessing the database
//     */
//    @Override
//    public void changeServicePrice(String type, BigDecimal price) throws DaoException {
//        update(connection, ADDITIONALSERVICES_CHANGE_SERVICE_PRICE, MESSAGE_ADDITIONALSERVICES_CHANGE_SERVICE_PRICE,
//                statement -> {
//                    statement.setBigDecimal(1, price);
//                    statement.setString(2, type);
//                });
//    }


}