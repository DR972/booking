package by.rozmysl.booking.model.entity.hotel;

import by.rozmysl.booking.model.entity.Entity;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.StringJoiner;

/**
 * It is used to store Food objects with the property <b>price</b>.
 */
public class Food extends Entity<String> {
    private BigDecimal price;

    /**
     * The constructor creates a new object Food with the <b>type</b> property
     *
     * @param type id of the Food
     */
    public Food(String type) {
        super(type);
    }

    /**
     * The constructor creates a new object Food with the properties <b>type</b> and <b>price</b>
     *
     * @param type  id of the Food
     * @param price price
     */
    public Food(String type, BigDecimal price) {
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
        if (!(o instanceof Food)) return false;
        if (!super.equals(o)) return false;

        Food food = (Food) o;

        return Objects.equals(price, food.price);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (price != null ? price.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Food.class.getSimpleName() + "[", "]")
                .add("type=" + id)
                .add("price=" + price)
                .toString();
    }
}