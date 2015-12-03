package factory.response;

import manager.RecipeManager;
import model.Recipe;
import validator.UserValidator;

import javax.ws.rs.core.Response;

import static javax.ws.rs.core.Response.Status.*;

/**
 * Created by adam on 21/11/15.
 */
public class RecipeResponseFactory extends ResponseFactory {

    private static RecipeManager recipeManager = new RecipeManager();

    public static Response buildGetAllRecipeResponse() throws Exception {
        Response response;
        try {
            response = Response.status(OK).entity(buildResponseBody(CONTENT_RETRIEVAL_SUCCESS, recipeManager.getRecipes())).build();
        }
        catch (Exception exception) {
            response = Response.status(BAD_REQUEST).entity(buildResponseBody(CONTENT_RETRIEVAL_FAILURE)).build();
        }
        return response;
    }

    public static Response buildGetRecipeByIdResponse(Integer recipeId) throws Exception {
        Response response;
        try {
            response = Response.status(OK).entity(buildResponseBody(CONTENT_RETRIEVAL_SUCCESS, recipeManager.getRecipeByID(recipeId))).build();
        }
        catch (Exception exception) {
            response = Response.status(BAD_REQUEST).entity(buildResponseBody(CONTENT_RETRIEVAL_FAILURE)).build();
        }
        return response;
    }

    public static Response buildPostRecipeResponse(String authorization, Recipe recipe) throws Exception {
        Response response;
        if (!UserValidator.isValid(authorization)) {
            response = getForbiddenResponse("invalid access key [" + authorization + "]");
        }
        else {
            try {
                response = Response.status(CREATED).entity(buildResponseBody(CONTENT_CREATION_SUCCESS, recipeManager.addRecipe(recipe))).build();
            } catch (Exception exception) {
                response = Response.status(BAD_REQUEST).entity(buildResponseBody(CONTENT_CREATION_FAILURE)).build();
            }
        }
        return response;
    }

    public static Response buildPutRecipeResponse(String authorization, Integer recipeId, Recipe recipeUpdate) throws Exception {
        Response response;
        if (!UserValidator.isValid(authorization, recipeId)) {
            response = getForbiddenResponse("invalid access key [" + authorization + "] or recipe id [" + recipeId + "]");
        }
        else {
            try {
                response = Response.status(OK).entity(buildResponseBody(CONTENT_UPDATE_SUCCESS_MESSAGE, recipeManager.updateRecipe(recipeId, recipeUpdate))).build();
            } catch (Exception exception) {
                response = Response.status(BAD_REQUEST).entity(buildResponseBody(CONTENT_UPDATE_FAIL_MESSAGE, exception.getMessage())).build();
            }
        }
        return response;
    }

}
