package projeto2concdist;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Cliente {

    private static final String HOST_A = "localhost";
    private static final int PORTA_A = 8080;

    public void fazerConsulta(String consulta) {
        try (Socket socket = new Socket(HOST_A, PORTA_A);
             PrintWriter saida = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            // Envia a consulta
            saida.println(consulta);

            // Recebe a resposta
            String resposta = entrada.readLine();
            System.out.println("Resposta do servidor: " + resposta);

        } catch (IOException e) {
            System.err.println("Erro na comunicação com o Servidor A: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
