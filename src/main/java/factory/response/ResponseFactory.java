package factory.response;

import javax.ws.rs.core.Response;

/**
 * Created by adam on 14/11/15.
 */
public class ResponseFactory {

    private static final String FORBIDDEN_MESSAGE = "Lack permission to perform action.";

    public static Response buildResponse (boolean validationCheck, Object entity) {
        if (validationCheck)
            return Response.status(Response.Status.CREATED).entity(entity).build();
        else
            return Response.status(Response.Status.FORBIDDEN).entity(FORBIDDEN_MESSAGE).build();
    }
}
