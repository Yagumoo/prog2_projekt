package eshop.domain;

import eshop.domain.exceptions.DoppelteIdException;
import eshop.enitities.*;

import java.util.Date;
import java.util.Map;
import java.util.List;

public class E_Shop {

    private ArtikelManagement artikelManagement = new ArtikelManagement();
    private MitarbeiterManagement mitarbeiterManagement = new MitarbeiterManagement();
    private KundenManagement kundenManagement = new KundenManagement();
    private Warenkorb warenkorb = new Warenkorb();
    private EreignisManagement ereignisManagement = new EreignisManagement();
    // => WarenkorbManagement
    //private MitarbeiterManagement mitarbeiterManagement = new MitarbeiterManagement(artikelManagement);

    public E_Shop() {

    }

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
        Person mitarbeiter = mitarbeiterManagement.getEingeloggterMitarbeiter();
        ereignisManagement.addEreignis(new Ereignis(new Date(), artikelbezeichnung, artikelbestand, mitarbeiter, Ereignis.EreignisTyp.NEU));
    }

    public List<Ereignis> getEreignisListe(){
        return ereignisManagement.getEreignisse();
    }

    public void addMitarbeiter(String vorname, String nachname, String email, String username, String password, int id) throws DoppelteIdException {
        mitarbeiterManagement.addMitarbeiter(vorname, nachname, email, username, password, id);
        Person mitarbeiter = mitarbeiterManagement.getEingeloggterMitarbeiter();
    }

    public void addKunde(String vorname, String nachname, String email, String username, String password, int id, String ort, int plz, String strasse, int strassenNummer) throws  DoppelteIdException {
        kundenManagement.addKunde(vorname, nachname, email, username, password, id, ort, plz, strasse, strassenNummer);
    }

    public boolean loginMitarbeiter(String usernameOrEmail, String password){
        return mitarbeiterManagement.loginMitarbeiter(usernameOrEmail, password);
    }

    public Kunde loginKunde(String usernameOrEmail, String password){
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
        Artikel a = artikelManagement.gibArtikelPerId(artikelnummer);
        if(a != null){
            Person mitarbeiter = mitarbeiterManagement.getEingeloggterMitarbeiter();
            ereignisManagement.addEreignis(new Ereignis(new Date(), a.getArtikelbestand(), a.getArtikelbestand(), mitarbeiter, Ereignis.EreignisTyp.ERHOEHUNG));
            return artikelManagement.aendereArtikelBestand(artikelnummer, neuerBestand);
        }else{
            return false;
        }
    }

    //Warenkorb
    //public void artikelInWarenkorbHinzufuegen1(Kunde kunde, Artikel artikel, int menge){
    public void artikelInWarenkorbHinzufuegen1(Artikel artikel, int menge){
        // 1. Artikelbestand im ArtikelManagement prüfen
        // 2. Wenn ok: Artikel über WarenkorbManagement hinzufügen
        Kunde k = kundenManagement.getEingeloggterKunde();
        Warenkorb wk = k.getWarenkorb();
        wk.artikelHinzufuegen(artikel, menge);
    }

    public String printWarenkorbRechnung(){
        Kunde k = kundenManagement.getEingeloggterKunde();
        Warenkorb wk = k.getWarenkorb();
        return wk.gibtRechnung();
    }

    public double gesamtPreis(){
        Kunde k = kundenManagement.getEingeloggterKunde();
        Warenkorb wk = k.getWarenkorb();
        return wk.gesamtPreis();
    }

    public void warenkorbLeeren(){
        Kunde k = kundenManagement.getEingeloggterKunde();
        Warenkorb wk = k.getWarenkorb();
        wk.warenkorbLeeren();
    }

    public void warenkorbKaufen(){
        Kunde k = kundenManagement.getEingeloggterKunde();
        Warenkorb wk = k.getWarenkorb();
        wk.warenkorbKaufen();
    }

    public void bestandImWarenkorbAendern(Artikel artikel, int menge){
        Kunde k = kundenManagement.getEingeloggterKunde();
        Warenkorb wk = k.getWarenkorb();
        wk.bestandImWarenkorbAendern(artikel, menge);
    }

    public void artikelImWarenkorbEntfernen(Artikel artikel){
        Kunde k = kundenManagement.getEingeloggterKunde();
        Warenkorb wk = k.getWarenkorb();
        wk.artikelEntfernen(artikel);
    }

}


