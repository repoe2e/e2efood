# E2E Food API

## Descrição do Projeto

O **E2E Food API** é um sistema de gerenciamento de restaurantes que permite buscar restaurantes por cidade. O projeto foi desenvolvido em **Spring Boot** e utiliza **MySQL** como banco de dados.

---

## Requisitos

Antes de começar, verifique se você tem os seguintes pré-requisitos instalados:

- **Java 17** ou superior
- **Maven** 3.6.0 ou superior
- **MySQL** 8.0 ou superior
- **IDE** (como IntelliJ IDEA ou Eclipse)

---

## Configuração do Banco de Dados

### 1. Instalar o MySQL

Siga as instruções de instalação do MySQL de acordo com seu sistema operacional. Você pode encontrar as instruções [aqui](https://dev.mysql.com/doc/refman/8.0/en/installing.html).

### 2. Criar o Banco de Dados

Após instalar o MySQL, você precisará criar um banco de dados para o projeto. Abra o terminal do MySQL e execute os seguintes comandos:

```sql
CREATE DATABASE e2efood;
```

### 3. Configurar as Credenciais no `application.properties`

No seu projeto, você encontrará um arquivo chamado `application.properties`. Configure as credenciais do banco de dados da seguinte maneira:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/e2efood
spring.datasource.username=root
spring.datasource.password=sua_senha
spring.jpa.hibernate.ddl-auto=update
```

Certifique-se de substituir `sua_senha` pela senha do seu usuário root do MySQL.

---

## Como Construir e Iniciar o Projeto

### 1. Clone o Repositório

Clone o repositório do projeto:

```bash
git clone https://github.com/seu_usuario/e2efood.git
cd e2efood
```

### 2. Construir o Projeto com Maven

Execute o seguinte comando para construir o projeto:

```bash
mvn clean install
```

### 3. Iniciar o Projeto

Para iniciar o projeto, você pode usar o comando:

```bash
mvn spring-boot:run
```

O aplicativo será iniciado na porta padrão **8080**.

---

## Contribuição

Sinta-se à vontade para contribuir com melhorias ou correções! Para isso, siga estas etapas:

1. Fork o projeto.
2. Crie sua feature branch (`git checkout -b feature/nome-da-sua-feature`).
3. Faça o commit das suas mudanças (`git commit -m 'Adicionando uma nova feature'`).
4. Envie para o branch principal (`git push origin feature/nome-da-sua-feature`).
5. Abra um Pull Request.

---


