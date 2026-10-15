package com.example.biblioteca.repositories;


import com.example.biblioteca.entitites.Livro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LivroRepository extends JpaRepository<Livro, Long> {
}
