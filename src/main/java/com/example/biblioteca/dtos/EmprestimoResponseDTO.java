package com.example.biblioteca.dtos;

import com.example.biblioteca.entitites.Emprestimo;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record EmprestimoResponseDTO(
        Long id,
        Long livroId,
        Long leitorId,
        LocalDateTime dataEmprestimo,
        LocalDate prazoDevolucao,
        LocalDateTime dataDevolucao
) {

    public EmprestimoResponseDTO(Emprestimo emprestimo) {
        this(emprestimo.getId(), emprestimo.getLivro().getId(),
                emprestimo.getLeitor().getId(), emprestimo.getDataEmprestimo(),
                emprestimo.getPrazoDevolucao(), emprestimo.getDataDevolucao());
    }
}
