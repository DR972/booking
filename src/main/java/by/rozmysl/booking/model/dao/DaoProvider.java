package by.rozmysl.booking.model.dao;

import by.rozmysl.booking.model.dao.impl.*;

/**
 * Provides class DaoProvider.
 */
public final class DaoProvider {
    private final AdditionalServicesDao servicesDao = new AdditionalServicesDaoImpl();
    private final FoodDao foodDao = new FoodDaoImpl();
    private final RoomDao roomDao = new RoomDaoImpl();
    private final BookingDao bookingDao = new BookingDaoImpl();
    private final RoleDao roleDao = new RoleDaoImpl();
    private final UserDao userDAO = new UserDaoImpl();

    /**
     * Private constructor without parameters.
     * Restrict instantiation of the class.
     */
    private DaoProvider() {
    }

    private static class DaoProviderHolder {
        static final DaoProvider INSTANCE = new DaoProvider();
    }

    /**
     * Returns DaoProvider instance. Initialize instance if it doesn't.
     *
     * @return dao
     */
    public static DaoProvider getInstance() {
        return DaoProviderHolder.INSTANCE;
    }

    /**
     * Provides access to the AdditionalServicesDao interface.
     *
     * @return the implementation of the AdditionalServicesDao interface
     */
    public AdditionalServicesDao getServicesDao() {
        return servicesDao;
    }

    /**
     * Provides access to the FoodDao interface.
     *
     * @return the implementation of the FoodDao interface
     */
    public FoodDao getFoodDao() {
        return foodDao;
    }

    /**
     * Provides access to the RoomDao interface.
     *
     * @return the implementation of the RoomDao interface
     */
    public RoomDao getRoomDao() {
        return roomDao;
    }

    /**
     * Provides access to the BookingDao interface.
     *
     * @return the implementation of the BookingDao interface
     */
    public BookingDao getBookingDao() {
        return bookingDao;
    }

    /**
     * Provides access to the RoleDao interface.
     *
     * @return the implementation of the RoleDao interface
     */
    public RoleDao getRoleDao() {
        return roleDao;
    }

    /**
     * Provides access to the UserDao interface.
     *
     * @return the implementation of the UserDao interface
     */
    public UserDao getUserDao() {
        return userDAO;
    }
}
