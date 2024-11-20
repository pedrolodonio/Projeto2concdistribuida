package projeto2.servidor_A;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class Servidor_A {

    public static void main(String[] args) {

        int port = 8080;


        try(ServerSocket serverSocket = new ServerSocket(port)){
            System.out.println("Servidor A iniciado na porta "+ port);

            while (true) { 
                try (Socket clientSocket = serverSocket.accept()){
                    System.out.println("conexão estabelecida com " + clientSocket.getInetAddress());
                } catch(IOException e){
                    e.printStackTrace();
                }
                
            }
        }catch (IOException e){
            e.printStackTrace();
        
    }

}
}
