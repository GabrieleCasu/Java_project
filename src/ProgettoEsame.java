import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ProgettoEsame {
    static String nomeFileTesserati = "C:\\Users\\lekse\\IdeaProjects\\ProgettoPerEsame\\Tesserati";
    static String campiDisponibili = "C:\\Users\\lekse\\IdeaProjects\\ProgettoPerEsame\\Campi_da_gioco";
    static String orariDisponibili = "C:\\Users\\lekse\\IdeaProjects\\ProgettoPerEsame\\Orari_Prenotazioni";
    static ArrayList<Tesserati> tesserati;
    static ArrayList<Campi_da_gioco> campi;
    static ArrayList<Prenotazioni> prenotazioni;

    public static ArrayList<Tesserati> elencoTesserati(String filename) throws IOException {
        ArrayList<Tesserati> tesserati = new ArrayList<>();
        FileReader fr = new FileReader(filename);
        BufferedReader br = new BufferedReader(fr);
        String line;
        while ((line=br.readLine())!=null){
            String[] f = line.split(" ");
            tesserati.add(new Tesserati(f[0],f[1],f[2]));
        }
        br.close();
        fr.close();
        return tesserati;

    }

    public static ArrayList<Campi_da_gioco> elencoCampi(String filename) throws IOException {
        ArrayList<Campi_da_gioco> campi = new ArrayList<>();
        FileReader fr = new FileReader(filename);
        BufferedReader br = new BufferedReader(fr);
        String line;
        while ((line=br.readLine())!=null){
            String[] f = line.split(" ");
            campi.add(new Campi_da_gioco(f[0]));
        }
        br.close();
        fr.close();
        return campi;
    }

    public static ArrayList<Prenotazioni> elencoPrenotazioni(String filename) throws IOException {
        ArrayList<Prenotazioni> prenotazioni = new ArrayList<>();
        FileReader fr = new FileReader(filename);
        BufferedReader br = new BufferedReader(fr);
        String line;
        while ((line=br.readLine())!=null){
            String[] f = line.split(" ");
            prenotazioni.add(new Prenotazioni(f[0]));
        }
        br.close();
        fr.close();
        return prenotazioni;
    }

    public static void main(String[] args) throws IOException {
        ArrayList<Tesserati> tesserati = elencoTesserati(nomeFileTesserati);
        ArrayList<Campi_da_gioco> campi = elencoCampi(campiDisponibili);
        ArrayList<Prenotazioni> prenotazioni = elencoPrenotazioni(orariDisponibili);
        System.out.println("Elenco tesserati: ");
        for (Tesserati t : tesserati) {
            System.out.println(t);
        }
        System.out.println("Elenco campi disponibili: ");
        for (Campi_da_gioco t : campi) {
            System.out.println(t);
        }
        System.out.println("Elenco orari disponibili: ");
        for (Prenotazioni t : prenotazioni) {
            System.out.println(t);
        }
        }
    }