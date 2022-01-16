package by.rozmysl.booking.controller.command.impl.admin;

import by.rozmysl.booking.controller.command.Command;
import by.rozmysl.booking.controller.command.PageAddress;
import by.rozmysl.booking.controller.command.PageGuide;
import by.rozmysl.booking.controller.command.TransferMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * Provides service to initialize actions on the ToAdminPageCommand.
 */
public class ToAdminPageCommand implements Command {

    /**
     * Executes actions on request content.
     *
     * @param req request content
     * @return page to go
     */
    @Override
    public PageGuide execute(HttpServletRequest req) {
        return new PageGuide(PageAddress.ADMIN, TransferMethod.FORWARD);
    }
}