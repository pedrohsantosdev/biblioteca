package com.example.biblioteca.services.exceptions;

public class LivroIndisponivelException extends RuntimeException {
    public LivroIndisponivelException(String message) {
        super(message);
    }
}
