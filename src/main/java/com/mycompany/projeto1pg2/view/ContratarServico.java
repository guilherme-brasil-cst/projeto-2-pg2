package com.mycompany.projeto1pg2.view;

import com.mycompany.projeto1pg2.main.SistemaPetShop;
import com.mycompany.projeto1pg2.model.Cliente;
import com.mycompany.projeto1pg2.model.Pet;
import com.mycompany.projeto1pg2.model.Servico;
import com.mycompany.projeto1pg2.dao.ServicoDAO;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.JFormattedTextField; // Importar

public class ContratarServico extends JFrame {

    private JComboBox<Cliente> comboClientes;
    private JComboBox<Pet> comboPets;
    private JSpinner spinnerDia, spinnerMes, spinnerAno;

    public ContratarServico() {
        setTitle("Contratar Serviço");
        setSize(450, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panel.add(new JLabel("Cliente:"));
        comboClientes = new JComboBox<>();
        
        if (!carregarClientesNoCombo()) {
            dispose(); 
            return; 
        }
        panel.add(comboClientes);

        panel.add(new JLabel("Pet:"));
        comboPets = new JComboBox<>();
        comboClientes.addActionListener(e -> carregarPetsNoCombo()); 
        carregarPetsNoCombo(); 
        panel.add(comboPets);

        panel.add(new JLabel("Data do serviço:"));
        JPanel datePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        
        LocalDate today = LocalDate.now();
        spinnerDia = new JSpinner(new SpinnerNumberModel(today.getDayOfMonth(), 1, 31, 1));
        spinnerMes = new JSpinner(new SpinnerNumberModel(today.getMonthValue(), 1, 12, 1));
        spinnerAno = new JSpinner(new SpinnerNumberModel(today.getYear(), today.getYear(), today.getYear() + 50, 1));
        
        // CORREÇÃO DO FORMATO DO ANO AQUI
        JSpinner.NumberEditor yearEditor = new JSpinner.NumberEditor(spinnerAno, "0"); // "0" remove separadores de milhar
        spinnerAno.setEditor(yearEditor);

        datePanel.add(spinnerDia);
        datePanel.add(new JLabel("/"));
        datePanel.add(spinnerMes);
        datePanel.add(new JLabel("/"));
        datePanel.add(spinnerAno);
        panel.add(datePanel);

        // Listener para ajustar o dia máximo quando o mês/ano muda
        ChangeListener dateChangeListener = e -> {
            try {
                int year = (int) spinnerAno.getValue();
                int month = (int) spinnerMes.getValue();
                LocalDate tempDate = LocalDate.of(year, month, 1);
                int maxDay = tempDate.lengthOfMonth();
                int currentDay = (int) spinnerDia.getValue();
                ((SpinnerNumberModel) spinnerDia.getModel()).setMaximum(maxDay);
                if (currentDay > maxDay) {
                    spinnerDia.setValue(maxDay);
                }
            } catch (Exception ex) {
                // Ignora erros de data inválida (ex: 31 de fevereiro) durante a digitação
            }
        };
        spinnerMes.addChangeListener(dateChangeListener);
        spinnerAno.addChangeListener(dateChangeListener);

        JButton btnContratar = new JButton("Contratar");
        JButton btnCancelar = new JButton("Cancelar");

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(btnContratar);
        buttonPanel.add(btnCancelar);

        add(panel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        
        setVisible(true);

        btnContratar.addActionListener(e -> {
            try {
                Cliente clienteSelecionado = (Cliente) comboClientes.getSelectedItem();
                Pet petSelecionado = (Pet) comboPets.getSelectedItem();

                if (clienteSelecionado == null) {
                    JOptionPane.showMessageDialog(this, "Selecione um cliente.", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (petSelecionado == null) {
                    JOptionPane.showMessageDialog(this, "Selecione um pet.", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int dia = (int) spinnerDia.getValue();
                int mes = (int) spinnerMes.getValue();
                int ano = (int) spinnerAno.getValue();
                LocalDate dataServico = LocalDate.of(ano, mes, dia);

                if (!dataServico.isAfter(LocalDate.now())) {
                    JOptionPane.showMessageDialog(this, "A data do serviço deve ser futura.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Servico servico = new Servico(dataServico) {
                    @Override
                    public String getDescricao() {
                        return "Serviço de " + petSelecionado.getTipo() + " para " + petSelecionado.getNome();
                    }
                };
                
                ServicoDAO servicoDAO = new ServicoDAO();
                int idServicoGerado = servicoDAO.inserirServico(servico, clienteSelecionado.getId(), petSelecionado.getId()); 
                
                if (idServicoGerado != -1) {
                    servico.setId(idServicoGerado); 
                    servico.setClienteId(clienteSelecionado.getId());
                    servico.setPetId(petSelecionado.getId());
                    SistemaPetShop.adicionarServico(servico); 
                    JOptionPane.showMessageDialog(this, "Serviço contratado com sucesso!");
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Erro ao contratar serviço no banco de dados.", "Erro no Banco de Dados", JOptionPane.ERROR_MESSAGE);
                }

            } catch (DateTimeParseException ex) {
                JOptionPane.showMessageDialog(this, "Data inválida. Verifique o dia, mês e ano.", "Erro de Data", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro ao contratar serviço: " + ex.getMessage(), "Erro no Sistema", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        });

        btnCancelar.addActionListener(e -> dispose());
    }

    private boolean carregarClientesNoCombo() {
        comboClientes.removeAllItems();
        SistemaPetShop.carregarClientesDoBanco(); // Garante que a lista de clientes em SistemaPetShop esteja atualizada
        List<Cliente> clientes = SistemaPetShop.getClientes();
        if (clientes.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nenhum cliente cadastrado. Por favor, cadastre um cliente primeiro.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return false; 
        }
        for (Cliente c : clientes) {
            comboClientes.addItem(c);
        }
        return true; 
    }
    
    private void carregarPetsNoCombo() {
        comboPets.removeAllItems();
        Cliente clienteSelecionado = (Cliente) comboClientes.getSelectedItem();
        if (clienteSelecionado != null) {
            List<Pet> petsDoCliente = clienteSelecionado.getPets();
            if (petsDoCliente.isEmpty()) {
                // Combo de pets ficará vazio
            } else {
                for (Pet p : petsDoCliente) {
                    comboPets.addItem(p);
                }
            }
        }
    }
}