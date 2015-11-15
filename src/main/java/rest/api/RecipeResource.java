package rest.api;

import factory.response.ResponseFactory;
import manager.RecipeManager;
import model.Recipe;
import validator.UserValidator;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
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

    @POST
    @Path("/user/{user_id}")
    @Consumes("application/json")
    public Response createRecipe(@PathParam("user_id") int userId, @HeaderParam("Authorization") String authorization, Recipe recipe) throws Exception {
        // verify user
        boolean validation = UserValidator.validate(userId, authorization);
        return ResponseFactory.buildResponse(validation, recipe);
    }

}