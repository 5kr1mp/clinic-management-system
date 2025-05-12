package com.usep.clinic.management.system.util;

public class InvalidDateRangeException extends RuntimeException{

    public static final String EQUAL_DATE = "Dates should not be equal.";
    public static final String UPPER_DATE_PRECEDES_LOWER_DATE = "Lower date range should precede upper date range";

    public InvalidDateRangeException(String message){

        super(message);
        
    }
    
}
