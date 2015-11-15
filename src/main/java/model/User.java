package model;

/**
 * Created by wendywang on 2015-11-14.
 */
public class User {
    private int user_id;
    private String email;
    private String passcode;
    private String username;

    public User(){} //JAXB needs this
    public User(int user_id, String email, String passcode, String username){
        this.user_id = user_id;
        this.email = email;
        this.passcode = passcode;
        this.username = username;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasscode() {
        return passcode;
    }

    public void setPasscode(String passcode) {
        this.passcode = passcode;
    }
}
