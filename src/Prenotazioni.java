//created by Gabriele Casu

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

public class Prenotazioni implements Serializable/*, Iterable<Tesserati>*/ {
    private final Date data;
    private final int inizio;
    private final double prezzo;
    private final Campi_da_gioco campo;
    private final List<String> tesserati;

    public Date getData() {
        return data;
    }

    public int getInizio() {
        return inizio;
    }

    public double getPrezzo() {
        return prezzo;
    }

    public Campi_da_gioco getCampo() {
        return campo;
    }

    public List<String> getTesserati() {
        return tesserati;
    }

    public Prenotazioni(Date data,int inizio, double prezzo, Campi_da_gioco campo, List<String> tesserati ) {
        this.data = data;
        this.inizio = inizio;
        this.prezzo = prezzo;
        this.campo = campo;
        this.tesserati = tesserati;

    }

    public String toString() {
        return "Prenotazione per il giorno: "+data+" dalle ore "+inizio+":00 alle ore "+(inizio+1)+":00\nCampo: "+campo+"\nPrezzo: "+prezzo+"\nTesserati: "+tesserati+"\n";
    }


}
