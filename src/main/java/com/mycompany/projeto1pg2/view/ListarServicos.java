package com.mycompany.projeto1pg2.view;

import com.mycompany.projeto1pg2.main.SistemaPetShop;
import com.mycompany.projeto1pg2.model.Servico;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ListarServicos extends JFrame {

    private JTable tableServicos; // Adicionado para referência à tabela
    private DefaultTableModel tableModel; // Adicionado para referência ao modelo da tabela

    public ListarServicos() {
        setTitle("Lista de Serviços Contratados");
        setSize(600, 400); // Ajustado para ser consistente com ListarClientes
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // Define as colunas da tabela
        // Adicionado ID Cliente e ID Pet para consistência com o DB e o modelo Servico.java
        String[] colunas = {"ID Serviço", "Descrição", "Data", "ID Cliente", "ID Pet"}; 

        tableModel = new DefaultTableModel(colunas, 0); // Inicializa tableModel
        tableServicos = new JTable(tableModel); // Inicializa tableServicos
        JScrollPane scrollPane = new JScrollPane(tableServicos);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(scrollPane, BorderLayout.CENTER);

        JButton btnSair = new JButton("Sair");
        btnSair.addActionListener(e -> dispose());
        JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        southPanel.add(btnSair);
        panel.add(southPanel, BorderLayout.SOUTH);

        add(panel);
        
        carregarServicosNaTabela(); // Carrega serviços na tabela ao iniciar a janela
        
        setVisible(true); // Torna a janela visível
    }

    // NOVO MÉTODO: Para carregar/recarregar os serviços na tabela
    private void carregarServicosNaTabela() {
        tableModel.setRowCount(0); // Limpa a tabela
        
        // Garante que a lista de serviços em SistemaPetShop esteja atualizada com os dados do banco
        SistemaPetShop.carregarServicosDoBanco(); 
        
        List<Servico> servicos = SistemaPetShop.getServicos(); // Pega a lista de serviços atualizada
        DateTimeFormatter formatoBr = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        if (servicos.isEmpty()) {
            System.out.println("Nenhum serviço carregado para exibição na tabela."); 
        } else {
            for (Servico s : servicos) {
                Object[] linha = {
                    s.getId(), // ID do serviço
                    s.getDescricao(),
                    s.getData().format(formatoBr),
                    s.getClienteId(), // ID do cliente
                    s.getPetId()     // ID do pet
                };
                tableModel.addRow(linha);
            }
        }
    }
    
    // Recarrega a tabela sempre que a janela for exibida (se estiver visível)
    @Override
    public void setVisible(boolean b) {
        super.setVisible(b);
        if (b) { 
            carregarServicosNaTabela();
        }
    }
}
