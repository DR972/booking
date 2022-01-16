package by.rozmysl.bookingServlet.model.entity;

import java.io.Serializable;
import java.util.Objects;

/**
 * Represents common database table's properties.
 */
public abstract class Entity<ID> implements Serializable {
    protected ID id;

    /**
     * Constructs entity without parameters.
     */
    public Entity() {
    }

    /**
     * The constructor creates a new object User with the <b>id</b> properties
     *
     * @param id of the Entity
     */
    public Entity(ID id) {
        this.id = id;
    }

    /**
     * Gets the value of the id property
     *
     * @return a value of the activationCode
     */
    public ID getId() {
        return id;
    }

    /**
     * The method sets the value of the id property
     *
     * @param id of the Entity
     */
    public void setId(ID id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Entity)) return false;

        Entity<?> entity = (Entity<?>) o;

        return Objects.equals(id, entity.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
