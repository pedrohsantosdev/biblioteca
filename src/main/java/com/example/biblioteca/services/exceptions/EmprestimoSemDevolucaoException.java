package com.example.biblioteca.services.exceptions;

public class EmprestimoSemDevolucaoException extends RuntimeException {
    public EmprestimoSemDevolucaoException(String message) {
        super(message);
    }
}
