package database;

import java.sql.*;
import java.util.Map;

/**
 * DBController is a singleton patterned class that
 * communicates to the sql database.
 *
 * @author GriffinBabe
 */
public class DBController {

    /**
     * Environment variable name for the user.
     */
    static String SYS_ENV_USER = "DManager_DB_User";

    /**
     * Environment variable name for the password
     */
    static String SYS_ENV_PWD = "DManager_DB_Pwd";

    /**
     * Environment variable name for the ip
     */
    static String SYS_ENV_IP = "DManager_DB_Ip";

    /**
     * Name of the database from the mysql server. Hard coded
     */
    static String DB_NAME = "DManager";

    static int DEFAULT_PORT = 3306;

    /**
     * String needed by {@link Connection} to connect to the sql database.
     */
    static String CONNECTION_ADDRESS = "jdbc:mysql://%s:%d/%s?user=%s&password=%s";

    /**
     * String containing all the information to connect to the database.
     */
    private String connectionString;

    /**
     * Singleton {@link DBController} instace.
     */
    private static DBController instance = null;

    /**
     * Connection, interface to the MySQL server.
     */
    private Connection connection = null;

    /**
     * {@link DBController} constructor.
     * Private as we use singleton pattern.
     */
    private DBController() throws SQLException {
        Map<String, String> enVariables = System.getenv();
        String username = enVariables.get(SYS_ENV_USER);
        String password = enVariables.get(SYS_ENV_PWD);
        String ip = enVariables.get(SYS_ENV_IP);
        this.connectionString = String.format(CONNECTION_ADDRESS, ip, DEFAULT_PORT, DB_NAME, username, password);

        // We instantiate the connection object but we close it asap.
        connection = DriverManager.getConnection(this.connectionString);
        this.connection.close();
    }

    /**
     * Singleton design pattern. Gets the instance of
     * {@link DBController} to access the SQL database.
     *
     * @return
     */
    public static DBController getInstance() throws SQLException {
        if (DBController.instance == null) {
            DBController.instance = new DBController();
        }
        return DBController.instance;
    }

    /**
     * Wrapper around {@link Connection#prepareStatement(String)} to keep
     * the connection management encapsulated.
     *
     * @param query the SQL query to execute
     * @return a {@link PreparedStatement}.
     * @throws SQLException if there is a problem with the connection.
     */
    public PreparedStatement getPreparedStmt(String query) throws SQLException {
        if (connection.isClosed()) {
            this.connection = DriverManager.getConnection(connectionString);
        }
        return connection.prepareStatement(query);
    }

    /**
     * Wrapper around {@link Statement#executeQuery(String)} to keep
     * the connection management encapsulated.
     *
     * @param stmt prepared and parametrized statement
     * @return the {@link ResultSet}.
     * @throws SQLException if there is a problem with the connection or the query.
     */
    public ResultSet executePreparedStatement(PreparedStatement stmt) throws SQLException {
        ResultSet set = stmt.executeQuery();
        if (!connection.isClosed()) {
            this.connection.close();
        }
        return set;
    }

    /**
     * See {@link #executePreparedStatement(PreparedStatement)}. Same
     * but with a simple {@link Statement} instead of {@link PreparedStatement}.
     */
    public ResultSet executeStatement(String query) throws SQLException {
        if (connection.isClosed()) {
            this.connection = DriverManager.getConnection(connectionString);
        }
        Statement stmt = connection.createStatement();
        ResultSet set = stmt.executeQuery(query);
        this.connection.close();
        return set;
    }

    /**
     * Returns a set of the metadata from the specified table.
     *
     * @param tableName, the name we want to look for
     * @throws SQLException
     */
    public ResultSet getDatabaseTableData(String tableName) throws SQLException {
        if (connection.isClosed()) {
            this.connection = DriverManager.getConnection(connectionString);
        }
        DatabaseMetaData meta = connection.getMetaData();
        ResultSet set = meta.getTables(null, null, tableName, new String[] {"TABLE"});
        this.connection.close();
        return set;
    }


}
