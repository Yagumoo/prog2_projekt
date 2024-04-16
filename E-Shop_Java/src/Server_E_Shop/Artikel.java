package Server_E_Shop;
import Klassen_E_Shop.ArtikelFunktion;

public class Artikel{
    public int artikelnummer;
    public String bezeichnung;
    public int bestand;

    public Artikel(int artikelnummer, String bezeichnung, int bestand ){
        this.artikelnummer = artikelnummer;
        this.bezeichnung = bezeichnung;
        this.bestand = bestand;
    }

    public void printDetails() {
        System.out.println();
        System.out.println("Artikelnummer: " + artikelnummer + " |Bezeichnung: " + bezeichnung + " |Bestand: " + bestand);
    }
}
