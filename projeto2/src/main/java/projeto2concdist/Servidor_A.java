package projeto2concdist;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class Servidor_A {

    private static final int PORT = 8080;
    private static final int SERVER_B_PORT = 8081;
    private static final String DATA_PATH = "projeto2/arquivos.json/data_A.json";

    public void iniciarServidor() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Servidor A iniciado na porta " + PORT);

            while (true) {
                try (Socket clientSocket = serverSocket.accept()) {
                    System.out.println("Conexão estabelecida com cliente: " + clientSocket.getInetAddress());

                    BufferedReader entrada = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    String busca = entrada.readLine();
                    System.out.println("Recebida consulta do cliente: " + busca);

                    // Busca local no Servidor A
                    String resultadoLocal = buscaLocal(busca);

                    // Busca no Servidor B
                    String resultadoServidorB = consultarServidorB(busca);

                    // Combina resultados
                    String resultadoFinal = resultadoLocal + "\n" + resultadoServidorB;

                    PrintWriter saida = new PrintWriter(clientSocket.getOutputStream(), true);
                    saida.println(resultadoFinal);

                } catch (IOException e) {
                    System.err.println("Erro na conexão com o cliente: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao iniciar o Servidor A: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private String buscaLocal(String busca) {
        List<Artigo> artigos = carregarDados(DATA_PATH);

        StringBuilder resultados = new StringBuilder();
        for (Artigo artigo : artigos) {
            if (artigo.getTitulo().toLowerCase().contains(busca.toLowerCase()) ||
                artigo.getIntroducao().toLowerCase().contains(busca.toLowerCase())) {
                resultados.append(artigo.getTitulo()).append("\n");
            }
        }

        return resultados.length() > 0 ? resultados.toString() : "Nenhum artigo encontrado no Servidor A.";
    }

    private String consultarServidorB(String busca) {
        try (Socket socket = new Socket("localhost", SERVER_B_PORT);
             PrintWriter saida = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            // Envia consulta para o Servidor B
            saida.println(busca);

            // Recebe resposta do Servidor B
            return entrada.readLine();

        } catch (IOException e) {
            System.err.println("Erro ao consultar o Servidor B: " + e.getMessage());
            return "Erro ao consultar o Servidor B.";
        }
    }

    private List<Artigo> carregarDados(String arquivoJson) {
        try (FileReader reader = new FileReader(arquivoJson)) {
            Type listType = new TypeToken<List<Artigo>>() {}.getType();
            return new Gson().fromJson(reader, listType);
        } catch (IOException e) {
            System.err.println("Erro ao carregar arquivo JSON: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    private static class Artigo {
        private String titulo;
        private String introducao;

        public String getTitulo() {
            return titulo;
        }

        public String getIntroducao() {
            return introducao;
        }
    }
}
