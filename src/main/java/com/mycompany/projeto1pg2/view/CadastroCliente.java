package com.mycompany.projeto1pg2.view;

import com.mycompany.projeto1pg2.main.SistemaPetShop;
import com.mycompany.projeto1pg2.model.Cliente;
import javax.swing.*;
import java.awt.*;

public class CadastroCliente extends JFrame {

    public CadastroCliente() {
        setTitle("Cadastro de Cliente");
        setSize(350, 300);
        setLocationRelativeTo(null); // centraliza na tela
        setDefaultCloseOperation(DISPOSE_ON_CLOSE); // não fecha o sistema

        // Layout principal
        JPanel painel = new JPanel(new GridLayout(5, 2, 10, 10));
        painel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Componentes
        JLabel lblNome = new JLabel("Nome:");
        JTextField txtNome = new JTextField();

        JLabel lblTelefone = new JLabel("Telefone:");
        JTextField txtTelefone = new JTextField();

        JLabel lblEmail = new JLabel("Email:");
        JTextField txtEmail = new JTextField();

        JButton btnCadastrar = new JButton("Cadastrar");
        JButton btnCancelar = new JButton("Cancelar");

        painel.add(lblNome);
        painel.add(txtNome);
        painel.add(lblTelefone);
        painel.add(txtTelefone);
        painel.add(lblEmail);
        painel.add(txtEmail);
        painel.add(btnCadastrar);
        painel.add(btnCancelar);

        add(painel);
        setVisible(true);

        // Ação do botão cadastrar
        btnCadastrar.addActionListener(e -> {
            String nome = txtNome.getText().trim();
            String telefone = txtTelefone.getText().trim();
            String email = txtEmail.getText().trim();

            try {
                Cliente cliente = new Cliente(nome, telefone, email);
                SistemaPetShop.getClientes().add(cliente);
                JOptionPane.showMessageDialog(this, "Cliente cadastrado com sucesso!");
                dispose(); // fecha a janela
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage(), "Erro de Validação", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Ação do botão cancelar
        btnCancelar.addActionListener(e -> dispose());
    }
}
