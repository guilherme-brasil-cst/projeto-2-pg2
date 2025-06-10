package com.mycompany.projeto1pg2.main;

import com.mycompany.projeto1pg2.dao.ClienteDAO;
import com.mycompany.projeto1pg2.dao.PetDAO; 
import com.mycompany.projeto1pg2.dao.ServicoDAO; // Importar ServicoDAO
import com.mycompany.projeto1pg2.model.Cliente;
import com.mycompany.projeto1pg2.model.Pet;
import com.mycompany.projeto1pg2.model.Servico;
import com.mycompany.projeto1pg2.view.MenuPrincipal;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.swing.SwingUtilities;
import javax.swing.JOptionPane; 

public class SistemaPetShop {

    private static List<Cliente> clientes = new ArrayList<>(); 
    private static List<Servico> servicos = new ArrayList<>(); // REMOVIDO 'final' para permitir recarga
    private static final Scanner scanner = new Scanner(System.in); 

    public SistemaPetShop() { }
    
    public static void carregarClientesDoBanco() {
        ClienteDAO clienteDAO = new ClienteDAO();
        try {
            clientes = clienteDAO.listarClientes(); 
            System.out.println("Clientes carregados do banco: " + clientes.size());
        } catch (Exception e) { 
            System.err.println("❌ Erro ao carregar clientes do banco: " + e.getMessage());
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, 
                "Erro ao carregar dados iniciais do banco de dados.\n" + 
                "Verifique sua conexão com o MySQL e o log do console para detalhes.", 
                "Erro de Inicialização", JOptionPane.ERROR_MESSAGE);
        }
    }

    // NOVO MÉTODO: Carregar serviços do banco de dados
    public static void carregarServicosDoBanco() {
        ServicoDAO servicoDAO = new ServicoDAO();
        try {
            servicos = servicoDAO.listarServicos();
            System.out.println("Serviços carregados do banco: " + servicos.size());
        } catch (Exception e) {
            System.err.println("❌ Erro ao carregar serviços do banco: " + e.getMessage());
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,
                "Erro ao carregar serviços do banco de dados.\n" +
                "Verifique sua conexão com o MySQL e o log do console para detalhes.",
                "Erro de Inicialização", JOptionPane.ERROR_MESSAGE);
        }
    }


    public static List<Cliente> getClientes() {
        return clientes;
    }

    public static void adicionarClienteNaLista(Cliente cliente) {
        clientes.add(cliente);
    }
    
    // --- MÉTODOS DE CONSOLE (MANTIDOS) ---
    public static void cadastrarCliente() {
        System.out.println("\n=== CADASTRO DE CLIENTE (Console) ===");
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
                ClienteDAO clienteDAO = new ClienteDAO();
                int idClienteGerado = clienteDAO.inserirCliente(cliente); 
                if (idClienteGerado != -1) {
                    cliente.setId(idClienteGerado); 
                    clientes.add(cliente); 
                    System.out.println("Cliente cadastrado com sucesso (via console e DB)! ID: " + idClienteGerado);
                    break;
                } else { 
                    System.out.println("Erro ao cadastrar cliente no banco de dados (console)."); 
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Erro de validação (console): " + e.getMessage());
                System.out.println("Tente novamente.\n");
            } catch (Exception e) { 
                System.out.println("Erro ao salvar cliente no banco (console): " + e.getMessage()); 
                e.printStackTrace(); 
            }
        }
    }

    public void cadastrarPet() { 
        System.out.println("\n=== CADASTRO DE PET (Console) ===");

        if (clientes.isEmpty()) { 
            System.out.println("Nenhum cliente cadastrado. Cadastre um cliente primeiro (console)."); 
            return; 
        }

        listarClientesConsole(); 
        System.out.print("Escolha o número do cliente dono do pet: ");
        try {
            int indice = Integer.parseInt(scanner.nextLine()) - 1;
            if (indice < 0 || indice >= clientes.size()) { 
                System.out.println("Cliente inválido (console)."); 
                return; 
            }

            Cliente clienteSelecionado = clientes.get(indice); 

            System.out.print("Nome do pet: "); 
            String nomePet = scanner.nextLine();
            System.out.print("Tipo do pet (Ex: cachorro, gato): "); 
            String tipoPet = scanner.nextLine();
            System.out.print("Idade do pet: "); 
            int idade = Integer.parseInt(scanner.nextLine());

            if (idade <= 0 || idade > 30) { 
                System.out.println("A idade do pet deve estar entre 1 e 30 anos (console)."); 
                return; 
            }

            try {
                Pet pet = new Pet(nomePet, tipoPet, idade);
                PetDAO petDAO = new PetDAO(); 
                int idPetGerado = petDAO.salvar(pet, clienteSelecionado.getId()); 
                
                if (idPetGerado != -1) {
                    pet.setId(idPetGerado);
                    pet.setClienteId(clienteSelecionado.getId());
                    clienteSelecionado.adicionarPet(pet); 
                    System.out.println("Pet cadastrado com sucesso (via console e DB)! ID: " + idPetGerado);
                } else {
                    System.out.println("Erro ao salvar pet no banco de dados (console).");
                }
            } catch (IllegalArgumentException e) { 
                System.out.println("Erro ao cadastrar pet (console): " + e.getMessage()); 
            } catch (Exception e) { 
                System.out.println("Erro ao salvar pet no banco (console): " + e.getMessage()); 
                e.printStackTrace(); 
            }

        } catch (NumberFormatException e) {
            System.out.println("Entrada inválida. Digite um número válido (console).");
        }
    }

    public static void listarClientesConsole() {
        System.out.println("\n=== LISTA DE CLIENTES (Console) ===");
        if (clientes.isEmpty()) { 
            System.out.println("Nenhum cliente cadastrado (console)."); 
        } else {
            for (int i = 0; i < clientes.size(); i++) {
                Cliente c = clientes.get(i);
                System.out.printf("%d - ID: %d | %s | %s | %s | Pets: %d%n", 
                                  i + 1, c.getId(), c.getNome(), c.getTelefone(), c.getEmail(), c.getPets().size());
                for (Pet p : c.getPets()) {
                    System.out.printf("    - Pet ID: %d | Nome: %s | Tipo: %s | Idade: %d%n", 
                                      p.getId(), p.getNome(), p.getTipo(), p.getIdade());
                }
            }
        }
    }
    
    public void contratarServico() {
        System.out.println("\n=== CONTRATAÇÃO DE SERVIÇO (Console) ===");
        // TODO: Implementar persistência de serviço via console se necessário
        System.out.println("Funcionalidade de contratação de serviço via console (apenas em memória).");
    }
    
    public void listarServicos() {
        System.out.println("\n=== LISTA DE SERVIÇOS CONTRATADOS (Console) ===");
        // TODO: Implementar carregamento de serviço via console se necessário
        System.out.println("Lista de serviços (apenas em memória): " + servicos.size());
    }

    public static List<Servico> getServicos() {
        return servicos;
    }

    // Método para adicionar serviço à lista em memória (chamado após persistência no DB)
    public static void adicionarServico(Servico servico) {
        servicos.add(servico);
    }
    
    public static void main(String[] args) {
        // Carrega clientes e serviços do banco na inicialização
        carregarClientesDoBanco(); 
        carregarServicosDoBanco(); // NOVO: Carregar serviços também
        
        SwingUtilities.invokeLater(() -> {
            new MenuPrincipal().setVisible(true); 
        });

        // MENU DE CONSOLE SECUNDÁRIO (mantido como estava)
        SistemaPetShop sistemaConsole = new SistemaPetShop(); 
        int opcao;
        do {
            System.out.println("\n--- MENU DE CONSOLE (SECUNDÁRIO) ---");
            System.out.println("1. Cadastrar Cliente (Console)");
            System.out.println("2. Listar Clientes (Console)");
            System.out.println("3. Cadastrar Pet (Console)");
            System.out.println("4. Contratar Serviço (Console)");
            System.out.println("5. Listar Serviços (Console)");
            System.out.println("0. Sair do Console");
            System.out.print("Escolha uma opção: ");
            try {
                opcao = Integer.parseInt(scanner.nextLine());
                switch (opcao) {
                    case 1: 
                        cadastrarCliente(); 
                        break; 
                    case 2: 
                        listarClientesConsole(); 
                        break; 
                    case 3: 
                        sistemaConsole.cadastrarPet(); 
                        break; 
                    case 4: 
                        sistemaConsole.contratarServico(); 
                        break; 
                    case 5: 
                        sistemaConsole.listarServicos(); 
                        break; 
                    case 0: 
                        System.out.println("Saindo do menu de console."); 
                        break;
                    default: 
                        System.out.println("Opção inválida."); 
                        break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Digite um número.");
                opcao = -1; 
            }
            // Recarrega clientes e serviços após cada operação no console para manter a consistência
            carregarClientesDoBanco();
            carregarServicosDoBanco(); // NOVO: Recarregar serviços também
        } while (opcao != 0);
        scanner.close(); 
        System.exit(0); 
    }
}
