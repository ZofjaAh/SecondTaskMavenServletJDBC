package com.aston.secondTask.service.exeptions;

public class AuthorisationException extends RuntimeException{
    public AuthorisationException(String message) {
        super(message);
    }
}
