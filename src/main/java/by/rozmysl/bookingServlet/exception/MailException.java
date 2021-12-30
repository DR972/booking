package by.rozmysl.bookingServlet.exception;

/**
 * Provides the Bad Credentials Exception class
 */
public class MailException extends Exception {

    /**
     * The constructor creates a new object MailException with the <b>message</b> property
     */
    public MailException() {
    }

    public MailException(String message) {
        super(message);
    }

    public MailException(String message, Throwable cause) {
        super(message, cause);
    }

    public MailException(Throwable cause) {
        super(cause);
    }
}
