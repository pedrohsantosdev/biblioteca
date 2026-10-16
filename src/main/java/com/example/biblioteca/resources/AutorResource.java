package com.example.biblioteca.resources;

import com.example.biblioteca.dtos.AutorRequestDTO;
import com.example.biblioteca.dtos.AutorResponseDTO;
import com.example.biblioteca.entitites.Autor;
import com.example.biblioteca.services.AutorService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/autores")
public class AutorResource {

    private final AutorService autorService;

    public AutorResource(AutorService autorService) {
        this.autorService = autorService;
    }

    @GetMapping
    public ResponseEntity<List<AutorResponseDTO>> listarAutores() {
        List<Autor> autores = autorService.listarAutores();
        List<AutorResponseDTO> autoresDtos = autores.stream()
                .map(autor -> new AutorResponseDTO(autor)).toList();

        return ResponseEntity.ok().body(autoresDtos);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<AutorResponseDTO> buscarAutorPorId(@PathVariable Long id) {
        Autor autor = autorService.buscarAutorPorId(id);
        return ResponseEntity.ok().body(new AutorResponseDTO(autor));
    }

    @PostMapping
    public ResponseEntity<AutorResponseDTO> cadastrarAutor(@Valid @RequestBody AutorRequestDTO requestDTO) {
        Autor autor = autorService.cadastrarAutor(requestDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(autor.getId()).toUri();

        return ResponseEntity.created(uri).body(new AutorResponseDTO(autor));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<AutorResponseDTO> atualizarAutor(@PathVariable Long id, @Valid @RequestBody AutorRequestDTO requestDTO) {
        Autor autor = autorService.atualizarAutor(id, requestDTO);
        return ResponseEntity.ok().body(new AutorResponseDTO(autor));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deletarAutor(@PathVariable Long id) {
        autorService.deletarAutor(id);
        return ResponseEntity.noContent().build();
    }

}
