package com.example.biblioteca.resources;

import com.example.biblioteca.dtos.EmprestimoRenovarDTO;
import com.example.biblioteca.dtos.EmprestimoRequestDTO;
import com.example.biblioteca.dtos.EmprestimoResponseDTO;
import com.example.biblioteca.entitites.Emprestimo;
import com.example.biblioteca.services.EmprestimoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/emprestimos")
public class EmprestimoResource {

    private final EmprestimoService emprestimoService;

    public EmprestimoResource(EmprestimoService emprestimoService) {
        this.emprestimoService = emprestimoService;
    }

    @GetMapping
    public ResponseEntity<List<EmprestimoResponseDTO>> listarEmprestimos() {
        List<Emprestimo> emprestimos = emprestimoService.listarEmprestimos();
        List<EmprestimoResponseDTO> emprestimosDtos = emprestimos.stream()
                .map(emprestimo -> new EmprestimoResponseDTO(emprestimo)).toList();

        return ResponseEntity.ok().body(emprestimosDtos);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<EmprestimoResponseDTO> buscarEmprestimoPorId(@PathVariable Long id) {
        Emprestimo emprestimo = emprestimoService.buscarEmprestimoPorId(id);
        return ResponseEntity.ok().body(new EmprestimoResponseDTO(emprestimo));
    }

    @GetMapping(value = "/leitor/{id}")
    public ResponseEntity<List<EmprestimoResponseDTO>> buscarEmprestimosPorLeitor(@PathVariable Long id) {
        List<Emprestimo> emprestimos = emprestimoService.buscarEmprestimosPorLeitor(id);
        List<EmprestimoResponseDTO> emprestimosDtos = emprestimos.stream()
                .map(emprestimo -> new EmprestimoResponseDTO(emprestimo)).toList();

        return ResponseEntity.ok().body(emprestimosDtos);
    }

    @PostMapping
    public ResponseEntity<EmprestimoResponseDTO> cadastrarEmprestimo(@Valid @RequestBody EmprestimoRequestDTO requestDTO) {
        Emprestimo emprestimo = emprestimoService.cadastrarEmprestimo(requestDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(emprestimo.getId()).toUri();
        return ResponseEntity.created(uri).body(new EmprestimoResponseDTO(emprestimo));
    }

    @PatchMapping(value = "/renovar/{id}")
    public ResponseEntity<EmprestimoResponseDTO> renovarEmprestimo(@PathVariable Long id, @Valid @RequestBody EmprestimoRenovarDTO dto) {
        Emprestimo emprestimo = emprestimoService.renovarEmprestimo(id, dto);
        return ResponseEntity.ok().body(new EmprestimoResponseDTO(emprestimo));
    }

    @PatchMapping(value = "/devolucao/{id}")
    public ResponseEntity<EmprestimoResponseDTO> registrarDevolucao(@PathVariable Long id) {
        Emprestimo emprestimo = emprestimoService.registrarDevolucao(id);
        return ResponseEntity.ok().body(new EmprestimoResponseDTO(emprestimo));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deletarEmprestimo(@PathVariable Long id) {
        emprestimoService.deletarEmprestimo(id);
        return ResponseEntity.noContent().build();
    }

}
