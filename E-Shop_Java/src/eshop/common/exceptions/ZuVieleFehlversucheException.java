package eshop.common.exceptions;
/**
 * Diese Ausnahme wird ausgelöst, wenn zu viele Fehlversuche bei der Eingabe des Passworts oder des Benutzernamens vorliegen.
 *
 * <p>Die Klasse {@code ZuVieleFehlversucheException} erweitert die {@code Exception}-Klasse und wird verwendet,
 * um anzuzeigen, dass die maximale Anzahl an Fehlversuchen überschritten wurde und der Benutzer es später erneut versuchen soll.</p>
 *
 * @see java.lang.Exception
 */
public class ZuVieleFehlversucheException extends Exception {
    /**
     * Konstruktor, der eine Standardfehlermeldung erstellt.
     *
     * <p>Dieser Konstruktor initialisiert die Ausnahme mit einer vordefinierten Fehlermeldung, die darauf hinweist,
     * dass zu viele Fehlversuche aufgetreten sind.</p>
     */
    public ZuVieleFehlversucheException() {
        super("Sie haben zu oft das falsche Passwort oder den Username verwendet. Versuchen Sie es später erneut");
    }
}
