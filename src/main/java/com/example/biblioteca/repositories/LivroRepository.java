package com.example.biblioteca.repositories;


import com.example.biblioteca.entitites.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LivroRepository extends JpaRepository<Livro, Long> {

    List<Livro> findByTituloContainingIgnoreCase(String titulo);
    List<Livro> findByCategoriaId(Long id);
    List<Livro> findByAutorId(Long id);

    @Query("SELECT l FROM Livro l WHERE l.quantidadeDisponivel > 0")
    List<Livro> buscarLivrosDisponiveis();

}
