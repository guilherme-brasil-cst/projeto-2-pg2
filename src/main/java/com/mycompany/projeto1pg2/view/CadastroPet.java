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
        JComboBox<Cliente> comboClientes = new JComboBox<>();
        
        List<Cliente> clientes = SistemaPetShop.getClientes(); 

        if (clientes.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nenhum cliente cadastrado. Por favor, cadastre um cliente primeiro e reinicie esta janela.", "Aviso", JOptionPane.WARNING_MESSAGE);
            dispose();
            return;
        }

        for (Cliente c : clientes) {
            comboClientes.addItem(c); 
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
                Cliente clienteSelecionado = (Cliente) comboClientes.getSelectedItem();
                
                if (clienteSelecionado == null) {
                    JOptionPane.showMessageDialog(this, "Selecione um cliente válido.", "Erro de Seleção", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                String nomePet = txtNomePet.getText().trim();
                String tipoPet = (String) comboTipoPet.getSelectedItem();
                int idade = Integer.parseInt(txtIdade.getText().trim());

                if (idade < 0 || idade > 50) { 
                    throw new IllegalArgumentException("Idade inválida. A idade deve ser entre 0 e 50.");
                }

                Pet pet = new Pet(nomePet, tipoPet, idade);
                
                PetDAO dao = new PetDAO();
                int idPetGerado = dao.salvar(pet, clienteSelecionado.getId()); 

                if (idPetGerado != -1) {
                    clienteSelecionado.adicionarPet(pet); 
                    JOptionPane.showMessageDialog(this, "Pet cadastrado com sucesso!");
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Erro ao cadastrar pet no banco de dados.", "Erro no Banco de Dados", JOptionPane.ERROR_MESSAGE);
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Digite uma idade válida (número inteiro).", "Erro de Entrada", JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro de Validação", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro ao salvar pet: " + ex.getMessage() + ". Verifique o console.", "Erro no Sistema", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace(); 
            }
        });

        btnCancelar.addActionListener(e -> dispose());
    }
}