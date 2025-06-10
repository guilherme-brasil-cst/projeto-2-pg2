package com.mycompany.projeto1pg2.view;

import javax.swing.*;
import java.awt.*;

public class MenuPrincipal extends JFrame {

    public MenuPrincipal() {
        setTitle("Sistema Pet Shop");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(400, 450); // Ajustado para dar espaço para o título e botões
        setLocationRelativeTo(null); // Centraliza na tela

        // Painel principal
        JPanel painelPrincipal = new JPanel();
        painelPrincipal.setLayout(new BorderLayout(10, 10));
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // margem

        // Título
        JLabel titulo = new JLabel("Bem-vindo ao Pet Shop!", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        painelPrincipal.add(titulo, BorderLayout.NORTH);

        // Painel de botões
        JPanel painelBotoes = new JPanel();
        painelBotoes.setLayout(new BoxLayout(painelBotoes, BoxLayout.Y_AXIS));

        // Cria os botões
        String[] opcoes = {
            "Cadastrar Cliente",
            "Cadastrar Pet",
            "Contratar Serviço",
            "Listar Clientes",
            "Listar Serviços",
            "Sair"
        };

        for (String texto : opcoes) {
            JButton botao = new JButton(texto);
            botao.setAlignmentX(Component.CENTER_ALIGNMENT); // Centraliza horizontalmente
            botao.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40)); // Expande largura, altura fixa
            painelBotoes.add(Box.createVerticalStrut(10)); // espaço entre botões
            painelBotoes.add(botao);

            // Ações dos botões - Apenas instancia as janelas, elas controlam sua própria visibilidade
            switch (texto) {
                case "Cadastrar Cliente" ->
                    botao.addActionListener(e -> new CadastroCliente());
                case "Cadastrar Pet" ->
                    botao.addActionListener(e -> new CadastroPet());
                case "Contratar Serviço" ->
                    botao.addActionListener(e -> new ContratarServico());
                case "Listar Clientes" ->
                    botao.addActionListener(e -> new ListarClientes());
                case "Listar Serviços" ->
                    botao.addActionListener(e -> new ListarServicos());
                case "Sair" ->
                    botao.addActionListener(e -> System.exit(0));
            }
        }

        painelPrincipal.add(painelBotoes, BorderLayout.CENTER);
        add(painelPrincipal);
        // O setVisible(true) para a MenuPrincipal já está aqui no final do construtor
        setVisible(true); 
    }

    // O método main principal da aplicação, responsável por iniciar a GUI na EDT.
    public static void main(String[] args) {
        // A carga dos clientes do banco é feita no SistemaPetShop.main
        // antes de chamar SwingUtilities.invokeLater para a MenuPrincipal
        // então não precisamos duplicar aqui.
        SwingUtilities.invokeLater(() -> new MenuPrincipal()); // A própria MenuPrincipal se torna visível.
    }
}