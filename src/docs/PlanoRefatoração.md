# Plano de Refatoração — LiterAlura (ChallengeAluraLiteralura)

- Escopo preservado: Aplicação CLI que consome a API pública Gutendex e persiste localmente em PostgreSQL. Sem expansão de funcionalidades — apenas melhoria de arquitetura, boas práticas e correção de bugs estruturais.

## Sprint 0 — Diagnóstico (concluído)

### Problemas identificados:
1. Principal.java viola SRP — mistura apresentação (menu/console), orquestração HTTP, conversão de dados e regra de negócio.
2. Acoplamento alto: ConsumoAPI e ConverteDados são instanciadas com new dentro de Principal, quebrando o padrão de injeção de dependência usado no restante do projeto.
3. Bug real: Autor e Livro mapeiam para a mesma tabela (@Table(name = "livros") em ambas as entidades).

4. Falta uma camada de negócio corretamente delimitada — o pacote service existe, mas mistura infraestrutura técnica (ConsumoAPI, ConverteDados) com o que deveria ser regra de negócio (find-or-create de Autor).

5. Lógica de "find or create autor" está hoje dentro de Principal, quando deveria estar na camada de serviço.

6. Zero rede de segurança: nenhum teste automatizado, nenhuma verificação manual documentada do comportamento atual.

## Sprint 1 — Rede de segurança mínima (antes de qualquer refatoração)

- Objetivo: Ter como confirmar, depois de cada mudança estrutural, que o comportamento observável não mudou.

- [ ] Task 1.1: Documentar manualmente o comportamento atual de cada opção do menu (input → output esperado no console e no banco), para cada uma das 5 opções funcionais.

- [ ] Task 1.2: Decidir se, neste momento do seu nível de conhecimento, vale escrever 1–2 testes automatizados simples (ex: um teste de repositório) ou se o checklist manual da Task 1.1 é suficiente por ora.

Documentação a consultar:
- Spring Boot Testing — visão geral (@SpringBootTest, @DataJpaTest)

## Sprint 2 — Correção do modelo de dados

- Objetivo: Corrigir o mapeamento entidade-tabela e decidir a estratégia de schema, sem tocar em camadas de serviço.

- [ ] Task 2.1: Corrigir o mapeamento de tabela da entidade Autor (hoje aponta para o nome de tabela errado).

- [ ] Task 2.2: Decidir o que fazer com a tabela física já existente no seu PostgreSQL local antes de subir a aplicação novamente (você já concluiu que ddl-auto=update não apaga automaticamente).

- [ ] Task 2.3: Revisar se ddl-auto=update é a estratégia adequada para o momento atual do projeto (ambiente local de estudo) ou se outra opção se encaixa melhor.

- [ ] Task 2.4 (opcional): Esboçar o Diagrama de Entidade-Relacionamento (DER) já com a correção aplicada. (Recomendado antes do Sprint 5)

Documentação a consultar:
- Hibernate — estratégias de hbm2ddl.auto (create, update, validate, none)
- Spring Data JPA — mapeamento de entidades (@Entity, @Table, @Column)

## Sprint 3 — Introdução da camada de Service

- Objetivo: Mover a regra de negócio (find-or-create de Autor, orquestração da busca) para fora de Principal, com injeção de dependência.

- [ ] Task 3.1: Criar uma classe de serviço (ou mais de uma, caso você conclua que infraestrutura técnica e regra de negócio merecem classes separadas dentro de service) responsável por orquestrar a busca de livro.

- [ ] Task 3.2: Transformar ConsumoAPI e ConverteDados em beans gerenciados pelo Spring (em vez de new), e injetá-los via construtor onde forem necessários.

- [ ] Task 3.3: Mover a lógica de "find or create autor" para dentro da nova camada de serviço.

- [ ] Task 3.4: Validar com o checklist do Sprint 1 que o comportamento não mudou.

Documentação a consultar:

- Spring Framework — Injeção de Dependência e estereótipos (@Component, @Service, @Repository)

- Spring Framework — @Service vs @Component (visão geral de estereótipos)

## Sprint 4 — Redução da Principal à sua responsabilidade real

- Objetivo: Principal deve restar responsável apenas por apresentação (menu, leitura de input, impressão de output), delegando tudo o mais à camada de serviço criada no Sprint 3.

- [ ] Task 4.1: Revisar cada método de Principal e mover para o serviço tudo que não for "ler input" ou "imprimir output".

- [ ] Task 4.2: Validar novamente com o checklist do Sprint 1.
Documentação a consultar:

- Nenhuma nova — este sprint é aplicação direta dos conceitos já revisados nos Sprints 2 e 3. Consulta-se a documentação de DI novamente apenas se surgir dúvida pontual de injeção.

## Sprint 5 — Documentação do projeto (agora que a estrutura está estável)

- Objetivo: Produzir a documentação do projeto em si — não de ferramentas — agora que a arquitetura parou de mudar.

- [ ] Task 5.1: Diagrama de Entidade-Relacionamento (DER) final, refletindo o modelo já corrigido.

- [ ] Task 5.2: Documento de casos de uso, um por opção do menu (ator único: usuário do CLI).

- [ ] Task 5.3 (opcional): Diagrama de pacotes/camadas, mostrando a separação apresentação → serviço → repositório → modelo.

Documentação a consultar:

- Nenhuma de ferramenta — este sprint é sobre modelar o que foi construído, não sobre aprender uma tecnologia nova.

## Sprint 6 (opcional) — Testes automatizados

- [ ] Task 6.1: Escrever testes para a camada de serviço criada no Sprint 3 (especialmente a lógica de find-or-create).

- [ ] Task 6.2: Escrever testes de repositório para as queries customizadas (findByIdioma, findByAnoDeNascimentoLessThanEqualAndAnoDeFalecimentoGreaterThanEqual).

Documentação a consultar:

- Spring Boot — @DataJpaTest e testes de repositório

- Mockito (para mockar ConsumoAPI nos testes de serviço)

Observação sobre a ordem

A ordem acima segue o critério de dependência (schema antes de serviço, serviço antes de reduzir `Principal`, documentação depois de estabilizar) e risco (mexer no banco primeiro, isolado, antes de reorganizar código em memória).