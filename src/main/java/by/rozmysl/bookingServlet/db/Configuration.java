package by.rozmysl.bookingServlet.db;

/**
 * Defines the database configuration with the <b>DB_USER_NAME</b>, <b>DB_PASSWORD</b>, <b>DB_URL</b>, <b>DB_DRIVER</b>,
 * <b>DB_MAX_CONNECTIONS</b> properties
 */
public class Configuration {
    public String DB_USER_NAME;
    public String DB_PASSWORD;
    public String DB_URL;
    public String DB_DRIVER;
    public Integer DB_MAX_CONNECTIONS;

    /**
     * The constructor creates a new object Configuration
     */
    public Configuration() {
        init();
    }

    private static final Configuration configuration = new Configuration();

    /**
     * Returns the value of the configuration property
     *
     * @return a value of the configuration
     */
    public static Configuration getInstance() {
        return configuration;
    }

    /**
     * Initializes the configuration
     */
    private void init() {
        DB_USER_NAME = "sa";
        DB_PASSWORD = "";
        DB_URL = "jdbc:h2:file:./src/main/resources/data/hotelDB;MODE=MYSQL;IFEXISTS=FALSE;DB_CLOSE_DELAY=-1;INIT=RUNSCRIPT FROM 'classpath:/hotel_db_create.sql'";
        DB_DRIVER = "org.h2.Driver";
        DB_MAX_CONNECTIONS = 3;
    }
}