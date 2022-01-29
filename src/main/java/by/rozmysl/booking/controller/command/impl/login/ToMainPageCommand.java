package by.rozmysl.booking.controller.command.impl.login;

import by.rozmysl.booking.controller.command.Command;
import by.rozmysl.booking.controller.command.PageAddress;
import by.rozmysl.booking.controller.command.PageGuide;
import by.rozmysl.booking.controller.command.TransferMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * Provides service to initialize actions on the ToUserPageCommand.
 */
public class ToMainPageCommand implements Command {

    /**
     * Executes actions on request content.
     *
     * @param req request content
     * @return page to go
     */
    @Override
    public PageGuide execute(HttpServletRequest req) {
        return new PageGuide(PageAddress.MAIN, TransferMethod.FORWARD);
    }
}


