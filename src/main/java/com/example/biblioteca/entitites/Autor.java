package com.example.biblioteca.entitites;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Autor implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome")
    private String nome;

    @JsonIgnore
    @OneToMany(mappedBy = "autor")
    private List<Livro> livros = new ArrayList<>();

    public Autor(Long id, String nome) {
        this.id = id;
        this.nome = nome;
    }
}
