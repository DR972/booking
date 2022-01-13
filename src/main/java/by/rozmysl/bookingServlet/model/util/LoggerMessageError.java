package by.rozmysl.bookingServlet.model.util;

/**
 * Stores logger message.
 */
public final class LoggerMessageError {

    /**
     * Private constructor without parameters.
     * Restrict instantiation of the class.
     */
    private LoggerMessageError() {
    }

    /* TABLE USER */
    public static final String MESSAGE_COUNT_USERS_ROWS = "'CountUsersRows' query has been failed";
    public static final String MESSAGE_COUNT_USERS_PAGES = "'CountUsersPages' query has been failed";
    public static final String MESSAGE_USER_FIND_BY_ID = "'User findById' query has been failed";
    public static final String MESSAGE_USER_FIND_ALL = "'User findAll' query has been failed";
    public static final String MESSAGE_USER_SAVE = "'Save User' query has been failed";
    public static final String MESSAGE_USER_DELETE = "'Delete User' query has been failed";
    public static final String MESSAGE_USER_ACTIVATE = "'ActivateUser' query has been failed";
    public static final String MESSAGE_USER_FIND_BY_ACTIVATION_CODE = "'FindUserByActivationCode' query has been failed";
    public static final String MESSAGE_USER_CHANGE_ACCOUNT_LOCK = "'changeAccountLock' query has been failed";

    /* TABLE USER_ROLE */
    public static final String MESSAGE_USER_ROLE_SAVE = "'UserRole save' query has been failed";
    public static final String MESSAGE_USER_ROLE_DELETE = "'UserRole delete' query has been failed";
    public static final String MESSAGE_USER_ROLE_DELETE_ADMIN = "'UserRole delete ADMIN' query has been failed";

    /* TABLE BOOKING */
    public static final String MESSAGE_COUNT_BOOKINGS_ROWS = "'CountBookingsRows' query has been failed";
    public static final String MESSAGE_COUNT_BOOKINGS_PAGES = "'countBookingsPages' query has been failed";
    public static final String MESSAGE_BOOKING_FIND_BY_ID = "'Booking findById' query has been failed";
    public static final String MESSAGE_BOOKING_FIND_ALL = "'Booking findAll' query has been failed";
    public static final String MESSAGE_BOOKING_SAVE = "'Booking save' query has been failed";
    public static final String MESSAGE_BOOKING_DELETE = "'Booking delete' query has been failed";
    public static final String MESSAGE_BOOKING_FIND_ALL_BOOKINGS_BETWEEN_TWO_DATES = "'FindAllBookingsBetweenTwoDates' query has been failed";
    public static final String MESSAGE_BOOKING_FIND_ALL_BOOKINGS_BY_USER = "'FindAllBookingsByUser' query has been failed";
    public static final String MESSAGE_BOOKING_CHANGE_ROOM = "'Booking changeRoom' query has been failed";
    public static final String MESSAGE_BOOKING_CHANGE_BOOKING_STATUS = "'ChangeBookingStatus' query has been failed";
    public static final String MESSAGE_BOOKING_INVOICE = "Failed to create and send an invoice";

    /* TABLE ROOM */
    public static final String MESSAGE_ROOM_FIND_BY_ID = "'Room findById' query has been failed";
    public static final String MESSAGE_ROOM_FIND_ALL = "'Room findAll' query has been failed";
    public static final String MESSAGE_ROOM_SAVE = "'Room save' query has been failed";
    public static final String MESSAGE_ROOM_DELETE = "'Room delete' query has been failed";
    public static final String MESSAGE_ROOM_UPDATE_PRICE = "'Room updatePrice' query has been failed";
    public static final String MESSAGE_ROOM_UPDATE_PARAMETERS = "'Room updateParameters' query has been failed";
    public static final String MESSAGE_ROOM_FIND_ALL_TYPES_ROOMS = "'FindAllTypesRooms' query has been failed";
    public static final String MESSAGE_ROOM_FIND_ALL_ROOMS_BY_TYPES_AND_SLEEPS = "'FindAllRoomsByTypesAndSleeps' query has been failed";
    public static final String MESSAGE_ROOM_FIND_ROOM_BY_TYPE_AND_SLEEPING = "'FindRoomByTypeAndSleeping' query has been failed";
    public static final String MESSAGE_ROOM_FIND_ALL_FREE_ROOMS_BETWEEN_TWO_DATES = "'FindAllFreeRoomsBetweenTwoDates' query has been failed";
    public static final String MESSAGE_ROOM_FIND_ALL_TYPES_FREE_ROOMS_BETWEEN_TWO_DATES = "'FindAllTypesFreeRoomsBetweenTwoDates' query has been failed";
    public static final String MESSAGE_ROOM_FIND_ALL_FREE_ROOMS_BETWEEN_TWO_DATES_BY_TYPES_AND_SLEEPS = "'FindAllFreeRoomsBetweenTwoDatesByTypesAndSleeps' query has been failed";
    public static final String MESSAGE_ROOM_FIND_ALL_TYPES_FREE_ROOMS_BETWEEN_TWO_DATES_WITH_GREATER_OR_EQUAL_SLEEPS = "'FindAllTypesFreeRoomsBetweenTwoDatesWithGreaterOrEqualSleeps' query has been failed";
    public static final String MESSAGE_ROOM_FIND_ALL_FREE_ROOMS_BETWEEN_TWO_DATES_WITH_GREATER_OR_EQUAL_SLEEPS = "'FindAllFreeRoomsBetweenTwoDatesWithGreaterOrEqualSleeps' query has been failed";

    /* TABLE FOOD */
    public static final String MESSAGE_FOOD_FIND_BY_ID = "'Food findById' query has been failed";
    public static final String MESSAGE_FOOD_FIND_ALL = "'Food findAll' query has been failed";
    public static final String MESSAGE_FOOD_CHANGE_FOOD_PRICE = "'ChangeFoodPrice' query has been failed";

    /* TABLE ADDITIONALSERVICES */
    public static final String MESSAGE_ADDITIONALSERVICES_FIND_BY_ID = "'AdditionalServices findById' query has been failed";
    public static final String MESSAGE_ADDITIONALSERVICES_FIND_ALL = "'AdditionalServices findAll' query has been failed";
    public static final String MESSAGE_ADDITIONALSERVICES_CHANGE_SERVICE_PRICE = "'ChangeServicePrice' query has been failed";
}
