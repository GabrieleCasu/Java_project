import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static java.lang.System.exit;

public class ProgettoEsame {
    static String nomeFileTesserati = "C:\\Users\\lekse\\IdeaProjects\\ProgettoPerEsame\\Tesserati";
    static String campiDisponibili = "C:\\Users\\lekse\\IdeaProjects\\ProgettoPerEsame\\Campi_da_gioco";
    static String orariDisponibili = "C:\\Users\\lekse\\IdeaProjects\\ProgettoPerEsame\\Orari_Prenotazioni";
    static String filePrenotazioni = "C:\\Users\\lekse\\IdeaProjects\\ProgettoPerEsame\\src\\Prenotazioni";
    static ArrayList<Tesserati> tesserati;
    static ArrayList<Campi_da_gioco> campi;
    static ArrayList<Prenotazioni> prenotazioni;
    static ArrayList<String> orari;

    public static void salvaPrenotazioni(ArrayList<Prenotazioni> prenotazioni,String filename) throws IOException {
        FileOutputStream fos = new FileOutputStream(filename);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(prenotazioni);
        oos.close();
        fos.close();
    }

    public static void RipristinaPrenotazioni(String filename) throws IOException, ClassNotFoundException {
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename));
        prenotazioni = (ArrayList<Prenotazioni>) ois.readObject();
        System.out.println("File delle prenotazioni ripristinato.");
        ois.close();
    }

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
            campi.add(new Campi_da_gioco(f[0],f[1]));
        }
        br.close();
        fr.close();
        return campi;
    }

    public static ArrayList<String> elencoOrari(String filename) throws IOException {
        ArrayList<String> orari = new ArrayList<>();
        FileReader fr = new FileReader(filename);
        BufferedReader br = new BufferedReader(fr);
        String line;
        while ((line=br.readLine())!=null){
            orari.add(line);
        }
        br.close();
        fr.close();
        return orari;
    }

    public static Tesserati getTesserati(String codice, ArrayList<Tesserati> tesserati) {
        for (Tesserati t : tesserati) {
            if(t.getCodiceTesserato().equals(codice)) return t;
        }
        return null;
    }

    public static Campi_da_gioco getCampi(String codice, ArrayList<Campi_da_gioco> campi) {
        for (Campi_da_gioco t : campi) {
            if(t.getCodiceCampo().equals(codice)) return t;
        }
        return null;
    }
    /*
    public static ArrayList<Prenotazioni> elencoPrenotazioni(String filename) throws IOException {
        ArrayList<Prenotazioni> prenotazioni = new ArrayList<>();
        FileReader fr = new FileReader(filename);
        BufferedReader br = new BufferedReader(fr);
        String line;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        while ((line=br.readLine())!=null){
            String[] f = line.split(" ");
            String[] tesseratiString = line.substring(11).split(", ");
            List<String> tesserati = new ArrayList<>();
            double prezzo = Double.parseDouble(line.substring(8));
            for (String t : tesseratiString) {
                tesserati.add(t);
            }
            prenotazioni.add(new Prenotazioni(f[0],prezzo,f[2],f[3],tesserati));
        }
        br.close();
        fr.close();
        return prenotazioni;
    }*/

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("HH:mm");
        ArrayList<Tesserati> tesserati = elencoTesserati(nomeFileTesserati);
        ArrayList<Campi_da_gioco> campi = elencoCampi(campiDisponibili);
        ArrayList<String> orari = elencoOrari(orariDisponibili);

        try{
            RipristinaPrenotazioni(filePrenotazioni);
        }catch (IOException | ClassNotFoundException e) {
            prenotazioni = new ArrayList<>();
        }
        System.out.println("Elenco tesserati: ");
        for (Tesserati t : tesserati) {
            System.out.println(t);
        }
        System.out.println("Elenco campi disponibili: ");
        for (Campi_da_gioco t : campi) {
            System.out.println(t);
        }
        System.out.println("Elenco orari disponibili: ");
        for (String t : orari) {
            System.out.println(t);
        }

        Scanner scanner = new Scanner(System.in);
        int n;
        while(true){
            System.out.println("Benvenuto al campo sportivo CUS SASSARI! Selezionare un numero corrispondente alla propria richiesta.");
            System.out.println("1-Effettua una prenotazione");
            System.out.println("2-Cancella una prenotazione");
            System.out.println("3-Verifica la disponibilita' di un campo");
            System.out.println("4-Diventa un tesserato");
            System.out.println("5-Modifica una prenotazione");
            System.out.println("6-Visualizza la tua prenotazione");


            n=scanner.nextInt();
            switch(n){
                case 1:
                    System.out.println("In quale giorno si vorrebbe prenotare il campo?(inserire una data nel formato dd/MM/yyyy)");
                    String data = scanner.next();
                    LocalDate userDate = LocalDate.parse(data,formatter);
                    System.out.println("Data inserita: "+userDate+". Inserire ora l'orario desiderato(formato HH:mm): ");
                    String orario = scanner.next();
                    LocalTime userTime = LocalTime.parse(orario,formatter2);
                    LocalDateTime userDateTime = LocalDateTime.of(userDate,userTime);
                    System.out.println("Data e ora inserite: "+userDateTime);
                    System.out.println("Inserire il codice del campo:  ");
                    String codice = scanner.next();
                    Campi_da_gioco campo = getCampi(codice, campi);
                    if(campo==null){
                        System.out.println("Codice campo inesistente.");
                        break;
                    }
                    System.out.println("Inserire il numero di tesserati(il prezzo è di 10$ a persona): ");
                    int tesseratiNumero = scanner.nextInt();
                    double prezzo = 10;
                    List<String> tesseratiLista = new ArrayList<>();
                    for (int i = 0; i < tesseratiNumero; i++) {
                        System.out.println("Inserire il codice del tesserato: ");
                        String codiceTesserato = scanner.next();
                        Tesserati tesserato = getTesserati(codiceTesserato, tesserati);
                        if(tesserato==null){
                            System.out.println("Codice tesserato inesistente.");
                        }
                        else tesseratiLista.add(codiceTesserato);
                    }
                    prezzo = prezzo*tesseratiNumero;
                    Prenotazioni prenotazione = new Prenotazioni(java.sql.Timestamp.valueOf(userDateTime),userTime.getHour(),prezzo,campo,tesseratiLista);
                    prenotazioni.add(prenotazione);
                    System.out.println("Prenotazione effettuata con successo! Di seguito avrete la stampa della prenotazione," +
                            " se per qualsiasi ragione volete cancellare o modificare la prenotazione avvisateci per tempo.\n"+prenotazione);
                    salvaPrenotazioni(prenotazioni,filePrenotazioni);
                    break;

                case 2:
                    System.out.println("Inserire l'ID della prenotazione da cancellare: ");
                    int id = scanner.nextInt();
                    Prenotazioni daRimuovere = null;
                    for (Prenotazioni p : prenotazioni) {
                        if(p.getId()==id){
                            daRimuovere = p;
                            break;
                        }
                    }
                    if(daRimuovere==null){
                        System.out.println("Prenotazione inesistente.");
                    }
                    else{
                        prenotazioni.remove(daRimuovere);
                        salvaPrenotazioni(prenotazioni,filePrenotazioni);
                        System.out.println("Prenotazione cancellata.");
                    }
                    break;













                default:
                    System.out.println("Termina il programma.");
                    RipristinaPrenotazioni(filePrenotazioni);
                    exit(-1);
                    break;



            }
        }
        }
    }