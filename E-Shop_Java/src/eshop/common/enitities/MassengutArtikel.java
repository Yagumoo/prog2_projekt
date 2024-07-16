package eshop.common.enitities;
/**
 * Repräsentiert einen Massengutartikel im E-Shop.
 *
 * <p>Die {@code MassengutArtikel}-Klasse erweitert die {@code Artikel}-Klasse und fügt die Fähigkeit hinzu, Massengutartikel zu verwalten, bei denen die Menge in definierten Einheiten gehandhabt wird.</p>
 *
 * <p>Zusätzlich zu den Attributen der Basisklasse {@code Artikel} enthält diese Klasse ein Attribut, das angibt, wie viele Einheiten als Massengut betrachtet werden.</p>
 */
public class MassengutArtikel extends Artikel {
    private  int anzahlMassengut;

    /**
     * Konstruktor für die {@code MassengutArtikel}-Klasse.
     *
     * <p>Initialisiert ein neues Massengutartikel mit den angegebenen Werten für Artikelnummer, Artikelbezeichnung, Artikelbestand, Artikelpreis und Anzahl für Massengut.</p>
     *
     * @param artikelnummer   die eindeutige Artikelnummer des Massengutartikels
     * @param artikelbezeichnung die Bezeichnung des Massengutartikels
     * @param artikelbestand  der Bestand des Massengutartikels
     * @param artikelPreis    der Preis des Massengutartikels
     * @param anzahlMassengut die Anzahl für Massengut, die als Vielfaches berücksichtigt wird
     */
    public MassengutArtikel(int artikelnummer, String artikelbezeichnung, int artikelbestand, double artikelPreis, int anzahlMassengut){
        super(artikelnummer, artikelbezeichnung, artikelbestand, artikelPreis);
        this.anzahlMassengut = anzahlMassengut;
    }

    /**
     * Gibt die Anzahl für Massengut zurück.
     *
     * <p>Diese Methode gibt die Anzahl zurück, die als Massengut für diesen Artikel definiert ist.</p>
     *
     * @return die Anzahl für Massengut
     */
    public int getAnzahlMassengut() {

        return anzahlMassengut;
    }

    /**
     * Setzt die Anzahl für Massengut.
     *
     * <p>Diese Methode aktualisiert die Anzahl, die als Massengut für diesen Artikel definiert ist.</p>
     *
     * @param anzahlMassengut die neue Anzahl für Massengut
     */
    public void setAnzahlMassengut(int anzahlMassengut) {
        this.anzahlMassengut = anzahlMassengut;
    }
    /**
     * Gibt eine textuelle Darstellung des Massengutartikels zurück.
     *
     * <p>Diese Methode gibt eine {@code String}-Repräsentation des Massengutartikels zurück, die die Artikelnummer, Artikelbezeichnung, Bestand, Preis und die Massengut-Anzahl enthält.</p>
     *
     * @return eine {@code String}-Darstellung des Massengutartikels
     */
    @Override
    public String toString() {
        return super.toString() + " |Einheit " + anzahlMassengut;
    }
}
