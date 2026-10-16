package com.example.biblioteca.resources;

import com.example.biblioteca.dtos.CategoriaRequestDTO;
import com.example.biblioteca.dtos.CategoriaResponseDTO;
import com.example.biblioteca.entitites.Categoria;
import com.example.biblioteca.services.CategoriaService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/categorias")
public class CategoriaResource {

    private final CategoriaService categoriaService;

    public CategoriaResource(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @GetMapping
    public ResponseEntity<List<CategoriaResponseDTO>> listarCategorias() {
        List<Categoria> categorias = categoriaService.listarCategorias();
        List<CategoriaResponseDTO> categoriasDtos = categorias.stream()
                .map(categoria -> new CategoriaResponseDTO(categoria)).toList();

        return ResponseEntity.ok().body(categoriasDtos);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<CategoriaResponseDTO> buscarCategoriaPorId(@PathVariable Long id) {
        Categoria categoria = categoriaService.buscarCategoriaPorId(id);
        return ResponseEntity.ok().body(new CategoriaResponseDTO(categoria));
    }

    @PostMapping
    public ResponseEntity<CategoriaResponseDTO> cadastrarCategoria(@Valid @RequestBody CategoriaRequestDTO requestDTO) {
        Categoria categoria = categoriaService.cadastrarCategoria(requestDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(categoria.getId()).toUri();
        return ResponseEntity.created(uri).body(new CategoriaResponseDTO(categoria));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<CategoriaResponseDTO> atualizarCategoria(@PathVariable Long id, @Valid @RequestBody CategoriaRequestDTO requestDTO) {
        Categoria categoria = categoriaService.atualizarCategoria(id, requestDTO);
        return ResponseEntity.ok().body(new CategoriaResponseDTO(categoria));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deletarCategoria(@PathVariable Long id) {
        categoriaService.deletarCategoria(id);
        return ResponseEntity.noContent().build();
    }

}
