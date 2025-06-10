package com.mycompany.projeto1pg2.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoMySQL {
    private static final String URL = "jdbc:mysql://localhost:3306/petshop";
    private static final String USUARIO = "root";
    private static final String SENHA = "root160055"; // VERIFIQUE ESTA SENHA!

    public static Connection conectar() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, SENHA);
    }
}