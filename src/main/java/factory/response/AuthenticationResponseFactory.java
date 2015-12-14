package factory.response;

import exception.DatabaseException;
import exception.InvalidUserCreationException;
import exception.SessionNotFoundException;
import manager.UserManager;
import model.User;

import javax.print.attribute.standard.Finishings;
import javax.ws.rs.core.Response;

import java.security.NoSuchAlgorithmException;

import static javax.ws.rs.core.Response.Status.BAD_REQUEST;
import static javax.ws.rs.core.Response.Status.CREATED;
import static javax.ws.rs.core.Response.Status.NO_CONTENT;

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

    public Response buildUserLogoutResponse(String authorizationToken) {
        // return NO_CONTENT status and inform the user that his/her session is finished if the authorization key is valid
        // any exception means a BAD_REQUEST should be returned
        UserManager userManager = new UserManager();
        Response response;
        try {
            userManager.logout(authorizationToken);
            response = Response.status(NO_CONTENT).entity(buildResponseBody(SESSION_END_SUCCESS)).build();

        } catch (SessionNotFoundException e) {
            response = Response.status(BAD_REQUEST).entity(buildResponseBody(CONTENT_CREATION_FAILURE, e.getMessage())).build();
        }

        return response;
    }
}
