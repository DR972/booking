package by.rozmysl.booking.model.entity.hotel;

import by.rozmysl.booking.model.entity.Entity;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.StringJoiner;

/**
 * It is used to store Additional Services objects with the <b>type</b>, <b>sleeps</b>, <b>price</b> properties.
 */
public class Room extends Entity<Integer> {
    private String type;
    private int sleeps;
    private BigDecimal price;

    /**
     * The constructor creates a new object Room without parameters
     */
    public Room() {
    }

    /**
     * The constructor creates a new object Room with the <b>roomNumber</b> properties
     *
     * @param roomNumber id of the Room
     */
    public Room(int roomNumber) {
        super(roomNumber);
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
        super(roomNumber);
        this.type = type;
        this.sleeps = sleeps;
        this.price = price;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Room)) return false;
        if (!super.equals(o)) return false;

        Room room = (Room) o;

        if (sleeps != room.sleeps) return false;
        if (!Objects.equals(type, room.type)) return false;
        return Objects.equals(price, room.price);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + sleeps;
        result = 31 * result + (price != null ? price.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Room.class.getSimpleName() + "[", "]")
                .add("roomNumber=" + id)
                .add("type='" + type + "'")
                .add("sleeps=" + sleeps)
                .add("price=" + price)
                .toString();
    }
}