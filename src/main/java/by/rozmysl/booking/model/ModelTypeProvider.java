package by.rozmysl.booking.model;

import by.rozmysl.booking.model.dao.Dao;

import static by.rozmysl.booking.model.dao.DaoProvider.*;
import static by.rozmysl.booking.model.util.LoggerMessageError.*;
import static by.rozmysl.booking.model.util.SqlQuery.*;

public enum ModelTypeProvider {
    /* USER */
    USER_FIND_BY_ID(USER_QUERY_FIND_BY_ID, MESSAGE_USER_FIND_BY_ID, getInstance().getUserDao()),
    USER_FIND_ALL(USER_QUERY_FIND_ALL, MESSAGE_USER_FIND_ALL, getInstance().getUserDao()),
    USER_SAVE(USER_QUERY_SAVE, MESSAGE_USER_SAVE, getInstance().getUserDao()),
    USER_ACTIVATE(USER_QUERY_ACTIVATE, MESSAGE_USER_ACTIVATE, getInstance().getUserDao()),
    USER_DELETE(USER_QUERY_DELETE, MESSAGE_USER_DELETE, getInstance().getUserDao()),
    USER_FIND_BY_ACTIVATION_CODE(USER_QUERY_FIND_BY_ACTIVATION_CODE, MESSAGE_USER_FIND_BY_ACTIVATION_CODE, getInstance().getUserDao()),
    USER_FIND_ROWS_COUNT(USER_QUERY_FIND_ROWS_COUNT, MESSAGE_COUNT_USERS_ROWS, getInstance().getUserDao()),
    USER_CHANGE_ACCOUNT_LOCK(USER_QUERY_CHANGE_ACCOUNT_LOCK, MESSAGE_USER_CHANGE_ACCOUNT_LOCK, getInstance().getUserDao()),

    /* USER_ROLE */
    USER_ROLE_SAVE(USER_ROLE_QUERY_SAVE, MESSAGE_USER_ROLE_SAVE, getInstance().getRoleDao()),
    USER_ROLE_DELETE(USER_ROLE_QUERY_DELETE, MESSAGE_USER_ROLE_DELETE, getInstance().getRoleDao()),
    USER_ROLE_DELETE_ADMIN(USER_ROLE_QUERY_DELETE_ADMIN, MESSAGE_USER_ROLE_DELETE_ADMIN, getInstance().getRoleDao()),

    /* BOOKING */
    BOOKING_FIND_BY_ID(BOOKING_QUERY_FIND_BY_ID, MESSAGE_BOOKING_FIND_BY_ID, getInstance().getBookingDao()),
    BOOKING_FIND_ALL(BOOKING_QUERY_FIND_ALL, MESSAGE_BOOKING_FIND_ALL, getInstance().getBookingDao()),
    BOOKING_SAVE(BOOKING_QUERY_SAVE, MESSAGE_BOOKING_SAVE, getInstance().getBookingDao()),
    BOOKING_DELETE(BOOKING_QUERY_DELETE, MESSAGE_BOOKING_DELETE, getInstance().getBookingDao()),
    BOOKING_FIND_ALL_BOOKINGS_BETWEEN_TWO_DATES(BOOKING_QUERY_FIND_ALL_BOOKINGS_BETWEEN_TWO_DATES,
            MESSAGE_BOOKING_FIND_ALL_BOOKINGS_BETWEEN_TWO_DATES, getInstance().getBookingDao()),
    BOOKING_FIND_ALL_BOOKINGS_BY_USER(BOOKING_QUERY_FIND_ALL_BOOKINGS_BY_USER,
            MESSAGE_BOOKING_FIND_ALL_BOOKINGS_BY_USER, getInstance().getBookingDao()),
    BOOKING_CHANGE_ROOM(BOOKING_QUERY_CHANGE_ROOM, MESSAGE_BOOKING_CHANGE_ROOM, getInstance().getBookingDao()),
    BOOKING_CHANGE_BOOKING_STATUS(BOOKING_QUERY_CHANGE_BOOKING_STATUS, MESSAGE_BOOKING_CHANGE_BOOKING_STATUS, getInstance().getBookingDao()),
    BOOKING_FIND_ROWS_COUNT(BOOKING_QUERY_FIND_ROWS_COUNT, MESSAGE_COUNT_BOOKINGS_ROWS, getInstance().getBookingDao()),

    /* ADDITIONALSERVICES */
    ADDITIONALSERVICES_FIND_BY_ID(ADDITIONALSERVICES_QUERY_FIND_BY_ID, MESSAGE_ADDITIONALSERVICES_FIND_BY_ID, getInstance().getServicesDao()),
    ADDITIONALSERVICES_FIND_ALL(ADDITIONALSERVICES_QUERY_FIND_ALL, MESSAGE_ADDITIONALSERVICES_FIND_ALL, getInstance().getServicesDao()),
    ADDITIONALSERVICES_CHANGE_SERVICE_PRICE(ADDITIONALSERVICES_QUERY_CHANGE_SERVICE_PRICE,
            MESSAGE_ADDITIONALSERVICES_CHANGE_SERVICE_PRICE, getInstance().getServicesDao()),

    /* FOOD */
    FOOD_FIND_BY_ID(FOOD_QUERY_FIND_BY_ID, MESSAGE_FOOD_FIND_BY_ID, getInstance().getFoodDao()),
    FOOD_FIND_ALL(FOOD_QUERY_FIND_ALL, MESSAGE_FOOD_FIND_ALL, getInstance().getFoodDao()),
    FOOD_CHANGE_FOOD_PRICE(FOOD_QUERY_CHANGE_FOOD_PRICE, MESSAGE_FOOD_CHANGE_FOOD_PRICE, getInstance().getFoodDao()),

    /* ROOM */
    ROOM_FIND_BY_ID(ROOM_QUERY_FIND_BY_ID, MESSAGE_ROOM_FIND_BY_ID, getInstance().getRoomDao()),
    ROOM_FIND_ALL(ROOM_QUERY_FIND_ALL, MESSAGE_ROOM_FIND_ALL, getInstance().getRoomDao()),
    ROOM_SAVE(ROOM_QUERY_SAVE, MESSAGE_ROOM_SAVE, getInstance().getRoomDao()),
    ROOM_DELETE(ROOM_QUERY_DELETE, MESSAGE_ROOM_DELETE, getInstance().getRoomDao()),
    ROOM_UPDATE_PRICE(ROOM_QUERY_UPDATE_PRICE, MESSAGE_ROOM_UPDATE_PRICE, getInstance().getRoomDao()),
    ROOM_UPDATE_PARAMETERS(ROOM_QUERY_UPDATE_PARAMETERS, MESSAGE_ROOM_UPDATE_PARAMETERS, getInstance().getRoomDao()),
    ROOM_FIND_ALL_TYPES_ROOMS(ROOM_QUERY_FIND_ALL_TYPES_ROOMS, MESSAGE_ROOM_FIND_ALL_TYPES_ROOMS, getInstance().getRoomDao()),
    ROOM_FIND_ALL_ROOMS_BY_TYPES_AND_SLEEPS(ROOM_QUERY_FIND_ALL_ROOMS_BY_TYPES_AND_SLEEPS,
            MESSAGE_ROOM_FIND_ALL_ROOMS_BY_TYPES_AND_SLEEPS, getInstance().getRoomDao()),
    ROOM_FIND_ROOM_BY_TYPE_AND_SLEEPING(ROOM_QUERY_FIND_ROOM_BY_TYPE_AND_SLEEPING,
            MESSAGE_ROOM_FIND_ROOM_BY_TYPE_AND_SLEEPING, getInstance().getRoomDao()),
    ROOM_FIND_ALL_FREE_ROOMS_BETWEEN_TWO_DATES(ROOM_QUERY_FIND_ALL_FREE_ROOMS_BETWEEN_TWO_DATES,
            MESSAGE_ROOM_FIND_ALL_FREE_ROOMS_BETWEEN_TWO_DATES, getInstance().getRoomDao()),
    ROOM_FIND_ALL_TYPES_FREE_ROOMS_BETWEEN_TWO_DATES(ROOM_QUERY_FIND_ALL_TYPES_FREE_ROOMS_BETWEEN_TWO_DATES,
            MESSAGE_ROOM_FIND_ALL_TYPES_FREE_ROOMS_BETWEEN_TWO_DATES, getInstance().getRoomDao()),
    ROOM_FIND_ALL_FREE_ROOMS_BETWEEN_TWO_DATES_BY_TYPES_AND_SLEEPS(ROOM_QUERY_FIND_ALL_FREE_ROOMS_BETWEEN_TWO_DATES_BY_TYPES_AND_SLEEPS,
            MESSAGE_ROOM_FIND_ALL_FREE_ROOMS_BETWEEN_TWO_DATES_BY_TYPES_AND_SLEEPS, getInstance().getRoomDao()),
    ROOM_FIND_ALL_TYPES_FREE_ROOMS_BETWEEN_TWO_DATES_WITH_GREATER_OR_EQUAL_SLEEPS
            (ROOM_QUERY_FIND_ALL_TYPES_FREE_ROOMS_BETWEEN_TWO_DATES_WITH_GREATER_OR_EQUAL_SLEEPS,
                    MESSAGE_ROOM_FIND_ALL_TYPES_FREE_ROOMS_BETWEEN_TWO_DATES_WITH_GREATER_OR_EQUAL_SLEEPS, getInstance().getRoomDao()),
    ROOM_FIND_ALL_FREE_ROOMS_BETWEEN_TWO_DATES_WITH_GREATER_OR_EQUAL_SLEEPS(ROOM_QUERY_FIND_ALL_FREE_ROOMS_BETWEEN_TWO_DATES_WITH_GREATER_OR_EQUAL_SLEEPS,
            MESSAGE_ROOM_FIND_ALL_FREE_ROOMS_BETWEEN_TWO_DATES_WITH_GREATER_OR_EQUAL_SLEEPS, getInstance().getRoomDao());

    private final String query;
    private final String loggerMessage;
    private final Dao<?, ?> dao;

    ModelTypeProvider(String query, String loggerMessage, Dao<?, ?> dao) {
        this.query = query;
        this.loggerMessage = loggerMessage;
        this.dao = dao;
    }

    public String getQuery() {
        return query;
    }

    public String getLoggerMessage() {
        return loggerMessage;
    }

    public Dao<?, ?> getDao() {
        return dao;
    }
}