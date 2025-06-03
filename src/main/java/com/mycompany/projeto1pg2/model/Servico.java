package com.mycompany.projeto1pg2.model;

import java.time.LocalDate;

public abstract class Servico {
    protected LocalDate data;

    public Servico(LocalDate data) {
        if (data == null || !data.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("A data do serviço deve ser futura.");
        }
        this.data = data;
    }

    public abstract String getDescricao();

    public LocalDate getData() {
        return data;
    }
}
