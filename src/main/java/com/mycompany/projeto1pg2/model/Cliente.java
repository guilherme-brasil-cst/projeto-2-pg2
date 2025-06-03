package com.mycompany.projeto1pg2.model;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class Cliente {
    private int id;  // novo campo
    private String nome;
    private String telefone;
    private String email;
    private List<Pet> pets;

    public Cliente(String nome, String telefone, String email) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("O nome não pode estar em branco.");
        }
        if (!isTelefoneValido(telefone)) {
            throw new IllegalArgumentException("Telefone inválido.");
        }
        if (!isEmailValido(email)) {
            throw new IllegalArgumentException("E-mail inválido.");
        }

        this.nome = nome.trim();
        this.telefone = telefone.trim();
        this.email = email.trim();
        this.pets = new ArrayList<>();
    }

    public Cliente(int id, String nome, String telefone, String email) {
        this(nome, telefone, email);
        this.id = id;
    }

    public void adicionarPet(Pet pet) {
        if (pet != null) pets.add(pet);
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getEmail() {
        return email;
    }

    public List<Pet> getPets() {
        return new ArrayList<>(pets);
    }

    private boolean isEmailValido(String email) {
        String regex = "^[\\w\\.-]+@[\\w\\.-]+\\.\\w{2,}$";
        return Pattern.matches(regex, email);
    }

    private boolean isTelefoneValido(String telefone) {
        String regex = "^\\(?\\d{2}\\)?\\s?\\d{4,5}-?\\d{4}$";
        return Pattern.matches(regex, telefone);
    }
}
