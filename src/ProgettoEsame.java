import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.sql.Date;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static java.lang.System.exit;

public class ProgettoEsame {
    //caricamento delle informazioni da file
    static String nomeFileTesserati = "C:\\Users\\lekse\\IdeaProjects\\ProgettoPerEsame\\src\\Tesserati";
    static String campiDisponibili = "C:\\Users\\lekse\\IdeaProjects\\ProgettoPerEsame\\Campi_da_gioco";
    static String orariDisponibili = "C:\\Users\\lekse\\IdeaProjects\\ProgettoPerEsame\\Orari_Prenotazioni";
    static String filePrenotazioni = "C:\\Users\\lekse\\IdeaProjects\\ProgettoPerEsame\\src\\Prenotazioni";
    static ArrayList<Prenotazioni> prenotazioni;
    static Scanner scanner = new Scanner(System.in);
    //i formatter servono a rendere più leggibile data e ora
    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    static DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("HH:mm");



//funzione per salvare le prenotazioni su file
    public static void salvaPrenotazioni(ArrayList<Prenotazioni> prenotazioni,String filename) throws IOException {
        FileOutputStream fos = new FileOutputStream(filename);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(prenotazioni);
        oos.close();
        fos.close();
    }

//funzione per ripristinare il file delle prenotazioni(viene utilizzato ogni volta che si 'runna' il programma)
    public static void RipristinaPrenotazioni(String filename) throws IOException {
        new FileOutputStream(filename).close();
        System.out.println("File delle prenotazioni ripristinato.");
    }

//funzione per caricare i tesserati
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
//funzione per caricare i campi da gioco
    public static ArrayList<Campi_da_gioco> elencoCampi(String filename) throws IOException {
        ArrayList<Campi_da_gioco> campi = new ArrayList<>();
        FileReader fr = new FileReader(filename);
        BufferedReader br = new BufferedReader(fr);
        String line;
        while ((line=br.readLine())!=null){
            String[] f = line.split(" ");
            campi.add(new Campi_da_gioco(f[0],f[1],Integer.parseInt(f[2])));
        }
        br.close();
        fr.close();
        return campi;
    }
//funzione per caricare gli orari in cui è possibile prenotarsi
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
//funzione per poter chiamare il tesserato grazie al proprio codice
    public static Tesserati getTesserati(String codice, ArrayList<Tesserati> tesserati) {
        for (Tesserati t : tesserati) {
            if(t.getCodiceTesserato().equals(codice)) return t;
        }
        return null;
    }
//funzione per poter chiamare il campo grazie al proprio codice
    public static Campi_da_gioco getCampi(String codice, ArrayList<Campi_da_gioco> campi) {
        for (Campi_da_gioco t : campi) {
            if(t.getCodiceCampo().equals(codice)) return t;
        }
        return null;
    }
//funzione per generare il codice di un tesserato in modo che esso sia composto dalle sue iniziali di nome e cognome e 3 numeri
    public static String generaCodiceTesserato(String nome,String cognome,ArrayList<Tesserati> tesserati) {
        //recupera la prima lettera di nome e cognome
        String iniziali = (nome.charAt(0) + cognome.substring(0,1)).toUpperCase();
        int nuovo = 1;
        if(!tesserati.isEmpty()){
            //recupero il codice dell'ultimo tesserato
            String last = tesserati.getLast().getCodiceTesserato();
            //num sono le ultime 3 cifre, quindi parte dalla terza
            int num = Integer.parseInt(last.substring(2));
            //il codice dev'essere quello dell'ultimo tesserato + 1
            nuovo = num + 1;
        }
        //il formato da restituire prevede la stringa e 3 numeri
        return String.format(iniziali+"0"+nuovo);
    }
//funzione per poter stampare i vari elenchi, il ? è utilizzato per non dover specificare il tipo della lista
    public static void stampaListe(String elenco,List<?>lista){
        System.out.println(elenco);
        for (Object o : lista) {
            System.out.println(o);
        }
    }
//funzione utilizzata ogni volta che viene inserita una stringa per controllare sia scritta correttamente
    public static String getStringInput(String input){
        while(true) {
            try{
            System.out.println(input);
            return scanner.next();
        }catch(InputMismatchException e){
            System.out.println("Input errato. Riprova.");
            scanner.next();
        }}
    }
//come getStringInput ma per gli interi
    public static int getIntInput(String input){
        while(true){
            try{
                System.out.println(input);
                return scanner.nextInt();
            }catch(InputMismatchException e){
                System.out.println("Input errato. Riprova.");
                scanner.next();
            }
        }
    }
//per le date
    public static LocalDate getDateInput(String input){
        while(true){
            try{
                System.out.println(input);
                String data = scanner.next();
                //restituisce la data formattata
                return LocalDate.parse(data,formatter);
            }catch(Exception e){
                System.out.println("Data non valida. Riprova.");
            }
        }
    }
//per gli orari
    public static LocalTime getTimeInput(String input){
        while(true){
            try{
                System.out.println(input);
                String ora = scanner.next();
                return LocalTime.parse(ora,formatter2);
            }catch(Exception e) {
                System.out.println("Orario non valido. Riprova.");
            }
        }
    }
//funzione per effettuare le prenotazioni(caso 1)
    public static void effettuaPrenotazione(ArrayList<Tesserati> tesserati, ArrayList<Campi_da_gioco> campi) throws IOException {
        LocalDate userDate = getDateInput("In quale giorno si vorrebbe prenotare il campo? (formato dd/MM/yyyy)");
        LocalDate oggi = LocalDate.now();
        //controllo per evitare venga inserita una data precedente a quella odierna
        if (userDate.isBefore(oggi)) {
            System.out.println("Errore: non e' possibile prenotare indietro nel tempo.");
            getDateInput("In quale giorno si vorrebbe prenotare il campo? (formato dd/MM/yyyy)");
        }
        LocalTime userTime = getTimeInput("Inserire ora l'orario desiderato (formato HH:mm): ");
        //controllo per evitare che venga inserito un orario in cui il campo è chiuso
        if (userTime.isBefore(LocalTime.of(8, 0)) || userTime.isAfter(LocalTime.of(22, 0))) {
            getTimeInput("Orario non valido. Si prega di scegliere un orario compreso tra le 8:00 e le 21:00.");
        }
        LocalDateTime userDateTime = LocalDateTime.of(userDate, userTime);
        System.out.println("Data e ora inserite: " + userDateTime);
        String codice = getStringInput("Inserire il codice del campo: ");
        Campi_da_gioco campo = getCampi(codice, campi);
        if (campo == null) {
            System.out.println("Codice campo inesistente.");
            getStringInput("Inserire il codice del campo: ");
        }
        else {
            int tesseratiNumero = getIntInput("Inserire il numero di tesserati (il prezzo è di 10$ a persona): ");
            if (tesseratiNumero > campo.getMaxGiocatori()) {
                System.out.println("Troppi giocatori per il campo inserito. Inserire un numero adeguato di giocatori.");
                getIntInput("Inserire il numero di tesserati (il prezzo è di 10$ a persona): ");
            }
            List<String> tesseratiLista = new ArrayList<>();
            for (int i = 0; i < tesseratiNumero; i++) {
                String codiceTesserato = getStringInput("Inserire il codice del tesserato: ");
                Tesserati tesserato = getTesserati(codiceTesserato, tesserati);
                if (tesserato == null) {
                    System.out.println("Codice tesserato inesistente.");
                    codiceTesserato = getStringInput("Inserisci un codice valido: ");
                }
                tesseratiLista.add(codiceTesserato);
            }
        //controllo per evitare prenotazioni in contemporanea da parte dello stesso utente
        boolean conflitto = false;
        for (Prenotazioni p : prenotazioni) {
            //serie di riferimenti per poter convertire il tipo Date a LocalDateTime
            LocalDateTime pren = p.getData().atStartOfDay();
            if (pren.equals(userDateTime)) {
                for (String codiceTesserato : tesseratiLista) {
                    if (p.getTesserati().contains(codiceTesserato)) {
                        System.out.println("Errore: il tesserato con codice " + codiceTesserato + " ha gia' una prenotazione nello stesso orario.");
                        System.out.println("Prenotazione annullata.");
                        conflitto = true;
                        break;
                    }
                }
            }
        }
        if (conflitto) {
            return;
        }
        if (campo.getPrenotazioni(userDate, userTime) + tesseratiNumero <= campo.getMaxGiocatori()) {
            campo.aggiungiPrenotazione(userDate, userTime, tesseratiNumero);
            double prezzo = 10 * tesseratiNumero;
            //il primo parametro è un tipo LocalDateTime convertito a un tipo Date
            Prenotazioni prenotazione = new Prenotazioni(userDate, userTime.format(formatter2), prezzo, campo, tesseratiLista);
            prenotazioni.add(prenotazione);
            System.out.println("Prenotazione effettuata con successo! Di seguito avrete la stampa della prenotazione, " +
                    "se per qualsiasi ragione volete cancellare o modificare la prenotazione avvisateci per tempo.\n" + prenotazione);
            salvaPrenotazioni(prenotazioni, filePrenotazioni);
        } else {
            System.out.println("Ci dispiace ma il campo selezionato in data: " + userDate + " alle: " + userTime + " risulta occupato, scegliere un'altra/o ora/giorno/campo.");
        }
    }}
//funzione per cancellare le prenotazioni(caso 2)
    public static void cancellaPrenotazione() throws IOException {
        int id = getIntInput("Inserire l'ID della prenotazione da cancellare: ");
        Prenotazioni daRimuovere = null;
        for (Prenotazioni p : prenotazioni) {
            if (p.getId() == id) {
                daRimuovere = p;
                break;
            }
        }
        if (daRimuovere != null) {
            prenotazioni.remove(daRimuovere);
            System.out.println("Prenotazione cancellata con successo.");
            salvaPrenotazioni(prenotazioni, filePrenotazioni);
        } else {
            System.out.println("Prenotazione con ID " + id + " non trovata.");
        }
    }
//funzione per la verifica della disponibilità di un campo(caso 3)
    public static void verificaDisponibilitaCampo(ArrayList<Campi_da_gioco> campi){
        String codiceQual = getStringInput("Inserire il codice del campo del quale vuole sapere la disponibilita':  ");
        Campi_da_gioco info = null;
        for (Campi_da_gioco p: campi){
            if(Objects.equals(codiceQual, p.getCodiceCampo())){
                info=p;
                break;
            }
        }
        if(info==null) {
            System.out.println("Codice campo inesistente.");
        }
        else{
            LocalDate dataQual = getDateInput("Quale data le interessa?");
            System.out.println("In data: " + dataQual + " la disponibilita' e' la seguente: ");
            //il Set di orari è utilizzato per evitare doppioni, se ci sono 2 prenotazioni per la stessa ora non stamperà due volte quell'ora
            Set<LocalTime> orariPrenotati = info.getPrenotazioni(dataQual);
            if (orariPrenotati.isEmpty()) {
                System.out.println("Il campo e' disponibile per tutti gli orari.");
            } else {
                for (LocalTime o : orariPrenotati) {
                    int numeroGiocatori = info.getPrenotazioni(dataQual, o);
                    System.out.println("Orario: " + o.format(formatter2) + " Prenotato (" + numeroGiocatori + "/" + info.getMaxGiocatori() + " persona/e presente/i)");
                }
                System.out.println("Tutti gli altri orari sono disponibili.");
            }
        }
    }
//funzione per diventare tesserati(caso 4)
    public static void diventaTesserato(ArrayList<Tesserati> tesserati){
        String nome = getStringInput("Inserirci il tuo nome: ");
        //metodo per mettere in maiuscolo le prime lettere di nome e cognome
        nome = nome.substring(0,1).toUpperCase() + nome.substring(1);
        String cognome = getStringInput("Inserisci il tuo cognome: ");
        cognome = cognome.substring(0,1).toUpperCase() + cognome.substring(1);
        System.out.println("Congratulazioni, sei diventato un tesserato! Il tuo codice da tesserato e': ");
        String codiceTesserato = generaCodiceTesserato(nome,cognome,tesserati);
        System.out.println(codiceTesserato);
        tesserati.add(new Tesserati(nome,cognome,codiceTesserato));
        System.out.println("Elenco tesserati aggiornato: ");
        for (Tesserati p : tesserati){
            System.out.println(p);
        }
    }
//funzione per visualizzare una prenotazione(caso 5)
    public static void visualizzaPrenotazione() {
        int id = getIntInput("Inserire l'ID della prenotazione da visualizzare: ");
        for (Prenotazioni p : prenotazioni) {
            if (p.getId() == id) {
                System.out.println(p);
                return;
            }
        }
        System.out.println("Prenotazione con ID " + id + " non trovata.");
    }
// funzione per modificare una prenotazione
    public static void modificaPrenotazioni(ArrayList<Prenotazioni> prenotazioni,ArrayList<Campi_da_gioco> campi,ArrayList<Tesserati> tesserati, String filePrenotazioni) throws IOException {
        int ID = getIntInput("Inserire l'ID della prenotazione da visualizzare: ");
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
                        LocalDate userDate = getDateInput("Inserisci la nuova data(dd/MM/yyyy): ");
                        LocalDate Oggi = LocalDate.now();
                        if(userDate.isBefore(Oggi)){
                            System.out.println("Errore: non e' possibile prenotare indietro nel tempo.");
                            getDateInput("Inserisci la nuova data(dd/MM/yyyy): ");
                        }
                        //senza java.sql non posso utilizzare valueOf, specifico di quella classe
                        modifica.setData(Date.valueOf(userDate).toLocalDate());
                        break;
                    case 2:
                        LocalTime userTime = getTimeInput("Inserisci il nuovo orario(HH:mm): ");
                        LocalTime startTime = LocalTime.of(8, 0);
                        LocalTime endTime = LocalTime.of(22, 0);
                        if (userTime.isBefore(startTime) || userTime.isAfter(endTime)) {
                            System.out.println("Tutti i campi sono chiusi alle ore: " + userTime);
                            break;
                        }
                        modifica.setInizio(userTime.format(formatter2));
                        break;
                    case 3:
                        String codice = getStringInput("Inserisci il nuovo codice del campo: ");
                        Campi_da_gioco campo = getCampi(codice, campi);
                        if (campo != null) {
                            modifica.setCampo(campo);
                        } else System.out.println("Il codice inserito è inesistente.");
                        break;
                    case 4:
                        int tesseratiNumero = getIntInput("Inserire il nuovo numero di tesserati: ");
                        Campi_da_gioco campoCorrente = modifica.getCampo();
                        LocalDate localDate = modifica.getData();
                        int numTesseratiCorrente = campoCorrente.getPrenotazioni(localDate,LocalTime.parse(modifica.getInizio()));
                        //dopo aver recuperato la quantità di persone già presenti nel campo, verifico che con la modifica venga comunque
                        //rispettata la capienza massima
                        if(tesseratiNumero + numTesseratiCorrente > campoCorrente.getMaxGiocatori() || tesseratiNumero<0){
                            System.out.println("Errore: numero di tesserati troppo elevato per il campetto scelto.");
                        }
                        else{
                            modificaTesserati(modifica,tesserati,tesseratiNumero);
                            if(verificaConflitti(prenotazioni,modifica,modifica.getTesserati())){
                                modificaTesserati(modifica,tesserati,modifica.getTesserati().size());
                            }
                        }
                        break;
                    default:
                        System.out.println("Scelta non valida.");
                        break;
                }
                String risposta = getStringInput("Vuoi modificare qualcos'altro?(si/no)");
                if (!risposta.equals("si")) continua = false;
            }
            System.out.println("Prenotazione modificata con successo.");
            System.out.println(modifica);
            salvaPrenotazioni(prenotazioni,filePrenotazioni);
        }else System.out.println("Prenotazione inesistente.");
    }
//funzione per la modifica dei tesserati
    private static void modificaTesserati(Prenotazioni modifica, ArrayList<Tesserati> tesserati, int tesseratiNumero){
        List<String> nuovaListaTesserati = new ArrayList<>();
        for (int i = 0; i < tesseratiNumero; i++) {
            String codiceTesserato = getStringInput("Inserire il codice del tesserato: ");
            Tesserati tesserato = getTesserati(codiceTesserato, tesserati);
            if (tesserato == null) {
                System.out.println("Codice tesserato inesistente.");
                getStringInput("Inserire il codice del tesserato: ");
            } else {
                nuovaListaTesserati.add(codiceTesserato);
            }
        }
        modifica.setTesserati(nuovaListaTesserati);
        modifica.setPrezzo(tesseratiNumero * 10);
    }
//funzione per evitare che un tesserato possa prenotarsi contemporaneamente in due campi diversi o nello stesso
    private static boolean verificaConflitti(ArrayList<Prenotazioni> prenotazioni,Prenotazioni modifica,List<String> nuovaListaTesserati){
        LocalDateTime userDataOra = LocalDateTime.of(modifica.getData(), LocalTime.parse(modifica.getInizio(),formatter2));
        for (Prenotazioni p : prenotazioni) {
            LocalDateTime pren = p.getData().atStartOfDay();
            if (pren.equals(userDataOra) && p.getId() != modifica.getId()) {
                for (String codTesserato : nuovaListaTesserati) {
                    if (p.getTesserati().contains(codTesserato)) {
                        System.out.println("Errore: il tesserato con codice " + codTesserato + " ha già una prenotazione nello stesso orario.");
                        return true;
                    }
                }
            }
        }
        return false;
    }
//funzione per eliminare un tesserato(caso 7)
    //con esso vengono cancellate anche le sue prenotazioni
    public static void annullaAbbonamento(ArrayList<Tesserati> tesserati) throws IOException {
        String codice = getStringInput("Inserire il codice del tesserato da annullare: ");
        Tesserati tesserato = getTesserati(codice, tesserati);
        if (tesserato != null) {
            tesserati.remove(tesserato);
            System.out.println("Abbonamento a tesserato annullato con successo.");
            ArrayList<Prenotazioni> invalide = new ArrayList<>();
            for (Prenotazioni p : prenotazioni) {
                if(p.getTesserati().contains(codice)){
                    invalide.add(p);
                }
            }
            prenotazioni.removeAll(invalide);
            System.out.println("Prenotazioni associate al tesserato rimosse.");
            salvaPrenotazioni(prenotazioni,filePrenotazioni);
        } else {
            System.out.println("Codice tesserato inesistente.");
        }
    }
//funzione per verificare la disponibilità dei campi in base a un orario
    public static void verificaDisponibilitaOrario(List<Campi_da_gioco> campi){
        LocalDate userDate = getDateInput("Quale data le interessa?");
        LocalDate today = LocalDate.now();
        if(userDate.isBefore(today)){
            System.out.println("Errore: non e' possibile prenotare indietro nel tempo.");
            getDateInput("Quale data le interessa?");
        }
        LocalTime userTime = getTimeInput("Inserire ora l'orario desiderato (formato HH:mm): ");
        LocalTime startTime = LocalTime.of(8, 0);
        LocalTime endTime = LocalTime.of(22, 0);
        if (userTime.isBefore(startTime) || userTime.isAfter(endTime)) {
            System.out.println("Tutti i campi sono chiusi in data: " + userDate + " alle ore: " + userTime);
            return;
        }
        System.out.println("Verifica della disponibilita' per il centro sportivo in data: "+userDate+" alle ore: "+userTime);
        boolean disponibilita = true;
        //controllo i campi e recupero le prenotazioni con il Set per non avere duplicati
        for(Campi_da_gioco camp : campi){
            Set<LocalTime> prenotazioni = camp.getPrenotazioni(userDate);
            //se la prenotazione contiene l'orario richiesto vuol dire che qualcuno lo ha gia' prenotato
            if(prenotazioni.contains(userTime)){
                disponibilita = false;
                //recupero il numero di giocatori del campo e confronto quel numero con la sua capienza massima
                int numGiocatori = camp.getPrenotazioni(userDate,userTime);
                if(numGiocatori==camp.getMaxGiocatori()){
                    System.out.println("Il campo "+camp.getTipologiaCampo()+ " (codice: "+camp.getCodiceCampo()+") risulta pieno.");
                }
                else System.out.println("Il campo "+camp.getTipologiaCampo()+" (codice: "+camp.getCodiceCampo()+ ") e' stato prenotato ma ci sono ancora posti disponibili: "+numGiocatori+"/"+camp.getMaxGiocatori()+".");
            }else System.out.println("Il campo "+camp.getTipologiaCampo()+" (codice: "+camp.getCodiceCampo()+ ") e' disponibile a quest'ora.");
        }
        if(disponibilita){
            System.out.println("Tutti i campi sono disponibili in data: "+userDate+" alle ore: "+userTime);
        }
    }

    public static void main(String[] args) throws IOException {
        ArrayList<Tesserati> tesserati = elencoTesserati(nomeFileTesserati);
        ArrayList<Campi_da_gioco> campi = elencoCampi(campiDisponibili);
        ArrayList<String> orari = elencoOrari(orariDisponibili);
        //ogni volta che il programma viene "runnato", il file delle prenotazioni viene resettato
        RipristinaPrenotazioni(filePrenotazioni);
        prenotazioni = new ArrayList<>();
        stampaListe("Elenco tesserati: ",tesserati);
        stampaListe("Elenco campi disponibili: ",campi);
        stampaListe("Elenco orari disponibili: ",orari);
        int n;
        while(true){
        //l'idea è gestire le scelte come se fosse un utente che accede al sito del campo sportivo
            System.out.println("Benvenuto al campo sportivo CUS SASSARI! Selezionare un numero corrispondente alla propria richiesta.");
            System.out.println("1-Effettua una prenotazione");
            System.out.println("2-Cancella una prenotazione");
            System.out.println("3-Verifica la disponibilita' di un campo");
            System.out.println("4-Diventa un tesserato");
            System.out.println("5-Visualizza una prenotazione");
            System.out.println("6-Modifica la tua prenotazione");
            System.out.println("7-Annulla l'abbonamento a tesserato");
            System.out.println("8-Verifica la disponibilita' del centro sportivo a un determinato orario.");
            System.out.println("0-Inserire qualsiasi altro numero per uscire.");

            n = getIntInput("");
            switch(n){
                case 1:
                    effettuaPrenotazione(tesserati,campi);
                    break;
                case 2:
                    cancellaPrenotazione();
                    break;
                case 3:
                    verificaDisponibilitaCampo(campi);
                    break;
                case 4:
                    diventaTesserato(tesserati);
                    break;
                case 5:
                    visualizzaPrenotazione();
                    break;
                case 6:
                    modificaPrenotazioni(prenotazioni,campi,tesserati,filePrenotazioni);
                    break;
                case 7:
                    annullaAbbonamento(tesserati);
                    break;
                case 8:
                    verificaDisponibilitaOrario(campi);
                    break;
                default:
                    System.out.println("Termina il programma.");
                    exit(-1);
                    break;

            }
        }
        }
    }