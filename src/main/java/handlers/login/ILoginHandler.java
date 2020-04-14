package handlers.login;

import models.Reply;

public interface ILoginHandler {
    Reply login(String data);
}
