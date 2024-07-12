package eshop.common.exceptions;

import eshop.common.enitities.Artikel;

public class BestandNichtAusreichendException extends Exception {
    public BestandNichtAusreichendException(Artikel artike, int aktuellerBestand) {
        super("Der Artikel: " + artike.getArtikelbezeichnung() + " hat nicht genug auf Lager. Es sind nur noch " + aktuellerBestand + " Einheiten vorhanden.");
    }
}




