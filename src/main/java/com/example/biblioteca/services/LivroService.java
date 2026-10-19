package com.example.biblioteca.services;

import com.example.biblioteca.dtos.LivroExemplaresDTO;
import com.example.biblioteca.dtos.LivroRequestDTO;
import com.example.biblioteca.entitites.Autor;
import com.example.biblioteca.entitites.Categoria;
import com.example.biblioteca.entitites.Livro;
import com.example.biblioteca.repositories.LivroRepository;
import com.example.biblioteca.services.exceptions.EstoqueInsuficienteException;
import com.example.biblioteca.services.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class LivroService {
    
    private final LivroRepository livroRepository;
    private final AutorService autorService;
    private final CategoriaService categoriaService;

    public LivroService(LivroRepository livroRepository, AutorService autorService, CategoriaService categoriaService) {
        this.livroRepository = livroRepository;
        this.autorService = autorService;
        this.categoriaService = categoriaService;
    }

    public List<Livro> listarLivros() {
        return livroRepository.findAll();
    }
    
    public Livro buscarLivroPorId(Long id) {
        Optional<Livro> livro = livroRepository.findById(id);
        return livro.orElseThrow(() -> new ResourceNotFoundException(id));
    }

    public List<Livro> buscarLivroPorTitulo(String titulo) {
        return livroRepository.findByTituloContainingIgnoreCase(titulo);
    }

    @Transactional
    public List<Livro> buscarLivrosPorCategoria(Long id) {
        categoriaService.buscarCategoriaPorId(id);
        return livroRepository.findByCategoriaId(id);
    }

    @Transactional
    public List<Livro> buscarLivrosPorAutor(Long id) {
        autorService.buscarAutorPorId(id);
        return livroRepository.findByAutorId(id);
    }

    public List<Livro> listarLivrosDisponiveis() {
        return livroRepository.buscarLivrosDisponiveis();
    }
    
    public Livro cadastrarLivro(LivroRequestDTO requestDTO) {
       Autor autor = autorService.buscarAutorPorId(requestDTO.autorId());
       Categoria categoria = categoriaService.buscarCategoriaPorId(requestDTO.categoriaId());

       Livro livro = new Livro(null, requestDTO.titulo(),0,  autor, categoria);

       return livroRepository.save(livro);
    }

    @Transactional
    public Livro atualizarLivro(Long id, LivroRequestDTO requestDTO) {
        Livro livroAtual = buscarLivroPorId(id);
        atualizarDados(livroAtual, requestDTO);
        return livroRepository.save(livroAtual);
    }

    @Transactional
    public void deletarLivroPorId(Long id) {
        Livro livroDeletar = buscarLivroPorId(id);
        livroRepository.delete(livroDeletar);
    }

    private void atualizarDados(Livro livroAtual, LivroRequestDTO requestDTO) {
        livroAtual.setTitulo(requestDTO.titulo());

        livroAtual.setAutor(
                autorService.buscarAutorPorId(requestDTO.autorId())
        );

        livroAtual.setCategoria(
                categoriaService.buscarCategoriaPorId(requestDTO.categoriaId())
        );
    }

    @Transactional
    public Livro adicionarExemplaresAoEstoque(Long id, LivroExemplaresDTO adicionarExemplaresDTO) {
        Livro livro = buscarLivroPorId(id);
        livro.setQuantidadeDisponivel(livro.getQuantidadeDisponivel() + adicionarExemplaresDTO.quantidade());
        return livroRepository.save(livro);
    }

    @Transactional
    public Livro retirarExemplaresDoEstoque(Long id, LivroExemplaresDTO retirarExemplaresDTO) {
        Livro livro = buscarLivroPorId(id);

        if(retirarExemplaresDTO.quantidade() > livro.getQuantidadeDisponivel()) {
            throw new EstoqueInsuficienteException("Impóssivel retirar uma quantidade maior que o atual estoque de livros!");
        }

        livro.setQuantidadeDisponivel(livro.getQuantidadeDisponivel() - retirarExemplaresDTO.quantidade());
        return livroRepository.save(livro);
    }

}
