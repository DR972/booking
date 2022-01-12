package by.rozmysl.bookingServlet.exception;

/**
 * Provides the Dao Exception class
 */
public class DaoException extends Exception {

    /**
     * The constructor creates a new object DaoException without parameters
     */
    public DaoException() {
    }

    /**
     * The constructor creates a new object DaoException with the <b>message</b> property
     */
    public DaoException(String message) {
        super(message);
    }

    /**
     * The constructor creates a new object DaoException with the <b>message</b>, <b>cause</b>  properties
     */
    public DaoException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * The constructor creates a new object DaoException with the <b>cause</b> property
     */
    public DaoException(Throwable cause) {
        super(cause);
    }
}
