package manager;

import factory.database.ConnectionFactory;
import model.ApplicationLogging;
import model.User;
import model.UserAttempt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wendywang on 2015-11-28.
 */
public class UserAttemptManager {

    private UserAttempt userAttempt;
    private ApplicationLogging applicationLogging;

    public UserAttemptManager()
    {
        userAttempt = new UserAttempt();
        applicationLogging = new ApplicationLogging();

    }

    public List<UserAttempt> getUserAttempt() throws Exception
    {
        ArrayList<UserAttempt> userAttemptsArrayList = new ArrayList<UserAttempt>();
        try(Connection connection = ConnectionFactory.getConnection())
        {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM user_attempt");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()){
                UserAttempt userAttemptObj = new UserAttempt();
                userAttemptObj.setUserId(rs.getInt("user_id"));
                userAttemptObj.setAttemptNumber(rs.getInt("attempt_number"));
                userAttemptObj.setMessage(rs.getString("message"));
                userAttemptObj.setSessionId(rs.getString("session_id"));
                userAttemptsArrayList.add(userAttemptObj);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return userAttemptsArrayList;
    }


}
