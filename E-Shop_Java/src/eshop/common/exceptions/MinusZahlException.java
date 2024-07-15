package eshop.common.exceptions;
/**
 * Diese Ausnahme wird ausgelöst, wenn ein Wert kleiner als 0 verwendet wird, was in der gegebenen
 * Situation nicht zulässig ist.
 *
 * <p>Die Klasse {@code MinusZahlException} erweitert die {@code Exception}-Klasse und wird verwendet,
 * um eine Fehlermeldung anzuzeigen, wenn ein Wert kleiner als 0 übergeben oder verwendet wird.</p>
 *
 * @see java.lang.Exception
 */
public class MinusZahlException extends Exception {
    /**
     * Konstruktor, der eine standardisierte Fehlermeldung erstellt, die angibt, dass ein Wert kleiner
     * als 0 nicht zulässig ist.
     */
    public MinusZahlException() {
        super("Ein Wert kleiner als 0 ist nicht möglich");
    }

}
