package com.usep.clinic.management.system.service;

public class InsufficientStockException extends Exception{

    public InsufficientStockException(){
        super("Insufficient Stock");
    }
}
