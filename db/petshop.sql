-- Este script SQL cria o banco de dados 'petshop' e suas tabelas
-- para o sistema de gerenciamento de pet shop.

-- Se o banco de dados 'petshop' já existir, ele será removido e recriado para garantir uma instalação limpa.
DROP DATABASE IF EXISTS petshop;
CREATE DATABASE petshop;

-- Seleciona o banco de dados 'petshop' para as operações seguintes.
USE petshop;

-- Desativa a verificação de chaves estrangeiras temporariamente.
SET FOREIGN_KEY_CHECKS = 0;

-- Dropa as tabelas existentes no schema 'petshop' (se existirem) para evitar erros de recriação.
DROP TABLE IF EXISTS servico;
DROP TABLE IF EXISTS pet;
DROP TABLE IF EXISTS cliente;

-- Reativa a verificação de chaves estrangeiras.
SET FOREIGN_KEY_CHECKS = 1;

-- Tabela: cliente
-- Armazena informações sobre os clientes do pet shop.
CREATE TABLE cliente (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    telefone VARCHAR(20),
    email VARCHAR(100) UNIQUE
);

-- Tabela: pet
-- Armazena informações sobre os pets, associados a um cliente.
CREATE TABLE pet (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    tipo VARCHAR(50),
    idade INT,
    cliente_id INT NOT NULL, -- Chave estrangeira que referencia a tabela 'cliente'.
    FOREIGN KEY (cliente_id) REFERENCES cliente(id) ON DELETE CASCADE
);

-- Tabela: servico
-- Armazena informações sobre os serviços contratados, associados a um cliente e um pet.
CREATE TABLE servico (
    id INT AUTO_INCREMENT PRIMARY KEY,
    descricao TEXT,
    data DATE,
    cliente_id INT NOT NULL, -- Chave estrangeira que referencia a tabela 'cliente'.
    pet_id INT NOT NULL,     -- Chave estrangeira que referencia a tabela 'pet'.
    FOREIGN KEY (cliente_id) REFERENCES cliente(id),
    FOREIGN KEY (pet_id) REFERENCES pet(id)
);



