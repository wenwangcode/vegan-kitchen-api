package factory.response;

import exception.DatabaseException;
import manager.RecipeManager;
import model.Recipe;

import javax.ws.rs.core.Response;
import java.util.List;

import static javax.ws.rs.core.Response.Status.*;

/**
 * Created by adam on 21/11/15.
 */
public class RecipeResponseFactory extends ResponseFactory {

    private static RecipeManager recipeManager = new RecipeManager();

    public static Response buildGetAllRecipeResponse() throws Exception {
        Response response;
        try {
            List<Recipe> recipeList = recipeManager.getRecipes();
            response = Response.status(OK).entity(buildResponseBody(CONTENT_RETRIEVAL_SUCCESS, recipeList)).build();
        }
        catch (DatabaseException exception) {
            response = Response.status(BAD_REQUEST).entity(buildResponseBody(CONTENT_RETRIEVAL_FAILURE, exception.getMessage())).build();
        }
        return response;
    }

    public static Response buildGetRecipeByIdResponse(Integer recipeId) throws Exception {
        Response response;
        try {
            Recipe recipe = recipeManager.getRecipeById(recipeId);
            response = Response.status(OK).entity(buildResponseBody(CONTENT_RETRIEVAL_SUCCESS, recipe)).build();
        }
        catch (DatabaseException exception) {
            response = Response.status(BAD_REQUEST).entity(buildResponseBody(CONTENT_RETRIEVAL_FAILURE, exception.getMessage())).build();
        }
        return response;
    }

    public static Response buildPostRecipeResponse(Recipe recipe) throws Exception {
        Response response;
        try {
            Recipe createdRecipe = recipeManager.addRecipe(recipe);
            response = Response.status(CREATED).entity(buildResponseBody(CONTENT_CREATION_SUCCESS, createdRecipe)).build();
        } catch (DatabaseException exception) {
            response = Response.status(BAD_REQUEST).entity(buildResponseBody(CONTENT_CREATION_FAILURE, exception.getMessage())).build();
        }
        return response;
    }

    public static Response buildPutRecipeResponse(Integer recipeId, Recipe recipeUpdate) throws Exception {
        Response response;
        try {
            Recipe updatedRecipe = recipeManager.updateRecipe(recipeId, recipeUpdate);
            response = Response.status(OK).entity(buildResponseBody(CONTENT_UPDATE_SUCCESS_MESSAGE, updatedRecipe)).build();
        } catch (DatabaseException exception) {
            response = Response.status(BAD_REQUEST).entity(buildResponseBody(CONTENT_UPDATE_FAIL_MESSAGE, exception.getMessage())).build();
        }
        return response;
    }

    public static Response buildDeleteRecipeResponse(Integer recipeId) throws Exception {
        return null; // TODO
    }

}
