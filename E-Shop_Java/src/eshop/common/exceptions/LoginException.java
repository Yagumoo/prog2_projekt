package eshop.common.exceptions;
/**
 * Diese Ausnahme wird ausgelöst, wenn beim Login ein Fehler auftritt, wie z.B. ein falsches Passwort
 * oder ein ungültiger Benutzername.
 *
 * <p>Die Klasse {@code LoginException} erweitert die {@code Exception}-Klasse und wird verwendet,
 * um eine Fehlermeldung anzuzeigen, wenn Login-Daten inkorrekt sind oder der Login-Vorgang fehlschlägt.</p>
 *
 * @see java.lang.Exception
 */
public class LoginException extends Exception {
    /**
     * Konstruktor, der eine standardisierte Fehlermeldung erstellt, die angibt, dass der Benutzername
     * oder das Passwort falsch ist und der Login-Vorgang erneut versucht werden sollte.
     */
    public LoginException() {
        super("Falsches Passwort oder Username. Bitte erneut versuchen");
    }
}
