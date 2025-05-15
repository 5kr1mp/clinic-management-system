package com.usep.clinic.management.system.service;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Skrimp
 */
public class InvalidPasswordLengthException extends Exception {

    public InvalidPasswordLengthException() {
        super("Password should be at least 8 characters.");
    }
    
    
    
}
