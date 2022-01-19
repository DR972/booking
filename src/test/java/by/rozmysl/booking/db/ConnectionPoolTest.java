package by.rozmysl.booking.db;

import by.rozmysl.booking.model.db.ConnectionPool;
import org.junit.AfterClass;
import org.junit.Test;
import org.junit.Ignore;

import java.sql.Connection;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNull.notNullValue;

@Ignore
public class ConnectionPoolTest {

    @Test
    public void initTest() {
        assertThat(ConnectionPool.getInstance(), notNullValue());
    }

    @Test
    public void getConnectionFromPool(){
        Connection connection = ConnectionPool.getInstance().getConnectionFromPool();
        assertThat(connection, notNullValue());
        ConnectionPool.getInstance().returnConnectionToPool(connection);
    }

    @AfterClass
    public static void destroy(){
        ConnectionPool.getInstance().destroy();
    }
}
