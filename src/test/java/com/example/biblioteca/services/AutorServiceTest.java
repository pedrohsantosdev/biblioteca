package com.example.biblioteca.services;

import com.example.biblioteca.dtos.AutorRequestDTO;
import com.example.biblioteca.entitites.Autor;
import com.example.biblioteca.repositories.AutorRepository;
import com.example.biblioteca.services.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class AutorServiceTest {

    @Mock
    private AutorRepository autorRepository;

    @InjectMocks
    private AutorService autorService;

    @Test
    void deveRetornarListaDeAutores() {
        Autor autor = new Autor(1L, "Machado de Assis");
        Autor autor1 = new Autor(2L, "Clarice Lispector");

        when(autorRepository.findAll()).thenReturn(List.of(autor, autor1));

        List<Autor> resultado = autorService.listarAutores();

        assertEquals(2, resultado.size());
        assertSame(autor, resultado.get(0));
        assertSame(autor1, resultado.get(1));
    }

    @Test
    void deveRetornarListaVaziaAoListaAutores() {
        when(autorRepository.findAll()).thenReturn(List.of());

        List<Autor> resultado = autorService.listarAutores();

        assertTrue(resultado.isEmpty());
        verify(autorRepository).findAll();
    }

    @Test
    void deveRetornarAutorComIdExistente() {
        Autor autor = new Autor(1L, "Machado de Assis");

        when(autorRepository.findById(1L)).thenReturn(Optional.of(autor));

        Autor resultado = autorService.buscarAutorPorId(1L);

        assertSame(autor, resultado);
        verify(autorRepository).findById(1L);
    }

    @Test
    void deveLancarExcecaoAoBuscarAutorComIdInexistente() {
        when(autorRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> autorService.buscarAutorPorId(99L)
        );

        verify(autorRepository).findById(99L);
    }

    @Test
    void deveRetornarListaDeAutoresAoBuscarPorNome() {
        Autor autor = new Autor(1L, "Machado de Assis");
        Autor autor1 = new Autor(2L, "Monteiro Lobato");

        when(autorRepository.findByNomeContainingIgnoreCase("m")).thenReturn(List.of(autor, autor1));

        List<Autor> resultado = autorService.buscarAutoresPorNome("m");

        assertEquals(2, resultado.size());
        assertSame(autor, resultado.get(0));
        assertSame(autor1, resultado.get(1));

        verify(autorRepository).findByNomeContainingIgnoreCase("m");
    }

    @Test
    void deveRetornarListaVaziaDeAutoresAoBuscarPorNome() {
        when(autorRepository.findByNomeContainingIgnoreCase("machado")).thenReturn(List.of());

        List<Autor> resultado = autorService.buscarAutoresPorNome("machado");

        assertTrue(resultado.isEmpty());

        verify(autorRepository).findByNomeContainingIgnoreCase("machado");
    }

    @Test
    void deveCadastrarAutor() {
        AutorRequestDTO autorDto = new AutorRequestDTO("Machado de Assis");
        Autor autorSalvo = new Autor(1L, "Machado de Assis");

        when(autorRepository.save(any(Autor.class))).thenReturn(autorSalvo);

        Autor resultado = autorService.cadastrarAutor(autorDto);

        ArgumentCaptor<Autor> captor = ArgumentCaptor.forClass(Autor.class);
        verify(autorRepository).save(captor.capture());
        Autor autorCapturado = captor.getValue();

        assertNull(autorCapturado.getId());
        assertEquals("Machado de Assis", autorCapturado.getNome());

        assertSame(autorSalvo, resultado);
        assertEquals(1L, resultado.getId());
    }

    @Test
    void deveAtualizarAutorComIdExistente() {
        Autor autor = new Autor(1L, "Machado");
        Autor autorSalvo = new Autor(1L, "Machado de Assis");
        AutorRequestDTO autorDtos = new AutorRequestDTO("Machado de Assis");

        when(autorRepository.findById(1L)).thenReturn(Optional.of(autor));
        when(autorRepository.save(autor)).thenReturn(autorSalvo);

        Autor resultado = autorService.atualizarAutor(1L, autorDtos);

        assertSame(autorSalvo, resultado);
        assertEquals(1L, autor.getId());
        assertEquals("Machado de Assis", autor.getNome());

        verify(autorRepository).findById(1L);
        verify(autorRepository).save(autor);
    }

    @Test
    void deveLancarExcecaoAoAtualizarAutorComIdInexistente() {
        when(autorRepository.findById(99L)).thenReturn(Optional.empty());
        AutorRequestDTO autorDtos = new AutorRequestDTO("Machado de Assis");

        assertThrows(
                ResourceNotFoundException.class,
                () -> autorService.atualizarAutor(99L, autorDtos)
        );

        verify(autorRepository, never()).save(any(Autor.class));
    }

    @Test
    void deveDeletarAutorComIdExistente() {
        Autor autor = new Autor(1L, "Machado de Assis");

        when(autorRepository.findById(1L)).thenReturn(Optional.of(autor));

        autorService.deletarAutor(1L);

        verify(autorRepository).delete(autor);
    }

    @Test
    void deveLancarExcecaoAoDeletarAutorComIdInexistente() {
        when(autorRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> autorService.deletarAutor(99L)
        );

        verify(autorRepository, never()).delete(any(Autor.class));
    }

}
