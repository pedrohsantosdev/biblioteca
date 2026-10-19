package com.example.biblioteca.services;

import com.example.biblioteca.dtos.LeitorRequestDTO;
import com.example.biblioteca.entitites.Leitor;
import com.example.biblioteca.entitites.Livro;
import com.example.biblioteca.repositories.LeitorRepository;
import com.example.biblioteca.services.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        return leitor.orElseThrow(() -> new ResourceNotFoundException(id));
    }

    public List<Leitor> buscarLeitoresPorNome(String nome) {
        return leitorRepository.findByNomeContainingIgnoreCase(nome);
    }

    public Leitor cadastrarLeitor(LeitorRequestDTO requestDTO) {
        Leitor leitor = new Leitor(null, requestDTO.nome(), requestDTO.telefone(), requestDTO.email());
        return leitorRepository.save(leitor);
    }

    @Transactional
    public Leitor atualizarLeitor(Long id, LeitorRequestDTO requestDTO) {
        Leitor leitorAtual = buscarLeitorPorId(id);
        atualizarDados(leitorAtual, requestDTO);
        return leitorRepository.save(leitorAtual);
    }

    @Transactional
    public void deletarLeitor(Long id) {
        Leitor leitorDeletar = buscarLeitorPorId(id);
        leitorRepository.delete(leitorDeletar);
    }

    private void atualizarDados(Leitor leitorAtual, LeitorRequestDTO requestDTO) {
        leitorAtual.setNome(requestDTO.nome());
        leitorAtual.setTelefone(requestDTO.telefone());
        leitorAtual.setEmail(requestDTO.email());
    }

}
