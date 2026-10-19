package com.example.biblioteca.resources.exceptions;

import com.example.biblioteca.services.exceptions.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

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

    @ExceptionHandler(EmprestimoSemDevolucaoException.class)
    public ResponseEntity<StandardError> impossivelDeletarEmprestimoSemDevolucao(EmprestimoSemDevolucaoException e, HttpServletRequest request) {

        HttpStatus status = HttpStatus.CONFLICT;
        String error = "Impossível deletar empréstimo sem devolução!";

        StandardError err = new StandardError(
                status.value(), error, e.getMessage(), Instant.now(), request.getRequestURI()
        );

        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<StandardError> corpoInvalido(HttpMessageNotReadableException e, HttpServletRequest request) {

        HttpStatus status = HttpStatus.BAD_REQUEST;
        String error = "Corpo da requisição inválido!";
        String message = "Envie um JSON válido, com os tipos dos campos "
                + "corretos e as datas no formato yyyy-MM-dd.";

        StandardError err = new StandardError(status.value(), error, message, Instant.now(), request.getRequestURI());

        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<StandardError> requisicaoInvalida(MethodArgumentTypeMismatchException e, HttpServletRequest request) {

        HttpStatus status = HttpStatus.BAD_REQUEST;
        String error = "Requisição inválida!";
        String message = "Valor inválido para o parâmetro '" + e.getName() + "'.";

        StandardError err = new StandardError(status.value(), error, message, Instant.now(), request.getRequestURI());

        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(EstoqueInsuficienteException.class)
    public ResponseEntity<StandardError> estoqueInsuficiente(EstoqueInsuficienteException e, HttpServletRequest request) {

        HttpStatus status = HttpStatus.CONFLICT;
        String error = "Estoque insuficiente";

        StandardError err = new StandardError(
                status.value(), error, e.getMessage(), Instant.now(), request.getRequestURI()
        );

        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(LimiteEmprestimoException.class)
    public ResponseEntity<StandardError> limiteEmprestimo(LimiteEmprestimoException e, HttpServletRequest request) {

        HttpStatus status = HttpStatus.CONFLICT;
        String error = "Limite de empréstimos atingido";

        StandardError err = new StandardError(
                status.value(), error, e.getMessage(), Instant.now(), request.getRequestURI()
        );

        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(EmprestimoEmAtrasoException.class)
    public ResponseEntity<StandardError> emprestimoEmAtraso(EmprestimoEmAtrasoException e, HttpServletRequest request) {

        HttpStatus status = HttpStatus.CONFLICT;
        String error = "Leitor possui empréstimos em situação de atraso!";

        StandardError err = new StandardError(
                status.value(), error, e.getMessage(), Instant.now(), request.getRequestURI()
        );

        return ResponseEntity.status(status).body(err);
    }

}
