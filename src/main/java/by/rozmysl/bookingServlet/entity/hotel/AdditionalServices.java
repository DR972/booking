package by.rozmysl.bookingServlet.entity.hotel;

import java.math.BigDecimal;
import java.util.StringJoiner;

/**
 * It is used for store objects of Additional Services with the properties <b>type</b> and <b>price</b>.
 */
public class AdditionalServices {
    private String type;
    private BigDecimal price;

    /**
     * The constructor creates a new object AdditionalServices with the <b>type</b> properties
     *
     * @param type id of the Additional services
     */
    public AdditionalServices(String type) {
        this.type = type;
    }

    /**
     * The constructor creates a new object AdditionalServices with the properties <b>type</b> and <b>price</b>
     *
     * @param type  id of the Additional services
     * @param price price
     */
    public AdditionalServices(String type, BigDecimal price) {
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
        return new StringJoiner(", ", AdditionalServices.class.getSimpleName() + "[", "]")
                .add("type='" + type + "'")
                .add("price=" + price)
                .toString();
    }
}