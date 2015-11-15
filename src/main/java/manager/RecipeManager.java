package manager;

import factory.database.ConnectionFactory;
import model.Recipe;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by wendywang on 2015-11-08.
 */

public class RecipeManager {


    public RecipeManager(){

    }
    public ArrayList<Recipe> getRecipes() throws SQLException {
        ArrayList<Recipe> recipeArrayList = new ArrayList<>();

        //it will close the query after the try block
        try (Connection connection = ConnectionFactory.getConnection()) {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM recipe");
            ResultSet rs = stmt.executeQuery();
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
        catch (Exception e) {
            e.printStackTrace();
        }
        return recipeArrayList;
    }

    public Recipe getRecipeByID(int recipeID) throws SQLException {
        Recipe recipe = new Recipe();
        try(Connection connection = ConnectionFactory.getConnection()) {
            //added ? to prevent injection
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM recipe WHERE recipe_id = ?");
            stmt.setInt(1, recipeID);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                Recipe recipeObj = new Recipe();
                recipeObj.setId(rs.getInt("recipe_id"));
                recipeObj.setDishName(rs.getString("dish_name"));
                recipeObj.setImageURL(rs.getString("dish_image_url"));
                recipeObj.setServingSize(rs.getString("serving"));
                recipeObj.setSummary(rs.getString("summary"));
                recipeObj.setAuthorUserID(rs.getInt("author_user_id"));
                recipe = recipeObj;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return recipe;
    }


}
