package by.rozmysl.bookingServlet.action.admin;

import by.rozmysl.bookingServlet.action.Action;
import by.rozmysl.bookingServlet.dao.DaoFactory;
import by.rozmysl.bookingServlet.dao.hotel.RoomDao;
import by.rozmysl.bookingServlet.db.ConnectionPool;
import by.rozmysl.bookingServlet.db.ConnectionSource;
import by.rozmysl.bookingServlet.entity.hotel.Room;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.sql.SQLException;

/**
 * Provides service to initialize actions on the CreateNewRoom.
 */
public class CreateNewRoom implements Action {
    private static final Logger LOGGER = LoggerFactory.getLogger(CreateNewRoom.class);

    /**
     * Executes actions on request content.
     *
     * @param req request content
     * @return page to go
     * @throws SQLException if there was an error accessing the database
     */
    @Override
    public String execute(HttpServletRequest req) throws SQLException {
        final ConnectionSource con = ConnectionPool.getInstance().getConnectionFromPool();
        try {
            RoomDao roomDao = DaoFactory.getInstance().roomDao(con);
            req.setAttribute("allRooms", roomDao.findAllTypesRooms());
            if (req.getParameter("action") == null) return String.format("forward:%s", "/WEB-INF/views/admin/addRoom.jsp");
            if (roomDao.getById(Integer.parseInt(req.getParameter("roomNumber"))) != null) {
                req.setAttribute("errorRoomNumber", "Изменить параметры существующих номеров можно на странице" +
                        " \"Изменить параметры номеров\"");
                ConnectionPool.getInstance().returnConnectionToPool(con);
                return String.format("forward:%s", "/WEB-INF/views/admin/addRoom.jsp");
            }
            Room room = roomDao.findRoomByTypeAndSleeping(req.getParameter("type"), Integer.parseInt(req.getParameter("sleeps")));
            if (room == null && req.getParameter("price") == null) {
                req.setAttribute("roomNumber", req.getParameter("roomNumber"));
                req.setAttribute("type", req.getParameter("type"));
                req.setAttribute("sleeps", Integer.parseInt(req.getParameter("sleeps")));
                req.setAttribute("newRoom", null);
                ConnectionPool.getInstance().returnConnectionToPool(con);
                return String.format("forward:%s", "/WEB-INF/views/admin/addRoom.jsp");
            }
            if (room != null) {
                room = roomDao.save(new Room(Integer.parseInt(req.getParameter("roomNumber")), room.getType(),
                        room.getSleeps(), room.getPrice()));
                LOGGER.info("Admin '" + req.getUserPrincipal().getName() + "' created a new room - " + room);
            }
            if (req.getParameter("price") != null) {
                room = roomDao.save(new Room(Integer.parseInt(req.getParameter("roomNumber")), req.getParameter("type"),
                        Integer.parseInt(req.getParameter("sleeps")), new BigDecimal(req.getParameter("price"))));
                LOGGER.info("Admin '" + req.getUserPrincipal().getName() + "' created a new room - " + room);
            }
        } finally {
            ConnectionPool.getInstance().returnConnectionToPool(con);
        }
        return String.format("forward:%s", "/WEB-INF/views/admin/admin.jsp");
    }
}