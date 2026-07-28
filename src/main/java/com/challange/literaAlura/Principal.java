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
import com.challange.literaAlura.service.LivroService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@Component
public class Principal {
    private final Scanner leitura = new Scanner(System.in);
    private final LivroRepository livroRepository;
    private final AutorRepository autorRepository;
    private final LivroService livroService;

    public Principal(LivroRepository livroRepository, AutorRepository autorRepository, LivroService livroService) {
        this.livroRepository = livroRepository;
        this.autorRepository = autorRepository;
        this.livroService = livroService;
    }

    public void exibeMenu() {
        var opcao = -1;
        while (opcao != 0) {
            String menu = "╔════════════════════════════════════════╗\n" +
                       "║       LITERALURA CATÁLOGO              ║\n" +
                       "╠════════════════════════════════════════╣\n" +
                       "║ 1 - Buscar livro por título            ║\n" +
                       "║ 2 - Listar livros registrados          ║\n" +
                       "║ 3 - Listar autores registrados         ║\n" +
                       "║ 4 - Listar autores vivos em um det. ano║\n" +
                       "║ 5 - Listar livros em um det. idioma    ║\n" +
                       "║                                        ║\n" +
                       "║ 0 - Sair                               ║\n" +
                       "╚════════════════════════════════════════╝\n" +
                       "Escolha uma opção: ";

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
        String nomeLivro = leitura.nextLine();
        Livro livroSalvo = livroService.buscarESalvarLivros(nomeLivro);
        if (livroSalvo != null){
            System.out.println("\n--- Livro Encontrado ---");
            String nomeAutor = (livroSalvo.getAutor() != null) ? livroSalvo.getAutor().getNome() : "Autor Desconhecido";
            System.out.println("Título: " + livroSalvo.getTitulo());
            System.out.println("Autor: " + nomeAutor);
            System.out.println("Idioma: " + livroSalvo.getIdioma());
            System.out.println("Downloads: " + livroSalvo.getNumeroDeDownloads());
            System.out.println("-----------------------\n");
        }

        else {
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
                System.out.println("--------------------------\n");
            });
        }
    }

    private void listarAutoresVivosEmDeterminadoAno() {
        System.out.println("Digite o ano que deseja pesquisar:");
        int ano = leitura.nextInt();
        leitura.nextLine();

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
        String idioma = leitura.nextLine();

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