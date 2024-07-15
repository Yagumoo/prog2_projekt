package eshop.common.exceptions;
/**
 * Diese Ausnahme wird ausgelöst, wenn versucht wird, auf einen nicht existierenden Artikel zuzugreifen.
 *
 * <p>Die Klasse {@code ArtikelExisitiertNichtException} erweitert die {@code Exception}-Klasse und wird
 * verwendet, um eine spezifische Fehlermeldung anzugeben, wenn ein Artikel nicht existiert.</p>
 *
 * @see java.lang.Exception
 */

public class ArtikelExisitiertNichtException extends Exception{
    /**
     * Konstruktor, der eine Fehlermeldung mit der Artikelbezeichnung erstellt.
     *
     * @param artikelbezeichnung die Bezeichnung des nicht existierenden Artikels
     */
    public ArtikelExisitiertNichtException(String artikelbezeichnung) {
        super("Der Artikel: " + artikelbezeichnung + " existiert nicht");
    }
}
