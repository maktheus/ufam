package urnaeletronica.BackEnd.Controllers;

import java.sql.*;


public class DataBaseController {

    //get data from gradleProperties
    private static String url = "jdbc:mysql://localhost:3306/mydatabase";
    private static String user = "myuser";
    private static String password = "mypassword";

    //connect to database
    public static Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to the MYSQL server successfully.");
            return conn;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    //disconnect from database
    public static void disconnect(Connection conn) {
        try {
            conn.close();
            System.out.println("Disconnected from the MYSQL server successfully.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public  static PreparedStatement prepareStatement( String sql) {
        try {
            Connection conn = connect();
            PreparedStatement stmt = conn.prepareStatement(sql);
            return stmt;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

}
