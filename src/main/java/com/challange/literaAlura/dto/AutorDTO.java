package com.challange.literaAlura.dto;

import com.fasterxml.jackson.annotation.JsonAlias;

public record AutorDTO(
        @JsonAlias("name") String nome,
        @JsonAlias("birth_year") Integer anoDeNascimento,
        @JsonAlias("death_year") Integer anoDeFalecimento) {
}
