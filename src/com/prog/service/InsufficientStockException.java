package com.prog.service;

public class InsufficientStockException extends Exception{

    public InsufficientStockException(){
        super("Insufficient Stock");
    }
}
