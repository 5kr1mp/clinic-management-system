package com.usep.clinic.management.system.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import com.usep.clinic.management.system.model.*;
import com.usep.clinic.management.system.util.DatabaseConnection;

/**
 * Changes:
 * <ul>
 *  <li>changed batch.getBatchId() to batch.getId() </li>
 * </ul>
 */

public class MedicineBatchDao {

    public void add(MedicineBatch batch){
        Connection conn = null;
        PreparedStatement statement = null;

        try {
            conn = DatabaseConnection.getConnection();
            String query = "INSERT INTO issued_medicines (medicine_id, stock," +
                    "quantity, expiry_date, stock_date) VALUES(?, ?, ?, ?, ?)";
            statement = conn.prepareStatement(query);

            statement.setInt(1, batch.getMedicineId());
            statement.setInt(2, batch.getStock());
            statement.setInt(3, batch.getQuantity());
            statement.setDate(4, Date.valueOf(batch.getExpiryDate()));
            statement.setDate(5, Date.valueOf(batch.getStockedDate()));
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

    public ArrayList<MedicineBatch> getAll(){
        ArrayList<MedicineBatch> medicineBatch = new ArrayList<>();
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet rs = null;

        try {
            conn = DatabaseConnection.getConnection();
            String query = "SELECT * FROM medicine_batch";
            statement = conn.prepareStatement(query);
            rs = statement.executeQuery();

            while (rs.next()){
                medicineBatch.add(
                        new MedicineBatch(
                                rs.getInt("batch_id"),
                                rs.getInt("medicine_id"),
                                rs.getInt("stock"),
                                rs.getInt("quantity"),
                                rs.getDate("expiry_date").toLocalDate(),
                                rs.getDate("stock_date").toLocalDate()
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
        return medicineBatch;
    }

    public MedicineBatch get(int id){
        MedicineBatch medicineBatch = null;
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet rs = null;

        try {
            conn = DatabaseConnection.getConnection();
            String query = "SELECT * FROM medicine_batch WHERE batch_id = ?";
            statement = conn.prepareStatement(query);
            statement.setInt(1, id);
            rs = statement.executeQuery();
            rs.next();

            medicineBatch = new MedicineBatch(
                rs.getInt("batch_id"),
                rs.getInt("medicine_id"),
                rs.getInt("stock"),
                rs.getInt("quantity"),
                rs.getDate("expiry_date").toLocalDate(),
                rs.getDate("stock_date").toLocalDate()
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
        return medicineBatch;
    }

    public ArrayList<MedicineBatch> getMedicineBatchesByMedicineId(int medId){
        ArrayList<MedicineBatch> medicineBatch = new ArrayList<>();

        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet rs = null;

        try {
            conn = DatabaseConnection.getConnection();
            String query = "SELECT * FROM medicine_batch WHERE medicine_id = ?";
            statement = conn.prepareStatement(query);
            statement.setInt(1, medId);
            rs = statement.executeQuery();

            while (rs.next()){
                medicineBatch.add(
                        new MedicineBatch(
                                rs.getInt("batch_id"),
                                rs.getInt("medicine_id"),
                                rs.getInt("stock"),
                                rs.getInt("quantity"),
                                rs.getDate("expiry_date").toLocalDate(),
                                rs.getDate("stock_date").toLocalDate()
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
        return medicineBatch;
    }

    public void update(MedicineBatch batch){
        Connection conn = null;
        PreparedStatement statement = null;

        try {
            conn = DatabaseConnection.getConnection();
            String query = "UPDATE medicine_batch SET medicine_id = ?, stock = ?, quantity = ?" +
                    "expiry_date = ?, stock_date = ? WHERE id = ?";
            statement = conn.prepareStatement(query);
            statement.setInt(1, batch.getMedicineId());
            statement.setInt(2, batch.getStock());
            statement.setInt(3, batch.getQuantity());
            statement.setDate(4, Date.valueOf(batch.getExpiryDate()));
            statement.setDate(5, Date.valueOf(batch.getStockedDate()));
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
            String query = "DELETE FROM medicine_batch WHERE id = ?";
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
