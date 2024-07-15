package eshop.server.serverClientVerbindung;

import eshop.common.enitities.*;
import eshop.common.exceptions.*;
import eshop.server.domain.E_Shop;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClientRequestProcessor extends Thread {

    private Socket clientSocket;
    private E_Shop eShop;

    private PrintStream out;
    private BufferedReader in;

    public ClientRequestProcessor(Socket clientSocket, E_Shop eShop){
        this.clientSocket = clientSocket;
        this.eShop = eShop;
        try {
            // erstellt Input/Output Streams um Daten vom Client zu lesen/schreiben
            out = new PrintStream(clientSocket.getOutputStream());
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (IOException e) {
            System.err.println("Error beim erstellen von In und Out-Streams");
        }
    }

    /**
     * Die run() Methode sorgt dafür, dass einzelne oder die selbe Methode Paralel laufen können
     * @Param input ist wür den Empfang zuständig
     * */
    @Override
    public void run() {
        String input = "";
        do {
            try {
                input = in.readLine();
                System.out.println(input);
            } catch (IOException e) {
                System.err.println("Error beim lesen vom Client " + e);
                break;
            }
            //TODO: SIMONS KACK AUSGABEN Prüfen
            switch (input) {
                case "gibAlleArtikel":
                    gibAlleArtikel();
                    break;
                case "gibAlleMassengutartikel":
                    gibAlleMassengutartikel();
                    break;
                case "gibAlleMitarbeiter":
                    gibAlleMitarbeiter();
                    break;
                case "addArtikel":
                    addArtikel();
                    break;
                case "addMassengutartikel":
                    addMassengutartikel();
                    break;
                case "aendereArtikelBestand":
                    aendereArtikelBestand();
                    break;
                case "addKunde":
                    addKunde();
                    break;
                case "addMitarbeiter":
                    addMitarbeiter();
                    break;
                case "loginMitarbeiter":
                    loginMitarbeiter();
                    break;
                case "loginKunde":
                    loginKunde();
                    break;
                case "loescheArtikel":
                    loescheArtikel();
                    break;
                case "getEreignisListe":
                    getEreignisListe();
                    break;
                case "sucheArtikelMitNummer":
                    sucheArtikelMitNummer();
                    break;
                case "artikelInWarenkorbHinzufügen":
                    artikelInWarenkorbHinzufügen();
                    break;
                case "gibWarenkorbArtikel":
                    gibWarenkorbArtikel();
                    break;
                case "gesamtPreis":
                    gesamtPreis();
                    break;
                case "warenkorbLeeren":
                    warenkorbLeeren();
                    break;
                case "warenkorbKaufen":
                    warenkorbKaufen();
                    break;
                case "bestandImWarenkorbAendern":
                    bestandImWarenkorbAendern();
                    break;
                default:
            }

        } while (!input.equals("exit"));
    }
    /**
     * Funktion um alle Artikel aus der Map auszulesen und diese in Strings umzuwandeln
     * @Param Integer ist der Key-Wert in der Map und steht für die eindeutige Artikelnummer
     * @Param Artikel Holt sich das Artikel-Objekt aus der Map zum zugehörigen Key
     * */
    private void gibAlleArtikel(){
        Map<Integer, Artikel> alleArtikel = eShop.gibAlleArtikel();
        Map<Integer, Artikel> onlyAlleArtikel = new HashMap<Integer, Artikel>();
        for(Map.Entry<Integer, Artikel> entry : alleArtikel.entrySet()) {
            if(entry.getValue() instanceof Artikel) {
                onlyAlleArtikel.put(entry.getKey(), (Artikel) entry.getValue());
            }
        }
        out.println(onlyAlleArtikel.size());
        for(Map.Entry<Integer, Artikel> entry : onlyAlleArtikel.entrySet()){
            out.println(entry.getValue().getArtikelnummer());
            out.println(entry.getValue().getArtikelbezeichnung());
            out.println(entry.getValue().getArtikelbestand());
            out.println(entry.getValue().getArtikelPreis());
        }
        //out.println("Erfolgreich: gibAlleArtikel()");
    }

    /**
     * Funktion um alle Massengutartikel aus der Map auszulesen und diese in Strings umzuwandeln
     * @Param Integer ist der Key-Wert in der Map und steht für die eindeutige Artikelnummer
     * @Param Artikel holt sich das Artikel-Objekt aus der Map zum zugehörigen Key
     * */
    private void gibAlleMassengutartikel(){
        Map<Integer, Artikel> alleMassengutartikel = eShop.gibAlleArtikel();
        Map<Integer, MassengutArtikel> onlyAlleMassengutArtikel = new HashMap<Integer, MassengutArtikel>();
        for(Map.Entry<Integer, Artikel> entry : alleMassengutartikel.entrySet()) {
            if(entry.getValue() instanceof MassengutArtikel) {
                onlyAlleMassengutArtikel.put(entry.getKey(), (MassengutArtikel) entry.getValue());
            }
        }
        out.println(onlyAlleMassengutArtikel.size());
        for(Map.Entry<Integer, MassengutArtikel> entry : onlyAlleMassengutArtikel.entrySet()){
            out.println(entry.getValue().getArtikelnummer());
            out.println(entry.getValue().getArtikelbezeichnung());
            out.println(entry.getValue().getArtikelbestand());
            out.println(entry.getValue().getArtikelPreis());
            out.println(((MassengutArtikel) entry.getValue()).getAnzahlMassengut());
        }
        //out.println("Erfolgreich: gibAlleMassengutartikel()");
    }

    /**
     * Funktion um die Werte für den Mitarbeiter zu bekommen, damit diese aus der Map ausgelesen werden könne
     * @Param Integer Key Wert der Map ist die Mitarbeiter ID
     * @Param Mitarbeiter ist der Value, dass ausgegeben wird. (Mitarbeiter-Objekt)
     *
     * @Param out.println(entry.getValue().getId()); holt sich die ID aus der Map
     * @Param out.println(entry.getValue().getVorname()); holt sich den Vornamen aus der Map
     * @Param out.println(entry.getValue().getNachname()); holt sich den Nachnamen aus der Map
     * @Param out.println(entry.getValue().getEmail()); holt sich die E-mail aus der Map
     * @Param out.println(entry.getValue().getUsername()); holt sich den Username aus der Map
     * @Param out.println(entry.getValue().getPassword()); holt sich das Passwort aus der Map
     * */
    private void gibAlleMitarbeiter(){
        Map<Integer, Mitarbeiter> alleMitarbeiter = eShop.gibAlleMitarbeiter();
        for (Map.Entry<Integer, Mitarbeiter> entry : alleMitarbeiter.entrySet()) {
            out.println(entry.getValue().getId());
            out.println(entry.getValue().getVorname());
            out.println(entry.getValue().getNachname());
            out.println(entry.getValue().getEmail());
            out.println(entry.getValue().getUsername());
            out.println(entry.getValue().getPassword());
        }
        out.println("Erfolgreich: gibAlleMitarbeiter()");
    }

    /**
     * Funktion um einen neuen Artikel von der EshopClientsite zu empfangen und dem eshop zu übergeben
     * @Param mitarbeiterID empfängt die mitarbeiterID
     * @Param artikelNummer empfängt die artikelNummer
     * @Param artikelbezeichnung empfängt die artikelbezeichnung
     * @Param artikelpreis empfängt den artikelpreis
     * @Param artikelbestand empfängt den artikelbestand
     *
     * @Param IdNichtVorhandenException wird geworfen, wenn die ID nicht vorhanden ist
     * @Param DoppelteIdException wird geworfen, wenn die ID schon vorhanden ist
     * @Param MinusZahlException wird geworfen, wenn eine Negative Zahl gegeben wurde
     * @Param KeinMassengutException wird geworfen, wenn der Artikel kein Massengut ist
     * */
    private void addArtikel(){
        try {
            int mitarbeiterID = Integer.parseInt(in.readLine());
            int artikelNummer = Integer.parseInt(in.readLine());
            String artikelbezeichnung = in.readLine();
            int artikelpreis = Integer.parseInt(in.readLine());
            int artikelbestand = Integer.parseInt(in.readLine());

            Mitarbeiter mitarbeiter = eShop.sucheMirarbeiterMitNummer(mitarbeiterID);
            Artikel artikel = new Artikel(artikelNummer, artikelbezeichnung, artikelpreis, artikelbestand);
            eShop.addArtikel(mitarbeiter, artikel);
            out.println("Erfolgreich: addArtikel()");
        } catch (IOException e) {
            System.err.println("Error beim lesen vom Client bei = addArtikel()" + e);
            out.println("ERROR 101");
        } catch (IdNichtVorhandenException e){
            System.err.println("Error beim lesen vom Client bei = addArtikel()" + e);
            out.println("ERROR 404");
        } catch (DoppelteIdException e){
            System.err.println("Error beim lesen vom Client bei = addArtikel()" + e);
            out.println("ERROR 505");
        } catch (MinusZahlException e){
            System.err.println("Error beim lesen vom Client bei = addArtikel()" + e);
            out.println("ERROR 606");
        } catch (KeinMassengutException e){
            System.err.println("Error beim lesen vom Client bei = addArtikel()" + e);
            out.println("ERROR 707");
        }
    }

    private void addMassengutartikel(){
        try {
            int mitarbeiterID = Integer.parseInt(in.readLine());
            int artikelNummer = Integer.parseInt(in.readLine());
            String artikelbezeichnung = in.readLine();
            int artikelpreis = Integer.parseInt(in.readLine());
            int artikelbestand = Integer.parseInt(in.readLine());
            int artikelMassengut = Integer.parseInt(in.readLine());

            Mitarbeiter mitarbeiter = eShop.sucheMirarbeiterMitNummer(mitarbeiterID);
            Artikel massengutArtikel = new MassengutArtikel(artikelNummer, artikelbezeichnung, artikelpreis, artikelbestand, artikelMassengut);
            eShop.addArtikel(mitarbeiter, massengutArtikel);
            out.println("Erfolgreich: addMassengutartikel()");
        } catch (IOException e) {
            System.err.println("Error beim lesen vom Client bei = addMassengutartikel()" + e);
            out.println("ERROR 101");
        } catch (IdNichtVorhandenException e){
            System.err.println("Error beim lesen vom Client bei = addMassengutartikel()" + e);
            out.println("ERROR 505");
        } catch (DoppelteIdException e){
            System.err.println("Error beim lesen vom Client bei = addMassengutartikel()" + e);
            out.println("ERROR 606");
        } catch (MinusZahlException e){
            System.err.println("Error beim lesen vom Client bei = addMassengutartikel()" + e);
            out.println("ERROR 707");
        } catch (KeinMassengutException e){
            System.err.println("Error beim lesen vom Client bei = addMassengutartikel()" + e);
            out.println("ERROR 808");
        }
    }

    private void aendereArtikelBestand()  {
        try {
            int mitarbeiterID = Integer.parseInt(in.readLine());
            Mitarbeiter mitarbeiter = eShop.sucheMirarbeiterMitNummer(mitarbeiterID);
            int artikelnummer = Integer.parseInt(in.readLine());
            int neuerBestand = Integer.parseInt(in.readLine());

            eShop.aendereArtikelBestand(mitarbeiter, artikelnummer, neuerBestand);
            out.println("Erfolgreich: aendereArtikelBestand()");
        } catch (IOException e){
            System.err.println("Error beim lesen vom Client bei = aendereArtikelBestand()" + e);
            out.println("ERROR 101");
        } catch(IdNichtVorhandenException e) {
            System.err.println("Error beim lesen vom Client bei = aendereArtikelBestand()" + e);
            out.println("ERROR 505");
        } catch(MinusZahlException e) {
            System.err.println("Error beim lesen vom Client bei = aendereArtikelBestand()" + e);
            out.println("ERROR 606");
        } catch(KeinMassengutException e) {
            System.err.println("Error beim lesen vom Client bei = aendereArtikelBestand()" + e);
            out.println("ERROR 707");
        }
        //TODO: Fehler zurük zum client senden wie oben ^

    }

    private void addKunde(){
        try {
            String vorname = in.readLine();
            String nachname = in.readLine();
            String email = in.readLine();
            String username = in.readLine();
            String password = in.readLine();
            String ort = in.readLine();
            int plz = Integer.parseInt(in.readLine());
            String strasse = in.readLine();
            int strassenNummer = Integer.parseInt(in.readLine());

            eShop.addKunde(vorname, nachname, email, username, password, ort, plz, strasse, strassenNummer);
            out.println("Erfolgreich addKunde()");
        }catch (IOException e){
            System.err.println("Error beim lesen vom Client bei = addKunde()" + e);
            out.println("ERROR 101");
        } catch (DoppelteIdException e) {
            System.err.println("Error beim lesen vom Client bei = addKunde()" + e);
            out.println("ERROR 505");
        } catch (UsernameExistiertException e) {
            System.err.println("Error beim lesen vom Client bei = addKunde()" + e);
            out.println("ERROR 606");
        } catch (EmailExistiertException e) {
            System.err.println("Error beim lesen vom Client bei = addKunde()" + e);
            out.println("ERROR 707");
        }
    }

    private void addMitarbeiter(){
        try {
            int mitarbeiterID = Integer.parseInt(in.readLine());
            String vorname = in.readLine();
            String nachname = in.readLine();
            String email = in.readLine();
            String username = in.readLine();
            String password = in.readLine();

            Mitarbeiter mitarbeiter = eShop.sucheMirarbeiterMitNummer(mitarbeiterID);
            eShop.addMitarbeiter(mitarbeiter,vorname, nachname, email, username, password);
            out.println("Erfolgreich addMitarbeiter()");
        }catch (IOException e){
            System.err.println("Error beim lesen vom Client bei = addMitarbeiter()" + e);
            out.println("ERROR 101");
        } catch (DoppelteIdException e) {
            System.err.println("Error beim lesen vom Client bei = addMitarbeiter()" + e);
            out.println("ERROR 505");
        } catch (UsernameExistiertException e) {
            System.err.println("Error beim lesen vom Client bei = addMitarbeiter()" + e);
            out.println("ERROR 606");
        } catch (EmailExistiertException e) {
            System.err.println("Error beim lesen vom Client bei = addMitarbeiter()" + e);
            out.println("ERROR 707");
        } catch (IdNichtVorhandenException e) {
            System.err.println("Error beim lesen vom Client bei = addMitarbeiter()" + e);
            out.println("ERROR 808");
        }
    }

    private void loginMitarbeiter()  {
        try {
            String usernameOrEmail = in.readLine();
            String password = in.readLine();

            Mitarbeiter mitarbeiter = eShop.loginMitarbeiter(usernameOrEmail, password);
            out.println(mitarbeiter.getId());
            out.println(mitarbeiter.getVorname());
            out.println(mitarbeiter.getNachname());
            out.println(mitarbeiter.getEmail());
            out.println(mitarbeiter.getUsername());
            out.println(mitarbeiter.getPassword());
            out.println("Erfolgreich loginMitarbeiter()");
        } catch (IOException e) {
            System.err.println("Error beim lesen vom Client bei = loginMitarbeiter()" + e);
            out.println("ERROR 101");
        } catch (LoginException e) {
            System.err.println("Error beim lesen vom Client bei = loginMitarbeiter()" + e);
            out.println("ERROR 404");
        }
    }

    private void loginKunde(){
        try {
            String usernameOrEmail = in.readLine();
            String password = in.readLine();

            Kunde kunde = eShop.loginKunde(usernameOrEmail, password);
            out.println(kunde.getId());
            out.println(kunde.getVorname());
            out.println(kunde.getNachname());
            out.println(kunde.getEmail());
            out.println(kunde.getUsername());
            out.println(kunde.getPassword());
            out.println(kunde.getOrt());
            out.println(kunde.getPlz());
            out.println(kunde.getStrasse());
            out.println(kunde.getStrassenNummer());
            out.println("Erfolgreich loginKunde()"); //TODO: BEIM AUSKOMMENTIEREN HÄNGT SICH DAS PROGRAMM AUF
        } catch (IOException e) {
            System.err.println("Error beim lesen vom Client bei = loginKunde()" + e);
            out.println("ERROR 101");
        } catch (LoginException e) {
            System.err.println("Error beim lesen vom Client bei = loginKunde()" + e);
            out.println("ERROR 404");
        }
    }

    /**
     * Funktion um die Daten für loescheArtikel() zu empfangen und für Eshop umzuwandeln
     * @Param mitarbeiterID erwartet einen Integer
     * @Param mitarbeiter ruft eine Methode auf, um die Mitarbeiter ID vom eingeloggten mitarbeiter zu bekommen
     * @Param artikelnummer erwartet einen Integer
     *
     * */
    private void loescheArtikel(){
        try{
            int mitarbeiterID = Integer.parseInt(in.readLine());
            Mitarbeiter mitarbeiter = eShop.sucheMirarbeiterMitNummer(mitarbeiterID);
            int artikelnummer = Integer.parseInt(in.readLine());
            eShop.loescheArtikel(mitarbeiter, artikelnummer);
            out.println("Erfolgreich loescheArtikel()");
        }catch (IOException e){
            System.err.println("Error beim lesen vom Client bei = loescheArtikel()" + e);
            out.println("ERROR 101");
        } catch (IdNichtVorhandenException e) {
            System.err.println("Error beim lesen vom Client bei = loescheArtikel()" + e);
            out.println("ERROR 404");
        }

    }

    private void sucheArtikelMitNummer() {
        try{
            int artikenummer = Integer.parseInt(in.readLine());
            eShop.sucheArtikelMitNummer(artikenummer);
            //out.println("Erfolgreich sucheArtikelMitNummer()"); //TODO: WENN WEG HÄNGT AUF
        }catch (IOException e){
            System.err.println("Error beim lesen vom Client bei = sucheArtikelMitNummer()" + e);
            out.println("ERROR 101");
        } catch (IdNichtVorhandenException e){
            System.err.println("Error beim lesen vom Client bei = sucheArtikelMitNummer()" + e);
            out.println("ERROR 404");
        }
    }

    private void getEreignisListe() {
        List<Ereignis> ereignisListe = eShop.getEreignisListe();
        out.println(ereignisListe.size());
        for(Ereignis ereignis :ereignisListe) {
            out.println(ereignis.getDatum());
            out.println(ereignis.getArtikel());
            out.println(ereignis.getAnzahl());
            out.println(ereignis.getKundeOderMitarbeiter());
            try {
                if(ereignis.getKundeOderMitarbeiter() instanceof Mitarbeiter){
                    Mitarbeiter mitarbeiter = eShop.sucheMirarbeiterMitNummer(ereignis.getKundeOderMitarbeiter().getId());
                    out.println("m");
                    out.println(mitarbeiter.getId());
                    out.println(mitarbeiter.getVorname());
                    out.println(mitarbeiter.getNachname());
                    out.println(mitarbeiter.getEmail());
                    out.println(mitarbeiter.getUsername());
                    out.println(mitarbeiter.getPassword());
                }
                if(ereignis.getKundeOderMitarbeiter() instanceof Kunde){
                    Kunde kunde = eShop.sucheKundeMitNummer(ereignis.getKundeOderMitarbeiter().getId());
                    out.println("k");
                    out.println(kunde.getId());
                    out.println(kunde.getVorname());
                    out.println(kunde.getNachname());
                    out.println(kunde.getEmail());
                    out.println(kunde.getUsername());
                    out.println(kunde.getPassword());
                    out.println(kunde.getOrt());
                    out.println(kunde.getPlz());
                    out.println(kunde.getStrasse());
                    out.println(kunde.getStrassenNummer());
                }
            }catch (IdNichtVorhandenException e){
                //System.out.println("Fehler bei getEreignisListe ID konnte nicht gefunden werden");
            }
            out.println(ereignis.getTyp());
        }
        out.println("Erfolgreich getEreignisListe()");
    }

    private void artikelInWarenkorbHinzufügen(){
        try {
            int kundenID = Integer.parseInt(in.readLine());
            Kunde kunde = eShop.sucheKundeMitNummer(kundenID);
            int artikelnummer = Integer.parseInt(in.readLine());
            int menge = Integer.parseInt(in.readLine());

            eShop.artikelInWarenkorbHinzufügen(kunde, artikelnummer, menge);
            out.println("Erfolgreich: artikelInWarenkorbHinzufügen()");
        } catch(IOException e) {
            System.err.println("Error beim lesen vom Client bei = artikelInWarenkorbHinzufügen()" + e);
            out.println("ERROR 101");
        } catch (BestandNichtAusreichendException e) {
            throw new RuntimeException(e);
        } catch (MinusZahlException e) {
            System.err.println("Error beim lesen vom Client bei = artikelInWarenkorbHinzufügen()" + e);
            out.println("ERROR 404");
        } catch (KeinMassengutException e) {
            System.err.println("Error beim lesen vom Client bei = artikelInWarenkorbHinzufügen()" + e);
            out.println("ERROR 505");
        } catch (IdNichtVorhandenException e) {
            System.err.println("Error beim lesen vom Client bei = artikelInWarenkorbHinzufügen()" + e);
            out.println("ERROR 606");
        }
    }

    private void gesamtPreis(){
        try {
            int kundenID = Integer.parseInt(in.readLine());
            Kunde kunde = eShop.sucheKundeMitNummer(kundenID);

            double gesamtpreis = eShop.gesamtPreis(kunde);
            out.println(gesamtpreis);
            out.println("Erfolgreich: gesamtPreis()");
        } catch (IOException e){
            System.err.println("Error beim lesen vom Client bei = gesamtPreis()" + e);
            out.println("ERROR 101");
        } catch (IdNichtVorhandenException e) {
            System.err.println("Error beim lesen vom Client bei = gesamtPreis()" + e);
            out.println("ERROR 404");
        } catch (IstLeerException e) {
            System.err.println("Error beim lesen vom Client bei = gesamtPreis()" + e);
            out.println("ERROR 505");
        }
    }

    private void warenkorbLeeren(){
        try {
            int kundenID = Integer.parseInt(in.readLine());
            Kunde kunde = eShop.sucheKundeMitNummer(kundenID);

            eShop.warenkorbLeeren(kunde);
            //out.println("Erfolgreich: warenkorbLeeren()");
        } catch (IOException e){
            System.err.println("Error beim lesen vom Client bei = warenkorbLeeren()" + e);
            out.println("ERROR 101");
        } catch (IdNichtVorhandenException e) {
            System.err.println("Error beim lesen vom Client bei = warenkorbLeeren()" + e);
            out.println("ERROR 404");
        }
    }

    private void bestandImWarenkorbAendern() {
        try {
            int kundenID = Integer.parseInt(in.readLine());
            Kunde kunde = eShop.sucheKundeMitNummer(kundenID);

            int artikelnummer = Integer.parseInt(in.readLine());
            Artikel artikel = eShop.sucheArtikelMitNummer(artikelnummer);
            int menge = Integer.parseInt(in.readLine());

            eShop.bestandImWarenkorbAendern(kunde, artikel, menge);
            //out.println("Erfolgreich: bestandImWarenkorbAendern()");
        } catch (IOException e) {
            System.err.println("Error beim lesen vom Client bei = bestandImWarenkorbAendern()" + e);
            out.println("ERROR 101");
        } catch (IdNichtVorhandenException e) {
            System.err.println("Error beim lesen vom Client bei = bestandImWarenkorbAendern()" + e);
            out.println("ERROR 404");
        } catch (BestandNichtAusreichendException e) {
            System.err.println("Error beim lesen vom Client bei = bestandImWarenkorbAendern()" + e);
            out.println("ERROR 505");
        } catch (IstLeerException e) {
            System.err.println("Error beim lesen vom Client bei = bestandImWarenkorbAendern()" + e);
            out.println("ERROR 606");
        } catch (KeinMassengutException e) {
            System.err.println("Error beim lesen vom Client bei = bestandImWarenkorbAendern()" + e);
            out.println("ERROR 707");
        } catch (MinusZahlException e) {
            System.err.println("Error beim lesen vom Client bei = bestandImWarenkorbAendern()" + e);
            out.println("ERROR 808");
        }
    }

//    public Map<Artikel, Integer> gibWarenkorbArtikel(Person kunde) throws IstLeerException {
//    if (kunde instanceof Kunde k) {
//        Warenkorb wk = warenkorbManagement.getWarenkorb(k);
//        return wk.getWarenkorbMap();
//    } else {
//        throw new IstLeerException();
//    }
    private void gibWarenkorbArtikel(){
        //Map<Artikel, Integer> gibWarenkorbAusArtikel = eShop.gibWarenkorbArtikel(kunde);
        try {
            int kundenID = Integer.parseInt(in.readLine());
            Kunde kunde = eShop.sucheKundeMitNummer(kundenID);
            Map<Artikel, Integer> warenKorbInhalt = eShop.gibWarenkorbArtikel(kunde);
            out.println(warenKorbInhalt.size());
            for(Map.Entry<Artikel, Integer> entry: warenKorbInhalt.entrySet()) {
                out.println(entry.getValue());
                out.println(entry.getKey().getArtikelnummer());
                out.println(entry.getKey().getArtikelbezeichnung());
                out.println(entry.getKey().getArtikelbestand());
                out.println(entry.getKey().getArtikelPreis());
            }
            out.println("Erfolgreich: gibWarenkorbArtikel()"); //TODO: WENN NICHT DA, DANN KEINE KUNDENSEITE
        } catch (IOException  e) {
            System.err.println("Error beim lesen vom Client bei = gibWarenkorbArtikel()" + e);
            out.println("ERROR 101");
        } catch (IdNichtVorhandenException e) {
            System.err.println("Error beim lesen vom Client bei = gibWarenkorbArtikel()" + e);
            out.println("ERROR 404");
        } catch (IstLeerException e) {
            System.err.println("Error beim lesen vom Client bei = gibWarenkorbArtikel()" + e);
            out.println("ERROR 505");
        }

    }
    //public Kunde(String vorname, String nachname, String email, String username, String password, int id, String ort, int plz, String strasse, int strassenNummer) {
    private void warenkorbKaufen(){ //TODO: HÄNGT SICH AUF
        try {
            int kundenID = Integer.parseInt(in.readLine());
            Kunde kunde = eShop.sucheKundeMitNummer(kundenID);

            Rechnung rechnung = eShop.warenkorbKaufen(kunde);
            Warenkorb warenkorb = rechnung.getWarenkorb();
            out.println(warenkorb.getWarenkorbMap().size());
            for (Map.Entry<Artikel, Integer> entry : warenkorb.getWarenkorbMap().entrySet()) {
                out.println(entry.getValue());
                out.println(entry.getKey().getArtikelnummer());
                out.println(entry.getKey().getArtikelbezeichnung());
                out.println(entry.getKey().getArtikelbestand());
                out.println(entry.getKey().getArtikelPreis());
            }
            //out.println("Erfolgreich: warenkorbKaufen()");
        } catch (IOException e) {
            System.err.println("Error beim lesen vom Client bei = warenkorbKaufen()" + e);
            out.println("ERROR 101");
        } catch (IdNichtVorhandenException e) {
            System.err.println("Error beim lesen vom Client bei = warenkorbKaufen()" + e);
            out.println("ERROR 404");
        } catch (BestandNichtAusreichendException e) {
            System.err.println("Error beim lesen vom Client bei = warenkorbKaufen()" + e);
            out.println("ERROR 505");
        } catch (IstLeerException e) {
            System.err.println("Error beim lesen vom Client bei = warenkorbKaufen()" + e);
            out.println("ERROR 606");
        }
    }

}
