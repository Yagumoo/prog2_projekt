package eshop.common.exceptions;
/**
 * Diese Ausnahme wird ausgelöst, wenn versucht wird, eine E-Mail-Adresse zu verwenden, die bereits existiert.
 *
 * <p>Die Klasse {@code EmailExistiertException} erweitert die {@code Exception}-Klasse und wird
 * verwendet, um eine spezifische Fehlermeldung anzugeben, wenn eine E-Mail-Adresse bereits vorhanden ist.</p>
 *
 * @see java.lang.Exception
 */
public class EmailExistiertException extends Exception {

    private String email;
    /**
     * Konstruktor, der eine Fehlermeldung mit der bereits existierenden E-Mail-Adresse erstellt.
     *
     * @param email die E-Mail-Adresse, die bereits existiert
     */

    public EmailExistiertException(String email) {
        super("Die E-mail '" + email + "' existiert bereits.");
        this.email = email;
    }
    /**
     * Gibt die E-Mail-Adresse zurück, die die Ausnahme ausgelöst hat.
     *
     * @return die E-Mail-Adresse, die bereits existiert
     */

    public String getUsername() {
        return email;
    }
}
