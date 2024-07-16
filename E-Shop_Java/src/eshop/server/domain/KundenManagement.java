package eshop.server.domain;

import eshop.common.exceptions.*;
import eshop.common.enitities.Kunde;

import java.util.Map;
import java.util.HashMap;

import eshop.server.persistence.filePersistenceManager;

/**
 * Verwaltet die Kunden im System und bietet Methoden zum Laden, Hinzufügen und Verwalten von Kunden.
 *
 * Diese Klasse ist verantwortlich für die Verwaltung von Kunden im E-Shop-System. Sie lädt Kundeninformationen aus einer Datei, ermöglicht das Hinzufügen
 * neuer Kunden und verwaltet den aktuell eingeloggten Kunden. Die Klasse verwendet eine Instanz von {@link filePersistenceManager} für das Laden und
 * Speichern von Kundendaten.
 */
public class KundenManagement {
    //Warenkorb öffnen
    private Map<Integer, Kunde> kundenListe = new HashMap<>();
    private Kunde eingeloggterKunde;
    private filePersistenceManager fpm;// = new filePersistenceManager();

    /**
     * Konstruktor für die Klasse {@link KundenManagement}.
     *
     * Initialisiert das Kundenmanagement mit dem angegebenen {@link filePersistenceManager} und lädt die Kundenliste aus der Datei "kunden.txt".
     * Wenn keine Kunden geladen werden, werden drei Standardkunden hinzugefügt.
     *
     * @param fpm Die Instanz von {@link filePersistenceManager}, die für das Laden und Speichern der Kundendaten verwendet wird.
     */
    public KundenManagement(filePersistenceManager fpm) {
        try{
            this.fpm = fpm;

            kundenListe = fpm.ladeKundenListe("kunden.txt");
            if(kundenListe.isEmpty()){
                addKunde("Hannah", "Lotus", "Hannah@gmail.com", "H4n", "1234", "Hamburg", 27754, "Feldweg", 69);
                addKunde("Dima", "Lotik", "Dima@gmail.com", "D1m", "1234", "Hamburg", 27754, "Feldweg", 69);
                addKunde("Hans", "Lotus", "Hans@gmail.com", "H4n5", "1234", "Hamburg", 27554, "Feldstr", 123);
            }

        }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }

    /**
     * Fügt einen neuen Kunden zur Kundenliste hinzu.
     *
     * Diese Methode erstellt ein neues {@link Kunde}-Objekt mit den angegebenen Parametern und fügt es zur {@link #kundenListe} hinzu.
     * Vor dem Hinzufügen wird überprüft, ob der Username und die Email-Adresse bereits existieren. Zudem wird geprüft, ob die Kunden-ID bereits vergeben ist.
     *
     * @param vorname Der Vorname des Kunden.
     * @param nachname Der Nachname des Kunden.
     * @param email Die E-Mail-Adresse des Kunden.
     * @param username Der Benutzername des Kunden.
     * @param password Das Passwort des Kunden.
     * @param ort Der Ort, an dem der Kunde lebt.
     * @param plz Die Postleitzahl des Kunden.
     * @param strasse Die Straße, in der der Kunde wohnt.
     * @param strassenNummer Die Hausnummer des Kunden.
     * @throws DoppelteIdException Wenn eine Kunden-ID bereits existiert.
     * @throws UsernameExistiertException Wenn der angegebene Benutzername bereits vergeben ist.
     * @throws EmailExistiertException Wenn die angegebene E-Mail-Adresse bereits vergeben ist.
     */
    public void addKunde(String vorname, String nachname, String email, String username, String password, String ort, int plz, String strasse, int strassenNummer) throws DoppelteIdException, UsernameExistiertException, EmailExistiertException {

        // Überprüfung, ob der Username bereits existiert
        for (Kunde kunde : kundenListe.values()) {
            if (kunde.getUsername().equals(username)) {
                throw new UsernameExistiertException(username);
            }
        }

        for(Kunde kunde : kundenListe.values()){
            if(kunde.getEmail().equals(email)){
                throw new EmailExistiertException(email);
            }
        }

        Kunde kunde = new Kunde(vorname, nachname, email, username, password, ort, plz, strasse, strassenNummer);
        int id = kunde.getId();

        if (sucheKunde(id)) {
            throw new DoppelteIdException(id);
        } else {
            kundenListe.put(id, kunde);
        }
    }

    /**
     * Meldet einen Kunden basierend auf dem angegebenen Benutzernamen oder der E-Mail-Adresse und dem Passwort an.
     *
     * Diese Methode überprüft die Anmeldeinformationen eines Kunden. Wenn der Benutzername oder die E-Mail-Adresse mit einem vorhandenen Kunden übereinstimmt
     * und das Passwort korrekt ist, wird der Kunde als eingeloggter Kunde gesetzt und zurückgegeben. Andernfalls wird eine {@link LoginException} ausgelöst.
     *
     * @param usernameOrEmail Der Benutzername oder die E-Mail-Adresse des Kunden, der sich anmelden möchte.
     * @param password Das Passwort des Kunden.
     * @return Der {@link Kunde}-Objekt des angemeldeten Kunden, wenn die Anmeldeinformationen gültig sind.
     * @throws LoginException Wenn die Anmeldeinformationen ungültig sind (Benutzername oder E-Mail-Adresse existiert nicht oder Passwort ist falsch).
     */
    public Kunde loginkunde(String usernameOrEmail, String password) throws LoginException {
        // Überprüfung der Mitarbeiter-Anmeldeinformationen
        for (Map.Entry<Integer, Kunde> entry : kundenListe.entrySet()) {
            Kunde kunde = entry.getValue();
            if (kunde.getUsername().equals(usernameOrEmail) || kunde.getEmail().equals(usernameOrEmail)) {
                if (kunde.checkPasswort(password)) {
                    // Kunde erfolgreich angemeldet
                    setEingeloggterKunde(kunde);
                    return kunde;
                }
            }
        }

        // Ungültige Anmeldeinformationen
        throw new LoginException();

    }

    /**
     * Setzt den aktuell eingeloggten Kunden.
     *
     * Diese Methode aktualisiert die Referenz auf den Kunden, der momentan im System eingeloggt ist.
     * Der übergebene Kunde wird als der aktuell eingeloggte Benutzer festgelegt.
     * Dies ist eine wichtige Methode für die Verwaltung des Kundenstatus während einer Sitzung.
     *
     * @param kunde Das {@link Kunde}-Objekt des einzuloggenden Kunden.
     *
     * @see #getEingeloggterKunde()
     */
    public  void setEingeloggterKunde(Kunde kunde) {
        this.eingeloggterKunde = kunde;
    }

    /**
     * Gibt den aktuell eingeloggten Kunden zurück.
     *
     * Diese Methode gibt das {@link Kunde}-Objekt zurück, das den Kunden repräsentiert, der momentan im System eingeloggt ist.
     * Sie wird verwendet, um den aktuellen Kundenstatus während einer Sitzung abzurufen, beispielsweise um Informationen über den eingeloggten Kunden zu erhalten.
     *
     * @return Das {@link Kunde}-Objekt des aktuell eingeloggten Kunden. Kann {@code null} sein, wenn kein Kunde eingeloggt ist.
     *
     * @see #setEingeloggterKunde(Kunde)
     */
    public Kunde getEingeloggterKunde() {
        return eingeloggterKunde;
    }

    /**
     * Gibt eine Map aller Kunden im System zurück.
     *
     * Diese Methode gibt die gesamte Liste der Kunden zurück, die im System registriert sind.
     * Die Kunden sind in einer {@link Map} gespeichert, wobei die Kunden-ID als Schlüssel und das {@link Kunde}-Objekt als Wert verwendet wird.
     *
     * @return Eine {@link Map} mit Kunden-IDs als Schlüsseln und {@link Kunde}-Objekten als Werten, die alle registrierten Kunden im System repräsentiert.
     *
     * @see #sucheKundePerId(int)
     * @see #setEingeloggterKunde(Kunde)
     * @see #getEingeloggterKunde()
     */
    public Map<Integer, Kunde> gibAlleKunden() {
        return kundenListe;
    }

    /**
     * Sucht einen Kunden anhand der Kunden-ID und gibt das {@link Kunde}-Objekt zurück.
     *
     * Diese Methode prüft, ob ein Kunde mit der angegebenen ID in der Kundenliste vorhanden ist.
     * Wenn der Kunde gefunden wird, wird das entsprechende {@link Kunde}-Objekt zurückgegeben.
     * Andernfalls wird eine {@link IdNichtVorhandenException} ausgelöst, um anzuzeigen, dass die angegebene ID nicht existiert.
     *
     * @param id Die ID des zu suchenden Kunden.
     * @return Das {@link Kunde}-Objekt, das dem angegebenen ID entspricht.
     * @throws IdNichtVorhandenException Wenn kein Kunde mit der angegebenen ID in der Liste vorhanden ist.
     *
     * @see #sucheKunde(int)
     * @see #getEingeloggterKunde()
     * @see #setEingeloggterKunde(Kunde)
     */
    public Kunde sucheKundePerId(int id) throws IdNichtVorhandenException{
        if(!kundenListe.containsKey(id)){
            throw new IdNichtVorhandenException(id);
        } else {
            return kundenListe.get(id);
        }
    }

    /**
     * Überprüft, ob ein Kunde mit der angegebenen ID in der Kundenliste vorhanden ist.
     *
     * Diese Methode prüft, ob eine Kunden-ID bereits in der Kundenliste enthalten ist.
     * Sie gibt {@code true} zurück, wenn ein Kunde mit der angegebenen ID existiert, andernfalls {@code false}.
     *
     * @param id Die ID des zu überprüfenden Kunden.
     * @return {@code true}, wenn ein Kunde mit der angegebenen ID in der Kundenliste vorhanden ist; andernfalls {@code false}.
     *
     * @see #sucheKundePerId(int)
     * @see #setEingeloggterKunde(Kunde)
     * @see #getEingeloggterKunde()
     * @see #gibAlleKunden()
     */
    public boolean sucheKunde(int id){
        return  kundenListe.containsKey(id);
    }

}