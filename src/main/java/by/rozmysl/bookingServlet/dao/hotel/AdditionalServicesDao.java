package by.rozmysl.bookingServlet.dao.hotel;

import by.rozmysl.bookingServlet.dao.Dao;
import by.rozmysl.bookingServlet.entity.hotel.AdditionalServices;

import java.math.BigDecimal;
import java.sql.SQLException;

/**
 * Provides the base model DAO interface for `ADDITIONALSERVICES` table.
 */
public interface AdditionalServicesDao extends Dao<AdditionalServices, String> {

    /**
     * Changes the price of the Additional Service in the `ADDITIONALSERVICES` table
     *
     * @param type  id Additional Service
     * @param price new price
     * @throws SQLException if there was an error accessing the database
     */
    void changeServicePrice(String type, BigDecimal price) throws SQLException;
}
