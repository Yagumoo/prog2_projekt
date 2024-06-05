package eshop.enitities;

import eshop.enitities.*;
import java.util.Map;

public class Rechnung {
    private Artikel artikel;
    private Warenkorb warenkorb;
    private Kunde kunde;

    public Rechnung(Warenkorb warenkorb, Kunde kunde){
        this.warenkorb = warenkorb;
        this.kunde = kunde;
    }

    @Override
    public String toString() {
        String rechnung = "";
        for (Map.Entry<Artikel, Integer> entry : warenkorb.getWarenkorbMap().entrySet()) {
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
