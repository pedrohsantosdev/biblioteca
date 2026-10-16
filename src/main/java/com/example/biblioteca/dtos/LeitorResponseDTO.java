package com.example.biblioteca.dtos;

import com.example.biblioteca.entitites.Leitor;

public record LeitorResponseDTO(
        Long id,
        String nome,
        String telefone,
        String email
) {

    public LeitorResponseDTO(Leitor leitor) {
        this(leitor.getId(), leitor.getNome(), leitor.getTelefone(), leitor.getEmail());
    }
}
