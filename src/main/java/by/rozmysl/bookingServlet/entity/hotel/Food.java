package by.rozmysl.bookingServlet.entity.hotel;

import java.util.StringJoiner;

/**
 * It is used to store Food objects with the <b>type</b> and <b>price</b> properties.
 */
public class Food {
    private String type;
    private double price;

    /**
     * The constructor creates a new object Food with the <b>type</b> properties
     *
     * @param type id of the Food
     */
    public Food(String type) {
        this.type = type;
    }

    /**
     * The constructor creates a new object Food with the properties <b>type</b> and <b>price</b>
     *
     * @param type  id of the Food
     * @param price price
     */
    public Food(String type, double price) {
        this.type = type;
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
     * Gets the value of the price property
     *
     * @return a value of the price
     */
    public double getPrice() {
        return price;
    }

    /**
     * The method sets the value of the price property
     *
     * @param price price
     */
    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Food.class.getSimpleName() + "[", "]")
                .add("type='" + type + "'")
                .add("price=" + price)
                .toString();
    }
}