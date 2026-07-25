# LiterAlura

> A Java command-line application for searching, cataloging, and managing books through the Gutendex API with persistent storage in PostgreSQL.

---

## Technologies

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)
![Spring Data JPA](https://img.shields.io/badge/Spring_Data_JPA-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
![Hibernate](https://img.shields.io/badge/Hibernate-59666C?style=for-the-badge&logo=hibernate&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-4169E1?style=for-the-badge&logo=postgresql&logoColor=white)
![Jackson](https://img.shields.io/badge/Jackson-000000?style=for-the-badge)
![Maven](https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white)

---

## Overview

LiterAlura is a backend application developed as part of the **Oracle Next Education (ONE)** program. The project demonstrates the integration of a Spring Boot application with an external REST API, transforming JSON responses into domain entities and persisting them in a relational database using JPA/Hibernate.

The application acts as a local literary catalog, allowing users to search books from the **Gutendex API**, store them in PostgreSQL, and perform custom queries through an interactive command-line interface.

---

## Features

- Search books by title using the Gutendex API.
- Persist books and authors in a PostgreSQL database.
- Automatically deserialize JSON responses using Jackson.
- List all registered books.
- List all registered authors.
- Filter authors alive in a specific year.
- Filter books by language.
- Interactive command-line interface (CLI).

---

## Requirements

- Java 17 or later
- Maven 3.9+
- PostgreSQL
- Internet connection (required for API requests)

---

## Installation

Clone the repository:

```bash
git clone https://github.com/StJ0hn/LiterAlura.git
cd Literalura
```

Create a PostgreSQL database:

```text
literalura_db
```

Configure your database credentials in:

```text
src/main/resources/application.properties
```

Example:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/literalura_db
spring.datasource.username=your_username
spring.datasource.password=your_password
```

Build and run the application:

```bash
./mvnw spring-boot:run
```

---

## Usage

After the application starts, an interactive command-line menu will be displayed.

Available operations include:

- Search books by title
- List all registered books
- List registered authors
- Search authors alive in a given year
- Filter books by language

All retrieved books are automatically persisted in the local PostgreSQL database.

---

## Architecture

The application follows a layered architecture based on the Spring ecosystem.

```text
                    Command-Line Interface (CLI)
                                  │
                                  ▼
                            Service Layer
                            │           │
                            ▼           ▼
                    Gutendex API    Repository Layer
                       (HTTP)           │
                                        ▼
                            PostgreSQL Database (JPA)
```
### Design Decisions

#### External API Integration

The application consumes the Gutendex REST API through HTTP requests, allowing books to be searched dynamically without maintaining a proprietary catalog.

#### Object-Relational Mapping

Books and authors are represented as relational entities and persisted using Spring Data JPA with Hibernate.

#### JSON Deserialization

Jackson Databind is responsible for converting API responses into Data Transfer Objects (DTOs), which are then mapped into domain entities.

#### Interactive CLI

The presentation layer is entirely terminal-based, providing a lightweight interface for interacting with the catalog.

---

## Project Structure

```text
src/main/java/
├── dto/                 # Data Transfer Objects
├── model/               # Domain entities
├── repository/          # Spring Data repositories
├── service/             # Business logic and API integration
├── principal/           # CLI entry point
└── LiterAluraApplication.java
```

---

## References

- Gutendex API
- Spring Boot Documentation
- Spring Data JPA Documentation
- Hibernate ORM
- Jackson Databind
- PostgreSQL Documentation
- Oracle Next Education (ONE)

---

## License

This project was developed for educational purposes as part of the **Oracle Next Education (ONE)** program.

Feel free to study, modify, and adapt the code for learning purposes.
