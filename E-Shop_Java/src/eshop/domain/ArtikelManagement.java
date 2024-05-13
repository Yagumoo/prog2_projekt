package eshop.domain;
import java.util.ArrayList;
import java.util.List;

import eshop.domain.exceptions.DoppelteIdException;
import eshop.enitities.Artikel;
import java.util.Map;
import java.util.HashMap;

public class ArtikelManagement {


    private Map<Integer, Artikel> artikelListe = new HashMap<>();
    ArrayList<Artikel> artikelListe1 = new ArrayList<>(artikelListe.values());

    public ArtikelManagement() {
        try{
            addArtikel(5, "Energy", 20, 2.49);
            addArtikel(2, "Laptop", 3, 1599.99);
            addArtikel(1, "Hähnchen", 2000, 5.99);
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void addArtikel(int artikelnummer, String artikelbezeichnung, int artikelbestand, double artikelPreis) throws DoppelteIdException {
        if(sucheArtikel(artikelnummer)){

        }else{
            Artikel artikel = new Artikel(artikelnummer, artikelbezeichnung, artikelbestand, artikelPreis);
            artikelListe.put(artikelnummer, artikel);
        }
    }

    public boolean aendereArtikelBestand(int artikelnummer, int neuerBestand) {
        Artikel artikel = artikelListe.get(artikelnummer);
        if (artikel != null) {
            artikel.setArtikelbestand(neuerBestand);
            return true;
        }
        return false;
    }


    public Map<Integer, Artikel> gibAlleArtikel() {

        return artikelListe;
    }

    public Artikel gibArtikelPerId(int artikelnummer){
        return artikelListe.get(artikelnummer);
    }

    public boolean sucheArtikel(int artikelnummer){
        return artikelListe.containsKey(artikelnummer);
    }

    public void warenkorbleeren(Map<Artikel, Integer> warenkorb) {
    // Clear the Warenkorb
    for (Map.Entry<Artikel, Integer> entry : warenkorb.entrySet()) {
        entry.setValue(0);
    }
}
}






