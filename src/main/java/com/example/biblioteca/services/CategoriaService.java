package com.example.biblioteca.services;

import com.example.biblioteca.entitites.Categoria;
import com.example.biblioteca.repositories.CategoriaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    public List<Categoria> listarCategorias() {
        return categoriaRepository.findAll();
    }

    public Categoria buscarCategoriaPorId(Long id) {
        Optional<Categoria> categoria = categoriaRepository.findById(id);
        return categoria.get();
    }

    public Categoria cadastrarCategoria(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    public Categoria atualizarCategoria(Long id, Categoria novosDados) {
        Categoria dadosAtuais = buscarCategoriaPorId(id);
        atualizarDados(dadosAtuais, novosDados);
        return categoriaRepository.save(dadosAtuais);
    }

    public void deletarCategoria(Long id) {
        Categoria categoriaDeletar = buscarCategoriaPorId(id);
        categoriaRepository.delete(categoriaDeletar);
    }

    private void atualizarDados(Categoria dadosAtuais, Categoria novosDados) {
        dadosAtuais.setNome(novosDados.getNome());
    }
}
