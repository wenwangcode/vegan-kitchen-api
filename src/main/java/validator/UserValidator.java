package validator;

import manager.UserManager;

/**
 * Created by adam on 14/11/15.
 */
public class UserValidator {

    private UserManager userManager;

    public UserValidator()
    {
        userManager = new UserManager();
    }


    //check user if blocked
    //check if the token is valid
    //anything
    //handle error and pass to users, use number to represent
    /*
    **
     */
    public static boolean isValid(String authorization)
    {
        authorization = this.userManager.createSessionId();
        if (authorization != null)
        {

        }

        return true;
    }
}
