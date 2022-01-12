package by.rozmysl.bookingServlet.model.service;

import by.rozmysl.bookingServlet.exception.ServiceException;
import by.rozmysl.bookingServlet.model.entity.hotel.Food;

import java.math.BigDecimal;
import java.util.List;

public interface FoodService {

    Food findById(String type) throws ServiceException;

    List<Food> findAll(int pageNumber, int rows) throws ServiceException;

    void changeFoodPrice(String type, BigDecimal price) throws ServiceException;
}
