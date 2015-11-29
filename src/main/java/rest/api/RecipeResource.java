package rest.api;

import factory.response.RecipeResponseFactory;
import model.Recipe;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("recipe")
public class  RecipeResource {

    @GET
    @Path("/all")
    @Produces("application/json")
    public Response getAllRecipe() throws Exception{
        return RecipeResponseFactory.buildGetAllRecipeResponse();
    }

    @GET
    @Path("/{recipe_id}")
    @Produces("application/json")
    public Response getRecipeById(@PathParam("recipe_id") int recipeId) throws Exception {
        return RecipeResponseFactory.buildGetRecipeByIdResponse(recipeId);
    }

    @POST
    @Path("/")
    @Consumes("application/json")
    @Produces("application/json")
    public Response createRecipe(@HeaderParam("Authorization") String authorization, Recipe recipe) throws Exception {
        return RecipeResponseFactory.buildPostRecipeResponse(authorization, recipe);
    }

}