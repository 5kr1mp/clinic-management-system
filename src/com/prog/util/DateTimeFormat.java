package com.prog.util;

import java.time.*;
import java.time.format.*;

@Deprecated
public class DateTimeFormat {
    
    static DateTimeFormatter dateTimeFormatter = new DateTimeFormatterBuilder()
                                                    .parseCaseInsensitive()
                                                    .appendPattern("MM-dd-yyyy h:mm a")
                                                    .toFormatter();

    static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");

     /**
     * Converts {@code String} of pattern (MM-dd-yyyy) into a {@code LocalDate} object
     * @param dateString String to parse into a LocalDate object
     * @return {@code LocalDate} object derived from {@code dateString}
     */
    public static LocalDate parseDate(String dateString){
        return LocalDate.parse(dateString,dateFormatter);
    }

    /**
     * Converts {@code LocalDate} objects into {@code String}
     * @param dateTime object to format
     * @return {@code String} format of LocalDate object
     */
    public static String formatDate(LocalDate date){
        return date.format(dateFormatter);
    }

    public static LocalDateTime parseDateTime(String dateTime){
        return LocalDateTime.parse(dateTime, dateTimeFormatter);
    }

    /**
     * Converts {@code LocalDateTime} objects into {@code String}
     * @param dateTime object to format
     * @return {@code String} format of LocalDateTime object
     */
    public static String formatDateTime(LocalDateTime dateTime){
        return dateTime.format(dateTimeFormatter);
    }
}
