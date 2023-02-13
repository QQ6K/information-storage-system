package ru.task.iss.exceptions;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ErrorHandler {

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @ExceptionHandler({CrudException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> notAvailableException(CrudException e) {
        HashMap hashMap = new HashMap();
        hashMap.put("Ошибка:", e.getMessage());
        return hashMap;
}
}
