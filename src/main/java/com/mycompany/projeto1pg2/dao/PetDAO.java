package com.mycompany.projeto1pg2.dao;

import com.mycompany.projeto1pg2.model.Pet;
import com.mycompany.projeto1pg2.util.ConexaoMySQL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PetDAO {

    public void salvar(Pet pet, int clienteId) {
        String sql = "INSERT INTO pets (nome, tipo, idade, cliente_id) VALUES (?, ?, ?, ?)";

        try (Connection conn = ConexaoMySQL.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, pet.getNome());
            stmt.setString(2, pet.getTipo());
            stmt.setInt(3, pet.getIdade());
            stmt.setInt(4, clienteId);

            stmt.executeUpdate();
            System.out.println("✅ Pet salvo com sucesso no banco!");

        } catch (SQLException e) {
            System.err.println("❌ Erro ao salvar pet: " + e.getMessage());
        }
    }
}
