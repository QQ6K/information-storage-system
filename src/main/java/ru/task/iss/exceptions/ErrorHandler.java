package ru.task.iss.exceptions;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
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
        Map map = new HashMap();
        map.put("error", e.getMessage());
        return map;
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> notAvailableException(MethodArgumentNotValidException e) {
        Map map = new HashMap();
        map.put("error", e.getFieldError().getDefaultMessage());
        return map;
    }

    @ExceptionHandler({InvalidFormatException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> InvalidFormatException(InvalidFormatException e) {
        Map map = new HashMap();
        map.put("error", "Неверный формат данных");
        return map;
    }

}
