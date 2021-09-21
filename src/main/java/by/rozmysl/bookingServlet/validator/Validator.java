package by.rozmysl.bookingServlet.validator;

import java.util.regex.Pattern;

/**
 * Provides base functionality for Validator classes.
 */
abstract class Validator {
    private String validationMessage;

    /**
     * Returns validationMessage.
     *
     * @return validationMessage
     */
    public String getValidationMessage() {
        return validationMessage;
    }

    /**
     * Validate.
     *
     * @param verifiable verifiable
     * @param pattern    pattern
     * @param param      param
     * @return validation result
     */
    boolean validate(String verifiable, String pattern, String param) {
        if (verifiable != null && Pattern.compile(pattern).matcher(verifiable).matches()) return true;
        else {
            this.validationMessage = "val." + param;
            return false;
        }
    }
}