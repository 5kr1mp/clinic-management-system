package com.prog.dao;

import java.sql.*;
import java.util.*;

import com.prog.model.Medicine;
import com.prog.util.DatabaseConnection;

public class MedicineDao {
    

    public void add(Medicine medicine){
        Connection conn = null;
        PreparedStatement statement = null;

        try {
            conn = DatabaseConnection.getConnection();
            String query = "INSERT INTO medicines (name, manufacturer) VALUES(?, ?)";
            statement = conn.prepareStatement(query);

            statement.setString(1, medicine.getName());
            statement.setString(2, medicine.getManufacturer());
            statement.executeUpdate();
        } catch (SQLException e){
            DatabaseConnection.displaySQLErrors(e);
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }

                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
            }
        }

    }

    public ArrayList<Medicine> getAll(){
        ArrayList<Medicine> medicines = new ArrayList<>();
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet rs = null;

        try {
            conn = DatabaseConnection.getConnection();
            String query = "SELECT * FROM medicines";
            statement = conn.prepareStatement(query);
            rs = statement.executeQuery();

            while (rs.next()){
                medicines.add(
                        new Medicine(
                                rs.getInt("medicine_id"),
                                rs.getString("name"),
                                rs.getString("manufacturer")
                        )
                );
            }
        } catch (SQLException e){
            DatabaseConnection.displaySQLErrors(e);
        } finally {
            try{
                if (statement != null){
                    statement.close();
                }

                if (conn != null){
                    conn.close();
                }
            }
            catch (Exception e){
            }
        }
        return medicines;

    }

    public Medicine get(int id){
        Medicine medicine = null;
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet rs = null;

        try {
            conn = DatabaseConnection.getConnection();
            String query = "SELECT * FROM medicines WHERE medicine_id = ?";
            statement = conn.prepareStatement(query);
            statement.setInt(1, id);
            rs = statement.executeQuery();
            rs.next();

            medicine = new Medicine(
                            rs.getInt("medicine_id"),
                            rs.getString("name"),
                            rs.getString("manufacturer")
            );
        } catch (SQLException e){
            DatabaseConnection.displaySQLErrors(e);
        } finally {
            try{
                if (statement != null){
                    statement.close();
                }

                if (conn != null){
                    conn.close();
                }
            }
            catch (Exception e){
            }
        }
        return medicine;
    }

    public ArrayList<Medicine> getMedicinesByName(String name){
        ArrayList<Medicine> medicines = new ArrayList<>();
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet rs = null;

        try {
            conn = DatabaseConnection.getConnection();
            String query = "SELECT * FROM medicines WHERE name = ?";
            statement = conn.prepareStatement(query);
            statement.setString(1, name);
            rs = statement.executeQuery();

            while (rs.next()){
                medicines.add(
                        new Medicine(
                                rs.getInt("medicine_id"),
                                rs.getString("name"),
                                rs.getString("manufacturer")
                        )
                );
            }
        } catch (SQLException e) {
            DatabaseConnection.displaySQLErrors(e);
        } finally {
            try{
                if (statement != null){
                    statement.close();
                }
                if (conn != null){
                    conn.close();
                }
            }
            catch (Exception e){
            }
        }
        return medicines;
    }

    public void update(Medicine medicine){
        Connection conn = null;
        PreparedStatement statement = null;

        try {
            conn = DatabaseConnection.getConnection();
            String query = "UPDATE medicines SET medicine_id = ?, manufacturer = ? WHERE name = ?";
            statement = conn.prepareStatement(query);
            statement.setInt(1, medicine.getId());
            statement.setString(2, medicine.getManufacturer());
            statement.executeUpdate();
        } catch (SQLException e){
            DatabaseConnection.displaySQLErrors(e);
        } finally {
            try{
                if (statement != null){
                    statement.close();
                }
                if (conn != null){
                    conn.close();
                }
            }
            catch (Exception e){
            }
        }

    }

    public void delete(int id){
        Connection conn = null;
        PreparedStatement statement = null;

        try {
            conn = DatabaseConnection.getConnection();
            String query = "DELETE FROM medicines WHERE id = ?";
            statement = conn.prepareStatement(query);
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e){
            DatabaseConnection.displaySQLErrors(e);
        } finally {
            try{
                if (statement != null){
                    statement.close();
                }
                if (conn != null){
                    conn.close();
                }
            } catch (Exception e){
            }
        }
    }
}
