package database.utility;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Created by wendywang on 2015-11-08.
 *
 * Be able to connect the database
 */


public class ConnectionFactory {

    public static Connection getConnection() throws Exception{
        try{
            String connectionURL = "jdbc:mysql://localhost:3306/vegan_kitchen_api";
            Connection connection = null;
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            connection = DriverManager.getConnection(connectionURL, "root", "abcde");
            return connection;
        }
        catch (Exception e){
            throw e;
        }
    }

}
