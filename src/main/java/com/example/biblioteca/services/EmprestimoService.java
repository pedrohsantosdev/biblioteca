package com.example.biblioteca.services;

import com.example.biblioteca.dtos.EmprestimoRenovarDTO;
import com.example.biblioteca.dtos.EmprestimoRequestDTO;
import com.example.biblioteca.entitites.Emprestimo;
import com.example.biblioteca.entitites.Leitor;
import com.example.biblioteca.entitites.Livro;
import com.example.biblioteca.repositories.EmprestimoRepository;
import com.example.biblioteca.services.exceptions.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public List<Emprestimo> buscarEmprestimosPorLeitor(Long id) {
        leitorService.buscarLeitorPorId(id);
        List<Emprestimo> emprestimos = emprestimoRepository.findByLeitorId(id);
        return emprestimos;
    }

    public List<Emprestimo> buscarEmprestimosAtrasados() {
        LocalDate dataAtual = LocalDate.now();
        List<Emprestimo> emprestimos = emprestimoRepository.findByDataDevolucaoNullAndPrazoDevolucaoBefore(dataAtual);
        return emprestimos;
    }


    @Transactional
    public Emprestimo cadastrarEmprestimo(EmprestimoRequestDTO requestDTO) {

        Livro livro = livroService.buscarLivroPorId(requestDTO.livroId());

        if(livro.getQuantidadeDisponivel() <= 0) {
            throw new LivroIndisponivelException("Não há exemplares disponíveis no momento!");
        }

        livro.setQuantidadeDisponivel(livro.getQuantidadeDisponivel() - 1);

        Leitor leitor = leitorService.buscarLeitorPorId(requestDTO.leitorId());

        LocalDateTime dataEmprestimo = LocalDateTime.now();
        LocalDate prazoDevolucao = LocalDate.now().plusDays(30);

        Emprestimo emprestimo = new Emprestimo(null, livro,
                leitor, dataEmprestimo, prazoDevolucao, null);

        return emprestimoRepository.save(emprestimo);
    }

    @Transactional
    public Emprestimo renovarEmprestimo(Long id, EmprestimoRenovarDTO dto) {
        Emprestimo emprestimoAtual = buscarEmprestimoPorId(id);

        if(emprestimoAtual.getDataDevolucao() != null) {
            throw new EmprestimoJaDevolvidoException("Impossível renovar um empréstimo com livro já devolvido!");
        }
        if(!dto.novoPrazo().isAfter(emprestimoAtual.getPrazoDevolucao())) {
            throw new InvalidDateException("O novo prazo deve ser posterior ao atual!");
        }

        emprestimoAtual.setPrazoDevolucao(dto.novoPrazo());

        return emprestimoRepository.save(emprestimoAtual);
    }

    @Transactional
    public Emprestimo registrarDevolucao(Long id) {
        Emprestimo emprestimoAtual = buscarEmprestimoPorId(id);

        if(emprestimoAtual.getDataDevolucao() != null) {
            return emprestimoAtual;
        }

        emprestimoAtual.getLivro().setQuantidadeDisponivel(emprestimoAtual.getLivro().getQuantidadeDisponivel() + 1);
        emprestimoAtual.setDataDevolucao(LocalDateTime.now());
        return emprestimoRepository.save(emprestimoAtual);
    }

    @Transactional
    public void deletarEmprestimo(Long id) {
        Emprestimo emprestimo = buscarEmprestimoPorId(id);

        if(emprestimo.getDataDevolucao() == null) {
            throw new EmprestimoSemDevolucaoException("Impossível deletar empréstimo sem devolução");
        }

        emprestimoRepository.delete(emprestimo);
    }
}
