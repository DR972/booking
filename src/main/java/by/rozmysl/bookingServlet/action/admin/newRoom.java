package by.rozmysl.bookingServlet.action.admin;

import by.rozmysl.bookingServlet.action.Action;
import by.rozmysl.bookingServlet.dao.DaoFactory;
import by.rozmysl.bookingServlet.entity.hotel.Room;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;

/**
 * Provides service to initialize actions on the newRoom.
 */
public class newRoom implements Action {
    private static final Logger logger = LoggerFactory.getLogger(newRoom.class);

    /**
     * Executes actions on request content.
     *
     * @param req request content
     * @return page to go
     * @throws SQLException if there was an error accessing the database
     */
    @Override
    public String execute(HttpServletRequest req) throws SQLException {
        DaoFactory dao = new DaoFactory();
        if (dao.roomDao().getById(Integer.parseInt(req.getParameter("roomNumber"))) != null) {
            req.setAttribute("errorRoomNumber", "Изменить параметры существующих номеров можно на странице" +
                    " \"Изменить параметры номеров\"");
            return String.format("forward:%s", "/WEB-INF/views/admin/addRoom.jsp");
        }
        Room room = dao.roomDao().findRoomByTypeAndSleeping(req.getParameter("type"), Integer.parseInt(req.getParameter("sleeps")));
        if (room == null && req.getParameter("price") == null) {
            req.setAttribute("roomNumber", req.getParameter("roomNumber"));
            req.setAttribute("type", req.getParameter("type"));
            req.setAttribute("sleeps", Integer.parseInt(req.getParameter("sleeps")));
            return String.format("forward:%s", "/WEB-INF/views/admin/addRoom.jsp");
        }
        if (room != null) {
            room = dao.roomDao().save(new Room(Integer.parseInt(req.getParameter("roomNumber")), room.getType(),
                    room.getSleeps(), room.getPrice()));
        }
        if (req.getParameter("price") != null) {
            room = dao.roomDao().save(new Room(Integer.parseInt(req.getParameter("roomNumber")), req.getParameter("type"),
                    Integer.parseInt(req.getParameter("sleeps")), Double.parseDouble(req.getParameter("price"))));
        }
        logger.info("Admin '" + req.getUserPrincipal().getName() + "' created a new room - " + room);
        return String.format("forward:%s", "/WEB-INF/views/admin/admin.jsp");
    }
}