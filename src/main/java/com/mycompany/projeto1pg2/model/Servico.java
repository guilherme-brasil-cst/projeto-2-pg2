package com.mycompany.projeto1pg2.model;

import java.time.LocalDate;

public abstract class Servico {
    private int id; 
    private LocalDate data;
    private int clienteId; 
    private int petId;     

    public Servico(LocalDate data) {
        if (data == null) {
            throw new IllegalArgumentException("A data do serviço não pode ser nula.");
        }
        this.data = data;
    }
    
    public Servico(int id, LocalDate data, int clienteId, int petId) {
        this(data); 
        this.id = id;
        this.clienteId = clienteId;
        this.petId = petId;
    }

    public abstract String getDescricao();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getData() {
        return data;
    }
    
    public void setData(LocalDate data) { // Adicionando setter para data, se necessário
        this.data = data;
    }

    public int getClienteId() {
        return clienteId;
    }

    public void setClienteId(int clienteId) {
        this.clienteId = clienteId;
    }

    public int getPetId() {
        return petId;
    }

    public void setPetId(int petId) {
        this.petId = petId;
    }
}
