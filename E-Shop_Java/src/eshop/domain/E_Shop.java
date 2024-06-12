package eshop.domain;


import eshop.domain.exceptions.*;
import eshop.enitities.*;
import eshop.persistence.filePersistenceManager;

import java.util.Date;
import java.util.Map;
import java.util.List;
import java.io.IOException;



public class E_Shop {

    private ArtikelManagement artikelManagement; // = new ArtikelManagement();
    private MitarbeiterManagement mitarbeiterManagement; //= new MitarbeiterManagement();
    private KundenManagement kundenManagement; //= new KundenManagement();
    private WarenkorbManagement warenkorbManagement; //= new WarenkorbManagement();
    private EreignisManagement ereignisManagement;//= new EreignisManagement();
    private filePersistenceManager fpm; //  = new filePersistenceManager();
    // => WarenkorbManagement
    //private MitarbeiterManagement mitarbeiterManagement = new MitarbeiterManagement(artikelManagement);

    public E_Shop() {
        fpm = new filePersistenceManager();
        artikelManagement = new ArtikelManagement(fpm);
        mitarbeiterManagement = new MitarbeiterManagement(fpm);
        kundenManagement = new KundenManagement(fpm);
        warenkorbManagement = new WarenkorbManagement();
        ereignisManagement = new EreignisManagement(fpm, kundenManagement.gibAlleKunden(), mitarbeiterManagement.gibAlleMitarbeiter());

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Speichern der Listen beim Beenden...");
            saveAlleListen();
            System.out.println("Speichern abgeschlossen.");
        }));
    }

    public void ListeVonArtikel() {
        Map<Integer, Artikel> artikel = artikelManagement.gibAlleArtikel();
        artikel.forEach((arikelnummer, artikelbezeichnung)-> {
            System.out.println("Gesamt Preis: "+ artikelbezeichnung);
        });
    }

    public void ListeVonMitarbeiter(){
        Map<Integer, Mitarbeiter> mitarbeiter = mitarbeiterManagement.gibAlleMitarbeiter();
        mitarbeiter.forEach((mitarbeiterId, mitarbeiterDaten)-> {
            System.out.println(mitarbeiterDaten.toString());
        });
    }

    public void ListeVonKunden(){
        Map<Integer, Kunde> kunden = kundenManagement.gibAlleKunden();
        kunden.forEach((kundeId, kundeDaten)-> {
            System.out.println(kundeDaten.toString());
        });
    }

    public void ListeVonWarenkorb(Person kunde){
        System.out.println(printWarenkorbArtikel(kunde));
        System.out.println("Gesamt Preis: "+ gesamtPreis(kunde));
    }

    public void EreignisListeAusgeben(){
        for (Ereignis ereignisListe : getEreignisListe()){
            System.out.println(ereignisListe);
        }
    }

    public void addArtikel(Artikel artikel) throws DoppelteIdException {
        artikelManagement.addArtikel(artikel);
        Person mitarbeiter = mitarbeiterManagement.getEingeloggterMitarbeiter();
        Ereignis neuesEreignis = new Ereignis(new Date(), artikel.getArtikelbezeichnung(), artikel.getArtikelbestand(), mitarbeiter, Ereignis.EreignisTyp.NEU);
        ereignisManagement.addEreignis(/*mitarbeiter,*/ neuesEreignis);
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

    public Mitarbeiter loginMitarbeiter(String usernameOrEmail, String password) throws LoginException{
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
           // System.out.println(e.getMessage());
            return null;
        }
    }

    public void loescheArtikel(int artikelnummer) throws IdNichtVorhandenException {
        sucheArtikelMitNummer(artikelnummer);
        artikelManagement.loescheArtikel(artikelnummer);

    }

    public boolean aendereArtikelBestand(int artikelnummer, int neuerBestand) {
        try {
            Artikel artikel = artikelManagement.gibArtikelPerId(artikelnummer);

            // Überprüfen Sie, ob der eingeloggte Mitarbeiter existiert
            Person mitarbeiter = mitarbeiterManagement.getEingeloggterMitarbeiter();

            //Aktuellen Artikelbestand Speichern
            int aktuellerBestand = artikel.getArtikelbestand();

            int differenz = neuerBestand - aktuellerBestand;

            // Ändern des Artikelbestands und das Ergebnis der Operation speichern
            boolean bestandGeaendert = artikelManagement.aendereArtikelBestand(artikelnummer, neuerBestand);

            if (bestandGeaendert) {
                // Erstellen eines neuen Ereignisses und Hinzufügen zum Ereignis-Management
                Ereignis.EreignisTyp ereignisTyp;

                if(differenz < 0){
                    ereignisTyp = Ereignis.EreignisTyp.REDUZIERUNG;

                }else{
                    ereignisTyp = Ereignis.EreignisTyp.ERHOEHUNG;

                }
                // Erstellen eines neuen Ereignisses und Hinzufügen zum Ereignis-Management
                Ereignis neuesEreignis = new Ereignis(new Date(), artikel.getArtikelbezeichnung(), differenz, mitarbeiter, ereignisTyp);
                ereignisManagement.addEreignis(/*mitarbeiter,*/ neuesEreignis);
                System.out.println("Artikel wurde erfolgreich geändert");
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
    public void artikelInWarenkorbHinzufuegen(Person kunde, int artikelnummer, int menge){
        if(kunde instanceof Kunde k){
            try{
                warenkorbManagement.warenkorbHinzufuegen(k);
                Artikel artikel = artikelManagement.gibArtikelPerId(artikelnummer);
                if (artikel != null) {
                    System.out.println("Artikel erfolgreich hinzugefügt.");
                }
                warenkorbManagement.artikelInWarenkorbHinzufuegen(k, artikel, menge);
            }catch (IdNichtVorhandenException e){
                System.out.println(e.getMessage());
            }
        }
    }


    public String printWarenkorbArtikel(Person kunde){
        if(kunde instanceof Kunde k){
            Warenkorb wk = warenkorbManagement.getWarenkorb(k);
            return wk.toString();
        }
        return "Person ist kein Kunde";
    }


    public double gesamtPreis(Person kunde){
        if(kunde instanceof Kunde k){
            Warenkorb wk = warenkorbManagement.getWarenkorb(k);
            return wk.gesamtPreis();
        }
        return 0.0;
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


    public void bestandImWarenkorbAendern(Person kunde, Artikel artikel, int menge) {
        if(kunde instanceof Kunde k){
            int aktuellerBestand = artikel.getArtikelbestand();
            Warenkorb wk = warenkorbManagement.getWarenkorb(k);
            if(artikel != null){
                wk.bestandImWarenkorbAendern(artikel, menge);
                System.out.println("Artikel wurde erfolgreich geaendert!");
            }
            if(menge > aktuellerBestand){
                System.out.println("Es sind nur noch " + aktuellerBestand + " Einheiten vom Artikel: " + artikel.getArtikelbezeichnung() + " enthalten!");
            }
        }
    }


    public void artikelImWarenkorbEntfernen(Person kunde, Artikel artikel){
        if(kunde instanceof Kunde k){
            Warenkorb wk = warenkorbManagement.getWarenkorb(k);
            wk.artikelEntfernen(artikel);
        }
    }


    public void saveAlleListen(){
        try {
            fpm.saveArtikelListe("artikel.txt", artikelManagement.gibAlleArtikel());
            System.out.println("Artikelliste gespeichert");

            fpm.saveKundenListe("kunden.txt", kundenManagement.gibAlleKunden());
            System.out.println("Kundenliste gespeichert");

            fpm.saveMitarbeiterListe("mitarbeiter.txt", mitarbeiterManagement.gibAlleMitarbeiter());
            System.out.println("Mitarbeiterliste gespeichert");

            fpm.saveEreignisListe("ereignis.txt", ereignisManagement.getEreignisse());
            System.out.println("Ereignisliste gespeichert");

            System.out.println("Alle Listen wurden erfolgreich gespeichert.");
        } catch (IOException e) {
            System.err.println("Fehler beim Speichern der Listen: " + e.getMessage());
            e.printStackTrace();
        }
    }
}


