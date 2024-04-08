package ru.task.iss.exceptions;

import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;

public class AuthException extends AuthenticationCredentialsNotFoundException {

    public AuthException(String msg) {
        super(msg);
    }
}
