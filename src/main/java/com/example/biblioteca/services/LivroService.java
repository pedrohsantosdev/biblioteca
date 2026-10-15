package com.example.biblioteca.services;

import com.example.biblioteca.entitites.Livro;
import com.example.biblioteca.repositories.LivroRepository;
import org.springframework.stereotype.Service;

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
        return livro.get();
    }
    
    public Livro cadastrarLivro(Livro livro) {
        return livroRepository.save(livro);
    }
    
    public Livro atualizarLivro(Long id, Livro novosDados) {
        Livro livroAtual = buscarLivroPorId(id);
        atualizarDados(livroAtual, novosDados);
        return livroRepository.save(livroAtual);
    }

    public void deletarLivroPorId(Long id) {
        Livro livroDeletar = buscarLivroPorId(id);
        livroRepository.delete(livroDeletar);
    }

    private void atualizarDados(Livro livroAtual, Livro novosDados) {
        livroAtual.setTitulo(novosDados.getTitulo());

        livroAtual.setAutor(
                autorService.buscarAutorPorId(novosDados.getAutor().getId())
        );

        livroAtual.setCategoria(
                categoriaService.buscarCategoriaPorId(novosDados.getCategoria().getId())
        );
    }

    public Livro adicionarExemplaresAoEstoque(Long id, Integer quantidade) {
        Livro livro = buscarLivroPorId(id);
        livro.setQuantidadeDisponivel(livro.getQuantidadeDisponivel() + quantidade);
        return livroRepository.save(livro);
    }

}
