package com.example.biblioteca.resources;

import com.example.biblioteca.dtos.EmprestimoResponseDTO;
import com.example.biblioteca.dtos.LeitorRequestDTO;
import com.example.biblioteca.dtos.LeitorResponseDTO;
import com.example.biblioteca.entitites.Leitor;
import com.example.biblioteca.services.LeitorService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/leitores")
public class LeitorResource {

    private final LeitorService leitorService;

    public LeitorResource(LeitorService leitorService) {
        this.leitorService = leitorService;
    }

    @GetMapping
    public ResponseEntity<List<LeitorResponseDTO>> listarLeitores() {
        List<Leitor> leitores = leitorService.listarLeitores();
        List<LeitorResponseDTO> leitoresDtos = leitores.stream()
                .map(leitor -> new LeitorResponseDTO(leitor)).toList();

        return ResponseEntity.ok().body(leitoresDtos);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<LeitorResponseDTO> buscarLeitorPorId(@PathVariable Long id) {
        Leitor leitor = leitorService.buscarLeitorPorId(id);
        return ResponseEntity.ok().body(new LeitorResponseDTO(leitor));
    }

    @GetMapping(value = "/nome")
    public ResponseEntity<List<LeitorResponseDTO>> buscarLeitorPorNome(@RequestParam(name = "nome") String nome) {
        List<Leitor> leitores = leitorService.buscarLeitoresPorNome(nome);
        List<LeitorResponseDTO> leitoresDtos = leitores.stream()
                .map(leitor -> new LeitorResponseDTO(leitor)).toList();

        return ResponseEntity.ok().body(leitoresDtos);
    }

    @PostMapping
    public ResponseEntity<LeitorResponseDTO> cadastrarLeitor(@Valid @RequestBody LeitorRequestDTO requestDTO) {
        Leitor leitor = leitorService.cadastrarLeitor(requestDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(leitor.getId()).toUri();
        return ResponseEntity.created(uri).body(new LeitorResponseDTO(leitor));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<LeitorResponseDTO> atualizarLeitor(@PathVariable Long id, @Valid @RequestBody LeitorRequestDTO requestDTO) {
        Leitor leitor = leitorService.atualizarLeitor(id, requestDTO);
        return ResponseEntity.ok().body(new LeitorResponseDTO(leitor));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deletarLeitor(@PathVariable Long id) {
        leitorService.deletarLeitor(id);
        return ResponseEntity.noContent().build();
    }

}
