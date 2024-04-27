package eshop.domain;
import java.util.ArrayList;
import java.util.List;
import eshop.enitities.Artikel;

public class ArtikelManagement {


    private List<Artikel> artikelListe = new ArrayList<>();

    public ArtikelManagement() {
        addArtikel(5, "Energy", 20, 2.49);
        addArtikel(2, "Laptop", 3, 1599.99);
        addArtikel(1, "Hähnchen", 2000, 5.99);
    }

    public void addArtikel(int artikelnummer, String artikelbezeichnung, int artikelbestand, double artikelPreis) {
        Artikel artikel = new Artikel(artikelnummer, artikelbezeichnung, artikelbestand, artikelPreis);
        artikelListe.add(artikel);
    }

    public boolean aendereArtikelBestand(int artikelnummer, int neuerBestand) {
        for (Artikel artikel : artikelListe) {
            if (artikel.getArtikelnummer() == artikelnummer) {
                artikel.setArtikelbestand(neuerBestand);
                return true;
            }
        }
        return false;
    }


    public List<Artikel> gibAlleArtikel() {

        return artikelListe;
    }
}






