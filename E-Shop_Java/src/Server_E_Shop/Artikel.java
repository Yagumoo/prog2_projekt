package Server_E_Shop;
import Klassen_E_Shop.ArtikelFunktion;

import java.util.ArrayList;

public class Artikel{
    public int artikelnummer;
    public String artikelbezeichnung;
    public int artikelbestand;

    public Artikel(int artikelnummer, String artikelbezeichnung, int artikelbestand ){
        this.artikelnummer = artikelnummer;
        this.artikelbezeichnung = artikelbezeichnung;
        this.artikelbestand = artikelbestand;
    }

    public void printDetails() {
<<<<<<< Updated upstream
        System.out.println();
        System.out.println("Artikelnummer: " + artikelnummer + " |Bezeichnung: " + bezeichnung + " |Bestand: " + bestand);
=======
        System.out.println("Artikelnummer: " + artikelnummer);
        System.out.println("Bezeichnung: " + artikelbezeichnung);
        System.out.println("Bestand: " + artikelbestand);
>>>>>>> Stashed changes
    }




}
