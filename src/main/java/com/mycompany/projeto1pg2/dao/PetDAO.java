package com.mycompany.projeto1pg2.dao;

import com.mycompany.projeto1pg2.model.Pet;
import com.mycompany.projeto1pg2.util.ConexaoMySQL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Statement;

public class PetDAO {

    public int salvar(Pet pet, int clienteId) {
        String sql = "INSERT INTO pet (nome, tipo, idade, cliente_id) VALUES (?, ?, ?, ?)";
        int idGerado = -1;

        try (Connection conn = ConexaoMySQL.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, pet.getNome());
            stmt.setString(2, pet.getTipo());
            stmt.setInt(3, pet.getIdade());
            stmt.setInt(4, clienteId);

            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        idGerado = rs.getInt(1);
                        pet.setId(idGerado); 
                        pet.setClienteId(clienteId); 
                        System.out.println("✅ Pet salvo com sucesso no banco! ID: " + idGerado);
                    }
                }
            }

        } catch (SQLException e) {
            System.err.println("❌ Erro ao salvar pet: " + e.getMessage());
            e.printStackTrace();
        }
        return idGerado;
    }

    public List<Pet> listarPetsPorClienteId(int clienteId) {
        List<Pet> pets = new ArrayList<>();
        String sql = "SELECT id, nome, tipo, idade, cliente_id FROM pet WHERE cliente_id = ?";

        try (Connection conn = ConexaoMySQL.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, clienteId); 

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String nome = rs.getString("nome");
                    String tipo = rs.getString("tipo");
                    int idade = rs.getInt("idade");
                    int fkClienteId = rs.getInt("cliente_id");

                    Pet pet = new Pet(id, nome, tipo, idade, fkClienteId); 
                    pets.add(pet);
                }
            }

        } catch (SQLException e) {
            System.err.println("❌ Erro ao listar pets por cliente: " + e.getMessage());
            e.printStackTrace();
        }
        return pets;
    }
}