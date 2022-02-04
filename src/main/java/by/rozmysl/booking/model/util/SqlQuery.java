package by.rozmysl.booking.model.util;

/**
 * Stores sql query.
 */
public final class SqlQuery {

    /**
     * Private constructor without parameters.
     * Restrict instantiation of the class.
     */
    private SqlQuery() {
    }

    /* Query to the USER table */
    public static final String USER_QUERY_FIND_BY_ID =
            "select USERNAME, LASTNAME, FIRSTNAME, EMAIL, PASSWORD, ACTIVE, ACTIVATIONCODE, BANNED, group_concat (ROLE.NAME) as NAME from USER " +
                    "left join USER_ROLE on USERNAME = USER left join ROLE on ROLE = NAME where USERNAME = ? group by USERNAME";
    public static final String USER_QUERY_FIND_ALL =
            "select USERNAME, LASTNAME, FIRSTNAME, EMAIL, PASSWORD, ACTIVE, ACTIVATIONCODE, BANNED, group_concat (ROLE.NAME) as NAME from USER " +
                    "left join USER_ROLE on USERNAME = USER left join ROLE on ROLE = NAME group by USERNAME " +
                    "offset ? * ? ROWS fetch next ? ROWS only";
    public static final String USER_QUERY_SAVE =
            "insert into USER(USERNAME, LASTNAME, FIRSTNAME, EMAIL, PASSWORD, ACTIVE, ACTIVATIONCODE, BANNED) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
    public static final String USER_QUERY_ACTIVATE =
            "update USER set ACTIVE = 'true', ACTIVATIONCODE = 'null' where USERNAME = ?";
    public static final String USER_QUERY_DELETE =
            "delete from USER where USERNAME  = ?";
    public static final String USER_QUERY_FIND_BY_ACTIVATION_CODE =
            "select USERNAME, LASTNAME, FIRSTNAME, EMAIL, PASSWORD, ACTIVE, ACTIVATIONCODE, BANNED, group_concat (ROLE.NAME) as NAME from USER " +
                    "left join USER_ROLE on USERNAME = USER left join ROLE on ROLE = NAME where ACTIVATIONCODE = ?";
    public static final String USER_QUERY_FIND_ROWS_COUNT =
            "select COUNT(*) as count from USER";
    public static final String USER_QUERY_CHANGE_ACCOUNT_LOCK =
            "update USER set BANNED = ? where USERNAME = ?";

    /* Query to the USER_ROLE table */
    public static final String USER_ROLE_QUERY_SAVE =
            "insert into USER_ROLE(USER, ROLE) VALUES(?, ?)";
    public static final String USER_ROLE_QUERY_DELETE =
            "delete from USER_ROLE where USER  = ?";
    public static final String USER_ROLE_QUERY_DELETE_ADMIN =
            "delete from USER_ROLE where USER  = ? and ROLE = 'ADMIN'";

    /* Query to the BOOKING table */
    public static final String BOOKING_QUERY_FIND_BY_ID =
            "select NUMBER, USER, ROOM, PERSONS, ARRIVAL, DEPARTURE, FOOD, DAYS, SERVICES, AMOUNT, STATUS, ROOM.TYPE, " +
                    "ROOM.SLEEPS, ROOM.PRICE, FOOD.PRICE, ADDITIONALSERVICES.PRICE from BOOKING " +
                    "left join ROOM on ROOM = ROOMNUMBER left join FOOD on FOOD = FOOD.TYPE " +
                    "left join ADDITIONALSERVICES on SERVICES = ADDITIONALSERVICES.TYPE where NUMBER = ?";
    public static final String BOOKING_QUERY_FIND_ALL =
            "select NUMBER, USER, ROOM, PERSONS, ARRIVAL, DEPARTURE, FOOD, DAYS, SERVICES, AMOUNT, STATUS, ROOM.TYPE, " +
                    "ROOM.SLEEPS, ROOM.PRICE, FOOD.PRICE, ADDITIONALSERVICES.PRICE from BOOKING " +
                    "left join ROOM on ROOM = ROOMNUMBER left join FOOD on FOOD = FOOD.TYPE " +
                    "left join ADDITIONALSERVICES on SERVICES = ADDITIONALSERVICES.TYPE offset ? * ? ROWS fetch next ? ROWS only";
    public static final String BOOKING_QUERY_SAVE =
            "insert into BOOKING(USER, ROOM, PERSONS, ARRIVAL, DEPARTURE, FOOD, DAYS, SERVICES, AMOUNT, STATUS) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    public static final String BOOKING_QUERY_DELETE =
            "delete from BOOKING where NUMBER  = ?";
    public static final String BOOKING_QUERY_FIND_ALL_BOOKINGS_BETWEEN_TWO_DATES =
            "select NUMBER, USER, ROOM, PERSONS, ARRIVAL, DEPARTURE, FOOD, DAYS, SERVICES, AMOUNT, STATUS, ROOM.TYPE, " +
                    "ROOM.SLEEPS, ROOM.PRICE, FOOD.PRICE, ADDITIONALSERVICES.PRICE from BOOKING " +
                    "left join ROOM on ROOM = ROOMNUMBER left join FOOD on FOOD = FOOD.TYPE " +
                    "left join ADDITIONALSERVICES on SERVICES = ADDITIONALSERVICES.TYPE where ARRIVAL < ? and DEPARTURE > ?";
    public static final String BOOKING_QUERY_FIND_ALL_BOOKINGS_BY_USER =
            "select NUMBER, USER, ROOM, PERSONS, ARRIVAL, DEPARTURE, FOOD, DAYS, SERVICES, AMOUNT, STATUS, ROOM.TYPE, " +
                    "ROOM.SLEEPS, ROOM.PRICE, FOOD.PRICE, ADDITIONALSERVICES.PRICE from BOOKING " +
                    "left join ROOM on ROOM = ROOMNUMBER left join FOOD on FOOD = FOOD.TYPE " +
                    "left join ADDITIONALSERVICES on SERVICES = ADDITIONALSERVICES.TYPE where USER = ?";
    public static final String BOOKING_QUERY_CHANGE_ROOM =
            "update BOOKING set ROOM = ?, AMOUNT = ? where NUMBER = ?";
    public static final String BOOKING_QUERY_CHANGE_BOOKING_STATUS =
            "update BOOKING set STATUS = ? where NUMBER = ?";
    public static final String BOOKING_QUERY_FIND_ROWS_COUNT =
            "select COUNT(*) as count from BOOKING";

    /* Query to the ADDITIONALSERVICES table */
    public static final String ADDITIONALSERVICES_QUERY_FIND_BY_ID =
            "select TYPE, PRICE from ADDITIONALSERVICES where TYPE = ?";
    public static final String ADDITIONALSERVICES_QUERY_FIND_ALL =
            "select TYPE, PRICE from ADDITIONALSERVICES  offset ? * ? ROWS fetch next ? ROWS only";
    public static final String ADDITIONALSERVICES_QUERY_CHANGE_SERVICE_PRICE =
            "update ADDITIONALSERVICES set PRICE = ? where TYPE = ?";

    /* Query to the FOOD table */
    public static final String FOOD_QUERY_FIND_BY_ID =
            "select TYPE, PRICE from FOOD where TYPE = ?";
    public static final String FOOD_QUERY_FIND_ALL =
            "select TYPE, PRICE from FOOD  offset ? * ? ROWS fetch next ? ROWS only";
    public static final String FOOD_QUERY_CHANGE_FOOD_PRICE =
            "update FOOD set PRICE = ? where TYPE = ?";

    /* Query to the ROOM table */
    public static final String ROOM_QUERY_FIND_BY_ID =
            "select ROOMNUMBER, TYPE, SLEEPS, PRICE from ROOM where ROOMNUMBER = ?";
    public static final String ROOM_QUERY_FIND_ALL =
            "select ROOMNUMBER, TYPE, SLEEPS, PRICE from ROOM  offset ? * ? ROWS fetch next ? ROWS only";
    public static final String ROOM_QUERY_SAVE =
            "insert into ROOM(ROOMNUMBER, TYPE, SLEEPS, PRICE) VALUES(?, ?, ?, ?)";
    public static final String ROOM_QUERY_DELETE =
            "delete from ROOM where ROOMNUMBER  = ?";
    public static final String ROOM_QUERY_UPDATE_PRICE =
            "update ROOM set PRICE = ? where TYPE = ? and SLEEPS = ?";
    public static final String ROOM_QUERY_UPDATE_PARAMETERS =
            "update ROOM set TYPE = (select TYPE from ROOM where ROOMNUMBER = ?), SLEEPS = (select SLEEPS from ROOM where ROOMNUMBER = ?), " +
                    "PRICE = (select PRICE from ROOM where ROOMNUMBER = ?) where ROOM.ROOMNUMBER = ?";
    public static final String ROOM_QUERY_FIND_ALL_TYPES_ROOMS =
            "select min(ROOMNUMBER) as ROOMNUMBER, min(SLEEPS) as SLEEPS, min(PRICE) as PRICE, TYPE from ROOM group by TYPE order by PRICE";
    public static final String ROOM_QUERY_FIND_ALL_ROOMS_BY_TYPES_AND_SLEEPS =
            "select min(ROOMNUMBER) as ROOMNUMBER, TYPE, SLEEPS, PRICE from ROOM GROUP BY TYPE, SLEEPS order by PRICE";
    public static final String ROOM_QUERY_FIND_ROOM_BY_TYPE_AND_SLEEPING =
            "select min(ROOMNUMBER) as ROOMNUMBER, TYPE, SLEEPS, PRICE from ROOM where TYPE = ? and SLEEPS = ? GROUP BY TYPE, SLEEPS";
    public static final String ROOM_QUERY_FIND_ALL_FREE_ROOMS_BETWEEN_TWO_DATES =
            "select ROOMNUMBER, TYPE, SLEEPS, PRICE from ROOM where ROOMNUMBER not in " +
                    "(select ROOM from BOOKING where ARRIVAL < ? and DEPARTURE > ?)";
    public static final String ROOM_QUERY_FIND_ALL_TYPES_FREE_ROOMS_BETWEEN_TWO_DATES =
            "select min(ROOMNUMBER) as ROOMNUMBER, min(SLEEPS) as SLEEPS, min(PRICE) as PRICE, TYPE from ROOM where ROOMNUMBER not in " +
                    "(select ROOM from BOOKING where ARRIVAL < ? and DEPARTURE > ?) group by TYPE order by PRICE";
    public static final String ROOM_QUERY_FIND_ALL_FREE_ROOMS_BETWEEN_TWO_DATES_BY_TYPES_AND_SLEEPS =
            "select ROOMNUMBER, TYPE, SLEEPS, PRICE from ROOM where TYPE = ? and SLEEPS = ? and ROOMNUMBER not in " +
                    "(select ROOM from BOOKING where ARRIVAL < ? and DEPARTURE > ?)";
    public static final String ROOM_QUERY_FIND_ALL_TYPES_FREE_ROOMS_BETWEEN_TWO_DATES_WITH_GREATER_OR_EQUAL_SLEEPS =
            "select min(ROOMNUMBER) as ROOMNUMBER, SLEEPS, PRICE, TYPE from ROOM where SLEEPS >= ? and ROOMNUMBER not in " +
                    "(select ROOM from BOOKING where ARRIVAL < ? and DEPARTURE > ?) group by TYPE, SLEEPS order by PRICE";
    public static final String ROOM_QUERY_FIND_ALL_FREE_ROOMS_BETWEEN_TWO_DATES_WITH_GREATER_OR_EQUAL_SLEEPS =
            "select ROOMNUMBER, TYPE, SLEEPS, PRICE from ROOM where SLEEPS >= ? and ROOMNUMBER not in " +
                    "(select ROOM from BOOKING where ARRIVAL < ? and DEPARTURE > ?) order by ROOMNUMBER";
}
