package by.rozmysl.bookingServlet.controller.command;

/**
 * Stores page addresses.
 */
public final class PageAddress {

    /**
     * Private constructor without parameters.
     * Restrict instantiation of the class.
     */
    private PageAddress() {
    }

    /* Admin pages */
    public static final String ADMIN = "/WEB-INF/views/admin/admin.jsp";
    public static final String ADMIN_REDIRECT = "/admin/admin";
    public static final String ADD_ROOM = "/WEB-INF/views/admin/addRoom.jsp";
    public static final String ALL_BOOKINGS = "/WEB-INF/views/admin/allBookings.jsp";
    public static final String ALL_ROOMS = "/WEB-INF/views/admin/allRooms.jsp";
    public static final String ALL_USERS = "/WEB-INF/views/admin/allUsers.jsp";
    public static final String CHANGE_ROOM_PARAMETERS = "/WEB-INF/views/admin/changeRoomParameters.jsp";
    public static final String CHANGE_SERVICES_PRICE = "/WEB-INF/views/admin/changeServicesPrice.jsp";
    public static final String FREE_ROOMS = "/WEB-INF/views/admin/freeRooms.jsp";

    /* Anonymous pages */
    public static final String LOGIN = "/WEB-INF/views/anonymous/login.jsp";
    public static final String REGISTRATION = "/WEB-INF/views/anonymous/registration.jsp";
    public static final String PRICE = "/WEB-INF/views/anonymous/price.jsp";
    public static final String MAIN = "/";

    /* USER pages */
    public static final String USER = "/WEB-INF/views/user/user.jsp";
    public static final String USER_REDIRECT = "/user/user";
    public static final String BOOKING_DETAILS = "/WEB-INF/views/user/bookingDetails.jsp";
    public static final String BOOKING = "/WEB-INF/views/user/booking.jsp";
    public static final String CONFIRMATION = "/WEB-INF/views/user/confirmation.jsp";
    public static final String USER_BOOKINGS = "/WEB-INF/views/user/userBookings.jsp";


    /* Error pages */
    public static final String PAGE_DOES_NOT_EXIST = "/WEB-INF/views/error/pageDoesNotExist.jsp";
    public static final String ACCESS_DENIED = "/WEB-INF/views/error/accessDenied.jsp";
    public static final String ERROR = "/WEB-INF/views/error/error.jsp";
    public static final String ERROR_500 = "/WEB-INF/views/error/error500.jsp";
}
