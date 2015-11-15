package database.utility;

import model.ApplicationLogging;
import model.CookingInstruction;
import model.Recipe;
import model.User;

import java.sql.Connection;
import java.util.ArrayList;

/**
 * Created by wendywang on 2015-11-14.
 *
 * Since we are able to connect the database in ConnectionFactory class
 * and we can execute columns from tables in Access class, We can return
 * all the json of a specific table
 *
 */
public class AccessManager {

    public ArrayList<Recipe> getRecipes() throws Exception
    {
        ArrayList<Recipe> recipeArrayList = new ArrayList<Recipe>();
        ConnectionFactory db = new ConnectionFactory();
        Connection con = db.getConnection();
        Access access = new Access();
        recipeArrayList = access.getRecipes(con);
        return recipeArrayList;

    }

    public ArrayList<User> getUsers() throws Exception
    {
        ArrayList<User> userArrayList = new ArrayList<User>();
        ConnectionFactory db = new ConnectionFactory();
        Connection con  = db.getConnection();
        Access access = new Access();
        userArrayList = access.getUsers(con);
        return userArrayList;

    }

    public ArrayList<CookingInstruction> getCookingInstruction() throws Exception
    {
        ArrayList<CookingInstruction> cookingInstructions = new ArrayList<CookingInstruction>();
        ConnectionFactory db = new ConnectionFactory();
        Connection con = db.getConnection();
        Access access = new Access();
        cookingInstructions = access.getCookingInstructions(con);
        return cookingInstructions;
    }

    public ArrayList<ApplicationLogging> getApplicationLoggings() throws Exception
    {
        ArrayList<ApplicationLogging> applicationLoggings = new ArrayList<ApplicationLogging>();
        ConnectionFactory db = new ConnectionFactory();
        Connection con = db.getConnection();
        Access access = new Access();
        applicationLoggings = access.getApplicationLogging(con);
        return applicationLoggings;
    }


}
