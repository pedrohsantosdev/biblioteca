package com.example.biblioteca.services.exceptions;

public class InvalidDateException extends RuntimeException {
    public InvalidDateException(String message) {
        super(message);
    }
}
