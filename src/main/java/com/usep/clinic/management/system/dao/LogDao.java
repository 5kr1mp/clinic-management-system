package com.usep.clinic.management.system.dao;

import com.usep.clinic.management.system.util.*;
import com.usep.clinic.management.system.model.*;
import com.usep.clinic.management.system.model.enums.Category;
import java.sql.*;
import java.util.*;

public class LogDao {

    public void add(Log log) {
        Connection connect = null;
        PreparedStatement statement = null;

        try {
            connect = DatabaseConnection.getConnection();

            String query = """
            INSERT INTO logs (
                patient_id,
                name,
                designation,
                category,
                purpose,
                contact,
                date_time
            )
                VALUES (?, ?, ?, ?, ?, ?, ?)
            """;
            statement = connect.prepareStatement(query);

            statement.setInt(1, log.getPatientId());
            statement.setString(2, log.getName());
            statement.setString(3, log.getDesignation());
            statement.setString(4, log.getCategory().toString());
            statement.setString(6, log.getContact());
            statement.setTimestamp(7, Timestamp.valueOf(log.getDateTime()));

            statement.executeUpdate();

        } catch (SQLException e) {
            DatabaseConnection.displaySQLErrors(e);
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                if (connect != null) {
                    connect.close();
                }
            } catch (Exception e) {}
        }
    }

    public ArrayList<Log> getAll() {
        ArrayList<Log> logs = new ArrayList<>();

        Connection connect = null;
        PreparedStatement statement = null;
        ResultSet rs = null;

        try {
            connect = DatabaseConnection.getConnection();

            // TODO : FIX QUERY
            String query = """
                SELECT *
                FROM logs
            """;
            statement = connect.prepareStatement(query);

            rs = statement.executeQuery();

            while (rs.next()) {

                logs.add(new Log(
                    rs.getInt("id"),
                    rs.getTimestamp("date_time").toLocalDateTime(),
                    rs.getString("reason"),
                    rs.getInt("patient_id"),
                    rs.getString("name"),
                    rs.getString("designation"),
                    Category.valueOf(rs.getString("category")),
                    rs.getString("contact")
                ));

            }
        } catch (SQLException e) {
            DatabaseConnection.displaySQLErrors(e);e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                if (connect != null) {
                    connect.close();
                }
            } catch (Exception e) {}
        }

        return logs;
    }

    public Log get(int id) {
        Log log = null;

        Connection connect = null;
        PreparedStatement statement = null;
        ResultSet rs = null;

        try {
            connect = DatabaseConnection.getConnection();
            // TODO : Fix query
            String query = """
                SELECT *
                FROM logs
                WHERE id = ?        
            """;
            statement = connect.prepareStatement(query);
            statement.setInt(1, id);

            rs = statement.executeQuery();

            if (rs.next()) {

                log = new Log(
                    rs.getInt("id"),
                    rs.getTimestamp("date_time").toLocalDateTime(),
                    rs.getString("reason"),
                    rs.getInt("patient_id"),
                    rs.getString("name"),
                    rs.getString("designation"),
                    Category.valueOf(rs.getString("category")),
                    rs.getString("contact")
                );
            }
        } catch (SQLException e) {
            DatabaseConnection.displaySQLErrors(e);e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                if (connect != null) {
                    connect.close();
                }
            } catch (Exception e) {}
        }

        return log;
    }

    public void delete(int id) {
        Connection conn = null;
        PreparedStatement statement = null;

        try {
            conn = DatabaseConnection.getConnection();

            String query = "DELETE FROM logs WHERE id = ?";
            statement = conn.prepareStatement(query);
            statement.setInt(1, id);

            statement.executeUpdate();

        } catch (SQLException e) {
            DatabaseConnection.displaySQLErrors(e);e.printStackTrace();
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {}
        }
    }
}
