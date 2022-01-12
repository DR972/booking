package by.rozmysl.bookingServlet.controller.command.impl.admin;

import by.rozmysl.bookingServlet.controller.command.*;
import by.rozmysl.bookingServlet.exception.ServiceException;
import by.rozmysl.bookingServlet.model.service.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;

import static by.rozmysl.bookingServlet.controller.command.RequestAttribute.*;
import static by.rozmysl.bookingServlet.controller.command.RequestParameter.*;

/**
 * Provides service to initialize actions on the GetFreeRoomsCommand.
 */
public class GetFreeRoomsCommand implements Command {

    /**
     * Executes actions on request content.
     *
     * @param req request content
     * @return page to go
     * @throws ServiceException if there was an error accessing the service
     */
    @Override
    public PageGuide execute(HttpServletRequest req) throws ServiceException {
        if (req.getParameter(FROM) != null && req.getParameter(TO) != null) {
            if (LocalDate.parse(req.getParameter(FROM)).isBefore(LocalDate.parse(req.getParameter(TO)))) {
                req.setAttribute(FREE_ROOMS, ServiceFactory.getInstance().getRoomService().findAllFreeRoomsBetweenTwoDates(
                        LocalDate.parse(req.getParameter(FROM)), LocalDate.parse(req.getParameter(TO))));
            } else {
                req.setAttribute(DATE_ERROR, DATE_ERROR_RU);
            }
        }
        return new PageGuide(PageAddress.FREE_ROOMS, TransferMethod.FORWARD);
    }
}