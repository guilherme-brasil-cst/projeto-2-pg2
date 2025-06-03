package com.mycompany.projeto1pg2.view;

import com.mycompany.projeto1pg2.main.SistemaPetShop;
import com.mycompany.projeto1pg2.dao.PetDAO;
import com.mycompany.projeto1pg2.model.Cliente;
import com.mycompany.projeto1pg2.model.Pet;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class CadastroPet extends JFrame {

    public CadastroPet() {
        setTitle("Cadastro de Pet");
        setSize(400, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel painel = new JPanel(new GridLayout(5, 2, 10, 10));
        painel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel lblCliente = new JLabel("Cliente:");
        JComboBox<String> comboClientes = new JComboBox<>();
        List<Cliente> clientes = SistemaPetShop.getClientes();

        if (clientes.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nenhum cliente cadastrado.", "Aviso", JOptionPane.WARNING_MESSAGE);
            dispose();
            return;
        }

        for (Cliente c : clientes) {
            comboClientes.addItem(c.getNome());
        }

        JLabel lblNomePet = new JLabel("Nome do Pet:");
        JTextField txtNomePet = new JTextField();

        JLabel lblTipoPet = new JLabel("Tipo:");
        JComboBox<String> comboTipoPet = new JComboBox<>(new String[]{"Cachorro", "Gato"});

        JLabel lblIdade = new JLabel("Idade:");
        JTextField txtIdade = new JTextField();

        JButton btnSalvar = new JButton("Salvar");
        JButton btnCancelar = new JButton("Cancelar");

        painel.add(lblCliente);
        painel.add(comboClientes);
        painel.add(lblNomePet);
        painel.add(txtNomePet);
        painel.add(lblTipoPet);
        painel.add(comboTipoPet);
        painel.add(lblIdade);
        painel.add(txtIdade);
        painel.add(btnSalvar);
        painel.add(btnCancelar);

        add(painel);
        setVisible(true);

        btnSalvar.addActionListener(e -> {
            try {
                int clienteIndex = comboClientes.getSelectedIndex();
                Cliente cliente = clientes.get(clienteIndex);

                String nomePet = txtNomePet.getText().trim();
                String tipoPet = (String) comboTipoPet.getSelectedItem();
                int idade = Integer.parseInt(txtIdade.getText().trim());

                if (idade < 0 || idade > 50) {
                    throw new IllegalArgumentException("Idade inválida.");
                }

                Pet pet = new Pet(nomePet, tipoPet, idade);
                cliente.adicionarPet(pet);

                PetDAO dao = new PetDAO();
                dao.salvar(pet, cliente.getId());

                JOptionPane.showMessageDialog(this, "Pet cadastrado com sucesso!");
                dispose();

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Digite uma idade válida (número inteiro).", "Erro", JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnCancelar.addActionListener(e -> dispose());
    }
}
