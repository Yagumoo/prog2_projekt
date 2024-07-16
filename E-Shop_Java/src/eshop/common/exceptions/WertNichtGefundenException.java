package eshop.common.exceptions;
/**
 * Diese Ausnahme wird ausgelöst, wenn ein Wert nicht gefunden werden kann.
 *
 * <p>Die Klasse {@code WertNichtGefundenException} erweitert die {@code Exception}-Klasse und wird verwendet,
 * um anzuzeigen, dass ein bestimmter Wert in einem Suchvorgang nicht gefunden wurde.</p>
 *
 * @see java.lang.Exception
 */
public class WertNichtGefundenException extends  Exception{
    /**
     * Konstruktor, der eine benutzerdefinierte Fehlermeldung erstellt.
     *
     * @param message die Fehlermeldung, die bei der Ausnahme angezeigt werden soll
     */
    public WertNichtGefundenException(String message) {
        super(message);
    }
}
