package database.utility;

import application.MyApplication;

import java.sql.Connection;
import java.sql.DriverManager;

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

    public static Connection getConnection() throws Exception{
        try{
            Class.forName(mysqlConnectorDriver).newInstance();
            return DriverManager.getConnection(connectionUrl, mysqlUser, mysqlPassword);
        }
        catch (Exception exception){
            exception.printStackTrace();
            throw exception;
        }
    }

}
