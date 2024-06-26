package eshop.domain;
import java.util.*;

import eshop.domain.exceptions.*;
import eshop.enitities.Artikel;
import eshop.enitities.Warenkorb;
import eshop.enitities.MassengutArtikel;


import eshop.persistence.filePersistenceManager;

public class ArtikelManagement {

    private filePersistenceManager fpm; // = new filePersistenceManager();
    private final Warenkorb warenkorb = new Warenkorb();
    private Map<Integer, Artikel> artikelListe = new HashMap<>();
    private final Map<String, Artikel> artikelListe2 = new HashMap<>();
    private final List<Artikel> artikelListeAsList = new ArrayList<>();
    private EreignisManagement ereignisManagement;

    public ArtikelManagement(filePersistenceManager fpm) {
        try{
            this.fpm = fpm;
            artikelListe = fpm.ladeArtikelListe("artikel.txt");

            if(artikelListe.isEmpty()){
                addArtikel(new Artikel(5, "Energy", 20, 2.49));
                addArtikel(new Artikel(2, "Laptop", 3, 1599.99));
                addArtikel(new Artikel(1, "Hähnchen", 2000, 5.99));
                addArtikel(new MassengutArtikel(9, "Bier", 100, 0.99, 6));
            }

        } catch (Exception e){
            System.out.println(e.getMessage());
        }

    }

    public void addArtikel(Artikel artikel) throws DoppelteIdException, MinusZahlException {
        if(artikel.getArtikelnummer() <=0 ||artikel.getArtikelbestand() <=0 || artikel.getArtikelPreis() <0){
            throw new MinusZahlException();

        }
        if (sucheArtikel(artikel.getArtikelnummer())) {
            throw new DoppelteIdException(artikel.getArtikelnummer());
        } else {
            artikelListe.put(artikel.getArtikelnummer(), artikel);
        }
    }

    public boolean aendereArtikelBestand(int artikelnummer, int neuerBestand) throws MinusZahlException{

        Artikel artikel = artikelListe.get(artikelnummer);
        if (artikel != null) {

            artikel.setArtikelbestand(neuerBestand);
            if(neuerBestand <=0 ) {
                throw new MinusZahlException();
            }else {
                return true;
            }
        }
        return false;
    }

    public void bestandAbbuchen(Warenkorb warenkorb) throws BestandNichtAusreichendException {

        for (Map.Entry<Artikel, Integer> entry : warenkorb.getWarenkorbMap().entrySet()) {
            Artikel artikel = entry.getKey();
            int menge = entry.getValue();
            int aktuellerBestand = artikel.getArtikelbestand();
            if (artikel.getArtikelbestand() < menge) {
                throw new BestandNichtAusreichendException(artikel, aktuellerBestand);
            }
        }

        for (Map.Entry<Artikel, Integer> entry : warenkorb.getWarenkorbMap().entrySet()) {
            Artikel artikel = entry.getKey();
            int menge = entry.getValue();
            artikel.setArtikelbestand(artikel.getArtikelbestand() - menge);
        }
    }

    public Map<Integer, Artikel> gibAlleArtikel() {

        return artikelListe;
    }

    public void loescheArtikel(int artikelnummer) throws IdNichtVorhandenException {
        if (!artikelListe.containsKey(artikelnummer)) {
            throw new IdNichtVorhandenException(artikelnummer);
        }
        artikelListe.remove(artikelnummer);
    }

    public Artikel gibArtikelPerName(String artikelbezeichnung)throws ArtikelExisitiertNichtException{
        if (!artikelListe.containsKey(artikelbezeichnung)) {
            throw new ArtikelExisitiertNichtException(artikelbezeichnung);
        }
        return artikelListe.get(artikelbezeichnung);
    }

    public Artikel gibArtikelPerId(int artikelnummer)throws IdNichtVorhandenException{
        if (!artikelListe.containsKey(artikelnummer)) {
            throw new IdNichtVorhandenException(artikelnummer);
        }
        return artikelListe.get(artikelnummer);
    }

    public boolean sucheArtikel(int artikelnummer){
        return artikelListe.containsKey(artikelnummer);
    }

}






