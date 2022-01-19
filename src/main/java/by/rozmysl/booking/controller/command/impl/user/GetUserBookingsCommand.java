package by.rozmysl.booking.controller.command.impl.user;

import by.rozmysl.booking.controller.command.*;
import by.rozmysl.booking.exception.CommandException;
import by.rozmysl.booking.exception.ServiceException;
import by.rozmysl.booking.model.service.BookingService;
import by.rozmysl.booking.model.service.ServiceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

import static by.rozmysl.booking.controller.command.RequestAttribute.USER_BOOKING;
import static by.rozmysl.booking.controller.command.RequestParameter.*;

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
        BookingService bookingService = ServiceFactory.getInstance().getBookingService();
        try {
            if (req.getParameter(DELETE) != null && req.getParameter(DELETE).equals(DELETE)) {
                bookingService.delete(Long.parseLong(req.getParameter(BOOKING_NUMBER)));
                LOGGER.info("Booking # " + req.getParameter(BOOKING_NUMBER) + " was canceled by the user.");
            }
            req.setAttribute(USER_BOOKING, bookingService.findAllBookingsByUser(req.getUserPrincipal().getName()));
        } catch (ServiceException e) {
            LOGGER.error(e.getMessage(), e);
            throw new CommandException(e.getMessage(), e);
        }
        return new PageGuide(PageAddress.USER_BOOKINGS, TransferMethod.FORWARD);
    }
}