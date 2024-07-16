package eshop.common.exceptions;

import eshop.common.enitities.Artikel;
/**
 * Diese Ausnahme wird ausgelöst, wenn der Bestand eines Artikels nicht ausreicht, um eine bestimmte
 * Menge zu decken.
 *
 * <p>Die Klasse {@code BestandNichtAusreichendException} erweitert die {@code Exception}-Klasse und wird
 * verwendet, um eine spezifische Fehlermeldung anzugeben, wenn ein Artikel nicht genügend auf Lager hat.</p>
 *
 * @see java.lang.Exception
 */

public class BestandNichtAusreichendException extends Exception {
    /**
     * Diese Ausnahme wird ausgelöst, wenn der Bestand eines Artikels nicht ausreicht, um eine bestimmte
     * Menge zu decken.
     *
     * <p>Die Klasse {@code BestandNichtAusreichendException} erweitert die {@code Exception}-Klasse und wird
     * verwendet, um eine spezifische Fehlermeldung anzugeben, wenn ein Artikel nicht genügend auf Lager hat.</p>
     *
     * @see java.lang.Exception
     */
    public BestandNichtAusreichendException(Artikel artike, int aktuellerBestand) {
        super("Der Artikel: " + artike.getArtikelbezeichnung() + " hat nicht genug auf Lager. Es sind nur noch " + aktuellerBestand + " Einheiten vorhanden.");
    }

    public BestandNichtAusreichendException() {
        super("Der Artikel hat nicht genug auf Lager.");
    }
}




