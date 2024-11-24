package projeto2concdist;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        // Inicializa o Servidor B em uma thread separada
        Thread threadSB = new Thread(() -> {
            Servidor_B servidorB = new Servidor_B();
            servidorB.iniciarServidor();
        });

        // Inicializa o Servidor A em outra thread separada
        Thread threadSA = new Thread(() -> {
            Servidor_A servidorA = new Servidor_A();
            servidorA.iniciarServidor();
        });

        // Inicia o Servidor B
        threadSB.start();
        try {
            Thread.sleep(2000); // Garante que o Servidor B esteja ativo antes do Servidor A
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Inicia o Servidor A
        threadSA.start();
        try {
            Thread.sleep(2000); // Garante que o Servidor A esteja ativo antes de iniciar o cliente
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Inicia o Cliente
        Cliente cliente = new Cliente();
        System.out.println("Digite suas consultas (ou 'sair' para encerrar):");

        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                System.out.print("> ");
                String consulta = scanner.nextLine();

                if (consulta.equalsIgnoreCase("sair")) {
                    System.out.println("Encerrando o cliente...");
                    break;
                }

                cliente.fazerConsulta(consulta);
            }
        }
    }
}
