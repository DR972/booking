package by.rozmysl.bookingServlet.dao.user;

import by.rozmysl.bookingServlet.dao.DaoFactory;
import by.rozmysl.bookingServlet.dao.TableConstant;
import by.rozmysl.bookingServlet.db.ConnectionSource;
import by.rozmysl.bookingServlet.entity.hotel.AdditionalServices;
import by.rozmysl.bookingServlet.entity.hotel.Food;
import by.rozmysl.bookingServlet.entity.hotel.Room;
import by.rozmysl.bookingServlet.entity.user.Booking;
import by.rozmysl.bookingServlet.entity.user.User;
import by.rozmysl.bookingServlet.mail.Letter;
import by.rozmysl.bookingServlet.mail.MailSender;

import javax.mail.MessagingException;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Provides the base model implementation for `BOOKING` table DAO with the <b>ConnectionSource</b> properties.
 */
public class BookingDaoImp implements BookingDao {
    private final ConnectionSource con;

    /**
     * The constructor creates a new object BookingDaoImp with the <b>con</b> property
     */
    public BookingDaoImp(ConnectionSource con) {
        this.con = con;
    }

    /**
     * Counts the number of pages in the 'BOOKING` table
     *
     * @param rows number of rows per page
     * @return number of pages
     * @throws SQLException if there was an error accessing the database
     */
    @Override
    public int countBookingsPages(int rows) throws SQLException {
//        return (int) Math.ceil((float) con.countRows("select COUNT(*) as count from BOOKING") / rows);
        return (int) Math.ceil((float) con.countRows(TableConstant.BOOKING_QUERY_FIND_ROWS_COUNT) / rows);
    }

    /**
     * Searches for the Booking in the `BOOKING` table by id
     *
     * @param number id of the Room
     * @return Booking object
     * @throws SQLException if there was an error accessing the database
     */
    @Override
    public Booking findById(Long number) throws SQLException {
//        List<Booking> bookings = getResultSet("select BOOKING.*, ROOM.TYPE, ROOM.SLEEPS from BOOKING left join " +
//                "ROOM on ROOM = ROOMNUMBER where NUMBER = " + number);
        List<Booking> bookings = getResultSet(TableConstant.BOOKING_QUERY_FIND_BY_ID.replace("?number", String.valueOf(number)));
        return bookings.size() != 0 ? bookings.get(0) : null;
    }

    /**
     * Searches for all Bookings in the `BOOKING` table
     *
     * @param page number of the page to return
     * @param rows number of rows per page
     * @return list of Booking objects
     * @throws SQLException if there was an error accessing the database
     */
    @Override
    public List<Booking> findAll(int page, int rows) throws SQLException {
//        return getResultSet("select BOOKING.*, ROOM.TYPE, ROOM.SLEEPS from BOOKING left join ROOM on ROOM = ROOMNUMBER " +
//                "OFFSET " + rows + "*" + page + " ROWS FETCH NEXT " + rows + " ROWS ONLY");
        return getResultSet(TableConstant.BOOKING_QUERY_FIND_ALL.replace("?rows", String.valueOf(rows)).replace("?page", String.valueOf(page)));
    }

    /**
     * Saves the Booking in the `BOOKING` table
     *
     * @param booking Booking
     * @return Booking object
     * @throws SQLException if there was an error accessing the database
     */
    @Override
    public Booking save(Booking booking) throws SQLException {
        booking.setAmount(calculateAmount(booking));
//        String sql = "insert into BOOKING(USER, ROOM, PERSONS, ARRIVAL, DEPARTURE, FOOD, DAYS, SERVICES, AMOUNT, STATUS) VALUES('"
//                + booking.getUser().getUsername() + "'," + booking.getRoom().getRoomNumber() + "," + booking.getPersons()
//                + ",'" + Date.valueOf(booking.getArrival()) + "','" + Date.valueOf(booking.getDeparture()) + "','" +
//                booking.getFood().getType() + "'," + booking.getDays() + ",'" + booking.getServices().getType() + "'," +
//                booking.getAmount() + ",'" + booking.getStatus() + "')";
        String sql = TableConstant.BOOKING_QUERY_SAVE.replace("?, ?, ?, ?, ?, ?, ?, ?, ?, ?",
                "'" + booking.getUser().getUsername() + "'," + booking.getRoom().getRoomNumber() + "," + booking.getPersons()
                + ",'" + Date.valueOf(booking.getArrival()) + "','" + Date.valueOf(booking.getDeparture()) + "','" +
                booking.getFood().getType() + "'," + booking.getDays() + ",'" + booking.getServices().getType() + "'," +
                booking.getAmount() + ",'" + booking.getStatus() + "'");
        con.saveWithGeneratedKeys(sql);
        return booking;
    }

    /**
     * Deletes the Booking in the `BOOKING` table
     *
     * @param number id of the Booking
     * @throws SQLException if there was an error accessing the database
     */
    @Override
    public void delete(Long number) throws SQLException {
        new Letter().deleteInvoice("src/main/resources/static/" + number + ".pdf");
//        con.update("delete from BOOKING where NUMBER = " + bookingId);
        con.update(TableConstant.BOOKING_QUERY_DELETE.replace("?number", String.valueOf(number)));
    }

    /**
     * Searches for all Bookings between two dates in the `BOOKING` table
     *
     * @param arrival   arrival date
     * @param departure departure date
     * @return list of Booking objects
     * @throws SQLException if there was an error accessing the database
     */
    @Override
    public List<Booking> findAllBookingsBetweenTwoDates(LocalDate arrival, LocalDate departure) throws SQLException {
//        return getResultSet("select BOOKING.*, ROOM.TYPE, ROOM.SLEEPS from BOOKING left join ROOM on ROOM = ROOMNUMBER " +
//                "where ARRIVAL < '" + departure + "' and DEPARTURE > '" + arrival + "'");
        return getResultSet(TableConstant.BOOKING_QUERY_FIND_ALL_BOOKINGS_BETWEEN_TWO_DATES
                .replace("?departure", String.valueOf(departure)).replace("?arrival", String.valueOf(arrival)));
    }

    /**
     * Search for all Bookings made by the user in the `BOOKING` table
     *
     * @param user username
     * @return list of Booking objects
     * @throws SQLException if there was an error accessing the database
     */
    @Override
    public List<Booking> findAllBookingsByUser(String user) throws SQLException {
//        return getResultSet("select BOOKING.*, ROOM.TYPE, ROOM.SLEEPS from BOOKING left join ROOM on ROOM = ROOMNUMBER " +
//                "where USER = '" + user + "'");
        return getResultSet(TableConstant.BOOKING_QUERY_FIND_ALL_BOOKINGS_BY_USER.replace("?user", String.valueOf (user)));
    }

    /**
     * Changes the Room in the User's Booking in the `BOOKING` table
     *
     * @param number id of the Booking
     * @param roomNumber    id of the Room
     * @param dao       DaoFactory dao
     * @throws SQLException if there was an error accessing the database
     */
    @Override
    public void changeRoom(long number, int roomNumber, DaoFactory dao) throws SQLException {
        Booking booking = getAllBookingInfo(number, dao);
        booking.setRoom(dao.roomDao(con).findById(roomNumber));
//        con.update("update BOOKING set ROOM = " + roomNumber + ", AMOUNT = " + calculateAmount(booking)
//                + " where NUMBER = " + number);
        con.update(TableConstant.BOOKING_QUERY_CHANGE_ROOM.replace("?roomNumber", String.valueOf(roomNumber))
                .replace("?amount", String.valueOf(calculateAmount(booking))).replace("?number", String.valueOf(number)));
    }

    /**
     * Changes the User's Booking status in the `BOOKING` table
     *
     * @param number id of the Booking
     * @param status    booking status
     * @param dao       DaoFactory dao
     * @throws SQLException       if there was an error accessing the database
     * @throws MessagingException if the message cannot be created
     * @throws IOException        if the letter cannot be created
     */
    @Override
    public void changeBookingStatus(long number, String status, DaoFactory dao) throws IOException, MessagingException, SQLException {
        Booking booking = getAllBookingInfo(number, dao);
        String filePath = "src/main/resources/static/" + number + ".pdf";
        if (status.equals("СЧЕТ")) {
            new Letter().createInvoice(booking, filePath);
            new MailSender().sendMailWithAttachment(dao.userDao(con).findById(booking.getUser().getUsername()).getEmail(),
                    "СЧЕТ", "Счет во вложении", filePath);
        }
        if (status.equals("ЗАКРЫТ")) new Letter().deleteInvoice(filePath);
        con.update(TableConstant.BOOKING_QUERY_CHANGE_BOOKING_STATUS.replace("?status", status).replace("?number", String.valueOf(number)));
//        con.update("update BOOKING set STATUS = '" + status + "' where NUMBER = " + number);
    }

    /**
     * Get a list of query results from the 'BOOKING' table
     *
     * @param sql the wording of the request to the database
     * @return list of Booking objects
     * @throws SQLException if there was an error accessing the database
     */
    private List<Booking> getResultSet(String sql) throws SQLException {
        return con.get(sql).stream().map(d -> new Booking(
                Long.parseLong(d.get("NUMBER")),
                new User(d.get("USER")),
                new Room(Integer.parseInt(d.get("ROOM")), d.get("TYPE"), Integer.parseInt(d.get("SLEEPS"))),
                Integer.parseInt(d.get("PERSONS")),
                LocalDate.parse(d.get("ARRIVAL")),
                LocalDate.parse(d.get("DEPARTURE")),
                Integer.parseInt(d.get("DAYS")),
                new Food(d.get("FOOD")),
                new AdditionalServices(d.get("SERVICES")),
                new BigDecimal(d.get("AMOUNT")),
                d.get("STATUS"))).distinct().collect(Collectors.toList());
    }

    /**
     * Calculates the booking amount
     *
     * @param booking booking
     * @return booking amount
     */
    private BigDecimal calculateAmount(Booking booking) {
        BigDecimal service = BigDecimal.valueOf(0);
        if (booking.getServices().getType().equals("трансфер")) service = booking.getServices().getPrice();
        if (booking.getServices().getType().equals("стоянка"))
            service = booking.getServices().getPrice().multiply(new BigDecimal(booking.getDays()));
        return (booking.getFood().getPrice().multiply(new BigDecimal(booking.getPersons()))
                .add(booking.getRoom().getPrice()))
                .multiply(new BigDecimal(booking.getDays()))
                .add(service);
    }

    /**
     * Calculates the booking amount
     *
     * @param bookingId id of the Booking
     * @param dao       DaoFactory dao
     * @return Booking Booking
     * @throws SQLException if there was an error accessing the database
     */
    private Booking getAllBookingInfo(long bookingId, DaoFactory dao) throws SQLException {
        Booking booking = findById(bookingId);
        booking.setRoom(dao.roomDao(con).findById(booking.getRoom().getRoomNumber()));
        booking.setFood(dao.foodDao(con).findById(booking.getFood().getType()));
        booking.setServices(dao.servicesDao(con).findById(booking.getServices().getType()));
        return booking;
    }
}
