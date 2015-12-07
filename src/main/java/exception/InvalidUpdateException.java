package exception;

/**
 * Created by adam on 02/12/15.
 */
public class InvalidUpdateException extends Exception {

    private static final String INVALID_UPDATE_DATA_ERROR_MESSAGE = "Failed to validate data for update: ";

    public InvalidUpdateException(String message) {
        super(message);
    }

    public static String getInvalidUpdateDataMessage(String detail) {
        return INVALID_UPDATE_DATA_ERROR_MESSAGE + detail;
    }

}
