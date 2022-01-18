package by.rozmysl.booking.controller.command.impl.admin;

import by.rozmysl.booking.controller.command.RequestAttribute;
import by.rozmysl.booking.controller.command.RequestParameter;

import javax.servlet.http.HttpServletRequest;

/**
 * Defines the page parameters.
 */
public final class SelectingPageParameters {
    private static final int DEFAULT_PAGE_NUMBER = 0;
    private static final int DEFAULT_NUMBER_ROWS = 10;

    /**
     * Private constructor without parameters.
     * Restrict instantiation of the class.
     */
    private SelectingPageParameters() {
    }

    /**
     * Gets page number
     *
     * @param req HttpServletRequest
     * @return page number
     */
    public static int getPageNumber(HttpServletRequest req) {
        int pageNumber = DEFAULT_PAGE_NUMBER;
        if (req.getParameter(RequestParameter.PAGE_NUMBER) != null) {
            pageNumber = Integer.parseInt(req.getParameter(RequestParameter.PAGE_NUMBER));
        }
        req.setAttribute(RequestAttribute.PAGE_NUMBER, pageNumber);
        return pageNumber;
    }

    /**
     * Get the number of rows per page
     *
     * @param req HttpServletRequest
     * @return number of rows
     */
    public static int getNumberRows(HttpServletRequest req) {
        int rows = DEFAULT_NUMBER_ROWS;
        if (req.getParameter(RequestParameter.ROWS) != null) {
            rows = Integer.parseInt(req.getParameter(RequestParameter.ROWS));
        }
        req.setAttribute(RequestAttribute.ROWS, rows);
        return rows;
    }
}
