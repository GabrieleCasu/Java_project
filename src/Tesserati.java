//created by Gabriele Casu

import java.io.Serializable;

public class Tesserati implements Serializable ,Comparable<Tesserati>{
    private final String nomeTesserato;
    private final String cognomeTesserato;
    private final String codiceTesserato;

    public String getCodiceTesserato() {
        return codiceTesserato;
    }



    public Tesserati(String nomeTesserato, String cognomeTesserato, String codiceTesserato) {
        this.nomeTesserato = nomeTesserato;
        this.cognomeTesserato = cognomeTesserato;
        this.codiceTesserato = codiceTesserato;
    }

    public String toString() {
        return "Nome: " + nomeTesserato + " " + cognomeTesserato + ", Codice: " + codiceTesserato;
    }
    //ordina i tesserati in ordine alfabetico per cognome
    @Override
    // override è per rendere più chiaro che questo metodo andrà a sostituirne un altro nella superclasse
    public int compareTo(Tesserati t) {
        return this.cognomeTesserato.compareTo(t.cognomeTesserato);
    }

}
