package factory.response;


import javax.ws.rs.core.Response;
import java.util.HashMap;

/**
 * Created by adam on 14/11/15.
 */
public abstract class ResponseFactory {

    private static final String REQUEST_STATUS_KEY = "status";
    private static final String RESPONSE_RESULT_KEY = "result";

    protected static final String SESSION_CREATION_SUCCESS = "Login session created.";
    protected static final String SESSION_END_SUCCESS = "Session ended successfully";

    protected static final String SESSION_END_FAIULRE = "Failed to end session";
    protected static final String FORBIDDEN_MESSAGE = "Lack permission to perform action.";
    protected static final String CONTENT_RETRIEVAL_SUCCESS = "Content retrieval success.";
    protected static final String CONTENT_RETRIEVAL_FAILURE  = "Content retrieval failed.";
    protected static final String CONTENT_CREATION_SUCCESS = "Content creation success.";
    protected static final String CONTENT_CREATION_FAILURE = "Content creation failure.";
    protected static final String CONTENT_UPDATE_SUCCESS_MESSAGE = "Content update success.";
    protected static final String CONTENT_UPDATE_FAIL_MESSAGE = "Content update failure.";

    protected static Object buildResponseBody(String requestStatusMessage, Object resultContent) {
        HashMap<String, Object> responseBody = new HashMap<>();
        responseBody.put(REQUEST_STATUS_KEY, requestStatusMessage);
        responseBody.put(RESPONSE_RESULT_KEY, resultContent);
        return responseBody;
    }

    protected static Object buildResponseBody(String requestStatusMessage) {
        HashMap<String, Object> responseBody = new HashMap<>();
        responseBody.put(REQUEST_STATUS_KEY, requestStatusMessage);
        return responseBody;
    }

    protected static Object buildResponseBody(String requestStatusMessage, String errorMessage) {
        HashMap<String, Object> responseBody = new HashMap<>();
        responseBody.put(REQUEST_STATUS_KEY, requestStatusMessage + " - " + errorMessage);
        return responseBody;
    }

    protected static Response getForbiddenResponse(String message) {
        return Response.status(Response.Status.FORBIDDEN).entity(buildResponseBody(FORBIDDEN_MESSAGE + ": " + message)).build();
    }

}
