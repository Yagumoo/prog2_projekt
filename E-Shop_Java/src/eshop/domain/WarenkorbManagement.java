package eshop.domain;

import eshop.enitities.Artikel;
import eshop.enitities.Kunde;
import eshop.enitities.Warenkorb;
import eshop.enitities.Rechnung;

import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;

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
        rechnung = new Rechnung(warenkorb, kunde);
        return rechnung;
    }

    public void artikelInWarenkorbHinzufuegen(Kunde kunde,Artikel artikel,int menge){
        Warenkorb warenkorb = warenkorbVonKunde.get(kunde);
        warenkorb.artikelHinzufuegen(artikel, menge);
    }

    public Warenkorb getWarenkorb(Kunde kunde){
        return warenkorbVonKunde.get(kunde);
    }


    public Rechnung warenkorbKaufen(Kunde kunde) {
        Warenkorb warenkorb = warenkorbVonKunde.get(kunde);
        warenkorb.warenkorbKaufen();
        return rechnungErstellen(kunde);
    }

}