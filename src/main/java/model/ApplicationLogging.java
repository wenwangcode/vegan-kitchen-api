package model;

import org.glassfish.jersey.message.internal.XmlCollectionJaxbProvider;

/**
 * Created by wendywang on 2015-11-14.
 */
public class ApplicationLogging {

    private int userID;
    private String sessionID;
    private String message;
    private String exception;

    public ApplicationLogging(){}

    public ApplicationLogging(int userID, String sessionID, String message, String exception){

        this.userID = userID;
        this.sessionID = sessionID;
        this.exception = exception;
        this.message = message;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getSessionID() {
        return sessionID;
    }

    public void setSessionID(String sessionID) {
        this.sessionID = sessionID;
    }

    public String getMessage(){
        return message;
    }

    public void setMessage(String message){
        this.message = message;
    }

    public String getException(){
        return exception;
    }

    public void setException(String exception)
    {
        this.exception = exception;
    }


}
