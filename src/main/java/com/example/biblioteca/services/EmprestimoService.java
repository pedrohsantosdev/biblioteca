package com.example.biblioteca.services;

import com.example.biblioteca.entitites.Emprestimo;
import com.example.biblioteca.repositories.EmprestimoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class EmprestimoService {

    private final EmprestimoRepository emprestimoRepository;

    public EmprestimoService(EmprestimoRepository emprestimoRepository) {
        this.emprestimoRepository = emprestimoRepository;
    }

    public List<Emprestimo> listarEmprestimos() {
        return emprestimoRepository.findAll();
    }

    public Emprestimo buscarEmprestimoPorId(Long id) {
        Optional<Emprestimo> emprestimo = emprestimoRepository.findById(id);
        return emprestimo.get();
    }

    public Emprestimo cadastrarEmprestimo(Emprestimo emprestimo) {
        emprestimo.getLivro().setQuantidadeDisponivel(emprestimo.getLivro().getQuantidadeDisponivel() - 1);
        return emprestimoRepository.save(emprestimo);
    }

    public Emprestimo renovarEmprestimo(Long id, Emprestimo novoPrazo) {
        Emprestimo emprestimoAtual = buscarEmprestimoPorId(id);
        alterarPrazo(emprestimoAtual, novoPrazo);
        return emprestimoRepository.save(emprestimoAtual);
    }

    public Emprestimo registrarDevolucao(Long id) {
        Emprestimo emprestimoAtual = buscarEmprestimoPorId(id);
        emprestimoAtual.setDataDevolucao(LocalDateTime.now());
        return emprestimoRepository.save(emprestimoAtual);
    }

    public void deletarEmprestimo(Long id) {
        Emprestimo emprestimo = buscarEmprestimoPorId(id);
        emprestimoRepository.delete(emprestimo);
    }

    private void alterarPrazo(Emprestimo emprestimoAtual, Emprestimo novoPrazo) {
        emprestimoAtual.setPrazoDevolucao(novoPrazo.getPrazoDevolucao());
    }
}
