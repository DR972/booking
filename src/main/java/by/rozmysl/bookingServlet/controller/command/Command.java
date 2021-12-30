package by.rozmysl.bookingServlet.controller.command;

import by.rozmysl.bookingServlet.exception.ServiceException;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Provides base functionality for Command classes.
 */
public interface Command {

    /**
     * Executes actions on request content.
     *
     * @param req request content
     * @return page to go
     * @throws ServiceException       if there was an error accessing the service
     */
    PageGuide execute(HttpServletRequest req) throws ServiceException;
}
