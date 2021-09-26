package by.rozmysl.bookingServlet.exception;

/**
 * Provides the Bad Credentials Exception class
 */
public class BadCredentialsException extends Exception {

    /**
     * The constructor creates a new object BadCredentialsException with the <b>message</b> property
     */
    public BadCredentialsException(String message) {
        super(message);
    }
}
