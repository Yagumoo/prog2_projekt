package eshop.domain;


import eshop.domain.exceptions.*;
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

    public void addArtikel(Artikel artikel) throws DoppelteIdException {
        artikelManagement.addArtikel(artikel);
        Person mitarbeiter = mitarbeiterManagement.getEingeloggterMitarbeiter();
        Ereignis neuesEreignis = new Ereignis(new Date(), artikel.getArtikelbezeichnung(), artikel.getArtikelbestand(), mitarbeiter, Ereignis.EreignisTyp.NEU);
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
        try{
            return artikelManagement.gibArtikelPerId(artikelnummer);
        } catch (IdNichtVorhandenException e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    public boolean aendereArtikelBestand(int artikelnummer, int neuerBestand) {
        try {
            Artikel artikel = artikelManagement.gibArtikelPerId(artikelnummer);

            // Überprüfen Sie, ob der eingeloggte Mitarbeiter existiert
            Person mitarbeiter = mitarbeiterManagement.getEingeloggterMitarbeiter();

            // Ändern des Artikelbestands und das Ergebnis der Operation speichern
            boolean bestandGeaendert = artikelManagement.aendereArtikelBestand(artikelnummer, neuerBestand);

            if (bestandGeaendert) {
                // Erstellen eines neuen Ereignisses und Hinzufügen zum Ereignis-Management

                Ereignis neuesEreignis = new Ereignis(new Date(), artikel.getArtikelbezeichnung(), neuerBestand, mitarbeiter, Ereignis.EreignisTyp.ERHOEHUNG);
                ereignisManagement.addEreignis(mitarbeiter, neuesEreignis);
            }

            // Rückgabewert entsprechend dem Ergebnis der Bestandsänderung
            return bestandGeaendert;
        } catch (IdNichtVorhandenException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }


    //Warenkorb
    //public void artikelInWarenkorbHinzufuegen1(Kunde kunde, Artikel artikel, int menge){
    public void artikelInWarenkorbHinzufuegen1(Kunde kunde, int artikelnummer, int menge){
        // 1. Artikelbestand im ArtikelManagement prüfen

        try{
            Artikel artikel = artikelManagement.gibArtikelPerId(artikelnummer);
            if (artikel == null) {
                System.out.println("Artikel mit der angegebenen Artikelnummer nicht gefunden.");
                return; // Beendet Methode, wenn der Artikel nicht gefunden wurde
            }
            warenkorbManagement.artikelInWarenkorbHinzufuegen(kunde, artikel, menge);
        }catch (IdNichtVorhandenException e){
            System.out.println(e.getMessage());
        }

        // 2. Wenn ok: Artikel über WarenkorbManagement hinzufügen
//        Kunde k = kundenManagement.getEingeloggterKunde();
//        Warenkorb wk = kunde.getWarenkorb();
//        wk.artikelHinzufuegen(artikel, menge);

        Artikel artikel = artikelManagement.gibArtikelPerId(artikelnummer);
        if (artikel == null) {
            System.out.println("Artikel mit der angegebenen Artikelnummer nicht gefunden.");
            return; // Beendet Methode, wenn der Artikel nicht gefunden wurde
        }else {
            Kunde k = kundenManagement.getEingeloggterKunde();
            Warenkorb wk = kunde.getWarenkorb();
            wk.artikelHinzufuegen(artikel, menge);
            warenkorbManagement.artikelInWarenkorbHinzufuegen(kunde, artikel, menge);
        }




    }

    public String printWarenkorbArtikel(){
        Kunde kunden = kundenManagement.getEingeloggterKunde();
        Warenkorb wk = kunden.getWarenkorb();
        return wk.toString();
    }

    public double gesamtPreis(){
        Kunde k = kundenManagement.getEingeloggterKunde();
        Warenkorb wk = k.getWarenkorb();
        return wk.gesamtPreis();
    }


    public void warenkorbLeeren() {
        Kunde kunden = kundenManagement.getEingeloggterKunde();
        warenkorbManagement.warenkorbLeeren(kunden);
    }

    public Rechnung warenkorbKaufen() throws BestandNichtAusreichendException {
        Kunde kunden = kundenManagement.getEingeloggterKunde();

        // bestandAbbuchen wirft eine BestandNichtAusreichendException, wenn der Bestand nicht ausreicht
        Warenkorb wk = kunden.getWarenkorb();
        artikelManagement.bestandAbbuchen(wk);

        // Füge Ereignisse für alle Einträge im Warenkorb hinzu
        for (Map.Entry<Artikel, Integer> entry : wk.getWarenkorbMap().entrySet()) {
            Artikel artikel = entry.getKey();
            int menge = entry.getValue();
            Ereignis neuesEreignis = new Ereignis(new Date(), artikel.getArtikelbezeichnung(), menge, kunden, Ereignis.EreignisTyp.KAUF);
            ereignisManagement.addEreignis(kunden, neuesEreignis);

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

        return warenkorbManagement.warenkorbKaufen(kunden);

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


