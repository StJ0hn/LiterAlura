# LiterAlura - Catálogo de Livros

![Status](https://img.shields.io/badge/status-concluído-brightgreen)

## 📖 Sobre o Projeto

**LiterAlura** é uma aplicação de catálogo de livros desenvolvida como parte do Alura Challenges ONE (Oracle Next Education). A aplicação permite ao usuário interagir via console para buscar livros através da API pública [Gutendex](https://gutendex.com/), uma API web para o Project Gutenberg. Os livros e autores pesquisados são persistidos em um banco de dados PostgreSQL para consultas futuras.

Este projeto foi construído para praticar e demonstrar habilidades em Java com o ecossistema Spring, incluindo o consumo de APIs, manipulação de JSON, e persistência de dados com Spring Data JPA.

## ✨ Funcionalidades

A aplicação oferece um menu interativo no console com as seguintes opções:

- **1. Buscar livro por título:** Consulta a API Gutendex pelo título do livro, exibe o primeiro resultado e salva o livro e seu autor no banco de dados.
- **2. Listar livros registrados:** Exibe todos os livros que foram salvos no banco de dados.
- **3. Listar autores registrados:** Exibe todos os autores que foram salvos no banco de dados.
- **4. Listar autores vivos em um determinado ano:** Pede um ano ao usuário e exibe uma lista de autores que estavam vivos naquele ano.
- **5. Listar livros em um determinado idioma:** Pede ao usuário um código de idioma (ex: pt, en) e exibe os livros registrados naquele idioma.
- **0. Sair:** Encerra a aplicação.

## 🛠️ Tecnologias Utilizadas

As seguintes ferramentas e tecnologias foram usadas na construção do projeto:

- **Java 17+**
- **Spring Boot**
- **Spring Data JPA**
- **PostgreSQL**
- **Maven**
- **Jackson Databind**
- **Gutendex API**

## 🚀 Como Executar o Projeto

**Pré-requisitos:**
- Java 17 ou superior
- Maven 3.8 ou superior
- PostgreSQL 13 ou superior

**1. Clone o Repositório:**
```bash
git clone [URL-DO-SEU-REPOSITORIO-GITHUB]
cd literaAlura
```
2. Configure o Banco de Dados:

- Crie um banco de dados no PostgreSQL chamado `literalura_db`.

- Abra o arquivo `src/main/resources/application.properties`.

- Altere as seguintes propriedades com suas credenciais do PostgreSQL:
```Properties
spring.datasource.url=jdbc:postgresql://localhost:5432/literalura_db
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
```
3. Execute a Aplicação:
Você pode executar a aplicação através da sua IDE ou via linha de comando com o Maven:
```bash
mvn spring-boot:run
```
A aplicação iniciará e o menu interativo será exibido no console.
👨‍💻 Autor
John Miguel Da Silva Fernandes

```bash
git clone [URL-DO-SEU-REPOSITORIO-GITHUB]
cd literaAlura
