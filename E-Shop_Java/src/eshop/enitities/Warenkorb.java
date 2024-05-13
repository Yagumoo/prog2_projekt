package eshop.enitities;

import java.util.Map;
import java.util.HashMap;


public class Warenkorb {
    private Map<Artikel, Integer> warenkorbMap;

    public Warenkorb() {
        this.warenkorbMap = new HashMap<>();
    }

    public void artikelHinzufuegen(Artikel artikel, int menge) {
        if (warenkorbMap.containsKey(artikel)) {
            warenkorbMap.put(artikel, warenkorbMap.get(artikel) + menge);
        } else {
            warenkorbMap.put(artikel, menge);
        }
    }

    public void artikelEntfernen(Artikel artikel) {
        warenkorbMap.remove(artikel);
    }

    public void bestandImWarenkorbAendern(Artikel artikel, int newQuantity) {
        warenkorbMap.put(artikel, newQuantity);
    }

    public  void artikelImWarenkorbKaufen(){

    }

    public Map<Artikel, Integer> getWarenkorbMap() {
        return warenkorbMap;
    }
}


