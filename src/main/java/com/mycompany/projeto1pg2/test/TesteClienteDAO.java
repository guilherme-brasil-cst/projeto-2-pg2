package com.mycompany.projeto1pg2.test;

import com.mycompany.projeto1pg2.dao.ClienteDAO;
import com.mycompany.projeto1pg2.model.Cliente;
import java.util.List;

public class TesteClienteDAO {
    public static void main(String[] args) {
        ClienteDAO dao = new ClienteDAO();

        // Testar inserção
        Cliente novo = new Cliente("Maria", "6199999-0000", "maria@email.com");
        dao.inserirCliente(novo);

        // Testar listagem
        List<Cliente> clientes = dao.listarClientes();
        for (Cliente c : clientes) {
            System.out.println("Nome: " + c.getNome() + ", Telefone: " + c.getTelefone() + ", Email: " + c.getEmail());
        }
    }
}
