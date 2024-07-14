package eshop.client.clientServerVerbindung;

import eshop.common.enitities.*;
import eshop.common.exceptions.*;

import java.util.*;
import java.net.*;
import java.io.*;

public class Eshopclientsite {
    private Socket clientSocket;
    private PrintStream out;
    private BufferedReader in;

    public Eshopclientsite(String ip, int port){
        try {
            clientSocket = new Socket(ip, port);
        } catch (IOException e){
            System.err.println("Error beim Verbindungsaufbau" + e);
        }

        try {
            // erstellt Input/Output Streams um Daten vom Server zu lesen/schreiben
            out = new PrintStream(clientSocket.getOutputStream());
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (IOException e) {
            System.err.println("Error beim erstellen von In und Out-Streams");
        }

        out.println("Moin Moin hier ist der Client mit der IP: " + ip +" und Port: "+ port);
    }

    public Map<Integer, Artikel> gibAlleArtikel() {
        Map<Integer, Artikel> alleArtikel = new HashMap<Integer, Artikel>();
        out.println("gibAlleArtikel");
        try {
            int anzahlArtikelMap = Integer.parseInt(in.readLine());
            for(int i = 0; i < anzahlArtikelMap; i++){
                int nummer = Integer.parseInt(in.readLine());
                String bezeichnung = in.readLine();
                int bestand = Integer.parseInt(in.readLine());
                double preis = Double.parseDouble(in.readLine());
                Artikel artikel = new Artikel(nummer, bezeichnung, bestand, preis);
                alleArtikel.put(nummer, artikel);
            }
        } catch (IOException e){
            System.err.println("Error beim Artikel erstellen" + e);
        }
        out.println("gibAlleMassengutartikel");
        try {
            int anzahlArtikelMap = Integer.parseInt(in.readLine());
            for(int i = 0; i < anzahlArtikelMap; i++) {
                int nummer = Integer.parseInt(in.readLine());
                String bezeichnung = in.readLine();
                int bestand = Integer.parseInt(in.readLine());
                double preis = Double.parseDouble(in.readLine());
                int massengutAnzahl = Integer.parseInt(in.readLine());
                MassengutArtikel massengutArtikel = new MassengutArtikel(nummer, bezeichnung, bestand, preis, massengutAnzahl);
                alleArtikel.put(nummer, massengutArtikel);
            }
        } catch (IOException e){
            System.err.println("Error beim lesen vom Server bei = gibAlleMassengutartikel" + e);
        }

        return alleArtikel;
    }

    public Map<Integer, Mitarbeiter> gibAlleMitarbeiter() {
        Map<Integer, Mitarbeiter> alleMitarbeiter = new HashMap<Integer, Mitarbeiter>();
        out.println("gibAlleMitarbeiter");
        try {
            int anzahlMitarbeiterMap = Integer.parseInt(in.readLine());
            for(int i = 0; i < anzahlMitarbeiterMap; i++){
                int id = Integer.parseInt(in.readLine());
                String vorname = in.readLine();
                String nachname = in.readLine();
                String email = in.readLine();
                String username = in.readLine();
                String passwort = in.readLine();
                //TODO: Richtige übergabe der ID
                Mitarbeiter mitarbeiter = new Mitarbeiter(vorname, nachname, email, username, passwort, id);
                alleMitarbeiter.put(mitarbeiter.getId(), mitarbeiter);
            }
        } catch (IOException e){
            System.err.println("Error beim lesen vom Server bei = gibAlleMitarbeiter" + e);
        }
        return alleMitarbeiter;
    }

//    public Map<Integer, Kunde> gibAlleKunden() {
//        return kundenManagement.gibAlleKunden();
//    }

    public void addArtikel(Person mitarbeiter, Artikel artikel) throws DoppelteIdException, MinusZahlException, KeinMassengutException {
        out.println("addArtikel");
        out.println(mitarbeiter.getId());
        out.println(artikel.getArtikelnummer());
        out.println(artikel.getArtikelbezeichnung());
        out.println(artikel.getArtikelPreis());
        out.println(artikel.getArtikelbestand());

        try{
            String rückfrage = in.readLine();
            switch (rückfrage) {
                case "ERROR 505":
                    throw new DoppelteIdException(mitarbeiter.getId());
                case "ERROR 606":
                    throw new MinusZahlException();
                case "ERROR 707":
                    throw new KeinMassengutException(artikel.getArtikelbestand());
            }
            //TODO: Fehler abfangen
        } catch (IOException e){
            System.err.println("Fehler beim lesen vom Server = addArtikel" + e);
        }
    }

    public void addMassengutartikel(Person mitarbeiter, MassengutArtikel massengutArtikel) throws DoppelteIdException, MinusZahlException, KeinMassengutException {
        out.println("addMassengutartikel");
        out.println(mitarbeiter.getId());
        out.println(massengutArtikel.getArtikelnummer());
        out.println(massengutArtikel.getArtikelbezeichnung());
        out.println(massengutArtikel.getArtikelPreis());
        out.println(massengutArtikel.getArtikelbestand());
        out.println(massengutArtikel.getAnzahlMassengut());

        try{
            String rückfrage = in.readLine();
            switch (rückfrage) {
                case "ERROR 606":
                    throw new DoppelteIdException(mitarbeiter.getId());
                case "ERROR 707":
                    throw new MinusZahlException();
                case "ERROR 808":
                    throw new KeinMassengutException(massengutArtikel.getAnzahlMassengut());
            }
            //TODO: Fehler abfangen
        } catch (IOException e){
            System.err.println("Fehler beim lesen vom Server = addMassengutartikel" + e);
        }
    }

    public List<Ereignis> getEreignisListe() {
        List<Ereignis> ereignisListe = new ArrayList<Ereignis>();
        Person kundeOderMitarbeiter;
        out.println("getEreignisListe");
        try {
            int listengröße = Integer.parseInt(in.readLine());
            for(int i = 0; i < listengröße; i++) {
                Date datum = new Date(in.readLine());
                String artikelbezeichnung = in.readLine();
                int anzahl = Integer.parseInt(in.readLine());
                String kOderM = in.readLine();
                if(kOderM.equals("m")){
                    int id = Integer.parseInt(in.readLine());
                    String vorname = in.readLine();
                    String nachname = in.readLine();
                    String email = in.readLine();
                    String username = in.readLine();
                    String passwort = in.readLine();
                    kundeOderMitarbeiter = new Mitarbeiter(vorname, nachname, email, username, passwort, id);
                } else{
                    int id = Integer.parseInt(in.readLine());
                    String vorname = in.readLine();
                    String nachname = in.readLine();
                    String email = in.readLine();
                    String username = in.readLine();
                    String passwort = in.readLine();
                    String ort = in.readLine();
                    int plz = Integer.parseInt(in.readLine());
                    String strasse = in.readLine();
                    int strassenNummer = Integer.parseInt(in.readLine());
                    kundeOderMitarbeiter = new Kunde(vorname, nachname, email, username, passwort, id, ort, plz, strasse, strassenNummer);
                }
                Ereignis.EreignisTyp typ = Ereignis.EreignisTyp.valueOf(in.readLine());
                Ereignis ereignis = new Ereignis(datum, artikelbezeichnung, anzahl, kundeOderMitarbeiter, typ);
                ereignisListe.add(ereignis);
            }
            return ereignisListe;
        } catch (IOException e) {
            System.out.println("Fehler beim lesen der Ereignisliste = " + e);
        }
        return null;
    }


    public void addMitarbeiter(Person mitarbeiter, String vorname, String nachname, String email, String username, String password) throws DoppelteIdException, UsernameExistiertException, EmailExistiertException, IdNichtVorhandenException {
        out.println("addMitarbeiter");
        out.println(mitarbeiter.getId());
        out.println(vorname);
        out.println(nachname);
        out.println(email);
        out.println(username);
        out.println(password);
        try{
            String rückfrage = in.readLine();
            switch (rückfrage) {
                case "ERROR 505":
                    //throw new DoppelteIdException();
                case "ERROR 606":
                    throw new UsernameExistiertException(username);
                case "ERROR 707":
                    throw new EmailExistiertException(email);
                case "ERROR 808":
                    throw new IdNichtVorhandenException(mitarbeiter.getId());
            }
            //TODO: Fehler abfangen
        } catch (IOException e){
            System.err.println("Fehler beim lesen vom Server = addMitarbeiter" + e);
        }
    }

    public void addKunde(String vorname, String nachname, String email, String username, String password, String ort, int plz, String strasse, int strassenNummer) throws DoppelteIdException, UsernameExistiertException, EmailExistiertException {
        out.println("addKunde");
        out.println(vorname);
        out.println(nachname);
        out.println(email);
        out.println(username);
        out.println(password);
        out.println(ort);
        out.println(plz);
        out.println(strasse);
        out.println(strassenNummer);

        try{
            String rückfrage = in.readLine();
            switch (rückfrage) {
                case "ERROR 505":
                    //throw new DoppelteIdException();
                case "ERROR 606":
                    throw new UsernameExistiertException(username);
                case "ERROR 707":
                    throw new EmailExistiertException(email);
            }
            //TODO: Fehler abfangen
        } catch (IOException e){
            System.err.println("Fehler beim lesen vom Server = addKunde" + e);
        }

    }

    public Mitarbeiter loginMitarbeiter(String usernameOrEmail, String password) throws LoginException {
        out.println("loginMitarbeiter");
        out.println(usernameOrEmail);
        out.println(password);

        try{
            int id = Integer.parseInt(in.readLine());
            String vorname = in.readLine();
            String nachname = in.readLine();
            String email = in.readLine();
            String username = in.readLine();
            String passwort = in.readLine();
            //TODO: Richtige übergabe der ID
            Mitarbeiter mitarbeiter = new Mitarbeiter(vorname, nachname, email, username, passwort, id);

            String rückfrage = in.readLine();
            switch (rückfrage) {
                case "ERROR 404":
                    throw new LoginException();
            }
            //TODO: Fehler abfangen

            return mitarbeiter;
        } catch (IOException e){
            System.err.println("Fehler beim lesen vom Server = loginMitarbeiter" + e);
        }
        //TODO: Return Statement
        return null;
    }

    public Kunde loginKunde(String usernameOrEmail, String password) throws LoginException {
        out.println("loginKunde");

        out.println(usernameOrEmail);
        out.println(password);

        try{
            int id = Integer.parseInt(in.readLine());
            String vorname = in.readLine();
            String nachname = in.readLine();
            String email = in.readLine();
            String username = in.readLine();
            String passwort = in.readLine();
            String ort = in.readLine();
            int plz = Integer.parseInt(in.readLine());
            String strasse = in.readLine();
            int strassenNummer = Integer.parseInt(in.readLine());
            Kunde kunde = new Kunde(vorname, nachname, email, username, passwort, id, ort, plz, strasse, strassenNummer);

            String rückfrage = in.readLine();
            switch (rückfrage) {
                case "ERROR 404":
                    throw new LoginException();
            }
            //TODO: Fehler abfangen

            return kunde;
        } catch (IOException e){
            System.err.println("Fehler beim lesen vom Server = loginKunde" + e);
        }
        //TODO: Return Statement
        return null;
    }

    public Artikel sucheArtikelMitNummer(int artikelnummer) throws IdNichtVorhandenException {
       out.println("sucheArtikelMitNummer");
       out.println(artikelnummer);

        try{
            int nummer = Integer.parseInt(in.readLine());
            String bezeichnung = in.readLine();
            int bestand = Integer.parseInt(in.readLine());
            double preis = Double.parseDouble(in.readLine());
            Artikel artikel = new Artikel(nummer, bezeichnung, bestand, preis);

            String rückfrage = in.readLine();
            switch (rückfrage) {
                case "ERROR 404":
                    throw new IdNichtVorhandenException(artikelnummer);

            }
            //TODO: Fehler abfangen
            return artikel;
        } catch (IOException e){
            System.err.println("Fehler beim lesen vom Server = aendereArtikelBestand" + e);
        }
        //TODO: Return Statement
        return null;
    }

    public void loescheArtikel(Person mitarbeiter, int artikelnummer) throws IdNichtVorhandenException {
        out.println("loescheArtikel");

        out.println(mitarbeiter.getId());
        out.println(artikelnummer);

        try{
            String rückfrage = in.readLine();
            switch (rückfrage) {
                case "ERROR 404":
                    throw new IdNichtVorhandenException(mitarbeiter.getId());
            }
            //TODO: Fehler abfangen
        } catch (IOException e){
            System.err.println("Fehler beim lesen vom Server = loescheArtikel" + e);
        }
    }

    public void aendereArtikelBestand(Person mitarbeiter, int artikelnummer, int neuerBestand) throws IdNichtVorhandenException, KeinMassengutException, MinusZahlException {
        out.println("aendereArtikelBestand");
        out.println(mitarbeiter.getId());
        out.println(artikelnummer);
        out.println(neuerBestand);
        try{
            String rückfrage = in.readLine();
            switch (rückfrage) {
                case "ERROR 505":
                    throw new IdNichtVorhandenException(mitarbeiter.getId());
                case "ERROR 606":
                    throw new KeinMassengutException(neuerBestand);
                case "ERROR 707":
                    throw new MinusZahlException();
            }
            //TODO: Fehler abfangen
        } catch (IOException e){
            System.err.println("Fehler beim lesen vom Server = aendereArtikelBestand" + e);
        }

    }

    //Warenkorb
    //public void artikelInWarenkorbHinzufuegen1(Kunde kunde, Artikel artikel, int menge){
    public void artikelInWarenkorbHinzufügen(Person kunde, int artikelnummer, int menge) throws IdNichtVorhandenException, MinusZahlException, KeinMassengutException, BestandNichtAusreichendException {
        out.println("artikelInWarenkorbHinzufügen");
        out.println(kunde.getId());
        out.println(artikelnummer);
        out.println(menge);

        try{


            String rückfrage = in.readLine();
            switch (rückfrage) {
                case "ERROR 606":
                    throw new IdNichtVorhandenException(kunde.getId());
                case "ERROR 404":
                    throw new MinusZahlException();
                case "ERROR 505":
                    throw new KeinMassengutException(menge);
            }
            //TODO: Fehler abfangen
        } catch (IOException e){
            System.err.println("Fehler beim lesen vom Server = addArtikel" + e);
        }
    }

    //TODO: SIMON
    public Map<Artikel, Integer> gibWarenkorbArtikel(Person kunde) throws IstLeerException {
        Map<Artikel, Integer> artikelInWarenkorb = new HashMap<Artikel, Integer >();
        out.println("gibWarenkorbArtikel");
        out.println(kunde.getId());

        try{
            int warenkorbInhalt = Integer.parseInt(in.readLine());
            for (int i = 0; i < warenkorbInhalt; i++) {

                int mengeVonArtikelnImWarenkorb = Integer.parseInt(in.readLine());
                int artikelnummer = Integer.parseInt(in.readLine());
                String artikelbezeichnung = in.readLine();
                int artikelbestand = Integer.parseInt(in.readLine());
                double artikelpreis = Double.parseDouble(in.readLine());
                Artikel artikel = new Artikel(artikelnummer, artikelbezeichnung, artikelbestand, artikelpreis);
                artikelInWarenkorb.put(artikel, mengeVonArtikelnImWarenkorb);
            }


            String rückfrage = in.readLine();
            switch (rückfrage) {
                case "ERROR 505":
                    throw new IstLeerException();
            }
            //TODO: Fehler abfangen
            return artikelInWarenkorb;
        } catch (IOException e){
            System.err.println("Fehler beim lesen vom Server = addArtikel" + e);
        }
        return null;
    }


    public double gesamtPreis(Person kunde) throws IstLeerException {
        out.println("gesamtPreis");
        out.println(kunde.getId());

        try{
            double gesamtPreis = Double.parseDouble(in.readLine());
            String rückfrage = in.readLine();
            switch (rückfrage) {
                case "ERROR 505":
                    throw new IstLeerException();
            }
            return gesamtPreis;
            //TODO: Fehler abfangen
        } catch (IOException e){
            System.err.println("Fehler beim lesen vom Server = addArtikel" + e);
        }
        return -1.0;
    }


    public void warenkorbLeeren(Person kunde) {
        out.println("warenkorbLeeren");
        out.println(kunde.getId());

    }

    //TODO Marvin
    public Rechnung warenkorbKaufen(Kunde kunde) throws BestandNichtAusreichendException, IstLeerException {
        out.print("warenkorbKaufen");
        out.println(kunde.getId());

        try{
            Warenkorb warenkorb = new Warenkorb();
            int warenkorbInhalt = Integer.parseInt(in.readLine());
            for (int i = 0; i < warenkorbInhalt; i++) {
                int menge = Integer.parseInt(in.readLine());
                int artikelnummer = Integer.parseInt(in.readLine());
                String artikelbezeichnung = in.readLine();
                int artikelbestand = Integer.parseInt(in.readLine());
                double artikelpreis = Double.parseDouble(in.readLine());
                Artikel artikel = new Artikel(artikelnummer, artikelbezeichnung, artikelbestand ,artikelpreis);
                warenkorb.artikelHinzufuegen(artikel, menge);
            }

            Rechnung rechnung = new Rechnung(warenkorb, kunde);
            String rückfrage = in.readLine();
            switch (rückfrage) {
                case "ERROR 606":
                    throw new IstLeerException();
            }
            return rechnung;
            //TODO: Fehler abfangen
        } catch (IOException e){
            System.err.println("Fehler beim lesen vom Server = addArtikel" + e);
        }
        return null;
    }


    public void bestandImWarenkorbAendern(Person kunde, Artikel artikel, int menge) throws BestandNichtAusreichendException, IdNichtVorhandenException, KeinMassengutException, MinusZahlException, IstLeerException {
        out.println("bestandImWarenkorbAendern");
        out.println(kunde.getId());
        out.println(artikel.getArtikelnummer());
        out.println(menge);
    }

/*
    public void artikelImWarenkorbEntfernen(Person kunde, Artikel artikel) throws ArtikelExisitiertNichtException, IdNichtVorhandenException {
        if(kunde instanceof Kunde k){
            warenkorbManagement.entferneArtikelAusWarenkorb(k, artikel);
        }
    }

 */

}