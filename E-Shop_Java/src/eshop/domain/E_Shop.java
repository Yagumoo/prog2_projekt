package eshop.domain;

import eshop.enitities.Artikel;
import eshop.enitities.Mitarbeiter;

import java.util.List;

public class E_Shop {

    private ArtikelManagement artikelManagement = new ArtikelManagement();
    //private MitarbeiterManagement mitarbeiterManagement = new MitarbeiterManagement(artikelManagement);

    public List<Artikel> gibAlleArtikel() {

        return artikelManagement.gibAlleArtikel();
    }

    public void addArtikel(int artikelnummer, String artikelbezeichnung, int artikelbestand, double artikelPreis) {
        artikelManagement.addArtikel(artikelnummer, artikelbezeichnung, artikelbestand, artikelPreis);
    }

    public  boolean aendereArtikelBestand(int artikelnummer, int neuerBestand){
        return artikelManagement.aendereArtikelBestand(artikelnummer, neuerBestand);
    }


}


