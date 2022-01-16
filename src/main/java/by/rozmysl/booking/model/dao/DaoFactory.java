package by.rozmysl.booking.model.dao;

import by.rozmysl.booking.model.dao.impl.*;

/**
 * Provides class DaoFactory.
 */
public final class DaoFactory {
    private static DaoFactory daoFactory;

    /**
     * Private constructor without parameters.
     * Restrict instantiation of the class.
     */
    private DaoFactory() {
    }

    /**
     * Returns DaoFactory instance. Initialize instance if it doesn't.
     *
     * @return dao
     */
    public static DaoFactory getInstance() {
        if (daoFactory == null) {
            daoFactory = new DaoFactory();
        }
        return daoFactory;
    }

    /**
     * Provides access to the AdditionalServicesDao interface.
     *
     * @return the implementation of the AdditionalServicesDao interface
     */
    public AdditionalServicesDao getServicesDao() {
        return new AdditionalServicesDaoImpl();
    }

    /**
     * Provides access to the FoodDao interface.
     *
     * @return the implementation of the FoodDao interface
     */
    public FoodDao getFoodDao() {
        return new FoodDaoImpl();
    }

    /**
     * Provides access to the RoomDao interface.
     *
     * @return the implementation of the RoomDao interface
     */
    public RoomDao getRoomDao() {
        return new RoomDaoImpl();
    }

    /**
     * Provides access to the BookingDao interface.
     *
     * @return the implementation of the BookingDao interface
     */
    public BookingDao getBookingDao() {
        return new BookingDaoImpl();
    }

    /**
     * Provides access to the RoleDao interface.
     *
     * @return the implementation of the RoleDao interface
     */
    public RoleDao getRoleDao() {
        return new RoleDaoImpl();
    }

    /**
     * Provides access to the UserDao interface.
     *
     * @return the implementation of the UserDao interface
     */
    public UserDao getUserDao() {
        return new UserDaoImpl();
    }
}
