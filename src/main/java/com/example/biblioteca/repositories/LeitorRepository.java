package com.example.biblioteca.repositories;

import com.example.biblioteca.entitites.Leitor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LeitorRepository extends JpaRepository<Leitor, Long> {

    List<Leitor> findByNomeContainingIgnoreCase(String nome);
}
