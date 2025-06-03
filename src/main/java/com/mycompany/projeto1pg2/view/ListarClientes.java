package com.mycompany.projeto1pg2.view;

import com.mycompany.projeto1pg2.main.SistemaPetShop;
import com.mycompany.projeto1pg2.model.Cliente;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ListarClientes extends JFrame {

    public ListarClientes() {
        setTitle("Lista de Clientes");
        setSize(500, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // Colunas da tabela
        String[] colunas = {"Nome", "Telefone", "E-mail"};

        // Modelo da tabela
        DefaultTableModel modelo = new DefaultTableModel(colunas, 0);
        JTable tabela = new JTable(modelo);

        // Pega os clientes cadastrados
        List<Cliente> clientes = SistemaPetShop.getClientes();
        for (Cliente c : clientes) {
            Object[] linha = {c.getNome(), c.getTelefone(), c.getEmail()};
            modelo.addRow(linha);
        }

        JScrollPane scroll = new JScrollPane(tabela);
        add(scroll, BorderLayout.CENTER);

        setVisible(true);
    }
}
