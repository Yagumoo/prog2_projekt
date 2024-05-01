package eshop.domain;

import eshop.enitities.Artikel;
import eshop.enitities.Kunde;
import eshop.enitities.Mitarbeiter;
import eshop.enitities.Person;

import java.util.List;

public class E_Shop {

    private ArtikelManagement artikelManagement = new ArtikelManagement();
    private MitarbeiterManagement mitarbeiterManagement = new MitarbeiterManagement();
    private KundenManagement kundenManagement = new KundenManagement();
    //private MitarbeiterManagement mitarbeiterManagement = new MitarbeiterManagement(artikelManagement);

    public List<Artikel> gibAlleArtikel() {

        return artikelManagement.gibAlleArtikel();
    }

    public  List<Person> gibAlleMitarbeiter() {
        return mitarbeiterManagement.gibAlleMitarbeiter();
    }

    public  List<Kunde> gibAlleKunden() {
        return kundenManagement.gibAlleKunden();
    }

    public void addArtikel(int artikelnummer, String artikelbezeichnung, int artikelbestand, double artikelPreis) {
        artikelManagement.addArtikel(artikelnummer, artikelbezeichnung, artikelbestand, artikelPreis);
    }

    public void addMitarbeiter(String vorname, String nachname, String email, String username, String password, int id) {
        mitarbeiterManagement.addMitarbeiter(vorname, nachname, email, username, password, id);
    }

    public  boolean aendereArtikelBestand(int artikelnummer, int neuerBestand){
        return artikelManagement.aendereArtikelBestand(artikelnummer, neuerBestand);
    }
}


