package com.example.biblioteca.repositories;

import com.example.biblioteca.entitites.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
}
