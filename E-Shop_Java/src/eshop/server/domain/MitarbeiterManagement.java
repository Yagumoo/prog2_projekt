package eshop.server.domain;

import eshop.common.enitities.Mitarbeiter;
import eshop.common.enitities.Person;

import java.util.Map;
import java.util.HashMap;

import eshop.common.exceptions.*;
import eshop.server.persistence.filePersistenceManager;

/**
 * Verwaltet die Mitarbeiter im System und bietet Methoden zum Verwalten des aktuell eingeloggten Mitarbeiters sowie zum Abrufen von Mitarbeiterinformationen.
 *
 * Diese Klasse ist verantwortlich für die Verwaltung von Mitarbeitern im E-Shop-System. Sie ermöglicht das Verwalten des aktuell eingeloggten Mitarbeiters, das
 * Abrufen aller Mitarbeiter und das Erstellen neuer Mitarbeiter. Sie verwendet eine Instanz von {@link filePersistenceManager} für das Laden und Speichern von
 * Mitarbeiterdaten.
 */
public class MitarbeiterManagement {
    private filePersistenceManager fpm;
    //Mitarbeiter erstellen
    //KundenListeaufrufen
    private Map<Integer, Mitarbeiter> mitarbeiterListe = new HashMap<>();
    private Person eingeloggterMitarbeiter;
    //private filePersistenceManager fpm = new filePersistenceManager();

    /**
     * Konstruktor für die Klasse {@link MitarbeiterManagement}.
     *
     * Initialisiert das Mitarbeiter-Management mit der angegebenen {@link filePersistenceManager} Instanz und lädt die Mitarbeiterliste aus der Datei "mitarbeiter.txt".
     * Wenn keine Mitarbeiter geladen werden, wird ein Standardmitarbeiter hinzugefügt.
     *
     * @param fpm Die Instanz von {@link filePersistenceManager}, die für das Laden und Speichern der Mitarbeiterdaten verwendet wird.
     */
    public MitarbeiterManagement(filePersistenceManager fpm) {

        try {
            this.fpm = fpm;
            mitarbeiterListe = fpm.ladeMitarbeiterListe("mitarbeiter.txt");


            if(mitarbeiterListe.isEmpty()){
                addMitarbeiter("Johnny", "Sims", "sins.honny@gmail.com", "Sins", "12345");
            }

        }catch(Exception e){
            System.out.println(e.getMessage());
        }


    }

    /**
     * Fügt einen neuen Mitarbeiter zur Mitarbeiterliste hinzu.
     *
     * Diese Methode erstellt ein neues {@link Mitarbeiter}-Objekt mit den angegebenen Details und fügt es der Mitarbeiterliste hinzu.
     * Vor dem Hinzufügen wird überprüft, ob der Benutzername bereits vergeben ist und ob die E-Mail-Adresse bereits existiert.
     * Wenn der Benutzername oder die E-Mail-Adresse bereits vergeben ist, wird eine entsprechende Ausnahme ausgelöst.
     * Zudem wird geprüft, ob die ID des neuen Mitarbeiters bereits in der Liste vorhanden ist, um doppelte IDs zu vermeiden.
     *
     * @param vorname Der Vorname des Mitarbeiters.
     * @param nachname Der Nachname des Mitarbeiters.
     * @param email Die E-Mail-Adresse des Mitarbeiters.
     * @param username Der Benutzername des Mitarbeiters.
     * @param password Das Passwort des Mitarbeiters.
     *
     * @throws DoppelteIdException Wenn die ID des neuen Mitarbeiters bereits existiert.
     * @throws UsernameExistiertException Wenn der Benutzername bereits vergeben ist.
     * @throws EmailExistiertException Wenn die E-Mail-Adresse bereits vergeben ist.
     *
     * @see #sucheMitarbeiter(int)
     * @see #gibAlleMitarbeiter()
     */
    public void addMitarbeiter(String vorname, String nachname, String email, String username, String password) throws DoppelteIdException, UsernameExistiertException, EmailExistiertException {
        // Überprüfung, ob der Username bereits existiert
        for (Mitarbeiter mitarbeiter : mitarbeiterListe.values()) {
            if (mitarbeiter.getUsername().equals(username)) {
                throw new UsernameExistiertException(username);
            }
        }

        for(Mitarbeiter mitarbeiter : mitarbeiterListe.values()){
            if(mitarbeiter.getEmail().equals(email)){
                throw new EmailExistiertException(email);
            }
        }

        Mitarbeiter mitarbeiter = new Mitarbeiter(vorname, nachname, email, username, password);
        int id = mitarbeiter.getId();

        if (sucheMitarbeiter(id)) {
            throw new DoppelteIdException(id);
        } else {
            mitarbeiterListe.put(id, mitarbeiter);
        }
    }

    /**
     * Meldet einen Mitarbeiter im System an, basierend auf dem angegebenen Benutzernamen oder der E-Mail-Adresse und dem Passwort.
     *
     * Diese Methode überprüft die Anmeldeinformationen eines Mitarbeiters, indem sie den Benutzernamen oder die E-Mail-Adresse mit den gespeicherten Daten vergleicht und das Passwort überprüft.
     * Wenn die Anmeldedaten korrekt sind, wird der Mitarbeiter als eingeloggter Mitarbeiter gesetzt und das {@link Mitarbeiter}-Objekt zurückgegeben.
     * Andernfalls wird eine {@link LoginException} ausgelöst.
     *
     * @param usernameOrEmail Der Benutzername oder die E-Mail-Adresse des Mitarbeiters.
     * @param password Das Passwort des Mitarbeiters.
     * @return Das {@link Mitarbeiter}-Objekt des erfolgreich angemeldeten Mitarbeiters.
     * @throws LoginException Wenn die Anmeldeinformationen ungültig sind oder der Mitarbeiter nicht gefunden wird.
     *
     * @see #addMitarbeiter(String, String, String, String, String)
     */
    public Mitarbeiter loginMitarbeiter(String usernameOrEmail, String password) throws LoginException {
        // Überprüfung der Mitarbeiter-Anmeldeinformationen
        for (Map.Entry<Integer, Mitarbeiter> entry : mitarbeiterListe.entrySet()) {
            Mitarbeiter mitarbeiter = entry.getValue();
            if (mitarbeiter.getUsername().equals(usernameOrEmail) || mitarbeiter.getEmail().equals(usernameOrEmail)) {
                if (mitarbeiter.checkPasswort(password)) {
                    setEingeloggteMitarbeiter(mitarbeiter);
                    return mitarbeiter;
                }
            }
        }
        throw new LoginException();

    }
    /**
     * Setzt den aktuell eingeloggten Mitarbeiter im System.
     *
     * Diese Methode setzt den Mitarbeiter, der gerade im System eingeloggt ist, indem sie das übergebene {@link Person}-Objekt als `eingeloggterMitarbeiter` speichert.
     * Dieser Mitarbeiter kann dann für weitere Operationen innerhalb der Sitzung verwendet werden.
     *
     * @param mitarbeiter Das {@link Person}-Objekt des Mitarbeiters, der als eingeloggter Mitarbeiter gesetzt werden soll.
     *
     * @see #setEingeloggteMitarbeiter(Person mitarbeiter)
     * @see #loginMitarbeiter(String, String)
     */
    public  void setEingeloggteMitarbeiter(Person mitarbeiter) {
        this.eingeloggterMitarbeiter = mitarbeiter;
    }

    /**
     * Gibt den Mitarbeiter anhand der angegebenen ID zurück.
     *
     * Diese Methode sucht nach einem {@link Mitarbeiter} in der `mitarbeiterListe`, der die angegebene ID hat.
     * Wenn der Mitarbeiter mit der angegebenen ID nicht gefunden wird, wird eine {@link IdNichtVorhandenException} ausgelöst.
     *
     * @param id Die ID des gesuchten Mitarbeiters.
     * @return Das {@link Mitarbeiter}-Objekt, das der angegebenen ID entspricht.
     * @throws IdNichtVorhandenException Wenn kein Mitarbeiter mit der angegebenen ID gefunden wird.
     *
     * @see #setEingeloggteMitarbeiter(Person)
     *
     * @see #loginMitarbeiter(String, String)
     */
    public Mitarbeiter gibMitarbeiterPerID(int id) throws IdNichtVorhandenException{
        if(!mitarbeiterListe.containsKey(id)){
            throw new IdNichtVorhandenException(id);
        } else {
            return mitarbeiterListe.get(id);
        }

    }

    /**
     * Überprüft, ob ein Mitarbeiter mit der angegebenen ID in der Liste vorhanden ist.
     *
     * Diese Methode prüft, ob die `mitarbeiterListe` einen Eintrag mit der angegebenen ID enthält.
     * Wenn ein Mitarbeiter mit der ID gefunden wird, gibt die Methode `true` zurück; andernfalls `false`.
     *
     * @param id Die ID des Mitarbeiters, nach dem gesucht werden soll.
     * @return `true`, wenn ein Mitarbeiter mit der angegebenen ID vorhanden ist; `false`, wenn nicht.
     *
     * @see #gibMitarbeiterPerID(int)
     * @see #addMitarbeiter(String, String, String, String, String)
     */
    public boolean sucheMitarbeiter(int id){
        return  mitarbeiterListe.containsKey(id);
    }

    /**
     * Gibt alle Mitarbeiter im System zurück.
     *
     * Diese Methode liefert die gesamte Liste der Mitarbeiter als `Map`, wobei die Mitarbeiter-IDs als Schlüssel und die Mitarbeiter-Objekte als Werte dienen.
     *
     * @return Eine Map, die alle Mitarbeiter mit ihren IDs enthält. Die IDs sind die Schlüssel, die {@link Mitarbeiter}-Objekte sind die Werte.
     *
     * @see #sucheMitarbeiter(int)
     * @see #gibMitarbeiterPerID(int)
     * @see #addMitarbeiter(String, String, String, String, String)
     */
    public Map<Integer, Mitarbeiter> gibAlleMitarbeiter() {
        return mitarbeiterListe;
    }
}
