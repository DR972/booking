package by.rozmysl.bookingServlet.entity.hotel;

import java.math.BigDecimal;
import java.util.StringJoiner;

/**
 * It is used to store Additional Services objects with the <b>roomNumber</b>, <b>type</b>, <b>sleeps</b>, <b>price</b> properties.
 */
public class Room {
    private int roomNumber;
    private String type;
    private int sleeps;
    private BigDecimal price;

    /**
     * The constructor creates a new object Room without parameters
     */
    public Room() {
    }

    /**
     * The constructor creates a new object Room with the <b>roomNumber</b>, <b>type</b>, <b>sleeps</b> properties
     *
     * @param roomNumber id of the Room
     * @param type       type
     * @param sleeps     sleeps
     */
    public Room(int roomNumber, String type, int sleeps) {
        this.roomNumber = roomNumber;
        this.type = type;
        this.sleeps = sleeps;
    }

    /**
     * The constructor creates a new object Room with the <b>roomNumber</b> properties
     *
     * @param roomNumber id of the Room
     */
    public Room(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    /**
     * The constructor creates a new object Room with the <b>roomNumber</b>, <b>type</b>, <b>sleeps</b>, <b>price</b> properties
     *
     * @param roomNumber id of the Room
     * @param type       type
     * @param sleeps     sleeps
     * @param price      price
     */
    public Room(int roomNumber, String type, int sleeps, BigDecimal price) {
        this.roomNumber = roomNumber;
        this.type = type;
        this.sleeps = sleeps;
        this.price = price;
    }

    /**
     * Gets the value of the roomNumber property
     *
     * @return a value of the roomNumber
     */
    public int getRoomNumber() {
        return roomNumber;
    }

    /**
     * The method sets the value of the roomNumber property
     *
     * @param roomNumber roomNumber
     */
    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    /**
     * Gets the value of the type property
     *
     * @return a value of the type
     */
    public String getType() {
        return type;
    }

    /**
     * The method sets the value of the type property
     *
     * @param type type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Gets the value of the sleeps property
     *
     * @return a value of the sleeps
     */
    public int getSleeps() {
        return sleeps;
    }

    /**
     * The method sets the value of the sleeps property
     *
     * @param sleeps sleeps
     */
    public void setSleeps(int sleeps) {
        this.sleeps = sleeps;
    }

    /**
     * Gets the value of the price property
     *
     * @return a value of the price
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * The method sets the value of the price property
     *
     * @param price price
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Room.class.getSimpleName() + "[", "]")
                .add("roomNumber=" + roomNumber)
                .add("type='" + type + "'")
                .add("sleeps=" + sleeps)
                .add("price=" + price)
                .toString();
    }
}