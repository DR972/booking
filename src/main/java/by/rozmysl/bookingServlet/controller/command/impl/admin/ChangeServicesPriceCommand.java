package by.rozmysl.bookingServlet.controller.command.impl.admin;

import by.rozmysl.bookingServlet.controller.command.*;
import by.rozmysl.bookingServlet.exception.ServiceException;
import by.rozmysl.bookingServlet.model.service.AdditionalServicesService;
import by.rozmysl.bookingServlet.model.service.FoodService;
import by.rozmysl.bookingServlet.model.service.ServiceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

import static by.rozmysl.bookingServlet.controller.command.RequestAttribute.*;
import static by.rozmysl.bookingServlet.controller.command.RequestParameter.*;

/**
 * Provides service to initialize actions on the ChangeServicesPriceCommand.
 */
public class ChangeServicesPriceCommand implements Command {
    private static final Logger LOGGER = LoggerFactory.getLogger(ChangeServicesPriceCommand.class);
    private static final int DEFAULT_PAGE_NUMBER = 0;
    private static final int DEFAULT_NUMBER_ROWS = 5;

    /**
     * Executes actions on request content.
     *
     * @param req request content
     * @return page to go
     * @throws ServiceException if there was an error accessing the service
     */
    @Override
    public PageGuide execute(HttpServletRequest req) throws ServiceException {
        ServiceFactory service = ServiceFactory.getInstance();
        FoodService foodService = service.getFoodService();
        AdditionalServicesService servicesService = service.getServicesService();

        if (req.getParameter(CHANGE_FOOD_PRICE) != null
                && req.getParameter(CHANGE_FOOD_PRICE).equals(CHANGE_FOOD_PRICE)) {
            foodService.changeFoodPrice(req.getParameter(FOOD_TYPE), new BigDecimal(req.getParameter(FOOD_PRICE)));
            LOGGER.info("For food '" + req.getParameter(FOOD_TYPE) + "', the price was changed to '" +
                    req.getParameter(FOOD_PRICE) + "' by admin " + req.getUserPrincipal().getName());
        }

        if (req.getParameter(CHANGE_SERVICES_PRICE) != null
                && req.getParameter(CHANGE_SERVICES_PRICE).equals(CHANGE_SERVICES_PRICE)) {
            servicesService.changeServicePrice(req.getParameter(SERVICE_TYPE), new BigDecimal(req.getParameter(SERVICE_PRICE)));
            LOGGER.info("For service '" + req.getParameter(SERVICE_TYPE) + "', the price was changed to '" +
                    req.getParameter(SERVICE_PRICE) + "' by admin " + req.getUserPrincipal().getName());
        }

        req.setAttribute(ALL_FOOD, foodService.findAll(DEFAULT_PAGE_NUMBER, DEFAULT_NUMBER_ROWS));
        req.setAttribute(ALL_SERVICES, servicesService.findAll(DEFAULT_PAGE_NUMBER, DEFAULT_NUMBER_ROWS));
        return new PageGuide(PageAddress.CHANGE_SERVICES_PRICE, TransferMethod.FORWARD);
    }
}