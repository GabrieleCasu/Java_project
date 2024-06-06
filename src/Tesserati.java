//created by Gabriele Casu

import java.io.Serializable;

public class Tesserati implements Serializable ,Comparable<Tesserati>{
    private final String nomeTesserato;
    private final String cognomeTesserato;
    private final String codiceTesserato;

    public String getNomeTesserato() {
        return nomeTesserato;
    }

    public String getCognomeTesserato() {
        return cognomeTesserato;
    }

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

    public int compareTo(Tesserati t) {
        return this.cognomeTesserato.compareTo(t.cognomeTesserato);
    }

}
