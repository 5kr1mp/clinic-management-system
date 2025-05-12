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

            String query = "INSERT INTO logs (patient_id, date_time, purpose) VALUES (?, ?, ?)";
            statement = connect.prepareStatement(query);

            statement.setInt(1, log.getId());
            statement.setTimestamp(2, Timestamp.valueOf(log.getDate()));
            statement.setString(3, log.getReason());

            statement.executeUpdate();

        } catch (SQLException e) {
            DatabaseConnection.displaySQLErrors(e);
        } finally {
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

            String query = """
            SELECT logs.id,
                logs.date_time,
                logs.purpose,
                logs.patient_id,
                patients.firstname,
                patients.lastname,
                patients.middlename,
                patients.designation,
                patients.category,
                patients.contact
            FROM logs
		    INNER JOIN patients ON logs.patient_id = patients.id;        
            """;
            statement = connect.prepareStatement(query);

            rs = statement.executeQuery();

            while (rs.next()) {

                Patient patient = new Patient(
                    rs.getInt("patient_id"),
                    rs.getString("lastname"),
                    rs.getString("firstname"),
                    rs.getString("middlename"),
                    rs.getString("designation"),
                    Category.valueOf(rs.getString("category")),
                    rs.getString("contact"),
                    null
                );

                logs.add(new Log(
                    rs.getInt("id"),
                    patient,
                    rs.getTimestamp("date_time").toLocalDateTime(),
                    rs.getString("reason")
                ));
            }
        } catch (SQLException e) {
            DatabaseConnection.displaySQLErrors(e);
        } finally {
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

            String query = """
                SELECT logs.id,
                    logs.date_time,
                    logs.purpose,
                    logs.patient_id,
                    patients.firstname,
                    patients.lastname,
                    patients.middlename,
                    patients.designation,
                    patients.category,
                    patients.contact
                FROM logs
		        INNER JOIN patients ON logs.patient_id = patients.id
                WHERE logs.id = ?        
            """;
            statement = connect.prepareStatement(query);
            statement.setInt(1, id);

            rs = statement.executeQuery();

            if (rs.next()) {

                Patient patient = new Patient(
                    rs.getInt("patient_id"),
                    rs.getString("lastname"),
                    rs.getString("firstname"),
                    rs.getString("middlename"),
                    rs.getString("designation"),
                    Category.valueOf(rs.getString("category")),
                    rs.getString("contact"),
                    null
                );

                log = new Log(
                    rs.getInt("id"),
                    patient,
                    rs.getTimestamp("date_time").toLocalDateTime(),
                    rs.getString("reason")
                );
            }
        } catch (SQLException e) {
            DatabaseConnection.displaySQLErrors(e);
        } finally {
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
            DatabaseConnection.displaySQLErrors(e);
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
