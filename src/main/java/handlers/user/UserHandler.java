package handlers.user;

import com.google.gson.Gson;
import dbal.login.LoginRepo;
import dbal.user.UserRepo;
import logging.Logger;
import models.Reply;
import models.Status;
import models.User;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class UserHandler implements IUserHandler {

    private UserRepo repo;
    JSONParser jsonParser;
    Gson gson;

    public UserHandler(UserRepo repo) {
        this.repo = repo;
        this.jsonParser = new JSONParser();
        gson = new Gson();
    }

    public Reply getUserById(String data) {
        try {
            Object obj = jsonParser.parse(data);
            JSONObject entity = (JSONObject) obj;
            System.out.println(entity + "entity");

            String id = (String) entity.get("username");
            System.out.println(id);

            User u = repo.getUserById(id);
            if (u != null)
                return messageCreator(Status.OK, u);
            else
                return messageCreator(Status.NOAUTH, u);
        } catch (ParseException e) {
            Logger.getInstance().log(e);
            return messageCreator(Status.ERROR, new User());
        } catch (Exception ex) {
            Logger.getInstance().log(ex);
            return messageCreator(Status.ERROR, new User());
        }
    }

    private Reply messageCreator(Status s, Object data) {
        System.out.println(gson.toJson(data));
        return new Reply(s, gson.toJson(data));
    }
}
