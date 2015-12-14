package exception;

/**
 * Created by adam on 11/12/15.
 */
public class InvalidUserCreationException extends Exception {

    private static final String INVALID_USER_CREATION_MESSAGE = "Invalid user creation: ";

    public InvalidUserCreationException(String message) {
        super(INVALID_USER_CREATION_MESSAGE + message);
    }
}
