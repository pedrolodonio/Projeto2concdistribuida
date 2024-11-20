package projeto2concdist;
public class Main{

    //teste servidor a
    public static void main(String[] args) {
        Thread threadSA = new Thread(() -> {
            Servidor_A SA = new Servidor_A();
            SA.iniciarservidor();
        });

        threadSA.start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Cliente usuario = new Cliente();
        usuario.FazConsulta("teste do servidor A");

    }
}

// para executar o projeto maven usar comando mvn exec:java