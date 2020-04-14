package handlers.register;

import com.google.gson.Gson;
import dbal.register.RegisterRepo;

public class RegisterHandler implements IRegisterHandler{
    private RegisterRepo repo;
    private Gson gson = new Gson();

    public RegisterHandler(RegisterRepo repo) {
        this.repo = repo;
        this.gson = new Gson();
    }
}
