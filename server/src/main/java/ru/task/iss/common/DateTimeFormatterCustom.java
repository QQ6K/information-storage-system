package ru.task.iss.common;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
public class DateTimeFormatterCustom {
        public static String formatLocalDateTime(LocalDateTime dateTime) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH.ss");
            return dateTime.format(formatter);
        }
}
