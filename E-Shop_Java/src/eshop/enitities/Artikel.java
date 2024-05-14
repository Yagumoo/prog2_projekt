package eshop.enitities;

public class Artikel{
    private int artikelnummer;
    private String artikelbezeichnung;
    private int artikelbestand;
    private double artikelPreis;

    public Artikel(int artikelnummer, String artikelbezeichnung, int artikelbestand, double artikelPreis) {
        this.artikelnummer = artikelnummer;
        this.artikelbezeichnung = artikelbezeichnung;
        this.artikelbestand = artikelbestand;
        this.artikelPreis = artikelPreis;

    }

    public int getArtikelnummer() {

        return artikelnummer;
    }

    public void setArtikelnummer(int artikelNummer) {

        this.artikelnummer = artikelNummer;
    }

    public String getArtikelbezeichnung() {

        return artikelbezeichnung;
    }

    public void setArtikelbezeichnung(String artikelbezeichnung) {

        this.artikelbezeichnung = artikelbezeichnung;
    }

    public double getArtikelPreis() {

        return artikelPreis;
    }

    public void setArtikelPreis(double artikelPreis) {
        this.artikelPreis = artikelPreis;
    }

    public int getArtikelbestand() {

        return artikelbestand;
    }

    public void setArtikelbestand(int artikelBestand) {

        this.artikelbestand = artikelBestand;
    }

    public void bestandAendern(int neuerBestand) {
        this.artikelbestand = neuerBestand;
    }


   @Override
    public String toString() {
        return "Artikel: Artikelnummer: " + artikelnummer + " |Bezeichnung: " + artikelbezeichnung + " |Bestand: " + artikelbestand + " |Preis: " + artikelPreis + "";
    }
}
