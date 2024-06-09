//created by Gabriele Casu

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

public class Prenotazioni implements Serializable/*, Iterable<Tesserati>*/ {
    private static int contaID = 1;

    private int id;
    private Date data;
    private int inizio;
    private double prezzo;
    private Campi_da_gioco campo;
    private List<String> tesserati;

    public int getId() {
        return id;
    }

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

    public void setId(int id) {
        this.id = contaID++;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public void setInizio(int inizio) {
        this.inizio = inizio;
    }

    public void setCampo(Campi_da_gioco campo) {
        this.campo = campo;
    }

    public void setTesserati(List<String> tesserati) {
        this.tesserati = tesserati;
    }

    public void setPrezzo(double prezzo) {
        this.prezzo = prezzo;
    }

    public Prenotazioni(Date data,int inizio, double prezzo, Campi_da_gioco campo, List<String> tesserati ) {
        this.id = contaID++;
        this.data = data;
        this.inizio = inizio;
        this.prezzo = prezzo;
        this.campo = campo;
        this.tesserati = tesserati;

    }

    public String toString() {
        // il formatter serve a rendere più leggibile la data inserita
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDateTime date = new java.sql.Timestamp((data.getTime())).toLocalDateTime();
        String dataFormat = date.format(dtf);
        String inizioFormat = String.format("%02d:00",inizio);
        String fineFormat = String.format("%02d:00",inizio+1);
        return "ID: "+id+"\nPrenotazione per il giorno: "+dataFormat+" dalle ore "+inizioFormat+":00 alle ore "+(fineFormat)+":00\nCampo: "+campo+"\nPrezzo: "+prezzo+"$\nTesserati: "+tesserati+"\n";
    }


}
