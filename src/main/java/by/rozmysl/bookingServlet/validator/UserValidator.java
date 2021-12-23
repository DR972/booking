package by.rozmysl.bookingServlet.validator;

import by.rozmysl.bookingServlet.dao.user.UserDao;
import by.rozmysl.bookingServlet.entity.user.User;
import by.rozmysl.bookingServlet.exception.BadCredentialsException;

import java.sql.SQLException;

/**
 * Provides user validation.
 */
public class UserValidator extends Validator {
    private static final String USERNAME_PATTERN = "[A-Za-z][A-Za-z0-9.\\-]{4,20}";
    private static final String PASSWORD_PATTERN = "[0-9]{5,}";// "(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,30}";
    private static final String NAME_PATTERN = "[A-Za-zА-Яа-яЁё]{2,20}";
    private static final String EMAIL_PATTERN = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}$";

    /**
     * Executes all user validate
     *
     * @param user user
     * @throws SQLException if there was an error accessing the database
     */
    public void allValidate(User user, UserDao userDao) throws SQLException, BadCredentialsException {
        if (!validateSignUpParam(user)) throw new BadCredentialsException(getValidationMessage());
        if (userDao.findById(user.getUsername()) != null) throw new BadCredentialsException("val.repeatingName");
        if (!user.getPassword().equals(user.getPasswordConfirm())) throw new BadCredentialsException("val.passwordConfirm");
    }

    /**
     * Executes the registration parameters validate
     *
     * @param user user
     * @return validation result
     */
    public boolean validateSignUpParam(User user) {
        return validate(user.getUsername(), USERNAME_PATTERN, "username") &&
                validate(user.getPassword(), PASSWORD_PATTERN, "password") &&
                validate(user.getLastname(), NAME_PATTERN, "lastname") &&
                validate(user.getFirstname(), NAME_PATTERN, "firstname") &&
                validate(user.getEmail(), EMAIL_PATTERN, "email");
    }
}
