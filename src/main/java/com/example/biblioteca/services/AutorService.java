package com.example.biblioteca.services;

import com.example.biblioteca.entitites.Autor;
import com.example.biblioteca.repositories.AutorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AutorService {

    private final AutorRepository autorRepository;

    public AutorService(AutorRepository autorRepository) {
        this.autorRepository = autorRepository;
    }

    public List<Autor> listarTodos() {
        return autorRepository.findAll();
    }

    public Autor buscarAutorPorId(Long id) {
        Optional<Autor> autor = autorRepository.findById(id);
        return autor.get();
    }

    public Autor cadastrarAutor(Autor autor) {
        return autorRepository.save(autor);
    }

    public Autor atualizarAutor(Long id, Autor novosDados) {
        Autor dadosAtuais = buscarAutorPorId(id);
        atualizarDados(dadosAtuais, novosDados);
        return autorRepository.save(dadosAtuais);
    }

    public void deletarAutor(Long id) {
        Autor autorDeletar = buscarAutorPorId(id);
        autorRepository.delete(autorDeletar);
    }

    private void atualizarDados(Autor dadosAtuais, Autor novosDados) {
        dadosAtuais.setNome(novosDados.getNome());
    }

}
