package com.mycompany.projeto1pg2.model;

import java.time.LocalDate;

public class ConsultaVeterinaria extends Servico {
    public ConsultaVeterinaria(LocalDate data) {
        super(data);
    }

    @Override
    public String getDescricao() {
        return "Consulta em " + getData();
    }
}