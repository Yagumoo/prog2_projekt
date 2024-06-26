package eshop.domain;

import eshop.enitities.Artikel;
import eshop.enitities.Kunde;
import eshop.enitities.Warenkorb;
import eshop.enitities.Rechnung;

import java.util.Map;
import java.util.HashMap;


public class WarenkorbManagement {
    private Map<Artikel, Integer> warenkorbMap;
    private Map<Kunde, Warenkorb> warenkorbVonKunde;
    private Rechnung rechnung;

    public WarenkorbManagement(){
        this.warenkorbVonKunde = new HashMap<>();
    }

    public void warenkorbLeeren(Kunde kunde){
        Warenkorb warenkorb = warenkorbVonKunde.get(kunde);
        warenkorb.warenkorbLeeren();
    }

    public Rechnung rechnungErstellen(Kunde kunde){
        Warenkorb warenkorb = warenkorbVonKunde.get(kunde);
        return new Rechnung(warenkorb, kunde);
    }

    public void artikelInWarenkorbHinzufuegen(Kunde kunde, Artikel artikel,int menge) {
        //Warenkorb warenkorb = warenkorbVonKunde.get(kunde);
        Warenkorb warenkorb = warenkorbVonKunde.get(kunde);
        warenkorb.artikelHinzufuegen(artikel, menge);
    }

    public Warenkorb getWarenkorb(Kunde kunde){
        return warenkorbVonKunde.get(kunde);
    }

    public void warenkorbHinzufuegen(Kunde kunde){
        Warenkorb warenkorb = new Warenkorb();
        warenkorbVonKunde.put(kunde, warenkorb);
    }

    public void warenkorbEntfernen(Kunde kunde){
        warenkorbVonKunde.remove(kunde);
    }


    public Rechnung warenkorbKaufen(Kunde kunde) {
        warenkorbVonKunde.get(kunde);
        return rechnungErstellen(kunde);
    }

}