package com.example.biblioteca.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LeitorRequestDTO(

        @NotBlank(message = "Nome do leitor é obrigatório!")
        String nome,

        @NotBlank(message = "Telefone do leitor é obrigatório!")
        String telefone,

        @NotBlank(message = "Email do leitor é obrigatório!")
        @Email(message = "Email inválido!")
        String email
) {
}
