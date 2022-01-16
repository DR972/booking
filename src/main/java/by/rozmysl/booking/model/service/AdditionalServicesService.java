package by.rozmysl.booking.model.service;

import by.rozmysl.booking.exception.ServiceException;
import by.rozmysl.booking.model.entity.hotel.AdditionalServices;

import java.math.BigDecimal;
import java.util.List;

/**
 * Provides logic for working with data sent to the `AdditionalServices` table DAO.
 */
public interface AdditionalServicesService {

    /**
     * Provides logic for searching for a AdditionalServices object by id
     *
     * @param type id of the AdditionalServices object
     * @return AdditionalServices object
     * @throws ServiceException if the operation failed
     */
    AdditionalServices findById(String type) throws ServiceException;

    /**
     * Provides logic for searching for all AdditionalServices objects.
     *
     * @param pageNumber number of the page to return
     * @param rows       number of rows per page
     * @return list of AdditionalServices objects
     * @throws ServiceException if the operation failed
     */
    List<AdditionalServices> findAll(int pageNumber, int rows) throws ServiceException;

    /**
     * Provides logic for changing the price of the Additional Service.
     *
     * @param type  id of the Additional Service
     * @param price new price
     * @throws ServiceException if the operation failed
     */
    void changeServicePrice(String type, BigDecimal price) throws ServiceException;
}
