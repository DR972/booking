package by.rozmysl.bookingServlet.controller.command.impl.user;

import by.rozmysl.bookingServlet.controller.command.*;
import by.rozmysl.bookingServlet.model.db.ConnectionPool;
import by.rozmysl.bookingServlet.exception.ServiceException;
import by.rozmysl.bookingServlet.model.entity.user.Booking;
import by.rozmysl.bookingServlet.model.service.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.time.LocalDate;

import static by.rozmysl.bookingServlet.controller.command.RequestAttribute.*;
import static by.rozmysl.bookingServlet.controller.command.RequestParameter.*;

/**
 * Provides service to initialize actions on the GetBookingDetailsCommand.
 */
public class GetBookingDetailsCommand implements Command {

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
        final Connection connection = ConnectionPool.getInstance().getConnectionFromPool();
        try {
            req.setAttribute(ALL_FOOD, service.foodService(connection).findAll(0, 0));
            req.setAttribute(ALL_SERVICES, service.servicesService(connection).findAll(0, 0));
            if (req.getParameter(ACTION) == null) {
                return new PageGuide(PageAddress.BOOKING_DETAILS, TransferMethod.FORWARD);
            }
            Booking booking = new Booking(Integer.parseInt(req.getParameter(PERSONS)), LocalDate.parse(req.getParameter(ARRIVAL)),
                    LocalDate.parse(req.getParameter(ARRIVAL)).plusDays(Integer.parseInt(req.getParameter(DAYS))),
                    Integer.parseInt(req.getParameter(DAYS)), service.foodService(connection).findById(req.getParameter(FOOD)),
                    service.servicesService(connection).findById(req.getParameter(SERVICE)));

            String validate = service.bookingService(connection).validateBooking(booking);
            if (validate != null) {
                req.setAttribute(ERROR_VALIDATE, validate);
                ConnectionPool.getInstance().returnConnectionToPool(connection);
                return new PageGuide(PageAddress.BOOKING_DETAILS, TransferMethod.FORWARD);
            }

            if (service.roomService(connection).findAllTypesFreeRoomsBetweenTwoDatesWithGreaterOrEqualSleeps
                    (booking.getArrival(), booking.getDeparture(), booking.getPersons()).size() == 0) {
                req.setAttribute(NO_AVAILABLE, MESSAGE_NO_AVAILABLE);
            }
            req.setAttribute(BOOKING, booking);
            req.setAttribute(FIND_ALL_TYPES_FREE_ROOMS_BETWEEN_TWO_DATES_WITH_GREATER_OR_EQUAL_SLEEPS,
                    service.roomService(connection).findAllTypesFreeRoomsBetweenTwoDatesWithGreaterOrEqualSleeps
                            (booking.getArrival(), booking.getDeparture(), booking.getPersons()));
        } finally {
            ConnectionPool.getInstance().returnConnectionToPool(connection);
        }
        return new PageGuide(PageAddress.BOOKING, TransferMethod.FORWARD);
    }
}