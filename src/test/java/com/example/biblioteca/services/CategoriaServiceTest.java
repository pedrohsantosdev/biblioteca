package com.example.biblioteca.services;

import com.example.biblioteca.dtos.CategoriaRequestDTO;
import com.example.biblioteca.entitites.Categoria;
import com.example.biblioteca.repositories.CategoriaRepository;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CategoriaServiceTest {

    @Mock
    private CategoriaRepository categoriaRepository;

    @InjectMocks
    private CategoriaService categoriaService;

    @Test
    void deveRetornarListaDeCategoria() {
        Categoria categoria = new Categoria(1L, "Romance");
        Categoria categoria1 = new Categoria(2L, "Contos");

        when(categoriaRepository.findAll()).thenReturn(List.of(categoria, categoria1));

        List<Categoria> resultado = categoriaService.listarCategorias();

        assertEquals(2, resultado.size());
        assertSame(categoria, resultado.get(0));
        assertSame(categoria1, resultado.get(1));

        verify(categoriaRepository).findAll();
    }

    @Test
    void deveRetornarListaVaziaDeCategoria() {
        when(categoriaRepository.findAll()).thenReturn(List.of());

        List<Categoria> resultado = categoriaService.listarCategorias();

        assertTrue(resultado.isEmpty());
    }

    @Test
    void deveRetornarCategoriaAoBuscarPorIdExistente() {
        Categoria categoria = new Categoria(1L, "Romance");

        when(categoriaRepository.findById(1L)).thenReturn(Optional.of(categoria));
        Categoria resultado = categoriaService.buscarCategoriaPorId(1L);

        assertSame(categoria, resultado);

        verify(categoriaRepository).findById(1L);
    }

    @Test
    void deveLancarExcecaoAoBuscarCategoriaPorIdInexistente() {
        when(categoriaRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> categoriaService.buscarCategoriaPorId(99L)
        );

        verify(categoriaRepository).findById(99L);
    }

    @Test
    void deveCadastrarCategoria() {
        CategoriaRequestDTO categoriaDtos = new CategoriaRequestDTO("Romance");
        Categoria categoriaSalva = new Categoria(1L, "Romance");

        when(categoriaRepository.save(any(Categoria.class))).thenReturn(categoriaSalva);

        Categoria resultado = categoriaService.cadastrarCategoria(categoriaDtos);

        ArgumentCaptor<Categoria> captor = ArgumentCaptor.forClass(Categoria.class);
        verify(categoriaRepository).save(captor.capture());
        Categoria categoriaCapturada = captor.getValue();

        assertNull(categoriaCapturada.getId());
        assertEquals("Romance", categoriaCapturada.getNome());

        assertSame(categoriaSalva, resultado);
    }

    @Test
    void deveAtualizarCategoriaComIdExistente() {
        Categoria categoria = new Categoria(1L, "Contos");
        Categoria categoriaSalva = new Categoria(1L, "Romance");

        CategoriaRequestDTO categoriaDtos = new CategoriaRequestDTO("Romance");

        when(categoriaRepository.findById(1L)).thenReturn(Optional.of(categoria));
        when(categoriaRepository.save(categoria)).thenReturn(categoriaSalva);

        Categoria resultado = categoriaService.atualizarCategoria(1L, categoriaDtos);

        assertSame(categoriaSalva, resultado);
        assertEquals(1L, categoria.getId());
        assertEquals("Romance", categoria.getNome());

        verify(categoriaRepository).findById(1L);
        verify(categoriaRepository).save(categoria);
    }

    @Test
    void deveLancarExcecaoAoAtualizarCategoriaComIdInexistente() {
        CategoriaRequestDTO categoriaDtos = new CategoriaRequestDTO("Romance");

        when(categoriaRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> categoriaService.atualizarCategoria(99L, categoriaDtos)
        );

        verify(categoriaRepository).findById(99L);
        verify(categoriaRepository, never()).save(any(Categoria.class));
    }

    @Test
    void deveDeletarCategoriaComIdExistente() {
        Categoria categoria = new Categoria(1L, "Contos");

        when(categoriaRepository.findById(1L)).thenReturn(Optional.of(categoria));
        categoriaService.deletarCategoria(1L);

        verify(categoriaRepository).findById(1L);
        verify(categoriaRepository).delete(categoria);
    }

    @Test
    void deveLancarExcecaoAoDeletarCategoriaComIdInexistente() {
        when(categoriaRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> categoriaService.deletarCategoria(99L)
        );

        verify(categoriaRepository).findById(99L);
        verify(categoriaRepository, never()).delete(any(Categoria.class));
    }

}
