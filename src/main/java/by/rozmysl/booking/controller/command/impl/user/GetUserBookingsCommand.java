package by.rozmysl.booking.controller.command.impl.user;

import by.rozmysl.booking.controller.command.*;
import by.rozmysl.booking.exception.CommandException;
import by.rozmysl.booking.exception.ServiceException;
import by.rozmysl.booking.model.service.BookingService;
import by.rozmysl.booking.model.service.ServiceProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

import java.util.Objects;

import static by.rozmysl.booking.controller.command.RequestAttribute.USER_BOOKING;
import static by.rozmysl.booking.controller.command.RequestParameter.*;
import static by.rozmysl.booking.model.ModelTypeProvider.BOOKING_FIND_ALL_BOOKINGS_BY_USER;

/**
 * Provides service to initialize actions on the GetUserBookingsCommand.
 */
public class GetUserBookingsCommand implements Command {
    private static final Logger LOGGER = LoggerFactory.getLogger(GetUserBookingsCommand.class);

    /**
     * Executes actions on request content.
     *
     * @param req request content
     * @return page to go
     * @throws CommandException if the operation failed
     */
    @Override
    public PageGuide execute(HttpServletRequest req) throws CommandException {
        BookingService bookingService = ServiceProvider.getInstance().getBookingService();
        try {
            if (Objects.equals(req.getParameter(DELETE), DELETE)) {
                bookingService.delete(Long.parseLong(req.getParameter(BOOKING_NUMBER)));
                LOGGER.info("Booking # " + req.getParameter(BOOKING_NUMBER) + " was canceled by the user.");
            }
            req.setAttribute(USER_BOOKING, bookingService.findListEntities(BOOKING_FIND_ALL_BOOKINGS_BY_USER, req.getUserPrincipal().getName()));
        } catch (ServiceException e) {
            LOGGER.error(e.getMessage(), e);
            throw new CommandException(e.getMessage(), e);
        }
        return new PageGuide(PageAddress.USER_BOOKINGS, TransferMethod.FORWARD);
    }
}
