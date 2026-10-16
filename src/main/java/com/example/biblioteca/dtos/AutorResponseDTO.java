package com.example.biblioteca.dtos;

import com.example.biblioteca.entitites.Autor;

public record AutorResponseDTO(
        Long id,
        String nome
) {

    public AutorResponseDTO(Autor autor) {
        this(autor.getId(), autor.getNome());
    }
}
