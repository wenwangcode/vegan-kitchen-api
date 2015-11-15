package net.gazsi.laszlo.sandbox.ws;

import database.utility.Access;
import database.utility.AccessManager;
import database.utility.ConnectionFactory;
import model.Recipe;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.sql.Connection;

@Path("recipe")
public class RestExample {


    @GET
    @Path("/sql")
    @Produces("application/json")
    public Recipe sayHello() throws Exception{
        Connection connection = ConnectionFactory.getConnection();
        Access access = new Access();
        return access.getRecipes(connection).get(0);
    }



}