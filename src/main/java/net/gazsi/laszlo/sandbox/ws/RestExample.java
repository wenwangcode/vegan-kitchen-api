package net.gazsi.laszlo.sandbox.ws;

import model.Recipe;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

@Path("recipe")
public class RestExample {


    @GET
    @Path("{id}")
    @Produces("application/json")
    public Recipe sayHello(@PathParam("id") int id){
       // RecipeManager manager = new MockRecipeManager();
        //return manager.getRecipeByID(id);
        return new Recipe("Dishname", 123, "imageURL", 1, "String summary");

    }



}