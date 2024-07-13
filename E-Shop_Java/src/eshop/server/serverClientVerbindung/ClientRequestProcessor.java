package eshop.server.serverClientVerbindung;

import eshop.common.enitities.*;
import eshop.common.exceptions.*;
import eshop.server.domain.E_Shop;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ClientRequestProcessor extends Thread {

    private Socket clientSocket;
    private E_Shop eShop;

    private PrintStream out;
    private BufferedReader in;

    public ClientRequestProcessor(Socket clientSocket, E_Shop eShop){
        this.clientSocket = clientSocket;
        this.eShop = eShop;
        try {
            // erstellt Input/Output Streams um Daten vom Client zu lesen/schreiben
            out = new PrintStream(clientSocket.getOutputStream());
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (IOException e) {
            System.err.println("Error beim erstellen von In und Out-Streams");
        }
    }

    @Override
    public void run() {
        String input = "";
        do {
            try {
                input = in.readLine();
                System.out.println(input);
            } catch (IOException e) {
                System.err.println("Error beim lesen vom Client " + e);
                break;
            }

            switch (input) {
                case "gibAlleArtikel":
                    Map<Integer, Artikel> alleArtikel = eShop.gibAlleArtikel();
                    gibAlleArtikel(alleArtikel);
                case "gibAlleMassengutartikel":
                    Map<Integer, Artikel> alleMassengutartikel = eShop.gibAlleArtikel();
                    gibAlleMassengutartikel(alleMassengutartikel);
                case "gibAlleMitarbeiter":
                    Map<Integer, Mitarbeiter> alleMitarbeiter = eShop.gibAlleMitarbeiter();
                    gibAlleMitarbeiter(alleMitarbeiter);
                case "addArtikel":
                    addArtikel();
                case "addMassengutartikel":
                    addMassengutartikel();
                case "aendereArtikelBestand":
                    aendereArtikelBestand();
                case"addKunde":
                    addKunde();
                case "addMitarbeiter":
                    addMitarbeiter();
                case "loginMitarbeiter":
                    loginMitarbeiter();
                case "loginKunde":
                    loginKunde();
                case "loescheArtikel":
                    loescheArtikel();
            }

        } while (!input.equals("exit"));
    }

    public void gibAlleArtikel(Map<Integer, Artikel> alleArtikel){
        Map<Integer, Artikel> onlyAlleArtikel = new HashMap<Integer, Artikel>();
        for(Map.Entry<Integer, Artikel> entry : alleArtikel.entrySet()) {
            if(entry.getValue() instanceof Artikel) {
                onlyAlleArtikel.put(entry.getKey(), (Artikel) entry.getValue());
            }
        }
        out.println(onlyAlleArtikel.size());
        for(Map.Entry<Integer, Artikel> entry : onlyAlleArtikel.entrySet()){
            out.println(entry.getValue().getArtikelnummer());
            out.println(entry.getValue().getArtikelbezeichnung());
            out.println(entry.getValue().getArtikelbestand());
            out.println(entry.getValue().getArtikelPreis());
        }
    }

    public void gibAlleMassengutartikel(Map<Integer, Artikel> alleArtikel){
        Map<Integer, MassengutArtikel> onlyAlleMassengutArtikel = new HashMap<Integer, MassengutArtikel>();
        for(Map.Entry<Integer, Artikel> entry : alleArtikel.entrySet()) {
            if(entry.getValue() instanceof MassengutArtikel) {
                onlyAlleMassengutArtikel.put(entry.getKey(), (MassengutArtikel) entry.getValue());
            }
        }
        out.println(onlyAlleMassengutArtikel.size());
        for(Map.Entry<Integer, MassengutArtikel> entry : onlyAlleMassengutArtikel.entrySet()){
            out.println(entry.getValue().getArtikelnummer());
            out.println(entry.getValue().getArtikelbezeichnung());
            out.println(entry.getValue().getArtikelbestand());
            out.println(entry.getValue().getArtikelPreis());
            out.println(((MassengutArtikel) entry.getValue()).getAnzahlMassengut());
        }
    }

    public void gibAlleMitarbeiter(Map<Integer, Mitarbeiter> alleMitarbeiter){
        //TODO: brauche wir das hier?
        //Map<Integer, Mitarbeiter> onlyAlleMitarbeiter = new HashMap<>();
        for (Map.Entry<Integer, Mitarbeiter> entry : alleMitarbeiter.entrySet()) {
            out.println(entry.getValue().getId());
            out.println(entry.getValue().getVorname());
            out.println(entry.getValue().getNachname());
            out.println(entry.getValue().getEmail());
            out.println(entry.getValue().getUsername());
            out.println(entry.getValue().getPassword());
        }
    }

    public void addArtikel(){
        try {
            int mitarbeiterID = Integer.parseInt(in.readLine());
            int artikelNummer = Integer.parseInt(in.readLine());
            String artikelbezeichnung = in.readLine();
            int artikelpreis = Integer.parseInt(in.readLine());
            int artikelbestand = Integer.parseInt(in.readLine());

            Mitarbeiter mitarbeiter = eShop.sucheMirarbeiterMitNummer(mitarbeiterID);
            Artikel artikel = new Artikel(artikelNummer, artikelbezeichnung, artikelpreis, artikelbestand);
            eShop.addArtikel(mitarbeiter, artikel);
        } catch (IOException e) {
            System.err.println("Error beim lesen vom Client bei = addArtikel" + e);
        } catch (IdNichtVorhandenException e){
            System.err.println("Error beim lesen vom Client bei = addArtikel" + e);
            out.println("ERROR 404");
        } catch (DoppelteIdException e){
            System.err.println("Error beim lesen vom Client bei = addArtikel" + e);
            out.println("ERROR 505");
        } catch (MinusZahlException e){
            System.err.println("Error beim lesen vom Client bei = addArtikel" + e);
            out.println("ERROR 606");
        } catch (KeinMassengutException e){
            System.err.println("Error beim lesen vom Client bei = addArtikel" + e);
            out.println("ERROR 707");
        }
    }

    public void addMassengutartikel(){
        try {
            int mitarbeiterID = Integer.parseInt(in.readLine());
            int artikelNummer = Integer.parseInt(in.readLine());
            String artikelbezeichnung = in.readLine();
            int artikelpreis = Integer.parseInt(in.readLine());
            int artikelbestand = Integer.parseInt(in.readLine());
            int artikelMassengut = Integer.parseInt(in.readLine());

            Mitarbeiter mitarbeiter = eShop.sucheMirarbeiterMitNummer(mitarbeiterID);
            Artikel massengutArtikel = new MassengutArtikel(artikelNummer, artikelbezeichnung, artikelpreis, artikelbestand, artikelMassengut);
            eShop.addArtikel(mitarbeiter, massengutArtikel);

        } catch (IOException e) {
            System.err.println("Error beim lesen vom Client bei = addMassengutartikel" + e);
        } catch (IdNichtVorhandenException e){
            System.err.println("Error beim lesen vom Client bei = addMassengutartikel" + e);
            out.println("ERROR 505");
        } catch (DoppelteIdException e){
            System.err.println("Error beim lesen vom Client bei = addMassengutartikel" + e);
            out.println("ERROR 606");
        } catch (MinusZahlException e){
            System.err.println("Error beim lesen vom Client bei = addMassengutartikel" + e);
            out.println("ERROR 707");
        } catch (KeinMassengutException e){
            System.err.println("Error beim lesen vom Client bei = addMassengutartikel" + e);
            out.println("ERROR 808");
        }
    }

    public void aendereArtikelBestand()  {
        try {
            int mitarbeiterID = Integer.parseInt(in.readLine());
            Mitarbeiter mitarbeiter = eShop.sucheMirarbeiterMitNummer(mitarbeiterID);
            int artikelnummer = Integer.parseInt(in.readLine());
            int neuerBestand = Integer.parseInt(in.readLine());

            eShop.aendereArtikelBestand(mitarbeiter, artikelnummer, neuerBestand);
            out.println("aendereArtikelBestand war erfolgreich");
        } catch (IOException e){
            System.err.println("Error beim lesen vom Client bei = aendereArtikelBestand" + e);
            out.println("ERROR 404");
        } catch(IdNichtVorhandenException e) {
            System.err.println("Error beim lesen vom Client bei = aendereArtikelBestand" + e);
            out.println("ERROR 505");
        } catch(MinusZahlException e) {
            System.err.println("Error beim lesen vom Client bei = aendereArtikelBestand" + e);
            out.println("ERROR 606");
        } catch(KeinMassengutException e) {
            System.err.println("Error beim lesen vom Client bei = aendereArtikelBestand" + e);
            out.println("ERROR 707");
        }
        //TODO: Fehler zurük zum client senden wie oben ^

    }

    public void addKunde(){
        try {
            String vorname = in.readLine();
            String nachname = in.readLine();
            String email = in.readLine();
            String username = in.readLine();
            String password = in.readLine();
            String ort = in.readLine();
            int plz = Integer.parseInt(in.readLine());
            String strasse = in.readLine();
            int strassenNummer = Integer.parseInt(in.readLine());

            eShop.addKunde(vorname, nachname, email, username, password, ort, plz, strasse, strassenNummer);
        }catch (IOException e){
            System.err.println("Error beim lesen vom Client bei = addKunde" + e);
        } catch (DoppelteIdException e) {
            System.err.println("Error beim lesen vom Client bei = addKunde" + e);
            out.println("ERROR 505");
        } catch (UsernameExistiertException e) {
            System.err.println("Error beim lesen vom Client bei = addKunde" + e);
            out.println("ERROR 606");
        } catch (EmailExistiertException e) {
            System.err.println("Error beim lesen vom Client bei = addKunde" + e);
            out.println("ERROR 707");
        }
    }

    public void addMitarbeiter(){
        try {
            int mitarbeiterID = Integer.parseInt(in.readLine());
            String vorname = in.readLine();
            String nachname = in.readLine();
            String email = in.readLine();
            String username = in.readLine();
            String password = in.readLine();

            Mitarbeiter mitarbeiter = eShop.sucheMirarbeiterMitNummer(mitarbeiterID);
            eShop.addMitarbeiter(mitarbeiter,vorname, nachname, email, username, password);
        }catch (IOException e){
            System.err.println("Error beim lesen vom Client bei = addMitarbeiter" + e);
        } catch (DoppelteIdException e) {
            System.err.println("Error beim lesen vom Client bei = addMitarbeiter" + e);
            out.println("ERROR 505");
        } catch (UsernameExistiertException e) {
            System.err.println("Error beim lesen vom Client bei = addMitarbeiter" + e);
            out.println("ERROR 606");
        } catch (EmailExistiertException e) {
            System.err.println("Error beim lesen vom Client bei = addMitarbeiter" + e);
            out.println("ERROR 707");
        } catch (IdNichtVorhandenException e) {
            System.err.println("Error beim lesen vom Client bei = addMitarbeiter" + e);
            out.println("ERROR 808");
        }
    }

    private void loginMitarbeiter()  {
        try {
            String usernameOrEmail = in.readLine();
            String password = in.readLine();

            Mitarbeiter mitarbeiter = eShop.loginMitarbeiter(usernameOrEmail, password);
            out.println(mitarbeiter.getVorname());
            out.println(mitarbeiter.getNachname());
            out.println(mitarbeiter.getEmail());
            out.println(mitarbeiter.getUsername());
            out.println(mitarbeiter.getPassword());
        } catch (IOException e) {
            System.err.println("Error beim lesen vom Client bei = loginMitarbeiter" + e);
        } catch (LoginException e) {
            System.err.println("Error beim lesen vom Client bei = loginMitarbeiter" + e);
            out.println("ERROR 404");
        }
    }

    private void loginKunde(){
        try {
            String usernameOrEmail = in.readLine();
            String password = in.readLine();

            Kunde kunde = eShop.loginKunde(usernameOrEmail, password);
            out.println(kunde.getVorname());
            out.println(kunde.getNachname());
            out.println(kunde.getEmail());
            out.println(kunde.getUsername());
            out.println(kunde.getPassword());
            out.println(kunde.getOrt());
            out.println(kunde.getPlz());
            out.println(kunde.getStrasse());
            out.println(kunde.getStrassenNummer());
        } catch (IOException e) {
            System.err.println("Error beim lesen vom Client bei = loginKunde" + e);
        } catch (LoginException e) {
            System.err.println("Error beim lesen vom Client bei = loginKunde" + e);
            out.println("ERROR 404");
        }
    }

    private void loescheArtikel(){
        try{
            int mitarbeiterID = Integer.parseInt(in.readLine());
            Mitarbeiter mitarbeiter = eShop.sucheMirarbeiterMitNummer(mitarbeiterID);
            int artikelnummer = Integer.parseInt(in.readLine());
            eShop.loescheArtikel(mitarbeiter, artikelnummer);
        }catch (IOException e){
            System.err.println("Error beim lesen vom Client bei = loescheArtikel" + e);
        } catch (IdNichtVorhandenException e) {
            System.err.println("Error beim lesen vom Client bei = loginKunde" + e);
            out.println("ERROR 404");
        }

    }
}
