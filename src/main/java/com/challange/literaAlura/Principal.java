package com.challange.literaAlura;

import com.challange.literaAlura.dto.LivroDTO;
import com.challange.literaAlura.dto.ResultadosDTO;
import com.challange.literaAlura.service.ConsumoAPI;
import com.challange.literaAlura.service.ConverteDados;

import java.util.Scanner;

public class Principal {
    // 1. Declarando nossos serviços como atributos
    private ConsumoAPI consumo = new ConsumoAPI();
    private ConverteDados conversor = new ConverteDados();

    private final String ENDERECO_BASE = "https://gutendex.com/books/";



    private Scanner leitura = new Scanner(System.in);

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
                    Escolha uma opção:  """;

            System.out.print(menu);
            opcao = leitura.nextInt();
            leitura.nextLine();

            switch (opcao) {
                case 1:
                    buscarLivroPorTitulo();
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 5:
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

        // Montando a URL de busca
        var enderecoBusca = ENDERECO_BASE + "?search=" + nomeLivro.replace(" ", "%20");

        // Chamando nossos serviços
        var json = consumo.obterDados(enderecoBusca);
        ResultadosDTO dados = conversor.obterDados(json, ResultadosDTO.class);

        // Opcional: Pegar apenas o primeiro livro da lista, se houver
        if (dados != null && !dados.resultados().isEmpty()) {
            LivroDTO livro = dados.resultados().get(0);
            System.out.println("\n--- Livro Encontrado ---");
            System.out.println("Título: " + livro.titulo());
            System.out.println("Autor: " + livro.autores().get(0).nome());
            System.out.println("Idioma: " + livro.idiomas().get(0));
            System.out.println("Downloads: " + livro.numeroDeDownloads());
            System.out.println("-----------------------\n");
        } else {
            System.out.println("Livro não encontrado.");
        }
    }
}