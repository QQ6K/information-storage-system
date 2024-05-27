package ru.task.iss.exceptions;

import java.util.Date;

public class AppError {
    private int status;
    private String message;
    private Date timestamp;

    public AppError(int status, String message)
    {
        this.status = status;
        this.message = message;
        this.timestamp = new Date();
    }

}

