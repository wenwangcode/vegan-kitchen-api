package exception;

/**
 * Created by adam on 11/12/15.
 */
public class SessionNotFoundException extends Exception {
    public SessionNotFoundException(String message) {
        super(message);
    }
}
