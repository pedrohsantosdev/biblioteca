package com.example.biblioteca.resources.exceptions;

import com.example.biblioteca.services.exceptions.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@ControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<StandardError> recursoNaoEncontrado(ResourceNotFoundException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        String error = "Recurso não encontrado";

        StandardError stardardError = new StandardError(
                status.value(), error, e.getMessage(), Instant.now(), request.getRequestURI()
        );

        return ResponseEntity.status(status).body(stardardError);
    }
}
