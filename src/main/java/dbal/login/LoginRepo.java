package dbal.login;

import dbal.DBconnection;
import models.User;

import java.sql.*;

public class LoginRepo {
    Connection conn;

    public String getPassword(String username) {
        this.conn = DBconnection.createNewDBconnection();
        String query = "SELECT * FROM user.user WHERE username = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    System.out.println("here");
                    return rs.getString("password");
                }
            }
        } catch (SQLException e) {
            System.out.println(e);
        } catch (Exception ex){
            System.out.println(ex);
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                System.out.println(e);
            }
        }
        return "Error: retrieving password";
    }

    public User login(String username){
        this.conn = DBconnection.createNewDBconnection();
        User u = new User();
        String query = "SELECT * FROM user.user WHERE username = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    u.id = rs.getString("id");
                    u.username = rs.getString("username");
                    u.displayname = rs.getString("displayname");
                    u.email = rs.getString("email");
                    return u;
                }
            }
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                System.out.println(e);
            }
        }
        return u;
    }
}
