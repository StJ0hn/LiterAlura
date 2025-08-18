package com.challange.literaAlura.dto;

import com.fasterxml.jackson.annotation.JsonAlias;

import java.util.List;

public record ResultadosDTO(
        @JsonAlias("count") Integer contagem,
        @JsonAlias("next") String proximo,
        @JsonAlias("previous") String anterior,
        @JsonAlias("results") List<LivroDTO> resultados
        ) {
}
