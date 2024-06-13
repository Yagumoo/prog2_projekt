package eshop.enitities;

import eshop.domain.WarenkorbManagement;
import eshop.enitities.*;
import eshop.domain.*;

import java.util.HashMap;
import java.util.Map;


public class Rechnung {
    private Artikel artikel;
    private Warenkorb warenkorb;
    private Kunde kunde;
    private WarenkorbManagement warenkorbManagement;
    private Map<Artikel, Integer> warenkorbKopie;

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
    }
    public Kunde getKunde(){
        return kunde;
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
        }

        return rechnung;
    }

}
