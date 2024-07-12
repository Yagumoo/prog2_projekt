package eshop.common.exceptions;

public class KeinMassengutException extends Exception {
    public KeinMassengutException(int anzahlMassengut) {
        super("Die Artikelanzahl muss ein Vielfaches von der Massengutanzahl " + anzahlMassengut + " sein!");
    }

    // Methode zur Überprüfung, ob die Zahl ein Vielfaches einer anderen Zahl ist
    public static boolean istVielfaches(int zahl, int vielfaches) {
        return zahl % vielfaches == 0;
    }
}

