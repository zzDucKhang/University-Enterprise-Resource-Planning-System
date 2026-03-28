package vn.edu.ute.productmgmt.model.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public final class DateUtil {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private DateUtil() {}

    // Chuyển từ String (giao diện) sang LocalDate (Database)
    public static LocalDate parse(String dateString) {
        try {
            return LocalDate.parse(dateString, FORMATTER);
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    // Chuyển từ LocalDate sang String để hiện lên JTable
    public static String format(LocalDate date) {
        if (date == null) return "";
        return date.format(FORMATTER);
    }
}