package com.example.biblioteca.dtos;

import com.example.biblioteca.entitites.Livro;

public record LivroResponseDTO(
        Long id,
        String titulo,
        Integer quantidadeDisponivel,
        Long autorId,
        Long categoriaId
) {

    public LivroResponseDTO(Livro livro) {
        this(livro.getId(), livro.getTitulo(), livro.getQuantidadeDisponivel(),
                livro.getAutor().getId(), livro.getCategoria().getId());
    }
}
