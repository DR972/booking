package by.rozmysl.bookingServlet.model.dao;

import by.rozmysl.bookingServlet.exception.DaoException;
import by.rozmysl.bookingServlet.model.entity.hotel.AdditionalServices;

import java.math.BigDecimal;

/**
 * Provides the base model DAO interface for `ADDITIONALSERVICES` table.
 */
public interface AdditionalServicesDao extends Dao<AdditionalServices, String> {

    /**
     * Changes the price of the Additional Service in the `ADDITIONALSERVICES` table
     *
     * @param type  id Additional Service
     * @param price new price
     * @throws DaoException if there was an error accessing the database
     */
    void changeServicePrice(String type, BigDecimal price) throws DaoException;
}
