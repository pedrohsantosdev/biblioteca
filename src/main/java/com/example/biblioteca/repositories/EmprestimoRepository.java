package com.example.biblioteca.repositories;

import com.example.biblioteca.entitites.Emprestimo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmprestimoRepository extends JpaRepository<Emprestimo, Long> {

    List<Emprestimo> findByLeitorId(Long id);
}
