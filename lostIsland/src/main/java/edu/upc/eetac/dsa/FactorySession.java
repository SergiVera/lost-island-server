package edu.upc.eetac.dsa;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.ResourceBundle;

public class FactorySession {
    public static Session openSession() {


        Connection conn = getConnection();

        Session session = new SessionImpl(conn);

        return session;
    }



    private static Connection getConnection() {
        String namedb = getString("mysql", "namedb");
        String username = getString("mysql", "username");
        String password = getString("mysql", "password");

        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost/" + namedb + "?user=" + username + "&password=" + password +"");

        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        return conn;
    }

    private static String getString (String filename, String key){
        HashMap<String, ResourceBundle> mysql = new HashMap<>();

        ResourceBundle rbundle = mysql.get(filename);

        if (rbundle == null){
            rbundle = ResourceBundle.getBundle(filename);
            mysql.put(filename, rbundle);
        }

        return rbundle.getString(key);
    }
}
