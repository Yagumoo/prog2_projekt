package eshop.common.enitities;

import eshop.common.exceptions.BestandNichtAusreichendException;
import eshop.common.exceptions.IdNichtVorhandenException;
import eshop.common.exceptions.KeinMassengutException;
import eshop.common.exceptions.MinusZahlException;

import java.util.Map;
import java.util.HashMap;

/**
 * Die Klasse {@code Warenkorb} verwaltet die Artikel im Warenkorb eines Kunden und ermöglicht das Hinzufügen, Entfernen und Verwalten von Artikeln.
 *
 * <p>Die Klasse verwendet eine Map, um Artikel und deren Menge zu speichern, und bietet Methoden zum Hinzufügen, Entfernen und Abfragen von Artikeln.</p>
 */
public class Warenkorb {
    private Map<Artikel, Integer> warenkorbMap;
    private Rechnung rechnung;


    public Warenkorb() {
        this.warenkorbMap = new HashMap<>();
    }

    /**
     * Fügt einen Artikel zum Warenkorb hinzu oder aktualisiert die Menge eines bestehenden Artikels.
     *
     * @param artikel Der Artikel, der zum Warenkorb hinzugefügt werden soll.
     * @param menge   Die Menge des Artikels, die dem Warenkorb hinzugefügt werden soll.
     * @throws KeinMassengutException Wenn der Artikel kein Massengut ist und die Menge kleiner oder gleich null ist.
     * @throws BestandNichtAusreichendException Wenn die angegebene Menge den Bestand des Artikels überschreitet.
     */
    public void artikelHinzufuegen(Artikel artikel, int menge) {
        warenkorbMap.put(artikel, menge);
    }

    /**
     * Entfernt einen Artikel aus dem Warenkorb.
     *
     * @param artikel Der Artikel, der aus dem Warenkorb entfernt werden soll.
     */
    public void artikelEntfernen(Artikel artikel) {
        warenkorbMap.remove(artikel);
    }

    /**
     * Ändert die Menge eines Artikels im Warenkorb.
     *
     * <p>Diese Methode ermöglicht es, die Menge eines Artikels im Warenkorb anzupassen. Sie überprüft, ob der Artikel im Warenkorb vorhanden ist, ob die angegebene Menge gültig ist und ob der Artikel die Bedingungen für Massengut erfüllt. Außerdem wird geprüft, ob der Bestand des Artikels für die angegebene Menge ausreicht.</p>
     *
     * @param artikel Der Artikel, dessen Menge im Warenkorb geändert werden soll. Dieser Parameter darf nicht null sein.
     * @param newQuantity Die neue Menge des Artikels im Warenkorb. Diese Zahl muss größer als null sein und die Anforderungen für Massengut erfüllen.
     *
     * @throws IdNichtVorhandenException Wenn der angegebene Artikel nicht im Warenkorb vorhanden ist.
     * @throws MinusZahlException Wenn die angegebene Menge kleiner oder gleich null ist.
     * @throws KeinMassengutException Wenn der Artikel Massengut ist und die angegebene Menge kein Vielfaches der Massengut-Anzahl ist.
     * @throws BestandNichtAusreichendException Wenn die angegebene Menge den verfügbaren Bestand des Artikels überschreitet.
     *
     * @throws IllegalArgumentException Wenn der übergebene Artikel null ist.
     */
    public void bestandImWarenkorbAendern(Artikel artikel, int newQuantity) throws IdNichtVorhandenException, MinusZahlException, KeinMassengutException, BestandNichtAusreichendException {

        if (artikel instanceof MassengutArtikel) {
            MassengutArtikel massengutArtikel = (MassengutArtikel) artikel;
            // Überprüfen, ob die Menge ein Vielfaches der Massengut-Anzahl ist
            if (newQuantity % massengutArtikel.getAnzahlMassengut() != 0) {
                throw new KeinMassengutException(massengutArtikel.getAnzahlMassengut());
            }
        }

        if(newQuantity <=0 ){
            throw new MinusZahlException();
        }

        if(newQuantity > artikel.getArtikelbestand()){
            throw new BestandNichtAusreichendException(artikel, artikel.getArtikelbestand());
        }
        if (warenkorbMap.containsKey(artikel)) {
            warenkorbMap.replace(artikel, newQuantity);
        } else {
            throw new IdNichtVorhandenException(artikel.getArtikelnummer());
        }
    }
    /**
     * Gibt die aktuelle Map der Artikel und deren Mengen im Warenkorb zurück.
     *
     * <p>Diese Methode stellt die Map bereit, die die Artikel im Warenkorb und deren jeweilige Mengen enthält.
     * Die zurückgegebene Map ist eine unveränderliche Sicht auf die interne Datenstruktur des Warenkorbs.
     * Änderungen an der zurückgegebenen Map beeinflussen nicht den Zustand des Warenkorbs.</p>
     *
     * @return Eine unveränderliche Map, die die Artikel und deren Mengen im Warenkorb enthält.
     */
    public Map<Artikel, Integer> getWarenkorbMap() {
        return warenkorbMap;
    }
    /**
     * Berechnet den Gesamtpreis aller Artikel im Warenkorb.
     *
     * <p>Diese Methode summiert die Preise aller Artikel im Warenkorb basierend auf deren Menge und Preis. Wenn ein Artikel ein {@link MassengutArtikel} ist, wird der Preis entsprechend der Massengut-Anzahl und der Menge berechnet. Der Gesamtpreis wird auf zwei Dezimalstellen gerundet.</p>
     *
     * <p>Die Berechnung des Gesamtpreises erfolgt nach folgenden Regeln:</p>
     * <ul>
     *     <li>Für normale Artikel wird der Preis durch Multiplikation der Menge mit dem Artikelpreis berechnet.</li>
     *     <li>Für Massengutartikel wird der Preis auf Basis der Gesamtmenge und der Massengut-Anzahl berechnet. Die Menge muss ein Vielfaches der Massengut-Anzahl sein, daher wird die Menge in Einheiten von Massengut berechnet.</li>
     * </ul>
     *
     * @return Der Gesamtpreis aller Artikel im Warenkorb, gerundet auf zwei Dezimalstellen.
     */
    public double gesamtPreis() {
        double gesamtPreis = 0;

        for (Map.Entry<Artikel, Integer> entry : warenkorbMap.entrySet()) {
            Artikel artikel = entry.getKey();
            int menge = entry.getValue();

            // Überprüfen, ob es sich um einen Massengutartikel handelt
            if (artikel instanceof MassengutArtikel massengutArtikel) {
                int einheit = massengutArtikel.getAnzahlMassengut();

                // Gesamtpreis entsprechend der Gesamtmenge berechnen
                gesamtPreis += ((double) menge / einheit) * artikel.getArtikelPreis();
            } else {
                // Normalen Artikel Preis berechnen
                gesamtPreis += (menge * artikel.getArtikelPreis());
            }
        }

        // Gesamtpreis auf 2 Dezimalstellen begrenzen
        return begrenzeDezimalstellen(gesamtPreis, 2);
    }

    /**
     * Begrenzte die Anzahl der Dezimalstellen einer Zahl auf eine bestimmte Anzahl von Stellen.
     *
     * <p>Diese Methode rundet eine {@code double}-Zahl auf eine angegebene Anzahl von Dezimalstellen. Die Rundung erfolgt auf die nächstgelegene Zahl unter Berücksichtigung der vorgegebenen Dezimalstellen.</p>
     *
     * <p>Die Methode multipliziert die Eingabezahl mit einem Faktor, der durch Potenzieren von 10 mit der Anzahl der gewünschten Dezimalstellen erzeugt wird. Anschließend wird die Zahl gerundet und durch denselben Faktor geteilt, um das Ergebnis auf die gewünschte Anzahl von Dezimalstellen zu beschränken.</p>
     *
     * <p>Beispiel: Wenn {@code zahl = 3.14159} und {@code dezimalstellen = 2}, wird das Ergebnis {@code 3.14} sein.</p>
     *
     * @param zahl Die Zahl, deren Dezimalstellen begrenzt werden sollen. Dieser Parameter darf nicht {@code NaN} oder {@code Infinity} sein.
     * @param dezimalstellen Die Anzahl der Dezimalstellen, auf die die Zahl gerundet werden soll. Dieser Parameter muss ein nicht-negativer Wert sein.
     *
     * @return Die auf die angegebene Anzahl von Dezimalstellen gerundete Zahl.
     * @throws IllegalArgumentException Wenn {@code dezimalstellen} negativ ist.
     */
    public static double begrenzeDezimalstellen(double zahl, int dezimalstellen) {
        double faktor = Math.pow(10, dezimalstellen);
        return Math.round(zahl * faktor) / faktor;
    }

    /**
     * Leert den Warenkorb, indem alle Artikel und deren Mengen entfernt werden.
     *
     * <p>Diese Methode entfernt alle Einträge aus dem Warenkorb und setzt den Warenkorb auf einen leeren Zustand zurück.
     * Nach dem Aufruf dieser Methode ist der Warenkorb leer und enthält keine Artikel mehr.</p>
     *
     * <p>Die Methode ruft intern {@link Map#clear()} auf, um die {@code warenkorbMap} zu leeren.</p>
     *
     * @see Map#clear()
     */
    public void warenkorbLeeren() {
        warenkorbMap.clear();
    }
    /**
     * Gibt eine String-Darstellung des Warenkorbs zurück.
     *
     * <p>Diese Methode erstellt eine textuelle Darstellung aller Artikel im Warenkorb. Jeder Artikel wird mit seiner Artikelnummer, Bezeichnung, Menge und Preis angezeigt. Die Ausgabe ist so formatiert, dass jeder Artikel in einer neuen Zeile dargestellt wird.</p>
     *
     * <p>Die Methode durchläuft die {@code warenkorbMap} und erstellt eine Übersicht der enthaltenen Artikel zusammen mit den entsprechenden Mengen und Preisen.</p>
     *
     * @return Eine String-Darstellung aller Artikel im Warenkorb, einschließlich Artikelnummer, Bezeichnung, Menge und Preis für jeden Artikel.
     */
    public String toString(){
        String artikelAusWarenkorb =  "";
        for(Map.Entry<Artikel, Integer> entry : warenkorbMap.entrySet()){
            Artikel artikel = entry.getKey();
            int menge = entry.getValue();
            artikelAusWarenkorb += "Artikel: \n" + "Artikelnummer: " + artikel.getArtikelnummer() + " | ";
            artikelAusWarenkorb += "Bezeichnung: " + artikel.getArtikelbezeichnung() + " | ";
            artikelAusWarenkorb += "Menge: " + menge + " | ";
            artikelAusWarenkorb += "Preis: " + artikel.getArtikelPreis() + "\n";
        }
        return artikelAusWarenkorb;
    }
}


