package com.mycompany.projeto1pg2.test;

import com.mycompany.projeto1pg2.util.ConexaoMySQL;
import java.sql.Connection;
import java.sql.SQLException;

public class TesteConexao {
    public static void main(String[] args) {
        try {
            Connection conn = ConexaoMySQL.conectar();
            System.out.println("✅ Conexão com MySQL bem-sucedida!");
            conn.close();
        } catch (SQLException e) {
            System.err.println("❌ Erro ao conectar: " + e.getMessage());
        }
    }
}
