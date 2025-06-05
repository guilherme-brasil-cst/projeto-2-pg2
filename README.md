README GitHub PJ2PG2

# 🐾 Sistema de Gerenciamento de Pet Shop

Este projeto foi desenvolvido como parte da disciplina de Programação em Java. O sistema é uma aplicação de gerenciamento de um pet shop, com interface gráfica (Java Swing), persistência de dados com MySQL e estrutura modularizada por pacotes.

## 👨‍💻 Integrantes

- Guilherme Brasil  
- Gabriel Gonçalves  
- Caio Togawa  
- Gabriel Rodrigues

---

## 📦 Estrutura do Projeto

O projeto foi organizado em pacotes para manter o código limpo e coeso:

src/
├── com.mycompany.projeto1pg2.dao # Classes de acesso a dados (DAO)
├── com.mycompany.projeto1pg2.main # Classe Main e SistemaPetShop
├── com.mycompany.projeto1pg2.model # Classes de modelo (Cliente, Pet, Serviço etc.)
├── com.mycompany.projeto1pg2.util # Conexão com o banco de dados
├── com.mycompany.projeto1pg2.view # Interfaces gráficas (Swing)
└── db # Script SQL de criação do banco

(OBS: A pasta "projeto1pg2", que fica dentro de "projeto-2-pg2", se chama assim pois o projeto deste sistema foi feito a partir de uma base do projeto 1, solicitado anteriormente).

---

## 🛠️ Tecnologias Utilizadas

- Java 17+ (JDK 23)
- Swing (interface gráfica)
- MySQL Server 8.0+
- MySQL Workbench
- Apache NetBeans 24
- Maven

---

## 💾 Banco de Dados

O sistema utiliza o banco MySQL. O script SQL de criação do banco e das tabelas está disponível no diretório `/db` com o nome `petshop.sql`.

### Como configurar:

1. Abra o **MySQL Workbench**.
2. Execute o script `petshop.sql` para criar o banco `petshop` com suas tabelas.
3. Verifique se as credenciais usadas em `ConexaoMySQL.java` estão corretas:

```java
private static final String URL = "jdbc:mysql://localhost:3306/petshop";
private static final String USER = "root";
private static final String PASSWORD = "root160055";
```
---

🚀 Como Executar o Projeto
Pré-requisitos
MySQL Server e Workbench instalados

Java JDK 17+ instalado

NetBeans 24 com suporte Maven 

Conector JDBC do MySQL instalado (mysql-connector-j-8.0.x.jar ou incluído via Maven)

Passos
Clone o repositório:
bash:

git clone https://github.com/guilherme-brasil-cst/projeto-2-pg2.git
Abra o projeto no NetBeans:

Vá em File > Open Project e selecione a pasta projeto-2-pg2.

Crie o banco de dados:

No MySQL Workbench, execute o script petshop.sql localizado na pasta db.

Execute a classe MenuPrincipal.java.

--- ---

✅ Funcionalidades
Cadastro de clientes com validação de e-mail e telefone

Cadastro de pets vinculados a clientes

Contratação de serviços com escolha de data

Listagem de clientes e serviços contratados

Interface gráfica moderna com Swing

Integração com banco de dados MySQL via JDBC

Estrutura MVC e separação por pacotes

🧪 Testes
Foram criados arquivos de teste para validar a conexão com o banco e inserções:

TesteConexao.java: Testa a conexão com o banco

TesteClienteDAO.java: Testa inserção e listagem de clientes

🗃️ Controle de Versão
Cada integrante do grupo participou em praticamente todas as etapas da produção e construção do projeto, porém cada um ficou responsável por fazer commits de partes distintas do projeto para o repositório do GitHub:

Guilherme: pacotes model, test, e DAO.

Gabriel Rodrigues: pacote view.

Caio: pacotes main.

Gabriel Goncalves: script SQL e documentação.

---

📷 Prints e Demonstrações:
--- ---

🧾 Licença
Este projeto é acadêmico, desenvolvido com fins educacionais.
