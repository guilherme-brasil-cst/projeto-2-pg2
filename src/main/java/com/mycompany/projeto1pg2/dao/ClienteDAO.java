package com.mycompany.projeto1pg2.dao;

import com.mycompany.projeto1pg2.util.ConexaoMySQL;
import com.mycompany.projeto1pg2.model.Cliente;
import com.mycompany.projeto1pg2.model.Pet;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {

    public int inserirCliente(Cliente cliente) { 
        String sql = "INSERT INTO cliente (nome, telefone, email) VALUES (?, ?, ?)";
        int idGerado = -1; 

        try (Connection conn = ConexaoMySQL.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) { 

            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getTelefone());
            stmt.setString(3, cliente.getEmail());

            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        idGerado = rs.getInt(1); 
                        cliente.setId(idGerado); 
                        System.out.println("✅ Cliente salvo no banco com sucesso! ID: " + idGerado);
                    }
                }
            }

        } catch (SQLException e) {
            System.err.println("❌ Erro ao salvar cliente: " + e.getMessage());
            e.printStackTrace(); 
        }
        return idGerado; 
    }

    public List<Cliente> listarClientes() {
        List<Cliente> lista = new ArrayList<>();
        String sql = "SELECT id, nome, telefone, email FROM cliente"; 

        try (Connection conn = ConexaoMySQL.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            PetDAO petDAO = new PetDAO(); 

            while (rs.next()) {
                int id = rs.getInt("id"); 
                String nome = rs.getString("nome");
                String telefone = rs.getString("telefone");
                String email = rs.getString("email");

                Cliente cliente = new Cliente(id, nome, telefone, email);
                
                List<Pet> petsDoCliente = petDAO.listarPetsPorClienteId(id);
                cliente.setPets(petsDoCliente); 
                
                lista.add(cliente);
            }

        } catch (SQLException e) {
            System.err.println("❌ Erro ao listar clientes: " + e.getMessage());
            e.printStackTrace(); 
        }

        return lista;
    }
}
