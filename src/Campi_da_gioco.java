//created by Gabriele Casu

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Campi_da_gioco implements Serializable {
    private final String codiceCampo;
    private final String tipologiaCampo;
    private int maxGiocatori;
    //mappa per verificare che nella data scelta(prima String) e nell'orario scelto(seconda string), non sia presente
    //un numero eccessivo di giocatori
    private Map<String, Map<String,Integer>> prenotazioni;

    public String getCodiceCampo() {
        return codiceCampo;
    }

    public String getTipologiaCampo() {
        return tipologiaCampo;
    }

    public int getMaxGiocatori() {
        return maxGiocatori;
    }

    public boolean prenotazioniGiocatori(String data,String orario,int numGiocatori) {
        //se data non è presente nella mappa le viene associata un valore nullo nella Hashmap, se esiste non fa nulla
        //questo perchè anche se non è presente deve avere un valore nullo, non può essere vuoto
        prenotazioni.putIfAbsent(data, new HashMap<>());
        Map<String,Integer> orari = prenotazioni.get(data);
        //restituisce l'orario se è presente nella mappa, altrimenti 0
        int prenotati = orari.getOrDefault(orario,0);
        if(prenotati+numGiocatori > maxGiocatori) return false;
        else{
            orari.put(orario,prenotati+numGiocatori);
            return true;
        }
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
