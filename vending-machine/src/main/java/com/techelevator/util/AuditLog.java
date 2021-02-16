package com.techelevator.util;

import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AuditLog {
    public static void log(String message) {
        try (FileOutputStream stream = new FileOutputStream("logs/audit.log", true);
                PrintWriter writer = new PrintWriter(stream)) {
            LocalDateTime timestamp = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/y hh:mm:ss a");
            writer.println(timestamp.format(formatter) + " " + message);
        } catch (Exception e) {
            throw new AuditLogException(e.getMessage());
        }
    }

    public static String saveOpenTime(){
        LocalDateTime timestamp = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/y hh:mm:ss a");
        return timestamp.format(formatter);
    }


}
