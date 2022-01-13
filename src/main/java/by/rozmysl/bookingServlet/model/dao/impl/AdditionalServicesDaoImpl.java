package by.rozmysl.bookingServlet.model.dao.impl;

import by.rozmysl.bookingServlet.model.dao.AdditionalServicesDao;
import by.rozmysl.bookingServlet.model.entity.hotel.AdditionalServices;

import java.sql.ResultSet;
import java.sql.SQLException;

import static by.rozmysl.bookingServlet.model.dao.ColumnName.*;

/**
 * Provides the base model implementation for `ADDITIONALSERVICES` table DAO.
 */
public class AdditionalServicesDaoImpl extends DaoImpl<AdditionalServices, String> implements AdditionalServicesDao {

    /**
     * Create AdditionalServices entity
     *
     * @param resultSet ResultSet
     * @return AdditionalServices object
     * @throws SQLException if there was an error accessing the database
     */
    @Override
    public AdditionalServices buildEntity(ResultSet resultSet) throws SQLException {
        return new AdditionalServices(
                resultSet.getString(ADDITIONALSERVICES_COLUMN_TYPE),
                resultSet.getBigDecimal(ADDITIONALSERVICES_COLUMN_PRICE));
    }
}