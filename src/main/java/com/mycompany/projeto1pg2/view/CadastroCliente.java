package com.mycompany.projeto1pg2.view;

import com.mycompany.projeto1pg2.main.SistemaPetShop;
import com.mycompany.projeto1pg2.model.Cliente;
import com.mycompany.projeto1pg2.dao.ClienteDAO;
import javax.swing.*;
import java.awt.*;

public class CadastroCliente extends JFrame {

    public CadastroCliente() {
        setTitle("Cadastro de Cliente");
        setSize(350, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel painel = new JPanel(new GridLayout(5, 2, 10, 10));
        painel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

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

        btnCadastrar.addActionListener(e -> {
            String nome = txtNome.getText().trim();
            String telefone = txtTelefone.getText().trim();
            String email = txtEmail.getText().trim();

            try {
                Cliente cliente = new Cliente(nome, telefone, email);
                
                ClienteDAO clienteDAO = new ClienteDAO();
                
                int idClienteGerado = clienteDAO.inserirCliente(cliente);
                
                if (idClienteGerado != -1) {
                    SistemaPetShop.adicionarClienteNaLista(cliente); 
                    JOptionPane.showMessageDialog(this, "Cliente cadastrado com sucesso!");
                    dispose(); 
                } else {
                    JOptionPane.showMessageDialog(this, "Erro ao cadastrar cliente no banco de dados. Verifique o console para mais detalhes.", "Erro no Banco de Dados", JOptionPane.ERROR_MESSAGE);
                }
                
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, "Erro de validação: " + ex.getMessage(), "Erro de Validação", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                 JOptionPane.showMessageDialog(this, "Ocorreu um erro inesperado: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                 ex.printStackTrace();
            }
        });

        btnCancelar.addActionListener(e -> dispose());
    }
}