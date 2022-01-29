package by.rozmysl.booking.model.service;

import by.rozmysl.booking.exception.MailException;
import by.rozmysl.booking.model.service.impl.*;

/**
 * Provides class ServiceProvider.
 */
public final class ServiceProvider {
    private final AdditionalServicesService service = new AdditionalServicesServiceImpl();
    private final FoodService foodService = new FoodServiceImpl();
    private final RoomService roomService = new RoomServiceImpl();
    private final BookingService bookingService = new BookingServiceImpl();
    private final UserService userService = new UserServiceImpl();

    /**
     * Private constructor without parameters.
     * Restrict instantiation of the class.
     */
    private ServiceProvider() {
    }

    private static class ServiceProviderHolder {
        static final ServiceProvider INSTANCE = new ServiceProvider();
    }

    /**
     * Returns ServiceProvider instance. Initialize instance if it doesn't.
     *
     * @return service
     */
    public static ServiceProvider getInstance() {
        return ServiceProvider.ServiceProviderHolder.INSTANCE;
    }

    /**
     * Provides access to the AdditionalServicesService interface.
     *
     * @return the implementation of the AdditionalServicesService interface
     */
    public AdditionalServicesService getServicesService() {
        return service;
    }

    /**
     * Provides access to the FoodService interface.
     *
     * @return the implementation of the FoodService interface
     */
    public FoodService getFoodService() {
        return foodService;
    }

    /**
     * Provides access to the RoomService interface.
     *
     * @return the implementation of the RoomService interface
     */
    public RoomService getRoomService() {
        return roomService;
    }

    /**
     * Provides access to the BookingService interface.
     *
     * @return the implementation of the BookingService interface
     */
    public BookingService getBookingService() {
        return bookingService;
    }

    /**
     * Provides access to the UserService interface.
     *
     * @return the implementation of the UserService interface
     */
    public UserService getUserService() {
        return userService;
    }

    /**
     * Provides access to the MailSenderService interface.
     *
     * @return the implementation of the MailSenderService interface
     */
    public MailSenderService getMailSender() throws MailException {
        return new MailSenderServiceImpl();
    }
}
