package com.example.biblioteca.repositories;

import com.example.biblioteca.entitites.Emprestimo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface EmprestimoRepository extends JpaRepository<Emprestimo, Long> {

    List<Emprestimo> findByLeitorId(Long id);
    List<Emprestimo> findByDataDevolucaoNull();
    List<Emprestimo> findByDataDevolucaoNullAndPrazoDevolucaoBefore(LocalDate dataAtual);
    long countByLeitorIdAndDataDevolucaoIsNull(Long leitorId);
    boolean existsByLeitorIdAndDataDevolucaoIsNullAndPrazoDevolucaoBefore(Long leitorId, LocalDate dataAtual);
}
