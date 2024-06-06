//created by Gabriele Casu

public class Campi_da_gioco {
    private final String tipologiaCampo;

    public String getTipologiaCampo() {
        return tipologiaCampo;
    }

    public Campi_da_gioco(String tipo) {
        this.tipologiaCampo = tipo;
    }

    public String toString() {
        return tipologiaCampo;
    }
}
