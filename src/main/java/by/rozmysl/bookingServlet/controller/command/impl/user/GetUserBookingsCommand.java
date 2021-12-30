package by.rozmysl.bookingServlet.controller.command.impl.user;

import by.rozmysl.bookingServlet.controller.command.*;
import by.rozmysl.bookingServlet.model.db.ConnectionPool;
import by.rozmysl.bookingServlet.exception.ServiceException;
import by.rozmysl.bookingServlet.model.service.BookingService;
import by.rozmysl.bookingServlet.model.service.ServiceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

import java.sql.Connection;

import static by.rozmysl.bookingServlet.controller.command.RequestAttribute.USER_BOOKING;
import static by.rozmysl.bookingServlet.controller.command.RequestParameter.*;

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
     * @throws ServiceException if there was an error accessing the service
     */
    @Override
    public PageGuide execute(HttpServletRequest req) throws ServiceException {
        final Connection connection = ConnectionPool.getInstance().getConnectionFromPool();
        try {
            BookingService bookingService = ServiceFactory.getInstance().bookingService(connection);
            if (req.getParameter(DELETE) != null && req.getParameter(DELETE).equals(DELETE)) {
                bookingService.delete(Long.parseLong(req.getParameter(BOOKING_NUMBER)));
                LOGGER.info("Booking # " + req.getParameter(BOOKING_NUMBER) + " was canceled by the user.");
            }
            req.setAttribute(USER_BOOKING, bookingService.findAllBookingsByUser(req.getUserPrincipal().getName()));
        } finally {
            ConnectionPool.getInstance().returnConnectionToPool(connection);
        }
        return new PageGuide(PageAddress.USER_BOOKINGS, TransferMethod.FORWARD);
    }
}
