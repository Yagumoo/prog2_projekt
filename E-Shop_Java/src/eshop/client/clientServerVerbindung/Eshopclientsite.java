package eshop.client.clientServerVerbindung;

import eshop.common.enitities.*;
import eshop.common.exceptions.*;
import eshop.server.serverClientVerbindung.Server;

import javax.swing.*;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
                case "505":
                    throw new DoppelteIdException(mitarbeiter.getId());
                case "606":
                    throw new MinusZahlException();
                case "707":
                    throw new KeinMassengutException(artikel.getArtikelbestand());
            }
            //TODO: Fehler abfangen
        } catch (IOException e){
            System.err.println("Fehler beim lesen vom Server = aendereArtikelBestand" + e);
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
                case "606":
                    throw new DoppelteIdException(mitarbeiter.getId());
                case "707":
                    throw new MinusZahlException();
                case "808":
                    throw new KeinMassengutException(massengutArtikel.getAnzahlMassengut());
            }
            //TODO: Fehler abfangen
        } catch (IOException e){
            System.err.println("Fehler beim lesen vom Server = aendereArtikelBestand" + e);
        }
    }

    public List<Ereignis> getEreignisListe() {
        return ereignisManagement.getEreignisse();
    }

    public void addMitarbeiter(Person mitarbeiter, String vorname, String nachname, String email, String username, String password) throws DoppelteIdException, UsernameExistiertException, EmailExistiertException {
        if (mitarbeiter instanceof Mitarbeiter) {
            mitarbeiterManagement.addMitarbeiter(vorname, nachname, email, username, password);
            //Person mitarbeiter = mitarbeiterManagement.getEingeloggterMitarbeiter();
        }

    }

    public void addKunde(String vorname, String nachname, String email, String username, String password, String ort, int plz, String strasse, int strassenNummer) throws DoppelteIdException, UsernameExistiertException, EmailExistiertException {
        kundenManagement.addKunde(vorname, nachname, email, username, password, ort, plz, strasse, strassenNummer);
    }

    public Mitarbeiter loginMitarbeiter(String usernameOrEmail, String password) throws LoginException {
        return mitarbeiterManagement.loginMitarbeiter(usernameOrEmail, password);
    }

    public Kunde loginKunde(String usernameOrEmail, String password) throws LoginException {
        Kunde kunde = kundenManagement.loginkunde(usernameOrEmail, password);
        warenkorbManagement.warenkorbHinzufuegen(kunde);
        return kunde;
    }

    public Artikel sucheArtikelMitNummer(int artikelnummer) throws IdNichtVorhandenException {
        return artikelManagement.gibArtikelPerId(artikelnummer);
    }

    public void loescheArtikel(Person mitarbeiter, int artikelnummer) throws IdNichtVorhandenException {
        if (mitarbeiter instanceof Mitarbeiter) {
            sucheArtikelMitNummer(artikelnummer);
            artikelManagement.loescheArtikel(artikelnummer);
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
                case "505":
                    throw new IdNichtVorhandenException(mitarbeiter.getId());
                case "606":
                    throw new KeinMassengutException(neuerBestand);
                case "707":
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
        if (kunde instanceof Kunde k) {
            Artikel artikel = artikelManagement.gibArtikelPerId(artikelnummer);
            if (artikel != null) {
                warenkorbManagement.artikelInWarenkorbHinzufuegen(k, artikel, menge);
            }
        }
    }


    public Map<Artikel, Integer> gibWarenkorbArtikel(Person kunde) throws IstLeerException {
        if (kunde instanceof Kunde k) {
            Warenkorb wk = warenkorbManagement.getWarenkorb(k);
            return wk.getWarenkorbMap();
        } else {
            throw new IstLeerException();
        }
    }

/*
    public String printWarenkorbArtikel(Person kunde) throws IstLeerException {
        if (kunde instanceof Kunde k) {
            Warenkorb wk = warenkorbManagement.getWarenkorb(k);
            return wk.toString();
        }
        return "Person ist kein Kunde";
    }

 */


    public double gesamtPreis(Person kunde) throws IstLeerException {
        if (kunde instanceof Kunde k) {
            Warenkorb wk = warenkorbManagement.getWarenkorb(k);
            return wk.gesamtPreis();
        }
        return -1.0;
    }


    public void warenkorbLeeren(Person kunde) {
        if (kunde instanceof Kunde k) {
            warenkorbManagement.warenkorbLeeren(k);
        }
    }

    public Rechnung warenkorbKaufen(Kunde kunde) throws BestandNichtAusreichendException, IstLeerException {
        Warenkorb wk = warenkorbManagement.getWarenkorbKaufen(kunde);
        artikelManagement.bestandAbbuchen(wk);

        for (Map.Entry<Artikel, Integer> entry : wk.getWarenkorbMap().entrySet()) {
            Artikel artikel = entry.getKey();
            int menge = entry.getValue();
            Ereignis neuesEreignis = new Ereignis(new Date(), artikel.getArtikelbezeichnung(), menge, kunde, Ereignis.EreignisTyp.KAUF);
            ereignisManagement.addEreignis(neuesEreignis);
        }

        Rechnung rechnung = new Rechnung(wk, kunde);
        warenkorbLeeren(kunde);

        return rechnung;
    }


    public void bestandImWarenkorbAendern(Person kunde, Artikel artikel, int menge) throws BestandNichtAusreichendException, IdNichtVorhandenException, KeinMassengutException, MinusZahlException, IstLeerException {
        if (kunde instanceof Kunde k) {
            int aktuellerBestand = artikel.getArtikelbestand();
            Warenkorb wk = warenkorbManagement.getWarenkorb(k);

            // Überprüfen, ob der Artikel ein Massengutartikel ist

            wk.bestandImWarenkorbAendern(artikel, menge);
            int neuerBestand = artikel.getArtikelbestand();

            if (menge > aktuellerBestand) {
                throw new BestandNichtAusreichendException(artikel, neuerBestand);
            }
        }
    }


    public void artikelImWarenkorbEntfernen(Person kunde, Artikel artikel) throws ArtikelExisitiertNichtException, IdNichtVorhandenException {
        if(kunde instanceof Kunde k){
            warenkorbManagement.entferneArtikelAusWarenkorb(k, artikel);
        }
    }

}