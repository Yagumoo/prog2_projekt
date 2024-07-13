package eshop.server.serverClientVerbindung;

import eshop.common.enitities.Artikel;
import eshop.common.enitities.Ereignis;
import eshop.common.enitities.MassengutArtikel;
import eshop.common.enitities.Mitarbeiter;
import eshop.common.exceptions.DoppelteIdException;
import eshop.common.exceptions.IdNichtVorhandenException;
import eshop.common.exceptions.KeinMassengutException;
import eshop.common.exceptions.MinusZahlException;
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
        } catch (IOException | IdNichtVorhandenException | DoppelteIdException | MinusZahlException | KeinMassengutException e) {
            System.err.println("Error beim lesen vom Client bei = addArtikel" + e);
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
        } catch (IOException | IdNichtVorhandenException | DoppelteIdException | MinusZahlException | KeinMassengutException e) {
            System.err.println("Error beim lesen vom Client bei = addMassengutartikel" + e);
        }
    }

    public void aendereArtikelBestand()  {
        try {
            int mitarbeiterID = Integer.parseInt(in.readLine());
            Mitarbeiter mitarbeiter = eShop.sucheMirarbeiterMitNummer(mitarbeiterID);
            int artikelnummer = Integer.parseInt(in.readLine());
            int neuerBestand = Integer.parseInt(in.readLine());

            eShop.aendereArtikelBestand(mitarbeiter, artikelnummer, neuerBestand);
        } catch (IOException | IdNichtVorhandenException | KeinMassengutException | MinusZahlException e){
            System.err.println("Error beim lesen vom Client bei = aendereArtikelBestand" + e);
        }

    }

}
