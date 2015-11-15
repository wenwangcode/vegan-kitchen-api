package rest.api;

import manager.RecipeManager;
import model.Recipe;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.sql.SQLException;
import java.util.List;

@Path("recipe")
public class RecipeResource {

    RecipeManager recipeManager = new RecipeManager();

    @GET
    @Path("/all")
    @Produces("application/json")
    public List<Recipe> getAllRecipe() throws Exception{
        return recipeManager.getRecipes();
    }

    @GET
    @Path("/{recipe_id}")
    @Produces("application/json")
    public Recipe getRecipeById(@PathParam("recipe_id") int recipeId) throws SQLException {
        return recipeManager.getRecipeByID(recipeId);
    }

}