package Klassen_E_Shop;
import java.util.ArrayList;
import java.util.List;
import Server_E_Shop.Artikel;

public class ArtikelFunktion{

    

    public List artikelListe = new ArrayList();

    public void addArtikel(int artikelnummer, String bezeichnung, int bestand ){
        Artikel artikel = new Artikel(artikelnummer, bezeichnung, bestand);
        artikelListe.add(artikel);
    }

    public  void artikelAusgeben(){
        
    }
    /*
    * private ArrayList<Artikel> artikelListe;

    public ArtikelListe() {
        this.artikelListe = new ArrayList<>();
    }

    // Methode zum Hinzufügen eines Artikels zur Liste
    public void artikelHinzufuegen(Artikel artikel) {
        artikelListe.add(artikel);
    }

    // Methode zum Entfernen eines Artikels aus der Liste
    public void artikelEntfernen(Artikel artikel) {
        artikelListe.remove(artikel);
    }
    * */

}
