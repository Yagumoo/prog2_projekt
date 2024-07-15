package eshop.common.exceptions;
/**
 * Wird geworfen, wenn versucht wird, einen Artikel mit einem Namen hinzuzufügen, der bereits existiert.
 *
 * <p>Diese Ausnahme wird verwendet, um anzuzeigen, dass der Artikelname bereits in der Datenbank oder der Liste vorhanden ist.</p>
 */
public class ArtikelnameDoppeltException extends Exception{
    /**
     * Erstellt eine neue {@code ArtikelnameDoppeltException} mit der angegebenen Artikelbezeichnung.
     *
     * @param artikelbezeichnung der Name des Artikels, der bereits existiert
     */
    public ArtikelnameDoppeltException(String artikelbezeichnung){
        super("Der Artikelname " + artikelbezeichnung +" exestiert bereits" );
    }
}
