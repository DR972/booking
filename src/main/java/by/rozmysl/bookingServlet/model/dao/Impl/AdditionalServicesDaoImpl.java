package by.rozmysl.bookingServlet.model.dao.Impl;

import by.rozmysl.bookingServlet.exception.DaoException;
import by.rozmysl.bookingServlet.model.dao.AdditionalServicesDao;
import by.rozmysl.bookingServlet.model.entity.hotel.AdditionalServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static by.rozmysl.bookingServlet.model.dao.SqlQuery.*;

/**
 * Provides the base model implementation for `ADDITIONALSERVICES` table DAO with the <b>ProxyConnection</b> properties.
 */
public class AdditionalServicesDaoImpl implements AdditionalServicesDao {
    private static final Logger LOGGER = LoggerFactory.getLogger(AdditionalServicesDaoImpl.class);
    private final Connection connection;

    /**
     * The constructor creates a new object AdditionalServicesDaoImpl with the <b>connection</b> property
     */
    public AdditionalServicesDaoImpl(Connection connection) {
        this.connection = connection;
    }

    /**
     * Searches for the Additional Services in the `ADDITIONALSERVICES` table by id
     *
     * @param type id of the Additional services
     * @return AdditionalServices object
     * @throws DaoException if there was an error accessing the database
     */
    @Override
    public AdditionalServices findById(String type) throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(ADDITIONALSERVICES_FIND_BY_ID)) {
            preparedStatement.setString(1, type);
            final ResultSet rs = preparedStatement.executeQuery();
            rs.next();
            return buildAdditionalServices(rs);
        } catch (SQLException e) {
            LOGGER.error("'AdditionalServices findById' query has been failed", e);
            throw new DaoException("'AdditionalServices findById' query has been failed", e);
        }
    }

    /**
     * Searches for all Additional Services in the `ADDITIONALSERVICES` table
     *
     * @param page number of the page to return
     * @param rows number of rows per page
     * @return list of AdditionalServices objects
     * @throws DaoException if there was an error accessing the database
     */
    @Override
    public List<AdditionalServices> findAll(int page, int rows) throws DaoException {
        List<AdditionalServices> services = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(ADDITIONALSERVICES_FIND_ALL)) {
            final ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                services.add(buildAdditionalServices(rs));
            }
        } catch (SQLException e) {
            LOGGER.error("'AdditionalServices findAll' query has been failed", e);
            throw new DaoException("'AdditionalServices findAll' query has been failed", e);
        }
        return services;
    }

    @Override
    public AdditionalServices save(AdditionalServices additionalServices) {
        return additionalServices;
    }

    @Override
    public void delete(String type) {
    }

    /**
     * Changes the price of the Additional Service in the `ADDITIONALSERVICES` table
     *
     * @param type  id of the Additional Service
     * @param price new price
     * @throws DaoException if there was an error accessing the database
     */
    @Override
    public void changeServicePrice(String type, BigDecimal price) throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(ADDITIONALSERVICES_CHANGE_SERVICE_PRICE)) {
            preparedStatement.setBigDecimal(1, price);
            preparedStatement.setString(2, type);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("'changeServicePrice' query has been failed", e);
            throw new DaoException("'changeServicePrice' query has been failed", e);
        }
    }

    private AdditionalServices buildAdditionalServices(ResultSet rs) throws SQLException {
        return new AdditionalServices(
                rs.getString(ADDITIONALSERVICES_COLUMN_TYPE),
                rs.getBigDecimal(ADDITIONALSERVICES_COLUMN_PRICE));
    }
}