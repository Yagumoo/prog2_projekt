package eshop.common.exceptions;
/**
 * Diese Ausnahme wird ausgelöst, wenn ein Fehler während des Filtervorgangs auftritt.
 *
 * <p>Die Klasse {@code FilterException} erweitert die {@code Exception}-Klasse und wird
 * verwendet, um eine spezifische Fehlermeldung anzugeben, wenn ein Filterfehler auftritt.</p>
 *
 * @see java.lang.Exception
 */
public class FilterException extends Exception {
    /**
     * Konstruktor, der eine Fehlermeldung erstellt.
     *
     * @param message die Fehlermeldung
     */
    public FilterException(String message) {
        super(message);
    }
}
