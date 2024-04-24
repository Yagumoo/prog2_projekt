package eshop.domain;

import eshop.enitities.Artikel;

import java.util.List;

public class E_Shop {

    private ArtikelManagement artikelManagement = new ArtikelManagement();

    public List<Artikel> gibAlleArtikel() {

        return artikelManagement.gibAlleArtikel();
    }
}
