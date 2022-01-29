package by.rozmysl.booking.controller.command.impl.user;

import by.rozmysl.booking.controller.command.*;
import by.rozmysl.booking.exception.CommandException;
import by.rozmysl.booking.exception.ServiceException;
import by.rozmysl.booking.model.entity.hotel.AdditionalServices;
import by.rozmysl.booking.model.entity.hotel.Food;
import by.rozmysl.booking.model.entity.hotel.Room;
import by.rozmysl.booking.model.entity.user.Booking;
import by.rozmysl.booking.model.service.AdditionalServicesService;
import by.rozmysl.booking.model.service.FoodService;
import by.rozmysl.booking.model.service.RoomService;
import by.rozmysl.booking.model.service.ServiceProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import static by.rozmysl.booking.controller.command.RequestAttribute.*;
import static by.rozmysl.booking.controller.command.RequestParameter.*;
import static by.rozmysl.booking.model.ModelTypeProvider.*;

/**
 * Provides service to initialize actions on the GetBookingDetailsCommand.
 */
public class GetBookingDetailsCommand implements Command {
    private static final Logger LOGGER = LoggerFactory.getLogger(GetBookingDetailsCommand.class);
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
        RoomService roomService = service.getRoomService();
        FoodService foodService = service.getFoodService();
        AdditionalServicesService services = service.getServicesService();

        if (req.getSession().getAttribute(ACTION) == BOOKING) {
            return new PageGuide(PageAddress.BOOKING, TransferMethod.FORWARD);
        }

        try {
            req.setAttribute(ALL_FOOD, foodService.findListEntities(FOOD_FIND_ALL, DEFAULT_NUMBER_ROWS, DEFAULT_PAGE_NUMBER, DEFAULT_NUMBER_ROWS));
            req.setAttribute(ALL_SERVICES, services.findListEntities(ADDITIONALSERVICES_FIND_ALL, DEFAULT_NUMBER_ROWS, DEFAULT_PAGE_NUMBER, DEFAULT_NUMBER_ROWS));

            if (!Objects.equals(req.getParameter(ACTION), BOOKING_DETAILS)) {
                return new PageGuide(PageAddress.BOOKING_DETAILS, TransferMethod.FORWARD);
            }

            String arrival = req.getParameter(ARRIVAL);
            String days = req.getParameter(DAYS);
            String persons = req.getParameter(PERSONS);
            String validate = service.getBookingService().validateBooking(arrival, days, persons);
            if (validate != null) {
                req.setAttribute(ERROR_VALIDATE, validate);
                return new PageGuide(PageAddress.BOOKING_DETAILS, TransferMethod.FORWARD);
            }

            int numberDays = Integer.parseInt(days);
            LocalDate arrivalDate = LocalDate.parse(arrival);
            Booking booking = new Booking(
                    Integer.parseInt(persons),
                    arrivalDate,
                    arrivalDate.plusDays(numberDays),
                    numberDays,
                    foodService.findEntity(Food.class, FOOD_FIND_BY_ID, req.getParameter(FOOD)),
                    services.findEntity(AdditionalServices.class, ADDITIONALSERVICES_FIND_BY_ID, req.getParameter(SERVICE)));

            List<Room> rooms = roomService.findListEntities(ROOM_FIND_ALL_TYPES_FREE_ROOMS_BETWEEN_TWO_DATES_WITH_GREATER_OR_EQUAL_SLEEPS,
                    booking.getPersons(), booking.getDeparture(), booking.getArrival());

            if (rooms.size() == 0) {
                req.setAttribute(NO_AVAILABLE, MESSAGE_NO_AVAILABLE);
            }

            req.getSession().setAttribute(BOOKING, booking);
            req.getSession().setAttribute(FIND_ALL_TYPES_FREE_ROOMS_BETWEEN_TWO_DATES_WITH_GREATER_OR_EQUAL_SLEEPS, rooms);
        } catch (ServiceException e) {
            LOGGER.error(e.getMessage(), e);
            throw new CommandException(e.getMessage(), e);
        }

        req.getSession().setAttribute(ACTION, BOOKING);
        return new PageGuide(PageAddress.BOOKING, TransferMethod.FORWARD);
    }
}