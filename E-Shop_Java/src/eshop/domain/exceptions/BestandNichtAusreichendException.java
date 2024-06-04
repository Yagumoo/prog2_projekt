package eshop.domain.exceptions;

import eshop.enitities.Artikel;

public class BestandNichtAusreichendException extends Exception {
    public BestandNichtAusreichendException(Artikel artikelbezeichnung) {
        super("Der Artikel: " + artikelbezeichnung.getArtikelbezeichnung() + " ist ausverkauft");
    }
}
