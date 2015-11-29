package model;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Created by wendywang on 2015-11-28.
 */
public class UserAttempt extends model.mapping.tables.pojos.UserAttempt {
    private Integer userId;
    private Integer attemptNumber;
    private String  message;
    private String  sessionId;

    public UserAttempt() {}

    public UserAttempt(UserAttempt value) {
        this.userId = value.userId;
        this.attemptNumber = value.attemptNumber;
        this.message = value.message;
        this.sessionId = value.sessionId;
    }

    @Override
    @JsonIgnore
    public Integer getUserId() {
        return userId;
    }

    @Override
    @JsonIgnore
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    @JsonIgnore
    public String getSessionId() {
        return sessionId;
    }

    @Override
    @JsonIgnore
    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    @Override
    @JsonIgnore
    public String getMessage() {
        return message;
    }

    @Override
    @JsonIgnore
    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    @JsonIgnore
    public Integer getAttemptNumber() {
        return attemptNumber;
    }

    @Override
    @JsonIgnore
    public void setAttemptNumber(Integer attemptNumber) {
        this.attemptNumber = attemptNumber;
    }
}

