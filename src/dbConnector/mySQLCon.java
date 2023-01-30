package dbConnector;

import java.sql.Connection;
import java.sql.DriverManager;
public class mySQLCon {
    static Connection conn = null;
    public static Connection getConnection() {
        if (conn != null) return conn;
        String dbName = "bank";
        String Username = "root";
        String password = "@ariyan77";
        return getConnection(dbName, Username, password);
    }
    private static Connection getConnection(String databaseName, String UserName, String password) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost/" + databaseName + "?user=" + UserName + "&password=" + password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }
}
