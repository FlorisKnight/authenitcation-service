package handlers.register;

import com.google.gson.Gson;
import dbal.login.LoginRepo;
import dbal.register.RegisterRepo;
import handlers.PasswordStorage;
import logging.Logger;
import models.Reply;
import models.Status;
import models.User;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.rmi.server.UID;
import java.util.UUID;

public class RegisterHandler implements IRegisterHandler{
    private RegisterRepo repo;
    JSONParser jsonParser;
    Gson gson;
    PasswordStorage passwordStorage;

    public RegisterHandler(RegisterRepo repo) {
        this.repo = repo;
        this.jsonParser = new JSONParser();
        gson = new Gson();
        this.passwordStorage = new PasswordStorage();
    }

    @Override
    public Reply register(String data) {
        try {
            Object obj = jsonParser.parse(data);
            JSONObject entity = (JSONObject) obj;
            System.out.println(entity + "entity");

            String username = (String) entity.get("username");
            String displayname = (String) entity.get("displayname");
            String email = (String) entity.get("email");
            String password = (String) entity.get("password");

            if (repo.checkUnique("username", username) && repo.checkUnique("email", email)) {
                String id = getId();
                String hashedPassword = passwordHasher(password);
            if (repo.register(id, username, displayname, email, hashedPassword)) {
                User u = new User();
                u.id = id;
                u.username = username;
                u.displayname = displayname;
                u.email = email;
                return messageCreator(Status.OK, u);
                }
            }
            return messageCreator(Status.ERROR, new User());
        } catch (ParseException e) {
            Logger.getInstance().log(e);
            return messageCreator(Status.ERROR, new User());
        } catch (Exception ex) {
            Logger.getInstance().log(ex);
            return messageCreator(Status.ERROR, new User());
        }
    }

    private String getId() {
        return UUID.randomUUID().toString();
    }

    private String passwordHasher(String pass){
        try {
            return passwordStorage.createHash(pass);
        } catch (PasswordStorage.CannotPerformOperationException e) {
            e.printStackTrace();
        }
        return pass;
    }

    private Reply messageCreator(Status s, Object data) {
        System.out.println(gson.toJson(data));
        return new Reply(s, gson.toJson(data));
    }
}
