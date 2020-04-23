package handlers.login;

import com.google.gson.Gson;
import dbal.login.LoginRepo;
import handlers.PasswordStorage;
import logging.Logger;
import models.Reply;
import models.Status;
import models.User;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class LoginHandler implements ILoginHandler {

    private LoginRepo repo;
    JSONParser jsonParser;
    Gson gson;
    PasswordStorage passwordStorage;

    public LoginHandler(LoginRepo repo) {
        this.repo = repo;
        this.jsonParser = new JSONParser();
        gson = new Gson();
        this.passwordStorage = new PasswordStorage();
    }

    public Reply login(String data) {
        try {
            Object obj = jsonParser.parse(data);
            JSONObject entity = (JSONObject) obj;
            System.out.println(entity + "entity");

            String username = (String) entity.get("username");
            System.out.println(username);
            String password = (String) entity.get("password");
            System.out.println(password);

            String storedPassword = repo.getPassword(username);
            System.out.println(storedPassword);
            if (passwordStorage.verifyPassword(password, storedPassword))
            {
                User u = repo.login(username);
                if (u != null)
                    return messageCreator(Status.OK, u);
            } else
                return messageCreator(Status.NOACCESS, new User());
        } catch (ParseException e) {
            Logger.getInstance().log(e);
            return messageCreator(Status.ERROR, new User());
        } catch (Exception ex) {
            Logger.getInstance().log(ex);
            System.out.println(ex);
            return messageCreator(Status.ERROR, new User());
        }
        return messageCreator(Status.ERROR, new User());
    }

    private Reply messageCreator(Status s, Object data) {
        System.out.println(gson.toJson(data));
        return new Reply(s, gson.toJson(data));
    }
}
