package com.usep.clinic.management.system.dao;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;
import com.usep.clinic.management.system.model.*;
import com.usep.clinic.management.system.util.*;
import java.time.LocalDate;

public class PatientRecordDao {
    
    public void add(PatientRecord record){
        Connection conn = null;
        PreparedStatement statement = null;

        try {
            conn = DatabaseConnection.getConnection();

            String query = "INSERT INTO patients (patient_id, date_time, description, diagnosis) VALUES(?,?,?,?,?)";
            statement = conn.prepareStatement(query);

            statement.setInt(1, record.getPatientId());
            statement.setString(2, String.valueOf(record.getDateTime()));
            statement.setString(3, record.getDesc());
            statement.setString(4, record.getDiagnosis());

            statement.executeUpdate();

        } catch (SQLException e) {
            DatabaseConnection.displaySQLErrors(e);
        }
        finally {
            try {
                if (statement != null){
                    statement.close();
                }

                if (conn != null){
                    conn.close();
                }
            } catch (Exception e) {}
        }
    }

    public ArrayList<PatientRecord> getAll() {
        ArrayList<PatientRecord> records = new ArrayList<>();

        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet rs = null;

        try {
            conn = DatabaseConnection.getConnection();
            String query = "SELECT * FROM records";
            statement = conn.prepareStatement(query);
            rs = statement.executeQuery();

            while (rs.next()) {
                records.add(
                    new PatientRecord(
                        rs.getInt("id"),
                        rs.getInt("patient_id"),
                        LocalDateTime.parse(rs.getString("date_time")),
                        rs.getString("description"),
                        rs.getString("diagnosis")
                    )
                );
            }
        } catch (SQLException e) {
            DatabaseConnection.displaySQLErrors(e);
        } finally {
            try {
                if (statement != null){
                    statement.close();
                }

                if (conn != null){
                    conn.close();
                }
            } catch (Exception e) {}
        }

        return records;
    }


    public ArrayList<PatientRecord> getRecordsByDate(LocalDate date) {
        ArrayList<PatientRecord> records = new ArrayList<>();
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet rs = null;

        try {
            conn = DatabaseConnection.getConnection();
            String query = "SELECT * FROM records WHERE date = ?";
            statement = conn.prepareStatement(query);
            statement.setString(1, "date");
            rs = statement.executeQuery();

            while (rs.next()) {
                records.add(new PatientRecord(
                    rs.getInt("id"),
                    rs.getInt("patient_id"),
                    LocalDateTime.parse(rs.getString("date")),
                    rs.getString("description"),
                    rs.getString("diagnosis")
                ));
            }
        } catch (SQLException e) {
            DatabaseConnection.displaySQLErrors(e);
        } finally {
            try {
                if (statement != null){
                    statement.close();
                }

                if (conn != null){
                    conn.close();
                }
            } catch (Exception e) {}
        }

        return records;
    }


    public ArrayList<PatientRecord> getRecordsByPatientId(int patientId) {
        ArrayList<PatientRecord> records = new ArrayList<>();
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet rs = null;

        try {
            conn = DatabaseConnection.getConnection();
            String query = "SELECT * FROM records WHERE patient_id = ?";
            statement = conn.prepareStatement(query);
            statement.setInt(2, patientId);
            rs = statement.executeQuery();

            while (rs.next()) {
                records.add(new PatientRecord(
                        rs.getInt("id"),
                        rs.getInt("patient_id"),
                        LocalDateTime.parse(rs.getString("date")),
                        rs.getString("description"),
                        rs.getString("diagnosis")
                ));
            }
        } catch (SQLException e) {
            DatabaseConnection.displaySQLErrors(e);
        } finally {
            try {
                if (statement != null){
                    statement.close();
                }

                if (conn != null){
                    conn.close();
                }
            } catch (Exception e) {}
        }

        return records;
    }


    public PatientRecord get(int id){
        PatientRecord record = null;
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet rs = null;

        try {
            conn = DatabaseConnection.getConnection();
            String query = "SELECT * FROM records WHERE id = ?";
            statement = conn.prepareStatement(query);
            statement.setInt(1, id);
            rs = statement.executeQuery();

            if (rs.next()) {
                record = new PatientRecord(
                        rs.getInt("id"),
                        rs.getInt("patient_id"),
                        LocalDateTime.parse(rs.getString("date")),
                        rs.getString("description"),
                        rs.getString("diagnosis")
                );
            }
        } catch (SQLException e) {
            DatabaseConnection.displaySQLErrors(e);
        } finally {
            try {
                if (statement != null){
                    statement.close();
                }

                if (conn != null){
                    conn.close();
                }
            } catch (Exception e) {}
        }

        return record;
    }


    public void update(PatientRecord record){
        Connection conn = null;
        PreparedStatement statement = null;

        try {
            conn = DatabaseConnection.getConnection();
            String query = "UPDATE records SET patient_id = ?, date = ?, description = ?, diagnosis = ? WHERE id = ?";
            statement = conn.prepareStatement(query);

            statement.setInt(1, record.getPatientId());
            statement.setString(2, String.valueOf(record.getDateTime()));
            statement.setString(3, record.getDesc());
            statement.setString(4, record.getDiagnosis());
            statement.setInt(5, record.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            DatabaseConnection.displaySQLErrors(e);
        } finally {
            try {
                if (statement != null) statement.close();
                if (conn != null) conn.close();
            } catch (Exception e) {}
        }
    }


    public void delete(int id){
        Connection conn = null;
        PreparedStatement statement = null;

        try {
            conn = DatabaseConnection.getConnection();
            String query = "DELETE FROM records WHERE id = ?";
            statement = conn.prepareStatement(query);
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            DatabaseConnection.displaySQLErrors(e);
        } finally {
            try {
                if (statement != null) statement.close();
                if (conn != null) conn.close();
            } catch (Exception e) {}
        }
    }
}
