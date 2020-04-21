package controllers;

import handlers.login.ILoginHandler;
import handlers.user.IUserHandler;
import models.Reply;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
public class UserController {

    private static IUserHandler handler;

    public static void setHandler(IUserHandler handler) {
        UserController.handler = handler;
    }

    @POST
    @Path("/getUserById")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getUserById(String data) {
        System.out.println(data);
        Reply reply = handler.getUserById(data);
        return Response.status(reply.getStatus().getCode()).entity(reply.getMessage()).build();
    }
}
