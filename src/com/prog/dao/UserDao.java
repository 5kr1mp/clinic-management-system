package com.prog.dao;

import com.prog.model.enums.Role;
import com.prog.model.*;
import com.prog.util.*;
import java.sql.*;

public class UserDao {

    public void addUser(User user, byte[] salt){
        Connection conn = null;
        PreparedStatement statement = null;

        try {
            conn = DatabaseConnection.getConnection();

            statement = conn.prepareStatement("INSERT INTO users (username,password,role,salt) VALUES (?,?,?,?)");
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getRole().toString());
            statement.setBytes(4, salt);

            statement.executeUpdate();

        } catch (SQLException e){
            DatabaseConnection.displaySQLErrors(e);
        } catch (Exception e){
            System.out.println("Error: "+ e.getMessage());
        }
        finally {
            try{
                if (statement != null){
                    statement.close();
                }

                if (conn != null){
                    conn.close();
                }
            }
            catch (Exception e){}
        }
    }

    public User getUser(String username){
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        User user = null;

        try {

            conn = DatabaseConnection.getConnection();

            statement = conn.prepareStatement("SELECT * FROM users WHERE username = ?");
            statement.setString(1, username);
            rs = statement.executeQuery();

            if (rs.next()) user = new User(
                rs.getString("name"),
                rs.getString("hashed_password"),
                Role.valueOf(rs.getString("role"))
            );

        } catch (SQLException e){
            DatabaseConnection.displaySQLErrors(e);
        } catch (Exception e){
            System.out.println("Error: "+ e.getMessage());
        }
        finally {
            try{
                if (statement != null){
                    statement.close();
                }

                if (conn != null){
                    conn.close();
                }
            }
            catch (Exception e){}
        }

        return user;
    }

    public byte[] getSalt(String username){
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        byte[] salt = null;

        try {

            conn = DatabaseConnection.getConnection();

            statement = conn.prepareStatement("SELECT salt FROM users WHERE username = ?");
            statement.setString(1, username);
            rs = statement.executeQuery();

            if (rs.next()) salt = rs.getBytes("salt");

        } catch (SQLException e){
            DatabaseConnection.displaySQLErrors(e);
        } catch (Exception e){
            System.out.println("Error: "+ e.getMessage());
        }
        finally {
            try{
                if (statement != null){
                    statement.close();
                }

                if (conn != null){
                    conn.close();
                }
            }
            catch (Exception e){}
        }

        return salt;
    }

    public void setPassword(String username,String newHashedPassword,byte[] newSalt){
        Connection conn = null;
        PreparedStatement statement = null;
        
        try {


            conn = DatabaseConnection.getConnection();
            statement = conn.prepareStatement("UPDATE users SET password = ?, salt = ? WHERE username = ?");
            statement.setString(1, newHashedPassword);
            statement.setBytes(2, newSalt);
            statement.setString(3, username);

            statement.executeUpdate();
            
        } catch (SQLException e){
            DatabaseConnection.displaySQLErrors(e);
        } catch (Exception e){
            System.out.println("Error: "+ e.getMessage());
        }
        finally {
            try{
                if (statement != null){
                    statement.close();
                }

                if (conn != null){
                    conn.close();
                }
            }
            catch (Exception e){}
        }
    }

    public void deleteUser(String username){
        Connection conn = null;
        PreparedStatement statement = null;
        
        try {
            conn = DatabaseConnection.getConnection();
            statement = conn.prepareStatement("DELETE FROM users WHERE username = ?");
            statement.setString(1, username);
            statement.executeUpdate();
            
        } catch (SQLException e){
            DatabaseConnection.displaySQLErrors(e);
        } catch (Exception e){
            System.out.println("Error: "+ e.getMessage());
        }
        finally {
            try{
                if (statement != null){
                    statement.close();
                }
                if (conn != null){
                    conn.close();
                }
            }
            catch (Exception e){}
        }
    }

}
