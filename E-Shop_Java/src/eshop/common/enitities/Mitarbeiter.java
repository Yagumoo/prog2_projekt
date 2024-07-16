package eshop.common.enitities;
/**
 * Repräsentiert einen Mitarbeiter im E-Shop.
 *
 * <p>Die {@code Mitarbeiter}-Klasse erweitert die {@code Person}-Klasse und stellt spezifische Eigenschaften und Methoden für Mitarbeiter im E-Shop bereit.</p>
 */
public class Mitarbeiter extends Person{
    /**
     * Konstruktor für die {@code Mitarbeiter}-Klasse ohne ID.
     *
     * <p>Initialisiert einen neuen Mitarbeiter mit den angegebenen Werten für Vorname, Nachname, E-Mail, Benutzername und Passwort.</p>
     *
     * @param vorname    der Vorname des Mitarbeiters
     * @param nachname   der Nachname des Mitarbeiters
     * @param email      die E-Mail-Adresse des Mitarbeiters
     * @param username   der Benutzername des Mitarbeiters
     * @param password   das Passwort des Mitarbeiters
     */
    public Mitarbeiter(String vorname, String nachname, String email, String username, String password) {
        super(vorname, nachname, email, username, password);

    }
    /**
     * Konstruktor für die {@code Mitarbeiter}-Klasse mit ID.
     *
     * <p>Initialisiert einen neuen Mitarbeiter mit den angegebenen Werten für Vorname, Nachname, E-Mail, Benutzername, Passwort und ID.</p>
     *
     * @param vorname    der Vorname des Mitarbeiters
     * @param nachname   der Nachname des Mitarbeiters
     * @param email      die E-Mail-Adresse des Mitarbeiters
     * @param username   der Benutzername des Mitarbeiters
     * @param password   das Passwort des Mitarbeiters
     * @param id         die eindeutige ID des Mitarbeiters
     */
    public Mitarbeiter(String vorname, String nachname, String email, String username, String password, int id) {
        super(vorname, nachname, email, username, password, id);
    }

}
