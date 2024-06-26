package eshop.domain;


import eshop.domain.exceptions.*;
import eshop.enitities.*;
import eshop.persistence.filePersistenceManager;

import java.util.Date;
import java.util.Map;
import java.util.List;
import java.io.IOException;



public class E_Shop {

    private final ArtikelManagement artikelManagement; // = new ArtikelManagement();
    private final MitarbeiterManagement mitarbeiterManagement; //= new MitarbeiterManagement();
    private final KundenManagement kundenManagement; //= new KundenManagement();
    private final WarenkorbManagement warenkorbManagement; //= new WarenkorbManagement();
    private final EreignisManagement ereignisManagement;//= new EreignisManagement();
    private final filePersistenceManager fpm; //  = new filePersistenceManager();
    // => WarenkorbManagement
    //private MitarbeiterManagement mitarbeiterManagement = new MitarbeiterManagement(artikelManagement);

    public E_Shop() {
        fpm = new filePersistenceManager();
        artikelManagement = new ArtikelManagement(fpm);
        mitarbeiterManagement = new MitarbeiterManagement(fpm);
        kundenManagement = new KundenManagement(fpm);
        warenkorbManagement = new WarenkorbManagement();
        ereignisManagement = new EreignisManagement(fpm, kundenManagement.gibAlleKunden(), mitarbeiterManagement.gibAlleMitarbeiter());

        Runtime.getRuntime().addShutdownHook(new Thread(this::speicherAlleListen));
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

    public void addArtikel(Person mitarbeiter, Artikel artikel) throws DoppelteIdException, MinusZahlException {
        if(mitarbeiter instanceof Mitarbeiter m){
            artikelManagement.addArtikel(artikel);
            //Person mitarbeiter = mitarbeiterManagement.getEingeloggterMitarbeiter();
            Ereignis neuesEreignis = new Ereignis(new Date(), artikel.getArtikelbezeichnung(), artikel.getArtikelbestand(), m, Ereignis.EreignisTyp.NEU);
            ereignisManagement.addEreignis(/*mitarbeiter,*/ neuesEreignis);
        }
    }

    public List<Ereignis> getEreignisListe(){
        return ereignisManagement.getEreignisse();
    }

    public void addMitarbeiter(Person mitarbeiter, String vorname, String nachname, String email, String username, String password) throws DoppelteIdException {
        if(mitarbeiter instanceof Mitarbeiter){
            mitarbeiterManagement.addMitarbeiter(vorname, nachname, email, username, password);
            //Person mitarbeiter = mitarbeiterManagement.getEingeloggterMitarbeiter();
        }

    }

    public void addKunde(String vorname, String nachname, String email, String username, String password, String ort, int plz, String strasse, int strassenNummer) throws  DoppelteIdException {
        kundenManagement.addKunde(vorname, nachname, email, username, password, ort, plz, strasse, strassenNummer);
    }

    public Mitarbeiter loginMitarbeiter(String usernameOrEmail, String password) throws LoginException{
        return mitarbeiterManagement.loginMitarbeiter(usernameOrEmail, password);
    }

    public Kunde loginKunde(String usernameOrEmail, String password) throws LoginException {
        Kunde kunde = kundenManagement.loginkunde(usernameOrEmail, password);
        warenkorbManagement.warenkorbHinzufuegen(kunde);
        return kunde;
    }

    public Artikel sucheArtikelMitNummer(int artikelnummer) throws IdNichtVorhandenException{
        return artikelManagement.gibArtikelPerId(artikelnummer);
    }

    public void loescheArtikel(Person mitarbeiter, int artikelnummer) throws IdNichtVorhandenException {
        if(mitarbeiter instanceof Mitarbeiter){
            sucheArtikelMitNummer(artikelnummer);
            artikelManagement.loescheArtikel(artikelnummer);
        }
    }

    public void aendereArtikelBestand(Person mitarbeiter, int artikelnummer, int neuerBestand) throws IdNichtVorhandenException, KeinMassengutException, MinusZahlException {
        if (mitarbeiter instanceof Mitarbeiter) {
            Artikel artikel = artikelManagement.gibArtikelPerId(artikelnummer);

            // Überprüfen, ob der Artikel ein Massengutartikel ist
            if (artikel instanceof MassengutArtikel) {
                MassengutArtikel massengutArtikel = (MassengutArtikel) artikel;
                // Überprüfen, ob der neue Bestand ein Vielfaches der Massengut-Anzahl ist
                if (neuerBestand % massengutArtikel.getAnzahlMassengut() != 0) {
                    throw new KeinMassengutException(massengutArtikel.getAnzahlMassengut());
                }
            }

            // Aktuellen Artikelbestand speichern
            int aktuellerBestand = artikel.getArtikelbestand();
            int differenz = neuerBestand - aktuellerBestand;

            // Ändern des Artikelbestands und das Ergebnis der Operation speichern
            boolean bestandGeaendert = artikelManagement.aendereArtikelBestand(artikelnummer, neuerBestand);

            if (bestandGeaendert) {
                Ereignis.EreignisTyp ereignisTyp;
                if (differenz < 0) {
                    ereignisTyp = Ereignis.EreignisTyp.REDUZIERUNG;
                } else {
                    ereignisTyp = Ereignis.EreignisTyp.ERHOEHUNG;
                }

                // Erstellen eines neuen Ereignisses und Hinzufügen zum Ereignis-Management
                Ereignis neuesEreignis = new Ereignis(new Date(), artikel.getArtikelbezeichnung(), differenz, mitarbeiter, ereignisTyp);
                ereignisManagement.addEreignis(neuesEreignis);
            }
        }
    }

    //Warenkorb
    //public void artikelInWarenkorbHinzufuegen1(Kunde kunde, Artikel artikel, int menge){
    public void artikelInWarenkorbHinzufuegen(Person kunde, int artikelnummer, int menge) throws IdNichtVorhandenException, MinusZahlException, KeinMassengutException {
        if(kunde instanceof Kunde k){
            Artikel artikel = artikelManagement.gibArtikelPerId(artikelnummer);
            if (artikel != null) {
                warenkorbManagement.artikelInWarenkorbHinzufuegen(k, artikel, menge);
            }
        }
    }


    public String printWarenkorbArtikel(Person kunde) throws IstLeerException{
        if(kunde instanceof Kunde k){
            Warenkorb wk = warenkorbManagement.getWarenkorb(k);
            if(wk != null){
                return wk.toString();
            } else{
                throw new IstLeerException();
            }
        }
        return "Person ist kein Kunde";
    }


    public double gesamtPreis(Person kunde){
        if(kunde instanceof Kunde k){
            Warenkorb wk = warenkorbManagement.getWarenkorb(k);
            return wk.gesamtPreis();
        }
        return -1.0;
    }


    public void warenkorbLeeren(Person kunde) {
        if(kunde instanceof Kunde k){
            warenkorbManagement.warenkorbLeeren(k);
        }
    }


    public Rechnung warenkorbKaufen(Person kunde) throws BestandNichtAusreichendException {
        if(kunde instanceof Kunde k){
            Warenkorb wk = warenkorbManagement.getWarenkorb(k);
            artikelManagement.bestandAbbuchen(wk);

            for (Map.Entry<Artikel, Integer> entry : wk.getWarenkorbMap().entrySet()) {
                Artikel artikel = entry.getKey();
                int menge = entry.getValue();
                Ereignis neuesEreignis = new Ereignis(new Date(), artikel.getArtikelbezeichnung(), menge, k, Ereignis.EreignisTyp.KAUF);
                ereignisManagement.addEreignis(neuesEreignis);
            }
            Rechnung rechnung = new Rechnung(wk, k);
            warenkorbManagement.warenkorbKaufen(k);
            warenkorbLeeren(k);
            return rechnung;
        }
        return null;
    }

    public void bestandImWarenkorbAendern(Person kunde, Artikel artikel, int menge) throws BestandNichtAusreichendException, IdNichtVorhandenException, KeinMassengutException, MinusZahlException {
        if (kunde instanceof Kunde k) {
            int aktuellerBestand = artikel.getArtikelbestand();
            Warenkorb wk = warenkorbManagement.getWarenkorb(k);

            // Überprüfen, ob der Artikel ein Massengutartikel ist
            if (artikel instanceof MassengutArtikel) {
                MassengutArtikel massengutArtikel = (MassengutArtikel) artikel;
                // Überprüfen, ob die Menge ein Vielfaches der Massengut-Anzahl ist
                if (menge % massengutArtikel.getAnzahlMassengut() != 0) {
                    throw new KeinMassengutException(massengutArtikel.getAnzahlMassengut());
                }
            }

            wk.bestandImWarenkorbAendern(artikel, menge);
            int neuerBestand = artikel.getArtikelbestand();

            if (menge > aktuellerBestand) {
                throw new BestandNichtAusreichendException(artikel, neuerBestand);
            }
        }
    }


    public void artikelImWarenkorbEntfernen(Person kunde, Artikel artikel){
        if(kunde instanceof Kunde k){
            Warenkorb wk = warenkorbManagement.getWarenkorb(k);
            wk.artikelEntfernen(artikel);
        }
    }

    public void speicherAlleListen() {
        try {
            fpm.speicherArtikelListe("artikel.txt", artikelManagement.gibAlleArtikel());
            System.out.println("Artikelliste gespeichert");

            fpm.speicherKundenListe("kunden.txt", kundenManagement.gibAlleKunden());
            System.out.println("Kundenliste gespeichert");

            fpm.speicherMitarbeiterListe("mitarbeiter.txt", mitarbeiterManagement.gibAlleMitarbeiter());
            System.out.println("Mitarbeiterliste gespeichert");

            fpm.speicherEreignisListe("ereignis.txt", ereignisManagement.getEreignisse());
            System.out.println("Ereignisliste gespeichert");

            System.out.println("Alle Listen wurden erfolgreich gespeichert.");
        } catch (IOException e) {
            System.err.println("Fehler beim Speichern der Listen: " + e.getMessage());
            e.printStackTrace();
        }
    }
}


