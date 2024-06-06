package eshop.enitities;

import java.sql.SQLOutput;
import java.text.NumberFormat;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;


public class Warenkorb {
    private Map<Artikel, Integer> warenkorbMap;
    private Rechnung rechnung;


    public Warenkorb() {
        this.warenkorbMap = new HashMap<>();
    }


    public void artikelHinzufuegen(Artikel artikel, int menge) {
//         if (warenkorbMap.containsKey(artikel)) {
//             warenkorbMap.put(artikel, warenkorbMap.get(artikel) + menge);
//         } else {
//             warenkorbMap.put(artikel, menge);
//         }
        warenkorbMap.put(artikel, menge);

        //ereignisAusgeben(artikel);
    }

    public void artikelEntfernen(Artikel artikel) {
        warenkorbMap.remove(artikel);
        //ereignisAusgeben(artikel);
    }

    public void bestandImWarenkorbAendern(Artikel artikel, int newQuantity) {
        warenkorbMap.replace(artikel, newQuantity);
    }

    public Map<Artikel, Integer> getWarenkorbMap() {
        return warenkorbMap;
    }

    public void warenkorbKaufen() {
        for (Map.Entry<Artikel, Integer> entry : warenkorbMap.entrySet()) {
            Artikel artikel = entry.getKey();
            int mengeImWarenkorb = entry.getValue();

            // Überprüfen, ob der Artikel in der Artikel-Map vorhanden ist
            if (artikel != null) {
                int bestand = artikel.getArtikelbestand();
                int neuerBestand = bestand - mengeImWarenkorb;

                // Den neuen Bestand setzen
                artikel.setArtikelbestand(neuerBestand);
            }
        }

        // Warenkorb leeren, nachdem der Kauf abgeschlossen ist
        warenkorbLeeren();
    }

    public double gesamtPreis(){
        double gesamtPreis = 0;
        for(Map.Entry<Artikel, Integer> entry : warenkorbMap.entrySet()){
            Artikel artikel = entry.getKey();
            int menge = entry.getValue();

            gesamtPreis += (menge * artikel.getArtikelPreis());
        }
        //gesamtPreis = Math.round(gesamtPreis * 100.0) / 100.0;

        return begrenzeDezimalstellen(gesamtPreis, 2);

    }

    public static double begrenzeDezimalstellen(double zahl, int dezimalstellen) {
        double faktor = Math.pow(10, dezimalstellen);
        return Math.round(zahl * faktor) / faktor;
    }


    public void warenkorbLeeren() {
        warenkorbMap.clear();
    }

    public String toString(){
        String artikelAusWarenkorb =  "";
        for(Map.Entry<Artikel, Integer> entry : warenkorbMap.entrySet()){
            Artikel artikel = entry.getKey();
            int menge = entry.getValue();
            artikelAusWarenkorb += "Artikel: \n" + "Artikelnummer: " + artikel.getArtikelnummer() + " | ";
            artikelAusWarenkorb += "Bezeichnung: " + artikel.getArtikelbezeichnung() + " | ";
            artikelAusWarenkorb += "Menge: " + menge + " | ";
            artikelAusWarenkorb += "Preis: " + artikel.getArtikelPreis() + "\n";
        }
        return artikelAusWarenkorb;
    }

    /*
    Nur Ausgabe des Ereignis kein speichern
    public void ereignisAusgeben(Artikel artikel){

        System.out.println("Uhrzeit :2024");
        System.out.println(artikel);
        System.out.println("Die variable vom Mitarbeiter");

    }
    */
}

/*
*Jede Ein- und Auslagerung wird als Ereignis mit Datum (Nummer des Jahrestags reicht), betroffenem
*Artikel, Anzahl sowie beteiligtem Mitarbeiter bzw. Kunden festgehalten.
*
* Ausgabe nach Kauf: 2024 hat Kunde: nummer + name, Anzahl Artikel nummer + name + Anzahl  gekauft
* Ausgabe nach Einfügen: 2024 hat Mitarbeiter: nummer + name, Artikel nummer + name + Anzahl
* Ausgabe bestand ändern: 2025 hat Mitarbeiter: nummer + name den Bestand von Artikel: nummer + name um neuerBestand erhöht
*/
