package controllers;

import handlers.login.ILoginHandler;
import models.Reply;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/login")
@Produces(MediaType.APPLICATION_JSON)
public class LoginController {

    private static ILoginHandler handler;

    public static void setHandler(ILoginHandler handler) {
        LoginController.handler = handler;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response login(String data) {
        System.out.println(data);
        Reply reply = handler.login(data);
        return Response.status(reply.getStatus().getCode()).entity(reply.getMessage()).build();
    }
}
