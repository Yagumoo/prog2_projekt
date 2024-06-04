package eshop.domain.exceptions;

public class ArtikelExisitiertNichtException extends Exception{
    public ArtikelExisitiertNichtException(String artikelbezeichnung) {
        super("Der Artikel: " + artikelbezeichnung + " existiert nicht");
    }
}
