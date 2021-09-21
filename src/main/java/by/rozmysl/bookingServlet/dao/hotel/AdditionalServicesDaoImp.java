package by.rozmysl.bookingServlet.dao.hotel;

import by.rozmysl.bookingServlet.db.ConnectionSource;
import by.rozmysl.bookingServlet.entity.hotel.AdditionalServices;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Provides the base model implementation for `ADDITIONALSERVICES` table DAO with the <b>ConnectionSource</b> properties.
 */
public class AdditionalServicesDaoImp implements AdditionalServicesDao {
    private final ConnectionSource con = new ConnectionSource();

    /**
     * Searches for the Additional Services in the `ADDITIONALSERVICES` table by id
     *
     * @param type id of the Additional services
     * @return AdditionalServices object
     * @throws SQLException if there was an error accessing the database
     */
    @Override
    public AdditionalServices getById(String type) throws SQLException {
        return getResultSet("select * from ADDITIONALSERVICES where TYPE = '" + type + "'").get(0);
    }

    /**
     * Searches for all Additional Services in the `ADDITIONALSERVICES` table
     *
     * @param page number of the page to return
     * @param rows number of rows per page
     * @return list of AdditionalServices objects
     * @throws SQLException if there was an error accessing the database
     */
    @Override
    public List<AdditionalServices> getAll(int page, int rows) throws SQLException {
        return getResultSet("select * from ADDITIONALSERVICES");
    }

    @Override
    public AdditionalServices save(AdditionalServices additionalServices) {
        return additionalServices;
    }

    @Override
    public void delete(String type) {
    }

    /**
     * Changes the price of the Additional Service in the `ADDITIONALSERVICES` table
     *
     * @param type  id of the Additional Service
     * @param price new price
     * @throws SQLException if there was an error accessing the database
     */
    @Override
    public void changeServicePrice(String type, double price) throws SQLException {
        con.update("update ADDITIONALSERVICES set PRICE = " + price + " where TYPE = '" + type + "'");
    }

    /**
     * Get a list of query results from the 'ADDITIONALSERVICES' table
     *
     * @param sql the wording of the request to the database
     * @return list of AdditionalServices objects
     * @throws SQLException if there was an error accessing the database
     */
    private List<AdditionalServices> getResultSet(String sql) throws SQLException {
        return con.get(sql).stream().map(d -> new AdditionalServices(d.get("TYPE"), Double.parseDouble(d.get("PRICE"))))
                .distinct().collect(Collectors.toList());
    }
}