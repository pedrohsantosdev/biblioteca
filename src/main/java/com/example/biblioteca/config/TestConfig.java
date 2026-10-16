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
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Configuration
public class TestConfig implements CommandLineRunner {

    private final AutorRepository autorRepository;
    private final CategoriaRepository categoriaRepository;
    private final LivroRepository livroRepository;
    private final LeitorRepository leitorRepository;
    private final EmprestimoRepository emprestimoRepository;

    public TestConfig(AutorRepository autorRepository, CategoriaRepository categoriaRepository,
                      LivroRepository livroRepository, LeitorRepository leitorRepository,
                      EmprestimoRepository emprestimoRepository) {
        this.autorRepository = autorRepository;
        this.categoriaRepository = categoriaRepository;
        this.livroRepository = livroRepository;
        this.leitorRepository = leitorRepository;
        this.emprestimoRepository = emprestimoRepository;
    }

    @Override
    @Transactional
    public void run(String... args) {
        if (autorRepository.count() > 0
                || categoriaRepository.count() > 0
                || livroRepository.count() > 0
                || leitorRepository.count() > 0
                || emprestimoRepository.count() > 0) {
            return;
        }

        Autor machado = new Autor(null, "Machado de Assis");
        Autor clarice = new Autor(null, "Clarice Lispector");
        Autor graciliano = new Autor(null, "Graciliano Ramos");
        autorRepository.saveAll(List.of(machado, clarice, graciliano));

        Categoria romance = new Categoria(null, "Romance");
        Categoria contos = new Categoria(null, "Contos");
        categoriaRepository.saveAll(List.of(romance, contos));

        Leitor ana = new Leitor(null, "Ana Silva", "11900000001", "ana@example.com");
        Leitor bruno = new Leitor(null, "Bruno Souza", "11900000002", "bruno@example.com");
        Leitor carla = new Leitor(null, "Carla Oliveira", "11900000003", "carla@example.com");
        leitorRepository.saveAll(List.of(ana, bruno, carla));

        Livro domCasmurro = new Livro(null, "Dom Casmurro", 5, machado, romance);
        Livro memoriasPostumas = new Livro(null, "Memórias Póstumas de Brás Cubas", 3, machado, romance);
        Livro horaDaEstrela = new Livro(null, "A Hora da Estrela", 4, clarice, romance);
        Livro lacosDeFamilia = new Livro(null, "Laços de Família", 2, clarice, contos);
        Livro vidasSecas = new Livro(null, "Vidas Secas", 3, graciliano, romance);

        LocalDateTime agora = LocalDateTime.now();
        LocalDateTime dataEmprestimoAtivo = agora.minusDays(5);
        LocalDateTime dataEmprestimoDevolvido = agora.minusDays(20);

        Emprestimo emprestimoAtivo = new Emprestimo(null, domCasmurro, ana,
                dataEmprestimoAtivo, dataEmprestimoAtivo.toLocalDate().plusDays(30), null);
        Emprestimo emprestimoDevolvido = new Emprestimo(null, horaDaEstrela, bruno,
                dataEmprestimoDevolvido, dataEmprestimoDevolvido.toLocalDate().plusDays(30), agora.minusDays(10));

        // Apenas o empréstimo ainda em aberto reduz a quantidade disponível.
        domCasmurro.setQuantidadeDisponivel(domCasmurro.getQuantidadeDisponivel() - 1);
        livroRepository.saveAll(List.of(domCasmurro, memoriasPostumas, horaDaEstrela, lacosDeFamilia, vidasSecas));
        emprestimoRepository.saveAll(List.of(emprestimoAtivo, emprestimoDevolvido));
    }
}
