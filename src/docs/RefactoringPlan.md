# **Refactoring Plan — LiterAlura (ChallengeAluraLiteralura)**

> * **Preserved scope:** CLI application that consumes the Gutendex public API and persists locally in PostgreSQL. No expansion of features—only architecture improvements, best practices, and structural bug fixes.

## **Sprint 0 — Diagnosis (completed)**

### **Identified issues:**

> 1. Principal.java violates SRP — it mixes presentation (menu/console), HTTP orchestration, data conversion, and business rules.
> 2. High coupling: ConsumoAPI and ConverteDados are instantiated using new inside Principal, breaking the dependency injection pattern used in the rest of the project.
> 3. Real bug: Autor and Livro map to the same table (@Table(name \= "livros") in both entities).
> 4. Missing a properly defined business layer—the service package exists but mixes technical infrastructure (ConsumoAPI, ConverteDados) with what should be a business rule (find-or-create Author).
> 5. The "find or create author" logic is currently inside Principal, when it should be in the service layer.
> 6. Zero safety net: no automated tests, no documented manual verification of the current behavior.

## **Sprint 1 — Minimum safety net (before any refactoring)**

> * **Objective:** To be able to confirm, after each structural change, that the observable behavior has not changed.
- [x] **Task 1.1:** Manually document the current behavior of each menu option (input → expected output in the console and the database), for each of the 5 functional options.
- [ ] **Task 1.2:** Decide whether, at your current knowledge level, it is worth writing 1–2 simple automated tests (e.g., a repository test) or if the manual checklist from Task 1.1 is sufficient for now.

**Documentation to consult:**

> * Spring Boot Testing — overview (@SpringBootTest, @DataJpaTest)

## **Sprint 2 — Data model correction**

> * **Objective:** Fix the entity-table mapping and decide on the schema strategy, without touching the service layers.
- [ ] **Task 2.1:** Fix the table mapping for the Autor entity (it currently points to the wrong table name).
- [ ] **Task 2.2:** Decide what to do with the physical table that already exists in your local PostgreSQL before running the application again (you've already concluded that ddl-auto=update does not delete automatically).
- [ ] **Task 2.3:** Review if ddl-auto=update is the appropriate strategy for the current stage of the project (local study environment) or if another option fits better.
- [ ] **Task 2.4 (optional):** Draft the Entity-Relationship Diagram (ERD) with the fix already applied. (Recommended before Sprint 5\)

**Documentation to consult:**

> * Hibernate — hbm2ddl.auto strategies (create, update, validate, none)
> * Spring Data JPA — entity mapping (@Entity, @Table, @Column)

## **Sprint 3 — Introduction of the Service layer**

> * **Objective:** Move the business rule (find-or-create Author, search orchestration) out of Principal, using dependency injection.
- [ ] **Task 3.1:** Create a service class (or more than one, if you conclude that technical infrastructure and business rules deserve separate classes within service) responsible for orchestrating the book search.
- [ ] **Task 3.2:** Transform ConsumoAPI and ConverteDados into Spring-managed beans (instead of new), and inject them via constructor wherever they are needed.
- [ ] **Task 3.3:** Move the "find or create author" logic into the new service layer.
- [ ] **Task 3.4:** Validate with the Sprint 1 checklist that the behavior has not changed.

**Documentation to consult:**

> * Spring Framework — Dependency Injection and stereotypes (@Component, @Service, @Repository)
> * Spring Framework — @Service vs @Component (stereotype overview)

## **Sprint 4 — Reduction of Principal to its actual responsibility**

> * **Objective:** Principal should remain responsible only for presentation (menu, reading input, printing output), delegating everything else to the service layer created in Sprint 3\.
- [ ] **Task 4.1:** Review each method in Principal and move everything that is not "read input" or "print output" to the service.
- [ ] **Task 4.2:** Validate again using the Sprint 1 checklist.

**Documentation to consult:**

> * None required — this sprint is a direct application of the concepts already reviewed in Sprints 2 and 3\. Consult DI documentation again only if a specific injection question arises.

## **Sprint 5 — Project Documentation (now that the structure is stable)**

> * **Objective:** Produce the documentation for the project itself—not for the tools—now that the architecture has stopped changing.
- [ ] **Task 5.1:** Final Entity-Relationship Diagram (ERD), reflecting the corrected model.
- [ ] **Task 5.2:** Use Case document, one per menu option (single actor: CLI user).
- [ ] **Task 5.3 (optional):** Package/Layer diagram, showing the separation between presentation → service → repository → model.

**Documentation to consult:**

> * No tool documentation needed — this sprint is about modeling what you have already built, not about learning a new technology.

## **Sprint 6 (optional) — Automated tests**

- [ ] **Task 6.1:** Write tests for the service layer created in Sprint 3 (especially the find-or-create logic).
- [ ] **Task 6.2:** Write repository tests for custom queries (findByIdioma, findByAnoDeNascimentoLessThanEqualAndAnoDeFalecimentoGreaterThanEqual).

**Documentation to consult:**

> * Spring Boot — @DataJpaTest and repository tests
> * Mockito (to mock ConsumoAPI in service tests)

**Note on the order:**  
The order above follows dependency criteria (schema before service, service before reducing Principal, documentation after stabilizing) and risk (touching the database first, isolated, before reorganizing code in memory). If, while working on a sprint, you realize that a task depends on another not yet done, stop and adjust the order — the plan is a guide, not a straitjacket.