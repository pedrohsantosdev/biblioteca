package com.example.biblioteca.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record EmprestimoRequestDTO(
        @NotNull(message = "Id do livro é obrigatório!")
        @Positive(message = "Id não pode ser zero ou negativo!")
        Long livroId,

        @NotNull(message = "Id do leitor é obrigatório!")
        @Positive(message = "Id não pode ser zero ou negativo!")
        Long leitorId
) {
}
