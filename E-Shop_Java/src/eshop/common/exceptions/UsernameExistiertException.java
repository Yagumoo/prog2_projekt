package eshop.common.exceptions;
/**
 * Diese Ausnahme wird ausgelöst, wenn ein Benutzername bereits existiert und somit nicht für einen neuen Benutzer verwendet werden kann.
 *
 * <p>Die Klasse {@code UsernameExistiertException} erweitert die {@code Exception}-Klasse und wird verwendet,
 * um anzuzeigen, dass der angegebene Benutzername bereits in der Benutzerverwaltung existiert.</p>
 *
 * <p>Die Ausnahme enthält den Benutzernamen, der bereits vergeben ist, und bietet eine Methode, um diesen Benutzernamen abzurufen.</p>
 *
 * @see java.lang.Exception
 */
public class UsernameExistiertException extends Exception {

    private String username;
    /**
     * Konstruktor, der eine Fehlermeldung erstellt, die angibt, dass der angegebene Benutzername bereits existiert.
     *
     * <p>Dieser Konstruktor initialisiert die Ausnahme mit einer Fehlermeldung, die den Benutzernamen enthält, der bereits vergeben ist.</p>
     *
     * @param username der Benutzername, der bereits existiert
     */
    public UsernameExistiertException(String username) {
        super("Der Username '" + username + "' existiert bereits.");
        this.username = username;
    }
    /**
     * Gibt den Benutzernamen zurück, der bereits existiert.
     *
     * @return der bereits existierende Benutzername
     */
    public String getUsername() {
        return username;
    }
}

