package by.rozmysl.bookingServlet.dao;

import by.rozmysl.bookingServlet.dao.hotel.*;
import by.rozmysl.bookingServlet.dao.user.*;
import by.rozmysl.bookingServlet.db.ConnectionSource;

/**
 * Provides class DaoFactory.
 */
public class DaoFactory {
    private static DaoFactory dao;

    /**
     * Returns DaoFactory instance. Initialize instance if it doesn't.
     *
     * @return dao
     */
    public static synchronized DaoFactory getInstance() {
        if (dao == null) dao = new DaoFactory();
        return dao;
    }

    /**
     * Provides access to the AdditionalServicesDao interface.
     *
     * @return the implementation of the AdditionalServicesDao interface
     */
    public AdditionalServicesDao servicesDao(ConnectionSource con) {
        return new AdditionalServicesDaoImp(con);
    }

    /**
     * Provides access to the FoodDao interface.
     *
     * @return the implementation of the FoodDao interface
     */
    public FoodDao foodDao(ConnectionSource con) {
        return new FoodDaoImp(con);
    }

    /**
     * Provides access to the RoomDao interface.
     *
     * @return the implementation of the RoomDao interface
     */
    public RoomDao roomDao(ConnectionSource con) {
        return new RoomDaoImp(con);
    }

    /**
     * Provides access to the BookingDao interface.
     *
     * @return the implementation of the BookingDao interface
     */
    public BookingDao bookingDao(ConnectionSource con) {
        return new BookingDaoImp(con);
    }

    /**
     * Provides access to the RoleDao interface.
     *
     * @return the implementation of the RoleDao interface
     */
    public RoleDao roleDao(ConnectionSource con) {
        return new RoleDaoImp(con);
    }

    /**
     * Provides access to the UserDao interface.
     *
     * @return the implementation of the UserDao interface
     */
    public UserDao userDao(ConnectionSource con) {
        return new UserDaoImp(con);
    }
}
