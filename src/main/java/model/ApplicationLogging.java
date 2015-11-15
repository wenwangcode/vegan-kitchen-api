package model;

import org.glassfish.jersey.message.internal.XmlCollectionJaxbProvider;

/**
 * Created by wendywang on 2015-11-14.
 */
public class ApplicationLogging {

    private int userID;
    private String sessionID;

    public ApplicationLogging(){}

    public ApplicationLogging(int userID, String sessionID){

        this.userID = userID;
        this.sessionID = sessionID;
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
}
