package by.rozmysl.bookingServlet.model.service;

import by.rozmysl.bookingServlet.exception.MailException;
import by.rozmysl.bookingServlet.model.service.impl.*;

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
    public AdditionalServicesService getServicesService() {
        return new AdditionalServicesServiceImpl();
    }

    /**
     * Provides access to the FoodService interface.
     *
     * @return the implementation of the FoodService interface
     */
    public FoodService getFoodService() {
        return new FoodServiceImpl();
    }

    /**
     * Provides access to the RoomService interface.
     *
     * @return the implementation of the RoomService interface
     */
    public RoomService getRoomService() {
        return new RoomServiceImpl();
    }

    /**
     * Provides access to the BookingService interface.
     *
     * @return the implementation of the BookingService interface
     */
    public BookingService getBookingService() {
        return new BookingServiceImpl();
    }

    /**
     * Provides access to the UserService interface.
     *
     * @return the implementation of the UserService interface
     */
    public UserService getUserService() {
        return new UserServiceImpl();
    }

    /**
     * Provides access to the MailSender interface.
     *
     * @return the implementation of the MailSender interface
     */
    public MailSender getMailSender() throws MailException {
        return new MailSenderImpl();
    }
}
