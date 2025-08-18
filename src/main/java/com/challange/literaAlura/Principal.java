package com.challange.literaAlura;

import com.challange.literaAlura.dto.AutorDTO;
import com.challange.literaAlura.dto.LivroDTO;
import com.challange.literaAlura.dto.ResultadosDTO;
import com.challange.literaAlura.model.Autor;
import com.challange.literaAlura.model.Livro;
import com.challange.literaAlura.repository.AutorRepository;
import com.challange.literaAlura.repository.LivroRepository;
import com.challange.literaAlura.service.ConsumoAPI;
import com.challange.literaAlura.service.ConverteDados;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@Component
public class Principal {
    private final Scanner leitura = new Scanner(System.in);
    private final ConsumoAPI consumo = new ConsumoAPI();
    private final ConverteDados conversor = new ConverteDados();
    private final String ENDERECO_BASE = "https://gutendex.com/books/";
    private final LivroRepository livroRepository;
    private final AutorRepository autorRepository;

    public Principal(LivroRepository livroRepository, AutorRepository autorRepository) {
        this.livroRepository = livroRepository;
        this.autorRepository = autorRepository;
    }

    public void exibeMenu() {
        var opcao = -1;
        while (opcao != 0) {
            var menu = """
                    ╔════════════════════════════════════════╗
                    ║       LITERALURA CATÁLOGO              ║
                    ╠════════════════════════════════════════╣
                    ║ 1 - Buscar livro por título            ║
                    ║ 2 - Listar livros registrados          ║
                    ║ 3 - Listar autores registrados         ║
                    ║ 4 - Listar autores vivos em um det. ano║
                    ║ 5 - Listar livros em um det. idioma    ║
                    ║                                        ║
                    ║ 0 - Sair                               ║
                    ╚════════════════════════════════════════╝
                    Escolha uma opção: """;

            System.out.print(menu);
            opcao = leitura.nextInt();
            leitura.nextLine();

            switch (opcao) {
                case 1:
                    buscarLivroPorTitulo();
                    break;
                case 2:
                    listarLivrosRegistrados();
                    break;
                case 3:
                    listarAutoresRegistrados();
                    break;
                case 4:
                    listarAutoresVivosEmDeterminadoAno();
                    break;
                case 5:
                    listarLivrosPorIdioma();
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        }
    }

    private void buscarLivroPorTitulo() {
        System.out.println("Digite o nome do livro que você deseja buscar:");
        var nomeLivro = leitura.nextLine();
        var enderecoBusca = ENDERECO_BASE + "?search=" + nomeLivro.replace(" ", "%20");
        var json = consumo.obterDados(enderecoBusca);
        ResultadosDTO dados = conversor.obterDados(json, ResultadosDTO.class);

        if (dados != null && !dados.resultados().isEmpty()) {
            LivroDTO livroDTO = dados.resultados().get(0);

            Livro livro = new Livro();
            livro.setTitulo(livroDTO.titulo());

            if (!livroDTO.autores().isEmpty()) {
                AutorDTO autorDTO = livroDTO.autores().get(0);
                Optional<Autor> autorExistente = autorRepository.findByNomeContainingIgnoreCase(autorDTO.nome());
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

            System.out.println("\n--- Livro Encontrado ---");
            String nomeAutor = (livro.getAutor() != null) ? livro.getAutor().getNome() : "Autor Desconhecido";
            System.out.println("Título: " + livro.getTitulo());
            System.out.println("Autor: " + nomeAutor);
            System.out.println("Idioma: " + livro.getIdioma());
            System.out.println("Downloads: " + livro.getNumeroDeDownloads());
            System.out.println("-----------------------\n");
        } else {
            System.out.println("Livro não encontrado.");
        }
    }

    private void listarLivrosRegistrados() {
        List<Livro> livros = livroRepository.findAll();
        if (livros.isEmpty()) {
            System.out.println("Nenhum livro registrado ainda.");
        } else {
            System.out.println("\n--- LIVROS REGISTRADOS ---");
            livros.forEach(livro -> {
                String nomeAutor = (livro.getAutor() != null) ? livro.getAutor().getNome() : "Autor Desconhecido";
                System.out.println("Título: " + livro.getTitulo());
                System.out.println("Autor: " + nomeAutor);
                System.out.println("Idioma: " + livro.getIdioma());
                System.out.println("Downloads: " + livro.getNumeroDeDownloads());
                System.out.println("--------------------------\n");
            });
        }
    }

    private void listarAutoresRegistrados() {
        List<Autor> autores = autorRepository.findAll();

        if (autores.isEmpty()) {
            System.out.println("Nenhum autor registrado ainda.");
        } else {
            System.out.println("\n--- AUTORES REGISTRADOS ---");
            autores.forEach(autor -> {
                System.out.println("Nome: " + autor.getNome());
                System.out.println("Ano de Nascimento: " + autor.getAnoDeNascimento());
                System.out.println("Ano de Falecimento: " + autor.getAnoDeFalecimento());
                // Aqui podemos adicionar a lógica para listar os livros do autor no futuro
                System.out.println("--------------------------\n");
            });
        }
    }

    private void listarAutoresVivosEmDeterminadoAno() {
        System.out.println("Digite o ano que deseja pesquisar:");
        var ano = leitura.nextInt();
        leitura.nextLine(); // Limpa o buffer do teclado

        List<Autor> autores = autorRepository.findByAnoDeNascimentoLessThanEqualAndAnoDeFalecimentoGreaterThanEqual(ano, ano);

        if (autores.isEmpty()) {
            System.out.println("Nenhum autor vivo registrado para o ano de " + ano);
        } else {
            System.out.println("\n--- Autores Vivos em " + ano + " ---");
            autores.forEach(autor -> {
                System.out.println("Nome: " + autor.getNome());
                System.out.println("Ano de Nascimento: " + autor.getAnoDeNascimento());
                System.out.println("Ano de Falecimento: " + autor.getAnoDeFalecimento());
                System.out.println("--------------------------\n");
            });
        }
    }

    private void listarLivrosPorIdioma() {
        System.out.println("Digite o idioma para a busca (ex: pt, en, es, fr):");
        var idioma = leitura.nextLine();

        List<Livro> livros = livroRepository.findByIdioma(idioma);

        if (livros.isEmpty()) {
            System.out.println("Nenhum livro encontrado para o idioma: " + idioma);
        } else {
            System.out.println("\n--- Livros no idioma: " + idioma + " ---");
            livros.forEach(livro -> {
                String nomeAutor = (livro.getAutor() != null) ? livro.getAutor().getNome() : "Autor Desconhecido";
                System.out.println("Título: " + livro.getTitulo());
                System.out.println("Autor: " + nomeAutor);
                System.out.println("--------------------------\n");
            });
        }
    }
}