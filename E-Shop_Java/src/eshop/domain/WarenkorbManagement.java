package eshop.domain;

import eshop.enitities.*;
import eshop.domain.exceptions.*;

import java.util.Map;
import java.util.HashMap;


public class WarenkorbManagement {
    //TODO: Gucken
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

    public void artikelInWarenkorbHinzufuegen(Kunde kunde, Artikel artikel,int menge) throws MinusZahlException, KeinMassengutException, BestandNichtAusreichendException {
        //Warenkorb warenkorb = warenkorbVonKunde.get(kunde);
        if (artikel instanceof MassengutArtikel massengutArtikel) {
            int massengutAnzahl = massengutArtikel.getAnzahlMassengut();
            if (menge % massengutAnzahl != 0) {
                throw new KeinMassengutException(massengutAnzahl);
            }
        }

        if(menge <=0 ){
            throw new MinusZahlException();
        }

        if(menge > artikel.getArtikelbestand() ){
            throw new BestandNichtAusreichendException(artikel, artikel.getArtikelbestand());
        }

        Warenkorb warenkorb = warenkorbVonKunde.get(kunde);
        //Wenn Artikel schon im Warenkorb ist, wird die neue Menge dazu addiert
        if(warenkorb.getWarenkorbMap().containsKey(artikel)){
            int bestehendeMenge = warenkorb.getWarenkorbMap().get(artikel);
            menge += bestehendeMenge;
        }
        warenkorb.artikelHinzufuegen(artikel, menge);
    }

    public void entferneArtikelAusWarenkorb(Kunde kunde, Artikel artikel) throws ArtikelExisitiertNichtException{
        Warenkorb warenkorb = warenkorbVonKunde.get(kunde);

        if(!warenkorb.getWarenkorbMap().containsKey(artikel)){
            throw new ArtikelExisitiertNichtException(artikel.getArtikelbezeichnung());
        }
        warenkorb.artikelEntfernen(artikel);
    }

    public Warenkorb getWarenkorb(Kunde kunde) throws IstLeerException{
        if(warenkorbVonKunde == null){
            throw new IstLeerException();
        }
        return warenkorbVonKunde.get(kunde);
    }

    public void warenkorbHinzufuegen(Kunde kunde){
        Warenkorb warenkorb = new Warenkorb();
        warenkorbVonKunde.put(kunde, warenkorb);
    }

    public void warenkorbEntfernen(Kunde kunde){
        warenkorbVonKunde.remove(kunde);
    }

    //holt sich den Warenkorb für Gui zum kaufen
    public Warenkorb getWarenkorbKaufen(Kunde kunde) throws IstLeerException {
        Warenkorb wk = warenkorbVonKunde.get(kunde);
        Map<Artikel, Integer> wkMap = wk.getWarenkorbMap();
        if(wkMap.isEmpty()){
            throw new IstLeerException();
        }
        return warenkorbVonKunde.get(kunde);
    }

    //TODO: Gucken
    public Rechnung WarenkorbKaufen(Kunde kunde){
        warenkorbVonKunde.get(kunde);
        return rechnungErstellen(kunde);
    }

    public Rechnung rechnungErstellen(Kunde kunde){
        Warenkorb warenkorb = warenkorbVonKunde.get(kunde);
        return new Rechnung(warenkorb, kunde);
    }

}