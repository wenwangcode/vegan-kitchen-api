package model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.jooq.types.UInteger;

/**
 * Created by wendywang on 2015-11-14.
 */
public class User extends model.mapping.tables.pojos.User {

    public User(){} //JAXB needs this

    public User(Integer userId, String userName, String email, String password, String authorizationToken, UInteger lastAccess) {
        super(userId, userName, email, password, authorizationToken, lastAccess);
    }

    @Override
    @JsonProperty("user_id")
    public Integer getUserId() {
        return super.getUserId();
    }

    @Override
    @JsonProperty("user_id")
    public void setUserId(Integer userId) {
        super.setUserId(userId);
    }

    @Override
    @JsonProperty("email")
    public String getEmail() {
        return super.getEmail();
    }

    @Override
    @JsonProperty("email")
    public void setEmail(String email) {
        super.setEmail(email);
    }

    @Override
    @JsonProperty("username")
    public String getUserName() {
        return super.getUserName();
    }

    @Override
    @JsonProperty("username")
    public void setUserName(String userName) {
        super.setUserName(userName);
    }

    @Override
    @JsonProperty("password")
    public String getPassword() {
        return super.getPassword();
    }

    @Override
    @JsonProperty("password")
    public void setPassword(String password) {
        super.setPassword(password);
    }

    @Override
    @JsonIgnore
    public void setAuthorizationToken(String authorizationToken) {
        super.setAuthorizationToken(authorizationToken);
    }

    @Override
    @JsonIgnore
    public String getAuthorizationToken() {
        return super.getAuthorizationToken();
    }

    @Override
    @JsonIgnore
    public UInteger getLastAccess() {
        return super.getLastAccess();
    }

    @Override
    @JsonIgnore
    public void setLastAccess(UInteger lastAccess) {
        super.setLastAccess(lastAccess);
    }

}
