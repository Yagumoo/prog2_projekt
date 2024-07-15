package eshop.common.exceptions;
public class ArtikelnameDoppeltException extends Exception{
    public ArtikelnameDoppeltException(String artikelbezeichnung){
        super("Der Artikelname " + artikelbezeichnung +" exestiert bereits" );
    }
}
