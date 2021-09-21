package by.rozmysl.bookingServlet.validator;

import by.rozmysl.bookingServlet.dao.DaoFactory;
import by.rozmysl.bookingServlet.entity.user.User;

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
     * @return validation result
     * @throws SQLException if there was an error accessing the database
     */
    public String allValidate(User user) throws SQLException {
        if (!validateSignUpParam(user)) return getValidationMessage();
        if (new DaoFactory().userDao().getById(user.getUsername()) != null) return "val.repeatingName";
        if (!user.getPassword().equals(user.getPasswordConfirm())) return "val.passwordConfirm";
        return "Ok";
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
