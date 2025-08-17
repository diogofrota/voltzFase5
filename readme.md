# CRUD de Criptomoedas - Projeto Faculdade

Este projeto consiste em um **sistema CRUD** (Create, Read, Update, Delete) desenvolvido em **Java** utilizando **JDBC** para conexão com banco de dados Oracle. O objetivo é praticar a implementação de operações básicas em banco de dados com boas práticas de programação.

---

## 📌 Funcionalidades Implementadas

1. **Cadastrar Criptomoeda**

   - Função: `cadastrarCriptomoeda()`
   - Permite inserir uma nova criptomoeda no banco com **nome**, **sigla** e **valor atual**.
   - O campo `ID_CRIPT` é preenchido automaticamente via *sequence* (`CRIPT_SEQ.NEXTVAL`).

2. **Atualizar Preço da Criptomoeda**

   - Função: `atualizarPrecoCriptomoeda()`
   - Solicita a sigla da criptomoeda e atualiza o campo `VALOR_ATUAL`.
   - Exibe mensagem caso a sigla informada não exista.

3. **Excluir Criptomoeda**

   - Função: `excluirCriptomoeda()`
   - Solicita a sigla da criptomoeda e remove o registro do banco.
   - Exibe mensagem confirmando a exclusão ou informando se não encontrou o registro.

4. **Listar Criptomoedas**

   - Função: `listarCriptomoedas()`
   - Exibe todos os registros cadastrados, mostrando **ID**, **nome**, **sigla** e **valor atual**.
   - Caso não haja registros, informa que não existem criptomoedas cadastradas.

---

## 🔑 Estrutura do Código

- **Classe **``

  - Centraliza a lógica de conexão com o banco.
  - Possui método `getConnection()` que retorna uma instância de `Connection`.
  - Encapsula tratamento de erro com `RuntimeException` em caso de falha na conexão.

- **Uso de **``

  - Todas as operações de banco utilizam o recurso ``, introduzido no Java 7.
  - Exemplo:
    ```java
    try (Connection conn = ConnectionFactory.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        // execução
    } catch (SQLException e) {
        System.err.println("Erro: " + e.getMessage());
    }
    ```
  - Vantagens:
    - Fecha automaticamente recursos como `Connection`, `PreparedStatement` e `ResultSet`.
    - Evita a necessidade de chamadas explícitas a `close()` no bloco `finally`.
    - Garante que os recursos sejam liberados mesmo em caso de exceções.

- **Tratamento de Exceções**

  - Cada função trata erros com `try-catch` e exibe mensagens amigáveis no console.
  - O erro original é logado em `System.err` para facilitar o diagnóstico.

---

## 🛠️ Scripts SQL

### 📂 Script DDL (Data Definition Language)

Responsável pela criação da tabela e da sequence:

```sql
CREATE TABLE CRIPTOMOEDA (
    ID_CRIPT NUMBER PRIMARY KEY,
    NOME VARCHAR2(100) NOT NULL,
    SIGLA VARCHAR2(10) NOT NULL,
    VALOR_ATUAL NUMBER(10,2) NOT NULL
);

CREATE SEQUENCE CRIPT_SEQ START WITH 1 INCREMENT BY 1 NOCACHE;
```

### 📂 Script DML (Data Manipulation Language)

Exemplos de manipulação de dados na tabela:

- **INSERT**

```sql
INSERT INTO CRIPTOMOEDA (ID_CRIPT, NOME, SIGLA, VALOR_ATUAL)
VALUES (CRIPT_SEQ.NEXTVAL, 'Bitcoin', 'BTC', 150000.00);
```

- **UPDATE**

```sql
UPDATE CRIPTOMOEDA SET VALOR_ATUAL = 155000.00 WHERE SIGLA = 'BTC';
```

- **DELETE**

```sql
DELETE FROM CRIPTOMOEDA WHERE SIGLA = 'BTC';
```

- **SELECT**

```sql
SELECT ID_CRIPT, NOME, SIGLA, VALOR_ATUAL FROM CRIPTOMOEDA;
```

---

## 🚀 Execução

1. Compilar o projeto no IntelliJ ou outra IDE Java.
2. Garantir que o **driver JDBC** está configurado no projeto.
3. Rodar a classe `Main` e interagir pelo **menu de opções**:
   - 1 → Cadastrar criptomoeda
   - 2 → Atualizar preço
   - 3 → Excluir criptomoeda
   - 4 → Listar criptomoedas
   - 0 → Sair

---

## 📖 Observações Importantes

- O uso de `` foi fundamental para simplificar o código e garantir que as conexões com o banco sejam sempre fechadas de forma segura.
- O sistema foi implementado com foco em clareza e boas práticas para uso em trabalhos acadêmicos e projetos introdutórios de persistência em banco.
- Pode ser facilmente expandido para incluir mais campos (ex.: data de criação, descrição, etc.) ou integrar com frameworks como Hibernate e Spring Boot.

---

# Scripts SQL (DDL & DML)

Para manter o banco de dados versionado junto com o projeto, foram criados arquivos `.sql` organizados nas pastas `db/ddl` e `db/dml`.

## Estrutura de pastas
```
db/
 ├─ ddl/
 │   ├─ V1__create_tables.sql
 │   └─ V2__constraints_indexes.sql
 └─ dml/
     ├─ seed_data.sql
     └─ test_queries.sql
```

## Ordem de execução
1. **V1__create_tables.sql** → cria as tabelas `CRIPTOMOEDA`, `USUARIO` e `APLICACAO`.  
2. **V2__constraints_indexes.sql** → adiciona as FKs e índices de performance.  
3. **seed_data.sql** → insere dados de exemplo para testes.  
4. **test_queries.sql** → consultas de validação para conferir os relacionamentos.

## Convenções adotadas
- **DDL** (Data Definition Language) → arquivos `Vx__descricao.sql`, versionados incrementalmente.  
- **DML** (Data Manipulation Language) → scripts de carga inicial (`seed_data`) e consultas de apoio (`test_queries`).  
- As **constraints** utilizam `ON DELETE CASCADE` para manter integridade quando um usuário ou criptomoeda for removido.  
- Senhas devem ser armazenadas como **hash** e não em texto simples (script usa placeholder `hash_da_senha`).

## Execução manual no Oracle SQL Developer
1. Conecte-se ao schema desejado.  
2. Execute os scripts na ordem descrita acima.  
3. Após rodar o `seed_data.sql`, use o `test_queries.sql` para validar se os relacionamentos estão funcionando corretamente.  

---

## 👨‍💻 Autor

Trabalho desenvolvido para a disciplina de **Engenharia de Software / Banco de Dados**, com objetivo de praticar a criação de sistemas CRUD em Java utilizando JDBC.

