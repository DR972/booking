package by.rozmysl.bookingServlet.action.admin;

import by.rozmysl.bookingServlet.action.Action;
import by.rozmysl.bookingServlet.dao.DaoFactory;
import by.rozmysl.bookingServlet.dao.hotel.AdditionalServicesDao;
import by.rozmysl.bookingServlet.dao.hotel.FoodDao;
import by.rozmysl.bookingServlet.db.ConnectionPool;
import by.rozmysl.bookingServlet.db.ConnectionSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.sql.SQLException;

/**
 * Provides service to initialize actions on the ChangeServicesPrice.
 */
public class ChangeServicesPrice implements Action {
    private static final Logger LOGGER = LoggerFactory.getLogger(ChangeServicesPrice.class);

    /**
     * Executes actions on request content.
     *
     * @param req request content
     * @return page to go
     * @throws SQLException if there was an error accessing the database
     */
    @Override
    public String execute(HttpServletRequest req) throws SQLException {
        DaoFactory dao = DaoFactory.getInstance();
        final ConnectionSource con = ConnectionPool.getInstance().getConnectionFromPool();
        FoodDao foodDao =dao.foodDao(con);
        AdditionalServicesDao servicesDao = dao.servicesDao(con);
        if (req.getParameter("changeFoodPrice") != null && req.getParameter("changeFoodPrice").equals("changeFoodPrice")) {
            foodDao.changeFoodPrice(req.getParameter("foodId"), new BigDecimal(req.getParameter("foodPrice")));
            LOGGER.info("For food  '" + req.getParameter("foodId") + "', the price was changed to '" +
                    req.getParameter("foodPrice") + "' by admin " + req.getUserPrincipal().getName());
        }
        if (req.getParameter("changeServicePrice") != null && req.getParameter("changeServicePrice").equals("changeServicePrice")) {
            servicesDao.changeServicePrice(req.getParameter("serviceId"), new BigDecimal(req.getParameter("servicePrice")));
            LOGGER.info("For service  '" + req.getParameter("serviceId") + "', the price was changed to '" +
                    req.getParameter("servicePrice") + "' by admin " + req.getUserPrincipal().getName());
        }
        req.setAttribute("food", foodDao.getAll(0, 0));
        req.setAttribute("services", servicesDao.getAll(0, 0));
        ConnectionPool.getInstance().returnConnectionToPool(con);
        return String.format("forward:%s", "/WEB-INF/views/admin/changeServicesPrice.jsp");
    }
}