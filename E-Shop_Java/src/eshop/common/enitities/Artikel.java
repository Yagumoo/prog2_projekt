package eshop.common.enitities;

public class Artikel{
    private int artikelnummer;
    private String artikelbezeichnung;
    private int artikelbestand;
    private double artikelPreis;
    /**
     * @param artikelbezeichnung ist der Name vom Artikel
     * @param artikelbestand ist die Anzahl der Artikel
     * @param artikelnummer ist die Nummer der Artikel
     * @param artikelPreis ist der Preis der Artikel
     *
     * */
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

   @Override
    public String toString() {
        return "Artikel: Artikelnummer: " + artikelnummer + " |Bezeichnung: " + artikelbezeichnung + " |Bestand: " + artikelbestand + " |Preis: " + artikelPreis + "";
    }
}
