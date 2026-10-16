package com.example.biblioteca.dtos;

import jakarta.validation.constraints.NotBlank;

public record CategoriaRequestDTO(
        @NotBlank(message = "Nome da categoria é obrigatório!")
        String nome
) {
}
