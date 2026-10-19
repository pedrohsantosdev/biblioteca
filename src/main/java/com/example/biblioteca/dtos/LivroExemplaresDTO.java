package com.example.biblioteca.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record LivroExemplaresDTO(
        @NotNull(message = "Quantidade é obrigatória!")
        @Positive(message = "Quantidade não deve ser zero ou número negativo!")
        Integer quantidade
) {
}
