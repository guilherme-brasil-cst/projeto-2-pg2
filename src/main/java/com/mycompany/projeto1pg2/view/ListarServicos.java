package com.mycompany.projeto1pg2.view;

import com.mycompany.projeto1pg2.main.SistemaPetShop;
import com.mycompany.projeto1pg2.model.Servico;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ListarServicos extends JFrame {

    public ListarServicos() {
        setTitle("Lista de Serviços Contratados");
        setSize(600, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        String[] colunas = {"Descrição", "Data"};

        DefaultTableModel modelo = new DefaultTableModel(colunas, 0);
        JTable tabela = new JTable(modelo);

        List<Servico> servicos = SistemaPetShop.getServicos();
        DateTimeFormatter formatoBr = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        for (Servico s : servicos) {
            Object[] linha = {
                s.getDescricao(),
                s.getData().format(formatoBr)
            };
            modelo.addRow(linha);
        }

        JScrollPane scroll = new JScrollPane(tabela);
        add(scroll, BorderLayout.CENTER);

        setVisible(true);
    }
}
