package com.mycompany.projeto1pg2.model;

public class Pet {
    private String nome;
    private String tipo;
    private int idade;

    public Pet(String nome, String tipo, int idade) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("O nome do pet não pode estar em branco.");
        }
        if (tipo == null || tipo.trim().isEmpty()) {
            throw new IllegalArgumentException("O tipo do pet não pode estar em branco.");
        }
        if (idade <= 0) {
            throw new IllegalArgumentException("A idade do pet deve ser maior que zero.");
        }

        this.nome = nome.trim();
        this.tipo = tipo.trim();
        this.idade = idade;
    }

    public String getNome() {
        return nome;
    }

    public String getTipo() {
        return tipo;
    }

    public int getIdade() {
        return idade;
    }

    @Override
    public String toString() {
        return nome + " - " + tipo + " - " + idade + " anos";
    }
}
