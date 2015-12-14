package rest.api;

import application.filter.Authentication;
import factory.response.RecipeResponseFactory;
import model.Recipe;

import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("recipe")
@Singleton
public class  RecipeResource {

    @GET
    @Path("all")
    @Produces("application/json")
    public Response getAllRecipe() throws Exception{
        return RecipeResponseFactory.buildGetAllRecipeResponse();
    }

    @GET
    @Path("{recipe_id}")
    @Produces("application/json")
    public Response getRecipeById(@PathParam("recipe_id") int recipeId) throws Exception {
        return RecipeResponseFactory.buildGetRecipeByIdResponse(recipeId);
    }

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    @Authentication
    public Response createRecipe(Recipe recipe) throws Exception {
        return RecipeResponseFactory.buildPostRecipeResponse(recipe);
    }

    @PUT
    @Path("{recipe_id}")
    @Consumes("application/json")
    @Produces("application/json")
    @Authentication
    public Response putRecipe(@PathParam("recipe_id") Integer recipeId, Recipe recipeUpdate) throws Exception {
        return RecipeResponseFactory.buildPutRecipeResponse(recipeId, recipeUpdate);
    }

    @DELETE
    @Path("{recipe_id}")
    @Authentication
    public Response deleteRecipe(@PathParam("recipe_id") Integer recipeId) {
        return null; // TODO
    }

}