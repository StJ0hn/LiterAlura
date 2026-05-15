# LiterAlura (Catálogo de Livros e Integração de API)

Aplicação em interface de linha de comando (CLI) desenvolvida em Java para consumo de API externa, estruturação de catálogo literário e persistência em banco de dados relacional.

## Objetivo
O projeto atua como desafio prático de backend para o programa Oracle Next Education (ONE). O foco técnico central é a orquestração do ecossistema Spring para realizar requisições HTTP a serviços de terceiros (Gutendex API), desserializar as respostas JSON em objetos de transferência de dados (DTOs) e mapeá-los para entidades relacionais persistidas no PostgreSQL via Hibernate.

## Stack Tecnológico
* Java 17+
* Spring Boot
* Spring Data JPA (ORM)
* PostgreSQL
* Desserialização: Jackson Databind
* Gerenciamento de Dependências: Maven

## Arquitetura e Funcionalidades Principais
A aplicação funciona como um cliente consumidor de dados e um gerenciador de acervo local:
* Consumo Dinâmico: Integração com a API do Project Gutenberg para busca de obras literárias por título.
* Mapeamento Objeto-Relacional: Estruturação de entidades bidirecionais (Livros e Autores) para persistência consistente no banco de dados.
* Consultas Customizadas (JPQL): Filtragem avançada de registros, permitindo a extração de dados específicos, como a listagem de autores vivos em um determinado ano ou a filtragem de obras por idioma.
* Interface CLI: Menu interativo em console para orquestração das operações de busca, registro e leitura.

## Como Executar Localmente

1. Clone o repositório:
git clone https://github.com/StJ0hn/Challange-Alura-Literalura.git
cd literaAlura

2. Provisione o banco de dados:
Crie um banco de dados no PostgreSQL nomeado `literalura_db`.

3. Configure as credenciais de acesso:
No diretório `src/main/resources/`, localize o arquivo `application.properties` e insira as credenciais do seu ambiente local:
spring.datasource.url=jdbc:postgresql://localhost:5432/literalura_db
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha

4. Compile e execute a aplicação:
./mvnw spring-boot:run
(A aplicação será inicializada e o menu interativo será renderizado diretamente no terminal).

---
Autor: John Miguel da Silva Fernandes
