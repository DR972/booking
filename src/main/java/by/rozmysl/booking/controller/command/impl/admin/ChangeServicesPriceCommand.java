package by.rozmysl.booking.controller.command.impl.admin;

import by.rozmysl.booking.controller.command.*;
import by.rozmysl.booking.exception.CommandException;
import by.rozmysl.booking.exception.ServiceException;
import by.rozmysl.booking.model.service.AdditionalServicesService;
import by.rozmysl.booking.model.service.FoodService;
import by.rozmysl.booking.model.service.ServiceProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

import static by.rozmysl.booking.controller.command.RequestAttribute.*;
import static by.rozmysl.booking.controller.command.RequestParameter.*;
import static by.rozmysl.booking.model.ModelTypeProvider.*;

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
     * @throws CommandException if the operation failed
     */
    @Override
    public PageGuide execute(HttpServletRequest req) throws CommandException {
        ServiceProvider service = ServiceProvider.getInstance();
        FoodService foodService = service.getFoodService();
        AdditionalServicesService servicesService = service.getServicesService();

        try {
            if (req.getParameter(CHANGE_FOOD_PRICE) != null && req.getParameter(CHANGE_FOOD_PRICE).equals(CHANGE_FOOD_PRICE)) {
                foodService.updateEntity(FOOD_CHANGE_FOOD_PRICE, new BigDecimal(req.getParameter(FOOD_PRICE)), req.getParameter(FOOD_TYPE));
                LOGGER.info("For food '" + req.getParameter(FOOD_TYPE) + "', the price was changed to '" +
                        req.getParameter(FOOD_PRICE) + "' by admin " + req.getUserPrincipal().getName());
            }

            if (req.getParameter(CHANGE_SERVICES_PRICE) != null && req.getParameter(CHANGE_SERVICES_PRICE).equals(CHANGE_SERVICES_PRICE)) {
                servicesService.updateEntity(ADDITIONALSERVICES_CHANGE_SERVICE_PRICE, new BigDecimal(req.getParameter(SERVICE_PRICE)), req.getParameter(SERVICE_TYPE));
                LOGGER.info("For service '" + req.getParameter(SERVICE_TYPE) + "', the price was changed to '" +
                        req.getParameter(SERVICE_PRICE) + "' by admin " + req.getUserPrincipal().getName());
            }

            req.setAttribute(ALL_FOOD, foodService.findListEntities(FOOD_FIND_ALL, DEFAULT_NUMBER_ROWS, DEFAULT_PAGE_NUMBER, DEFAULT_NUMBER_ROWS));
            req.setAttribute(ALL_SERVICES, servicesService.findListEntities(ADDITIONALSERVICES_FIND_ALL, DEFAULT_NUMBER_ROWS, DEFAULT_PAGE_NUMBER, DEFAULT_NUMBER_ROWS));
        } catch (ServiceException e) {
            LOGGER.error(e.getMessage(), e);
            throw new CommandException(e.getMessage(), e);
        }
        return new PageGuide(PageAddress.CHANGE_SERVICES_PRICE, TransferMethod.FORWARD);
    }
}