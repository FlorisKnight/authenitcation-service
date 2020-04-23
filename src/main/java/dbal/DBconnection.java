package dbal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBconnection {
    private static String dbhost = "jdbc:mysql://nooty-user-mysql";
    private static String username = "admin";
    private static String password = "admin";
    private static Connection conn;

    @SuppressWarnings("finally")
    public static Connection createNewDBconnection() {
        try  {
            conn = DriverManager.getConnection(
                    dbhost, username, password);
            System.out.println("Created db connection");
        } catch (SQLException e) {
            System.out.println("Cannot create database connection");
            e.printStackTrace();
        } finally {
            return conn;
        }
    }
}