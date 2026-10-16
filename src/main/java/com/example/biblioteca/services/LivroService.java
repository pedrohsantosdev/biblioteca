package com.example.biblioteca.services;

import com.example.biblioteca.dtos.LivroAdicionarExemplaresDTO;
import com.example.biblioteca.dtos.LivroRequestDTO;
import com.example.biblioteca.entitites.Autor;
import com.example.biblioteca.entitites.Categoria;
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
    
    public Livro cadastrarLivro(LivroRequestDTO requestDTO) {
       Autor autor = autorService.buscarAutorPorId(requestDTO.autorId());
       Categoria categoria = categoriaService.buscarCategoriaPorId(requestDTO.categoriaId());

       Livro livro = new Livro(null, requestDTO.titulo(),0,  autor, categoria);

       return livroRepository.save(livro);
    }
    
    public Livro atualizarLivro(Long id, LivroRequestDTO requestDTO) {
        Livro livroAtual = buscarLivroPorId(id);
        atualizarDados(livroAtual, requestDTO);
        return livroRepository.save(livroAtual);
    }

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

    public Livro adicionarExemplaresAoEstoque(Long id, LivroAdicionarExemplaresDTO adicionarExemplaresDTO) {
        Livro livro = buscarLivroPorId(id);
        livro.setQuantidadeDisponivel(livro.getQuantidadeDisponivel() + adicionarExemplaresDTO.quantidade());
        return livroRepository.save(livro);
    }

}
