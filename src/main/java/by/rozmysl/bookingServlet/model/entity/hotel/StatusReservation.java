package by.rozmysl.bookingServlet.model.entity.hotel;

/**
 * It is used to store Status Booking objects with the <b>status</b> properties.
 */
public enum StatusReservation {
    ORDER("ЗАКАЗ"),
    INVOICE("СЧЕТ"),
    PAID("ОПЛАЧЕН"),
    CLOSED("ЗАКРЫТ");

    private String status;

    /**
     * The constructor creates a new object Status Booking
     *
     * @param status status reservation
     */
    StatusReservation(String status) {
        this.status = status;
    }

    /**
     * Gets the value of the status property
     *
     * @return property value status reservation
     */
    public String getStatus() {
        return status;
    }

    /**
     * The method sets the value of the status property
     *
     * @param status status reservation
     */
    public void setStatus(String status) {
        this.status = status;
    }
}