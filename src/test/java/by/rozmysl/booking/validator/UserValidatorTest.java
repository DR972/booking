package by.rozmysl.booking.validator;

import by.rozmysl.booking.model.entity.user.User;
import by.rozmysl.booking.model.validator.UserValidator;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(Parameterized.class)
public class UserValidatorTest {

    private static UserValidator userValidator;

    @BeforeClass
    public static void init() {
        userValidator = new UserValidator();
    }

    private final User user;
    private final boolean signUpParamValidity;
    private final boolean passwordConfirmationValidity;

    public UserValidatorTest(User user, boolean signUpParamValidity, boolean passwordConfirmationValidity) {
        this.user = user;
        this.signUpParamValidity = signUpParamValidity;
        this.passwordConfirmationValidity = passwordConfirmationValidity;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {

        return Arrays.asList(new Object[][]{
                {new User("user1", "lastname",
                        "firstname", "D222hfh22",
                        "D222hfh22", "email@email.com"),
                        true, true},

                {new User("user2                 345", "lastname",
                        "firstname", "Pas56word",
                        "Pas56word", "email@email.com"),
                        false, true},

                {new User("user3", "lastname4669",
                        "firstname", "us33GOOD",
                        "us33GOOD", "email@email.com"),
                        false, true},

                {new User("user4", "lastname",
                        "firstname 4", "22222Ok",
                        "2222", "email@email.com"),
                        false, false},

                {new User("user5", "lastname",
                        "lastname", "WertSD34",
                        "22222Lu", "email@email.com"),
                        true, false},

                {new User("user6", "lastname",
                        "lastname", "222",
                        "22222", "email@email.com"),
                        false, false},

                {new User("user7", "lastname",
                        "lastname", "22222",
                        "22222", "email@email.com"),
                        false, true},

                {new User("user8", "lastname",
                        "firstname", "passWord33",
                        "password333", "email@.com"),
                        false, false},

                {new User("user9", "lastname",
                        "firstname", "passWord33",
                        "+375298965324", "email"),
                        false, false}
        });
    }

    @Test
    public void validateSignUpParamTest() {
        assertThat(userValidator.validateSignUpParam(user), is(signUpParamValidity));
    }

    @Test
    public void validatePasswordConfirmationTest() {
        assertThat(userValidator.validatePasswordConfirmation(user), is(passwordConfirmationValidity));
    }
}
