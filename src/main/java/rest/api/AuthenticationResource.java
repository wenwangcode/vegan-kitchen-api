package rest.api;

import factory.response.AuthenticationResponseFactory;
import model.User;

import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

/**
 * Created by adam on 07/12/15.
 */

@Path("authentication")
@Singleton
public class AuthenticationResource {

    AuthenticationResponseFactory responseFactory = new AuthenticationResponseFactory();

    @POST
    @Path("signup")
    @Consumes("application/json")
    @Produces("application/json")
    public Response signUp(User user) throws Exception {
        return responseFactory.buildUserSignUpResponse(user);
    }

    @POST
    @Path("login")
    @Consumes("application/json")
    @Produces("application/json")
    public Response login(@HeaderParam("username") String userName, @HeaderParam("password") String password) {
        return responseFactory.buildUserLoginResponse(userName, password);
    };

    @POST
    @Path("logout")
    @Produces("application/json")
    public Response logout(@HeaderParam("Authorization") String authorization) {
        return responseFactory.buildUserLogoutResponse(authorization);
    }

}
