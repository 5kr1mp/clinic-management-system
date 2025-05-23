package com.usep.clinic.management.system;

import java.sql.Connection;
import java.sql.SQLException;

import com.usep.clinic.management.system.util.DatabaseConnection;

public class AppConfig {

    public AppConfig(){

        try (
            Connection conn = DatabaseConnection.getConnection("jdbc:mysql://localhost:3306","root","");
        ){

            conn.createStatement().execute("""
            CREATE DATABASE IF NOT EXISTS clinic;            
            """);

            conn.createStatement().execute("""
            USE clinic;
            """);

            conn.createStatement().execute("""
            CREATE TABLE IF NOT EXISTS medicines(
                id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
                name VARCHAR(30) NOT NULL,
                manufacturer VARCHAR(30) NOT NULL
            );
            """);

            conn.createStatement().execute("""
            CREATE TABLE IF NOT EXISTS medicine_batch(
                id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
                medicine_id INT NOT NULL,
                stock INT NOT NULL, 
                quantity INT NOT NULL,
                expiry_date DATE,
                stocked_date DATE,
                FOREIGN KEY(medicine_id) REFERENCES medicines(id)
            );""");

            conn.createStatement().execute("""
            CREATE TABLE IF NOT EXISTS patients(
                id INT NOT NULL PRIMARY KEY,
                firstname VARCHAR(50) NOT NULL,
                lastname VARCHAR(50) NOT NULL,
                middlename VARCHAR(50) NOT NULL,
                designation VARCHAR(20),
                category ENUM('STUDENT','FACULTY') NOT NULL,
                contact VARCHAR(30)
            );""");

            conn.createStatement().execute("""
            CREATE TABLE IF NOT EXISTS records(
                id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
                patient_id INT NOT NULL,
                date_time timestamp NOT NULL,
                description TEXT,
                diagnosis TEXT NOT NULL,
                FOREIGN KEY(patient_id) REFERENCES patients(id)
            );""");

            conn.createStatement().execute("""
            CREATE TABLE IF NOT EXISTS issued_medicines(
                id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
                record_id INT NOT NULL ,
                medicine_id INT NOT NULL,
                amount INTEGER NOT NULL,
                FOREIGN KEY(record_id) REFERENCES records(id),
                FOREIGN KEY(medicine_id) REFERENCES medicines(id)
            );""");

            conn.createStatement().execute("""
            CREATE TABLE IF NOT EXISTS logs(
                id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
                name VARCHAR(50) NOT NULL,
                designation VARCHAR(20),
                category ENUM('STUDENT','FACULTY') NOT NULL,
                purpose TEXT,
                contact VARCHAR(30),
                date_time TIMESTAMP NOT NULL
            );""");

            conn.createStatement().execute("""
            CREATE TABLE IF NOT EXISTS users (
                username VARCHAR(25) PRIMARY KEY,
                password CHAR(44) NOT NULL,
                role ENUM('ADMIN','DOCTOR','NURSE','PATIENT') NOT NULL,
                salt BINARY(16) NOT NULL
            );
            """);
            
        } catch( SQLException ex){
            DatabaseConnection.displaySQLErrors(ex);
        }

    }

}
