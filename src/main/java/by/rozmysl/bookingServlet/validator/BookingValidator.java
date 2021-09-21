package by.rozmysl.bookingServlet.validator;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;

/**
 * Provides booking validation.
 */
public class BookingValidator extends Validator {
    private static final String DATE_PATTERN = "[0-9]{4}-(0[1-9]|1[012])-(0[1-9]|1[0-9]|2[0-9]|3[01])";
    private static final String DAYS_PATTERN = "^(?:[1-9]|[1-2][0-9]|3[0-1])$";// "(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,30}";
    private static final String PERSONS_PATTERN = "^(?:[1-9]|[1][0])$";

    /**
     * Executes all booking validate
     *
     * @param req request content
     * @return validation result
     */
    public String allValidate(HttpServletRequest req) {
        if (!validateBookingParam(req)) return getValidationMessage();
        if (LocalDate.parse(req.getParameter("arrival")).isBefore(LocalDate.now())) return "val.date";
        return "Ok";
    }

    /**
     * Executes the booking parameters validate
     *
     * @param req request content
     * @return validation result
     */
    public boolean validateBookingParam(HttpServletRequest req) {
        return validate(req.getParameter("arrival"), DATE_PATTERN, "date") &&
                validate(req.getParameter("days"), DAYS_PATTERN, "days") &&
                validate(req.getParameter("persons"), PERSONS_PATTERN, "persons");
    }
}