package factory.database;

import application.MyApplication;
import exception.DatabaseException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by wendywang on 2015-11-08.
 *
 * Be able to connect the database
 */
public class ConnectionFactory {

    private static final String connectionUrl = MyApplication.properties.getProperty("mysql_url");
    private static final String mysqlConnectorDriver = MyApplication.properties.getProperty("mysql_connector_driver");
    private static final String mysqlUser = MyApplication.properties.getProperty("mysql_user");
    private static final String mysqlPassword = MyApplication.properties.getProperty("mysql_password");

    public static Connection getConnection() throws DatabaseException {
        try{
            Class.forName(mysqlConnectorDriver).newInstance();
            return DriverManager.getConnection(connectionUrl, mysqlUser, mysqlPassword);
        } catch (IllegalAccessException|InstantiationException|ClassNotFoundException exception) {
            throw new DatabaseException(DatabaseException.DATABASE_DRIVER_INSTANTIATION_ERROR_MESSAGE);
        } catch (SQLException exception) {
            throw new DatabaseException(DatabaseException.DATABASE_CONNECTION_ERROR_MESSAGE);
        }
    }

}
