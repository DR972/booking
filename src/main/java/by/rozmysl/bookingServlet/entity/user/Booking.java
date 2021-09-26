package by.rozmysl.bookingServlet.entity.user;

import by.rozmysl.bookingServlet.entity.hotel.AdditionalServices;
import by.rozmysl.bookingServlet.entity.hotel.Food;
import by.rozmysl.bookingServlet.entity.hotel.Room;
import by.rozmysl.bookingServlet.entity.hotel.StatusReservation;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.StringJoiner;

/**
 * It is used to store Booking objects with the <b>number</b>, <b>user</b>, <b>room</b>, <b>persons</b>,
 * <b>arrival</b>, <b>departure</b>, <b>days</b>, <b>food</b>, <b>services</b>, <b>amount</b>, <b>status</b> properties.
 */
public class Booking {
    private long number;
    private User user;
    private Room room;
    private int persons;
    private LocalDate arrival;
    private LocalDate departure;
    private int days;
    private Food food;
    private AdditionalServices services;
    private BigDecimal amount;
    private String status = StatusReservation.ORDER.getStatus();

    /**
     * The constructor creates a new object Booking without parameters
     */
    public Booking() {
    }

    /**
     * The constructor creates a new object Booking with the <b>user</b>, <b>persons</b>, <b>arrival</b>, <b>departure</b>,
     * <b>days</b>, <b>food</b>, <b>services</b> properties
     *
     * @param user      User
     * @param persons   persons
     * @param arrival   arrival
     * @param departure departure
     * @param days      days
     * @param food      Food
     * @param services  AdditionalServices
     */
    public Booking(User user, int persons, LocalDate arrival, LocalDate departure, int days, Food food, AdditionalServices services) {
        this.user = user;
        this.persons = persons;
        this.arrival = arrival;
        this.departure = departure;
        this.days = days;
        this.food = food;
        this.services = services;
    }

    /**
     * The constructor creates a new object Booking with the <b>persons</b>, <b>arrival</b>, <b>departure</b>, <b>days</b>,
     * <b>food</b>, <b>services</b> properties
     *
     * @param persons   persons
     * @param arrival   arrival
     * @param departure departure
     * @param days      days
     * @param food      Food
     * @param services  AdditionalServices
     */
    public Booking(int persons, LocalDate arrival, LocalDate departure, int days, Food food, AdditionalServices services) {
        this.persons = persons;
        this.arrival = arrival;
        this.departure = departure;
        this.days = days;
        this.food = food;
        this.services = services;
    }

    /**
     * The constructor creates a new object Booking with the <b>persons</b>, <b>arrival</b>, <b>days</b>, <b>food</b>,
     * <b>services</b> properties
     *
     * @param persons  persons
     * @param arrival  arrival
     * @param days     days
     * @param food     Food
     * @param services AdditionalServices
     */
    public Booking(int persons, LocalDate arrival, int days, Food food, AdditionalServices services) {
        this.persons = persons;
        this.arrival = arrival;
        this.days = days;
        this.food = food;
        this.services = services;
    }

    /**
     * The constructor creates a new object Booking with the <b>number</b>, <b>user</b>, <b>room</b>, <b>persons</b>,
     * <b>arrival</b>, <b>departure</b>, <b>days</b>, <b>food</b>, <b>services</b>, <b>amount</b>, <b>status</b> properties
     *
     * @param number    id of the Room
     * @param user      User
     * @param room      Room
     * @param persons   persons
     * @param arrival   arrival
     * @param departure departure
     * @param days      days
     * @param food      Food
     * @param services  AdditionalServices
     * @param amount    amount
     * @param status    status
     */
    public Booking(long number, User user, Room room, int persons, LocalDate arrival, LocalDate departure, int days,
                   Food food, AdditionalServices services, BigDecimal amount, String status) {
        this.number = number;
        this.user = user;
        this.room = room;
        this.persons = persons;
        this.arrival = arrival;
        this.departure = departure;
        this.days = days;
        this.food = food;
        this.services = services;
        this.amount = amount;
        this.status = status;
    }

    /**
     * Gets the value of the number property
     *
     * @return a value of the number
     */
    public long getNumber() {
        return number;
    }

    /**
     * The method sets the value of the number property
     *
     * @param number number
     */
    public void setNumber(long number) {
        this.number = number;
    }

    /**
     * Gets the value of the user property
     *
     * @return a value of the user
     */
    public User getUser() {
        return user;
    }

    /**
     * The method sets the value of the user property
     *
     * @param user user
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Gets the value of the room property
     *
     * @return a value of the room
     */
    public Room getRoom() {
        return room;
    }

    /**
     * The method sets the value of the room property
     *
     * @param room room
     */
    public void setRoom(Room room) {
        this.room = room;
    }

    /**
     * Gets the value of the persons property
     *
     * @return a value of the persons
     */
    public int getPersons() {
        return persons;
    }

    /**
     * The method sets the value of the persons property
     *
     * @param persons persons
     */
    public void setPersons(int persons) {
        this.persons = persons;
    }

    /**
     * Gets the value of the arrival property
     *
     * @return a value of the arrival
     */
    public LocalDate getArrival() {
        return arrival;
    }

    /**
     * The method sets the value of the arrival property
     *
     * @param arrival arrival
     */
    public void setArrival(LocalDate arrival) {
        this.arrival = arrival;
    }

    /**
     * Gets the value of the departure property
     *
     * @return a value of the departure
     */
    public LocalDate getDeparture() {
        return departure;
    }

    /**
     * The method sets the value of the departure property
     *
     * @param departure departure
     */
    public void setDeparture(LocalDate departure) {
        this.departure = departure;
    }

    /**
     * Gets the value of the days property
     *
     * @return a value of the days
     */
    public int getDays() {
        return days;
    }

    /**
     * The method sets the value of the days property
     *
     * @param days days
     */
    public void setDays(int days) {
        this.days = days;
    }

    /**
     * Gets the value of the food property
     *
     * @return a value of the food
     */
    public Food getFood() {
        return food;
    }

    /**
     * The method sets the value of the food property
     *
     * @param food food
     */
    public void setFood(Food food) {
        this.food = food;
    }

    /**
     * Gets the value of the services property
     *
     * @return a value of the services
     */
    public AdditionalServices getServices() {
        return services;
    }

    /**
     * The method sets the value of the services property
     *
     * @param services services
     */
    public void setServices(AdditionalServices services) {
        this.services = services;
    }

    /**
     * Gets the value of the amount property
     *
     * @return a value of the amount
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * The method sets the value of the amount property
     *
     * @param amount amount
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    /**
     * Gets the value of the status property
     *
     * @return a value of the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * The method sets the value of the status property
     *
     * @param status status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Booking.class.getSimpleName() + "[", "]")
                .add("number=" + number)
                .add("user=" + user)
                .add("room=" + room)
                .add("persons=" + persons)
                .add("arrival=" + arrival)
                .add("departure=" + departure)
                .add("days=" + days)
                .add("food=" + food)
                .add("services=" + services)
                .add("amount=" + amount)
                .add("status='" + status + "'")
                .toString();
    }
}