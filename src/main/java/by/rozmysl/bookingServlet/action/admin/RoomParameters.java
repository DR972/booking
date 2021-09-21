package by.rozmysl.bookingServlet.action.admin;

import by.rozmysl.bookingServlet.action.Action;
import by.rozmysl.bookingServlet.dao.DaoFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;

/**
 * Provides service to initialize actions on the RoomParameters.
 */
public class RoomParameters implements Action {
    private static final Logger logger = LoggerFactory.getLogger(RoomParameters.class);

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
        if (req.getParameter("changeParameters") != null && req.getParameter("changeParameters").equals("changeParameters")) {
            dao.roomDao().updateParameters(Integer.parseInt(req.getParameter("roomId")),
                    dao.roomDao().getById(Integer.parseInt(req.getParameter("parameter"))));
            logger.info("For room # " + req.getParameter("roomId") + ", the parameters were changed to '" +
                    req.getParameter("parameter") + "' by admin " + req.getUserPrincipal().getName());
        }
        if (req.getParameter("changePrice") != null && req.getParameter("changePrice").equals("changePrice")) {
            dao.roomDao().updatePrice(dao.roomDao().getById(Integer.parseInt(req.getParameter("roomId"))),
                    Double.parseDouble(req.getParameter("price")));
            logger.info("For room # " + req.getParameter("roomId") + ", the price was changed to '" +
                    req.getParameter("price") + "' by admin " + req.getUserPrincipal().getName());
        }
        return String.format("forward:%s", "/WEB-INF/views/admin/changeRoomParameters.jsp");
    }
}