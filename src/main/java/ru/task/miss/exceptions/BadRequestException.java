package ru.task.miss.exceptions;

import org.springframework.core.MethodParameter;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;

public class BadRequestException extends MethodArgumentNotValidException {
    String param;
    String value;

    public BadRequestException(MethodParameter parameter, BindingResult bindingResult) {
        super(parameter, bindingResult);
    }

    public String getParam() {
        return param;
    }

    public String getValue() {
        return value;
    }
}
