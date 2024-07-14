package eshop.enitities;

import eshop.domain.WarenkorbManagement;
import eshop.enitities.*;
import eshop.domain.*;

import java.time.format.DateTimeFormatter;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;


public class Rechnung {
    private Artikel artikel;
    private Warenkorb warenkorb;
    private Kunde kunde;
    private WarenkorbManagement warenkorbManagement;
    private Map<Artikel, Integer> warenkorbKopie;
    private LocalDate datumaktuel;

    /**
     * @param kunde ist das Kundenobjekt
     * @param warenkorb ist das Warenkorbobjekt
     *
     * */
    public Rechnung(Warenkorb warenkorb, Kunde kunde){
        this.warenkorb = warenkorb;
        this.kunde = kunde;
        this.warenkorbKopie = new HashMap<>();
        this.warenkorbKopie.putAll(warenkorb.getWarenkorbMap());
        this.datumaktuel = LocalDate.now();
    }


    public Kunde getKunde(){
        return kunde;
    }


    public String getDatum() {
        return simpleDatum();
    }


    public Warenkorb getWarenkorb() {
        return warenkorb;
    }


    public Map<Artikel, Integer> getWarenkorbKopie(){
        return warenkorbKopie;
    }


    public String simpleDatum() {
        // Formatierung des Datums und der Uhrzeit
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return datumaktuel.format(formatter);
    }


    @Override
    public String toString() {
        String rechnung = "Rechnung für Kunde: " + kunde.getVorname() + " " + kunde.getNachname() +"\n" +
                "Wohnort:"+ kunde.getPlz() + " " + kunde.getOrt() + " " + kunde.getStrasse() + " " + kunde.getStrassenNummer() +  "\n\n";

        for (Map.Entry<Artikel, Integer> entry : warenkorbKopie.entrySet()) {
            Artikel artikel = entry.getKey();
            int menge = entry.getValue();
            rechnung += "Artikel: \n" + "Artikelnummer: " + artikel.getArtikelnummer() + " | ";
            rechnung += "Bezeichnung: " + artikel.getArtikelbezeichnung() + " | ";
            rechnung += "Menge: " + menge + " | ";
            rechnung += "Preis: " + artikel.getArtikelPreis() + "\n";
            rechnung += "Datum: " + getDatum() + "\n";
        }

        return rechnung;
    }


}
