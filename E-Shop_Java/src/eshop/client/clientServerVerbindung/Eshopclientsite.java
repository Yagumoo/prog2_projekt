package eshop.client.clientServerVerbindung;

import eshop.common.enitities.*;
import eshop.common.exceptions.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.net.*;
import java.io.*;
/**
 * Repräsentiert die Client-Seite für die Kommunikation mit dem E-Shop-Server.
 *
 * Die Klasse `Eshopclientsite` stellt die Verbindung zum E-Shop-Server her und ermöglicht die Kommunikation durch das Senden von
 * Anfragen und das Empfangen von Antworten. Sie verwaltet die Socket-Verbindung und die zugehörigen Input- und Output-Streams für
 * den Datenaustausch zwischen dem Client und dem Server.
 *
 * <p>
 * Beim Erstellen einer Instanz von `Eshopclientsite` wird eine Verbindung zum Server aufgebaut und die Streams für die Kommunikation
 * initialisiert. Der Client kann dann Nachrichten an den Server senden und Antworten empfangen.
 * </p>
 */
public class Eshopclientsite {
    private Socket clientSocket;
    private PrintStream out;
    private BufferedReader in;

    /**
     * Erstellt eine neue Client-Instanz und stellt eine Verbindung zum Server auf dem angegebenen IP und Port her.
     *
     * Der Konstruktor initialisiert den {@link Socket} für die Verbindung zum Server und erstellt die Input- und Output-Streams
     * für die Kommunikation. Der Client sendet eine Begrüßungsnachricht an den Server, um die erfolgreiche Verbindung anzuzeigen.
     *
     * @param ip Die IP-Adresse des Servers, zu dem die Verbindung hergestellt werden soll.
     * @param port Der Port, auf dem der Server Verbindungen akzeptiert.
     * @throws IOException Falls ein Fehler beim Verbindungsaufbau oder beim Erstellen der Streams auftritt.
     */
    public Eshopclientsite(String ip, int port){
        try {
            clientSocket = new Socket(ip, port);
        } catch (IOException e){
            System.err.println("Error beim Verbindungsaufbau" + e);
        }

        try {
            // erstellt Input/Output Streams um Daten vom Server zu lesen/schreiben
            out = new PrintStream(clientSocket.getOutputStream());
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (IOException e) {
            System.err.println("Error beim erstellen von In und Out-Streams");
        }

        out.println("Moin Moin hier ist der Client mit der IP: " + ip +" und Port: "+ port);
    }

    /**
     * Liest eine Liste aller Artikel, einschließlich Massengutartikel, vom Server und gibt eine Map von Artikelnummern auf Artikelobjekte zurück.
     *
     * Diese Methode sendet eine Anfrage an den Server, um alle Artikel und Massengutartikel zu erhalten. Sie verarbeitet die Antwort des Servers,
     * um die Artikelinformationen zu lesen und in eine {@link Map} von Artikelnummern auf {@link Artikel}-Objekte zu speichern. Die Methode
     * liest nacheinander die Details zu normalen Artikeln und Massengutartikeln und erstellt entsprechende {@link Artikel}- oder {@link MassengutArtikel}-Objekte.
     *
     * <p>
     * Der Rückgabewert ist eine {@link Map}, die Artikelnummern auf die entsprechenden {@link Artikel}-Objekte abbildet. Die Map enthält sowohl
     * normale {@link Artikel}-Objekte als auch {@link MassengutArtikel}-Objekte, wobei {@link MassengutArtikel} von {@link Artikel} erbt.
     * </p>
     *
     * <p>
     * Die Methode geht davon aus, dass der Server in einem festgelegten Format antwortet. Bei der Kommunikation mit dem Server können folgende
     * Formate erwartet werden:
     * <ul>
     *   <li>Erste Anfrage: "gibAlleArtikel"</li>
     *   <li>Antwort auf Anfrage enthält die Anzahl der Artikel, gefolgt von Details für jeden Artikel.</li>
     *   <li>Weitere Anfrage: "gibAlleMassengutartikel"</li>
     *   <li>Antwort auf Anfrage enthält die Anzahl der Massengutartikel, gefolgt von Details für jeden Massengutartikel.</li>
     * </ul>
     * </p>
     *
     * <p>
     * Bei Fehlern während der Kommunikation mit dem Server wird eine Fehlermeldung ausgegeben. Diese Fehler sind hauptsächlich IO-Fehler, die
     * beim Lesen der Daten vom Server auftreten können.
     * </p>
     *
     * @return Eine {@link Map} von {@link Integer} (Artikelnummer) auf {@link Artikel} (Artikelobjekt), die alle normalen und Massengutartikel enthält.
     * @throws IOException Falls ein Fehler beim Lesen der Daten vom Server auftritt.
     */
    public Map<Integer, Artikel> gibAlleArtikel() {
        Map<Integer, Artikel> alleArtikel = new HashMap<Integer, Artikel>();
        out.println("gibAlleArtikel");
        try {

            String rückfrage = in.readLine();
            switch (rückfrage) {
                //TODO: machen
                default:
                    System.out.println("Erfolgreich gibAlleArtikel()");
            }

            int anzahlArtikelMap = Integer.parseInt(in.readLine());
            for(int i = 0; i < anzahlArtikelMap; i++){
                int nummer = Integer.parseInt(in.readLine());
                String bezeichnung = in.readLine();
                int bestand = Integer.parseInt(in.readLine());
                double preis = Double.parseDouble(in.readLine());
                Artikel artikel = new Artikel(nummer, bezeichnung, bestand, preis);
                alleArtikel.put(nummer, artikel);
            }
        } catch (IOException e){
            System.err.println("Error beim Artikel erstellen" + e);
        }
        out.println("gibAlleMassengutartikel");
        try {
            String rückfrage = in.readLine();
            switch (rückfrage) {
                //TODO: machen
                default:
                    System.out.println("Erfolgreich gibAlleArtikel()");
            }
            int anzahlArtikelMap = Integer.parseInt(in.readLine());
            for(int i = 0; i < anzahlArtikelMap; i++) {
                int nummer = Integer.parseInt(in.readLine());
                String bezeichnung = in.readLine();
                int bestand = Integer.parseInt(in.readLine());
                double preis = Double.parseDouble(in.readLine());
                int massengutAnzahl = Integer.parseInt(in.readLine());
                MassengutArtikel massengutArtikel = new MassengutArtikel(nummer, bezeichnung, bestand, preis, massengutAnzahl);
                alleArtikel.put(nummer, massengutArtikel);
            }
        } catch (IOException e){
            System.err.println("Error beim lesen vom Server bei = gibAlleMassengutartikel" + e);
        }

        return alleArtikel;
    }

    /**
     * Fordert vom Server eine Liste aller Mitarbeiter an und gibt eine Map von Mitarbeiter-IDs auf {@link Mitarbeiter}-Objekte zurück.
     *
     * Diese Methode sendet eine Anfrage an den Server, um alle Mitarbeiterinformationen zu erhalten. Sie verarbeitet die Serverantwort,
     * um die Details der Mitarbeiter zu lesen und {@link Mitarbeiter}-Objekte zu erstellen. Diese Objekte werden in einer {@link Map} gespeichert,
     * die Mitarbeiter-IDs auf {@link Mitarbeiter}-Objekte abbildet.
     *
     * <p>
     * Der Rückgabewert ist eine {@link Map}, die Mitarbeiter-IDs auf die entsprechenden {@link Mitarbeiter}-Objekte abbildet. Die Map enthält
     * alle Mitarbeiter, die auf dem Server vorhanden sind.
     * </p>
     *
     * <p>
     * Die Methode geht davon aus, dass der Server in einem bestimmten Format antwortet, das die Anzahl der Mitarbeiter sowie die Details
     * zu jedem Mitarbeiter umfasst. Die Details umfassen ID, Vorname, Nachname, Email, Username und Passwort.
     * </p>
     *
     * <p>
     * Bei Fehlern während der Kommunikation mit dem Server wird eine Fehlermeldung ausgegeben. Diese Fehler sind hauptsächlich IO-Fehler, die
     * beim Lesen der Daten vom Server auftreten können.
     * </p>
     *
     * @return Eine {@link Map} von {@link Integer} (Mitarbeiter-ID) auf {@link Mitarbeiter} (Mitarbeiterobjekt), die alle Mitarbeiter enthält.
     * @throws IOException Falls ein Fehler beim Lesen der Daten vom Server auftritt.
     */
    public Map<Integer, Mitarbeiter> gibAlleMitarbeiter() {
        Map<Integer, Mitarbeiter> alleMitarbeiter = new HashMap<Integer, Mitarbeiter>();
        out.println("gibAlleMitarbeiter");
        try {
            String rückfrage = in.readLine();
            switch (rückfrage) {
                //TODO: machen
                default:
                    System.out.println("Erfolgreich gibAlleMitarbeiter()");
            }
            int anzahlMitarbeiterMap = Integer.parseInt(in.readLine());
            for(int i = 0; i < anzahlMitarbeiterMap; i++){
                int id = Integer.parseInt(in.readLine());
                String vorname = in.readLine();
                String nachname = in.readLine();
                String email = in.readLine();
                String username = in.readLine();
                String passwort = in.readLine();
                //TODO: Richtige übergabe der ID
                Mitarbeiter mitarbeiter = new Mitarbeiter(vorname, nachname, email, username, passwort, id);
                alleMitarbeiter.put(mitarbeiter.getId(), mitarbeiter);
            }
        } catch (IOException e){
            System.err.println("Error beim lesen vom Server bei = gibAlleMitarbeiter" + e);
        }
        return alleMitarbeiter;
    }

    /**
     * Sendet eine Anfrage an den Server, um einen neuen Artikel hinzuzufügen und verarbeitet die Antwort des Servers.
     *
     * Diese Methode sendet die Details eines neuen {@link Artikel}-Objekts zusammen mit der ID des {@link Person}-Objekts, das den Artikel hinzufügt, an den Server.
     * Die Methode verarbeitet die Antwort des Servers, um zu überprüfen, ob der Artikel erfolgreich hinzugefügt wurde oder ob Fehler aufgetreten sind.
     *
     * <p>
     * Der Artikel wird nur hinzugefügt, wenn die Serverantwort anzeigt, dass keine Fehler aufgetreten sind. Andernfalls werden die entsprechenden
     * Fehler ausgelöst, die durch den Server zurückgegeben werden.
     * </p>
     *
     * <p>
     * Die Methode sendet folgende Informationen an den Server:
     * <ul>
     *   <li>ID des Mitarbeiters, der den Artikel hinzufügt.</li>
     *   <li>Artikelnummer des neuen Artikels.</li>
     *   <li>Bezeichnung des neuen Artikels.</li>
     *   <li>Bestand des neuen Artikels.</li>
     *   <li>Preis des neuen Artikels.</li>
     * </ul>
     * Die Methode verarbeitet dann die Rückmeldung des Servers, um mögliche Fehler zu identifizieren und entsprechend zu handeln.
     * </p>
     *
     * <p>
     * Bei folgenden Fehlern wird eine entsprechende Ausnahme ausgelöst:
     * <ul>
     *   <li>{@link DoppelteIdException} - Wenn die Artikelnummer bereits vergeben ist (Fehlercode "ERROR 304").</li>
     *   <li>{@link MinusZahlException} - Wenn der Artikelbestand negativ ist (Fehlercode "ERROR 202").</li>
     *   <li>{@link KeinMassengutException} - Wenn ein Massengut-Artikel erstellt werden soll, aber der Bestand nicht für Massengüter geeignet ist (Fehlercode "ERROR 405").</li>
     *   <li>{@link ArtikelnameDoppeltException} - Wenn die Bezeichnung des Artikels bereits existiert (Fehlercode "ERROR 407").</li>
     *   <li>{@link IdNichtVorhandenException} - Wenn die Mitarbeiter-ID nicht vorhanden ist (Fehlercode "ERROR 303").</li>
     * </ul>
     * </p>
     *
     * <p>
     * Bei IO-Fehlern während der Kommunikation mit dem Server wird eine Fehlermeldung ausgegeben.
     * </p>
     *
     * @param mitarbeiter Ein {@link Person}-Objekt, das den Artikel hinzufügt. Es wird die ID des Mitarbeiters verwendet, um die Anfrage zu identifizieren.
     * @param artikel Ein {@link Artikel}-Objekt, das die Details des hinzuzufügenden Artikels enthält.
     * @throws DoppelteIdException Wenn die Artikelnummer bereits vorhanden ist.
     * @throws MinusZahlException Wenn der Artikelbestand negativ ist.
     * @throws KeinMassengutException
     * * @throws ArtikelnameDoppeltException Wenn die Bezeichnung des Artikels bereits existiert.
     *  * @throws IdNichtVorhandenException Wenn die Mitarbeiter-ID nicht vorhanden ist.
     *  * @throws IOException Wenn ein Fehler beim Lesen der Antwort vom Server auftritt.
     *  */

    public void addArtikel(Person mitarbeiter, Artikel artikel) throws DoppelteIdException, MinusZahlException, KeinMassengutException, ArtikelnameDoppeltException, IdNichtVorhandenException {
        out.println("addArtikel");
        out.println(mitarbeiter.getId());
        out.println(artikel.getArtikelnummer());
        out.println(artikel.getArtikelbezeichnung());
        out.println(artikel.getArtikelbestand());
        out.println(artikel.getArtikelPreis());

        try{
            String rückfrage = in.readLine();
            switch (rückfrage) {
                case "ERROR 304":
                    throw new DoppelteIdException(artikel.getArtikelnummer());
                case "ERROR 202":
                    throw new MinusZahlException();
                case "ERROR 405":
                    throw new KeinMassengutException(artikel.getArtikelbestand());
                case "ERROR 407":
                    throw new ArtikelnameDoppeltException(artikel.getArtikelbezeichnung());
                case "ERROR 303":
                    throw new IdNichtVorhandenException(artikel.getArtikelnummer());
                default:
                    System.out.println("Erfolgreich addArtikel()");
            }
            //TODO: Fehler abfangen
        } catch (IOException e){
            System.err.println("Fehler beim lesen vom Server = addArtikel" + e);
        }
    }

    /**
     * Sendet eine Anfrage an den Server, um einen neuen Massengut-Artikel hinzuzufügen und verarbeitet die Antwort des Servers.
     *
     * Diese Methode sendet die Details eines neuen {@link MassengutArtikel}-Objekts zusammen mit der ID des {@link Person}-Objekts, das den Artikel hinzufügt, an den Server.
     * Die Methode verarbeitet die Antwort des Servers, um zu überprüfen, ob der Massengut-Artikel erfolgreich hinzugefügt wurde oder ob Fehler aufgetreten sind.
     *
     * <p>
     * Der Massengut-Artikel wird nur hinzugefügt, wenn die Serverantwort anzeigt, dass keine Fehler aufgetreten sind. Andernfalls werden die entsprechenden
     * Fehler ausgelöst, die durch den Server zurückgegeben werden.
     * </p>
     *
     * <p>
     * Die Methode sendet folgende Informationen an den Server:
     * <ul>
     *   <li>ID des Mitarbeiters, der den Massengut-Artikel hinzufügt.</li>
     *   <li>Artikelnummer des neuen Massengut-Artikels.</li>
     *   <li>Bezeichnung des neuen Massengut-Artikels.</li>
     *   <li>Bestand des neuen Massengut-Artikels.</li>
     *   <li>Preis des neuen Massengut-Artikels.</li>
     *   <li>Anzahl der Massengut-Einheiten.</li>
     * </ul>
     * Die Methode verarbeitet dann die Rückmeldung des Servers, um mögliche Fehler zu identifizieren und entsprechend zu handeln.
     * </p>
     *
     * <p>
     * Bei folgenden Fehlern wird eine entsprechende Ausnahme ausgelöst:
     * <ul>
     *   <li>{@link DoppelteIdException} - Wenn die Artikelnummer bereits vergeben ist (Fehlercode "ERROR 304").</li>
     *   <li>{@link MinusZahlException} - Wenn der Artikelbestand negativ ist (Fehlercode "ERROR 202").</li>
     *   <li>{@link KeinMassengutException} - Wenn der Artikel nicht als Massengut-Artikel erstellt werden kann (Fehlercode "ERROR 405").</li>
     *   <li>{@link ArtikelnameDoppeltException} - Wenn die Bezeichnung des Massengut-Artikels bereits existiert (Fehlercode "ERROR 404").</li>
     *   <li>{@link IdNichtVorhandenException} - Wenn die Mitarbeiter-ID nicht vorhanden ist (Fehlercode "ERROR 303").</li>
     * </ul>
     * </p>
     *
     * <p>
     * Bei IO-Fehlern während der Kommunikation mit dem Server wird eine Fehlermeldung ausgegeben.
     * </p>
     *
     * @param mitarbeiter Ein {@link Person}-Objekt, das den Massengut-Artikel hinzufügt. Es wird die ID des Mitarbeiters verwendet, um die Anfrage zu identifizieren.
     * @param massengutArtikel Ein {@link MassengutArtikel}-Objekt, das die Details des hinzuzufügenden Massengut-Artikels enthält.
     * @throws DoppelteIdException Wenn die Artikelnummer bereits vorhanden ist.
     * @throws MinusZahlException Wenn der Artikelbestand negativ ist.
     * @throws KeinMassengutException Wenn der Artikel nicht als Massengut-Artikel erstellt werden kann.
     * @throws ArtikelnameDoppeltException Wenn die Bezeichnung des Massengut-Artikels bereits existiert.
     * @throws IdNichtVorhandenException Wenn die Mitarbeiter-ID nicht vorhanden ist.
     * @throws IOException Wenn ein Fehler beim Lesen der Antwort vom Server auftritt.
     */
    public void addMassengutartikel(Person mitarbeiter, MassengutArtikel massengutArtikel) throws DoppelteIdException, MinusZahlException, KeinMassengutException, ArtikelnameDoppeltException, IdNichtVorhandenException {
        out.println("addMassengutartikel");
        out.println(mitarbeiter.getId());
        out.println(massengutArtikel.getArtikelnummer());
        out.println(massengutArtikel.getArtikelbezeichnung());
        out.println(massengutArtikel.getArtikelbestand());
        out.println(massengutArtikel.getArtikelPreis());
        out.println(massengutArtikel.getAnzahlMassengut());

        try{
            String rückfrage = in.readLine();
            switch (rückfrage) {
                case "ERROR 304":
                    throw new DoppelteIdException(massengutArtikel.getArtikelnummer());
                case "ERROR 202":
                    throw new MinusZahlException();
                case "ERROR 405":
                    throw new KeinMassengutException(massengutArtikel.getAnzahlMassengut());
                case "ERROR 404":
                    throw new ArtikelnameDoppeltException(massengutArtikel.getArtikelbezeichnung());
                case "ERROR 303":
                    throw new IdNichtVorhandenException(massengutArtikel.getAnzahlMassengut());
                default:
                    System.out.println("Erfolgreich addMassengutartikel()");
            }
            //TODO: Fehler abfangen
        } catch (IOException e){
            System.err.println("Fehler beim lesen vom Server = addMassengutartikel" + e);
        }
    }

    /**
     * Fordert vom Server eine Liste von {@link Ereignis}-Objekten an und gibt diese als {@link List} zurück.
     *
     * Diese Methode sendet eine Anfrage an den Server, um eine Liste aller Ereignisse zu erhalten. Die Methode liest die Daten vom Server,
     * erstellt {@link Ereignis}-Objekte und fügt diese einer Liste hinzu. Die Liste wird dann zurückgegeben.
     *
     * <p>
     * Die Methode geht davon aus, dass der Server die Ereignisse im folgenden Format zurückgibt:
     * <ul>
     *   <li>Datum des Ereignisses (im Format "yyyy-MM-dd").</li>
     *   <li>Bezeichnung des Artikels.</li>
     *   <li>Anzahl der Artikel.</li>
     *   <li>Typ der Person ("m" für Mitarbeiter, sonst Kunde).</li>
     *   <li>Falls Mitarbeiter: ID, Vorname, Nachname, Email, Username, Passwort.</li>
     *   <li>Falls Kunde: ID, Vorname, Nachname, Email, Username, Passwort, Ort, PLZ, Straße, Hausnummer.</li>
     *   <li>Ereignis-Typ (als {@link Ereignis.EreignisTyp}).</li>
     * </ul>
     * </p>
     *
     * <p>
     * Bei IO-Fehlern während der Kommunikation mit dem Server wird eine Fehlermeldung ausgegeben.
     * </p>
     *
     * @return Eine {@link List} von {@link Ereignis}-Objekten, die alle Ereignisse enthält.
     * @throws IOException Falls ein Fehler beim Lesen der Daten vom Server auftritt.
     */
    public List<Ereignis> getEreignisListe() {
        List<Ereignis> ereignisListe = new ArrayList<>();
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH);
        Person kundeOderMitarbeiter;
        out.println("getEreignisListe");
        try {
            String rückfrage = in.readLine();
            switch (rückfrage) {
                //TODO: abfangen
                default:
                    System.out.println("Erfolgreich getEreignisListe()");
            }
            int listengröße = Integer.parseInt(in.readLine());
            for(int i = 0; i < listengröße; i++) {
                //TODO: Datum kann nicht richtig übergeben werden
                LocalDate datum = LocalDate.parse(in.readLine(), dateFormat);
                String artikelbezeichnung = in.readLine();
                int anzahl = Integer.parseInt(in.readLine());
                String kOderM = in.readLine();
                if(kOderM.equals("m")){
                    int id = Integer.parseInt(in.readLine());
                    String vorname = in.readLine();
                    String nachname = in.readLine();
                    String email = in.readLine();
                    String username = in.readLine();
                    String passwort = in.readLine();
                    kundeOderMitarbeiter = new Mitarbeiter(vorname, nachname, email, username, passwort, id);
                } else{
                    int id = Integer.parseInt(in.readLine());
                    String vorname = in.readLine();
                    String nachname = in.readLine();
                    String email = in.readLine();
                    String username = in.readLine();
                    String passwort = in.readLine();
                    String ort = in.readLine();
                    int plz = Integer.parseInt(in.readLine());
                    String strasse = in.readLine();
                    int strassenNummer = Integer.parseInt(in.readLine());
                    kundeOderMitarbeiter = new Kunde(vorname, nachname, email, username, passwort, id, ort, plz, strasse, strassenNummer);
                }
                Ereignis.EreignisTyp typ = Ereignis.EreignisTyp.valueOf(in.readLine());
                Ereignis ereignis = new Ereignis(datum, artikelbezeichnung, anzahl, kundeOderMitarbeiter, typ);
                ereignisListe.add(ereignis);
            }
            return ereignisListe;
        } catch (IOException e) {
            System.out.println("Fehler beim lesen der Ereignisliste = " + e);
        }
        return null;
    }

    /**
     * Sendet eine Anfrage an den Server, um einen neuen Mitarbeiter hinzuzufügen und verarbeitet die Serverantwort.
     *
     * Diese Methode überträgt die Details eines neuen {@link Mitarbeiter}-Objekts an den Server und behandelt mögliche Fehler, die durch den Server zurückgegeben werden.
     *
     * <p>
     * Die Methode sendet folgende Informationen an den Server:
     * <ul>
     *   <li>ID des Mitarbeiters, der den neuen Mitarbeiter hinzufügt.</li>
     *   <li>Vorname des neuen Mitarbeiters.</li>
     *   <li>Nachname des neuen Mitarbeiters.</li>
     *   <li>Email des neuen Mitarbeiters.</li>
     *   <li>Username des neuen Mitarbeiters.</li>
     *   <li>Passwort des neuen Mitarbeiters.</li>
     * </ul>
     * </p>
     *
     * <p>
     * Bei folgenden Fehlern wird eine entsprechende Ausnahme ausgelöst:
     * <ul>
     *   <li>{@link DoppelteIdException} - Wenn die ID bereits vergeben ist (Fehlercode "ERROR 304").</li>
     *   <li>{@link UsernameExistiertException} - Wenn der Username bereits existiert (Fehlercode "ERROR 808").</li>
     *   <li>{@link EmailExistiertException} - Wenn die Email bereits existiert (Fehlercode "ERROR 809").</li>
     *   <li>{@link IdNichtVorhandenException} - Wenn die Mitarbeiter-ID nicht vorhanden ist (Fehlercode "ERROR 303").</li>
     * </ul>
     * </p>
     *
     * @param mitarbeiter Der {@link Person}-Objekt, der den neuen Mitarbeiter hinzufügt. Die ID des Mitarbeiters wird zur Identifizierung verwendet.
     * @param vorname Vorname des neuen Mitarbeiters.
     * @param nachname Nachname des neuen Mitarbeiters.
     * @param email Email-Adresse des neuen Mitarbeiters.
     * @param username Username des neuen Mitarbeiters.
     * @param password Passwort des neuen Mitarbeiters.
     * @throws DoppelteIdException Wenn die ID bereits existiert.
     * @throws UsernameExistiertException Wenn der Username bereits vergeben ist.
     * @throws EmailExistiertException Wenn die Email-Adresse bereits vergeben ist.
     * @throws IdNichtVorhandenException Wenn die Mitarbeiter-ID nicht gefunden wurde.
     * @throws IOException Wenn ein Fehler beim Lesen der Antwort vom Server auftritt.
     */
    public void addMitarbeiter(Person mitarbeiter, String vorname, String nachname, String email, String username, String password) throws DoppelteIdException, UsernameExistiertException, EmailExistiertException, IdNichtVorhandenException {
        out.println("addMitarbeiter");
        out.println(mitarbeiter.getId());
        out.println(vorname);
        out.println(nachname);
        out.println(email);
        out.println(username);
        out.println(password);
        try{
            String rückfrage = in.readLine();
            switch (rückfrage) {
                case "ERROR 304":
                    throw new DoppelteIdException(mitarbeiter.getId());
                case "ERROR 808":
                    throw new UsernameExistiertException(username);
                case "ERROR 809":
                    throw new EmailExistiertException(email);
                case "ERROR 303":
                    throw new IdNichtVorhandenException(mitarbeiter.getId());
                default:
                    System.out.println("Erfolgreich addMitarbeiter()");
            }
            //TODO: Fehler abfangen
        } catch (IOException e){
            System.err.println("Fehler beim lesen vom Server = addMitarbeiter" + e);
        }
    }

    /**
     * Sendet eine Anfrage an den Server, um einen neuen Kunden hinzuzufügen und verarbeitet die Serverantwort.
     *
     * Diese Methode überträgt die Details eines neuen {@link Kunde}-Objekts an den Server und behandelt mögliche Fehler, die durch den Server zurückgegeben werden.
     *
     * <p>
     * Die Methode sendet folgende Informationen an den Server:
     * <ul>
     *   <li>Vorname des neuen Kunden.</li>
     *   <li>Nachname des neuen Kunden.</li>
     *   <li>Email-Adresse des neuen Kunden.</li>
     *   <li>Username des neuen Kunden.</li>
     *   <li>Passwort des neuen Kunden.</li>
     *   <li>Ort des neuen Kunden.</li>
     *   <li>Postleitzahl des neuen Kunden.</li>
     *   <li>Straße des neuen Kunden.</li>
     *   <li>Hausnummer des neuen Kunden.</li>
     * </ul>
     * </p>
     *
     * <p>
     * Bei folgenden Fehlern wird eine entsprechende Ausnahme ausgelöst:
     * <ul>
     *   <li>{@link DoppelteIdException} - Wenn die ID bereits vergeben ist (Fehlercode "ERROR 304").</li>
     *   <li>{@link UsernameExistiertException} - Wenn der Username bereits existiert (Fehlercode "ERROR 808").</li>
     *   <li>{@link EmailExistiertException} - Wenn die Email-Adresse bereits existiert (Fehlercode "ERROR 809").</li>
     * </ul>
     * </p>
     *
     * @param vorname Vorname des neuen Kunden.
     * @param nachname Nachname des neuen Kunden.
     * @param email Email-Adresse des neuen Kunden.
     * @param username Username des neuen Kunden.
     * @param password Passwort des neuen Kunden.
     * @param ort Ort des neuen Kunden.
     * @param plz Postleitzahl des neuen Kunden.
     * @param strasse Straße des neuen Kunden.
     * @param strassenNummer Hausnummer des neuen Kunden.
     * @throws DoppelteIdException Wenn die ID bereits existiert (Fehlercode "ERROR 304").
     * @throws UsernameExistiertException Wenn der Username bereits vergeben ist (Fehlercode "ERROR 808").
     * @throws EmailExistiertException Wenn die Email-Adresse bereits vergeben ist (Fehlercode "ERROR 809").
     * @throws IOException Wenn ein Fehler beim Lesen der Antwort vom Server auftritt.
     */
    public void addKunde(String vorname, String nachname, String email, String username, String password, String ort, int plz, String strasse, int strassenNummer) throws DoppelteIdException, UsernameExistiertException, EmailExistiertException {
        out.println("addKunde");
        out.println(vorname);
        out.println(nachname);
        out.println(email);
        out.println(username);
        out.println(password);
        out.println(ort);
        out.println(plz);
        out.println(strasse);
        out.println(strassenNummer);

        try{
            String rückfrage = in.readLine();
            switch (rückfrage) {
                case "ERROR 304":
                    //throw new DoppelteIdException(kunde.getId());
                case "ERROR 808":
                    throw new UsernameExistiertException(username);
                case "ERROR 809":
                    throw new EmailExistiertException(email);
                default:
                    System.out.println("Erfolgreich addKunde()");
            }
            //TODO: Fehler abfangen
        } catch (IOException e){
            System.err.println("Fehler beim lesen vom Server = addKunde" + e);
        }

    }

    /**
     * Meldet einen Mitarbeiter beim Server an und gibt ein {@link Mitarbeiter}-Objekt zurück, wenn die Anmeldedaten korrekt sind.
     *
     * <p>
     * Die Methode sendet den Benutzernamen oder die E-Mail-Adresse und das Passwort an den Server und verarbeitet die Serverantwort.
     * </p>
     *
     * <p>
     * Bei folgendem Fehler wird eine {@link LoginException} ausgelöst:
     * <ul>
     *   <li>{@link LoginException} - Wenn der Benutzername oder die E-Mail-Adresse und das Passwort nicht übereinstimmen (Fehlercode "ERROR 807").</li>
     * </ul>
     * </p>
     *
     * @param usernameOrEmail Der Benutzername oder die E-Mail-Adresse des Mitarbeiters.
     * @param password Das Passwort des Mitarbeiters.
     * @return Ein {@link Mitarbeiter}-Objekt, wenn die Anmeldedaten korrekt sind.
     * @throws LoginException Wenn die Anmeldedaten falsch sind (Fehlercode "ERROR 807").
     * @throws IOException Wenn ein Fehler beim Lesen der Antwort vom Server auftritt.
     */
    public Mitarbeiter loginMitarbeiter(String usernameOrEmail, String password) throws LoginException {
        out.println("loginMitarbeiter");
        out.println(usernameOrEmail);
        out.println(password);

        try{
            String rückfrage = in.readLine();
            switch (rückfrage) {
                case "ERROR 807":
                    throw new LoginException();
                default:
                    System.out.println("Erfolgreich loginMitarbeiter()");
            }
            //TODO: Fehler abfangen
            int id = Integer.parseInt(in.readLine());
            String vorname = in.readLine();
            String nachname = in.readLine();
            String email = in.readLine();
            String username = in.readLine();
            String passwort = in.readLine();
            //TODO: Richtige übergabe der ID
            Mitarbeiter mitarbeiter = new Mitarbeiter(vorname, nachname, email, username, passwort, id);
            return mitarbeiter;
        } catch (IOException e){
            System.err.println("Fehler beim lesen vom Server = loginMitarbeiter" + e);
        }
        //TODO: Return Statement
        return null;
    }

    /**
     * Meldet einen Kunden beim Server an und gibt ein {@link Kunde}-Objekt zurück, wenn die Anmeldedaten korrekt sind.
     *
     * <p>
     * Die Methode sendet den Benutzernamen oder die E-Mail-Adresse und das Passwort an den Server und verarbeitet die Serverantwort.
     * </p>
     *
     * <p>
     * Bei folgendem Fehler wird eine {@link LoginException} ausgelöst:
     * <ul>
     *   <li>{@link LoginException} - Wenn der Benutzername oder die E-Mail-Adresse und das Passwort nicht übereinstimmen (Fehlercode "ERROR 807").</li>
     * </ul>
     * </p>
     *
     * @param usernameOrEmail Der Benutzername oder die E-Mail-Adresse des Kunden.
     * @param password Das Passwort des Kunden.
     * @return Ein {@link Kunde}-Objekt, wenn die Anmeldedaten korrekt sind.
     * @throws LoginException Wenn die Anmeldedaten falsch sind (Fehlercode "ERROR 807").
     * @throws IOException Wenn ein Fehler beim Lesen der Antwort vom Server auftritt.
     */
    public Kunde loginKunde(String usernameOrEmail, String password) throws LoginException {
        out.println("loginKunde");

        out.println(usernameOrEmail);
        out.println(password);

        try{
            String rückfrage = in.readLine();
            switch (rückfrage) {
                case "ERROR 807":
                    throw new LoginException();
                    //TODO: Fehler abfangen
                default:
                    System.out.println("Erfolgreich loginKunde()");
            }

            int id = Integer.parseInt(in.readLine());
            String vorname = in.readLine();
            String nachname = in.readLine();
            String email = in.readLine();
            String username = in.readLine();
            String passwort = in.readLine();
            String ort = in.readLine();
            int plz = Integer.parseInt(in.readLine());
            String strasse = in.readLine();
            int strassenNummer = Integer.parseInt(in.readLine());
            Kunde kunde = new Kunde(vorname, nachname, email, username, passwort, id, ort, plz, strasse, strassenNummer);

            return kunde;
        } catch (IOException e){
            System.err.println("Fehler beim lesen vom Server = loginKunde" + e);
        }
        //TODO: Return Statement
        return null;
    }

    /**
     * Sucht einen Artikel auf dem Server anhand der Artikelnummer und gibt das {@link Artikel}-Objekt zurück.
     *
     * <p>
     * Die Methode sendet die Artikelnummer an den Server und verarbeitet die Serverantwort. Bei Erfolg wird ein {@link Artikel}-Objekt
     * mit den Details des Artikels zurückgegeben. Im Fehlerfall wird eine {@link IdNichtVorhandenException} ausgelöst.
     * </p>
     *
     * <p>
     * Bei folgendem Fehler wird eine {@link IdNichtVorhandenException} ausgelöst:
     * <ul>
     *   <li>{@link IdNichtVorhandenException} - Wenn die Artikelnummer nicht existiert (Fehlercode "ERROR 303").</li>
     * </ul>
     * </p>
     *
     * @param artikelnummer Die Artikelnummer des gesuchten Artikels.
     * @return Ein {@link Artikel}-Objekt, wenn der Artikel gefunden wurde.
     * @throws IdNichtVorhandenException Wenn die Artikelnummer nicht existiert (Fehlercode "ERROR 303").
     * @throws IOException Wenn ein Fehler beim Lesen der Antwort vom Server auftritt.
     */
    public Artikel sucheArtikelMitNummer(int artikelnummer) throws IdNichtVorhandenException {
       out.println("sucheArtikelMitNummer");
       out.println(artikelnummer);

        try{
            String rückfrage = in.readLine();
            switch (rückfrage) {
                case "ERROR 303":
                    throw new IdNichtVorhandenException(artikelnummer);
                default:
                    System.out.println("Erfolgreich sucheArtikelMitNummer()");

            }
            //TODO: Fehler abfangen
            String mOdera = in.readLine();
            if (mOdera.equals("m")){
                int nummer = Integer.parseInt(in.readLine());
                String bezeichnung = in.readLine();
                int bestand = Integer.parseInt(in.readLine());
                double preis = Double.parseDouble(in.readLine());
                int massgut = Integer.parseInt(in.readLine());
                MassengutArtikel massengutArtikel = new MassengutArtikel(nummer, bezeichnung, bestand, preis, massgut);
            } else {
                int nummer = Integer.parseInt(in.readLine());
                String bezeichnung = in.readLine();
                int bestand = Integer.parseInt(in.readLine());
                double preis = Double.parseDouble(in.readLine());
                Artikel artikel = new Artikel(nummer, bezeichnung, bestand, preis);
                return artikel;
            }
        } catch (IOException e){
            System.err.println("Fehler beim lesen vom Server = aendereArtikelBestand" + e);
        }
        //TODO: Return Statement
        return null;
    }

    /**
     * Löscht einen Artikel auf dem Server anhand der Artikelnummer.
     *
     * <p>
     * Die Methode sendet die Artikelnummer und die ID des Mitarbeiters an den Server. Bei Erfolg wird die Nachricht "Erfolgreich loescheArtikel()"
     * ausgegeben. Bei Fehlern wird eine {@link IdNichtVorhandenException} ausgelöst oder es wird eine Fehlermeldung in der Konsole ausgegeben.
     * </p>
     *
     * <p>
     * Bei folgendem Fehler wird eine {@link IdNichtVorhandenException} ausgelöst:
     * <ul>
     *   <li>{@link IdNichtVorhandenException} - Wenn die Artikelnummer nicht existiert (Fehlercode "ERROR 303").</li>
     * </ul>
     * </p>
     *
     * @param mitarbeiter Der {@link Person}-Typ Mitarbeiter, der den Artikel löschen möchte.
     * @param artikelnummer Die Artikelnummer des zu löschenden Artikels.
     * @throws IdNichtVorhandenException Wenn die Artikelnummer nicht existiert (Fehlercode "ERROR 303").
     */
    public void loescheArtikel(Person mitarbeiter, int artikelnummer) throws IdNichtVorhandenException {
        out.println("loescheArtikel");

        out.println(mitarbeiter.getId());
        out.println(artikelnummer);

        try{
            String rückfrage = in.readLine();
            switch (rückfrage) {
                case "ERROR 303":
                    throw new IdNichtVorhandenException(artikelnummer);
                default:
                    System.out.println("Erfolgreich loescheArtikel()");
            }
            //TODO: Fehler abfangen
        } catch (IOException e){
            System.err.println("Fehler beim lesen vom Server = loescheArtikel" + e);
        }
    }

    /**
     * Ändert den Bestand eines Artikels auf dem Server.
     *
     * <p>
     * Die Methode sendet die Artikelnummer und den neuen Bestand an den Server. Bei Erfolg wird die Nachricht "Erfolgreich aendereArtikelBestand()"
     * ausgegeben. Bei Fehlern wird eine entsprechende Ausnahme ausgelöst oder es wird eine Fehlermeldung in der Konsole ausgegeben.
     * </p>
     *
     * <p>
     * Folgende Fehler werden abgefangen:
     * <ul>
     *   <li>{@link IdNichtVorhandenException} - Wenn die Artikelnummer nicht existiert (Fehlercode "ERROR 303").</li>
     *   <li>{@link KeinMassengutException} - Wenn der Artikel kein Massengut ist, aber die Menge geändert werden soll (Fehlercode "ERROR 405").</li>
     *   <li>{@link MinusZahlException} - Wenn die neue Menge negativ ist (Fehlercode "ERROR 202").</li>
     * </ul>
     * </p>
     *
     * @param mitarbeiter Der {@link Person}-Typ Mitarbeiter, der den Artikelbestand ändern möchte.
     * @param artikelnummer Die Artikelnummer des zu ändernden Artikels.
     * @param neuerBestand Der neue Bestand des Artikels.
     * @throws IdNichtVorhandenException Wenn die Artikelnummer nicht existiert (Fehlercode "ERROR 303").
     * @throws KeinMassengutException Wenn der Artikel kein Massengut ist, aber die Menge geändert werden soll (Fehlercode "ERROR 405").
     * @throws MinusZahlException Wenn die neue Menge negativ ist (Fehlercode "ERROR 202").
     */
    public void aendereArtikelBestand(Person mitarbeiter, int artikelnummer, int neuerBestand) throws IdNichtVorhandenException, KeinMassengutException, MinusZahlException {
        out.println("aendereArtikelBestand");
        out.println(mitarbeiter.getId());
        out.println(artikelnummer);
        out.println(neuerBestand);
        try{
            String rückfrage = in.readLine();
            switch (rückfrage) {
                case "ERROR 303":
                    throw new IdNichtVorhandenException(artikelnummer);
                case "ERROR 405":
                    throw new KeinMassengutException(neuerBestand);
                case "ERROR 202":
                    throw new MinusZahlException();
                default:
                    System.out.println("Erfolgreich aendereArtikelBestand()");
            }
            //TODO: Fehler abfangen
        } catch (IOException e){
            System.err.println("Fehler beim lesen vom Server = aendereArtikelBestand()" + e);
        }
    }

    /**
     * Fügt einen Artikel in den Warenkorb eines Kunden ein.
     *
     * <p>
     * Die Methode sendet die Artikelnummer und die Menge des Artikels an den Server. Bei Erfolg wird die Nachricht "Erfolgreich artikelInWarenkorbHinzufügen()"
     * ausgegeben. Bei Fehlern werden entsprechende Ausnahmen ausgelöst oder Fehlermeldungen in der Konsole ausgegeben.
     * </p>
     *
     * <p>
     * Folgende Fehler werden abgefangen:
     * <ul>
     *   <li>{@link IdNichtVorhandenException} - Wenn der Kunde oder Artikel nicht existiert (Fehlercode "ERROR 303").</li>
     *   <li>{@link MinusZahlException} - Wenn die angegebene Menge negativ ist (Fehlercode "ERROR 202").</li>
     *   <li>{@link KeinMassengutException} - Wenn der Artikel kein Massengutartikel ist, aber eine Menge angegeben wird (Fehlercode "ERROR 405").</li>
     *   <li>{@link BestandNichtAusreichendException} - Wenn der Bestand des Artikels nicht ausreicht (Fehlercode "ERROR 408").</li>
     *   <li>{@link IstLeerException} - Wenn der Artikel nicht im Warenkorb sein kann (Fehlercode "ERROR 406").</li>
     * </ul>
     * </p>
     *
     * @param kunde Der {@link Person}-Typ Kunde, der den Artikel in den Warenkorb legen möchte.
     * @param artikel Der {@link Artikel}, der dem Warenkorb hinzugefügt werden soll.
     * @param menge Die Menge des Artikels, die in den Warenkorb gelegt werden soll.
     * @throws IdNichtVorhandenException Wenn die ID des Kunden oder Artikels nicht existiert (Fehlercode "ERROR 303").
     * @throws MinusZahlException Wenn die Menge negativ ist (Fehlercode "ERROR 202").
     * @throws KeinMassengutException Wenn der Artikel kein Massengutartikel ist, aber eine Menge angegeben wird (Fehlercode "ERROR 405").
     * @throws BestandNichtAusreichendException Wenn der Bestand des Artikels nicht ausreicht (Fehlercode "ERROR 408").
     * @throws IstLeerException Wenn der Artikel nicht in den Warenkorb gelegt werden kann (Fehlercode "ERROR 406").
     */
    public void artikelInWarenkorbHinzufügen(Person kunde, Artikel artikel, int menge) throws IdNichtVorhandenException, MinusZahlException, KeinMassengutException, BestandNichtAusreichendException, IstLeerException {
        out.println("artikelInWarenkorbHinzufügen");
        out.println(kunde.getId());
        out.println(artikel.getArtikelnummer());
        out.println(menge);

        try{
            String rückfrage = in.readLine();
            switch (rückfrage) {
                case "ERROR 303":
                    throw new IdNichtVorhandenException(artikel.getArtikelnummer());
                case "ERROR 202":
                    throw new MinusZahlException();
                case "ERROR 405":
                    throw new KeinMassengutException(menge);
                case "ERROR 408":
                    throw new BestandNichtAusreichendException(artikel, artikel.getArtikelbestand());
                case "ERROR 406":
                    throw new IstLeerException();
                default:
                    System.out.println("Erfolgreich artikelInWarenkorbHinzufügen()");
            }
            //TODO: Fehler abfangen
        } catch (IOException e){
            System.err.println("Fehler beim lesen vom Server = artikelInWarenkorbHinzufügen()" + e);
        }
    }

    /**
     * Holt die Artikel und deren Mengen aus dem Warenkorb eines Kunden.
     *
     * <p>
     * Die Methode sendet die Kunden-ID an den Server und erhält die Liste von Artikeln und deren Mengen im Warenkorb. Bei Erfolg wird eine Map mit den Artikeln
     * und deren Mengen zurückgegeben. Bei Fehlern werden entsprechende Ausnahmen ausgelöst oder Fehlermeldungen in der Konsole ausgegeben.
     * </p>
     *
     * <p>
     * Folgende Fehler werden abgefangen:
     * <ul>
     *   <li>{@link IstLeerException} - Wenn der Warenkorb des Kunden leer ist (Fehlercode "ERROR 406").</li>
     *   <li>{@link IdNichtVorhandenException} - Wenn die Kunden-ID nicht existiert (Fehlercode "ERROR 303").</li>
     * </ul>
     * </p>
     *
     * @param kunde Der {@link Person}-Typ Kunde, dessen Warenkorb abgerufen werden soll.
     * @return Eine {@link Map} mit {@link Artikel}-Objekten als Schlüsseln und den entsprechenden Mengen als Werten.
     * @throws IstLeerException Wenn der Warenkorb des Kunden leer ist (Fehlercode "ERROR 406").
     * @throws IdNichtVorhandenException Wenn die Kunden-ID nicht existiert (Fehlercode "ERROR 303").
     */
    public Map<Artikel, Integer> gibWarenkorbArtikel(Person kunde) throws IstLeerException, IdNichtVorhandenException {
        Map<Artikel, Integer> artikelInWarenkorb = new HashMap<Artikel, Integer >();
        out.println("gibWarenkorbArtikel");
        out.println(kunde.getId());

        try{
            String rückfrage = in.readLine();
            switch (rückfrage) {
                case "ERROR 406":
                    throw new IstLeerException();
                case "ERROR 303":
                    //throw new IdNichtVorhandenException(); TODO: Was fehlt noch
                default:
                    System.out.println("Erfolgreich gibWarenkorbArtikel()");
            }
            //TODO: Fehler abfangen
            int warenkorbInhalt = Integer.parseInt(in.readLine());
            for (int i = 0; i < warenkorbInhalt; i++) {

                int mengeVonArtikelnImWarenkorb = Integer.parseInt(in.readLine());
                int artikelnummer = Integer.parseInt(in.readLine());
                String artikelbezeichnung = in.readLine();
                int artikelbestand = Integer.parseInt(in.readLine());
                double artikelpreis = Double.parseDouble(in.readLine());
                Artikel artikel = new Artikel(artikelnummer, artikelbezeichnung, artikelbestand, artikelpreis);
                artikelInWarenkorb.put(artikel, mengeVonArtikelnImWarenkorb);
            }
            return artikelInWarenkorb;
        } catch (IOException e){
            System.err.println("Fehler beim lesen vom Server = gibWarenkorbArtikel()" + e);
        }
        return null;
    }

    /**
     * Berechnet den Gesamtpreis der Artikel im Warenkorb eines Kunden.
     *
     * <p>
     * Die Methode sendet die Kunden-ID an den Server und erhält den Gesamtpreis aller Artikel im Warenkorb. Bei Erfolg wird der Gesamtpreis als {@code double} zurückgegeben.
     * Bei Fehlern werden entsprechende Ausnahmen ausgelöst oder Fehlermeldungen in der Konsole ausgegeben.
     * </p>
     *
     * <p>
     * Folgende Fehler werden abgefangen:
     * <ul>
     *   <li>{@link IstLeerException} - Wenn der Warenkorb des Kunden leer ist (Fehlercode "ERROR 202").</li>
     *   <li>{@link IdNichtVorhandenException} - Wenn die Kunden-ID nicht existiert (Fehlercode "ERROR 406").</li>
     * </ul>
     * </p>
     *
     * @param kunde Der {@link Person}-Typ Kunde, dessen Warenkorb überprüft werden soll.
     * @return Der Gesamtpreis aller Artikel im Warenkorb als {@code double}.
     * @throws IstLeerException Wenn der Warenkorb des Kunden leer ist (Fehlercode "ERROR 202").
     * @throws IdNichtVorhandenException Wenn die Kunden-ID nicht existiert (Fehlercode "ERROR 406").
     */
    public double gesamtPreis(Person kunde) throws IstLeerException, IdNichtVorhandenException {
        out.println("gesamtPreis");
        out.println(kunde.getId());

        try{
            String rückfrage = in.readLine();
            switch (rückfrage) {
                case "ERROR 202":
                    throw new IstLeerException();
                case "ERROR 406":
                    //throw new IdNichtVorhandenException();
                default:
                    System.out.println("Erfolgreich gesamtPreis()");
            }
            //TODO: Fehler abfangen
            double gesamtPreis = Double.parseDouble(in.readLine());
            return gesamtPreis;
        } catch (IOException e){
            System.err.println("Fehler beim lesen vom Server = gesamtPreis()" + e);
        }
        return -1.0;
    }

    /**
     * Leert den Warenkorb eines Kunden.
     *
     * <p>
     * Die Methode sendet die Kunden-ID an den Server, um den Warenkorb des Kunden zu leeren. Bei Erfolg wird die Nachricht "Erfolgreich warenkorbLeeren()"
     * ausgegeben. Bei Fehlern werden entsprechende Ausnahmen ausgelöst oder Fehlermeldungen in der Konsole ausgegeben.
     * </p>
     *
     * <p>
     * Folgende Fehler werden abgefangen:
     * <ul>
     *   <li>{@link IdNichtVorhandenException} - Wenn die Kunden-ID nicht existiert (Fehlercode "ERROR 303").</li>
     *   <li>{@link IstLeerException} - Wenn der Warenkorb bereits leer ist (Fehlercode "ERROR 202").</li>
     * </ul>
     * </p>
     *
     * @param kunde Der {@link Person}-Typ Kunde, dessen Warenkorb geleert werden soll.
     * @throws IdNichtVorhandenException Wenn die Kunden-ID nicht existiert (Fehlercode "ERROR 303").
     * @throws IstLeerException Wenn der Warenkorb bereits leer ist (Fehlercode "ERROR 202").
     */
    public void warenkorbLeeren(Person kunde) {
        out.println("warenkorbLeeren");
        out.println(kunde.getId());
        try{
            String rückfrage = in.readLine();
            switch (rückfrage) {
                //TODO: machen
                default:
                    System.out.println("Erfolgreich warenkorbLeeren()");
            }
        } catch (IOException e){
            System.err.println("Fehler beim lesen vom Server = warenkorbLeeren()" + e);
        }
    }

    /**
     * Kauft den Warenkorb des Kunden und erstellt eine Rechnung.
     *
     * <p>
     * Diese Methode sendet die Kunden-ID an den Server, um den Warenkorb zu kaufen. Bei Erfolg wird eine Rechnung erstellt und zurückgegeben.
     * Bei Fehlern werden entsprechende Ausnahmen ausgelöst oder Fehlermeldungen in der Konsole ausgegeben.
     * </p>
     *
     * <p>
     * Folgende Fehler werden abgefangen:
     * <ul>
     *   <li>{@link IdNichtVorhandenException} - Wenn die Kunden-ID nicht existiert (Fehlercode "ERROR 303").</li>
     *   <li>{@link IstLeerException} - Wenn der Warenkorb leer ist (Fehlercode "ERROR 406").</li>
     *   <li>{@link BestandNichtAusreichendException} - Wenn der Bestand eines Artikels nicht ausreicht (Fehlercode "ERROR 408").</li>
     * </ul>
     * </p>
     *
     * @param kunde Der {@link Kunde}, dessen Warenkorb gekauft werden soll.
     * @return Die erstellte {@link Rechnung} für den Warenkorb.
     * @throws IdNichtVorhandenException Wenn die Kunden-ID nicht existiert (Fehlercode "ERROR 303").
     * @throws IstLeerException Wenn der Warenkorb leer ist (Fehlercode "ERROR 406").
     * @throws BestandNichtAusreichendException Wenn der Bestand eines oder mehrerer Artikel nicht ausreicht (Fehlercode "ERROR 408").
     */
    public Rechnung warenkorbKaufen(Kunde kunde) throws BestandNichtAusreichendException, IstLeerException, IdNichtVorhandenException {
        out.println("warenkorbKaufen");
        out.println(kunde.getId());

        try{
            String rückfrage = in.readLine();
            switch (rückfrage) {
//                case "ERROR 303":
//                    throw new IdNichtVorhandenException();
                case "ERROR 406":
                    throw new IstLeerException();
//                case "ERROR 408":
//                    throw new BestandNichtAusreichendException();
                default:
                    System.out.println("Erfolgreich warenkorbKaufen()");
            }
            Warenkorb warenkorb = new Warenkorb();
            int warenkorbInhalt = Integer.parseInt(in.readLine());
            for (int i = 0; i < warenkorbInhalt; i++) {
                int menge = Integer.parseInt(in.readLine());
                int artikelnummer = Integer.parseInt(in.readLine());
                String artikelbezeichnung = in.readLine();
                int artikelbestand = Integer.parseInt(in.readLine());
                double artikelpreis = Double.parseDouble(in.readLine());
                Artikel artikel = new Artikel(artikelnummer, artikelbezeichnung, artikelbestand ,artikelpreis);
                warenkorb.artikelHinzufuegen(artikel, menge);
            }
            Rechnung rechnung = new Rechnung(warenkorb, kunde);
            return rechnung;
            //TODO: Fehler abfangen
        } catch (IOException e){
            System.err.println("Fehler beim lesen vom Server = warenkorbKaufen()" + e);
        }
        return null;
    }

    /**
     * Ändert die Menge eines Artikels im Warenkorb eines Kunden.
     *
     * <p>
     * Diese Methode sendet die Kunden-ID, die Artikelnummer und die neue Menge an den Server, um die Menge eines Artikels im Warenkorb zu ändern.
     * Bei Erfolg wird eine Bestätigung ausgegeben.
     * Bei Fehlern werden entsprechende Ausnahmen ausgelöst oder Fehlermeldungen in der Konsole ausgegeben.
     * </p>
     *
     * <p>
     * Folgende Fehler werden abgefangen:
     * <ul>
     *   <li>{@link IdNichtVorhandenException} - Wenn die Artikel-ID nicht existiert (Fehlercode "ERROR 303").</li>
     *   <li>{@link BestandNichtAusreichendException} - Wenn der Bestand eines Artikels nicht ausreicht (Fehlercode "ERROR 408").</li>
     *   <li>{@link KeinMassengutException} - Wenn die Menge für Massengut nicht korrekt ist (Fehlercode "ERROR 405").</li>
     *   <li>{@link MinusZahlException} - Wenn die Menge negativ ist (Fehlercode "ERROR 202").</li>
     *   <li>{@link IstLeerException} - Wenn der Warenkorb leer ist (Fehlercode "ERROR 406").</li>
     * </ul>
     * </p>
     *
     * @param kunde Der {@link Person} (Kunde), dessen Warenkorb aktualisiert werden soll.
     * @param artikel Der {@link Artikel}, dessen Menge im Warenkorb geändert werden soll.
     * @param menge Die neue Menge des Artikels im Warenkorb.
     * @throws BestandNichtAusreichendException Wenn der Bestand des Artikels nicht ausreicht (Fehlercode "ERROR 408").
     * @throws IdNichtVorhandenException Wenn die Artikel-ID nicht existiert (Fehlercode "ERROR 303").
     * @throws KeinMassengutException Wenn die Menge für Massengut nicht korrekt ist (Fehlercode "ERROR 405").
     * @throws MinusZahlException Wenn die Menge negativ ist (Fehlercode "ERROR 202").
     * @throws IstLeerException Wenn der Warenkorb leer ist (Fehlercode "ERROR 406").
     */
    public void bestandImWarenkorbAendern(Person kunde, Artikel artikel, int menge) throws BestandNichtAusreichendException, IdNichtVorhandenException, KeinMassengutException, MinusZahlException, IstLeerException {
        out.println("bestandImWarenkorbAendern");
        out.println(kunde.getId());
        out.println(artikel.getArtikelnummer());
        out.println(menge);

        try{
            String rückfrage = in.readLine();
            switch (rückfrage) {
                case "ERROR 303":
                    throw new IdNichtVorhandenException(artikel.getArtikelnummer());
                case "ERROR 408":
                    throw new BestandNichtAusreichendException(artikel, artikel.getArtikelbestand());
                case "ERROR 405":
                    throw new KeinMassengutException(artikel.getArtikelbestand());
                case "ERROR 202":
                    throw new MinusZahlException();
                case "ERROR 406":
                    throw new IstLeerException();
                //TODO: machen
                default:
                    System.out.println("Erfolgreich bestandImWarenkorbAendern()");
            }
        } catch (IOException e){
            System.err.println("Fehler beim lesen vom Server = bestandImWarenkorbAendern()" + e);
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

        BestandNichtAusreichendException =>"ERROR 408"

        LoginException => "ERROR 807"

        UsernameExistiertException => "ERROR 808"

        EmailExistiertException => "ERROR 809"


        UngueltigeArtikelBezeichnung => /
        FalscheEingabeException => only CUI
        FilterException => only Clientseite
        WertNichtGefundenException => only Clientseite
        ArtikelExisitiertNichtException => only Warenkorbleeren CUI
     */

}