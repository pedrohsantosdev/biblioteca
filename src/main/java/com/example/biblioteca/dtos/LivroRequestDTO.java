package com.example.biblioteca.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record LivroRequestDTO(

        @NotBlank(message = "Título do livro é obrigatório!")
        String titulo,

        @NotNull(message = "Id do autor é obrigatório!")
        @Positive(message = "Id deve ser maior que zero!")
        Long autorId,

        @NotNull(message = "Id da categoria é obrigatório!")
        @Positive(message = "Id deve ser maior que zero!")
        Long categoriaId
) {
}
