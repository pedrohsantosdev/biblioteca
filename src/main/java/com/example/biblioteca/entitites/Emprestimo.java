package com.example.biblioteca.entitites;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Emprestimo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "livro_id")
    private Livro livro;

    @ManyToOne(optional = false)
    @JoinColumn(name = "leitor_id")
    private Leitor leitor;

    @Column(name = "dataEmprestimo")
    private LocalDateTime dataEmprestimo;

    @Column(name = "prazoDevolucao")
    private LocalDate prazoDevolucao;

    @Column(name = "dataDevolucao")
    private LocalDateTime dataDevolucao;

}
