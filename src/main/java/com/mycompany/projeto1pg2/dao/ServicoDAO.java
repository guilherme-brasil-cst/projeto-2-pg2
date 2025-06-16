package com.mycompany.projeto1pg2.dao;

import com.mycompany.projeto1pg2.model.Servico;
import com.mycompany.projeto1pg2.model.BanhoETosa;
import com.mycompany.projeto1pg2.model.Adestramento;
import com.mycompany.projeto1pg2.model.ConsultaVeterinaria;
import com.mycompany.projeto1pg2.model.Hospedagem;
import com.mycompany.projeto1pg2.util.ConexaoMySQL;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ServicoDAO {

    public int inserirServico(Servico servico, int clienteId, int petId) {
        String sql = "INSERT INTO servico (descricao, data, cliente_id, pet_id) VALUES (?, ?, ?, ?)";
        int idGerado = -1;

        try (Connection conn = ConexaoMySQL.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, servico.getDescricao());
            stmt.setDate(2, Date.valueOf(servico.getData())); 
            stmt.setInt(3, clienteId);
            stmt.setInt(4, petId);

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        idGerado = rs.getInt(1);
                        servico.setId(idGerado);
                        servico.setClienteId(clienteId);
                        servico.setPetId(petId);
                        System.out.println("✅ Serviço salvo com sucesso no banco! ID: " + idGerado);
                    }
                }
            }

        } catch (SQLException e) {
            System.err.println("❌ Erro ao salvar serviço: " + e.getMessage());
            e.printStackTrace();
        }
        return idGerado;
    }

    public List<Servico> listarServicos() {
        List<Servico> servicos = new ArrayList<>();
        String sql = "SELECT id, descricao, data, cliente_id, pet_id FROM servico";

        try (Connection conn = ConexaoMySQL.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String descricao = rs.getString("descricao");
                LocalDate data = rs.getDate("data").toLocalDate();
                int clienteId = rs.getInt("cliente_id");
                int petId = rs.getInt("pet_id");

                Servico servico = new Servico(id, data, clienteId, petId) {
                    @Override
                    public String getDescricao() {
                        return descricao;
                    }
                };
                servicos.add(servico);
            }

        } catch (SQLException e) {
            System.err.println("❌ Erro ao listar serviços: " + e.getMessage());
            e.printStackTrace();
        }
        return servicos;
    }

    /*** NOVO: lista as classes de Serviços (tipos) disponíveis ***/
    public List<Servico> listarTipos() {
        // usa LocalDate.now() apenas como placeholder; a data real será aplicada na UI
        LocalDate placeholder = LocalDate.now();
        List<Servico> tipos = new ArrayList<>();
        tipos.add(new BanhoETosa(placeholder));
        tipos.add(new Adestramento(placeholder));
        tipos.add(new ConsultaVeterinaria(placeholder));
        tipos.add(new Hospedagem(placeholder));
        return tipos;
    }
}
