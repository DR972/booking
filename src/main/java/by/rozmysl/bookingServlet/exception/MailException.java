package by.rozmysl.bookingServlet.exception;

/**
 * Provides the Mail Exception class
 */
public class MailException extends Exception {

    /**
     * The constructor creates a new object MailException without parameters
     */
    public MailException() {
    }

    /**
     * The constructor creates a new object MailException with the <b>message</b> property
     */
    public MailException(String message) {
        super(message);
    }

    /**
     * The constructor creates a new object MailException with the <b>message</b>, <b>cause</b>  properties
     */
    public MailException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * The constructor creates a new object MailException with the <b>cause</b> property
     */
    public MailException(Throwable cause) {
        super(cause);
    }
}
