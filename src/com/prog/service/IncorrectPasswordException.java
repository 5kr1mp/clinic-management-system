package com.prog.service;

public class IncorrectPasswordException extends Exception{

    public IncorrectPasswordException(){
        super("Incorrect Password");
    }

}
