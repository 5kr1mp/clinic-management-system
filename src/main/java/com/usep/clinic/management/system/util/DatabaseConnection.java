package com.usep.clinic.management.system.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    
    private static final String URL = "jdbc:mysql://localhost:3306/clinic";
    private static final String USER = "host";
    private static final String PASSWORD = "";

    public static Connection getConnection(String url, String user, String password){
        try {
            return DriverManager.getConnection(url, user, password);
        }
        catch (SQLException e){
            System.err.println(e.getMessage());
            return null;
        }
    }

    public static Connection getConnection(){
        return getConnection(URL,USER,PASSWORD);
    }

    public static void displaySQLErrors(SQLException e){
        System.out.println("SQLException: " + e.getMessage());
        System.out.println("SQLState: " + e.getSQLState());
        System.out.println("VendorError: " + e.getErrorCode());
    }

}
