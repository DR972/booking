package by.rozmysl.bookingServlet.action;

import by.rozmysl.bookingServlet.action.admin.*;
import by.rozmysl.bookingServlet.action.login.*;
import by.rozmysl.bookingServlet.action.user.BookingPage;
import by.rozmysl.bookingServlet.action.user.GetBookingDetails;
import by.rozmysl.bookingServlet.action.user.GetUserBookings;
import by.rozmysl.bookingServlet.action.user.UserPage;

public enum ActionType {
    LOGIN(new Login()),
    ACTIVATION(new ActivationAccount()),
    LOGOUT(new Logout()),
    REGISTRATION(new Registration()),
    USER(new UserPage()),
    PRICE(new Price()),
    BOOKING_DETAILS(new GetBookingDetails()),
    BOOKING(new BookingPage()),
    USER_BOOKINGS(new GetUserBookings()),
    ADMIN(new AdminPage()),
    ALL_USERS(new GetAllUsers()),
    ALL_BOOKINGS(new GetAllBookings()),
    ADD_ROOM(new CreateNewRoom()),
    ALL_ROOMS(new GetAllRooms()),
    FREE_ROOMS(new GetFreeRooms()),
    CHANGE_ROOM_PARAMETERS(new ChangeRoomParameters()),
    CHANGE_SERVICES_PRICE(new ChangeServicesPrice());

    private Action action;

    ActionType(Action action) {
        this.action = action;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }
}
