package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String DRIVER_NAME = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost/perpustakaan";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";
    private static Connection con;

    public static Connection getConnection() {
        try {
            Class.forName(DRIVER_NAME);
            try {
                con = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            } catch (SQLException se) {
                System.out.println("Failed to create the database connection");
            }
        } catch (ClassNotFoundException e) {
            System.out.println("Cannot find the Driver specified");
        }

        return con;
    }
}
