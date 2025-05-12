package com.usep.clinic.management.system.service;

public class InvalidAmountException extends Exception{

    public InvalidAmountException(){
        super("Invalid amount.");
    }
}
