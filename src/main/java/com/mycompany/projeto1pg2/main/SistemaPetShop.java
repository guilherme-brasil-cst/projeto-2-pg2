package com.mycompany.projeto1pg2.main;

import com.mycompany.projeto1pg2.model.Pet;
import com.mycompany.projeto1pg2.model.Servico;
import com.mycompany.projeto1pg2.model.Cliente;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SistemaPetShop {

    public SistemaPetShop(Scanner sc) {
    }

    private static final List<Cliente> clientes = new ArrayList<>();
    private static final List<Servico> servicos = new ArrayList<>();
    private static final Scanner scanner = new Scanner(System.in);

    // Cadastrar cliente (modo console)
    public static void cadastrarCliente() {
        System.out.println("\n=== CADASTRO DE CLIENTE ===");

        String nome, telefone, email;

        while (true) {
            System.out.print("Nome: ");
            nome = scanner.nextLine();
            System.out.print("Telefone: ");
            telefone = scanner.nextLine();
            System.out.print("Email: ");
            email = scanner.nextLine();

            try {
                Cliente cliente = new Cliente(nome, telefone, email);
                clientes.add(cliente);
                System.out.println("Cliente cadastrado com sucesso!");
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("Erro: " + e.getMessage());
                System.out.println("Tente novamente.\n");
            }
        }
    }

    // Listar clientes (modo console)
    public static void listarClientes() {
        System.out.println("\n=== LISTA DE CLIENTES ===");
        if (clientes.isEmpty()) {
            System.out.println("Nenhum cliente cadastrado.");
        } else {
            for (int i = 0; i < clientes.size(); i++) {
                Cliente c = clientes.get(i);
                System.out.printf("%d - %s | %s | %s%n", i + 1, c.getNome(), c.getTelefone(), c.getEmail());
            }
        }
    }

    // Cadastrar pet (modo console)
    public void cadastrarPet() {
        System.out.println("\n=== CADASTRO DE PET ===");

        if (clientes.isEmpty()) {
            System.out.println("Nenhum cliente cadastrado. Cadastre um cliente primeiro.");
            return;
        }

        listarClientes();
        System.out.print("Escolha o número do cliente dono do pet: ");
        try {
            int indice = Integer.parseInt(scanner.nextLine()) - 1;
            if (indice < 0 || indice >= clientes.size()) {
                System.out.println("Cliente inválido.");
                return;
            }

            Cliente cliente = clientes.get(indice);

            System.out.print("Nome do pet: ");
            String nome = scanner.nextLine();
            System.out.print("Tipo do pet (Ex: cachorro, gato): ");
            String tipo = scanner.nextLine();
            System.out.print("Idade do pet: ");
            int idade = Integer.parseInt(scanner.nextLine());

            if (idade <= 0 || idade > 30) {
                System.out.println("A idade do pet deve estar entre 1 e 30 anos.");
                return;
            }

            try {
                Pet pet = new Pet(nome, tipo, idade);
                cliente.adicionarPet(pet);
                System.out.println("Pet cadastrado com sucesso!");
            } catch (IllegalArgumentException e) {
                System.out.println("Erro ao cadastrar pet: " + e.getMessage());
            }

        } catch (NumberFormatException e) {
            System.out.println("Entrada inválida. Digite um número válido.");
        }
    }

    // Contratar serviço (modo console)
    public void contratarServico() {
        System.out.println("\n=== CONTRATAÇÃO DE SERVIÇO ===");

        if (clientes.isEmpty()) {
            System.out.println("Nenhum cliente cadastrado.");
            return;
        }

        listarClientes();
        System.out.print("Escolha o número do cliente: ");
        try {
            int indiceCliente = Integer.parseInt(scanner.nextLine()) - 1;
            if (indiceCliente < 0 || indiceCliente >= clientes.size()) {
                System.out.println("Cliente inválido.");
                return;
            }

            Cliente cliente = clientes.get(indiceCliente);
            List<Pet> petsDoCliente = cliente.getPets();

            if (petsDoCliente.isEmpty()) {
                System.out.println("Este cliente não possui pets cadastrados.");
                return;
            }

            System.out.println("Pets do cliente:");
            for (int i = 0; i < petsDoCliente.size(); i++) {
                System.out.printf("%d - %s%n", i + 1, petsDoCliente.get(i));
            }

            System.out.print("Escolha o número do pet: ");
            int indicePet = Integer.parseInt(scanner.nextLine()) - 1;
            if (indicePet < 0 || indicePet >= petsDoCliente.size()) {
                System.out.println("Pet inválido.");
                return;
            }

            System.out.print("Digite a data do serviço (AAAA-MM-DD): ");
            String dataStr = scanner.nextLine();

            try {
                java.time.LocalDate data = java.time.LocalDate.parse(dataStr);

                if (!data.isAfter(java.time.LocalDate.now())) {
                    System.out.println("A data deve ser futura.");
                    return;
                }

                Servico servico = new Servico(data) {
                    @Override
                    public String getDescricao() {
                        return "Consulta Veterinária para " + petsDoCliente.get(indicePet).getNome();
                    }
                };

                servicos.add(servico);
                System.out.println("Serviço contratado com sucesso!");

            } catch (java.time.format.DateTimeParseException e) {
                System.out.println("Data inválida. Use o formato AAAA-MM-DD.");
            } catch (IllegalArgumentException e) {
                System.out.println("Erro: " + e.getMessage());
            }

        } catch (NumberFormatException e) {
            System.out.println("Entrada inválida. Use apenas números.");
        }
    }

    // Listar serviços (modo console)
    public void listarServicos() {
        System.out.println("\n=== LISTA DE SERVIÇOS CONTRATADOS ===");

        if (servicos.isEmpty()) {
            System.out.println("Nenhum serviço contratado.");
        } else {
            for (int i = 0; i < servicos.size(); i++) {
                Servico s = servicos.get(i);
                System.out.printf("%d - %s | Data: %s%n", i + 1, s.getDescricao(), s.getData());
            }
        }
    }

    // Acesso à lista de clientes
    public static List<Cliente> getClientes() {
        return clientes;
    }

    // Acesso à lista de serviços (para Swing)
    public static List<Servico> getServicos() {
        return servicos;
    }

    // Adicionar serviço (usado pela GUI)
    public static void adicionarServico(Servico servico) {
        servicos.add(servico);
    }
}
