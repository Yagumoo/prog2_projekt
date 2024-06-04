package eshop.domain;
import java.util.*;

import eshop.domain.exceptions.DoppelteIdException;
import eshop.enitities.Artikel;
import eshop.enitities.Warenkorb;
import eshop.enitities.Ereignis;

import eshop.persistence.filePersistenceManager;

public class ArtikelManagement {

    private filePersistenceManager fpm = new filePersistenceManager();
    private Warenkorb warenkorb = new Warenkorb();
    private Map<Integer, Artikel> artikelListe = new HashMap<>();
    ArrayList<Artikel> artikelListe1 = new ArrayList<>(artikelListe.values());
    private EreignisManagement ereignisManagement;

    public ArtikelManagement() {
        try{
            artikelListe = fpm.loadArtikelListe("artikel.txt");

            if(artikelListe.isEmpty()){
                addArtikel(5, "Energy", 20, 2.49);
                addArtikel(2, "Laptop", 3, 1599.99);
                addArtikel(1, "Hähnchen", 2000, 5.99);
            }

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

            try {
                fpm.saveArtikelListe("artikel.txt", artikelListe);
            }catch (Exception e){
                System.err.println("Fehler beim Speichern der Artikel-Liste:" + e.getMessage());
            }
        }

    }

    public boolean aendereArtikelBestand(int artikelnummer, int neuerBestand) {
        Artikel artikel = artikelListe.get(artikelnummer);
        if (artikel != null) {
            artikel.setArtikelbestand(neuerBestand);
            try{
                fpm.saveArtikelListe("artikel.txt", artikelListe);
            }
            catch (Exception e){

            }


            return true;
        }
        return false;
    }

    public void bestandAbbuchen(int artikelbestand){
        artikelListe.containsKey(artikelbestand);
    }


    public Map<Integer, Artikel> gibAlleArtikel() {

        return artikelListe;
    }

    public Artikel gibArtikelPerId(int artikelnummer){
        // TODO: Exception werfen
        return artikelListe.get(artikelnummer);
    }

    public boolean sucheArtikel(int artikelnummer){
        return artikelListe.containsKey(artikelnummer);
    }

}






