package eshop.common.exceptions;
/**
 * Diese Ausnahme wird ausgelöst, wenn eine ungültige Artikelbezeichnung verwendet wird.
 *
 * <p>Die Klasse {@code UngueltigeArtikelBezeichnung} erweitert die {@code Exception}-Klasse und wird verwendet,
 * um anzuzeigen, dass eine Artikelbezeichnung nicht den erforderlichen Anforderungen oder Formaten entspricht.</p>
 *
 * @see java.lang.Exception
 */
public class UngueltigeArtikelBezeichnung extends Exception{
    /**
     * Konstruktor, der eine benutzerdefinierte Fehlermeldung erstellt.
     *
     * <p>Der Konstruktor nimmt eine Fehlermeldung entgegen, die spezifische Informationen über die
     * ungültige Artikelbezeichnung bereitstellt.</p>
     *
     * @param message die Fehlermeldung, die bei der Ausnahme angezeigt werden soll
     */
    public UngueltigeArtikelBezeichnung(String message) {
        super(message);
    }
}
