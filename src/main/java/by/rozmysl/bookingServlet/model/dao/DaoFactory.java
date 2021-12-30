package by.rozmysl.bookingServlet.model.dao;

import by.rozmysl.bookingServlet.model.dao.Impl.*;

import java.sql.Connection;

/**
 * Provides class DaoFactory.
 */
public final class DaoFactory {
    private static DaoFactory dao;

    private DaoFactory() {
    }

    /**
     * Returns DaoFactory instance. Initialize instance if it doesn't.
     *
     * @return dao
     */
    public static DaoFactory getInstance() {
        if (dao == null) {
            dao = new DaoFactory();
        }
        return dao;
    }

    /**
     * Provides access to the AdditionalServicesDao interface.
     *
     * @return the implementation of the AdditionalServicesDao interface
     */
    public AdditionalServicesDao servicesDao(Connection connection) {
        return new AdditionalServicesDaoImpl(connection);
    }

    /**
     * Provides access to the FoodDao interface.
     *
     * @return the implementation of the FoodDao interface
     */
    public FoodDao foodDao(Connection connection) {
        return new FoodDaoImpl(connection);
    }

    /**
     * Provides access to the RoomDao interface.
     *
     * @return the implementation of the RoomDao interface
     */
    public RoomDao roomDao(Connection connection) {
        return new RoomDaoImpl(connection);
    }

    /**
     * Provides access to the BookingDao interface.
     *
     * @return the implementation of the BookingDao interface
     */
    public BookingDao bookingDao(Connection connection) {
        return new BookingDaoImpl(connection);
    }

    /**
     * Provides access to the RoleDao interface.
     *
     * @return the implementation of the RoleDao interface
     */
    public RoleDao roleDao(Connection connection) {
        return new RoleDaoImpl(connection);
    }

    /**
     * Provides access to the UserDao interface.
     *
     * @return the implementation of the UserDao interface
     */
    public UserDao userDao(Connection connection) {
        return new UserDaoImpl(connection);
    }
}
