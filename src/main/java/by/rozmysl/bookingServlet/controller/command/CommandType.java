package by.rozmysl.bookingServlet.controller.command;

import by.rozmysl.bookingServlet.controller.command.impl.admin.*;
import by.rozmysl.bookingServlet.controller.command.impl.login.*;
import by.rozmysl.bookingServlet.controller.command.impl.user.GetUserBookingsCommand;
import by.rozmysl.bookingServlet.controller.command.impl.user.ToBookingPageCommand;
import by.rozmysl.bookingServlet.controller.command.impl.user.GetBookingDetailsCommand;
import by.rozmysl.bookingServlet.controller.command.impl.user.ToUserPageCommand;

public enum CommandType {
    LOGIN(new LoginCommand()),
    ACTIVATION(new ActivationAccountCommand()),
    LOGOUT(new LogoutCommand()),
    REGISTRATION(new Registration()),
    USER(new ToUserPageCommand()),
    PRICE(new ToPriceCommand()),
    BOOKING_DETAILS(new GetBookingDetailsCommand()),
    BOOKING(new ToBookingPageCommand()),
    USER_BOOKINGS(new GetUserBookingsCommand()),
    ADMIN(new ToAdminPageCommand()),
    ALL_USERS(new GetAllUsersCommand()),
    ALL_BOOKINGS(new GetAllBookingsCommand()),
    ADD_ROOM(new CreateNewRoomCommand()),
    ALL_ROOMS(new GetAllRoomsCommand()),
    FREE_ROOMS(new GetFreeRoomsCommand()),
    CHANGE_ROOM_PARAMETERS(new ChangeRoomParametersCommand()),
    CHANGE_SERVICES_PRICE(new ChangeServicesPriceCommand());

    private Command command;

    CommandType(Command command) {
        this.command = command;
    }

    public static CommandType chooseCommandType(String str) {
        return str.startsWith("/activation") ? CommandType.ACTIVATION : CommandType.valueOf(convert(str));
    }

    public static String convert(String str) {
        return str.replace("/", "").replaceAll("([a-z])([A-Z]+)", "$1_$2").toUpperCase();
    }

    public Command getAction() {
        return command;
    }

    public void setAction(Command command) {
        this.command = command;
    }
}