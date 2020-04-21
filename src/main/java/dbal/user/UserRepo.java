package dbal.user;

import models.User;

public class UserRepo {
    public User getUserById(String id) {
        if (id.equals("1")) {
            User admin = new User();
            admin.email = "admin@admin.com";
            admin.displayname = "admin";
            admin.id = id;
            admin.username = "admin";
            return admin;
        } else
            return null;
    }
}
