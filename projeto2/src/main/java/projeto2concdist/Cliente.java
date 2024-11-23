package projeto2concdist;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Cliente {

     
    public void FazConsulta(String consulta){
       
        try (
        Socket socket = new Socket ("localhost",8080);
        PrintWriter out = new PrintWriter (socket.getOutputStream(),true);
        BufferedReader in = new BufferedReader (new InputStreamReader (socket.getInputStream()));
        BufferedReader entradaUsuario = new BufferedReader (new InputStreamReader(System.in))
        ) {

            String busca;

            while (true) { 
                System.out.println("Faça sua busca ou digite 'cancelar' para cancelar: ");
                busca = entradaUsuario.readLine();
                    if("cancelar".equalsIgnoreCase(busca)){
                        System.out.println("Encerrando...");
                        break;
                    }
            }

            out.println(busca);

            String resultado = in.readLine();

            if (resultado != null){
                System.out.println("Resultado encontrado: " + resultado);
            }

            else{
                System.out.println("resultado não encontrado");
            }

            
        } catch(IOException e){
            System.err.println("erro ao estabelecer conexão com o servidor" +e.getMessage());
            e.printStackTrace();
        }
        
        
}
    

}
