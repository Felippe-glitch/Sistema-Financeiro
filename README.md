# 📊 Sistema Financeiro - Backend

Este repositório contém a **API Backend** da aplicação de gerenciamento financeiro, desenvolvida em **Java** com **Spring Boot** e **SQL Server**.  
O sistema é um projeto da disciplina de Banco de Dados II da Universidade Vila Velha e simular a informatização do setor financeiro de uma empresa fictícia, permitindo o controle de clientes, fornecedores, lançamentos bancários, duplicatas e relatórios gerenciais.

---

## 🚀 Tecnologias Utilizadas
- **Java 17+**
- **Maven** 
- **Spring Boot**
  - **Spring Web** 
  - **Spring Data JPA** 
  - **Spring Security** 
  - **Spring Boot Starter Validation** 
  - **Spring Boot DevTools** 
- **Lombok** 
- **SQL Server** 

---

## 📌 Funcionalidades do Backend
### 🔹 Cadastro
- Clientes e fornecedores em um **único cadastro unificado**.
- Controle de **usuários** e registro da data de cada lançamento.

### 🔹 Lançamentos Financeiros
- Gestão de **contas a pagar** e **contas a receber**.
- Controle de **saldos bancários** e movimentações automáticas.
- Regras automáticas:
  - **Duplicata paga** → gera movimentação bancária e atualiza saldo.
  - **Duplicata recebida** → gera movimentação bancária e atualiza saldo.

### 🔹 Relatórios Gerenciais
1. Previsão de caixa (detalhada ou resumida, com total diário).  
2. Extrato de clientes (duplicatas por cliente).  
3. Extrato de fornecedores (duplicatas por fornecedor).  
4. Lançamentos efetuados por usuário.  
5. Extrato bancário.  
6. Saldos dos bancos.  

---

## 🗄️ Estrutura do Banco de Dados
O backend utiliza o **SQL Server** com as seguintes entidades principais:

- **Empresa** → cadastro unificado.  
- **Duplicata** → controle de contas a pagar/receber.  
- **Movimentação** → histórico de entradas e saídas.  
- **Usuario** → autenticação e auditoria de lançamentos.  
- **Banco** → valores atualizados automaticamente.  

---

## ⚙️ Como Executar o Backend
### Pré-requisitos
- **Java 17+**
- **Maven**
- **SQL Server**

### Passos
1. Clone o repositório:
   ```bash
   git clone https://github.com/Felippe-glitch/Sistema-Financeiro.git
2. Atere o **application.properties** para seu servidor e seu usuário/senha
   ```bash
    spring.datasource.url=jdbc:sqlserver://**localhost:1433**;databaseName=financeiro
    spring.datasource.username=**SEU_USUARIO**
    spring.datasource.password=**SUA_SENHA**
    spring.jpa.hibernate.ddl-auto=update
    spring.jpa.show-sql=true
3. Execute no diretório "~/Desktop/Sistema-Financeiro/backend/appfinance"
   ```bash
     mvn spring-boot:run
