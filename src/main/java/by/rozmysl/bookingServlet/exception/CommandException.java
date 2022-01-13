package by.rozmysl.bookingServlet.exception;

/**
 * Provides the Command Exception class
 */
public class CommandException extends Exception {

    /**
     * The constructor creates a new object CommandException without parameters
     */
    public CommandException() {
    }

    /**
     * The constructor creates a new object CommandException with the <b>message</b> property
     */
    public CommandException(String message) {
        super(message);
    }

    /**
     * The constructor creates a new object CommandException with the <b>message</b>, <b>cause</b>  properties
     */
    public CommandException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * The constructor creates a new object CommandException with the <b>cause</b> property
     */
    public CommandException(Throwable cause) {
        super(cause);
    }
}