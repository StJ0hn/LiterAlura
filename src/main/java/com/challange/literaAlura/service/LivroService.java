package com.challange.literaAlura.service;

import com.challange.literaAlura.dto.AutorDTO;
import com.challange.literaAlura.dto.LivroDTO;
import com.challange.literaAlura.dto.ResultadosDTO;
import com.challange.literaAlura.model.Autor;
import com.challange.literaAlura.model.Livro;
import com.challange.literaAlura.repository.AutorRepository;
import com.challange.literaAlura.repository.LivroRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LivroService {
    private final String ENDERECO_BASE = "https://gutendex.com/books/";
    private final ConsumoAPI consumoAPI;
    private final ConverteDados converteDados;
    private final LivroRepository livroRepository;
    private final AutorService autorService;


    public LivroService(ConsumoAPI consumoAPI, ConverteDados converteDados, LivroRepository livroRepository, AutorService autorService){
        this.consumoAPI = consumoAPI;
        this.converteDados = converteDados;
        this.livroRepository = livroRepository;
        this.autorService = autorService;
    }

    public Livro buscarESalvarLivros (String nomeLivro){
        String enderecoBusca = ENDERECO_BASE + "?search=" + nomeLivro.replace(" ", "%20");
        String json = consumoAPI.obterDados(enderecoBusca);
        ResultadosDTO dados = converteDados.obterDados(json, ResultadosDTO.class);

        if (dados != null && !dados.resultados().isEmpty()) {
            LivroDTO livroDTO = dados.resultados().get(0);

            Livro livro = new Livro();
            livro.setTitulo(livroDTO.titulo());

            if (!livroDTO.autores().isEmpty()) {
                AutorDTO autorDTO = livroDTO.autores().get(0);
                Optional<Autor> autorExistente = autorService.encontrarPorNome(autorDTO.nome());
                Autor autor;
                if (autorExistente.isPresent()) {
                    autor = autorExistente.get();
                } else {
                    autor = new Autor();
                    autor.setNome(autorDTO.nome());
                    autor.setAnoDeNascimento(autorDTO.anoDeNascimento());
                    autor.setAnoDeFalecimento(autorDTO.anoDeFalecimento());
                }
                livro.setAutor(autor);
            } else {
                livro.setAutor(null);
            }

            livro.setIdioma(livroDTO.idiomas().get(0));
            livro.setNumeroDeDownloads(livroDTO.numeroDeDownloads());

            try {
                livroRepository.save(livro);
                System.out.println("Livro '" + livro.getTitulo() + "' salvo com sucesso!");
            } catch (Exception e) {
                System.out.println("Atenção: Este livro já foi salvo anteriormente.");
            }
            return livro;
        }
        return null;
    }

    public List<Livro> obterTodosOsLivros() { return livroRepository.findAll(); }

    public List<Livro> obterLivrosPorIdioma(String idioma){
        return livroRepository.findByIdioma(idioma);
    }
}
