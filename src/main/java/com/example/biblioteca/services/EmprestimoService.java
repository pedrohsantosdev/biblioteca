package com.example.biblioteca.services;

import com.example.biblioteca.dtos.EmprestimoRenovarDTO;
import com.example.biblioteca.dtos.EmprestimoRequestDTO;
import com.example.biblioteca.entitites.Emprestimo;
import com.example.biblioteca.entitites.Leitor;
import com.example.biblioteca.entitites.Livro;
import com.example.biblioteca.repositories.EmprestimoRepository;
import com.example.biblioteca.services.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class EmprestimoService {

    private final EmprestimoRepository emprestimoRepository;
    private final LivroService livroService;
    private final LeitorService leitorService;

    public EmprestimoService(EmprestimoRepository emprestimoRepository, LivroService livroService, LeitorService leitorService) {
        this.emprestimoRepository = emprestimoRepository;
        this.livroService = livroService;
        this.leitorService = leitorService;
    }

    public List<Emprestimo> listarEmprestimos() {
        return emprestimoRepository.findAll();
    }

    public Emprestimo buscarEmprestimoPorId(Long id) {
        Optional<Emprestimo> emprestimo = emprestimoRepository.findById(id);
        return emprestimo.orElseThrow(() -> new ResourceNotFoundException(id));
    }

    public Emprestimo cadastrarEmprestimo(EmprestimoRequestDTO requestDTO) {

        Livro livro = livroService.buscarLivroPorId(requestDTO.livroId());
        livro.setQuantidadeDisponivel(livro.getQuantidadeDisponivel() - 1);

        Leitor leitor = leitorService.buscarLeitorPorId(requestDTO.leitorId());

        LocalDateTime dataEmprestimo = LocalDateTime.now();
        LocalDate prazoDevolucao = LocalDate.now().plusDays(30);

        Emprestimo emprestimo = new Emprestimo(null, livro,
                leitor, dataEmprestimo, prazoDevolucao, null);

        return emprestimoRepository.save(emprestimo);
    }

    public Emprestimo renovarEmprestimo(Long id, EmprestimoRenovarDTO dto) {
        Emprestimo emprestimoAtual = buscarEmprestimoPorId(id);
        emprestimoAtual.setPrazoDevolucao(dto.novoPrazo());
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
}
