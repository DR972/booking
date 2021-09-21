package by.rozmysl.bookingServlet.dao;

import by.rozmysl.bookingServlet.dao.hotel.*;
import by.rozmysl.bookingServlet.dao.user.*;

/**
 * Provides class DaoFactory.
 */
public class DaoFactory {

    /**
     * Provides access to the AdditionalServicesDao interface.
     *
     * @return the implementation of the AdditionalServicesDao interface
     */
    public AdditionalServicesDao servicesDao() {
        return new AdditionalServicesDaoImp();
    }

    /**
     * Provides access to the FoodDao interface.
     *
     * @return the implementation of the FoodDao interface
     */
    public FoodDao foodDao() {
        return new FoodDaoImp();
    }

    /**
     * Provides access to the RoomDao interface.
     *
     * @return the implementation of the RoomDao interface
     */
    public RoomDao roomDao() {
        return new RoomDaoImp();
    }

    /**
     * Provides access to the BookingDao interface.
     *
     * @return the implementation of the BookingDao interface
     */
    public BookingDao bookingDao() {
        return new BookingDaoImp();
    }

    /**
     * Provides access to the RoleDao interface.
     *
     * @return the implementation of the RoleDao interface
     */
    public RoleDao roleDao() {
        return new RoleDaoImp();
    }

    /**
     * Provides access to the UserDao interface.
     *
     * @return the implementation of the UserDao interface
     */
    public UserDao userDao() {
        return new UserDaoImp();
    }
}
