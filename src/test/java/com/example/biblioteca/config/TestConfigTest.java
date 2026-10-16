package com.example.biblioteca.config;

import com.example.biblioteca.entitites.Autor;
import com.example.biblioteca.entitites.Categoria;
import com.example.biblioteca.entitites.Emprestimo;
import com.example.biblioteca.entitites.Leitor;
import com.example.biblioteca.entitites.Livro;
import com.example.biblioteca.repositories.AutorRepository;
import com.example.biblioteca.repositories.CategoriaRepository;
import com.example.biblioteca.repositories.EmprestimoRepository;
import com.example.biblioteca.repositories.LeitorRepository;
import com.example.biblioteca.repositories.LivroRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TestConfigTest {

    @Mock
    private AutorRepository autorRepository;
    @Mock
    private CategoriaRepository categoriaRepository;
    @Mock
    private LivroRepository livroRepository;
    @Mock
    private LeitorRepository leitorRepository;
    @Mock
    private EmprestimoRepository emprestimoRepository;

    @Captor
    private ArgumentCaptor<List<Autor>> autoresCaptor;
    @Captor
    private ArgumentCaptor<List<Categoria>> categoriasCaptor;
    @Captor
    private ArgumentCaptor<List<Leitor>> leitoresCaptor;
    @Captor
    private ArgumentCaptor<List<Livro>> livrosCaptor;
    @Captor
    private ArgumentCaptor<List<Emprestimo>> emprestimosCaptor;

    private TestConfig config;

    @BeforeEach
    void setUp() {
        config = new TestConfig(autorRepository, categoriaRepository, livroRepository,
                leitorRepository, emprestimoRepository);
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 3, 4})
    void naoPopulaSeQualquerTabelaContiverRegistros(int indiceRepositorio) {
        List<JpaRepository<?, Long>> repositorios = List.of(autorRepository, categoriaRepository,
                livroRepository, leitorRepository, emprestimoRepository);
        when(repositorios.get(indiceRepositorio).count()).thenReturn(1L);

        config.run();

        verify(autorRepository, never()).saveAll(any());
        verify(categoriaRepository, never()).saveAll(any());
        verify(livroRepository, never()).saveAll(any());
        verify(leitorRepository, never()).saveAll(any());
        verify(emprestimoRepository, never()).saveAll(any());
    }

    @Test
    void populaBancoVazioComRelacionamentosEEstoqueConsistentes() {
        config.run();

        var ordem = inOrder(autorRepository, categoriaRepository, leitorRepository,
                livroRepository, emprestimoRepository);
        ordem.verify(autorRepository).saveAll(autoresCaptor.capture());
        ordem.verify(categoriaRepository).saveAll(categoriasCaptor.capture());
        ordem.verify(leitorRepository).saveAll(leitoresCaptor.capture());
        ordem.verify(livroRepository).saveAll(livrosCaptor.capture());
        ordem.verify(emprestimoRepository).saveAll(emprestimosCaptor.capture());

        List<Livro> livros = livrosCaptor.getValue();
        List<Emprestimo> emprestimos = emprestimosCaptor.getValue();
        assertEquals(3, autoresCaptor.getValue().size());
        assertEquals(2, categoriasCaptor.getValue().size());
        assertEquals(3, leitoresCaptor.getValue().size());
        assertEquals(5, livros.size());
        assertEquals(2, emprestimos.size());
        assertTrue(livros.stream().allMatch(livro -> autoresCaptor.getValue().contains(livro.getAutor())
                && categoriasCaptor.getValue().contains(livro.getCategoria())
                && livro.getQuantidadeDisponivel() >= 0));

        Emprestimo ativo = emprestimos.get(0);
        Emprestimo devolvido = emprestimos.get(1);
        assertSame(livros.get(0), ativo.getLivro());
        assertTrue(leitoresCaptor.getValue().contains(ativo.getLeitor()));
        assertNull(ativo.getDataDevolucao());
        assertEquals(4, ativo.getLivro().getQuantidadeDisponivel());
        assertEquals(ativo.getDataEmprestimo().toLocalDate().plusDays(30), ativo.getPrazoDevolucao());
        assertSame(livros.get(2), devolvido.getLivro());
        assertTrue(leitoresCaptor.getValue().contains(devolvido.getLeitor()));
        assertNotNull(devolvido.getDataDevolucao());
        assertTrue(devolvido.getDataDevolucao().isAfter(devolvido.getDataEmprestimo()));
        assertEquals(4, devolvido.getLivro().getQuantidadeDisponivel());
    }

    @Test
    void naoRepeteCargaEmUmaNovaInicializacao() {
        when(autorRepository.count()).thenReturn(0L, 3L);

        config.run();
        config.run();

        verify(autorRepository, times(1)).saveAll(any());
        verify(categoriaRepository, times(1)).saveAll(any());
        verify(livroRepository, times(1)).saveAll(any());
        verify(leitorRepository, times(1)).saveAll(any());
        verify(emprestimoRepository, times(1)).saveAll(any());
    }
}
