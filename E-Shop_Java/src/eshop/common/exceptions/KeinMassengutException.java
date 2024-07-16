package eshop.common.exceptions;

import eshop.common.enitities.Artikel;
import eshop.common.enitities.MassengutArtikel;

/**
 * Diese Ausnahme wird ausgelöst, wenn eine Artikelanzahl, die für Massengüter vorgesehen ist,
 * nicht ein Vielfaches der Massengutanzahl ist.
 *
 * <p>Die Klasse {@code KeinMassengutException} erweitert die {@code Exception}-Klasse und wird
 * verwendet, um eine Fehlermeldung anzuzeigen, wenn die Anzahl eines Massenguts nicht den erforderlichen
 * Vielfach-Kriterien entspricht.</p>
 *
 * @see java.lang.Exception
 */
public class KeinMassengutException extends Exception {
    /**
     * Konstruktor, der eine Fehlermeldung erstellt, um darauf hinzuweisen, dass die Artikelanzahl
     * ein Vielfaches der Massengutanzahl sein muss.
     *
     * @param anzahlMassengut die Massengutanzahl, die als Vielfaches für die Artikelanzahl erforderlich ist
     */
    public KeinMassengutException(int anzahlMassengut) {
        super("Die Artikelanzahl muss ein Vielfaches von der Massengutanzahl " + anzahlMassengut + " sein!");
    }

    public KeinMassengutException(MassengutArtikel anzahlMassengut) {
        super("Die Artikelanzahl muss ein Vielfaches von der Massengutanzahl " + anzahlMassengut + " sein!");
    }
    /**
     * Überprüft, ob die angegebene Zahl ein Vielfaches der angegebenen Vielfachzahl ist.
     *
     * <p>Diese Methode prüft, ob der Rest bei der Division der Zahl durch die Vielfachzahl null ist,
     * was bedeutet, dass die Zahl ein Vielfaches der Vielfachzahl ist.</p>
     *
     * @param zahl die zu überprüfende Zahl
     * @param vielfaches die Vielfachzahl, die überprüft werden soll
     * @return {@code true}, wenn {@code zahl} ein Vielfaches von {@code vielfaches} ist, andernfalls {@code false}
     */


    // Methode zur Überprüfung, ob die Zahl ein Vielfaches einer anderen Zahl ist
    public static boolean istVielfaches(int zahl, int vielfaches) {
        return zahl % vielfaches == 0;
    }
}

