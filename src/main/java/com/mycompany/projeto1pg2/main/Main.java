package com.mycompany.projeto1pg2.main;


import java.util.Scanner;

//main
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        SistemaPetShop sistema = new SistemaPetShop(sc);

        while (true) {
            System.out.println("\n=== MENU PET SHOP ===");
            System.out.println("1. Cadastrar Cliente");
            System.out.println("2. Cadastrar Pet");
            System.out.println("3. Listar Clientes");
            System.out.println("4. Contratar Serviço");
            System.out.println("5. Listar Serviços");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");

            String opcao = sc.nextLine();
        

            switch (opcao) {
                case "1" -> SistemaPetShop.cadastrarCliente();
                case "2" -> sistema.cadastrarPet();
                case "3" -> SistemaPetShop.listarClientes();
                case "4" -> sistema.contratarServico();
                case "5" -> sistema.listarServicos();
                case "0" -> {
                    System.out.println("Encerrando o sistema. Até logo!");
                    return;
                }
                default -> System.out.println("Opção inválida.");
            }
        }
    }
}
