package Client_E_Shop;
import Server_E_Shop.Artikel;
import java.util.List;
import Klassen_E_Shop.ArtikelFunktion;
import Server_E_Shop.Kunde;
import Klassen_E_Shop.KundenFunktionen;

import java.util.Objects;
import java.util.Scanner;

import java.util.ArrayList;


public class Main{
    public static void main(String[] args) {

        ArtikelFunktion af = new ArtikelFunktion();
        //Artikel a = new Artikel();
        af.addArtikel(5, "Energy", 20);
        af.addArtikel(2, "Laptop", 3);
        af.addArtikel(1, "Hähnchen", 2000);
        Artikel leer = (Artikel) af.artikelListe.get(0);
        Kunde kunde = new Kunde("Max", "Mustermann", "lol@lol.de", "lol", "lol", 1, "Musterstadt", 12 , "Musterstrasse", 123);
        kunde.printDetails();
        leer.printDetails();

        for(Object artikel :af.artikelListe){
            System.out.println(artikel.printDetails());
        }




   }
}

