package com.example.biblioteca.resources;

import com.example.biblioteca.dtos.LivroAdicionarExemplaresDTO;
import com.example.biblioteca.dtos.LivroRequestDTO;
import com.example.biblioteca.dtos.LivroResponseDTO;
import com.example.biblioteca.entitites.Livro;
import com.example.biblioteca.services.LivroService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/livros")
public class LivroResource {

    private final LivroService livroService;

    public LivroResource(LivroService livroService) {
        this.livroService = livroService;
    }

    @GetMapping
    public ResponseEntity<List<LivroResponseDTO>> listarLivros() {
        List<Livro> livros = livroService.listarLivros();
        List<LivroResponseDTO> livrosDtos = livros.stream().
                map(livro -> new LivroResponseDTO(livro)).toList();

        return ResponseEntity.ok().body(livrosDtos);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<LivroResponseDTO> buscarLivroPorId(@PathVariable Long id) {
        Livro livro = livroService.buscarLivroPorId(id);
        return ResponseEntity.ok().body(new LivroResponseDTO(livro));
    }

    @GetMapping(value = "/titulo")
    public ResponseEntity <List<LivroResponseDTO>> buscarLivroPorTitulo(@RequestParam(name = "nome") String titulo) {
        List<Livro> livros = livroService.buscarLivroPorTitulo(titulo);
        List<LivroResponseDTO> livrosDtos = livros.stream()
                .map(livro -> new LivroResponseDTO(livro)).toList();
        return ResponseEntity.ok().body(livrosDtos);
    }

    @PostMapping
    public ResponseEntity<LivroResponseDTO> cadastrarLivro(@Valid @RequestBody LivroRequestDTO requestDTO) {
        Livro livro = livroService.cadastrarLivro(requestDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(livro.getId()).toUri();
        return ResponseEntity.created(uri).body(new LivroResponseDTO(livro));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<LivroResponseDTO> atualizarLivro(@PathVariable Long id, @Valid @RequestBody LivroRequestDTO requestDTO) {
        Livro livro = livroService.atualizarLivro(id, requestDTO);
        return ResponseEntity.ok().body(new LivroResponseDTO(livro));
    }

    @PatchMapping(value = "/adicionar/{id}")
    public ResponseEntity<LivroResponseDTO> adicionarExemplaresAoEstoque(@PathVariable Long id, @Valid @RequestBody LivroAdicionarExemplaresDTO adicionarExemplaresDTO) {
        Livro livro = livroService.adicionarExemplaresAoEstoque(id, adicionarExemplaresDTO);
        return ResponseEntity.ok().body(new LivroResponseDTO(livro));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deletarLivro(@PathVariable Long id) {
        livroService.deletarLivroPorId(id);
        return ResponseEntity.noContent().build();
    }

}
