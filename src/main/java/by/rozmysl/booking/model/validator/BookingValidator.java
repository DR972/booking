package by.rozmysl.booking.model.validator;

import java.time.LocalDate;

/**
 * Provides booking validation.
 */
public final class BookingValidator extends AbstractValidator {
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
     * @param arrival arrival date
     * @param days    number of days
     * @param persons number of persons
     */
    public String allValidate(String arrival, String days, String persons) {
        if (!validateBookingParam(arrival, days, persons)) {
            return getValidationMessage();
        }
        if (!validateArrivalDate(arrival)) {
            return VAL_DATE;
        }
        return null;
    }

    /**
     * Executes the booking parameters validate
     *
     * @param arrival arrival date
     * @param days    number of days
     * @param persons number of persons
     * @return validation result
     */
    public boolean validateBookingParam(String arrival, String days, String persons) {
        return validate(arrival, DATE_PATTERN, DATE) &&
                validate(days, DAYS_PATTERN, DAYS) &&
                validate(persons, PERSONS_PATTERN, PERSONS);
    }

    /**
     * Executes a check-in date validate
     *
     * @param arrival arrival date
     * @return validation result
     */
    public boolean validateArrivalDate(String arrival) {
        return !LocalDate.parse(arrival).isBefore(LocalDate.now());
    }
}