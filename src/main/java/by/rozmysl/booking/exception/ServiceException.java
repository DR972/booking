package by.rozmysl.booking.exception;

/**
 * Provides the Service Exception class
 */
public class ServiceException extends Exception {

    /**
     * The constructor creates a new object ServiceException without parameters
     */
    public ServiceException() {
    }

    /**
     * The constructor creates a new object ServiceException with the <b>message</b> property
     */
    public ServiceException(String message) {
        super(message);
    }

    /**
     * The constructor creates a new object ServiceException with the <b>message</b>, <b>cause</b>  properties
     */
    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * The constructor creates a new object ServiceException with the <b>cause</b> property
     */
    public ServiceException(Throwable cause) {
        super(cause);
    }
}
