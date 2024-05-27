package eshop.enitities;
import eshop.domain.EreignisManagement;

import java.util.Date;
import java.text.SimpleDateFormat;

public class Ereignis {

    public enum EreignisTyp {
        NEU, KAUF, ERHOEHUNG, REDUZIERUNG
    }

    private Date datum;
    private String artikelbezeichnung; // Artikel
    private int anzahl;
    private Person kundeOderMitarbeiter;
    private EreignisTyp typ;
    private Person betroffenePerson;

    public Ereignis(Date datum, String artikelbezeichnung, int anzahl, Person kundeOderMitarbeiter, EreignisTyp typ){
        this.datum = datum;
        this.artikelbezeichnung = artikelbezeichnung;
        this. anzahl = anzahl;
        this.kundeOderMitarbeiter = kundeOderMitarbeiter;
        this.typ = typ;
    }

    public Date getDatum() {
        return datum;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }

    public String getArtikel() {
        return artikelbezeichnung;
    }

    public void setArtikel(Artikel artikel) {
        this.artikelbezeichnung = artikelbezeichnung;
    }

    public void setBetroffenePerson(Person person){
        this.betroffenePerson = betroffenePerson;
    }

    public int getAnzahl() {
        return anzahl;
    }

    public void setAnzahl(int anzahl) {
        this.anzahl = anzahl;
    }

    public String simpleDatum() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        return sdf.format(datum);
    }


    @Override
    public String toString() {
        return "Datum: " + simpleDatum() +
                ", Artikel: " + artikelbezeichnung +
                ", Anzahl: " + anzahl +
                ", Person: " + kundeOderMitarbeiter +
                ", Typ: " + typ;
    }


    public Ereignis ereignisSpeicher(Date datum, String artikelbezeichnung, int anzahl, String nachname, int id){
        Ereignis ereignis = new Ereignis(datum, artikelbezeichnung, anzahl,kundeOderMitarbeiter,typ);
        return ereignis;

    }







}













/*
public void einlagern(Artikel artikel, int anzahl, Person mitarbeiter) {
        // Prüfen, ob der Artikel bereits in der Liste vorhanden ist
        if (artikelListe.containsKey(artikel.getArtikelnummer())) {
            // Artikel vorhanden, Bestand erhöhen
            Artikel vorhandenerArtikel = artikelListe.get(artikel.getArtikelnummer());
            vorhandenerArtikel.setArtikelbestand(vorhandenerArtikel.getArtikelbestand() + anzahl);
        } else {
            // Artikel nicht vorhanden, neuen Artikel hinzufügen
            artikel.setArtikelbestand(anzahl);
            artikelListe.put(artikel.getArtikelnummer(), artikel);
        }

        // Ereignis festhalten
        LagerEreignis ereignis = new LagerEreignis(new Date(), artikel, anzahl, mitarbeiter);
        // Hier könnten Sie das Ereignis speichern, z. B. in einer Liste oder Datenbank
    }

    public void auslagern(Artikel artikel, int anzahl, Person mitarbeiter) {
        // Prüfen, ob genügend Bestand vorhanden ist
        if (artikelListe.containsKey(artikel.getArtikelnummer())) {
            Artikel vorhandenerArtikel = artikelListe.get(artikel.getArtikelnummer());
            int bestand = vorhandenerArtikel.getArtikelbestand();
            if (bestand >= anzahl) {
                // Ausreichend Bestand vorhanden, Bestand reduzieren
                vorhandenerArtikel.setArtikelbestand(bestand - anzahl);
            } else {
                // Nicht genügend Bestand vorhanden
                System.out.println("Nicht genügend Bestand für " + artikel.getArtikelbezeichnung() + " vorhanden.");
                return;
            }
        } else {
            // Artikel nicht gefunden
            System.out.println("Artikel " + artikel.getArtikelbezeichnung() + " nicht gefunden.");
            return;
        }

        // Ereignis festhalten
        LagerEreignis ereignis = new LagerEreignis(new Date(), artikel, anzahl, mitarbeiter);
        // Hier könnten Sie das Ereignis speichern, z. B. in einer Liste oder Datenbank
    }



public class EShopCUI {
    // ...

    private void artikelHinzufugen(Scanner scan) {
        // Artikel hinzufügen Logik
        // ...

        // Einlagerungsereignis festhalten
        artikelManagement.einlagern(artikel, anzahl, beteiligtePerson);
    }

    private void artikelAusWarenkorbEntfernen(Scanner scan) {
        // Artikel aus Warenkorb entfernen Logik
        // ...

        // Auslagerungsereignis festhalten
        artikelManagement.auslagern(artikel, anzahl, beteiligtePerson);
    }

    // ...
}

 */
