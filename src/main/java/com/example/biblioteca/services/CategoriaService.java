package com.example.biblioteca.services;

import com.example.biblioteca.dtos.CategoriaRequestDTO;
import com.example.biblioteca.entitites.Categoria;
import com.example.biblioteca.repositories.CategoriaRepository;
import com.example.biblioteca.services.exceptions.ResourceNotFoundException;
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
        return categoria.orElseThrow(() -> new ResourceNotFoundException(id));
    }

    public Categoria cadastrarCategoria(CategoriaRequestDTO requestDTO) {
        Categoria categoria = new Categoria(null, requestDTO.nome());
        return categoriaRepository.save(categoria);
    }

    public Categoria atualizarCategoria(Long id, CategoriaRequestDTO requestDTO) {
        Categoria dadosAtuais = buscarCategoriaPorId(id);
        atualizarDados(dadosAtuais, requestDTO);
        return categoriaRepository.save(dadosAtuais);
    }

    public void deletarCategoria(Long id) {
        Categoria categoriaDeletar = buscarCategoriaPorId(id);
        categoriaRepository.delete(categoriaDeletar);
    }

    private void atualizarDados(Categoria dadosAtuais, CategoriaRequestDTO responseDTO) {
        dadosAtuais.setNome(responseDTO.nome());
    }
}
