package validator;

import manager.UserManager;

/**
 * Created by adam on 14/11/15.
 */
public class UserValidator {

    private static UserManager userManager;

    public UserValidator()
    {
        userManager = new UserManager();
    }



    //handle error and pass to users, use number to represent
    /*
    **
     */
    public static boolean isValid(String authorization) throws Exception
    {
        authorization = userManager.createSessionId();
        if (authorization != null && !userManager.isBlocked())
        {
            return true;

        }
        return false;
    }
}
