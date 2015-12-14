package factory.response;

import exception.DatabaseException;
import exception.InvalidUserCreationException;
import manager.UserManager;
import model.User;

import javax.ws.rs.core.Response;

import java.security.NoSuchAlgorithmException;

import static javax.ws.rs.core.Response.Status.BAD_REQUEST;
import static javax.ws.rs.core.Response.Status.CREATED;

/**
 * Created by adam on 11/12/15.
 */
public class AuthenticationResponseFactory extends ResponseFactory {

    private UserManager userManager = new UserManager();

    public Response buildUserSignUpResponse(User user) {
        Response response;
        try {
            userManager.validateNewUser(user);
            // store only encrypted password into database
            user.setPassword(userManager.getEncryptPassword(user.getPassword()));
            userManager.createUser(user);
            response = Response.status(CREATED).entity(buildResponseBody(CONTENT_CREATION_SUCCESS)).build();
        }
        catch (InvalidUserCreationException|DatabaseException|NoSuchAlgorithmException exception) {
            response = Response.status(BAD_REQUEST).entity(buildResponseBody(CONTENT_CREATION_FAILURE, exception.getMessage())).build();
        }
        return response;
    }

    public Response buildUserLoginResponse(String userName, String password) {
        Response response;
        try {
            String authorizationToken = userManager.login(userName, password);
            response = Response.status(CREATED).entity(buildResponseBody(SESSION_CREATION_SUCCESS, authorizationToken)).build();
        }
        catch (Exception exception) {
            response = Response.status(BAD_REQUEST).entity(buildResponseBody(CONTENT_CREATION_FAILURE, exception.getMessage())).build();
        }
        return response;
    }
}
