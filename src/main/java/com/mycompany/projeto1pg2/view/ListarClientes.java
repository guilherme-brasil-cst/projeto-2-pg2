package com.mycompany.projeto1pg2.view;

import com.mycompany.projeto1pg2.main.SistemaPetShop;
import com.mycompany.projeto1pg2.model.Cliente;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ListarClientes extends JFrame {

    private JTable tableClientes;
    private DefaultTableModel tableModel;

    public ListarClientes() {
        setTitle("Lista de Clientes");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // Define as colunas da tabela
        tableModel = new DefaultTableModel(new Object[]{"ID", "Nome", "Telefone", "E-mail"}, 0);
        tableClientes = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tableClientes);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(scrollPane, BorderLayout.CENTER);

        JButton btnSair = new JButton("Sair");
        btnSair.addActionListener(e -> dispose());
        JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        southPanel.add(btnSair);
        panel.add(southPanel, BorderLayout.SOUTH);

        add(panel);
        
        // Carrega os clientes na tabela assim que a janela é criada/aberta
        carregarClientesNaTabela();

        // Torna a janela visível
        setVisible(true);
    }

    private void carregarClientesNaTabela() {
        // Limpa todas as linhas existentes na tabela
        tableModel.setRowCount(0); 
        
        // Garante que a lista estática em SistemaPetShop esteja atualizada com os dados do banco
        SistemaPetShop.carregarClientesDoBanco(); 
        
        // Pega a lista de clientes atualizada
        List<Cliente> clientes = SistemaPetShop.getClientes(); 

        if (clientes.isEmpty()) {
            // Se a lista estiver vazia, pode exibir uma mensagem na própria tabela ou console
            System.out.println("Nenhum cliente cadastrado para exibição na tabela."); 
            // Você pode adicionar uma linha indicando que não há clientes, se desejar:
            // tableModel.addRow(new Object[]{"", "Nenhum cliente cadastrado", "", ""});
        } else {
            // Adiciona cada cliente à tabela
            for (Cliente c : clientes) {
                tableModel.addRow(new Object[]{c.getId(), c.getNome(), c.getTelefone(), c.getEmail()});
            }
        }
    }
    
    // Sobrescreve setVisible para recarregar a tabela cada vez que a janela for exibida (útil se ela for ocultada e depois reexibida)
    @Override
    public void setVisible(boolean b) {
        super.setVisible(b);
        if (b) { 
            carregarClientesNaTabela();
        }
    }
}
