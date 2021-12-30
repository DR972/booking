package by.rozmysl.bookingServlet.model.service.validator;

import by.rozmysl.bookingServlet.exception.ServiceException;
import by.rozmysl.bookingServlet.model.entity.user.User;
import by.rozmysl.bookingServlet.model.service.UserService;

/**
 * Provides user validation.
 */
public class UserValidator extends Validator {
    private static final String USERNAME_PATTERN = "[A-Za-z][A-Za-z0-9.\\-]{4,20}";
    private static final String PASSWORD_PATTERN = "[0-9]{5,}";// "(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,30}";
    private static final String NAME_PATTERN = "[A-Za-zА-Яа-яЁё]{2,20}";
    private static final String EMAIL_PATTERN = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}$";
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String LASTNAME = "lastname";
    private static final String FIRSTNAME = "firstname";
    private static final String EMAIL = "email";
    private static final String REPEATING_NAME = "val.repeatingName";
    private static final String PASSWORD_CONFIRM = "val.passwordConfirm";

    /**
     * Executes all user validate
     *
     * @param user user
     *  @param userService UserService userService
     * @throws ServiceException if there was an error accessing the database
     */
    public String allValidate(User user, UserService userService) throws ServiceException {
        if (!validateSignUpParam(user)) {
            return getValidationMessage();
        }
        if (userService.findById(user.getUsername()) != null) {
            return REPEATING_NAME;
        }
        if (!user.getPassword().equals(user.getPasswordConfirm())) {
            return PASSWORD_CONFIRM;
        }
        return null;
    }

    /**
     * Executes the registration parameters validate
     *
     * @param user user
     * @return validation result
     */
    public boolean validateSignUpParam(User user) {
        return validate(user.getUsername(), USERNAME_PATTERN, USERNAME) &&
                validate(user.getPassword(), PASSWORD_PATTERN, PASSWORD) &&
                validate(user.getLastname(), NAME_PATTERN, LASTNAME) &&
                validate(user.getFirstname(), NAME_PATTERN, FIRSTNAME) &&
                validate(user.getEmail(), EMAIL_PATTERN, EMAIL);
    }
}