package eshop.server.domain;


import eshop.common.enitities.*;
import eshop.common.exceptions.*;
import eshop.server.persistence.filePersistenceManager;

import java.time.LocalDate;
import java.util.Date;
import java.util.Map;
import java.util.List;
import java.io.IOException;

/**
 * Die Hauptklasse des E-Shops, die die verschiedenen Management-Komponenten des Systems verwaltet.
 *
 * Diese Klasse stellt die zentrale Schnittstelle zum Artikelmanagement, Mitarbeiter- und Kundenmanagement, Warenkorbmanagement und Ereignismanagement bereit.
 * Außerdem verwaltet sie die Dateipersistenz über eine Instanz von {@link filePersistenceManager}.
 * Beim Herunterfahren des Programms werden alle Listen automatisch gespeichert.
 */
public class E_Shop {

    private final ArtikelManagement artikelManagement; // = new ArtikelManagement();
    private final MitarbeiterManagement mitarbeiterManagement; //= new MitarbeiterManagement();
    private final KundenManagement kundenManagement; //= new KundenManagement();
    private final WarenkorbManagement warenkorbManagement; //= new WarenkorbManagement();
    private final EreignisManagement ereignisManagement;//= new EreignisManagement();
    private final filePersistenceManager fpm; //  = new filePersistenceManager();
    // => WarenkorbManagement
    //private MitarbeiterManagement mitarbeiterManagement = new MitarbeiterManagement(artikelManagement);
    /**
     * Konstruktor für die {@link E_Shop} Klasse.
     *
     * Initialisiert die verschiedenen Management-Komponenten und den {@link filePersistenceManager}.
     * Außerdem wird ein Shutdown-Hook hinzugefügt, der beim Beenden des Programms die Daten in den Dateien speichert.
     */
    public E_Shop() {
        fpm = new filePersistenceManager();
        artikelManagement = new ArtikelManagement(fpm);
        mitarbeiterManagement = new MitarbeiterManagement(fpm);
        kundenManagement = new KundenManagement(fpm);
        warenkorbManagement = new WarenkorbManagement();
        ereignisManagement = new EreignisManagement(fpm, kundenManagement.gibAlleKunden(), mitarbeiterManagement.gibAlleMitarbeiter());

        Runtime.getRuntime().addShutdownHook(new Thread(this::speicherAlleListen));
    }
    /**
     * Gibt eine Map aller Artikel im E-Shop zurück.
     *
     * Diese Methode ruft die {@link Map} von {@link Artikel} Objekten ab, die vom {@link ArtikelManagement} verwaltet werden.
     * Die Map verwendet die Artikelnummer als Schlüssel und den {@link Artikel} als Wert.
     *
     * @return Eine {@link Map} mit der Artikelnummer als Schlüssel und {@link Artikel} als Wert.
     */
    public Map<Integer, Artikel> gibAlleArtikel() {
        return artikelManagement.gibAlleArtikel();
    }
    /**
     * Gibt eine Map aller Mitarbeiter im E-Shop zurück.
     *
     * Diese Methode ruft die {@link Map} von {@link Mitarbeiter} Objekten ab, die vom {@link MitarbeiterManagement} verwaltet werden.
     * Die Map verwendet die Mitarbeiter-ID als Schlüssel und den {@link Mitarbeiter} als Wert.
     *
     * @return Eine {@link Map} mit der Mitarbeiter-ID als Schlüssel und {@link Mitarbeiter} als Wert.
     */
    public Map<Integer, Mitarbeiter> gibAlleMitarbeiter() {
        return mitarbeiterManagement.gibAlleMitarbeiter();
    }
    /**
     * Gibt eine Map aller Kunden im E-Shop zurück.
     *
     * Diese Methode ruft die {@link Map} von {@link Kunde} Objekten ab, die vom {@link KundenManagement} verwaltet werden.
     * Die Map verwendet die Kunden-ID als Schlüssel und den {@link Kunde} als Wert.
     *
     * @return Eine {@link Map} mit der Kunden-ID als Schlüssel und {@link Kunde} als Wert.
     */
    public Map<Integer, Kunde> gibAlleKunden() {
        return kundenManagement.gibAlleKunden();
    }

    /**
     * Fügt einen neuen Artikel zum Artikelmanagement hinzu und erstellt ein Ereignis für das Hinzufügen des Artikels.
     *
     * Diese Methode prüft, ob der übergebene {@link Person}-Parameter ein {@link Mitarbeiter} ist, und fügt dann den {@link Artikel}
     * zum {@link ArtikelManagement} hinzu. Anschließend wird ein {@link Ereignis} für das Hinzufügen des Artikels erstellt und
     * dem {@link EreignisManagement} übergeben.
     *
     * @param mitarbeiter Der Mitarbeiter, der den Artikel hinzufügt. Muss eine Instanz von {@link Mitarbeiter} sein.
     * @param artikel Der hinzuzufügende {@link Artikel}. Muss gültige Werte für Artikelnummer, Bezeichnung, Bestand und Preis haben.
     * @throws DoppelteIdException Wenn ein Artikel mit der gleichen Artikelnummer bereits existiert.
     * @throws MinusZahlException Wenn die Artikelnummer, der Artikelbestand oder der Artikelpreis ungültige Werte (negative Werte) haben.
     * @throws KeinMassengutException Wenn es sich um einen {@link MassengutArtikel} handelt, dessen Bestand nicht mit der Massengut-Anzahl übereinstimmt.
     * @throws ArtikelnameDoppeltException Wenn ein Artikel mit der gleichen Bezeichnung bereits existiert.
     */
    public void addArtikel(Person mitarbeiter, Artikel artikel) throws DoppelteIdException, MinusZahlException, KeinMassengutException,ArtikelnameDoppeltException  {
        if (mitarbeiter instanceof Mitarbeiter) {
            artikelManagement.addArtikel(artikel);
            //Person mitarbeiter = mitarbeiterManagement.getEingeloggterMitarbeiter();
            Ereignis neuesEreignis = new Ereignis(LocalDate.now(), artikel.getArtikelbezeichnung(), artikel.getArtikelbestand(), mitarbeiter, Ereignis.EreignisTyp.NEU);
            ereignisManagement.addEreignis(/*mitarbeiter,*/ neuesEreignis);
        }
    }

    /**
     * Gibt eine Liste aller Ereignisse im E-Shop zurück.
     *
     * Diese Methode ruft die Liste von {@link Ereignis} Objekten ab, die vom {@link EreignisManagement} verwaltet werden.
     * Die Liste enthält alle Ereignisse, die im System aufgezeichnet wurden, wie z.B. das Hinzufügen oder Ändern von Artikeln.
     *
     * @return Eine {@link List} von {@link Ereignis} Objekten, die alle Ereignisse im System repräsentieren.
     */
    public List<Ereignis> getEreignisListe() {
        return ereignisManagement.getEreignisse();
    }

    /**
     * Fügt einen neuen Mitarbeiter zum Mitarbeitermanagement hinzu.
     *
     * Diese Methode prüft, ob der übergebene {@link Person}-Parameter ein {@link Mitarbeiter} ist, und fügt dann einen neuen {@link Mitarbeiter}
     * mit den angegebenen Details zum {@link MitarbeiterManagement} hinzu.
     *
     * @param mitarbeiter Der Mitarbeiter, der den neuen Mitarbeiter hinzufügen möchte. Muss eine Instanz von {@link Mitarbeiter} sein.
     * @param vorname Der Vorname des neuen Mitarbeiters.
     * @param nachname Der Nachname des neuen Mitarbeiters.
     * @param email Die E-Mail-Adresse des neuen Mitarbeiters.
     * @param username Der Benutzername des neuen Mitarbeiters.
     * @param password Das Passwort des neuen Mitarbeiters.
     * @throws DoppelteIdException Wenn ein Mitarbeiter mit der gleichen ID bereits existiert.
     * @throws UsernameExistiertException Wenn der angegebene Benutzername bereits vergeben ist.
     * @throws EmailExistiertException Wenn die angegebene E-Mail-Adresse bereits vergeben ist.
     */
    public void addMitarbeiter(Person mitarbeiter, String vorname, String nachname, String email, String username, String password) throws DoppelteIdException, UsernameExistiertException, EmailExistiertException {
        if (mitarbeiter instanceof Mitarbeiter) {
            mitarbeiterManagement.addMitarbeiter(vorname, nachname, email, username, password);
            //Person mitarbeiter = mitarbeiterManagement.getEingeloggterMitarbeiter();
        }

    }

    /**
     * Fügt einen neuen Kunden zum Kundenmanagement hinzu.
     *
     * Diese Methode ruft die {@link KundenManagement#addKunde(String, String, String, String, String, String, int, String, int)}-Methode auf,
     * um einen neuen Kunden mit den angegebenen Details hinzuzufügen.
     *
     * @param vorname Der Vorname des neuen Kunden.
     * @param nachname Der Nachname des neuen Kunden.
     * @param email Die E-Mail-Adresse des neuen Kunden.
     * @param username Der Benutzername des neuen Kunden.
     * @param password Das Passwort des neuen Kunden.
     * @param ort Der Wohnort des neuen Kunden.
     * @param plz Die Postleitzahl des Wohnorts des neuen Kunden.
     * @param strasse Die Straße des Wohnorts des neuen Kunden.
     * @param strassenNummer Die Hausnummer des Wohnorts des neuen Kunden.
     * @throws DoppelteIdException Wenn ein Kunde mit der gleichen ID bereits existiert.
     * @throws UsernameExistiertException Wenn der angegebene Benutzername bereits vergeben ist.
     * @throws EmailExistiertException Wenn die angegebene E-Mail-Adresse bereits vergeben ist.
     */
    public void addKunde(String vorname, String nachname, String email, String username, String password, String ort, int plz, String strasse, int strassenNummer) throws DoppelteIdException, UsernameExistiertException, EmailExistiertException {
        kundenManagement.addKunde(vorname, nachname, email, username, password, ort, plz, strasse, strassenNummer);
    }

    /**
     * Meldet einen Mitarbeiter basierend auf Benutzername oder E-Mail und Passwort an.
     *
     * Diese Methode ruft die {@link MitarbeiterManagement#loginMitarbeiter(String, String)}-Methode auf,
     * um einen Mitarbeiter zu authentifizieren.
     *
     * @param usernameOrEmail Der Benutzername oder die E-Mail-Adresse des Mitarbeiters.
     * @param password Das Passwort des Mitarbeiters.
     * @return Der angemeldete {@link Mitarbeiter}, wenn die Anmeldedaten korrekt sind.
     * @throws LoginException Wenn die Anmeldedaten ungültig sind.
     */
    public Mitarbeiter loginMitarbeiter(String usernameOrEmail, String password) throws LoginException {
        return mitarbeiterManagement.loginMitarbeiter(usernameOrEmail, password);
    }

    /**
     * Meldet einen Kunden basierend auf Benutzername oder E-Mail und Passwort an.
     *
     * Diese Methode ruft die {@link KundenManagement#loginkunde(String, String)}-Methode auf,
     * um einen Kunden zu authentifizieren und fügt den Kunden zum Warenkorbmanagement hinzu.
     *
     * @param usernameOrEmail Der Benutzername oder die E-Mail-Adresse des Kunden.
     * @param password Das Passwort des Kunden.
     * @return Der angemeldete {@link Kunde}, wenn die Anmeldedaten korrekt sind.
     * @throws LoginException Wenn die Anmeldedaten ungültig sind.
     */
    public Kunde loginKunde(String usernameOrEmail, String password) throws LoginException {
        Kunde kunde = kundenManagement.loginkunde(usernameOrEmail, password);
        warenkorbManagement.warenkorbHinzufuegen(kunde);
        return kunde;
    }

    /**
     * Sucht einen Mitarbeiter anhand seiner ID.
     *
     * Diese Methode ruft die {@link MitarbeiterManagement#gibMitarbeiterPerID(int)}-Methode auf,
     * um einen {@link Mitarbeiter} mit der angegebenen ID zu finden.
     *
     * @param id Die ID des gesuchten Mitarbeiters.
     * @return Der {@link Mitarbeiter} mit der angegebenen ID.
     * @throws IdNichtVorhandenException Wenn kein Mitarbeiter mit der angegebenen ID existiert.
     */
    public Mitarbeiter sucheMirarbeiterMitNummer(int id) throws IdNichtVorhandenException {
        return mitarbeiterManagement.gibMitarbeiterPerID(id);
    }

    /**
     * Sucht einen Kunden anhand seiner ID.
     *
     * Diese Methode ruft die {@link KundenManagement#sucheKundePerId(int)}-Methode auf,
     * um einen {@link Kunde} mit der angegebenen ID zu finden.
     *
     * @param id Die ID des gesuchten Kunden.
     * @return Der {@link Kunde} mit der angegebenen ID.
     * @throws IdNichtVorhandenException Wenn kein Kunde mit der angegebenen ID existiert.
     */
    public Kunde sucheKundeMitNummer(int id) throws IdNichtVorhandenException {
        return kundenManagement.sucheKundePerId(id);
    }

    /**
     * Sucht einen Artikel anhand seiner Artikelnummer.
     *
     * Diese Methode ruft die {@link ArtikelManagement#gibArtikelPerId(int)}-Methode auf,
     * um einen {@link Artikel} mit der angegebenen Artikelnummer zu finden.
     *
     * @param artikelnummer Die Artikelnummer des gesuchten Artikels.
     * @return Der {@link Artikel} mit der angegebenen Artikelnummer.
     * @throws IdNichtVorhandenException Wenn kein Artikel mit der angegebenen Artikelnummer existiert.
     */
    public Artikel sucheArtikelMitNummer(int artikelnummer) throws IdNichtVorhandenException {
        return artikelManagement.gibArtikelPerId(artikelnummer);
    }

    /**
     * Löscht einen Artikel anhand seiner Artikelnummer, wenn der aufrufende Mitarbeiter ein gültiger Mitarbeiter ist.
     *
     * Diese Methode überprüft, ob der {@link Person} das Objekt ein {@link Mitarbeiter} ist, und ruft dann die
     * {@link ArtikelManagement#loescheArtikel(int)}-Methode auf, um den Artikel mit der angegebenen Artikelnummer zu löschen.
     *
     * @param mitarbeiter Der Mitarbeiter, der den Artikel löschen möchte. Muss eine Instanz von {@link Mitarbeiter} sein.
     * @param artikelnummer Die Artikelnummer des zu löschenden Artikels.
     * @throws IdNichtVorhandenException Wenn kein Artikel mit der angegebenen Artikelnummer existiert.
     */
    public void loescheArtikel(Person mitarbeiter, int artikelnummer) throws IdNichtVorhandenException {
        if (mitarbeiter instanceof Mitarbeiter) {
            sucheArtikelMitNummer(artikelnummer);
            artikelManagement.loescheArtikel(artikelnummer);
        }
    }

    /**
     * Ändert den Bestand eines Artikels und erstellt ein Ereignis über die Bestandänderung, wenn der aufrufende Benutzer ein Mitarbeiter ist.
     *
     * Diese Methode überprüft, ob der aufrufende Benutzer ein {@link Mitarbeiter} ist und ruft dann die Methode
     * {@link ArtikelManagement#aendereArtikelBestand(int, int)} auf, um den Bestand des Artikels zu ändern.
     * Anschließend wird ein {@link Ereignis} erstellt, das die Änderung dokumentiert und dem {@link EreignisManagement} hinzugefügt.
     *
     * <p>Falls der neue Bestand kleiner als der aktuelle Bestand ist, wird ein Ereignis des Typs {@link Ereignis.EreignisTyp#REDUZIERUNG} erstellt.
     * Falls der neue Bestand größer als der aktuelle Bestand ist, wird ein Ereignis des Typs {@link Ereignis.EreignisTyp#ERHOEHUNG} erstellt.</p>
     *
     * @param mitarbeiter Der Mitarbeiter, der die Bestandsänderung durchführen möchte. Muss eine Instanz von {@link Mitarbeiter} sein.
     * @param artikelnummer Die Artikelnummer des zu ändernden Artikels.
     * @param neuerBestand Der neue Bestand für den Artikel.
     * @throws IdNichtVorhandenException Wenn kein Artikel mit der angegebenen Artikelnummer existiert.
     * @throws KeinMassengutException Wenn es sich um ein Massengut handelt und der neue Bestand nicht mit der Anzahl der Massengut-Einheiten übereinstimmt.
     * @throws MinusZahlException Wenn der neue Bestand eine negative Zahl ist.
     */
    public void aendereArtikelBestand(Person mitarbeiter, int artikelnummer, int neuerBestand) throws IdNichtVorhandenException, KeinMassengutException, MinusZahlException {
        if (mitarbeiter instanceof Mitarbeiter) {
            Artikel artikel = artikelManagement.gibArtikelPerId(artikelnummer);

            // Aktuellen Artikelbestand speichern
            int aktuellerBestand = artikel.getArtikelbestand();
            int differenz = neuerBestand - aktuellerBestand;

            // Ändern des Artikelbestands und das Ergebnis der Operation speichern
            boolean bestandGeaendert = artikelManagement.aendereArtikelBestand(artikelnummer, neuerBestand);

            if (bestandGeaendert) {
                Ereignis.EreignisTyp ereignisTyp;
                if (differenz < 0) {
                    ereignisTyp = Ereignis.EreignisTyp.REDUZIERUNG;
                } else {
                    ereignisTyp = Ereignis.EreignisTyp.ERHOEHUNG;
                }

                // Erstellen eines neuen Ereignisses und Hinzufügen zum Ereignis-Management
                Ereignis neuesEreignis = new Ereignis(LocalDate.now(), artikel.getArtikelbezeichnung(), differenz, mitarbeiter, ereignisTyp);
                ereignisManagement.addEreignis(neuesEreignis);
            }
        }
    }


    /**
     * Fügt einen Artikel in den Warenkorb eines Kunden hinzu.
     *
     * Diese Methode überprüft, ob der {@link Person} ein {@link Kunde} ist und ob der Artikel mit der angegebenen Artikelnummer existiert.
     * Danach wird versucht, den Artikel mit der angegebenen Menge in den Warenkorb des Kunden zu legen.
     * Dabei werden verschiedene Validierungen durchgeführt, wie das Vorhandensein des Artikels, positive Menge und ausreichender Bestand.
     *
     * @param kunde Der Kunde, dessen Warenkorb verwendet werden soll. Muss eine Instanz von {@link Kunde} sein.
     * @param artikelnummer Die Artikelnummer des hinzuzufügenden Artikels.
     * @param menge Die Menge des hinzuzufügenden Artikels.
     * @throws IdNichtVorhandenException Wenn kein Artikel mit der angegebenen Artikelnummer existiert.
     * @throws MinusZahlException Wenn die Menge kleiner oder gleich null ist.
     * @throws KeinMassengutException Wenn es sich um ein Massengut handelt und die Menge nicht den Anforderungen entspricht.
     * @throws BestandNichtAusreichendException Wenn der Bestand des Artikels nicht ausreicht, um die angegebene Menge hinzuzufügen.
     * @throws IstLeerException Wenn der Kunde kein gültiger {@link Kunde} ist.
     */
    public void artikelInWarenkorbHinzufügen(Person kunde, int artikelnummer, int menge) throws IdNichtVorhandenException, MinusZahlException, KeinMassengutException, BestandNichtAusreichendException, IstLeerException {
        if (kunde instanceof Kunde k) {
            Artikel artikel = artikelManagement.gibArtikelPerId(artikelnummer);
            if (artikel != null) {
                warenkorbManagement.artikelInWarenkorbHinzufuegen(k, artikel, menge);
            }
        }
    }

    /**
     * Gibt eine Map aller Artikel und deren Mengen im Warenkorb eines Kunden zurück.
     *
     * Diese Methode überprüft, ob der {@link Person} ein {@link Kunde} ist und ruft dann die {@link WarenkorbManagement#getWarenkorb(Kunde)}-Methode auf,
     * um den Warenkorb des Kunden zu erhalten. Anschließend wird die Map mit den Artikeln und deren Mengen im Warenkorb des Kunden zurückgegeben.
     *
     * @param kunde Der Kunde, dessen Warenkorb abgerufen werden soll. Muss eine Instanz von {@link Kunde} sein.
     * @return Eine Map, die alle Artikel im Warenkorb des Kunden mit der Artikelnummer als Schlüssel und der Menge als Wert enthält.
     * @throws IstLeerException Wenn der Kunde kein gültiger {@link Kunde} ist.
     */
    public Map<Artikel, Integer> gibWarenkorbArtikel(Person kunde) throws IstLeerException {
        if (kunde instanceof Kunde k) {
            Warenkorb wk = warenkorbManagement.getWarenkorb(k);
            return wk.getWarenkorbMap();
        } else {
            throw new IstLeerException();
        }
    }

    /**
     * Gibt eine textuelle Darstellung der Artikel im Warenkorb eines Kunden zurück.
     *
     * Diese Methode überprüft, ob der {@link Person} ein {@link Kunde} ist. Wenn dies der Fall ist, wird der Inhalt des Warenkorbs des Kunden
     * abgerufen und als String zurückgegeben. Andernfalls wird eine Fehlermeldung zurückgegeben, dass die Person kein Kunde ist.
     *
     * @param kunde Der Kunde, dessen Warenkorb ausgegeben werden soll. Muss eine Instanz von {@link Kunde} sein.
     * @return Eine String-Repräsentation der Artikel im Warenkorb des Kunden. Gibt "Person ist kein Kunde" zurück, wenn die Person kein Kunde ist.
     * @throws IstLeerException Wenn der Kunde kein gültiger {@link Kunde} ist.
     */
    public String printWarenkorbArtikel(Person kunde) throws IstLeerException {
        if (kunde instanceof Kunde k) {
            Warenkorb wk = warenkorbManagement.getWarenkorb(k);
            return wk.toString();
        }
        return "Person ist kein Kunde";
    }

    /**
     * Berechnet den Gesamtpreis der Artikel im Warenkorb eines Kunden.
     *
     * Diese Methode überprüft, ob der {@link Person} ein {@link Kunde} ist. Wenn dies der Fall ist, wird der Gesamtpreis der Artikel im Warenkorb
     * des Kunden berechnet und zurückgegeben. Falls die Person kein Kunde ist, wird -1.0 zurückgegeben, um einen Fehler anzuzeigen.
     *
     * @param kunde Der Kunde, dessen Warenkorb geprüft werden soll. Muss eine Instanz von {@link Kunde} sein.
     * @return Der Gesamtpreis der Artikel im Warenkorb des Kunden. Gibt -1.0 zurück, wenn die Person kein Kunde ist.
     * @throws IstLeerException Wenn der Kunde kein gültiger {@link Kunde} ist.
     */
    public double gesamtPreis(Person kunde) throws IstLeerException {
        if (kunde instanceof Kunde k) {
            Warenkorb wk = warenkorbManagement.getWarenkorb(k);
            return wk.gesamtPreis();
        }
        return -1.0;
    }

    /**
     * Leert den Warenkorb eines Kunden.
     *
     * Diese Methode überprüft, ob der {@link Person} ein {@link Kunde} ist. Wenn dies der Fall ist, wird der Warenkorb des Kunden geleert.
     * Andernfalls passiert nichts.
     *
     * @param kunde Der Kunde, dessen Warenkorb geleert werden soll. Muss eine Instanz von {@link Kunde} sein.
     */
    public void warenkorbLeeren(Person kunde) {
        if (kunde instanceof Kunde k) {
            warenkorbManagement.warenkorbLeeren(k);
        }
    }

    /**
     * Führt den Kauf des Warenkorbs eines Kunden durch und erstellt eine Rechnung.
     *
     * Diese Methode überprüft, ob der {@link Person} ein {@link Kunde} ist, und ruft dann den Warenkorb des Kunden ab.
     * Anschließend wird überprüft, ob der Bestand der Artikel ausreichend ist, und der Bestand wird entsprechend angepasst.
     * Danach werden für jeden Artikel im Warenkorb Ereignisse vom Typ {@link Ereignis.EreignisTyp#KAUF} erstellt und zum {@link EreignisManagement} hinzugefügt.
     * Schließlich wird eine {@link Rechnung} für den Kauf erstellt und der Warenkorb des Kunden geleert.
     *
     * @param kunde Der Kunde, dessen Warenkorb gekauft werden soll. Muss eine Instanz von {@link Kunde} sein.
     * @return Eine {@link Rechnung} für den Kauf des Warenkorbs.
     * @throws BestandNichtAusreichendException Wenn der Bestand eines oder mehrerer Artikel im Warenkorb nicht ausreicht, um die Menge zu verkaufen.
     * @throws IstLeerException Wenn der Kunde kein gültiger {@link Kunde} ist oder der Warenkorb leer ist.
     */
    public Rechnung warenkorbKaufen(Kunde kunde) throws BestandNichtAusreichendException, IstLeerException {
        Warenkorb wk = warenkorbManagement.getWarenkorbKaufen(kunde);
        artikelManagement.bestandAbbuchen(wk);

        for (Map.Entry<Artikel, Integer> entry : wk.getWarenkorbMap().entrySet()) {
            Artikel artikel = entry.getKey();
            int menge = entry.getValue();
            Ereignis neuesEreignis = new Ereignis(LocalDate.now(), artikel.getArtikelbezeichnung(), menge, kunde, Ereignis.EreignisTyp.KAUF);
            ereignisManagement.addEreignis(neuesEreignis);
        }

        Rechnung rechnung = new Rechnung(wk, kunde);
        warenkorbLeeren(kunde);

        return rechnung;
    }

    /**
     * Ändert die Menge eines Artikels im Warenkorb eines Kunden.
     *
     * Diese Methode überprüft, ob der {@link Person} ein {@link Kunde} ist und ob der Artikel in der aktuellen Menge vorhanden ist.
     * Sie ruft dann die Methode {@link Warenkorb#bestandImWarenkorbAendern(Artikel, int)} auf, um die Menge des Artikels im Warenkorb zu ändern.
     * Danach wird überprüft, ob der neue Bestand des Artikels den aktuellen Bestand überschreitet und eine {@link BestandNichtAusreichendException} geworfen,
     * wenn dies der Fall ist.
     *
     * @param kunde Der Kunde, dessen Warenkorb geändert werden soll. Muss eine Instanz von {@link Kunde} sein.
     * @param artikel Der Artikel, dessen Menge im Warenkorb geändert werden soll.
     * @param menge Die neue Menge des Artikels im Warenkorb.
     * @throws BestandNichtAusreichendException Wenn die angegebene Menge die verfügbare Menge des Artikels überschreitet.
     * @throws IdNichtVorhandenException Wenn der Artikel nicht existiert.
     * @throws KeinMassengutException Wenn der Artikel ein Massengut ist und die Menge den Anforderungen für Massengüter nicht entspricht.
     * @throws MinusZahlException Wenn die Menge kleiner oder gleich null ist.
     * @throws IstLeerException Wenn der Kunde kein gültiger {@link Kunde} ist oder der Warenkorb leer ist.
     */
    public void bestandImWarenkorbAendern(Person kunde, Artikel artikel, int menge) throws BestandNichtAusreichendException, IdNichtVorhandenException, KeinMassengutException, MinusZahlException, IstLeerException {
        if (kunde instanceof Kunde k) {
            int aktuellerBestand = artikel.getArtikelbestand();
            Warenkorb wk = warenkorbManagement.getWarenkorb(k);

            // Überprüfen, ob der Artikel ein Massengutartikel ist

            wk.bestandImWarenkorbAendern(artikel, menge);
            int neuerBestand = artikel.getArtikelbestand();

            if (menge > aktuellerBestand) {
                throw new BestandNichtAusreichendException(artikel, neuerBestand);
            }
        }
    }

    /**
     * Entfernt einen Artikel aus dem Warenkorb eines Kunden.
     *
     * Diese Methode überprüft, ob der {@link Person} ein {@link Kunde} ist, und ruft dann die Methode {@link WarenkorbManagement#entferneArtikelAusWarenkorb(Kunde, Artikel)} auf,
     * um den angegebenen Artikel aus dem Warenkorb des Kunden zu entfernen.
     *
     * @param kunde Der Kunde, dessen Warenkorb geändert werden soll. Muss eine Instanz von {@link Kunde} sein.
     * @param artikel Der Artikel, der aus dem Warenkorb entfernt werden soll.
     * @throws ArtikelExisitiertNichtException Wenn der Artikel nicht existiert.
     * @throws IdNichtVorhandenException Wenn der Artikel im Warenkorb des Kunden nicht vorhanden ist.
     */
    public void artikelImWarenkorbEntfernen(Person kunde, Artikel artikel) throws ArtikelExisitiertNichtException, IdNichtVorhandenException {
        if(kunde instanceof Kunde k){
            warenkorbManagement.entferneArtikelAusWarenkorb(k, artikel);
        }
    }

    /**
     * Speichert alle aktuellen Daten (Artikel, Kunden, Mitarbeiter und Ereignisse) in Textdateien.
     *
     * Diese Methode verwendet die {@link filePersistenceManager} Instanz, um die Listen von Artikeln, Kunden und Mitarbeitern sowie die Ereignisliste
     * in separaten Textdateien zu speichern. Sie ruft die entsprechenden Methoden der {@link filePersistenceManager} auf, um die Daten zu speichern,
     * und gibt eine Erfolgsmeldung auf der Konsole aus, wenn das Speichern abgeschlossen ist.
     * Im Falle eines Fehlers während des Speicherns wird eine Fehlermeldung auf der Konsole ausgegeben und der Stacktrace des Fehlers angezeigt.
     *
     * Die folgenden Daten werden gespeichert:
     * <ul>
     *     <li>Artikel-Liste in der Datei "artikel.txt"</li>
     *     <li>Kunden-Liste in der Datei "kunden.txt"</li>
     *     <li>Mitarbeiter-Liste in der Datei "mitarbeiter.txt"</li>
     *     <li>Ereignis-Liste in der Datei "ereignis.txt"</li>
     * </ul>
     *
     * @throws IOException Wenn ein Fehler beim Zugriff auf die Datei auftritt oder beim Speichern der Daten.
     */
    public void speicherAlleListen() {
        try {
            fpm.speicherArtikelListe("artikel.txt", artikelManagement.gibAlleArtikel());
            System.out.println("Artikelliste gespeichert");

            fpm.speicherKundenListe("kunden.txt", kundenManagement.gibAlleKunden());
            System.out.println("Kundenliste gespeichert");

            fpm.speicherMitarbeiterListe("mitarbeiter.txt", mitarbeiterManagement.gibAlleMitarbeiter());
            System.out.println("Mitarbeiterliste gespeichert");

            fpm.speicherEreignisListe("ereignis.txt", ereignisManagement.getEreignisse());
            System.out.println("Ereignisliste gespeichert");

            System.out.println("Alle Listen wurden erfolgreich gespeichert.");
        } catch (IOException e) {
            System.err.println("Fehler beim Speichern der Listen: " + e.getMessage());
            e.printStackTrace();
        }
    }
}


