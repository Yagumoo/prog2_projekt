package eshop.enitities;

import java.util.Map;
import java.util.HashMap;


public class Warenkorb {
    private Map<Artikel, Integer> warenkorbMap;

    public Warenkorb() {
        this.warenkorbMap = new HashMap<>();
    }

    public void addItemToWarenkorb(Artikel artikel, int menge) {
        if (warenkorbMap.containsKey(artikel)) {
            warenkorbMap.put(artikel, warenkorbMap.get(artikel) + menge);
        } else {
            warenkorbMap.put(artikel, menge);
        }
    }

    public void removeItemFromWarenkorb(Artikel artikel) {
        warenkorbMap.remove(artikel);
    }

    public void updateItemQuantityInWarenkorb(Artikel artikel, int newQuantity) {
        warenkorbMap.put(artikel, newQuantity);
    }

    public Map<Artikel, Integer> getWarenkorbMap() {
        return warenkorbMap;
    }
}


