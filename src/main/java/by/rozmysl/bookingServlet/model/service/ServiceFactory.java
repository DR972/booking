package by.rozmysl.bookingServlet.model.service;

import by.rozmysl.bookingServlet.model.service.impl.*;

import java.sql.Connection;

public final class ServiceFactory {
    private static ServiceFactory service;

    private ServiceFactory() {
    }

    /**
     * Returns ServiceFactory instance. Initialize instance if it doesn't.
     *
     * @return service
     */
    public static ServiceFactory getInstance() {
        if (service == null) {
            service = new ServiceFactory();
        }
        return service;
    }

    /**
     * Provides access to the AdditionalServicesService interface.
     *
     * @return the implementation of the AdditionalServicesService interface
     */
    public AdditionalServicesService servicesService(Connection connection) {
        return new AdditionalServicesServiceImpl(connection);
    }

    /**
     * Provides access to the FoodService interface.
     *
     * @return the implementation of the FoodService interface
     */
    public FoodService foodService(Connection connection) {
        return new FoodServiceImpl(connection);
    }

    /**
     * Provides access to the RoomService interface.
     *
     * @return the implementation of the RoomDao interface
     */
    public RoomService roomService(Connection connection) {
        return new RoomServiceImpl(connection);
    }

    /**
     * Provides access to the BookingService interface.
     *
     * @return the implementation of the BookingDao interface
     */
    public BookingService bookingService(Connection connection) {
        return new BookingServiceImpl(connection);
    }

    /**
     * Provides access to the RoleService interface.
     *
     * @return the implementation of the RoleDao interface
     */
    public RoleService roleService(Connection connection) {
        return new RoleServiceImpl(connection);
    }

    /**
     * Provides access to the UserService interface.
     *
     * @return the implementation of the UserDao interface
     */
    public UserService userService(Connection connection) {
        return new UserServiceImpl(connection);
    }
}
