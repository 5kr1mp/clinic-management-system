package com.prog.util;

import java.time.DayOfWeek;
import java.time.LocalDate;

public class DateRange {
    
    private LocalDate lowerDateRange;
    private LocalDate upperDateRange;

    public DateRange(LocalDate lowerDateRange, LocalDate upperDateRange) {

        if (
            lowerDateRange.isAfter(upperDateRange)
        ){
            throw new InvalidDateRangeException(InvalidDateRangeException.UPPER_DATE_PRECEDES_LOWER_DATE);
        }

        if (
            lowerDateRange.equals(upperDateRange)
        ){
            throw new InvalidDateRangeException(InvalidDateRangeException.EQUAL_DATE);
        }

        this.lowerDateRange = lowerDateRange;
        this.upperDateRange = upperDateRange;
    }

    public static DateRange ofYear(){
        return ofYear(LocalDate.now());
    }

    public static DateRange ofMonth(){
        return ofMonth(LocalDate.now());
    }

    public static DateRange ofWeek(){
        return ofWeek(LocalDate.now());
    }

    public static DateRange ofYear(LocalDate date){
        int year = date.getYear();

        return new DateRange(
            LocalDate.of(year,1,1), 
            LocalDate.of(year,12,31)
        );
    }

    public static DateRange ofMonth(LocalDate date){
        
        LocalDate lowerRange = LocalDate.of(
            date.getYear(), 
            date.getMonth(), 
            1
        );
        
        LocalDate upperRange = LocalDate.of(
            date.getYear(),
            date.getMonth(),
            date.lengthOfMonth()
        );
        
        return new DateRange(lowerRange, upperRange);

    }

    public static DateRange ofWeek(LocalDate date){

        LocalDate lowerRange,
        upperRange;

        while (true){
            if (date.getDayOfWeek() != DayOfWeek.MONDAY){

                date = date.minusDays(1);
                continue;

            }
            lowerRange = date;
            upperRange = date.plusDays(6);
            break;
        }

        return new DateRange(lowerRange, upperRange);

    }

    public boolean isWithinRange(LocalDate date){
        return (
            date.isAfter(this.lowerDateRange) || date.equals(this.lowerDateRange) &&
            date.isBefore(this.upperDateRange ) || date.equals(this.upperDateRange) 
        );
    }


    public LocalDate getLowerDateRange() {
        return lowerDateRange;
    }

    public LocalDate getUpperDateRange() {
        return upperDateRange;
    }

}