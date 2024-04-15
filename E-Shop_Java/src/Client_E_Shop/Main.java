package Client_E_Shop;
import Server_E_Shop.Artikel;
import Klassen_E_Shop.ArtikelFunktion;

import java.util.ArrayList;


public class Main{
    public static void main(String[] args) {
        ArtikelFunktion af = new ArtikelFunktion();
        //Artikel a = new Artikel();
        af.addArtikel(5, "Energy", 20);
        Artikel leer = (Artikel) af.artikelListe.get(0);
        System.out.println(leer.artikelnummer + " " + leer.bezeichnung + " " + leer.bestand);

    }
}

