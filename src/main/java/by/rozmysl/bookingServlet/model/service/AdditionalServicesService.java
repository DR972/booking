package by.rozmysl.bookingServlet.model.service;

import by.rozmysl.bookingServlet.exception.ServiceException;
import by.rozmysl.bookingServlet.model.entity.hotel.AdditionalServices;

import java.math.BigDecimal;
import java.util.List;

public interface AdditionalServicesService {
    AdditionalServices findById(String type) throws ServiceException;
    List<AdditionalServices> findAll(int page, int rows) throws ServiceException;
    void changeServicePrice(String type, BigDecimal price) throws ServiceException;
}
