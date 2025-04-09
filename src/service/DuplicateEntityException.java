package service;

public class DuplicateEntityException extends Exception {

    DuplicateEntityException(String message){
        super(message);
    }
    
}
