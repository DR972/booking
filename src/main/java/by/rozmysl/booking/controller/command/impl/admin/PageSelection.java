package by.rozmysl.booking.controller.command.impl.admin;

import by.rozmysl.booking.controller.command.RequestAttribute;
import by.rozmysl.booking.controller.command.RequestParameter;
import javax.servlet.http.HttpServletRequest;

public final class PageSelection {
    private static final int DEFAULT_PAGE_NUMBER = 0;
    private static final int DEFAULT_NUMBER_ROWS = 10;

    /**
     * Private constructor without parameters.
     * Restrict instantiation of the class.
     */
    private PageSelection() {
    }

    public static int getPageNumber (HttpServletRequest req) {
        int pageNumber = DEFAULT_PAGE_NUMBER;
        if (req.getParameter(RequestParameter.PAGE_NUMBER) != null) {
            pageNumber = Integer.parseInt(req.getParameter(RequestParameter.PAGE_NUMBER));
        }
        req.setAttribute(RequestAttribute.PAGE_NUMBER, pageNumber);
        return pageNumber;
    }

    public static int getRows (HttpServletRequest req) {
        int rows = DEFAULT_NUMBER_ROWS;
        if (req.getParameter(RequestParameter.ROWS) != null) {
            rows = Integer.parseInt(req.getParameter(RequestParameter.ROWS));
        }
        req.setAttribute(RequestAttribute.ROWS, rows);
        return rows;
    }
}
