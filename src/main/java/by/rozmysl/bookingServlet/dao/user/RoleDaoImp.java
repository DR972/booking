package by.rozmysl.bookingServlet.dao.user;

import by.rozmysl.bookingServlet.db.ConnectionSource;
import by.rozmysl.bookingServlet.entity.user.UserRole;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Provides the base model implementation for `USER_ROLE` and `ROLE` table DAO with the <b>ConnectionSource</b> properties.
 */
public class RoleDaoImp implements RoleDao {
    private final ConnectionSource con;

    /**
     * The constructor creates a new object RoleDaoImp with the <b>con</b> property
     */
    public RoleDaoImp(ConnectionSource con) {
        this.con = con;
    }

    /**
     * Searches for the UserRole in the `USER_ROLE` table by id
     *
     * @param name of Role
     * @return UserRole object
     * @throws SQLException if there was an error accessing the database
     */
    @Override
    public UserRole getById(String name) throws SQLException {
        return getResultSet("select * from USER_ROLE left join ROLE on ROLE = ROLE.NAME where USER = '" + name + "'").get(0);
    }

    /**
     * Searches for all User's UserRoles in the `USER_ROLE` table
     *
     * @param username User's id
     * @return list of UserRole objects
     * @throws SQLException if there was an error accessing the database
     */
    @Override
    public List<UserRole> findUserRoleByUser(String username) throws SQLException {
        return getResultSet("select * from USER_ROLE left join ROLE on ROLE = ROLE.NAME where USER = '" + username + "'");
    }

    /**
     * Searches for all UserRoles in the `USER_ROLE` table
     *
     * @param page number of the page to return
     * @param rows number of rows per page
     * @return list of UserRole objects
     * @throws SQLException if there was an error accessing the database
     */
    @Override
    public List<UserRole> getAll(int page, int rows) throws SQLException {
        return getResultSet("select * from USER_ROLE left join ROLE on ROLE = ROLE.NAME");
    }

    /**
     * Saves the UserRole in the `USER_ROLE` table
     *
     * @param userRole UserRole
     * @return UserRole object
     * @throws SQLException if there was an error accessing the database
     */
    @Override
    public UserRole save(UserRole userRole) throws SQLException {
        con.update("insert into USER_ROLE(USER, ROLE) VALUES('" + userRole.getUsername() + "', '" + userRole.getRole() + "')");
        return userRole;
    }

    /**
     * Deletes the UserRole in the `USER_ROLE` table
     *
     * @param name of UserRole
     * @throws SQLException if there was an error accessing the database
     */
    @Override
    public void delete(String name) throws SQLException {
        con.update("delete from USER_ROLE where USER = '" + name + "'");
    }

    /**
     * Get a list of query results from the 'USER_ROLE' table
     *
     * @param sql the wording of the request to the database
     * @return list of UserRole objects
     * @throws SQLException if there was an error accessing the database
     */
    private List<UserRole> getResultSet(String sql) throws SQLException {
        return con.get(sql).stream().map(d -> new UserRole(d.get("USER"), d.get("ROLE"))).distinct().collect(Collectors.toList());
    }
}
