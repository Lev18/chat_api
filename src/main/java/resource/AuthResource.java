package main.java.resource;

import main.java.model.AppUser;
import main.java.util.PasswordUtil;
import io.smallrye.jwt.build.Jwt;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.HashSet;
import java.util.Set;
import java.time.Duration;

@Path("/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthResource {
    @ConfigProperty(name = "jwr.issuer")
    String issuer;

    @POST
    @Path("/register")
    @Transactional
    public Response register(AppUser user) {
        if(user.find("username", user.username)
                .firstResultOptional()
                .isPresent()) {
            return Response.status(Response.Status.CONFLICT).build();
            }
        user.password = PasswordUtil.hashPasswd(user.password);
        user.persist();
        return Response.ok().build();
    }

    @POST
    @Path("/login")
    public Response login(AppUser logReq) {
        AppUser user = AppUser.find("username", logReq.username).firstResult();
        if (user == null) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        if (!PasswordUtil.checkPasswd(logReq.password, user.password)) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        Set<String> roleSet = (user.roles != null) ? new HashSet<>(user.roles) : new HashSet<>();
        String tok = Jwt.issuer(issuer)
            .upn(user.username)
            .groups(new HashSet<String>(roleSet))
            .expiresIn(Duration.ofHours(1))
            .sign();
        return Response.ok().entity("{\"token\":\"" + tok + "\"}").build();
    }
}
