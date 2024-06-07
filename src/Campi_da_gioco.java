//created by Gabriele Casu

import java.io.Serializable;

public class Campi_da_gioco implements Serializable {
    private final String codiceCampo;
    private final String tipologiaCampo;

    public String getCodiceCampo() {
        return codiceCampo;
    }

    public String getTipologiaCampo() {
        return tipologiaCampo;
    }

    public Campi_da_gioco(String codice, String tipo) {
        this.codiceCampo = codice;
        this.tipologiaCampo = tipo;
    }

    public String toString() {
        return "Codice campo: " +codiceCampo + ", tipologia: "+tipologiaCampo;
    }
}
