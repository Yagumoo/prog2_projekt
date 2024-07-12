package eshop.common.enitities;

public class MassengutArtikel extends Artikel {
    private  int anzahlMassengut;

    //Konstruktor
    /**
     * @param anzahlMassengut ist die Anzahl, in der der Artikel verkauft werden soll
     *
     * */
    public MassengutArtikel(int artikelnummer, String artikelbezeichnung, int artikelbestand, double artikelPreis, int anzahlMassengut){
        super(artikelnummer, artikelbezeichnung, artikelbestand, artikelPreis);
        this.anzahlMassengut = anzahlMassengut;
    }

    //Getter von Anzahl des Massengut
    public int getAnzahlMassengut() {
        return anzahlMassengut;
    }

    // müssten wir nicht artikelNummer beutzten weil wir das übergeben
    //Setter von Anzahl des Massengut
    public void setAnzahlMassengut(int anzahlMassengut) {
        this.anzahlMassengut = anzahlMassengut;
    }

    @Override
    public String toString() {
        return super.toString() + " |Einheit " + anzahlMassengut;
    }
}
