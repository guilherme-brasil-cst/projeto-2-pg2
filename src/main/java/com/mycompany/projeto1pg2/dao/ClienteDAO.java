package com.mycompany.projeto1pg2.dao;

import com.mycompany.projeto1pg2.util.ConexaoMySQL;
import com.mycompany.projeto1pg2.model.Cliente;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {

    // Inserir cliente no banco
    public void inserirCliente(Cliente cliente) {
        String sql = "INSERT INTO cliente (nome, telefone, email) VALUES (?, ?, ?)";

        try (Connection conn = ConexaoMySQL.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getTelefone());
            stmt.setString(3, cliente.getEmail());

            stmt.executeUpdate();
            System.out.println("✅ Cliente salvo no banco com sucesso!");

        } catch (SQLException e) {
            System.err.println("❌ Erro ao salvar cliente: " + e.getMessage());
        }
    }

    // Listar clientes do banco
    public List<Cliente> listarClientes() {
        List<Cliente> lista = new ArrayList<>();
        String sql = "SELECT * FROM cliente";

        try (Connection conn = ConexaoMySQL.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String nome = rs.getString("nome");
                String telefone = rs.getString("telefone");
                String email = rs.getString("email");

                Cliente cliente = new Cliente(nome, telefone, email);
                lista.add(cliente);
            }

        } catch (SQLException e) {
            System.err.println("❌ Erro ao listar clientes: " + e.getMessage());
        }

        return lista;
    }
}
