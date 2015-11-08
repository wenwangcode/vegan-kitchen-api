package net.gazsi.laszlo.sandbox.ws;

import model.Recipe;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

@Path("hello")
public class RestExample {

    @GET
    @Path("/adam")
    @Produces("application/json")
    public Recipe sayHello(){
        return new Recipe("WENdfdgDY", 12);
    }

//    @GET
//    @Path("adam/{id}")
//    public String sayAdam(@PathParam("id") int id) {
//        return "Adam has id " + id;
//    }

}