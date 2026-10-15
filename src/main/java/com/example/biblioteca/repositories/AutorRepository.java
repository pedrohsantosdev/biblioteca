package com.example.biblioteca.repositories;

import com.example.biblioteca.entitites.Autor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AutorRepository extends JpaRepository<Autor, Long> {
}
