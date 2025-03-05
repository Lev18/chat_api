package main.java.resource;

import main.java.model.Message;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.validation.Valid;

import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

import java.util.List;

@Path("/chat")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ChatResource {
    @Inject
    @Channel("broadcast-out")
    Emitter<String> broadcastEmmiter;

    @Inject
    @Channel("direct-out")
    Emitter<String> dirEmmiter;

    @Inject 
    @Channel("group-out")
    Emitter<String> groupEmmiter;

    @POST
    @Path("/broadcast")
    @Transactional
    public Response sendEveryone(@Valid Message message) {
        message.receiver = "all";
        message.persist();
        // log.info(message.content);
        System.out.println(message.content);
        broadcastEmmiter.send(message.content);
        return Response.accepted().build();
    }

    @POST
    @Path("/send")
    @Transactional
    public void sendDirect(Message message) {
        message.persist();
        String pload = message.receiver + ':' + message.content;
        dirEmmiter.send(pload);
    }
     @GET
     @Path("/history")
     public List<Message> getChatHistory(@QueryParam("user") String username) {
         return Message.list("sender = ?1 OR receiver = ?1 OR receiver = 'all'", username);
     }
}
