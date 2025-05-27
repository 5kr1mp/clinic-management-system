package com.usep.clinic.management.system.dao;

import java.sql.*;
import java.util.ArrayList;

import com.usep.clinic.management.system.model.*;
import com.usep.clinic.management.system.model.enums.*;
import com.usep.clinic.management.system.util.DatabaseConnection;

public class PatientDao {

    public void add(Patient patient){
        Connection conn = null;
        PreparedStatement statement = null;

        try {
            conn = DatabaseConnection.getConnection();

            String query = "INSERT INTO patients (id, firstname, lastname, middlename, designation, category, contact) VALUES(?,?,?,?,?,?,?)";
            statement = conn.prepareStatement(query);

            statement.setInt(1, patient.getId());
            statement.setString(2, patient.getFirstname());
            statement.setString(3, patient.getLastname());
            statement.setString(4, patient.getMiddlename());
            statement.setString(5, patient.getDesignation());
            statement.setString(6, String.valueOf(patient.getCategory()));
            statement.setString(7, patient.getContact());
            
            statement.executeUpdate();

        } catch (SQLException e) {
            DatabaseConnection.displaySQLErrors(e);
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }finally {
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

    public ArrayList<Patient> getAll(){
        ArrayList<Patient> patients = new ArrayList<>();

        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet rs = null;

        try {
            conn = DatabaseConnection.getConnection();

            String query = "SELECT * FROM patients";
            statement = conn.prepareStatement(query);

            rs = statement.executeQuery();

            while (rs.next()) {
                patients.add(
                        new Patient (
                                rs.getInt("id"),
                                rs.getString("firstname"),
                                rs.getString("lastname"),
                                rs.getString("middlename"),
                                rs.getString("designation"),
                                Category.valueOf(rs.getString("category")),
                                rs.getString("contact"),
                               null
                        )
                );
            }
        } catch (SQLException e) {
            DatabaseConnection.displaySQLErrors(e);
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                if (statement != null){
                    statement.close();
                }

                if (conn != null){
                    conn.close();
                }
            } catch (Exception e) {}
        }
        return patients;
    }

    public ArrayList<Patient> getPatientsByName(String name){

        ArrayList<Patient> patients = new ArrayList<>();

        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet rs = null;

        try {
            conn = DatabaseConnection.getConnection();

            name = "%"+ name + "%";

            String query = """
            SELECT * FROM patients 
                WHERE firstname LIKE ? OR
                WHERE lastname LIKE ? OR
                WHERE middlename LIKE ?
            """;
            statement = conn.prepareStatement(query);

            statement.setString(1, name);
            statement.setString(2, name);
            statement.setString(3, name);
            rs = statement.executeQuery();

            while (rs.next()) {
                patients.add(
                        new Patient(
                            rs.getInt("id"),
                            rs.getString("firstname"),
                            rs.getString("lastname"),
                            rs.getString("middlename"),
                            rs.getString("designation"),
                            Category.valueOf(rs.getString("category")),
                            rs.getString("contact"),
                            null
                        )
                );
            }
        } catch (SQLException e) {
            DatabaseConnection.displaySQLErrors(e);
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                if (statement != null){
                    statement.close();
                }

                if (conn != null){
                    conn.close();
                }
            } catch (Exception e) {}
        }
      return patients;
    }

    public ArrayList<Patient> getFacultyPatients(){
        ArrayList<Patient> patients = new ArrayList<>();

        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet rs = null;

        try {
            conn = DatabaseConnection.getConnection();

            String query = "SELECT * FROM patients WHERE category = ?";
            statement = conn.prepareStatement(query);

            statement.setString(6, "FACULTY");
            rs = statement.executeQuery();

            while (rs.next()) {

                patients.add(
                    new Patient(
                        rs.getInt("patient_id"),
                        rs.getString("lastname"),
                        rs.getString("firstname"),
                        rs.getString("middlename"),
                        rs.getString("designation"),
                        Category.valueOf(rs.getString("category")),
                        rs.getString("contact"),
                        null
                    )
                );

            }

        } catch (SQLException e) {
            DatabaseConnection.displaySQLErrors(e);
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                if (statement != null){
                    statement.close();
                }

                if (conn != null){
                    conn.close();
                }
            } catch (Exception e) {}
        }
        return patients;
    }

    public ArrayList<Patient> getStudentPatients(){
        ArrayList<Patient> patients = new ArrayList<>();

        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet rs = null;

        try {
            conn = DatabaseConnection.getConnection();

            String query = "SELECT * FROM patients WHERE category = ?";
            statement = conn.prepareStatement(query);

            statement.setString(6, "STUDENT");
            rs = statement.executeQuery();

            while (rs.next()) {

                patients.add(
                    new Patient(
                        rs.getInt("patient_id"),
                        rs.getString("lastname"),
                        rs.getString("firstname"),
                        rs.getString("middlename"),
                        rs.getString("designation"),
                        Category.valueOf(rs.getString("category")),
                        rs.getString("contact"),
                        null
                    )
                );

            }

        } catch (SQLException e) {
            DatabaseConnection.displaySQLErrors(e);
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                if (statement != null){
                    statement.close();
                }

                if (conn != null){
                    conn.close();
                }
            } catch (Exception e) {}
        }
        return patients;
    }

    public Patient get(int id) {
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        Patient patient = null;

        try {
            conn = DatabaseConnection.getConnection();

            String query = "SELECT * FROM patients WHERE id = ?";
            statement = conn.prepareStatement(query);
            statement.setInt(1, id);

            rs = statement.executeQuery();

            if (rs.next()) {
                patient = new Patient(
                        rs.getInt("id"),
                        rs.getString("firstname"),
                        rs.getString("lastname"),
                        rs.getString("middlename"),
                        rs.getString("designation"),
                        Category.valueOf(rs.getString("category")),
                        rs.getString("contact"),
                        null
                );
            }
        } catch (SQLException e) {
            DatabaseConnection.displaySQLErrors(e);
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                if (rs != null) rs.close();
                if (statement != null) statement.close();
                if (conn != null) conn.close();
            } catch (Exception e) {}
        }

        return patient;
    }

    public void update(Patient patient) {
        Connection conn = null;
        PreparedStatement statement = null;

        try {
            conn = DatabaseConnection.getConnection();

            String query = "UPDATE patients SET firstname = ?, lastname = ?, middlename = ?, designation = ?, category = ?, contact = ? WHERE id = ?";
            statement = conn.prepareStatement(query);

            statement.setString(1, patient.getFirstname());
            statement.setString(2, patient.getLastname());
            statement.setString(3, patient.getMiddlename());
            statement.setString(4, patient.getDesignation());
            statement.setString(5, patient.getCategory().toString());
            statement.setString(6, patient.getContact());
            statement.setInt(7, patient.getId());

            statement.executeUpdate();

        } catch (SQLException e) {
            DatabaseConnection.displaySQLErrors(e);
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
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


    /**
     * Removes a patient object from {@link PatientDao#patients}
     * 
     * @param id of the patient to be removed
     */
    public void delete(int id){
        Connection conn = null;
        PreparedStatement statement = null;

        try{
            conn = DatabaseConnection.getConnection();

            String query = "DELETE FROM patients WHERE id = ?";
            statement = conn.prepareStatement(query);

            statement.setInt(1, id);

            statement.executeUpdate();

        } catch (SQLException e){
            DatabaseConnection.displaySQLErrors(e);
            e.printStackTrace();
        }  catch (Exception e){
            e.printStackTrace();
        }finally {
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
