package com.challange.literaAlura.service;

import com.challange.literaAlura.model.Autor;
import com.challange.literaAlura.repository.AutorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AutorService {
    private final AutorRepository autorRepository;

    public AutorService(AutorRepository autorRepository){
        this.autorRepository = autorRepository;
    }

    public List<Autor> obterTodosOsAutores(){
        return autorRepository.findAll();
    }

    public Optional<Autor> encontrarPorNome(String nome) { return autorRepository.findByNome(nome); }

    public List<Autor> encontrarPorAnoNascimento(Integer anoNascimento){
        return autorRepository.findByAnoDeNascimento(anoNascimento);
    }
}
