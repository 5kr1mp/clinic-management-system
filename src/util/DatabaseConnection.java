package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Optional;

public class DatabaseConnection {
    
    private final String URL = "jdbc:mysql://localhost:3306/clinic";
    private final String USER = "host";
    private final String PASSWORD = "arst1234";

    public Connection getConnection(String url, String user, String password){
        try {
            return DriverManager.getConnection(url, user, password);
        }
        catch (SQLException e){
            System.err.println(e.getMessage());
            return null;
        }
    }

    public Connection getConnection(){
        return getConnection(URL,USER,PASSWORD);
    }


}
