package com.example.biblioteca.services;

import com.example.biblioteca.entitites.Leitor;
import com.example.biblioteca.repositories.LeitorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LeitorService {

    private final LeitorRepository leitorRepository;

    public LeitorService(LeitorRepository leitorRepository) {
        this.leitorRepository = leitorRepository;
    }

    public List<Leitor> listarLeitores() {
        return leitorRepository.findAll();
    }

    public Leitor buscarLeitorPorId(Long id) {
        Optional<Leitor> leitor = leitorRepository.findById(id);
        return leitor.get();
    }

    public Leitor cadastrarLeitor(Leitor leitor) {
        return leitorRepository.save(leitor);
    }

    public Leitor atualizarLeitor(Long id, Leitor novosDados) {
        Leitor leitorAtual = buscarLeitorPorId(id);
        atualizarDados(leitorAtual, novosDados);
        return leitorRepository.save(leitorAtual);
    }

    public void deletarLeitor(Long id) {
        Leitor leitorDeletar = buscarLeitorPorId(id);
        leitorRepository.delete(leitorDeletar);
    }

    private void atualizarDados(Leitor leitorAtual, Leitor novosDados) {
        leitorAtual.setNome(novosDados.getNome());
        leitorAtual.setTelefone(novosDados.getTelefone());
        leitorAtual.setEmail(novosDados.getEmail());
    }

}
