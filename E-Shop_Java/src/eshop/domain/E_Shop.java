package eshop.domain;

import eshop.domain.exceptions.DoppelteIdException;
import eshop.enitities.Artikel;
import eshop.enitities.Kunde;
import eshop.enitities.Mitarbeiter;
import eshop.enitities.Person;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class E_Shop {

    private ArtikelManagement artikelManagement = new ArtikelManagement();
    private MitarbeiterManagement mitarbeiterManagement = new MitarbeiterManagement();
    private KundenManagement kundenManagement = new KundenManagement();
    //private MitarbeiterManagement mitarbeiterManagement = new MitarbeiterManagement(artikelManagement);

    public Map<Integer, Artikel> gibAlleArtikel() {

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

    public void addMitarbeiter(String vorname, String nachname, String email, String username, String password, int id) throws DoppelteIdException {
        mitarbeiterManagement.addMitarbeiter(vorname, nachname, email, username, password, id);
    }

    public void addKunde(String vorname, String nachname, String email, String username, String password, int id, String ort, int plz, String strasse, int strassenNummer) {
        kundenManagement.addKunde(vorname, nachname, email, username, password, id, ort, plz, strasse, strassenNummer);
    }

    public boolean loginMitarbeiter(String usernameOrEmail, String password){
        return mitarbeiterManagement.loginMitarbeiter(usernameOrEmail, password);
    }

    public boolean loginKunde(String usernameOrEmail, String password){
        return kundenManagement.loginkunde(usernameOrEmail, password);
    }

    public  boolean aendereArtikelBestand(int artikelnummer, int neuerBestand){
        return artikelManagement.aendereArtikelBestand(artikelnummer, neuerBestand);
    }
}


