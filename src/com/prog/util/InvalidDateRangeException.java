package com.prog.util;

public class InvalidDateRangeException extends RuntimeException{

    static final String EQUAL_DATE = "Dates should not be equal.";
    static final String UPPER_DATE_PRECEDES_LOWER_DATE = "Lower date range should precede upper date range";

    InvalidDateRangeException(String message){

        super(message);
        
    }
    
}
