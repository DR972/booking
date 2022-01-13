package by.rozmysl.bookingServlet.controller.command.impl.user;

import by.rozmysl.bookingServlet.controller.command.*;
import by.rozmysl.bookingServlet.controller.command.impl.login.ToPriceCommand;
import by.rozmysl.bookingServlet.exception.CommandException;
import by.rozmysl.bookingServlet.exception.ServiceException;
import by.rozmysl.bookingServlet.model.entity.user.Booking;
import by.rozmysl.bookingServlet.model.service.RoomService;
import by.rozmysl.bookingServlet.model.service.ServiceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;

import static by.rozmysl.bookingServlet.controller.command.RequestAttribute.*;
import static by.rozmysl.bookingServlet.controller.command.RequestParameter.*;

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
        try {
            req.setAttribute(ALL_FOOD, service.getFoodService().findAll(DEFAULT_PAGE_NUMBER, DEFAULT_NUMBER_ROWS));
            req.setAttribute(ALL_SERVICES, service.getServicesService().findAll(DEFAULT_PAGE_NUMBER, DEFAULT_NUMBER_ROWS));

            if (req.getParameter(ACTION) == null) {
                return new PageGuide(PageAddress.BOOKING_DETAILS, TransferMethod.FORWARD);
            }

            int days = Integer.parseInt(req.getParameter(DAYS));
            Booking booking = new Booking(
                    Integer.parseInt(req.getParameter(PERSONS)),
                    LocalDate.parse(req.getParameter(ARRIVAL)),
                    LocalDate.parse(req.getParameter(ARRIVAL)).plusDays(days),
                    days,
                    service.getFoodService().findById(req.getParameter(FOOD)),
                    service.getServicesService().findById(req.getParameter(SERVICE)));

            String validate = service.getBookingService().validateBooking(booking);
            if (validate != null) {
                req.setAttribute(ERROR_VALIDATE, validate);
                return new PageGuide(PageAddress.BOOKING_DETAILS, TransferMethod.FORWARD);
            }

            if (roomService.findAllTypesFreeRoomsBetweenTwoDatesWithGreaterOrEqualSleeps
                    (booking.getArrival(), booking.getDeparture(), booking.getPersons()).size() == 0) {
                req.setAttribute(NO_AVAILABLE, MESSAGE_NO_AVAILABLE);
            }

            req.setAttribute(BOOKING, booking);
            req.setAttribute(FIND_ALL_TYPES_FREE_ROOMS_BETWEEN_TWO_DATES_WITH_GREATER_OR_EQUAL_SLEEPS,
                    roomService.findAllTypesFreeRoomsBetweenTwoDatesWithGreaterOrEqualSleeps
                            (booking.getArrival(), booking.getDeparture(), booking.getPersons()));
        } catch (ServiceException e) {
            LOGGER.error(e.getMessage(), e);
            throw new CommandException(e.getMessage(), e);
        }
        return new PageGuide(PageAddress.BOOKING, TransferMethod.FORWARD);
    }
}