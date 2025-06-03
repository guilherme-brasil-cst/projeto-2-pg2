package com.mycompany.projeto1pg2.view;

import com.mycompany.projeto1pg2.main.SistemaPetShop;
import com.mycompany.projeto1pg2.model.Servico;
import com.mycompany.projeto1pg2.model.Pet;
import com.mycompany.projeto1pg2.model.Cliente;
import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

public class ContratarServico extends JFrame {

    public ContratarServico() {
        setTitle("Contratar Serviço");
        setSize(450, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        List<Cliente> clientes = SistemaPetShop.getClientes();
        if (clientes.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nenhum cliente cadastrado.", "Aviso", JOptionPane.WARNING_MESSAGE);
            dispose();
            return;
        }

        JPanel painel = new JPanel();
        painel.setLayout(new BoxLayout(painel, BoxLayout.Y_AXIS));
        painel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JComboBox<String> comboClientes = new JComboBox<>();
        for (Cliente c : clientes) comboClientes.addItem(c.getNome());

        JComboBox<String> comboPets = new JComboBox<>();

        JComboBox<Integer> comboDia = new JComboBox<>();
        for (int i = 1; i <= 31; i++) comboDia.addItem(i);

        JComboBox<Integer> comboMes = new JComboBox<>();
        for (int i = 1; i <= 12; i++) comboMes.addItem(i);

        JComboBox<Integer> comboAno = new JComboBox<>();
        int anoAtual = LocalDate.now().getYear();
        for (int i = anoAtual; i <= anoAtual + 2; i++) comboAno.addItem(i);

        JPanel linha1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        linha1.add(new JLabel("Cliente:"));
        linha1.add(comboClientes);

        JPanel linha2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        linha2.add(new JLabel("Pet:"));
        linha2.add(comboPets);

        JPanel linha3 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        linha3.add(new JLabel("Data do serviço:"));
        linha3.add(comboDia);
        linha3.add(new JLabel("/"));
        linha3.add(comboMes);
        linha3.add(new JLabel("/"));
        linha3.add(comboAno);

        JPanel linhaBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton btnContratar = new JButton("Contratar");
        JButton btnCancelar = new JButton("Cancelar");
        linhaBotoes.add(btnContratar);
        linhaBotoes.add(btnCancelar);

        painel.add(linha1);
        painel.add(linha2);
        painel.add(linha3);
        painel.add(Box.createVerticalStrut(10));
        painel.add(linhaBotoes);

        add(painel);
        setVisible(true);

        // Inicializa pets do cliente selecionado
        comboClientes.addActionListener(e -> {
            int index = comboClientes.getSelectedIndex();
            comboPets.removeAllItems();
            List<Pet> pets = clientes.get(index).getPets();
            for (Pet p : pets) comboPets.addItem(p.getNome());
        });
        comboClientes.setSelectedIndex(0);

        btnContratar.addActionListener(e -> {
            try {
                int clienteIndex = comboClientes.getSelectedIndex();
                Cliente cliente = clientes.get(clienteIndex);

                int petIndex = comboPets.getSelectedIndex();
                if (petIndex < 0 || cliente.getPets().isEmpty()) {
                    throw new IllegalArgumentException("Selecione um pet válido.");
                }

                Pet pet = cliente.getPets().get(petIndex);
                int dia = (int) comboDia.getSelectedItem();
                int mes = (int) comboMes.getSelectedItem();
                int ano = (int) comboAno.getSelectedItem();
                LocalDate data = LocalDate.of(ano, mes, dia);

                if (!data.isAfter(LocalDate.now())) {
                    throw new IllegalArgumentException("A data do serviço deve ser futura.");
                }

                Servico servico = new Servico(data) {
                    @Override
                    public String getDescricao() {
                        return "Consulta para " + pet.getNome() + " (Cliente: " + cliente.getNome() + ")";
                    }
                };

                SistemaPetShop.adicionarServico(servico);
                JOptionPane.showMessageDialog(this, "Serviço contratado com sucesso!");
                dispose();

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnCancelar.addActionListener(e -> dispose());
    }
}
