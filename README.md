# 🐾 Sistema de Gerenciamento de Pet Shop

Este projeto foi desenvolvido como parte da disciplina de Programação em Java. O sistema é uma aplicação de gerenciamento de um pet shop com **interface gráfica (Java Swing)** e **persistência de dados utilizando MySQL**, seguindo uma estrutura modularizada por pacotes.

## 👨‍💻 Integrantes

- Guilherme Brasil
- Gabriel Gonçalves
- Caio Togawa
- Gabriel Rodrigues

---

## 📦 Estrutura do Projeto

O projeto foi organizado em pacotes para manter o código limpo, coeso e seguindo os princípios de separação de responsabilidades:

projeto-2-pg2/
├── db/                         # Contém o script SQL para criação do banco de dados (petshop.sql)

├── lib/                        # Biblioteca externa (mysql-connector-java-8.0.30.jar)

├── pom.xml                     # Arquivo de configuração do projeto Maven

└── src/

└── main/

└── java/

└── com/mycompany/projeto1pg2/

├── dao/        # Data Access Objects (DAOs) - Classes para interagir com o banco de dados (ClienteDAO, PetDAO, ServicoDAO)

├── main/       # Contém a classe principal da aplicação (SistemaPetShop)

├── model/      # Classes de modelo de dados (Cliente, Pet, Servico, Adestramento, BanhoETosa, ConsultaVeterinaria, Hospedagem)

├── util/       # Classes de utilitários, como a conexão com o banco de dados (ConexaoMySQL)

└── view/       # Classes de interface gráfica de usuário (GUIs) em Swing (MenuPrincipal, CadastroCliente, ListarClientes, etc.)


**(OBS:** A pasta "projeto1pg2", que fica dentro de "projeto-2-pg2", se chama assim pois o projeto deste sistema foi feito a partir de uma base de um projeto anterior, solicitado anteriormente.)

---

## 🛠️ Tecnologias Utilizadas

-   **Linguagem:** Java (JDK 17+, compilado para Java 23)
-   **Interface Gráfica:** Swing
-   **Banco de Dados:** MySQL Server 8.0+
-   **Gerenciador de Banco de Dados:** MySQL Workbench
-   **IDE:** Apache NetBeans 24
-   **Gerenciador de Dependências:** Apache Maven

---

## 💾 Banco de Dados

O sistema utiliza o banco de dados **MySQL** para persistência de todos os dados (clientes, pets e serviços). O script SQL completo para criação do banco e de todas as tabelas (com nomes no singular, chaves primárias e estrangeiras) está disponível no diretório `/db`.

### Como Configurar o Banco de Dados:

1.  Abra o **MySQL Workbench**.
2.  Abra o arquivo `petshop.sql` localizado na pasta `/db` do projeto.
3.  Execute o script SQL completo para criar o banco de dados `petshop` e suas tabelas (`cliente`, `pet`, `servico`).
    * **Importante:** O script já inclui comandos para dropar o banco/tabelas se existirem, garantindo uma recriação limpa.
4.  Verifique se as credenciais de conexão no arquivo `ConexaoMySQL.java` (`src/main/java/com/mycompany/projeto1pg2/util/ConexaoMySQL.java`) correspondem às suas configurações do MySQL Server:

    ```java
    private static final String URL = "jdbc:mysql://localhost:3306/petshop";
    private static final String USUARIO = "root"; // Ou seu usuário do MySQL
    private static final String SENHA = "root160055"; // Ou sua senha do MySQL
    ```

---

## 🚀 Como Executar o Projeto

### Pré-requisitos

-   **MySQL Server e MySQL Workbench** instalados e configurados.
-   **Java Development Kit (JDK) 17+** (o projeto foi compilado com JDK 23).
-   **Apache NetBeans 24** com suporte a projetos Maven.
-   **Conector JDBC do MySQL** (o `mysql-connector-j-8.0.30.jar` está incluído na pasta `lib/` e configurado no `pom.xml`).

### Passos

1.  **Clone o Repositório:**
    ```bash
    git clone [https://github.com/guilherme-brasil-cst/projeto-2-pg2.git](https://github.com/guilherme-brasil-cst/projeto-2-pg2.git)
    ```
2.  **Abra o Projeto no NetBeans:**
    Vá em `File > Open Project` e selecione a pasta `projeto-2-pg2` clonada.
3.  **Crie o Banco de Dados:**
    No MySQL Workbench, execute o script `petshop.sql` localizado na pasta `db/` do projeto (veja as instruções em "Como Configurar o Banco de Dados" acima).
4.  **Limpe e Compile o Projeto no NetBeans:**
    Clique com o botão direito no projeto `projeto2pg2` no NetBeans e selecione `Clean and Build`.
5.  **Execute a Aplicação:**
    Clique com o botão direito no projeto `projeto2pg2` no NetBeans e selecione `Run`. A interface gráfica principal (`MenuPrincipal`) será iniciada.

---

## ✅ Funcionalidades Implementadas

-   **Cadastro de Clientes:** Com validação de nome, e-mail e telefone, e persistência no MySQL.
-   **Cadastro de Pets:** Vinculados a clientes existentes e persistência no MySQL.
-   **Contratação de Serviços:** Permite selecionar cliente e pet, escolher a data do serviço (com validação de data futura), e persistir o serviço no MySQL.
-   **Listagem de Clientes:** Exibe todos os clientes cadastrados em uma interface gráfica de tabela, carregando dados do MySQL.
-   **Listagem de Serviços:** Exibe todos os serviços contratados em uma interface gráfica de tabela, carregando dados do MySQL.
-   **Interface Gráfica (GUI):** Aplicação baseada em Java Swing para uma experiência de usuário intuitiva.
-   **Persistência de Dados:** Todos os dados são armazenados no MySQL utilizando JDBC.
-   **Estrutura Modular:** Organização do código em pacotes (DAO, Model, View, Main, Util) e princípios de MVC.
-   **Funcionalidades de Console:** Métodos de console para cadastro e listagem (executados em segundo plano, mas disponíveis para teste direto).

---

## 🧪 Testes

Foram criados arquivos de teste para validar a conexão com o banco de dados e as operações básicas de persistência:

-   `TesteConexao.java`: Testa a conexão com o banco de dados MySQL.
-   `TesteClienteDAO.java`: Testa as operações de inserção e listagem de clientes no banco de dados.

---

## 🗃️ Controle de Versão

Cada integrante do grupo participou ativamente em praticamente todas as etapas da produção e construção do projeto. Para o controle de versão no repositório do GitHub, a responsabilidade principal pelos commits foi dividida da seguinte forma:

-   **Guilherme:** Pacotes `model`, `test`, e `dao`.
-   **Gabriel Rodrigues:** Pacote `view`.
-   **Caio:** Pacote `main`.
-   **Gabriel Gonçalves:** Script SQL (`db/petshop.sql`) e documentação (`README.md`).

---

## 📷 Prints e Demonstrações:

- Print do menu principal aberto:
![menuprincipal](https://github.com/user-attachments/assets/e2850c2c-2f17-4fe8-b7b8-1fc24327b1e8)

- Print da página de cadastro de clientes:   
![cadastrocliente](https://github.com/user-attachments/assets/0b070ea0-a3f3-4999-937d-2e3452b2c116)

- Print da página da lista de clientes:
![listacliente](https://github.com/user-attachments/assets/f2f47863-ab14-4b52-8089-1a947d799fa2)

- Print do banco de dados aberto e as tabelas devidamente criadas:
![mysql](https://github.com/user-attachments/assets/7b99b0b4-70e5-4ebd-be7b-49570229b357)

- Print do Netbeans e o projeto abertos:
![netbeansaberto](https://github.com/user-attachments/assets/aca6a1a2-57e0-406c-bdf1-0798c954f97a)

---

## 🧾 Licença

Este projeto é acadêmico, desenvolvido com fins educacionais.
