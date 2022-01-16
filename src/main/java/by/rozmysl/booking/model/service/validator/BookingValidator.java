package by.rozmysl.booking.model.service.validator;

import by.rozmysl.booking.model.entity.user.Booking;

import java.time.LocalDate;

/**
 * Provides booking validation.
 */
public class BookingValidator extends Validator {
    private static final String DATE_PATTERN = "[0-9]{4}-(0[1-9]|1[012])-(0[1-9]|1[0-9]|2[0-9]|3[01])";
    private static final String DAYS_PATTERN = "^(?:[1-9]|[1-2][0-9]|3[0-1])$";
    private static final String PERSONS_PATTERN = "^(?:[1-9]|[1][0])$";
    private static final String DATE = "date";
    private static final String DAYS = "days";
    private static final String PERSONS = "persons";
    private static final String VAL_DATE = "val.date";

    /**
     * Executes all booking validate
     *
     * @param booking  Booking booking
     */
    public String allValidate(Booking booking) {
        if (!validateBookingParam(booking)) {
            return getValidationMessage();
        }
        if (booking.getArrival().isBefore(LocalDate.now())) {
            return VAL_DATE;
        }
        return null;
    }

    /**
     * Executes the booking parameters validate
     *
     * @param booking Booking booking
     * @return validation result
     */
    public boolean validateBookingParam(Booking booking) {
        return validate(String.valueOf(booking.getArrival()), DATE_PATTERN, DATE) &&
                validate(String.valueOf(booking.getDays()), DAYS_PATTERN, DAYS) &&
                validate(String.valueOf(booking.getPersons()), PERSONS_PATTERN, PERSONS);
    }
}