package by.rozmysl.bookingServlet.model.dao;

public final class ColumnName {

    /**
     * Private constructor without parameters.
     * Restrict instantiation of the class.
     */
    private ColumnName() {
    }

    /*TABLE USER*/
    public static final String USER_COLUMN_USERNAME = "USERNAME";
    public static final String USER_COLUMN_LAST_NAME = "LASTNAME";
    public static final String USER_COLUMN_FIRST_NAME = "FIRSTNAME";
    public static final String USER_COLUMN_PASSWORD = "PASSWORD";
    public static final String USER_COLUMN_ACTIVE = "ACTIVE";
    public static final String USER_COLUMN_EMAIL = "EMAIL";
    public static final String USER_COLUMN_ACTIVATIONCODE = "ACTIVATIONCODE";
    public static final String USER_COLUMN_BANNED = "BANNED";

    /*TABLE ROLE*/
    public static final String ROLE_COLUMN_NAME = "NAME";

    /*TABLE USER_ROLE*/
    public static final String USER_ROLE_COLUMN_USER = "USER";
    public static final String USER_ROLE_COLUMN_ROLE = "ROLE";

    /*TABLE BOOKING*/
    public static final String BOOKING_COLUMN_NUMBER = "NUMBER";
    public static final String BOOKING_COLUMN_USER = "USER";
    public static final String BOOKING_COLUMN_ROOM = "ROOM";
    public static final String BOOKING_COLUMN_PERSONS = "PERSONS";
    public static final String BOOKING_COLUMN_ARRIVAL = "ARRIVAL";
    public static final String BOOKING_COLUMN_DEPARTURE = "DEPARTURE";
    public static final String BOOKING_COLUMN_FOOD = "FOOD";
    public static final String BOOKING_COLUMN_DAYS = "DAYS";
    public static final String BOOKING_COLUMN_SERVICES = "SERVICES";
    public static final String BOOKING_COLUMN_AMOUNT = "AMOUNT";
    public static final String BOOKING_COLUMN_STATUS = "STATUS";

    /*TABLE ROOM*/
    public static final String ROOM_COLUMN_ROOMNUMBER = "ROOMNUMBER";
    public static final String ROOM_COLUMN_TYPE = "TYPE";
    public static final String ROOM_COLUMN_SLEEPS = "SLEEPS";
    public static final String ROOM_COLUMN_PRICE = "PRICE";

    /*TABLE FOOD*/
    public static final String FOOD_COLUMN_TYPE = "TYPE";
    public static final String FOOD_COLUMN_PRICE = "PRICE";

    /*TABLE ADDITIONALSERVICES*/
    public static final String ADDITIONALSERVICES_COLUMN_TYPE = "TYPE";
    public static final String ADDITIONALSERVICES_COLUMN_PRICE = "PRICE";

    public static final String ROWS_COUNT = "COUNT";
}
