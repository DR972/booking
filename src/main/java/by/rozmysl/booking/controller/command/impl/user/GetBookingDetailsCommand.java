package by.rozmysl.booking.controller.command.impl.user;

import by.rozmysl.booking.controller.command.*;
import by.rozmysl.booking.exception.CommandException;
import by.rozmysl.booking.exception.ServiceException;
import by.rozmysl.booking.model.entity.user.Booking;
import by.rozmysl.booking.model.service.RoomService;
import by.rozmysl.booking.model.service.ServiceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.Objects;

import static by.rozmysl.booking.controller.command.RequestAttribute.*;
import static by.rozmysl.booking.controller.command.RequestParameter.*;

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
        ServiceFactory service = ServiceFactory.getInstance();
        RoomService roomService = service.getRoomService();

        if (req.getSession().getAttribute(ACTION) == BOOKING) {
            return new PageGuide(PageAddress.BOOKING, TransferMethod.FORWARD);
        }

        try {
            req.setAttribute(ALL_FOOD, service.getFoodService().findAll(DEFAULT_PAGE_NUMBER, DEFAULT_NUMBER_ROWS));
            req.setAttribute(ALL_SERVICES, service.getServicesService().findAll(DEFAULT_PAGE_NUMBER, DEFAULT_NUMBER_ROWS));

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
                    service.getFoodService().findById(req.getParameter(FOOD)),
                    service.getServicesService().findById(req.getParameter(SERVICE)));

            if (roomService.findAllTypesFreeRoomsBetweenTwoDatesWithGreaterOrEqualSleeps
                    (booking.getArrival(), booking.getDeparture(), booking.getPersons()).size() == 0) {
                req.setAttribute(NO_AVAILABLE, MESSAGE_NO_AVAILABLE);
            }

            req.getSession().setAttribute(BOOKING, booking);
            req.getSession().setAttribute(FIND_ALL_TYPES_FREE_ROOMS_BETWEEN_TWO_DATES_WITH_GREATER_OR_EQUAL_SLEEPS,
                    roomService.findAllTypesFreeRoomsBetweenTwoDatesWithGreaterOrEqualSleeps
                            (booking.getArrival(), booking.getDeparture(), booking.getPersons()));
        } catch (ServiceException e) {
            LOGGER.error(e.getMessage(), e);
            throw new CommandException(e.getMessage(), e);
        }

        req.getSession().setAttribute(ACTION, BOOKING);
        return new PageGuide(PageAddress.BOOKING, TransferMethod.FORWARD);
    }
}