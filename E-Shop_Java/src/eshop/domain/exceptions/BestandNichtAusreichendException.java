package eshop.domain.exceptions;

import eshop.enitities.Artikel;

public class BestandNichtAusreichendException extends Exception {
    public BestandNichtAusreichendException(Artikel artikelbezeichnung, int aktuellerBestand) {
        super("Der Artikel: " + artikelbezeichnung.getArtikelbezeichnung() + " hat nicht genug auf Lager. Es sind nur noch " + aktuellerBestand + " Einheiten vorhanden.");
    }
}




