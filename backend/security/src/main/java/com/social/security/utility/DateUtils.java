package com.social.security.utility;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateUtils {
    
    public static LocalDate parseDate(String formaString) {
        LocalDate outputDate = LocalDate.parse(formaString, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        return outputDate;
    }

    public static String parseDateToString(LocalDate date) {
        String dateString = date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        return dateString;
    }
    
}
