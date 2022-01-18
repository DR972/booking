package by.rozmysl.booking.controller.command;

public final class RequestAttribute {

    /**
     * Private constructor without parameters.
     * Restrict instantiation of the class.
     */
    private RequestAttribute() {
    }

    public static final String ALL_ROOMS = "allRooms";
    public static final String ALL_ROOMS_BY_TYPES_AND_SLEEPS = "allRoomsByTypesAndSleeps";
    public static final String ALL_TYPES_ROOMS = "allTypesRooms";
    public static final String AVAILABLE_ROOMS = "availableRooms";
    public static final String FREE_ROOMS = "freeRooms";
    public static final String FIND_ALL_TYPES_FREE_ROOMS_BETWEEN_TWO_DATES_WITH_GREATER_OR_EQUAL_SLEEPS = "rooms";
    public static final String TYPE = "type";
    public static final String SLEEPS = "sleeps";
    public static final String ROOM_NUMBER = "roomNumber";
    public static final String NEW_ROOM = "newRoom";
    public static final String ERROR_ROOM_NUMBER = "errorRoomNumber";
    public static final String ERROR_ROOM_NUMBER_RU = "Изменить параметры существующих номеров можно на странице 'Изменить параметры номеров'";

    public static final String ALL_FOOD = "allFood";
    public static final String ALL_SERVICES = "allServices";

    public static final String BOOKING = "booking";
    public static final String CONFIRMATION = "confirmation";
    public static final String ALL_BOOKINGS = "allBookings";
    public static final String DATE_ERROR = "dateError";
    public static final String DATE_ERROR_RU = "Неправильно указаны даты!";
    public static final String MISSED = "missed";
    public static final String MESSAGE_MISSED = "message.missed";
    public static final String USER_BOOKING = "userBooking";
    public static final String NO_AVAILABLE = "noAvailable";
    public static final String MESSAGE_NO_AVAILABLE = "message.noAvailable";

    public static final String ALL_USERS = "allUsers";
    public static final String ERROR_DELETE_USER = "errorDeleteUser";
    public static final String ERROR_DELETE_USER_RU = "Нельзя удалить пользователя, имеющего бронирования!";
    public static final String MESSAGE_ACTIVE = "messageActive";
    public static final String MESSAGE_ACTIVE_TRUE = "message.activeTrue";
    public static final String MESSAGE_ACTIVE_ERROR = "message.activeError";
    public static final String LOGIN_ERROR = "loginError";
    public static final String ERROR_VALIDATE = "errorValidate";
    public static final String STATUS = "status";

    public static final String ROWS = "rows";
    public static final String PAGE_NUMBER = "pageNumber";
    public static final String COUNT_PAGES = "countPages";

    public static final String CODE = "code";
    public static final String URI = "uri";
    public static final String SERVLET_NAME = "servletName";
    public static final String NAME = "name";
    public static final String MESSAGE = "message";
}
