package eshop.domain;

import eshop.domain.exceptions.DoppelteIdException;
import eshop.domain.exceptions.LoginException;
import eshop.enitities.*;

import java.util.Date;
import java.util.Map;
import java.util.List;

public class E_Shop {

    private ArtikelManagement artikelManagement = new ArtikelManagement();
    private MitarbeiterManagement mitarbeiterManagement = new MitarbeiterManagement();
    private KundenManagement kundenManagement = new KundenManagement();
    private Warenkorb warenkorb = new Warenkorb();
    private WarenkorbManagement warenkorbManagement = new WarenkorbManagement();
    private EreignisManagement ereignisManagement = new EreignisManagement();
    // => WarenkorbManagement
    //private MitarbeiterManagement mitarbeiterManagement = new MitarbeiterManagement(artikelManagement);

    public E_Shop() {

    }

    public Map<Integer, Artikel> gibAlleArtikel() {
        return artikelManagement.gibAlleArtikel();
    }

    public  Map<Integer, Mitarbeiter> gibAlleMitarbeiter() {
        return mitarbeiterManagement.gibAlleMitarbeiter();
    }

    public  Map<Integer, Kunde> gibAlleKunden() {
        return kundenManagement.gibAlleKunden();
    }

    public void addArtikel(int artikelnummer, String artikelbezeichnung, int artikelbestand, double artikelPreis) throws DoppelteIdException {
        artikelManagement.addArtikel(artikelnummer, artikelbezeichnung, artikelbestand, artikelPreis);
        Person mitarbeiter = mitarbeiterManagement.getEingeloggterMitarbeiter();
        Ereignis neuesEreignis = new Ereignis(new Date(), artikelbezeichnung, artikelbestand, mitarbeiter, Ereignis.EreignisTyp.NEU);
        ereignisManagement.addEreignis(mitarbeiter, neuesEreignis);
    }

    public List<Ereignis> getEreignisListe(){
        return ereignisManagement.getEreignisse();
    }

    public void addMitarbeiter(String vorname, String nachname, String email, String username, String password) throws DoppelteIdException {
        mitarbeiterManagement.addMitarbeiter(vorname, nachname, email, username, password);
        Person mitarbeiter = mitarbeiterManagement.getEingeloggterMitarbeiter();
    }

    public void addKunde(String vorname, String nachname, String email, String username, String password, String ort, int plz, String strasse, int strassenNummer) throws  DoppelteIdException {
        kundenManagement.addKunde(vorname, nachname, email, username, password, ort, plz, strasse, strassenNummer);
    }

    public boolean loginMitarbeiter(String usernameOrEmail, String password) throws LoginException{
        return mitarbeiterManagement.loginMitarbeiter(usernameOrEmail, password);
    }

    public Kunde loginKunde(String usernameOrEmail, String password)throws LoginException {
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

    public boolean aendereArtikelBestand(int artikelnummer, int neuerBestand) {
        // Überprüfen Sie, ob artikelManagement und mitarbeiterManagement deklariert und initialisiert sind
        Artikel a = artikelManagement.gibArtikelPerId(artikelnummer);

        // Überprüfen Sie, ob mitarbeiterManagement deklariert und initialisiert ist
        Person mitarbeiter = mitarbeiterManagement.getEingeloggterMitarbeiter();

        // Erstellen eines neuen Ereignisses und Hinzufügen zum Ereignis-Management
        Ereignis neuesEreignis = new Ereignis(new Date(), a.getArtikelbezeichnung(), neuerBestand, mitarbeiter, Ereignis.EreignisTyp.ERHOEHUNG);
        ereignisManagement.addEreignis(mitarbeiter, neuesEreignis);

        // Ändern des Artikelbestands und das Ergebnis der Operation zurückgeben
        return artikelManagement.aendereArtikelBestand(artikelnummer, neuerBestand);
    }

    //Warenkorb
    //public void artikelInWarenkorbHinzufuegen1(Kunde kunde, Artikel artikel, int menge){
    public void artikelInWarenkorbHinzufuegen1(Kunde kunde, int artikelnummer, int menge){
        // 1. Artikelbestand im ArtikelManagement prüfen
        Artikel artikel = artikelManagement.gibArtikelPerId(artikelnummer);
        if (artikel == null) {
            System.out.println("Artikel mit der angegebenen Artikelnummer nicht gefunden.");
            return; // Beendet Methode, wenn der Artikel nicht gefunden wurde
        }
        // 2. Wenn ok: Artikel über WarenkorbManagement hinzufügen
//        Kunde k = kundenManagement.getEingeloggterKunde();
//        Warenkorb wk = kunde.getWarenkorb();
//        wk.artikelHinzufuegen(artikel, menge);
        warenkorbManagement.artikelInWarenkorbHinzufuegen(kunde, artikel, menge);
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

    public void warenkorbLeeren() {
        warenkorbManagement.warenkorbLeeren(kunde);
        Kunde k = kundenManagement.getEingeloggterKunde();
        Warenkorb wk = k.getWarenkorb();
        wk.warenkorbLeeren();
    }

    public void warenkorbKaufen() throws BestandNichtAusreichendException {
        Kunde k = kundenManagement.getEingeloggterKunde();
        Warenkorb wk = k.getWarenkorb();
        if (artikelManagement.bestandAbbuchen(wk)){ // Kann BestandNichtAusreichendException werfen
            throw new BestandNichtAusreichendException();
        }
        ereignisManagement.addEreignis(k, wk); // für alle Einträge im Warenkorb
        warenkorbManagement.rechnungErstellen(k);
        warenkorbManagement.warenkorbLeeren(k);
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


