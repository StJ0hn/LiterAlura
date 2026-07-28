package com.challange.literaAlura.repository;

import com.challange.literaAlura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AutorRepository extends JpaRepository<Autor, Long> {
    Optional<Autor> findByNome(String nome);
    List<Autor> findByAnoDeNascimento(Integer anoNascimento);
}
