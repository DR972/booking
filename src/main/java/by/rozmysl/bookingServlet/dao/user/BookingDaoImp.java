package by.rozmysl.bookingServlet.dao.user;

import by.rozmysl.bookingServlet.dao.DaoFactory;
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
import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Provides the base model implementation for `BOOKING` table DAO with the <b>ConnectionSource</b> properties.
 */
public class BookingDaoImp implements BookingDao {
    private final DaoFactory dao = new DaoFactory();
    private final ConnectionSource con = new ConnectionSource();

    /**
     * Counts the number of pages in the 'BOOKING` table
     *
     * @param rows number of rows per page
     * @return number of pages
     * @throws SQLException if there was an error accessing the database
     */
    @Override
    public int countBookingsPages(int rows) throws SQLException {
        return (int) Math.ceil((float) con.countRows("select COUNT(*) as count from BOOKING") / rows);
    }

    /**
     * Searches for the Booking in the `BOOKING` table by id
     *
     * @param number id of the Room
     * @return Booking object
     * @throws SQLException if there was an error accessing the database
     */
    @Override
    public Booking getById(Long number) throws SQLException {
        List<Booking> bookings = getResultSet("select BOOKING.*, ROOM.TYPE, ROOM.SLEEPS from BOOKING left join " +
                "ROOM on ROOM = ROOMNUMBER where NUMBER = " + number);
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
    public List<Booking> getAll(int page, int rows) throws SQLException {
        return getResultSet("select BOOKING.*, ROOM.TYPE, ROOM.SLEEPS from BOOKING left join ROOM on ROOM = ROOMNUMBER " +
                "OFFSET " + rows + "*" + page + " ROWS FETCH NEXT " + rows + " ROWS ONLY");
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
        String sql = "insert into BOOKING(USER, ROOM, PERSONS, ARRIVAL, DEPARTURE, FOOD, DAYS, SERVICES, AMOUNT, STATUS) VALUES('"
                + booking.getUser().getUsername() + "'," + booking.getRoom().getRoomNumber() + "," + booking.getPersons()
                + ",'" + Date.valueOf(booking.getArrival()) + "','" + Date.valueOf(booking.getDeparture()) + "','" +
                booking.getFood().getType() + "'," + booking.getDays() + ",'" + booking.getServices().getType() + "'," +
                booking.getAmount() + ",'" + booking.getStatus() + "')";
        con.saveWithGeneratedKeys(sql);
        return booking;
    }

    /**
     * Deletes the Booking in the `BOOKING` table
     *
     * @param bookingId id of the Booking
     * @throws SQLException if there was an error accessing the database
     */
    @Override
    public void delete(Long bookingId) throws SQLException {
        new Letter().deleteInvoice("src/main/resources/static/" + bookingId + ".pdf");
        con.update("delete from BOOKING where NUMBER = " + bookingId);
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
        return getResultSet("select BOOKING.*, ROOM.TYPE, ROOM.SLEEPS from BOOKING left join ROOM on ROOM = ROOMNUMBER " +
                "where ARRIVAL < '" + departure + "' and DEPARTURE > '" + arrival + "'");
    }

    /**
     * Search for all Bookings made by the user in the `BOOKING` table
     *
     * @param user username
     * @return list of Booking objects
     * @throws SQLException if there was an error accessing the database
     */
    @Override
    public List<Booking> findAllByUser(String user) throws SQLException {
        return getResultSet("select BOOKING.*, ROOM.TYPE, ROOM.SLEEPS from BOOKING left join ROOM on ROOM = ROOMNUMBER " +
                "where USER = '" + user + "'");
    }

    /**
     * Changes the Room in the User's Booking in the `BOOKING` table
     *
     * @param bookingId id of the Booking
     * @param roomId    id of the Room
     * @throws SQLException if there was an error accessing the database
     */
    @Override
    public void changeRoom(long bookingId, int roomId) throws SQLException {
        Booking booking = getById(bookingId);
        booking.setRoom(dao.roomDao().getById(roomId));
        con.update("update BOOKING set ROOM = " + roomId + ", AMOUNT = " + calculateAmount(booking) + " where NUMBER = " + bookingId);
    }

    /**
     * Changes the User's Booking status in the `BOOKING` table
     *
     * @param bookingId id of the Booking
     * @param status    booking status
     * @throws SQLException       if there was an error accessing the database
     * @throws MessagingException if the message cannot be created
     * @throws IOException        if the letter cannot be created
     */
    @Override
    public void changeStatusBooking(long bookingId, String status) throws IOException, MessagingException, SQLException {
        Booking booking = getById(bookingId);
        booking.setRoom(dao.roomDao().getById(booking.getRoom().getRoomNumber()));
        booking.setFood(dao.foodDao().getById(booking.getFood().getType()));
        booking.setServices(dao.servicesDao().getById(booking.getServices().getType()));
        String filePath = "src/main/resources/static/" + bookingId + ".pdf";
        if (status.equals("СЧЕТ")) {
            new Letter().createInvoice(booking, filePath);
            new MailSender().sendMailWithAttachment(dao.userDao().getById(booking.getUser().getUsername()).getEmail(),
                    "СЧЕТ", "Счет во вложении", filePath);
        }
        if (status.equals("ЗАКРЫТ")) new Letter().deleteInvoice(filePath);
        con.update("update BOOKING set STATUS = '" + status + "' where NUMBER = " + bookingId);
    }

    /**
     * Get a list of query results from the 'BOOKING' table
     *
     * @param sql the wording of the request to the database
     * @return list of Booking objects
     * @throws SQLException if there was an error accessing the database
     */
    private List<Booking> getResultSet(String sql) throws SQLException {
        return con.get(sql).stream().map(d -> new Booking(Long.parseLong(d.get("NUMBER")), new User(d.get("USER")),
                new Room(Integer.parseInt(d.get("ROOM")), d.get("TYPE"), Integer.parseInt(d.get("SLEEPS"))),
                Integer.parseInt(d.get("PERSONS")), LocalDate.parse(d.get("ARRIVAL")), LocalDate.parse(d.get("DEPARTURE")),
                Integer.parseInt(d.get("DAYS")), new Food(d.get("FOOD")), new AdditionalServices(d.get("SERVICES")),
                Double.parseDouble(d.get("AMOUNT")), d.get("STATUS"))).distinct().collect(Collectors.toList());
    }

    /**
     * Calculates the booking amount
     *
     * @param booking booking
     * @return booking amount
     */
    private double calculateAmount(Booking booking) {
        double service = 0;
        if (booking.getServices().getType().equals("трансфер")) service = booking.getServices().getPrice();
        if (booking.getServices().getType().equals("стоянка"))
            service = booking.getServices().getPrice() * booking.getDays();
        return (booking.getRoom().getPrice() + booking.getFood().getPrice() * booking.getPersons()) * booking.getDays() + service;
    }
}