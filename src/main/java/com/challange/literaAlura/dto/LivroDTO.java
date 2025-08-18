package com.challange.literaAlura.dto;

import com.fasterxml.jackson.annotation.JsonAlias;

import java.util.List;

public record LivroDTO(
        @JsonAlias("title") String titulo,
        @JsonAlias("authors") List<AutorDTO> autores,
        @JsonAlias("languages") List<String> idiomas,
        @JsonAlias("download_count") Double numeroDeDownloads){

}
