package utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class ConvertDate {
    private static final String DATE_PATTERN = "dd.MM.yyyy";

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_PATTERN);

    public static LocalDate convertStringToLocalDate (String birthday) {
        try {
            return DATE_FORMATTER.parse(birthday, LocalDate::from);
        } catch (DateTimeParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String convertLocalDateToString(LocalDate birthday) {
        if(birthday == null) {
            return null;
        }
        return DATE_FORMATTER.format(birthday);
    }
}
