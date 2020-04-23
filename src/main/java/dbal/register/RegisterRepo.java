package dbal.register;

import dbal.DBconnection;
import models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RegisterRepo {
    Connection conn;

    public boolean register(String id, String username, String displayname, String email, String password) {
         this.conn = DBconnection.createNewDBconnection();
         User u = new User();
         String query = "INSERT INTO user.user (id, username, displayname, email, password) VALUES (?, ?, ?, ?, ?) ";
         try (PreparedStatement stmt = conn.prepareStatement(query)) {
             stmt.setString(1, id);
             stmt.setString(2, username);
             stmt.setString(3, displayname);
             stmt.setString(4, email);
             stmt.setString(5, password);
             stmt.executeUpdate();
             return true;
         } catch (SQLException e) {
             System.out.println(e);
         } finally {
             try {
                 conn.close();
             } catch (SQLException e) {
                 System.out.println(e);
             }
         }
        return false;
    }

    public boolean checkUnique(String column, String value) {
        this.conn = DBconnection.createNewDBconnection();
        String query = "SELECT COUNT(*) FROM user.user WHERE ? = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, column);
            stmt.setString(2, value);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    if (rs.getInt(1) == 0)
                        return true;
                    else
                        return false;
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
        return false;
    }
}
