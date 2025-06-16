package com.mycompany.projeto1pg2.view;

import com.mycompany.projeto1pg2.main.SistemaPetShop;
import com.mycompany.projeto1pg2.model.Cliente;
import com.mycompany.projeto1pg2.model.Pet;
import com.mycompany.projeto1pg2.model.Servico;
import com.mycompany.projeto1pg2.dao.ServicoDAO;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;
import javax.swing.event.ChangeListener;
import javax.swing.SpinnerNumberModel;

public class ContratarServico extends JFrame {

    private JComboBox<Cliente> comboClientes;
    private JComboBox<Pet> comboPets;
    private JComboBox<Servico> comboTipos;       // ← novo combo
    private JSpinner spinnerDia, spinnerMes, spinnerAno;

    public ContratarServico() {
        setTitle("Contratar Serviço");
        setSize(450, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // 1) Cliente
        panel.add(new JLabel("Cliente:"));
        comboClientes = new JComboBox<>();
        if (!carregarClientesNoCombo()) {
            dispose();
            return;
        }
        panel.add(comboClientes);

        // 2) Pet
        panel.add(new JLabel("Pet:"));
        comboPets = new JComboBox<>();
        comboClientes.addActionListener(e -> carregarPetsNoCombo());
        carregarPetsNoCombo();
        panel.add(comboPets);

        // 3) Data
        panel.add(new JLabel("Data do serviço:"));
        JPanel datePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        LocalDate today = LocalDate.now();
        spinnerDia = new JSpinner(new SpinnerNumberModel(today.getDayOfMonth(), 1, 31, 1));
        spinnerMes = new JSpinner(new SpinnerNumberModel(today.getMonthValue(), 1, 12, 1));
        spinnerAno = new JSpinner(new SpinnerNumberModel(today.getYear(), today.getYear(), today.getYear() + 50, 1));
        JSpinner.NumberEditor yEd = new JSpinner.NumberEditor(spinnerAno, "0");
        spinnerAno.setEditor(yEd);
        datePanel.add(spinnerDia);
        datePanel.add(new JLabel("/"));
        datePanel.add(spinnerMes);
        datePanel.add(new JLabel("/"));
        datePanel.add(spinnerAno);
        panel.add(datePanel);

        // 4) Tipo de Serviço
        panel.add(new JLabel("Tipo de Serviço:"));
        comboTipos = new JComboBox<>();
        panel.add(comboTipos);
        // popula com os tipos
        ServicoDAO dao = new ServicoDAO();
        List<Servico> tipos = dao.listarTipos();
        for (Servico t : tipos) {
            comboTipos.addItem(t);
        }

        // Ajusta máximo de dias ao mudar mês/ano
        ChangeListener dateChange = e -> {
            try {
                int y = (int) spinnerAno.getValue();
                int m = (int) spinnerMes.getValue();
                int max = LocalDate.of(y, m, 1).lengthOfMonth();
                SpinnerNumberModel md = (SpinnerNumberModel) spinnerDia.getModel();
                md.setMaximum(max);
                if ((int) spinnerDia.getValue() > max) spinnerDia.setValue(max);
            } catch (Exception ignored) {}
        };
        spinnerMes.addChangeListener(dateChange);
        spinnerAno.addChangeListener(dateChange);

        // botões
        JButton btnContratar = new JButton("Contratar");
        JButton btnCancelar  = new JButton("Cancelar");
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnPanel.add(btnContratar);
        btnPanel.add(btnCancelar);

        add(panel, BorderLayout.CENTER);
        add(btnPanel, BorderLayout.SOUTH);
        setVisible(true);

        // ação Contratar
        btnContratar.addActionListener(e -> {
            Cliente cli = (Cliente) comboClientes.getSelectedItem();
            Pet pet   = (Pet)    comboPets.getSelectedItem();
            Servico selTipo = (Servico) comboTipos.getSelectedItem();

            if (cli == null || pet == null || selTipo == null) {
                JOptionPane.showMessageDialog(this, "Selecione cliente, pet e tipo.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            LocalDate dt = LocalDate.of(
                (int) spinnerAno.getValue(),
                (int) spinnerMes.getValue(),
                (int) spinnerDia.getValue()
            );
            if (!dt.isAfter(LocalDate.now())) {
                JOptionPane.showMessageDialog(this, "Data deve ser futura.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // sobrepõe a data do objeto tipo
            selTipo.setData(dt);

            int idGerado = dao.inserirServico(selTipo, cli.getId(), pet.getId());
            if (idGerado != -1) {
                selTipo.setId(idGerado);
                SistemaPetShop.adicionarServico(selTipo);
                JOptionPane.showMessageDialog(this, "Servico contratado com sucesso!");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao salvar no banco.", "Erro BD", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnCancelar.addActionListener(e -> dispose());
    }

    private boolean carregarClientesNoCombo() {
        comboClientes.removeAllItems();
        SistemaPetShop.carregarClientesDoBanco();
        for (Cliente c : SistemaPetShop.getClientes()) {
            comboClientes.addItem(c);
        }
        return !SistemaPetShop.getClientes().isEmpty();
    }

    private void carregarPetsNoCombo() {
        comboPets.removeAllItems();
        Cliente c = (Cliente) comboClientes.getSelectedItem();
        if (c != null) {
            for (Pet p : c.getPets()) {
                comboPets.addItem(p);
            }
        }
    }
}
