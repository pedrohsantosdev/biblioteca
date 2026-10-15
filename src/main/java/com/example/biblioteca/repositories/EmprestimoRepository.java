package com.example.biblioteca.repositories;

import com.example.biblioteca.entitites.Emprestimo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmprestimoRepository extends JpaRepository<Emprestimo, Long> {
}
