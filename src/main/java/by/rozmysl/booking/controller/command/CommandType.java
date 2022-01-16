package by.rozmysl.booking.controller.command;

import by.rozmysl.booking.controller.command.impl.admin.*;
import by.rozmysl.booking.controller.command.impl.login.*;
import by.rozmysl.booking.controller.command.impl.user.GetUserBookingsCommand;
import by.rozmysl.booking.controller.command.impl.user.ToBookingPageCommand;
import by.rozmysl.booking.controller.command.impl.user.GetBookingDetailsCommand;
import by.rozmysl.booking.controller.command.impl.user.ToUserPageCommand;

public enum CommandType {
    LOGIN(new LoginCommand()),
    ACTIVATION(new ActivationAccountCommand()),
    LOGOUT(new LogoutCommand()),
    REGISTRATION(new RegistrationCommand()),
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
    private static final String REGEX_PATTERN = "([a-z])([A-Z]+)";
    private static final String REPLACEMENT = "$1_$2";
    private static final String ACTIVATION_PATH = "/activation";

    CommandType(Command command) {
        this.command = command;
    }

    public static String convert(String str) {
        return str.startsWith(ACTIVATION_PATH) ? String.valueOf(CommandType.ACTIVATION)
                : str.replace("/", "").replaceAll(REGEX_PATTERN, REPLACEMENT).toUpperCase();
    }

    public Command getAction() {
        return command;
    }

    public void setAction(Command command) {
        this.command = command;
    }
}