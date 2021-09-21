package by.rozmysl.bookingServlet.action.admin;

import by.rozmysl.bookingServlet.action.Action;
import by.rozmysl.bookingServlet.dao.DaoFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;

/**
 * Provides service to initialize actions on the ServicesPrice.
 */
public class ServicesPrice implements Action {
    private static final Logger logger = LoggerFactory.getLogger(ServicesPrice.class);

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
        if (req.getParameter("changeFoodPrice") != null && req.getParameter("changeFoodPrice").equals("changeFoodPrice")) {
            dao.foodDao().changeFoodPrice(req.getParameter("foodId"), Double.parseDouble(req.getParameter("foodPrice")));
            logger.info("For food  '" + req.getParameter("foodId") + "', the price was changed to '" +
                    req.getParameter("foodPrice") + "' by admin " + req.getUserPrincipal().getName());
        }
        if (req.getParameter("changeServicePrice") != null && req.getParameter("changeServicePrice").equals("changeServicePrice")) {
            dao.servicesDao().changeServicePrice(req.getParameter("serviceId"), Double.parseDouble(req.getParameter("servicePrice")));
            logger.info("For service  '" + req.getParameter("serviceId") + "', the price was changed to '" +
                    req.getParameter("servicePrice") + "' by admin " + req.getUserPrincipal().getName());
        }
        return String.format("forward:%s", "/WEB-INF/views/admin/changeServicesPrice.jsp");
    }
}