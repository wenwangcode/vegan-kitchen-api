package manager;

import factory.database.ConnectionFactory;
import model.User;
import validator.UserValidator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by wendywang on 2015-11-14.
 */
public class UserManager {

    private User user;

    public UserManager()
    {
        user = new User();
    }

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

    /*
    ** Update email
     */
    public void updateEmail(String email) throws Exception
    {
        if (email != null && this.isValidEmail(email))
        {
            user.setEmail(email);
        }
    }

    /*
    ** Update password
     */
    public void updatePassword(String password) throws Exception
    {
        if (password != null)
        {
            user.setPassword(password);
        }
    }

    /*
    ** determine whether the user is blocked or not
     */
    public boolean isBlocked() throws Exception
    {
        return false;
    }

    /*
    ** source: http://stackoverflow.com/questions/624581/what-is-the-best-java-email-address-validation-method
     */
    public static boolean isValidEmail(String enteredEmail){
        String EMAIL_REGIX = "^[\\\\w!#$%&’*+/=?`{|}~^-]+(?:\\\\.[\\\\w!#$%&’*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\\\.)+[a-zA-Z]{2,6}$";
        Pattern pattern = Pattern.compile(EMAIL_REGIX);
        Matcher matcher = pattern.matcher(enteredEmail);
        return ((!enteredEmail.isEmpty()) && (enteredEmail!=null) && (matcher.matches()));
    }

    /*
    ** Verify whether the email address is valid or not
     */
    public boolean verifyEmailAddress() throws Exception
    {
            String email = this.user.getEmail();
            if (email == null) return false;
            else
            {
                if (this.isValidEmail(email)) return true;
                else return false;
            }
    }

    /*
    ** Create a new session ID
     */
    public void createSessionId() throws Exception
    {

    }

    /*
    ** Create a user id
     */
    public Integer createUserId() throws Exception
    {
        return 0;
    }







}
