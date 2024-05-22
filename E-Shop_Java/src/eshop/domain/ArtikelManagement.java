package eshop.domain;
import java.util.*;

import eshop.domain.exceptions.DoppelteIdException;
import eshop.enitities.Artikel;
import eshop.enitities.Ereignis;

public class ArtikelManagement {


    private Map<Integer, Artikel> artikelListe = new HashMap<>();
    ArrayList<Artikel> artikelListe1 = new ArrayList<>(artikelListe.values());
    private EreignisManagement ereignisManagement;

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
            throw new DoppelteIdException(artikelnummer);
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
        ereignisManagement.addEreignis(new Ereignis(new Date(), a.getArtikelbestand(), a.getArtikelbestand(), mitarbeiter, Ereignis.EreignisTyp.ERHOEHUNG));
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

}






