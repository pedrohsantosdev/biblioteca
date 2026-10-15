package com.example.biblioteca.repositories;

import com.example.biblioteca.entitites.Leitor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LeitorRepository extends JpaRepository<Leitor, Long> {
}
