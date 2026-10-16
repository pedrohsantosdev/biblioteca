package com.example.biblioteca.dtos;

import jakarta.validation.constraints.NotBlank;

public record AutorRequestDTO(
        @NotBlank(message = "Nome do autor é obrigatório")
        String nome
) {
}
