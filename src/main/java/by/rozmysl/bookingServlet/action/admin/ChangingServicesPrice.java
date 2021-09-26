package by.rozmysl.bookingServlet.action.admin;

import by.rozmysl.bookingServlet.action.Action;
import by.rozmysl.bookingServlet.dao.DaoFactory;
import by.rozmysl.bookingServlet.db.ConnectionSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.sql.SQLException;

/**
 * Provides service to initialize actions on the ChangingServicesPrice.
 */
public class ChangingServicesPrice implements Action {
    private static final Logger LOGGER = LoggerFactory.getLogger(ChangingServicesPrice.class);

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
        final ConnectionSource con = new ConnectionSource();
        req.setAttribute("fooDao", dao.foodDao(con));
        req.setAttribute("servicesDao", dao.servicesDao(con));
        if (req.getParameter("changeFoodPrice") != null && req.getParameter("changeFoodPrice").equals("changeFoodPrice")) {
            dao.foodDao(con).changeFoodPrice(req.getParameter("foodId"), new BigDecimal(req.getParameter("foodPrice")));
            LOGGER.info("For food  '" + req.getParameter("foodId") + "', the price was changed to '" +
                    req.getParameter("foodPrice") + "' by admin " + req.getUserPrincipal().getName());
        }
        if (req.getParameter("changeServicePrice") != null && req.getParameter("changeServicePrice").equals("changeServicePrice")) {
            dao.servicesDao(con).changeServicePrice(req.getParameter("serviceId"), new BigDecimal(req.getParameter("servicePrice")));
            LOGGER.info("For service  '" + req.getParameter("serviceId") + "', the price was changed to '" +
                    req.getParameter("servicePrice") + "' by admin " + req.getUserPrincipal().getName());
        }
        return String.format("forward:%s", "/WEB-INF/views/admin/changeServicesPrice.jsp");
    }
}