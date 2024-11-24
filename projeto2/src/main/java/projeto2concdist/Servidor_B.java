package projeto2concdist;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class Servidor_B {

    private static final int PORT = 8081;
    private static final String DATA_PATH = "projeto2/arquivos.json/data_B.json";

    public void iniciarServidor() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Servidor B iniciado na porta " + PORT);

            while (true) {
                try (Socket socket = serverSocket.accept()) {
                    System.out.println("Conexão estabelecida com " + socket.getInetAddress());

                    BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    String busca = entrada.readLine();
                    System.out.println("Buscando no Servidor B: " + busca);

                    String resultado = buscaLocal(busca);

                    PrintWriter saida = new PrintWriter(socket.getOutputStream(), true);
                    saida.println(resultado);

                } catch (IOException e) {
                    System.err.println("Erro na conexão com o cliente: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao iniciar o Servidor B: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private String buscaLocal(String busca) {
        // Carrega os dados do arquivo JSON
        JsonObject dados = carregarDados(DATA_PATH);

        StringBuilder resultados = new StringBuilder();

        // Obtém o objeto 'title' do JSON, que contém os títulos dos artigos
        JsonObject titulos = dados.getAsJsonObject("title");
        Set<Map.Entry<String, com.google.gson.JsonElement>> entradas = titulos.entrySet();

        // Verifica cada chave no objeto 'title' que representa o artigo
        for (Map.Entry<String, com.google.gson.JsonElement> entry : entradas) {
            String chave = entry.getKey();  // A chave é o ID do artigo (ex: "50000")
            String titulo = entry.getValue().getAsString();  // O valor é o título do artigo

            // Checa se o título contém a busca
            if (titulo.toLowerCase().contains(busca.toLowerCase())) {
                resultados.append("ID: ").append(chave).append(" - Título: ").append(titulo).append("\n"); // Adiciona os resultados
            }
        }

        // Se encontrar resultados, retorna os títulos encontrados, senão, uma mensagem informando que não encontrou nada
        return resultados.length() > 0 ? resultados.toString() : "Nenhum artigo encontrado no Servidor B.";
    }

    private JsonObject carregarDados(String arquivoJson) {
        // Tenta carregar os dados do arquivo JSON
        try (FileReader reader = new FileReader(arquivoJson)) {
            // Usa o Gson para desserializar o arquivo JSON
            return new Gson().fromJson(reader, JsonObject.class);
        } catch (IOException e) {
            System.err.println("Erro ao carregar arquivo JSON: " + e.getMessage());
            e.printStackTrace();
            return new JsonObject();
        }
    }
}
