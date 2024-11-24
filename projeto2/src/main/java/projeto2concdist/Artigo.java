package projeto2concdist;

public class Artigo {
    private String titulo;
    private String introducao;

    public Artigo(String titulo, String introducao) {
        this.titulo = titulo;
        this.introducao = introducao;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getIntroducao() {
        return introducao;
    }
}
