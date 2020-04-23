package controllers;

import handlers.register.IRegisterHandler;
import models.Reply;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/register")
@Produces(MediaType.APPLICATION_JSON)
public class RegisterController {
    private static IRegisterHandler handler;

    public static void setHandler(IRegisterHandler handler) {
        RegisterController.handler = handler;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response register(String data) {
        System.out.println(data);
        Reply reply = handler.register(data);
        return Response.status(reply.getStatus().getCode()).entity(reply.getMessage()).build();
    }
}
