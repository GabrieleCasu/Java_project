//created by Gabriele Casu

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Prenotazioni implements Serializable/*, Iterable<Tesserati>*/ {
    //private final String giorno;
    private final String orario;
    //private final double prezzo;
    //private final String campo;
    //private final String stato;
    //private final List<Tesserati> codici;

    /*public String getGiorno() {
        return giorno;
    }*/

    public String getOrario() {
        return orario;
    }

   /* public double getPrezzo() {
        return prezzo;
    }

    public String getCampo() {
        return campo;
    }

    public String getStato() {
        return stato;
    }

    public List<Tesserati> getCodici() {
        return codici;
    }*/

    public Prenotazioni(/*String giorno,*/ String orario/*, double prezzo, String campo, String stato*/) {
        //this.giorno = giorno;
        this.orario = orario;
        //this.prezzo = prezzo;
        //this.campo = campo;
        //this.stato = stato;
        //this.codici = new ArrayList<>();
    }

    /*public Iterator<Tesserati> iterator() {
        return codici.iterator();
    }*/

    public String toString() {
        return orario;
    }


}
