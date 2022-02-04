package by.rozmysl.booking.validator;

import by.rozmysl.booking.model.validator.BookingValidator;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.time.DateTimeException;
import java.util.Arrays;
import java.util.Collection;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(Parameterized.class)
public class BookingValidatorTest {
    private static BookingValidator bookingValidator;

    @BeforeClass
    public static void init() {
        bookingValidator = new BookingValidator();
    }

    private final String arrival;
    private final String days;
    private final String persons;
    private final boolean bookingParamValidity;
    private final boolean arrivalDateValidity;

    public BookingValidatorTest(String arrival, String days, String persons, boolean bookingParamValidity, boolean arrivalDateValidity) {
        this.arrival = arrival;
        this.days = days;
        this.persons = persons;
        this.bookingParamValidity = bookingParamValidity;
        this.arrivalDateValidity = arrivalDateValidity;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {

        return Arrays.asList(new Object[][]{
                {"2023-03-11", "2", "1",
                        true, true},

                {"2020-11-02", "-2", "1",
                        false, false},

                {"2030-02-18", "2", "-1",
                        false, true},

                {"2025.05.21", "11", "1",
                        false, false},

                {"2025-13-18", "df", "3",
                        false, true},

                {"2010-02-18", "2", "one",
                        false, false},

                {"2030/02/18", "2", "4",
                        false, false},

                {"2000-02-18", "2", "19",
                        false, false},

                {"2030-02-18", "40", "3",
                        false, true}
        });
    }

    @Test
    public void validateSignUpParamTest() {
        assertThat(bookingValidator.validateBookingParam(arrival, days, persons), is(bookingParamValidity));
    }

    @Test
    public void validateUsernameTest() {
        try {
            assertThat(bookingValidator.validateArrivalDate(arrival), is(arrivalDateValidity));
        } catch (DateTimeException ignored) {
        }
    }
}
