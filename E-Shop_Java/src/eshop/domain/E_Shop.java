package eshop.domain;

import eshop.domain.exceptions.DoppelteIdException;
import eshop.enitities.Artikel;
import eshop.enitities.Kunde;
import eshop.enitities.Person;
import eshop.enitities.Warenkorb;

import java.util.Map;

public class E_Shop {

    private ArtikelManagement artikelManagement = new ArtikelManagement();
    private MitarbeiterManagement mitarbeiterManagement = new MitarbeiterManagement();
    private KundenManagement kundenManagement = new KundenManagement();
    private Warenkorb warenkorb = new Warenkorb();
    //private MitarbeiterManagement mitarbeiterManagement = new MitarbeiterManagement(artikelManagement);

    public Map<Integer, Artikel> gibAlleArtikel() {

        return artikelManagement.gibAlleArtikel();
    }

    public  Map<Integer, Person> gibAlleMitarbeiter() {
        return mitarbeiterManagement.gibAlleMitarbeiter();
    }

    public  Map<Integer, Kunde> gibAlleKunden() {
        return kundenManagement.gibAlleKunden();
    }

    public void addArtikel(int artikelnummer, String artikelbezeichnung, int artikelbestand, double artikelPreis) throws DoppelteIdException {
        artikelManagement.addArtikel(artikelnummer, artikelbezeichnung, artikelbestand, artikelPreis);
    }

    public void addMitarbeiter(String vorname, String nachname, String email, String username, String password, int id) throws DoppelteIdException {
        mitarbeiterManagement.addMitarbeiter(vorname, nachname, email, username, password, id);
    }

    public void addKunde(String vorname, String nachname, String email, String username, String password, int id, String ort, int plz, String strasse, int strassenNummer) throws  DoppelteIdException {
        kundenManagement.addKunde(vorname, nachname, email, username, password, id, ort, plz, strasse, strassenNummer);
    }

    public boolean loginMitarbeiter(String usernameOrEmail, String password){
        return mitarbeiterManagement.loginMitarbeiter(usernameOrEmail, password);
    }

    public boolean loginKunde(String usernameOrEmail, String password){
        return kundenManagement.loginkunde(usernameOrEmail, password);
    }

    public Kunde getEingeloggterKunde() {
        return kundenManagement.gibKundePerId(1); // oder die entsprechende Logik, um den eingeloggten Kunden zu identifizieren
    }

    public void setEingeloggterKunde(Kunde kunde) {
        kundenManagement.setEingeloggterKunde(kunde);
    }

    public Artikel sucheArtikelMitNummer(int artikelnummer){
        return artikelManagement.gibArtikelPerId(artikelnummer);
    }

    public  boolean aendereArtikelBestand(int artikelnummer, int neuerBestand){
        return artikelManagement.aendereArtikelBestand(artikelnummer, neuerBestand);
    }

    //Warenkorb

    public void artikelInWarenkorbHinzufuegen1(Artikel artikel, int menge){
        warenkorb.artikelHinzufuegen(artikel, menge);
    }

    public String printWarenkorbRechnung(){
        return warenkorb.gibtRechnung();
    }

    public double gesamtPreis(){
        return warenkorb.gesamtPreis();
    }

    public void warenkorbLeeren(){
        warenkorb.warenkorbLeeren();
    }

    public void warenkorbKaufen(){
        warenkorb.warenkorbKaufen();
    }

    public void bestandImWarenkorbAendern(Artikel artikel, int menge){
        warenkorb.bestandImWarenkorbAendern(artikel, menge);
    }

    public void artikelImWarenkorbEntfernen(Artikel artikel){
        warenkorb.artikelEntfernen(artikel);
    }

}


