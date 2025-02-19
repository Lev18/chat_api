package main.java.resource;

import main.java.model.AppUser;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;


@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
public class UserResource{
    @GET
    public Response getUsers() {
        List<AppUser> all_users = AppUser.listAll();
        return Response.ok(all_users).build();
    }
    @GET
    @Path("/{username}")
    // find specific user
    public Response getUser(@PathParam("username") String username) {
        AppUser user = AppUser.findByUsername(username);
        if (user == null) {
            return Response.status(Response.Status.NOT_FOUND)
                .entity("User not found")
                .build();
        }
        return Response.ok(user).build();
    }
}
