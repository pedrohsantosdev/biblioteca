package com.example.biblioteca.dtos;

import com.example.biblioteca.entitites.Categoria;

public record CategoriaResponseDTO(
        Long id,
        String nome
) {

    public CategoriaResponseDTO(Categoria categoria) {
        this(categoria.getId(), categoria.getNome());
    }
}
