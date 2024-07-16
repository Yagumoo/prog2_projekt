package eshop.client.clientServerVerbindung;

import eshop.common.enitities.*;
import eshop.common.exceptions.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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

            String rückfrage = in.readLine();
            switch (rückfrage) {
                //TODO: machen
                default:
                    System.out.println("Erfolgreich gibAlleArtikel()");
            }

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
            String rückfrage = in.readLine();
            switch (rückfrage) {
                //TODO: machen
                default:
                    System.out.println("Erfolgreich gibAlleArtikel()");
            }
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
            String rückfrage = in.readLine();
            switch (rückfrage) {
                //TODO: machen
                default:
                    System.out.println("Erfolgreich gibAlleMitarbeiter()");
            }
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

    public void addArtikel(Person mitarbeiter, Artikel artikel) throws DoppelteIdException, MinusZahlException, KeinMassengutException, ArtikelnameDoppeltException, IdNichtVorhandenException {
        out.println("addArtikel");
        out.println(mitarbeiter.getId());
        out.println(artikel.getArtikelnummer());
        out.println(artikel.getArtikelbezeichnung());
        out.println(artikel.getArtikelbestand());
        out.println(artikel.getArtikelPreis());

        try{
            String rückfrage = in.readLine();
            switch (rückfrage) {
                case "ERROR 304":
                    throw new DoppelteIdException(artikel.getArtikelnummer());
                case "ERROR 202":
                    throw new MinusZahlException();
                case "ERROR 405":
                    throw new KeinMassengutException(artikel.getArtikelbestand());
                case "ERROR 407":
                    throw new ArtikelnameDoppeltException(artikel.getArtikelbezeichnung());
                case "ERROR 303":
                    throw new IdNichtVorhandenException(artikel.getArtikelnummer());
                default:
                    System.out.println("Erfolgreich addArtikel()");
            }
            //TODO: Fehler abfangen
        } catch (IOException e){
            System.err.println("Fehler beim lesen vom Server = addArtikel" + e);
        }
    }

    public void addMassengutartikel(Person mitarbeiter, MassengutArtikel massengutArtikel) throws DoppelteIdException, MinusZahlException, KeinMassengutException, ArtikelnameDoppeltException, IdNichtVorhandenException {
        out.println("addMassengutartikel");
        out.println(mitarbeiter.getId());
        out.println(massengutArtikel.getArtikelnummer());
        out.println(massengutArtikel.getArtikelbezeichnung());
        out.println(massengutArtikel.getArtikelbestand());
        out.println(massengutArtikel.getArtikelPreis());
        out.println(massengutArtikel.getAnzahlMassengut());

        try{
            String rückfrage = in.readLine();
            switch (rückfrage) {
                case "ERROR 304":
                    throw new DoppelteIdException(massengutArtikel.getArtikelnummer());
                case "ERROR 202":
                    throw new MinusZahlException();
                case "ERROR 405":
                    throw new KeinMassengutException(massengutArtikel.getAnzahlMassengut());
                case "ERROR 404":
                    throw new ArtikelnameDoppeltException(massengutArtikel.getArtikelbezeichnung());
                case "ERROR 303":
                    throw new IdNichtVorhandenException(massengutArtikel.getAnzahlMassengut());
                default:
                    System.out.println("Erfolgreich addMassengutartikel()");
            }
            //TODO: Fehler abfangen
        } catch (IOException e){
            System.err.println("Fehler beim lesen vom Server = addMassengutartikel" + e);
        }
    }

    public List<Ereignis> getEreignisListe() {
        List<Ereignis> ereignisListe = new ArrayList<>();
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH);
        Person kundeOderMitarbeiter;
        out.println("getEreignisListe");
        try {
            String rückfrage = in.readLine();
            switch (rückfrage) {
                //TODO: abfangen
                default:
                    System.out.println("Erfolgreich getEreignisListe()");
            }
            int listengröße = Integer.parseInt(in.readLine());
            for(int i = 0; i < listengröße; i++) {
                //TODO: Datum kann nicht richtig übergeben werden
                LocalDate datum = LocalDate.parse(in.readLine(), dateFormat);
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
                case "ERROR 304":
                    throw new DoppelteIdException(mitarbeiter.getId());
                case "ERROR 808":
                    throw new UsernameExistiertException(username);
                case "ERROR 809":
                    throw new EmailExistiertException(email);
                case "ERROR 303":
                    throw new IdNichtVorhandenException(mitarbeiter.getId());
                default:
                    System.out.println("Erfolgreich addMitarbeiter()");
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
                case "ERROR 304":
                    //throw new DoppelteIdException(kunde.getId());
                case "ERROR 808":
                    throw new UsernameExistiertException(username);
                case "ERROR 809":
                    throw new EmailExistiertException(email);
                default:
                    System.out.println("Erfolgreich addKunde()");
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
            String rückfrage = in.readLine();
            switch (rückfrage) {
                case "ERROR 807":
                    throw new LoginException();
                default:
                    System.out.println("Erfolgreich loginMitarbeiter()");
            }
            //TODO: Fehler abfangen
            int id = Integer.parseInt(in.readLine());
            String vorname = in.readLine();
            String nachname = in.readLine();
            String email = in.readLine();
            String username = in.readLine();
            String passwort = in.readLine();
            //TODO: Richtige übergabe der ID
            Mitarbeiter mitarbeiter = new Mitarbeiter(vorname, nachname, email, username, passwort, id);
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
            String rückfrage = in.readLine();
            switch (rückfrage) {
                case "ERROR 807":
                    throw new LoginException();
                    //TODO: Fehler abfangen
                default:
                    System.out.println("Erfolgreich loginKunde()");
            }

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
            String rückfrage = in.readLine();
            switch (rückfrage) {
                case "ERROR 303":
                    throw new IdNichtVorhandenException(artikelnummer);
                default:
                    System.out.println("Erfolgreich sucheArtikelMitNummer()");

            }
            //TODO: Fehler abfangen
            int nummer = Integer.parseInt(in.readLine());
            String bezeichnung = in.readLine();
            int bestand = Integer.parseInt(in.readLine());
            double preis = Double.parseDouble(in.readLine());
            Artikel artikel = new Artikel(nummer, bezeichnung, bestand, preis);
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
                case "ERROR 303":
                    throw new IdNichtVorhandenException(artikelnummer);
                default:
                    System.out.println("Erfolgreich loescheArtikel()");
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
                case "ERROR 303":
                    throw new IdNichtVorhandenException(mitarbeiter.getId());
                case "ERROR 405":
                    throw new KeinMassengutException(neuerBestand);
                case "ERROR 202":
                    throw new MinusZahlException();
                default:
                    System.out.println("Erfolgreich aendereArtikelBestand()");
            }
            //TODO: Fehler abfangen
        } catch (IOException e){
            System.err.println("Fehler beim lesen vom Server = aendereArtikelBestand()" + e);
        }

    }

    //Warenkorb
    //public void artikelInWarenkorbHinzufuegen1(Kunde kunde, Artikel artikel, int menge){
    public void artikelInWarenkorbHinzufügen(Person kunde, Artikel artikel, int menge) throws IdNichtVorhandenException, MinusZahlException, KeinMassengutException, BestandNichtAusreichendException, IstLeerException {
        out.println("artikelInWarenkorbHinzufügen");
        out.println(kunde.getId());
        out.println(artikel.getArtikelnummer());
        out.println(menge);

        try{
            String rückfrage = in.readLine();
            switch (rückfrage) {
                case "ERROR 303":
                    throw new IdNichtVorhandenException(artikel.getArtikelnummer());
                case "ERROR 202":
                    throw new MinusZahlException();
                case "ERROR 405":
                    throw new KeinMassengutException(menge);
                case "ERROR 408":
                    throw new BestandNichtAusreichendException(artikel, artikel.getArtikelbestand());
                case "ERROR 406":
                    throw new IstLeerException();
                default:
                    System.out.println("Erfolgreich artikelInWarenkorbHinzufügen()");
            }
            //TODO: Fehler abfangen
        } catch (IOException e){
            System.err.println("Fehler beim lesen vom Server = artikelInWarenkorbHinzufügen()" + e);
        }
    }

    //TODO: SIMON
    public Map<Artikel, Integer> gibWarenkorbArtikel(Person kunde) throws IstLeerException, IdNichtVorhandenException {
        Map<Artikel, Integer> artikelInWarenkorb = new HashMap<Artikel, Integer >();
        out.println("gibWarenkorbArtikel");
        out.println(kunde.getId());

        try{
            String rückfrage = in.readLine();
            switch (rückfrage) {
                case "ERROR 406":
                    throw new IstLeerException();
                case "ERROR 303":
                    //throw new IdNichtVorhandenException(); TODO: Was fehlt noch
                default:
                    System.out.println("Erfolgreich gibWarenkorbArtikel()");
            }
            //TODO: Fehler abfangen
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
            return artikelInWarenkorb;
        } catch (IOException e){
            System.err.println("Fehler beim lesen vom Server = gibWarenkorbArtikel()" + e);
        }
        return null;
    }


    public double gesamtPreis(Person kunde) throws IstLeerException, IdNichtVorhandenException {
        out.println("gesamtPreis");
        out.println(kunde.getId());

        try{
            String rückfrage = in.readLine();
            switch (rückfrage) {
                case "ERROR 202":
                    throw new IstLeerException();
                case "ERROR 406":
                    //throw new IdNichtVorhandenException();
                default:
                    System.out.println("Erfolgreich gesamtPreis()");
            }
            //TODO: Fehler abfangen
            double gesamtPreis = Double.parseDouble(in.readLine());
            return gesamtPreis;
        } catch (IOException e){
            System.err.println("Fehler beim lesen vom Server = gesamtPreis()" + e);
        }
        return -1.0;
    }


    public void warenkorbLeeren(Person kunde) {
        out.println("warenkorbLeeren");
        out.println(kunde.getId());
        try{
            String rückfrage = in.readLine();
            switch (rückfrage) {
                //TODO: machen
                default:
                    System.out.println("Erfolgreich warenkorbLeeren()");
            }
        } catch (IOException e){
            System.err.println("Fehler beim lesen vom Server = warenkorbLeeren()" + e);
        }
    }

    //TODO Marvin
    public Rechnung warenkorbKaufen(Kunde kunde) throws BestandNichtAusreichendException, IstLeerException, IdNichtVorhandenException {
        out.println("warenkorbKaufen");
        out.println(kunde.getId());

        try{
            String rückfrage = in.readLine();
            switch (rückfrage) {
//                case "ERROR 303":
//                    throw new IdNichtVorhandenException();
                case "ERROR 406":
                    throw new IstLeerException();
//                case "ERROR 408":
//                    throw new BestandNichtAusreichendException();
                default:
                    System.out.println("Erfolgreich warenkorbKaufen()");
            }
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
            return rechnung;
            //TODO: Fehler abfangen
        } catch (IOException e){
            System.err.println("Fehler beim lesen vom Server = warenkorbKaufen()" + e);
        }
        return null;
    }


    public void bestandImWarenkorbAendern(Person kunde, Artikel artikel, int menge) throws BestandNichtAusreichendException, IdNichtVorhandenException, KeinMassengutException, MinusZahlException, IstLeerException {
        out.println("bestandImWarenkorbAendern");
        out.println(kunde.getId());
        out.println(artikel.getArtikelnummer());
        out.println(menge);

        try{
            String rückfrage = in.readLine();
            switch (rückfrage) {
                case"ERROR 303":
                    throw new IdNichtVorhandenException(artikel.getArtikelnummer());
                case "408":
                    throw new BestandNichtAusreichendException(artikel, artikel.getArtikelbestand());
                case "405":
                    throw new KeinMassengutException(artikel.getArtikelbestand());
                case "202":
                    throw new MinusZahlException();
                case "406":
                    throw new IstLeerException();
                //TODO: machen
                default:
                    System.out.println("Erfolgreich bestandImWarenkorbAendern()");
            }
        } catch (IOException e){
            System.err.println("Fehler beim lesen vom Server = bestandImWarenkorbAendern()" + e);
        }
    }

        /*
        Erfolgreich => Erfolgreich ...;

        IOException => "ERROR 101"

        MinusZahlException => "ERROR 202"

        IdNichtVorhandenException => "ERROR 303"

        DoppelteIdException => "ERROR 304"

        ArtikelnameDoppeltException => "ERROR 404"

        KeinMassengutException => "ERROR 405"

        IstLeerException => "ERROR 406"

        ArtikelnameDoppeltException =>"ERROR 407"

        BestandNichtAusreichendException =>"ERROR 408"

        LoginException => "ERROR 807"

        UsernameExistiertException => "ERROR 808"

        EmailExistiertException => "ERROR 809"


        UngueltigeArtikelBezeichnung => /
        FalscheEingabeException => only CUI
        FilterException => only Clientseite
        WertNichtGefundenException => only Clientseite
        ArtikelExisitiertNichtException => only Warenkorbleeren CUI
     */

}