package by.rozmysl.booking.model.dao;

import by.rozmysl.booking.exception.DaoException;
import by.rozmysl.booking.model.entity.user.Booking;

/**
 * Provides the base model DAO interface for `BOOKING` table.
 */
public interface BookingDao extends Dao<Booking, Long> {

    /**
     * Saves information to a database with automatic key generation.
     *
     * @param sql          the wording of the request to the database
     * @param errorMessage errorMessage in case of an error
     * @param params       Object parameters
     * @throws DaoException if there was an error accessing the database
     */
    void saveWithGeneratedKeys(String sql, String errorMessage, Object... params) throws DaoException;
}