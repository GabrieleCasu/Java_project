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
        new FileOutputStream(filename).close();
        System.out.println("File delle prenotazioni ripristinato.");
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

    public static String generaCodiceTesserato(String nome,String cognome,ArrayList<Tesserati> tesserati) {
        String iniziali = (nome.substring(0, 1) + cognome.substring(0,1)).toUpperCase();
        int nuovo = 1;
        if(!tesserati.isEmpty()){
            String last = tesserati.get(tesserati.size()-1).getCodiceTesserato();
            int num = Integer.parseInt(last.substring(2));
            nuovo = num + 1;
        }
        return String.format("%s%03d",iniziali,nuovo);
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

        RipristinaPrenotazioni(filePrenotazioni);
        prenotazioni = new ArrayList<>();
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
            System.out.println("5-Visualizza una prenotazione");
            System.out.println("6-Modifica la tua prenotazione");


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
                        //salvaPrenotazioni(prenotazioni,filePrenotazioni);
                        System.out.println("Prenotazione cancellata.");
                    }
                    break;
                case 3:


                case 4:
                    System.out.println("Inserisci il tuo nome: ");
                    String nome = scanner.next();
                    System.out.println("Inserisci il tuo cognome: ");
                    String cognome = scanner.next();
                    System.out.println("Congratulazione sei diventato un tesserato! Il tuo codice da tesserato e': ");
                    String codiceTesserato = generaCodiceTesserato(nome,cognome,tesserati);
                    System.out.println(codiceTesserato);
                    tesserati.add(new Tesserati(nome,cognome,codiceTesserato));
                    System.out.println("Elenco tesserati: ");
                    for (Tesserati t : tesserati) {
                        System.out.println(t);
                    }
                    break;

                case 5:
                    System.out.println("Inserisci l'ID della tua prenotazione: ");
                    int cod = scanner.nextInt();
                    boolean esiste = false;
                    for (Prenotazioni p : prenotazioni) {
                        if(p.getId()==cod){
                            esiste = true;
                            System.out.println(p);
                        }
                    }
                    if(!esiste){
                        System.out.println("Prenotazione inesistente.");
                    }
                    break;

                case 6:
                    System.out.println("Inserire l'ID della propria prenotazione per poterla visualizzare: ");
                    int ID = scanner.nextInt();
                    Prenotazioni modifica = null;
                    for (Prenotazioni p : prenotazioni) {
                        if(p.getId()==ID){
                            modifica = p;
                            System.out.println(p);
                            break;
                        }
                    }
                    if(modifica!=null) {
                        boolean continua = true;
                        while (continua) {
                            System.out.println("Quale parte della prenotazione vuoi modificare?");
                            System.out.println("1. Data");
                            System.out.println("2. Orario");
                            System.out.println("3. Campo");
                            System.out.println("4. Numero di tesserati");
                            int scelta = scanner.nextInt();
                            switch (scelta) {
                                case 1:
                                    System.out.println("Inserisci la nuova data(dd/MM/yyyy): ");
                                    data = scanner.next();
                                    userDate = LocalDate.parse(data, formatter);
                                    java.sql.Date date = java.sql.Date.valueOf(userDate);
                                    modifica.setData(date);
                                    break;
                                case 2:
                                    System.out.println("Inserisci il nuovo orario(HH:mm): ");
                                    orario = scanner.next();
                                    userTime = LocalTime.parse(orario, formatter2);
                                    modifica.setInizio(userTime.getHour());
                                    break;
                                case 3:
                                    System.out.println("Inserisci il nuovo codice del campo: ");
                                    codice = scanner.next();
                                    campo = getCampi(codice, campi);
                                    if (campo != null) {
                                        modifica.setCampo(campo);
                                    } else System.out.println("Il codice inserito è inesistente.");
                                    break;
                                case 4:
                                    System.out.println("Inserire il nuovo numero di tesserati: ");
                                    tesseratiNumero = scanner.nextInt();
                                    List<String> nuovaListaTesserati = new ArrayList<>();
                                    for (int i = 0; i < tesseratiNumero; i++) {
                                        System.out.println("Inserire il codice del tesserato: ");
                                        codiceTesserato = scanner.next();
                                        Tesserati tesserato = getTesserati(codiceTesserato, tesserati);
                                        if (tesserato == null) {
                                            System.out.println("Codice tesserato inesistente.");
                                        } else {
                                            nuovaListaTesserati.add(codiceTesserato);
                                        }
                                    }
                                    modifica.setTesserati(nuovaListaTesserati);
                                    modifica.setPrezzo(tesseratiNumero * 10);
                                    break;
                                default:
                                    System.out.println("Scelta non valida.");
                                    break;
                            }
                            System.out.println("Vuoi modificare qualcos'altro?(si/no)");
                            String risposta = scanner.next();
                            if (!risposta.equals("si")) continua = false;
                        }
                        System.out.println("Prenotazione modificata con successo.");
                    }else System.out.println("Prenotazione inesistente.");
                    break;
















                default:
                    System.out.println("Termina il programma.");
                    exit(-1);
                    break;



            }
        }
        }
    }