package exception;

/**
 * Created by adam on 06/12/15.
 */
public class DatabaseException extends Exception {

    public static final String DATABASE_CONNECTION_ERROR_MESSAGE = "Database connection error.";
    public static final String DATABASE_DRIVER_INSTANTIATION_ERROR_MESSAGE = "Failed to instantiate database driver.";

    private static final String DATA_ACCESS_ERROR_MESSAGE = "Failed to retrieve data: ";
    private static final String DATA_CREATION_ERROR_MESSAGE = "Failed to store passed in data: ";
    private static final String DATA_UPDATE_ERROR_MESSAGE = "Failed to update existing data: ";

    public DatabaseException(String message) {
        super(message);
    }

    public static String getDataRetrievalErrorMessage(String detail) {
        return DATA_ACCESS_ERROR_MESSAGE + detail;
    }

    public static String getDataCreationErrorMessage(String detail) {
        return  DATA_CREATION_ERROR_MESSAGE + detail;
    }

    public static String getDataUpdateErrorMessage(String detail) {
        return  DATA_UPDATE_ERROR_MESSAGE + detail;
    }

}
