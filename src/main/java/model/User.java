package model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by wendywang on 2015-11-14.
 */
public class User extends model.mapping.tables.pojos.User {
    private Integer user_id;
    private String email;
    private String passcode;
    private String username;
    private Byte isBlocked;

    public User(){} //JAXB needs this
    public User(Integer user_id, String email, String passcode, String username, Byte isBlocked){
        this.user_id = user_id;
        this.email = email;
        this.passcode = passcode;
        this.username = username;
        this.isBlocked = isBlocked;
    }

    @Override
    @JsonIgnore
    public Integer getUserId() {
        return user_id;
    }
    @Override
    @JsonIgnore
    public void setUserId(Integer user_id) {
        this.user_id = user_id;
    }
    @Override
    @JsonProperty("email")
    public String getEmail() {
        return email;
    }
    @Override
    @JsonProperty("email")
    public void setEmail(String email) {
        this.email = email;
    }
    @Override
    @JsonProperty("username")
    public String getUserName() {
        return username;
    }
    @Override
    @JsonProperty("username")
    public void setUserName(String username) {
        this.username = username;
    }
    @Override
    @JsonProperty("password")
    public String getPassword() {
        return passcode;
    }
    @Override
    @JsonProperty("password")
    public void setPassword(String passcode) {
        this.passcode = passcode;
    }
    @Override
    @JsonProperty("isblocked")
    public Byte getIsBlocked()
    {
        return isBlocked;
    }
    @Override
    @JsonProperty("isblocked")
    public void setIsBlocked(Byte isBlocked)
    {
        this.isBlocked = isBlocked;
    }
}
