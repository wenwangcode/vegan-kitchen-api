package application.context;

import model.User;

import java.security.Principal;

/**
 * Created by adam on 11/12/15.
 */
public class SecurityContext implements javax.ws.rs.core.SecurityContext {

    private User user;

    public SecurityContext(User user) {
        this.user = user;
    }

    @Override
    public Principal getUserPrincipal() {
        return new Principal() {
            @Override
            public String getName() {
                return user.getUserName();
            }
        };
    }

    @Override
    public boolean isUserInRole(String s) {
        return false; // TODO
    }

    @Override
    public boolean isSecure() {
        return false; // TODO
    }

    @Override
    public String getAuthenticationScheme() {
        return null; // TODO
    }
}
