package manager;

import factory.database.ConnectionFactory;
import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * Created by wendywang on 2015-11-14.
 */
public class UserManager {
    public ArrayList<User> getUsers() throws Exception {
        ArrayList<User> userArrayList = new ArrayList<User>();
        try(Connection connection = ConnectionFactory.getConnection())
        {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM user");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()){
                User userObj = new User();
                userObj.setUserId(rs.getInt("user_id"));
                userObj.setEmail(rs.getString("email"));
                userObj.setPassword(rs.getString("password"));
                userObj.setUserName(rs.getString("user_name"));
                userArrayList.add(userObj);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return userArrayList;
    }


}
