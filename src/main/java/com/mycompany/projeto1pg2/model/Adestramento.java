package com.mycompany.projeto1pg2.model;

import java.time.LocalDate;

public class Adestramento extends Servico {
    public Adestramento(LocalDate data) {
        super(data);
    }

    @Override
    public String getDescricao() {
        return "Adestramento a partir de " + getData();
    }
}
