package by.rozmysl.bookingServlet.model.entity.hotel;

import by.rozmysl.bookingServlet.model.entity.Entity;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.StringJoiner;

/**
 * It is used for store objects of Additional Services with the property <b>price</b>.
 */
public class AdditionalServices extends Entity<String> {
    private BigDecimal price;

    /**
     * The constructor creates a new object AdditionalServices with the <b>type</b> property
     *
     * @param type id of the Additional services
     */
    public AdditionalServices(String type) {
        super(type);
    }

    /**
     * The constructor creates a new object AdditionalServices with the properties <b>type</b> and <b>price</b>
     *
     * @param type  id of the Additional services
     * @param price price
     */
    public AdditionalServices(String type, BigDecimal price) {
        super(type);
        this.price = price;
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
        if (!(o instanceof AdditionalServices)) return false;
        if (!super.equals(o)) return false;

        AdditionalServices that = (AdditionalServices) o;

        return Objects.equals(price, that.price);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (price != null ? price.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", AdditionalServices.class.getSimpleName() + "[", "]")
                .add("type=" + id)
                .add("price=" + price)
                .toString();
    }
}