package by.rozmysl.bookingServlet.dao;

public final class TableConstant {

    public static final String USER_QUERY_FIND_BY_ID =
            "select USERNAME, LASTNAME, FIRSTNAME, EMAIL, PASSWORD, ACTIVE, ACTIVATIONCODE, ROLE.NAME from USER " +
                    "left join USER_ROLE on USERNAME = USER left join ROLE on ROLE = NAME where USERNAME = '?username'";
    public static final String USER_QUERY_FIND_ALL =
            "select USERNAME, LASTNAME, FIRSTNAME, EMAIL, PASSWORD, ACTIVE, ACTIVATIONCODE, ROLE.NAME from USER " +
                    "left join USER_ROLE on USERNAME = USER left join ROLE on ROLE = NAME order by UPPER (USERNAME) " +
                    "offset ?rows * ?page ROWS fetch next ?rows ROWS only";
    public static final String USER_QUERY_SAVE =
            "insert into USER(USERNAME, LASTNAME, FIRSTNAME, EMAIL, PASSWORD, ACTIVE, ACTIVATIONCODE) VALUES(?, ?, ?, ?, ?, ?, ?)";
    public static final String USER_QUERY_ACTIVATE =
            "update USER set ACTIVE = 'true', ACTIVATIONCODE = 'null' where USERNAME = '?username'";
    public static final String USER_QUERY_DELETE =
            "delete from USER where USERNAME  = '?username'";
    public static final String USER_QUERY_FIND_BY_ACTIVATION_CODE =
            "select USERNAME, LASTNAME, FIRSTNAME, EMAIL, PASSWORD, ACTIVE, ACTIVATIONCODE, ROLE.NAME from USER " +
                    "left join USER_ROLE on USERNAME = USER left join ROLE on ROLE = NAME where USERNAME = '?code'";
    public static final String USER_QUERY_FIND_ROWS_COUNT =
            "select COUNT(*) as count from USER";

    public static final String USER_ROLE_QUERY_FIND_BY_ROLE =
            "select USER, ROLE from USER_ROLE left join ROLE on ROLE = ROLE.NAME where USER = '?name'";
    public static final String USER_ROLE_QUERY_FIND_BY_USER =
            "select USER, ROLE from USER_ROLE left join ROLE on ROLE = ROLE.NAME where USER = '?username'";
    public static final String USER_ROLE_QUERY_FIND_ALL =
            "select USER, ROLE from USER_ROLE left join ROLE on ROLE = ROLE.NAME";
    public static final String USER_ROLE_QUERY_SAVE =
            "insert into USER_ROLE(USER, ROLE) VALUES(?, ?)";
    public static final String USER_ROLE_QUERY_DELETE =
            "delete from USER_ROLE where USER  = '?username'";

    public static final String BOOKING_QUERY_FIND_BY_ID =
            "select NUMBER, USER, ROOM, PERSONS, ARRIVAL, DEPARTURE, FOOD, DAYS, SERVICES, AMOUNT, STATUS, ROOM.TYPE, ROOM.SLEEPS from BOOKING" +
                    " left join ROOM on ROOM = ROOMNUMBER where NUMBER = ?number";
    public static final String BOOKING_QUERY_FIND_ALL =
            "select NUMBER, USER, ROOM, PERSONS, ARRIVAL, DEPARTURE, FOOD, DAYS, SERVICES, AMOUNT, STATUS, ROOM.TYPE, ROOM.SLEEPS from BOOKING" +
                    " left join ROOM on ROOM = ROOMNUMBER offset ?rows * ?page ROWS fetch next ?rows ROWS only";
    public static final String BOOKING_QUERY_SAVE =
            "insert into BOOKING(USER, ROOM, PERSONS, ARRIVAL, DEPARTURE, FOOD, DAYS, SERVICES, AMOUNT, STATUS) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    public static final String BOOKING_QUERY_DELETE =
            "delete from BOOKING where NUMBER  = ?number";
    public static final String BOOKING_QUERY_FIND_ALL_BOOKINGS_BETWEEN_TWO_DATES =
            "select NUMBER, USER, ROOM, PERSONS, ARRIVAL, DEPARTURE, FOOD, DAYS, SERVICES, AMOUNT, STATUS, ROOM.TYPE, ROOM.SLEEPS from BOOKING" +
                    " left join ROOM on ROOM = ROOMNUMBER ARRIVAL < '?departure' and DEPARTURE > '?arrival'";
    public static final String BOOKING_QUERY_FIND_ALL_BOOKINGS_BY_USER =
            "select NUMBER, USER, ROOM, PERSONS, ARRIVAL, DEPARTURE, FOOD, DAYS, SERVICES, AMOUNT, STATUS, ROOM.TYPE, ROOM.SLEEPS from BOOKING" +
                    " left join ROOM on ROOM = ROOMNUMBER where USER = '?user'";
    public static final String BOOKING_QUERY_CHANGE_ROOM =
            "update BOOKING set ROOM = ?roomNumber, AMOUNT = ?amount where NUMBER = ?number";
    public static final String BOOKING_QUERY_CHANGE_BOOKING_STATUS =
            "update BOOKING set STATUS = ?status where NUMBER = ?number";
    public static final String BOOKING_QUERY_FIND_ROWS_COUNT =
            "select COUNT(*) as count from BOOKING";

    public static final String ADDITIONALSERVICES_QUERY_FIND_BY_ID =
            "select TYPE, PRICE from ADDITIONALSERVICES where TYPE = '?type'";
    public static final String ADDITIONALSERVICES_QUERY_FIND_ALL =
            "select TYPE, PRICE from ADDITIONALSERVICES";
    public static final String ADDITIONALSERVICES_QUERY_CHANGE_SERVICE_PRICE =
            "update ADDITIONALSERVICES set PRICE = ?price where TYPE = '?type'";

    public static final String FOOD_QUERY_FIND_BY_ID =
            "select TYPE, PRICE from FOOD where TYPE = '?type'";
    public static final String FOOD_QUERY_FIND_ALL =
            "select TYPE, PRICE from FOOD";
    public static final String FOOD_QUERY_CHANGE_FOOD_PRICE =
            "update ADDITIONALSERVICES set PRICE = ?price where TYPE = '?type'";

    public static final String ROOM_QUERY_FIND_BY_ID =
            "select ROOMNUMBER, TYPE, SLEEPS, PRICE from ROOM where ROOMNUMBER = ?roomNumber";
    public static final String ROOM_QUERY_FIND_ALL =
            "select ROOMNUMBER, TYPE, SLEEPS, PRICE from ROOM";
    public static final String ROOM_QUERY_SAVE =
            "insert into ROOM(ROOMNUMBER, TYPE, SLEEPS, PRICE) VALUES(?, ?, ?, ?)";
    public static final String ROOM_QUERY_DELETE =
            "delete from ROOM where ROOMNUMBER  = ?roomNumber";
    public static final String ROOM_QUERY_UPDATE_PRICE =
            "update ROOM set PRICE = ?price where TYPE = '?type' + and SLEEPS = ?sleeps";
    public static final String ROOM_QUERY_UPDATE_PARAMETERS =
            "update ROOM set TYPE = '?type', SLEEPS =?sleeps, PRICE = ?price where ROOMNUMBER = ?roomNumber";
    public static final String ROOM_QUERY_FIND_ALL_TYPES_ROOMS =
            "select min(ROOMNUMBER) as ROOMNUMBER, min(SLEEPS) as SLEEPS, min(PRICE) as PRICE, TYPE from ROOM GROUP BY TYPE order by PRICE";
    public static final String ROOM_QUERY_FIND_ALL_ROOMS_BY_TYPES_AND_SLEEPS =
            "select min(ROOMNUMBER) as ROOMNUMBER, TYPE, SLEEPS, PRICE from ROOM GROUP BY TYPE, SLEEPS order by PRICE";
    public static final String ROOM_QUERY_FIND_ROOM_BY_TYPE_AND_SLEEPING =
            "select min(ROOMNUMBER) as ROOMNUMBER, TYPE, SLEEPS, PRICE from ROOM where TYPE = '?type' and SLEEPS = ?sleeps GROUP BY TYPE, SLEEPS";
    public static final String ROOM_QUERY_FIND_ALL_FREE_ROOMS_BETWEEN_TWO_DATES =
            "select ROOMNUMBER, TYPE, SLEEPS, PRICE from ROOM where ROOMNUMBER not in " +
                    "(select ROOM from BOOKING where ARRIVAL < '?second' and DEPARTURE > '?first')";
    public static final String ROOM_QUERY_FIND_ALL_TYPES_FREE_ROOMS_BETWEEN_TWO_DATES =
            "select min(ROOMNUMBER) as ROOMNUMBER, min(SLEEPS) as SLEEPS, min(PRICE) as PRICE, TYPE from ROOM where ROOMNUMBER not in " +
                    "(select ROOM from BOOKING where ARRIVAL < '?second' and DEPARTURE > '?first') GROUP BY TYPE order by PRICE";
    public static final String ROOM_QUERY_FIND_ALL_FREE_ROOMS_BETWEEN_TWO_DATES_BY_TYPES_AND_SLEEPS =
            "select ROOMNUMBER, TYPE, SLEEPS, PRICE from ROOM where TYPE ='?type' and SLEEPS = ?sleeps and ROOMNUMBER not in " +
                    "(select ROOM from BOOKING where ARRIVAL < '?second' and DEPARTURE > '?first')";
    public static final String ROOM_QUERY_FIND_ALL_TYPES_FREE_ROOMS_BETWEEN_TWO_DATES_WITH_GREATER_OR_EQUAL_SLEEPS =
            "select min(ROOMNUMBER) as ROOMNUMBER, SLEEPS, PRICE, TYPE from ROOM where SLEEPS >= ?sleeps and ROOMNUMBER not in " +
                    "(select ROOM from BOOKING where ARRIVAL < '?second' and DEPARTURE > '?first') GROUP BY TYPE, SLEEPS order by PRICE";
    public static final String ROOM_QUERY_FIND_ALL_FREE_ROOMS_BETWEEN_TWO_DATES_WITH_GREATER_OR_EQUAL_SLEEPS =
            "select ROOMNUMBER, TYPE, SLEEPS, PRICE from ROOM where SLEEPS >= ?sleeps and ROOMNUMBER not in " +
                    "(select ROOM from BOOKING where ARRIVAL < '?second' and DEPARTURE > '?first') order by ROOMNUMBER";
}
