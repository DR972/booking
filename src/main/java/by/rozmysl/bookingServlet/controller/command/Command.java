package by.rozmysl.bookingServlet.controller.command;

import by.rozmysl.bookingServlet.exception.CommandException;

import javax.servlet.http.HttpServletRequest;

/**
 * Provides base functionality for Command classes.
 */
public interface Command {

    /**
     * Executes actions on request content.
     *
     * @param req request content
     * @return page to go
     * @throws CommandException if the operation failed
     */
    PageGuide execute(HttpServletRequest req) throws CommandException;
}
