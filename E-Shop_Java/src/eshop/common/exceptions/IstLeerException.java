package eshop.common.exceptions;
/**
 * Diese Ausnahme wird ausgelöst, wenn ein Vorgang auf einen leeren Warenkorb angewendet wird.
 *
 * <p>Die Klasse {@code IstLeerException} erweitert die {@code Exception}-Klasse und wird verwendet,
 * um eine spezifische Fehlermeldung anzugeben, wenn versucht wird, auf einen Warenkorb ohne Artikel zuzugreifen.</p>
 *
 * @see java.lang.Exception
 */
public class IstLeerException extends Exception {
    /**
     * Konstruktor, der eine standardisierte Fehlermeldung erstellt, um anzuzeigen, dass der Warenkorb leer ist.
     */
    public IstLeerException() {
        super("Der Warenkorb enthält zurzeit keine Artikel");
    }
}
