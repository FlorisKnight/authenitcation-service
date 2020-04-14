package dbal.login;

import models.User;

public class LoginRepo {
    public User checkLogin(String username, String password) {
        if (username.equals("admin") && password.equals("admin")) {
            User admin = new User();
            admin.email = "admin@admin.com";
            admin.displayname = "admin";
            admin.id = "1";
            admin.username = "admin";
            return admin;
        } else
            return null;
    }
}
