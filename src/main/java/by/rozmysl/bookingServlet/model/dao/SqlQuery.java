package by.rozmysl.bookingServlet.model.dao;

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
    public static final String USER_FIND_BY_ID =
            "select USERNAME, LASTNAME, FIRSTNAME, EMAIL, PASSWORD, ACTIVE, ACTIVATIONCODE, BANNED, GROUP_CONCAT (ROLE.NAME) as NAME from USER " +
                    "left join USER_ROLE on USERNAME = USER left join ROLE on ROLE = NAME where USERNAME = ? GROUP BY USERNAME";
    public static final String USER_FIND_ALL =
            "select USERNAME, LASTNAME, FIRSTNAME, EMAIL, PASSWORD, ACTIVE, ACTIVATIONCODE, BANNED, GROUP_CONCAT (ROLE.NAME) as NAME from USER " +
                    "left join USER_ROLE on USERNAME = USER left join ROLE on ROLE = NAME GROUP BY USERNAME " +
                    "offset ? * ? ROWS fetch next ? ROWS only";
    public static final String USER_SAVE =
            "insert into USER(USERNAME, LASTNAME, FIRSTNAME, EMAIL, PASSWORD, ACTIVE, ACTIVATIONCODE, BANNED) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
    public static final String USER_ACTIVATE =
            "update USER set ACTIVE = 'true', ACTIVATIONCODE = 'null' where USERNAME = ?";
    public static final String USER_DELETE =
            "delete from USER where USERNAME  = ?";
    public static final String USER_FIND_BY_ACTIVATION_CODE =
            "select USERNAME, LASTNAME, FIRSTNAME, EMAIL, PASSWORD, ACTIVE, ACTIVATIONCODE, BANNED from USER " +
                    "left join USER_ROLE on USERNAME = USER left join ROLE on ROLE = NAME where ACTIVATIONCODE = ?";
    public static final String USER_FIND_ROWS_COUNT =
            "select COUNT(*) as count from USER";
    public static final String USER_CHANGE_ACCOUNT_LOCK =
            "update USER set BANNED = ? where USERNAME = ?";

    /* Query to the USER_ROLE table */
//    public static final String USER_ROLE_FIND_BY_ID =
//            "select USER, ROLE from USER_ROLE left join ROLE on ROLE = ROLE.NAME where USER = ?";
//    public static final String USER_ROLE_FIND_ALL =
//            "select USER, ROLE from USER_ROLE left join ROLE on ROLE = ROLE.NAME  offset ? * ? ROWS fetch next ? ROWS only";
    public static final String USER_ROLE_SAVE =
            "insert into USER_ROLE(USER, ROLE) VALUES(?, ?)";
    public static final String USER_ROLE_DELETE =
            "delete from USER_ROLE where USER  = ?";
    public static final String USER_ROLE_DELETE_ADMIN =
            "delete from USER_ROLE where USER  = ? and ROLE = 'ADMIN'";

    /* Query to the BOOKING table */
    public static final String BOOKING_FIND_BY_ID =
            "select NUMBER, USER, ROOM, PERSONS, ARRIVAL, DEPARTURE, FOOD, DAYS, SERVICES, AMOUNT, STATUS, ROOM.TYPE, ROOM.SLEEPS from BOOKING" +
                    " left join ROOM on ROOM = ROOMNUMBER where NUMBER = ?";
    public static final String BOOKING_FIND_ALL =
            "select NUMBER, USER, ROOM, PERSONS, ARRIVAL, DEPARTURE, FOOD, DAYS, SERVICES, AMOUNT, STATUS, ROOM.TYPE, ROOM.SLEEPS from BOOKING" +
                    " left join ROOM on ROOM = ROOMNUMBER offset ? * ? ROWS fetch next ? ROWS only";
    public static final String BOOKING_SAVE =
            "insert into BOOKING(USER, ROOM, PERSONS, ARRIVAL, DEPARTURE, FOOD, DAYS, SERVICES, AMOUNT, STATUS) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    public static final String BOOKING_DELETE =
            "delete from BOOKING where NUMBER  = ?";
    public static final String BOOKING_FIND_ALL_BOOKINGS_BETWEEN_TWO_DATES =
            "select NUMBER, USER, ROOM, PERSONS, ARRIVAL, DEPARTURE, FOOD, DAYS, SERVICES, AMOUNT, STATUS, ROOM.TYPE, ROOM.SLEEPS from BOOKING" +
                    " left join ROOM on ROOM = ROOMNUMBER ARRIVAL < ? and DEPARTURE > ?";
    public static final String BOOKING_FIND_ALL_BOOKINGS_BY_USER =
            "select NUMBER, USER, ROOM, PERSONS, ARRIVAL, DEPARTURE, FOOD, DAYS, SERVICES, AMOUNT, STATUS, ROOM.TYPE, ROOM.SLEEPS from BOOKING" +
                    " left join ROOM on ROOM = ROOMNUMBER where USER = ?";
    public static final String BOOKING_CHANGE_ROOM =
            "update BOOKING set ROOM = ?, AMOUNT = ? where NUMBER = ?";
    public static final String BOOKING_CHANGE_BOOKING_STATUS =
            "update BOOKING set STATUS = ? where NUMBER = ?";
    public static final String BOOKING_FIND_ROWS_COUNT =
            "select COUNT(*) as count from BOOKING";

    /* Query to the ADDITIONALSERVICES table */
    public static final String ADDITIONALSERVICES_FIND_BY_ID =
            "select TYPE, PRICE from ADDITIONALSERVICES where TYPE = ?";
    public static final String ADDITIONALSERVICES_FIND_ALL =
            "select TYPE, PRICE from ADDITIONALSERVICES  offset ? * ? ROWS fetch next ? ROWS only";
    public static final String ADDITIONALSERVICES_CHANGE_SERVICE_PRICE =
            "update ADDITIONALSERVICES set PRICE = ? where TYPE = ?";

    /* Query to the FOOD table */
    public static final String FOOD_FIND_BY_ID =
            "select TYPE, PRICE from FOOD where TYPE = ?";
    public static final String FOOD_FIND_ALL =
            "select TYPE, PRICE from FOOD  offset ? * ? ROWS fetch next ? ROWS only";
    public static final String FOOD_CHANGE_FOOD_PRICE =
            "update FOOD set PRICE = ? where TYPE = ?";

    /* Query to the ROOM table */
    public static final String ROOM_FIND_BY_ID =
            "select ROOMNUMBER, TYPE, SLEEPS, PRICE from ROOM where ROOMNUMBER = ?";
    public static final String ROOM_FIND_ALL =
            "select ROOMNUMBER, TYPE, SLEEPS, PRICE from ROOM  offset ? * ? ROWS fetch next ? ROWS only";
    public static final String ROOM_SAVE =
            "insert into ROOM(ROOMNUMBER, TYPE, SLEEPS, PRICE) VALUES(?, ?, ?, ?)";
    public static final String ROOM_DELETE =
            "delete from ROOM where ROOMNUMBER  = ?";
    public static final String ROOM_UPDATE_PRICE =
            "update ROOM set PRICE = ? where TYPE = ? and SLEEPS = ?";
    public static final String ROOM_UPDATE_PARAMETERS =
            "update ROOM set TYPE = ?, SLEEPS =?, PRICE = ? where ROOMNUMBER = ?";
    public static final String ROOM_FIND_ALL_TYPES_ROOMS =
            "select min(ROOMNUMBER) as ROOMNUMBER, min(SLEEPS) as SLEEPS, min(PRICE) as PRICE, TYPE from ROOM GROUP BY TYPE order by PRICE";
    public static final String ROOM_FIND_ALL_ROOMS_BY_TYPES_AND_SLEEPS =
            "select min(ROOMNUMBER) as ROOMNUMBER, TYPE, SLEEPS, PRICE from ROOM GROUP BY TYPE, SLEEPS order by PRICE";
    public static final String ROOM_FIND_ROOM_BY_TYPE_AND_SLEEPING =
            "select min(ROOMNUMBER) as ROOMNUMBER, TYPE, SLEEPS, PRICE from ROOM where TYPE = ? and SLEEPS = ? GROUP BY TYPE, SLEEPS";
    public static final String ROOM_FIND_ALL_FREE_ROOMS_BETWEEN_TWO_DATES =
            "select ROOMNUMBER, TYPE, SLEEPS, PRICE from ROOM where ROOMNUMBER not in " +
                    "(select ROOM from BOOKING where ARRIVAL < ? and DEPARTURE > ?)";
    public static final String ROOM_FIND_ALL_TYPES_FREE_ROOMS_BETWEEN_TWO_DATES =
            "select min(ROOMNUMBER) as ROOMNUMBER, min(SLEEPS) as SLEEPS, min(PRICE) as PRICE, TYPE from ROOM where ROOMNUMBER not in " +
                    "(select ROOM from BOOKING where ARRIVAL < ? and DEPARTURE > ?) GROUP BY TYPE order by PRICE";
    public static final String ROOM_FIND_ALL_FREE_ROOMS_BETWEEN_TWO_DATES_BY_TYPES_AND_SLEEPS =
            "select ROOMNUMBER, TYPE, SLEEPS, PRICE from ROOM where TYPE = ? and SLEEPS = ? and ROOMNUMBER not in " +
                    "(select ROOM from BOOKING where ARRIVAL < ? and DEPARTURE > ?)";
    public static final String ROOM_FIND_ALL_TYPES_FREE_ROOMS_BETWEEN_TWO_DATES_WITH_GREATER_OR_EQUAL_SLEEPS =
            "select min(ROOMNUMBER) as ROOMNUMBER, SLEEPS, PRICE, TYPE from ROOM where SLEEPS >= ? and ROOMNUMBER not in " +
                    "(select ROOM from BOOKING where ARRIVAL < ? and DEPARTURE > ?) GROUP BY TYPE, SLEEPS order by PRICE";
    public static final String ROOM_FIND_ALL_FREE_ROOMS_BETWEEN_TWO_DATES_WITH_GREATER_OR_EQUAL_SLEEPS =
            "select ROOMNUMBER, TYPE, SLEEPS, PRICE from ROOM where SLEEPS >= ? and ROOMNUMBER not in " +
                    "(select ROOM from BOOKING where ARRIVAL < ? and DEPARTURE > ?) order by ROOMNUMBER";
}
