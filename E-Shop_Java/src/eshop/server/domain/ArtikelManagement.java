package eshop.server.domain;
import java.util.*;

import eshop.common.enitities.Artikel;
import eshop.common.enitities.Warenkorb;
import eshop.common.enitities.MassengutArtikel;


import eshop.common.exceptions.*;
import eshop.server.persistence.filePersistenceManager;
/**
 * Verwaltet Artikel und bietet Funktionen zum Laden und Hinzufügen von Artikeln sowie zur Verwaltung des Warenkorbs.
 *
 * Die Klasse ist für das Management von Artikeln in einem Warenkorb verantwortlich und ermöglicht das Laden von Artikeldaten
 * aus einer Datei sowie das Hinzufügen von Artikeln. Sie verwaltet eine Liste von Artikeln und ein Ereignis-Management für
 * die Protokollierung von Ereignissen. Die Artikel werden beim Erstellen der Instanz der Klasse geladen, und Standardartikel
 * werden hinzugefügt, wenn die Artikelliste leer ist.
 */
public class ArtikelManagement {

    private filePersistenceManager fpm; // = new filePersistenceManager();
    private final Warenkorb warenkorb = new Warenkorb();
    private Map<Integer, Artikel> artikelListe = new HashMap<>();
    private EreignisManagement ereignisManagement;
    /**
     * Konstruktor der Klasse {@link ArtikelManagement}.
     *
     * Initialisiert das {@link filePersistenceManager}-Objekt zum Laden von Artikeldaten aus der Datei. Wenn die Artikelliste
     * nach dem Laden leer ist, werden einige Standardartikel hinzugefügt.
     *
     * @param fpm Das {@link filePersistenceManager}-Objekt, das für das Laden und Speichern von Artikeldaten verwendet wird.
     */
    public ArtikelManagement(filePersistenceManager fpm) {
        try{
            this.fpm = fpm;
            artikelListe = fpm.ladeArtikelListe("artikel.txt");

            if(artikelListe.isEmpty()){
                addArtikel(new Artikel(5, "Energy", 20, 2.49));
                addArtikel(new Artikel(2, "Laptop", 3, 1599.99));
                addArtikel(new Artikel(1, "Hähnchen", 2000, 5.99));
                addArtikel(new MassengutArtikel(9, "Bier", 100, 0.99, 6));
            }

        } catch (Exception e){
            System.out.println(e.getMessage());
        }

    }
    /**
     * Fügt einen neuen Artikel zur Artikel-Liste hinzu.
     *
     * Diese Methode überprüft, ob der Artikel gültige Eigenschaften hat und ob es sich um einen doppelten Artikel handelt.
     * Es wird sichergestellt, dass die Artikelnummer positiv ist, der Bestand positiv ist und der Preis nicht negativ ist.
     * Zudem wird überprüft, ob der Artikelname bereits vorhanden ist und ob Massengutartikel die richtigen Bedingungen erfüllen.
     * Wenn der Artikel alle Prüfungen besteht, wird er zur Artikel-Liste hinzugefügt.
     *
     * @param artikel Der {@link Artikel} oder {@link MassengutArtikel}, der zur Artikel-Liste hinzugefügt werden soll.
     * @throws DoppelteIdException Wenn die Artikelnummer bereits in der Liste vorhanden ist.
     * @throws MinusZahlException Wenn die Artikelnummer, der Artikelbestand oder die Anzahl des Massenguts eine negative Zahl ist.
     * @throws KeinMassengutException Wenn der Artikel ein Massengutartikel ist, dessen Bestand nicht durch die Anzahl des Massenguts teilbar ist.
     * @throws ArtikelnameDoppeltException Wenn der Artikelname bereits in der Liste vorhanden ist.
     */
    public void addArtikel(Artikel artikel) throws DoppelteIdException, MinusZahlException, KeinMassengutException,ArtikelnameDoppeltException {
        if(artikel.getArtikelnummer() <=0 || artikel.getArtikelbestand() <=0 || artikel.getArtikelPreis() <0){
            throw new MinusZahlException();

        }
        //TODO: angucken
        if (sucheArtikel(artikel.getArtikelnummer())) {
            throw new DoppelteIdException(artikel.getArtikelnummer());
        }

        for (Artikel bestimmterArtikel : artikelListe.values()){
            if (bestimmterArtikel.getArtikelbezeichnung().equals(artikel.getArtikelbezeichnung())) {
                throw new ArtikelnameDoppeltException(artikel.getArtikelbezeichnung());
            }
        }


        // Überprüfen, ob es sich um ein Massengut handelt und ob die Anzahl korrekt ist
        if (artikel instanceof MassengutArtikel) {
            MassengutArtikel massengutArtikel = (MassengutArtikel) artikel;
            int massengutAnzahl = massengutArtikel.getAnzahlMassengut();
            int artikelbestand = artikel.getArtikelbestand();

            if(((MassengutArtikel) artikel).getAnzahlMassengut() <= 0) {
                throw new MinusZahlException();
            }

            if (artikelbestand % massengutAnzahl != 0) {
                throw new KeinMassengutException(massengutAnzahl);
            }

        }
        artikelListe.put(artikel.getArtikelnummer(), artikel);

    }
    /**
     * Ändert den Bestand eines Artikels anhand seiner Artikelnummer.
     *
     * Diese Methode sucht einen Artikel mit der angegebenen Artikelnummer in der Artikel-Liste und aktualisiert den Bestand des Artikels.
     * Vor der Aktualisierung wird überprüft, ob der neue Bestand gültig ist. Wenn der Artikel ein Massengutartikel ist, wird zudem überprüft,
     * ob der neue Bestand ein Vielfaches der Massengut-Anzahl ist.
     *
     * @param artikelnummer Die Artikelnummer des Artikels, dessen Bestand geändert werden soll.
     * @param neuerBestand Der neue Bestand des Artikels, der gesetzt werden soll.
     * @return `true`, wenn der Bestand erfolgreich geändert wurde; `false`, wenn kein Artikel mit der angegebenen Artikelnummer gefunden wurde.
     * @throws MinusZahlException Wenn der neue Bestand kleiner oder gleich Null ist.
     * @throws KeinMassengutException Wenn der Artikel ein Massengutartikel ist und der neue Bestand nicht durch die Anzahl des Massenguts teilbar ist.
     */
    public boolean aendereArtikelBestand(int artikelnummer, int neuerBestand) throws MinusZahlException, KeinMassengutException {

        Artikel artikel = artikelListe.get(artikelnummer);
        if (artikel != null) {
            if(neuerBestand <=0 ) {
                throw new MinusZahlException();
            }

            if(artikel instanceof MassengutArtikel massengutArtikel){
                int massengutAnzahl = massengutArtikel.getAnzahlMassengut();
                if (neuerBestand % massengutAnzahl != 0){
                    throw new KeinMassengutException(massengutAnzahl);
                }
            }
            artikel.setArtikelbestand(neuerBestand);
            return true;
        }
        return false;
    }
    /**
     * Bucht den Bestand der Artikel im Warenkorb ab.
     *
     * Diese Methode überprüft zunächst, ob für jeden Artikel im Warenkorb der Bestand ausreichend ist, um die Menge abzubuchen.
     * Wenn der Bestand eines Artikels nicht ausreicht, wird eine {@link BestandNichtAusreichendException} ausgelöst.
     * Wenn alle Bestände ausreichen, wird die angegebene Menge für jeden Artikel vom aktuellen Bestand abgezogen.
     *
     * @param warenkorb Der Warenkorb, dessen Artikelbestände aktualisiert werden sollen.
     * @throws BestandNichtAusreichendException Wenn der Bestand eines Artikels im Warenkorb nicht ausreicht, um die angegebene Menge abzubuchen.
     */
    public void bestandAbbuchen(Warenkorb warenkorb) throws BestandNichtAusreichendException {

        for (Map.Entry<Artikel, Integer> entry : warenkorb.getWarenkorbMap().entrySet()) {
            Artikel artikel = entry.getKey();
            int menge = entry.getValue();
            int aktuellerBestand = artikel.getArtikelbestand();
            if (artikel.getArtikelbestand() < menge) {
                throw new BestandNichtAusreichendException(artikel, aktuellerBestand);
            }
        }

        for (Map.Entry<Artikel, Integer> entry : warenkorb.getWarenkorbMap().entrySet()) {
            Artikel artikel = entry.getKey();
            int menge = entry.getValue();
            artikel.setArtikelbestand(artikel.getArtikelbestand() - menge);
        }
    }
    /**
     * Gibt eine Map aller Artikel im System zurück.
     *
     * Diese Methode liefert eine Map, in der die Schlüssel die Artikelnummern und die Werte die entsprechenden {@link Artikel}-Objekte sind.
     *
     * @return Eine Map, die alle Artikel im System enthält. Die Schlüssel sind die Artikelnummern, die Werte sind die {@link Artikel}-Objekte.
     */
    public Map<Integer, Artikel> gibAlleArtikel() {
        return artikelListe;
    }
    /**
     * Löscht einen Artikel aus der Artikel-Liste anhand der angegebenen Artikelnummer.
     *
     * Diese Methode entfernt den Artikel mit der gegebenen Artikelnummer aus der Artikel-Liste.
     * Wenn kein Artikel mit der angegebenen Nummer gefunden wird, wird eine {@link IdNichtVorhandenException} ausgelöst.
     *
     * @param artikelnummer Die Artikelnummer des Artikels, der gelöscht werden soll.
     * @throws IdNichtVorhandenException Wenn kein Artikel mit der angegebenen Artikelnummer in der Liste vorhanden ist.
     */
    public void loescheArtikel(int artikelnummer) throws IdNichtVorhandenException {
        if (!artikelListe.containsKey(artikelnummer)) {
            throw new IdNichtVorhandenException(artikelnummer);
        }
        artikelListe.remove(artikelnummer);
    }
    /**
     * Gibt den Artikel mit der angegebenen Artikelnummer zurück.
     *
     * Diese Methode sucht den Artikel mit der angegebenen Artikelnummer in der Artikel-Liste.
     * Wenn kein Artikel mit der angegebenen Nummer gefunden wird, wird eine {@link IdNichtVorhandenException} ausgelöst.
     *
     * @param artikelnummer Die Artikelnummer des gesuchten Artikels.
     * @return Der {@link Artikel} mit der angegebenen Artikelnummer.
     * @throws IdNichtVorhandenException Wenn kein Artikel mit der angegebenen Artikelnummer in der Liste vorhanden ist.
     */
    public Artikel gibArtikelPerId(int artikelnummer) throws IdNichtVorhandenException{
        if (!artikelListe.containsKey(artikelnummer)) {
            throw new IdNichtVorhandenException(artikelnummer);
        }
        return artikelListe.get(artikelnummer);
    }
    /**
     * Überprüft, ob ein Artikel mit der angegebenen Artikelnummer in der Artikel-Liste vorhanden ist.
     *
     * Diese Methode sucht nach einem Artikel in der Artikel-Liste, der die angegebene Artikelnummer hat.
     * Sie gibt `true` zurück, wenn ein Artikel mit der Nummer vorhanden ist, andernfalls `false`.
     *
     * @param artikelnummer Die Artikelnummer des zu suchenden Artikels.
     * @return `true`, wenn ein Artikel mit der angegebenen Artikelnummer in der Liste vorhanden ist; andernfalls `false`.
     */
    public boolean sucheArtikel(int artikelnummer){
        return artikelListe.containsKey(artikelnummer);
    }

}






