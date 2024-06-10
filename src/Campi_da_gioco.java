//created by Gabriele Casu

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

public class Campi_da_gioco implements Serializable {
    private final String codiceCampo;
    private final String tipologiaCampo;
    private final int maxGiocatori;
    //private List<Prenotazioni> prenotazioni = new ArrayList<>();
    //mappa per verificare che nella data scelta(prima String) e nell'orario scelto(seconda string), non sia presente
    //un numero eccessivo di giocatori
    private final Map<LocalDate, Map<LocalTime,Integer>> prenotazioni;

    public String getCodiceCampo() {
        return codiceCampo;
    }

    public String getTipologiaCampo() {
        return tipologiaCampo;
    }

    public int getMaxGiocatori() {
        return maxGiocatori;
    }

    public void aggiungiPrenotazione(LocalDate date, LocalTime time, int giocatori) {
        prenotazioni.putIfAbsent(date,new HashMap<>());
        Map<LocalTime,Integer> orari = prenotazioni.get(date);
        orari.put(time,orari.getOrDefault(time,0) + giocatori);
    }
    //set per evitare doppioni
    public Set<LocalTime> getPrenotazioni(LocalDate date) {
        if(!prenotazioni.containsKey(date)) return new HashSet<>();
        return prenotazioni.get(date).keySet();
    }

    public int getPrenotazioni(LocalDate date, LocalTime time) {
        if(prenotazioni.containsKey(date) && prenotazioni.get(date).containsKey(time)) return prenotazioni.get(date).get(time);
        return 0;
    }

    public Campi_da_gioco(String codice, String tipo, int maxGiocatori) {
        this.codiceCampo = codice;
        this.tipologiaCampo = tipo;
        this.maxGiocatori = maxGiocatori;
        this.prenotazioni = new HashMap<>();
    }

    public String toString() {
        return "Codice campo: " +codiceCampo + ", tipologia: "+tipologiaCampo+",\tmaxGiocatori: "+maxGiocatori;
    }
}
