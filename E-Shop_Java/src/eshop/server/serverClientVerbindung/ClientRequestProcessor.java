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

            switch (input) {
                case "gibAlleArtikel":
                    Map<Integer, Artikel> alleArtikel = eShop.gibAlleArtikel();
                    gibAlleArtikel(alleArtikel);
                case "gibAlleMassengutartikel":
                    Map<Integer, Artikel> alleMassengutartikel = eShop.gibAlleArtikel();
                    gibAlleMassengutartikel(alleMassengutartikel);
                case "gibAlleMitarbeiter":
                    Map<Integer, Mitarbeiter> alleMitarbeiter = eShop.gibAlleMitarbeiter();
                    gibAlleMitarbeiter(alleMitarbeiter);
                case "addArtikel":
                    addArtikel();
                case "addMassengutartikel":
                    addMassengutartikel();
                case "aendereArtikelBestand":
                    aendereArtikelBestand();
                case "addKunde":
                    addKunde();
                case "addMitarbeiter":
                    addMitarbeiter();
                case "loginMitarbeiter":
                    loginMitarbeiter();
                case "loginKunde":
                    loginKunde();
                case "loescheArtikel":
                    loescheArtikel();
                case "getEreignisListe":
                    List<Ereignis> ereignisListe = eShop.getEreignisListe();
                    getEreignisListe(ereignisListe);
                case "sucheArtikelMitNummer":
                    sucheArtikelMitNummer();
                case "artikelInWarenkorbHinzufügen":
                    artikelInWarenkorbHinzufügen();
                case "gibWarenkorbArtikel":
                    Map<Artikel, Integer> gibWarenkorbAusArtikel = eShop.gibWarenkorbArtikel(kunde);
                    gibWarenkorbArtikel(gibWarenkorbAusArtikel);
                case "gesamtPreis":
                    gesamtPreis();
                case "warenkorbLeeren":
                    warenkorbLeeren();
                case "warenkorbKaufen":
                    warenkorbKaufen();
                case "bestandImWarenkorbAendern":
                    bestandImWarenkorbAendern();
                case "artikelImWarenkorbEntfernen":
                    artikelImWarenkorbEntfernen();
                default:
            }

        } while (!input.equals("exit"));
    }
    /**
     * Funktion um alle Artikel aus der Map auszulesen und diese in Strings umzuwandeln
     * @Param Integer ist der Key-Wert in der Map und steht für die eindeutige Artikelnummer
     * @Param Artikel Holt sich das Artikel-Objekt aus der Map zum zugehörigen Key
     * */
    private void gibAlleArtikel(Map<Integer, Artikel> alleArtikel){
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
    }

    /**
     * Funktion um alle Massengutartikel aus der Map auszulesen und diese in Strings umzuwandeln
     * @Param Integer ist der Key-Wert in der Map und steht für die eindeutige Artikelnummer
     * @Param Artikel holt sich das Artikel-Objekt aus der Map zum zugehörigen Key
     * */
    private void gibAlleMassengutartikel(Map<Integer, Artikel> alleArtikel){
        Map<Integer, MassengutArtikel> onlyAlleMassengutArtikel = new HashMap<Integer, MassengutArtikel>();
        for(Map.Entry<Integer, Artikel> entry : alleArtikel.entrySet()) {
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
    private void gibAlleMitarbeiter(Map<Integer, Mitarbeiter> alleMitarbeiter){
        //TODO: brauche wir das hier?
        //Map<Integer, Mitarbeiter> onlyAlleMitarbeiter = new HashMap<>();
        for (Map.Entry<Integer, Mitarbeiter> entry : alleMitarbeiter.entrySet()) {
            out.println(entry.getValue().getId());
            out.println(entry.getValue().getVorname());
            out.println(entry.getValue().getNachname());
            out.println(entry.getValue().getEmail());
            out.println(entry.getValue().getUsername());
            out.println(entry.getValue().getPassword());
        }
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
        } catch (IOException e) {
            System.err.println("Error beim lesen vom Client bei = addArtikel" + e);
        } catch (IdNichtVorhandenException e){
            System.err.println("Error beim lesen vom Client bei = addArtikel" + e);
            out.println("ERROR 404");
        } catch (DoppelteIdException e){
            System.err.println("Error beim lesen vom Client bei = addArtikel" + e);
            out.println("ERROR 505");
        } catch (MinusZahlException e){
            System.err.println("Error beim lesen vom Client bei = addArtikel" + e);
            out.println("ERROR 606");
        } catch (KeinMassengutException e){
            System.err.println("Error beim lesen vom Client bei = addArtikel" + e);
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

        } catch (IOException e) {
            System.err.println("Error beim lesen vom Client bei = addMassengutartikel" + e);
        } catch (IdNichtVorhandenException e){
            System.err.println("Error beim lesen vom Client bei = addMassengutartikel" + e);
            out.println("ERROR 505");
        } catch (DoppelteIdException e){
            System.err.println("Error beim lesen vom Client bei = addMassengutartikel" + e);
            out.println("ERROR 606");
        } catch (MinusZahlException e){
            System.err.println("Error beim lesen vom Client bei = addMassengutartikel" + e);
            out.println("ERROR 707");
        } catch (KeinMassengutException e){
            System.err.println("Error beim lesen vom Client bei = addMassengutartikel" + e);
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
            out.println("aendereArtikelBestand war erfolgreich");
        } catch (IOException e){
            System.err.println("Error beim lesen vom Client bei = aendereArtikelBestand" + e);
            out.println("ERROR 404");
        } catch(IdNichtVorhandenException e) {
            System.err.println("Error beim lesen vom Client bei = aendereArtikelBestand" + e);
            out.println("ERROR 505");
        } catch(MinusZahlException e) {
            System.err.println("Error beim lesen vom Client bei = aendereArtikelBestand" + e);
            out.println("ERROR 606");
        } catch(KeinMassengutException e) {
            System.err.println("Error beim lesen vom Client bei = aendereArtikelBestand" + e);
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
        }catch (IOException e){
            System.err.println("Error beim lesen vom Client bei = addKunde" + e);
        } catch (DoppelteIdException e) {
            System.err.println("Error beim lesen vom Client bei = addKunde" + e);
            out.println("ERROR 505");
        } catch (UsernameExistiertException e) {
            System.err.println("Error beim lesen vom Client bei = addKunde" + e);
            out.println("ERROR 606");
        } catch (EmailExistiertException e) {
            System.err.println("Error beim lesen vom Client bei = addKunde" + e);
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
        }catch (IOException e){
            System.err.println("Error beim lesen vom Client bei = addMitarbeiter" + e);
        } catch (DoppelteIdException e) {
            System.err.println("Error beim lesen vom Client bei = addMitarbeiter" + e);
            out.println("ERROR 505");
        } catch (UsernameExistiertException e) {
            System.err.println("Error beim lesen vom Client bei = addMitarbeiter" + e);
            out.println("ERROR 606");
        } catch (EmailExistiertException e) {
            System.err.println("Error beim lesen vom Client bei = addMitarbeiter" + e);
            out.println("ERROR 707");
        } catch (IdNichtVorhandenException e) {
            System.err.println("Error beim lesen vom Client bei = addMitarbeiter" + e);
            out.println("ERROR 808");
        }
    }

    private void loginMitarbeiter()  {
        try {
            String usernameOrEmail = in.readLine();
            String password = in.readLine();

            Mitarbeiter mitarbeiter = eShop.loginMitarbeiter(usernameOrEmail, password);
            out.println(mitarbeiter.getVorname());
            out.println(mitarbeiter.getNachname());
            out.println(mitarbeiter.getEmail());
            out.println(mitarbeiter.getUsername());
            out.println(mitarbeiter.getPassword());
        } catch (IOException e) {
            System.err.println("Error beim lesen vom Client bei = loginMitarbeiter" + e);
        } catch (LoginException e) {
            System.err.println("Error beim lesen vom Client bei = loginMitarbeiter" + e);
            out.println("ERROR 404");
        }
    }

    private void loginKunde(){
        try {
            String usernameOrEmail = in.readLine();
            String password = in.readLine();

            Kunde kunde = eShop.loginKunde(usernameOrEmail, password);
            out.println(kunde.getVorname());
            out.println(kunde.getNachname());
            out.println(kunde.getEmail());
            out.println(kunde.getUsername());
            out.println(kunde.getPassword());
            out.println(kunde.getOrt());
            out.println(kunde.getPlz());
            out.println(kunde.getStrasse());
            out.println(kunde.getStrassenNummer());
        } catch (IOException e) {
            System.err.println("Error beim lesen vom Client bei = loginKunde" + e);
        } catch (LoginException e) {
            System.err.println("Error beim lesen vom Client bei = loginKunde" + e);
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
        }catch (IOException e){
            System.err.println("Error beim lesen vom Client bei = loescheArtikel" + e);
        } catch (IdNichtVorhandenException e) {
            System.err.println("Error beim lesen vom Client bei = loginKunde" + e);
            out.println("ERROR 404");
        }

    }

    private void sucheArtikelMitNummer() {
        try{
            int artikenummer = Integer.parseInt(in.readLine());
            eShop.sucheArtikelMitNummer(artikenummer);
        }catch (IOException e){
            System.err.println("Error beim lesen vom Client bei = loescheArtikel" + e);
        } catch (IdNichtVorhandenException e){
            System.err.println("Error beim lesen vom Client bei = loescheArtikel" + e);
            out.println("ERROR 404");
        }
    }

    private void getEreignisListe(List<Ereignis> ereignisListe) {
        //return ereignisManagement.getEreignisse();
        //Date datum, String artikelbezeichnung, int anzahl, Person kundeOderMitarbeiter, EreignisTyp typ
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
    }

    private void artikelInWarenkorbHinzufügen(){
        try {
            int kundenID = Integer.parseInt(in.readLine());
            Kunde kunde = eShop.sucheKundeMitNummer(kundenID);
            int artikelnummer = Integer.parseInt(in.readLine());
            int menge = Integer.parseInt(in.readLine());

            eShop.artikelInWarenkorbHinzufügen(kunde, artikelnummer, menge);

        } catch(IOException e) {
            System.err.println("Error beim lesen vom Client bei = loescheArtikel" + e);
        } catch (BestandNichtAusreichendException e) {
            throw new RuntimeException(e);
        } catch (MinusZahlException e) {
            System.err.println("Error beim lesen vom Client bei = loescheArtikel" + e);
            out.println("ERROR 404");
        } catch (KeinMassengutException e) {
            System.err.println("Error beim lesen vom Client bei = loescheArtikel" + e);
            out.println("ERROR 505");
        } catch (IdNichtVorhandenException e) {
            System.err.println("Error beim lesen vom Client bei = loescheArtikel" + e);
            out.println("ERROR 606");
        }
    }

    private void gesamtPreis(){
        try {
            int kundenID = Integer.parseInt(in.readLine());
            Kunde kunde = eShop.sucheKundeMitNummer(kundenID);

            eShop.gesamtPreis(kunde);
        } catch (IOException e){
            System.err.println("Error beim lesen vom Client bei = loescheArtikel" + e);
        } catch (IdNichtVorhandenException e) {
            System.err.println("Error beim lesen vom Client bei = loescheArtikel" + e);
            out.println("ERROR 404");
        } catch (IstLeerException e) {
            System.err.println("Error beim lesen vom Client bei = loescheArtikel" + e);
            out.println("ERROR 505");
        }
    }

    private void warenkorbLeeren(){
        try {
            int kundenID = Integer.parseInt(in.readLine());
            Kunde kunde = eShop.sucheKundeMitNummer(kundenID);

            eShop.warenkorbLeeren(kunde);
        } catch (IOException e){
            System.err.println("Error beim lesen vom Client bei = loescheArtikel" + e);
        } catch (IdNichtVorhandenException e) {
            System.err.println("Error beim lesen vom Client bei = loescheArtikel" + e);
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
        } catch (IOException e) {

            System.err.println("Error beim lesen vom Client bei = loescheArtikel" + e);
        } catch (IdNichtVorhandenException e) {
            System.err.println("Error beim lesen vom Client bei = loescheArtikel" + e);
            out.println("ERROR 404");
        } catch (BestandNichtAusreichendException e) {
            System.err.println("Error beim lesen vom Client bei = loescheArtikel" + e);
            out.println("ERROR 505");
        } catch (IstLeerException e) {
            System.err.println("Error beim lesen vom Client bei = loescheArtikel" + e);
            out.println("ERROR 606");
        } catch (KeinMassengutException e) {
            System.err.println("Error beim lesen vom Client bei = loescheArtikel" + e);
            out.println("ERROR 707");
        } catch (MinusZahlException e) {
            System.err.println("Error beim lesen vom Client bei = loescheArtikel" + e);
            out.println("ERROR 808");
        }
    }

}
