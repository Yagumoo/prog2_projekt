package eshop.server.serverClientVerbindung;

import eshop.common.enitities.*;
import eshop.common.exceptions.*;
import eshop.server.domain.E_Shop;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Verarbeitet Anfragen von Clients in einem separaten Thread.
 *
 * Diese Klasse ist für die Kommunikation mit einem Client zuständig, indem sie Anfragen des Clients verarbeitet
 * und Antworten zurücksendet. Sie verwendet einen `Socket`, um mit dem Client zu kommunizieren, und einen `E_Shop`,
 * um auf die Geschäftslogik und Daten des Online-Shops zuzugreifen.
 *
 * Die Klasse erweitert `Thread`, um jede Client-Anfrage in einem eigenen Thread zu verarbeiten, sodass mehrere
 * Clients gleichzeitig bedient werden können.
 */
public class ClientRequestProcessor extends Thread {

    private Socket clientSocket;
    private E_Shop eShop;

    private PrintStream out;
    private BufferedReader in;

    /**
     * Erstellt einen neuen `ClientRequestProcessor`-Thread, der für die Verarbeitung von Anfragen eines Clients
     * zuständig ist.
     *
     * Initialisiert die Socket-Verbindung zum Client und die Kommunikationsströme (Input/Output Streams) für den
     * Datenaustausch.
     *
     * @param clientSocket Der Socket, der die Verbindung zwischen dem Client und dem Server repräsentiert.
     * @param eShop Die Hauptklasse des Online-Shops, die Zugriff auf die Geschäftslogik und Daten bietet.
     */
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
    private synchronized void gibAlleArtikel(){
        Map<Integer, Artikel> alleArtikel = eShop.gibAlleArtikel();
        Map<Integer, Artikel> onlyAlleArtikel = new HashMap<Integer, Artikel>();
        for(Map.Entry<Integer, Artikel> entry : alleArtikel.entrySet()) {
            if(entry.getValue() instanceof Artikel) {
                onlyAlleArtikel.put(entry.getKey(), (Artikel) entry.getValue());
            }
        }
        out.println("Erfolgreich: gibAlleArtikel()");
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
    private synchronized void gibAlleMassengutartikel(){
        Map<Integer, Artikel> alleMassengutartikel = eShop.gibAlleArtikel();
        Map<Integer, MassengutArtikel> onlyAlleMassengutArtikel = new HashMap<Integer, MassengutArtikel>();
        for(Map.Entry<Integer, Artikel> entry : alleMassengutartikel.entrySet()) {
            if(entry.getValue() instanceof MassengutArtikel) {
                onlyAlleMassengutArtikel.put(entry.getKey(), (MassengutArtikel) entry.getValue());
            }
        }
        out.println("Erfolgreich: gibAlleMassengutartikel()");
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
    private synchronized void gibAlleMitarbeiter(){
        Map<Integer, Mitarbeiter> alleMitarbeiter = eShop.gibAlleMitarbeiter();
        out.println("Erfolgreich: gibAlleMitarbeiter()");
        out.println(alleMitarbeiter.size());
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
    private synchronized void addArtikel(){
        try {
            int mitarbeiterID = Integer.parseInt(in.readLine());
            int artikelNummer = Integer.parseInt(in.readLine());
            String artikelbezeichnung = in.readLine();
            int artikelbestand = Integer.parseInt(in.readLine());
            double artikelpreis = Double.parseDouble(in.readLine());

            Mitarbeiter mitarbeiter = eShop.sucheMirarbeiterMitNummer(mitarbeiterID);
            Artikel artikel = new Artikel(artikelNummer, artikelbezeichnung, artikelbestand, artikelpreis);
            eShop.addArtikel(mitarbeiter, artikel);
            out.println("Erfolgreich: addArtikel()");
        } catch (IOException e) {
            System.err.println("Error beim lesen vom Client bei = addArtikel()" + e);
            out.println("ERROR 101");
        } catch (IdNichtVorhandenException e){
            System.err.println("Error beim lesen vom Client bei = addArtikel()" + e);
            out.println("ERROR 303");
        } catch (DoppelteIdException e){
            System.err.println("Error beim lesen vom Client bei = addArtikel()" + e);
            out.println("ERROR 304");
        } catch (MinusZahlException e){
            System.err.println("Error beim lesen vom Client bei = addArtikel()" + e);
            out.println("ERROR 202");
        } catch (KeinMassengutException e){
            System.err.println("Error beim lesen vom Client bei = addArtikel()" + e);
            out.println("ERROR 405");
        } catch (ArtikelnameDoppeltException e){
            System.err.println("Error beim lesen vom Client bei = addArtikel()" + e);
            out.println("ERROR 407");
        }
    }

    /**
     * Liest die Daten eines Massengutartikels vom Client und fügt diesen Artikel zum Online-Shop hinzu.
     *
     * Diese Methode verarbeitet eine Client-Anfrage zum Hinzufügen eines neuen Massengutartikels. Sie liest die erforderlichen
     * Informationen vom Client, erstellt ein `MassengutArtikel`-Objekt und fügt es mithilfe der `addArtikel`-Methode des `E_Shop`
     * zur Artikelliste hinzu. Bei Fehlern werden entsprechende Fehlermeldungen an den Client gesendet.
     *
     * Der Ablauf umfasst das Einlesen der folgenden Daten:
     * 1. Mitarbeiter-ID (zum Identifizieren des Mitarbeiters, der den Artikel hinzufügt)
     * 2. Artikelnummer
     * 3. Artikelbezeichnung
     * 4. Artikelbestand
     * 5. Artikelpreis
     * 6. Anzahl der Massengut-Einheiten
     *
     * Die Methode überprüft auch mögliche Fehlerquellen und gibt Fehlermeldungen zurück, wenn beim Einlesen oder Verarbeiten der
     * Daten ein Fehler auftritt.
     *
     * @throws IOException Wenn ein Fehler beim Lesen von Daten vom Client auftritt.
     * @throws IdNichtVorhandenException Wenn der angegebene Mitarbeiter nicht gefunden wird.
     * @throws DoppelteIdException Wenn bereits ein Artikel mit der gleichen ID existiert.
     * @throws MinusZahlException Wenn eine der übermittelten Zahlen negativ ist.
     * @throws ArtikelnameDoppeltException Wenn bereits ein Artikel mit der gleichen Bezeichnung existiert.
     * @throws KeinMassengutException Wenn die übergebene Anzahl für Massengutartikel nicht den Vorgaben entspricht.
     */
    private synchronized void addMassengutartikel(){
        try {
            int mitarbeiterID = Integer.parseInt(in.readLine());
            int artikelNummer = Integer.parseInt(in.readLine());
            String artikelbezeichnung = in.readLine();
            int artikelbestand = Integer.parseInt(in.readLine());
            double artikelpreis = Double.parseDouble(in.readLine());
            int artikelMassengut = Integer.parseInt(in.readLine());

            Mitarbeiter mitarbeiter = eShop.sucheMirarbeiterMitNummer(mitarbeiterID);
            Artikel massengutArtikel = new MassengutArtikel(artikelNummer, artikelbezeichnung, artikelbestand, artikelpreis, artikelMassengut);
            eShop.addArtikel(mitarbeiter, massengutArtikel);
            out.println("Erfolgreich: addMassengutartikel()");
        } catch (IOException e) {
            System.err.println("Error beim lesen vom Client bei = addMassengutartikel()" + e);
            out.println("ERROR 101");
        } catch (IdNichtVorhandenException e){
            System.err.println("Error beim lesen vom Client bei = addMassengutartikel()" + e);
            out.println("ERROR 303");
        } catch (DoppelteIdException e){
            System.err.println("Error beim lesen vom Client bei = addMassengutartikel()" + e);
            out.println("ERROR 304");
        } catch (MinusZahlException e){
            System.err.println("Error beim lesen vom Client bei = addMassengutartikel()" + e);
            out.println("ERROR 202");
        } catch (ArtikelnameDoppeltException e){
            System.err.println("Error beim lesen vom Client bei = addMassengutartikel()" + e);
            out.println("ERROR 404");
        } catch (KeinMassengutException e){
            System.err.println("Error beim lesen vom Client bei = addArtikel()" + e);
            out.println("ERROR 405");
        }
    }

    /**
     * Ändert den Lagerbestand eines Artikels basierend auf den eingegebenen Werten.
     *
     * @throws IOException                       wenn ein Fehler beim Lesen vom Client auftritt
     * @throws IdNichtVorhandenException         wenn die eingegebene Mitarbeiter-ID nicht vorhanden ist
     * @throws MinusZahlException                wenn der eingegebene Bestandswert negativ ist
     * @throws KeinMassengutException            wenn der Artikel kein Massengut ist und der Bestandswert größer als 1 ist
     */
    private synchronized void aendereArtikelBestand()  {
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
            out.println("ERROR 303");
        } catch(MinusZahlException e) {
            System.err.println("Error beim lesen vom Client bei = aendereArtikelBestand()" + e);
            out.println("ERROR 202");
        } catch(KeinMassengutException e) {
            System.err.println("Error beim lesen vom Client bei = aendereArtikelBestand()" + e);
            out.println("ERROR 405");
        }
        //TODO: Fehler zurük zum client senden wie oben ^

    }

    /**
     * Fügt einen neuen Kunden hinzu, basierend auf den eingegebenen Werten.
     *
     * @throws IOException                   wenn ein Fehler beim Lesen vom Client auftritt
     * @throws DoppelteIdException           wenn die eingegebene ID bereits existiert
     * @throws UsernameExistiertException    wenn der eingegebene Benutzername bereits existiert
     * @throws EmailExistiertException       wenn die eingegebene E-Mail-Adresse bereits existiert
     */
    private synchronized void addKunde(){
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
            out.println("ERROR 304");
        } catch (UsernameExistiertException e) {
            System.err.println("Error beim lesen vom Client bei = addKunde()" + e);
            out.println("ERROR 808");
        } catch (EmailExistiertException e) {
            System.err.println("Error beim lesen vom Client bei = addKunde()" + e);
            out.println("ERROR 809");
        }
    }

    /**
     * Fügt einen neuen Mitarbeiter zum eShop hinzu.
     *
     * @throws IOException                   Wenn ein Fehler beim Lesen vom Client auftritt.
     * @throws DoppelteIdException           Wenn die Mitarbeiter-ID bereits existiert.
     * @throws UsernameExistiertException    Wenn der Benutzername bereits existiert.
     * @throws EmailExistiertException       Wenn die E-Mail-Adresse bereits existiert.
     * @throws IdNichtVorhandenException     Wenn die Mitarbeiter-ID nicht gefunden wird.
     */
    private synchronized void addMitarbeiter(){
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
            out.println("ERROR 304");
        } catch (UsernameExistiertException e) {
            System.err.println("Error beim lesen vom Client bei = addMitarbeiter()" + e);
            out.println("ERROR 808");
        } catch (EmailExistiertException e) {
            System.err.println("Error beim lesen vom Client bei = addMitarbeiter()" + e);
            out.println("ERROR 809");
        } catch (IdNichtVorhandenException e) {
            System.err.println("Error beim lesen vom Client bei = addMitarbeiter()" + e);
            out.println("ERROR 303");
        }
    }

    /**
     * Loggt einen Mitarbeiter in das System ein.
     *
     * @throws IOException falls ein Fehler beim Lesen vom Client auftritt
     * @throws LoginException falls der Login fehlschlägt
     */
    private synchronized void loginMitarbeiter()  {
        try {
            String usernameOrEmail = in.readLine();
            String password = in.readLine();

            Mitarbeiter mitarbeiter = eShop.loginMitarbeiter(usernameOrEmail, password);
            out.println("Erfolgreich loginMitarbeiter()");
            out.println(mitarbeiter.getId());
            out.println(mitarbeiter.getVorname());
            out.println(mitarbeiter.getNachname());
            out.println(mitarbeiter.getEmail());
            out.println(mitarbeiter.getUsername());
            out.println(mitarbeiter.getPassword());
        } catch (IOException e) {
            System.err.println("Error beim lesen vom Client bei = loginMitarbeiter()" + e);
            out.println("ERROR 101");
        } catch (LoginException e) {
            System.err.println("Error beim lesen vom Client bei = loginMitarbeiter()" + e);
            out.println("ERROR 807");
        }
    }

    /**
     * Diese Methode ermöglicht es einem Kunden, sich mit seinem Benutzernamen oder seiner E-Mail-Adresse und seinem Passwort in den eShop einzuloggen.
     * Wenn der Login erfolgreich ist, werden die Informationen des Kunden zurückgegeben, einschließlich seiner ID, Vorname, Nachname, E-Mail, Benutzername,
     * Passwort, Ort, Postleitzahl, Straße und Hausnummer.
     *
     *@throws IOException Wenn ein Fehler beim Lesen vom Client auftritt.
     * @throws LoginException Wenn der Login fehlschlägt.
     *
     * */

    private synchronized void loginKunde(){
        try {
            String usernameOrEmail = in.readLine();
            String password = in.readLine();

            Kunde kunde = eShop.loginKunde(usernameOrEmail, password);
            out.println("Erfolgreich loginKunde()");
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

        } catch (IOException e) {
            System.err.println("Error beim lesen vom Client bei = loginKunde()" + e);
            out.println("ERROR 101");
        } catch (LoginException e) {
            System.err.println("Error beim lesen vom Client bei = loginKunde()" + e);
            out.println("ERROR 807");
        }
    }

    /**
     * Funktion um die Daten für loescheArtikel() zu empfangen und für Eshop umzuwandeln
     * @Param mitarbeiterID erwartet einen Integer
     * @Param mitarbeiter ruft eine Methode auf, um die Mitarbeiter ID vom eingeloggten mitarbeiter zu bekommen
     * @Param artikelnummer erwartet einen Integer
     *
     * */
    private synchronized void loescheArtikel(){
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
            out.println("ERROR 303");
        }

    }

    /**
     * Sucht einen Artikel anhand der Artikelnummer.
     *
     * @throws IOException Wenn ein Fehler beim Lesen vom Client auftritt.
     * @throws IdNichtVorhandenException Wenn die Artikelnummer nicht vorhanden ist.
     */
    private synchronized void sucheArtikelMitNummer() {
        try{
            int artikenummer = Integer.parseInt(in.readLine());
            Artikel artikel = eShop.sucheArtikelMitNummer(artikenummer);
            out.println("Erfolgreich sucheArtikelMitNummer()");
            if (artikel instanceof MassengutArtikel massengutArtikel) {
                out.println("m");
                out.println(massengutArtikel.getArtikelnummer());
                out.println(massengutArtikel.getArtikelbestand());
                out.println(massengutArtikel.getArtikelbestand());
                out.println(massengutArtikel.getArtikelPreis());
                out.println(massengutArtikel.getAnzahlMassengut());
            }else {
                out.println("a");
                out.println(artikel.getArtikelnummer());
                out.println(artikel.getArtikelbestand());
                out.println(artikel.getArtikelbestand());
                out.println(artikel.getArtikelPreis());
            }
        }catch (IOException e){
            System.err.println("Error beim lesen vom Client bei = sucheArtikelMitNummer()" + e);
            out.println("ERROR 101");
        } catch (IdNichtVorhandenException e){
            System.err.println("Error beim lesen vom Client bei = sucheArtikelMitNummer()" + e);
            out.println("ERROR 303");
        }
    }

    /**
     * Ruft die Liste der Ereignisse aus dem eShop ab und gibt detaillierte Informationen zu jedem Ereignis aus.
     *
     * Die Methode holt sich eine Liste von Ereignissen durch einen Aufruf der Methode {eShop.getEreignisListe()}
     * Danach wird die Größe der Liste und die Details jedes Ereignisses, wie Datum, Artikel und Anzahl, auf der Konsole ausgegeben.
     * Zusätzlich wird geprüft, ob der `KundeOderMitarbeiter` in dem Ereignis ein {@link Mitarbeiter} oder ein {@link Kunde} ist,
     * und es werden weitere Details wie ID, Vorname, Nachname, E-Mail und Benutzername für den jeweiligen Typ des `KundeOderMitarbeiter` ausgegeben.
     * Falls der `KundeOderMitarbeiter` nicht gefunden werden kann, wird eine Fehlermeldung auf der Konsole ausgegeben und ein Fehlercode angezeigt.
     * Abschließend wird der Typ des Ereignisses auf der Konsole ausgegeben.
     *
     * @throws IdNichtVorhandenException Wenn ein `KundeOderMitarbeiter` mit der angegebenen ID nicht gefunden werden kann,
     *                                   wird eine entsprechende Fehlermeldung ausgegeben und der Fehlercode 303 angezeigt.
     */
    private synchronized void getEreignisListe() {
        List<Ereignis> ereignisListe = eShop.getEreignisListe();
        out.println("Erfolgreich getEreignisListe()");
        out.println(ereignisListe.size());
        for(Ereignis ereignis :ereignisListe) {
            out.println(ereignis.getDatum());
            out.println(ereignis.getArtikel());
            out.println(ereignis.getAnzahl());
            //out.println(ereignis.getKundeOderMitarbeiter());
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
            }
            catch (IdNichtVorhandenException e){
                System.err.println("Error beim lesen vom Client bei = sucheArtikelMitNummer()" + e);
                out.println("ERROR 303");
            }
            out.println(ereignis.getTyp());
        }
    }

    /**
     * Fügt einen Artikel zum Warenkorb eines Kunden hinzu.
     *
     * Die Methode liest die Kunden-ID, Artikelnummer und Menge von der Eingabe. Anschließend wird überprüft, ob der Kunde
     * existiert und der Artikel in der angegebenen Menge zum Warenkorb des Kunden hinzugefügt. Bei Erfolg wird eine Bestätigung
     * ausgegeben. Bei Fehlern während des Lesevorgangs oder bei Problemen mit dem Hinzufügen des Artikels werden entsprechende
     * Fehlermeldungen ausgegeben.
     *
     * @throws IOException Falls ein Fehler beim Lesen der Eingabedaten vom Client auftritt.
     * @throws BestandNichtAusreichendException Wenn die angeforderte Menge des Artikels nicht verfügbar ist.
     * @throws MinusZahlException Wenn eine der Eingaben (Menge) eine negative Zahl ist.
     * @throws KeinMassengutException Wenn der Artikel kein Massengut ist, jedoch eine Massengut-Bedingung erwartet wird.
     * @throws IdNichtVorhandenException Wenn die Kunden-ID oder Artikelnummer nicht in der Datenbank gefunden werden kann.
     * @throws IstLeerException Wenn der Warenkorb leer ist und daher keine weiteren Artikel hinzugefügt werden können.
     */
    private synchronized void artikelInWarenkorbHinzufügen(){
        try {
            int kundenID = Integer.parseInt(in.readLine());
            Person kunde = eShop.sucheKundeMitNummer(kundenID);
            int artikelnummer = Integer.parseInt(in.readLine());
            int menge = Integer.parseInt(in.readLine());
            //TODO: ist Peron richtig gucken
            eShop.artikelInWarenkorbHinzufügen(kunde, artikelnummer, menge);
            out.println("Erfolgreich: artikelInWarenkorbHinzufügen()");
        } catch(IOException e) {
            System.err.println("Error beim lesen vom Client bei = artikelInWarenkorbHinzufügen()" + e);
            out.println("ERROR 101");
        } catch (BestandNichtAusreichendException e) {
            System.err.println("Error beim lesen vom Client bei = artikelInWarenkorbHinzufügen()" + e);
            out.println("ERROR 408");
        } catch (MinusZahlException e) {
            System.err.println("Error beim lesen vom Client bei = artikelInWarenkorbHinzufügen()" + e);
            out.println("ERROR 202");
        } catch (KeinMassengutException e) {
            System.err.println("Error beim lesen vom Client bei = artikelInWarenkorbHinzufügen()" + e);
            out.println("ERROR 405");
        } catch (IdNichtVorhandenException e) {
            System.err.println("Error beim lesen vom Client bei = artikelInWarenkorbHinzufügen()" + e);
            out.println("ERROR 303");
        } catch (IstLeerException e) {
            System.err.println("Error beim lesen vom Client bei = gesamtPreis()" + e);
            out.println("ERROR 406");
        }
    }

    /**
     * Berechnet und gibt den Gesamtpreis für den Warenkorb eines Kunden aus.
     *
     * Die Methode liest die Kunden-ID von der Eingabe, ruft den entsprechenden {@link Kunde} aus dem eShop ab und berechnet
     * den Gesamtpreis für den Warenkorb dieses Kunden. Der Gesamtpreis wird anschließend auf der Konsole ausgegeben. Bei Fehlern
     * während des Lesevorgangs oder bei Problemen mit dem Berechnen des Gesamtpreises werden entsprechende Fehlermeldungen
     * ausgegeben.
     *
     * @throws IOException Falls ein Fehler beim Lesen der Eingabedaten vom Client auftritt.
     * @throws IdNichtVorhandenException Wenn die Kunden-ID nicht in der Datenbank gefunden werden kann.
     * @throws IstLeerException Wenn der Warenkorb des Kunden leer ist und daher kein Gesamtpreis berechnet werden kann.
     */
    private synchronized void gesamtPreis(){
        try {
            int kundenID = Integer.parseInt(in.readLine());
            Kunde kunde = eShop.sucheKundeMitNummer(kundenID);

            double gesamtpreis = eShop.gesamtPreis(kunde);
            out.println("Erfolgreich: gesamtPreis()");
            out.println(gesamtpreis);
        } catch (IOException e){
            System.err.println("Error beim lesen vom Client bei = gesamtPreis()" + e);
            out.println("ERROR 101");
        } catch (IdNichtVorhandenException e) {
            System.err.println("Error beim lesen vom Client bei = gesamtPreis()" + e);
            out.println("ERROR 303");
        } catch (IstLeerException e) {
            System.err.println("Error beim lesen vom Client bei = gesamtPreis()" + e);
            out.println("ERROR 406");
        }
    }

    /**
     * Leert den Warenkorb eines Kunden.
     *
     * Die Methode liest die Kunden-ID von der Eingabe, ruft den entsprechenden {@link Kunde} aus dem eShop ab und leert
     * den Warenkorb dieses Kunden. Bei Erfolg wird eine Bestätigung ausgegeben. Bei Fehlern während des Lesevorgangs oder bei
     * Problemen beim Leeren des Warenkorbs werden entsprechende Fehlermeldungen ausgegeben.
     *
     * @throws IOException Falls ein Fehler beim Lesen der Eingabedaten vom Client auftritt.
     * @throws IdNichtVorhandenException Wenn die Kunden-ID nicht in der Datenbank gefunden werden kann.
     */
    private synchronized void warenkorbLeeren(){
        try {
            int kundenID = Integer.parseInt(in.readLine());
            Kunde kunde = eShop.sucheKundeMitNummer(kundenID);
            eShop.warenkorbLeeren(kunde);
            out.println("Erfolgreich: warenkorbLeeren()");
        } catch (IOException e){
            System.err.println("Error beim lesen vom Client bei = warenkorbLeeren()" + e);
            out.println("ERROR 101");
        } catch (IdNichtVorhandenException e) {
            System.err.println("Error beim lesen vom Client bei = warenkorbLeeren()" + e);
            out.println("ERROR 303");
        } catch (IstLeerException e) {
            System.err.println("Error beim lesen vom Client bei = bestandImWarenkorbAendern()" + e);
            out.println("ERROR 406");
        }
    }

    /**
     * Ändert die Menge eines Artikels im Warenkorb eines Kunden.
     *
     * Die Methode liest die Kunden-ID, die Artikelnummer und die neue Menge des Artikels von der Eingabe. Anschließend wird
     * überprüft, ob der Kunde und der Artikel existieren und ob die angegebene Menge gültig ist. Die Methode aktualisiert dann
     * die Menge des Artikels im Warenkorb des Kunden entsprechend der angegebenen Menge. Bei Erfolg wird eine Bestätigung auf der
     * Konsole ausgegeben. Bei Fehlern während des Lesevorgangs oder bei Problemen mit der Menge werden entsprechende Fehlermeldungen
     * ausgegeben.
     *
     * @throws IOException Falls ein Fehler beim Lesen der Eingabedaten vom Client auftritt.
     * @throws IdNichtVorhandenException Wenn die Kunden-ID oder die Artikelnummer in der Datenbank nicht gefunden werden kann.
     * @throws BestandNichtAusreichendException Wenn die angegebene Menge des Artikels nicht verfügbar ist.
     * @throws IstLeerException Wenn der Warenkorb des Kunden leer ist und daher keine Bestandsänderung durchgeführt werden kann.
     * @throws KeinMassengutException Wenn der Artikel kein Massengut ist, jedoch eine Massengut-Bedingung erwartet wird.
     * @throws MinusZahlException Wenn die angegebene Menge eine negative Zahl ist.
     */
    private synchronized void bestandImWarenkorbAendern() {
        try {
            int kundenID = Integer.parseInt(in.readLine());
            Kunde kunde = eShop.sucheKundeMitNummer(kundenID);
            int artikelnummer = Integer.parseInt(in.readLine());
            Artikel artikel = eShop.sucheArtikelMitNummer(artikelnummer);
            int menge = Integer.parseInt(in.readLine());
            eShop.bestandImWarenkorbAendern(kunde, artikel, menge);
            out.println("Erfolgreich: bestandImWarenkorbAendern()");
        } catch (IOException e) {
            System.err.println("Error beim lesen vom Client bei = bestandImWarenkorbAendern()" + e);
            out.println("ERROR 101");
        } catch (IdNichtVorhandenException e) {
            System.err.println("Error beim lesen vom Client bei = bestandImWarenkorbAendern()" + e);
            out.println("ERROR 303");
        } catch (BestandNichtAusreichendException e) {
            System.err.println("Error beim lesen vom Client bei = bestandImWarenkorbAendern()" + e);
            out.println("ERROR 408");
        } catch (IstLeerException e) {
            System.err.println("Error beim lesen vom Client bei = bestandImWarenkorbAendern()" + e);
            out.println("ERROR 406");
        } catch (KeinMassengutException e) {
            System.err.println("Error beim lesen vom Client bei = bestandImWarenkorbAendern()" + e);
            out.println("ERROR 405");
        } catch (MinusZahlException e) {
            System.err.println("Error beim lesen vom Client bei = bestandImWarenkorbAendern()" + e);
            out.println("ERROR 202");
        }
    }

    /**
     * Gibt die Artikel im Warenkorb eines Kunden aus.
     *
     * Die Methode liest die Kunden-ID von der Eingabe, ruft den entsprechenden {@link Kunde} aus dem eShop ab und erhält
     * die Artikel und deren Mengen im Warenkorb des Kunden. Die Methode gibt die Anzahl der Artikel im Warenkorb sowie Details
     * zu jedem Artikel, einschließlich der Artikelnummer, der Artikelbezeichnung, des Artikelbestands und des Artikelpreises, auf
     * der Konsole aus. Bei Fehlern während des Lesevorgangs oder bei Problemen mit dem Warenkorb werden entsprechende Fehlermeldungen
     * ausgegeben.
     *
     * @throws IOException Falls ein Fehler beim Lesen der Eingabedaten vom Client auftritt.
     * @throws IdNichtVorhandenException Wenn die Kunden-ID nicht in der Datenbank gefunden werden kann.
     * @throws IstLeerException Wenn der Warenkorb des Kunden leer ist.
     */
    private synchronized void gibWarenkorbArtikel(){
        //Map<Artikel, Integer> gibWarenkorbAusArtikel = eShop.gibWarenkorbArtikel(kunde);
        try {
            int kundenID = Integer.parseInt(in.readLine());
            Kunde kunde = eShop.sucheKundeMitNummer(kundenID);
            Map<Artikel, Integer> warenKorbInhalt = eShop.gibWarenkorbArtikel(kunde);
            out.println("Erfolgreich: gibWarenkorbArtikel()");
            out.println(warenKorbInhalt.size());
            for(Map.Entry<Artikel, Integer> entry: warenKorbInhalt.entrySet()) {
                out.println(entry.getValue());
                out.println(entry.getKey().getArtikelnummer());
                out.println(entry.getKey().getArtikelbezeichnung());
                out.println(entry.getKey().getArtikelbestand());
                out.println(entry.getKey().getArtikelPreis());
            }
        } catch (IOException  e) {
            System.err.println("Error beim lesen vom Client bei = gibWarenkorbArtikel()" + e);
            out.println("ERROR 101");
        } catch (IdNichtVorhandenException e) {
            System.err.println("Error beim lesen vom Client bei = gibWarenkorbArtikel()" + e);
            out.println("ERROR 303");
        } catch (IstLeerException e) {
            System.err.println("Error beim lesen vom Client bei = gibWarenkorbArtikel()" + e);
            out.println("ERROR 406");
        }

    }
    //public Kunde(String vorname, String nachname, String email, String username, String password, int id, String ort, int plz, String strasse, int strassenNummer) {
    /**
     * Kauft den Warenkorb eines Kunden und gibt die Details der Rechnung aus.
     *
     * Die Methode liest die Kunden-ID von der Eingabe, ruft den entsprechenden {@link Kunde} aus dem eShop ab und löst den
     * Kauf des Warenkorbs für diesen Kunden aus. Es wird eine {@link Rechnung} erstellt, die eine Kopie des Warenkorbs enthält.
     * Die Methode gibt die Anzahl der Artikel im Warenkorb sowie Details zu jedem Artikel, einschließlich der Menge, der Artikelnummer,
     * der Artikelbezeichnung, des Artikelbestands und des Artikelpreises, auf der Konsole aus. Bei Fehlern während des Lesevorgangs
     * oder beim Kauf des Warenkorbs werden entsprechende Fehlermeldungen ausgegeben.
     *
     * @throws IOException Falls ein Fehler beim Lesen der Eingabedaten vom Client auftritt.
     * @throws IdNichtVorhandenException Wenn die Kunden-ID in der Datenbank nicht gefunden werden kann.
     * @throws IstLeerException Wenn der Warenkorb des Kunden leer ist und daher keine Bestellung durchgeführt werden kann.
     * @throws BestandNichtAusreichendException Wenn ein oder mehrere Artikel im Warenkorb nicht genug Bestand haben, um die Bestellung auszuführen.
     */
    private synchronized void warenkorbKaufen(){
        try {
            int kundenID = Integer.parseInt(in.readLine());
            Kunde kunde = eShop.sucheKundeMitNummer(kundenID);
            Rechnung rechnung = eShop.warenkorbKaufen(kunde);
            Map<Artikel, Integer> warenkorbMap = rechnung.getWarenkorbKopieMap();
            out.println("Erfolgreich: warenkorbKaufen()");
            out.println(warenkorbMap.size());
            for (Map.Entry<Artikel, Integer> entry : warenkorbMap.entrySet()) {
                out.println(entry.getValue());
                out.println(entry.getKey().getArtikelnummer());
                out.println(entry.getKey().getArtikelbezeichnung());
                out.println(entry.getKey().getArtikelbestand());
                out.println(entry.getKey().getArtikelPreis());
            }
        } catch (IOException e) {
            System.err.println("Error beim lesen vom Client bei = warenkorbKaufen()" + e);
            out.println("ERROR 101");
        } catch (IdNichtVorhandenException e) {
            System.err.println("Error beim lesen vom Client bei = warenkorbKaufen()" + e);
            out.println("ERROR 303");
        } catch (IstLeerException e) {
            System.err.println("Error beim lesen vom Client bei = warenkorbKaufen()" + e);
            out.println("ERROR 406");
        } catch (BestandNichtAusreichendException e) {
            System.err.println("Error beim lesen vom Client bei = warenkorbKaufen()" + e);
            out.println("ERROR 408");
        }
    }

    /*
        Erfolgreich => Erfolgreich ...;

        IOException => "ERROR 101"

        MinusZahlException => "ERROR 202"

        IdNichtVorhandenException => "ERROR 303"

        DoppelteIdException => "ERROR 304"

        ArtikelnameDoppeltException => "ERROR 404"

        KeinMassengutException => "ERROR 405"

        IstLeerException => "ERROR 406"

        ArtikelnameDoppeltException =>"ERROR 407"

        ArtikelExisitiertNichtException =>"ERROR 408"

        BestandNichtAusreichendException =>"ERROR 408"

        LoginException => "ERROR 807"

        UsernameExistiertException => "ERROR 808"

        EmailExistiertException => "ERROR 809"


        UngueltigeArtikelBezeichnung => /
        FalscheEingabeException => only CUI
        FilterException => only Clientseite
        WertNichtGefundenException => only Clientseite
     */


}
