package com.example.biblioteca.resources.exceptions;

import com.example.biblioteca.services.exceptions.EmprestimoJaDevolvidoException;
import com.example.biblioteca.services.exceptions.InvalidDateException;
import com.example.biblioteca.services.exceptions.LivroIndisponivelException;
import com.example.biblioteca.services.exceptions.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;
import java.util.stream.Collectors;

@ControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<StandardError> recursoNaoEncontrado(ResourceNotFoundException e, HttpServletRequest request) {

        HttpStatus status = HttpStatus.NOT_FOUND;
        String error = "Recurso não encontrado";

        StandardError err = new StandardError(
                status.value(), error, e.getMessage(), Instant.now(), request.getRequestURI()
        );

        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StandardError> entradaInvalida(MethodArgumentNotValidException e, HttpServletRequest request) {

        HttpStatus status = HttpStatus.BAD_REQUEST;
        String error = "Entrada inválida!";

        String message = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(erro -> erro.getField() + ": " + erro.getDefaultMessage())
                .collect(Collectors.joining("; "));

        StandardError err = new StandardError(
            status.value(), error, message, Instant.now(), request.getRequestURI()
        );

        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<StandardError> conflitoNoBancoDeDados(DataIntegrityViolationException e, HttpServletRequest request) {

        HttpStatus status = HttpStatus.CONFLICT;
        String error = "Conflito no banco de dados!";
        String message = "A operação viola uma restrição de integridade dos dados!";

        StandardError err = new StandardError(status.value(), error, message, Instant.now(), request.getRequestURI());

        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(InvalidDateException.class)
    public ResponseEntity<StandardError> dataInvalida(InvalidDateException e, HttpServletRequest request) {

        HttpStatus status = HttpStatus.BAD_REQUEST;
        String error = "Data inválida!";

        StandardError err = new StandardError(status.value(), error, e.getMessage(), Instant.now(), request.getRequestURI());

        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(LivroIndisponivelException.class)
    public ResponseEntity<StandardError> livroIndisponivel(LivroIndisponivelException e, HttpServletRequest request) {

        HttpStatus status = HttpStatus.CONFLICT;
        String error = "Livro indisponível no momento!";

        StandardError err = new StandardError(
                status.value(), error, e.getMessage(), Instant.now(), request.getRequestURI()
        );

        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(EmprestimoJaDevolvidoException.class)
    public ResponseEntity<StandardError> emprestimoJaDevolvido(EmprestimoJaDevolvidoException e, HttpServletRequest request) {

        HttpStatus status = HttpStatus.CONFLICT;
        String error = "Livro já devolvido referente a esse empréstimo!";

        StandardError err = new StandardError(
                status.value(), error, e.getMessage(), Instant.now(), request.getRequestURI()
        );

        return ResponseEntity.status(status).body(err);
    }

    public ResponseEntity<StandardError> impossivelDeletarEmprestimoSemDevolucao(EmprestimoJaDevolvidoException e, HttpServletRequest request) {

        HttpStatus status = HttpStatus.CONFLICT;
        String error = "Impossível deletar empréstimo sem devolução!";

        StandardError err = new StandardError(
                status.value(), error, e.getMessage(), Instant.now(), request.getRequestURI()
        );

        return ResponseEntity.status(status).body(err);
    }

}
