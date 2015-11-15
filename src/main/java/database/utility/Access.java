package database.utility;

import model.ApplicationLogging;
import model.CookingInstruction;
import model.Recipe;
import model.User;
import org.glassfish.jersey.message.internal.XmlCollectionJaxbProvider;

import javax.xml.transform.Result;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by wendywang on 2015-11-14.
 *
 * Execute the query and be able to connect each column of each table
 */
public class Access {
    public ArrayList<Recipe> getRecipes(Connection con) throws SQLException {
        ArrayList<Recipe> recipeArrayList = new ArrayList<Recipe>();
        PreparedStatement stmt = con.prepareStatement("SELECT * FROM recipe");
        ResultSet rs = stmt.executeQuery();
        try{
            while(rs.next()){
                Recipe recipeObj = new Recipe();
                recipeObj.setId(rs.getInt("recipe_id"));
                recipeObj.setDishName(rs.getString("dish_name"));
                recipeObj.setImageURL(rs.getString("dish_image_url"));
                recipeObj.setServingSize(rs.getString("serving"));
                recipeObj.setSummary(rs.getString("summary"));
                recipeObj.setAuthorUserID(rs.getInt("author_user_id"));
                recipeArrayList.add(recipeObj);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return recipeArrayList;
    }

    public ArrayList<User> getUsers(Connection con) throws SQLException {
        ArrayList<User> userArrayList = new ArrayList<User>();
        PreparedStatement stmt = con.prepareStatement("SELECT * FROM user");
        ResultSet rs = stmt.executeQuery();
        try
        {
            while (rs.next()){
                User userObj = new User();
                userObj.setUser_id(rs.getInt("user_id"));
                userObj.setEmail(rs.getString("email"));
                userObj.setPasscode(rs.getString("password"));
                userObj.setUsername(rs.getString("user_name"));
                userArrayList.add(userObj);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return userArrayList;
    }

    public ArrayList<CookingInstruction> getCookingInstructions(Connection con) throws SQLException
    {
        ArrayList<CookingInstruction> cookingInstructions = new ArrayList<CookingInstruction>();
        PreparedStatement stmt = con.prepareStatement("SELECT * FROM cooking_instruction");
        ResultSet rs = stmt.executeQuery();
        try
        {
            while (rs.next()){
                CookingInstruction cookingInstructionObj = new CookingInstruction();
                cookingInstructionObj.setInstruction(rs.getString("instruction"));
                cookingInstructionObj.setInstructionID(rs.getInt("instruction_id"));
                cookingInstructionObj.setRecipeID(rs.getInt("recipe_id"));
                cookingInstructionObj.setImageURL((rs.getString("image_url")));
                cookingInstructions.add(cookingInstructionObj);
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return cookingInstructions;
    }
    public ArrayList<ApplicationLogging> getApplicationLogging(Connection con) throws SQLException
    {
        ArrayList<ApplicationLogging> applicationLoggings = new ArrayList<ApplicationLogging>();
        PreparedStatement stmt = con.prepareStatement("SELECT * FROM applicationLogging");
        ResultSet rs = stmt.executeQuery();
        try
        {
            while (rs.next()){
                ApplicationLogging applicationLoggingObj = new ApplicationLogging();
                applicationLoggingObj.setSessionID(rs.getString("session_id"));
                applicationLoggingObj.setSessionID(rs.getString("user_id"));
                applicationLoggings.add(applicationLoggingObj);
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return applicationLoggings;
    }


}
