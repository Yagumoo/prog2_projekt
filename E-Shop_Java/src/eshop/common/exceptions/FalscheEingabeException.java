package eshop.common.exceptions;
/**
 * Diese Ausnahme wird ausgelöst, wenn eine Eingabe nicht den erwarteten Wert hat.
 *
 * <p>Die Klasse {@code FalscheEingabeException} erweitert die {@code Exception}-Klasse und wird
 * verwendet, um eine spezifische Fehlermeldung anzugeben, wenn eine falsche Eingabe gemacht wurde.</p>
 *
 * @see java.lang.Exception
 */
public class FalscheEingabeException extends Exception {
    private String erwarteteEingabe;
    private String gegbeneEingabe;
    /**
     * Konstruktor, der eine Fehlermeldung mit der erwarteten und gegebenen Eingabe erstellt.
     *
     * @param message die Fehlermeldung
     * @param erwarteteEingabe die erwartete Eingabe
     * @param gegbeneEingabe die tatsächlich gegebene Eingabe
     */
    public FalscheEingabeException(String message, String erwarteteEingabe, String gegbeneEingabe) {
        super(message);
        this.erwarteteEingabe = erwarteteEingabe;
        this.gegbeneEingabe = gegbeneEingabe;
    }

    /**
     * Gibt die erwartete Eingabe zurück.
     *
     * @return die erwartete Eingabe
     */

    public String getErwarteteEingabe() {
        return erwarteteEingabe;
    }

    /**
     * Gibt die tatsächlich gegebene Eingabe zurück.
     *
     * @return die tatsächlich gegebene Eingabe
     */
    public String getGegbeneEingabe() {
        return gegbeneEingabe;
    }
    /**
     * Gibt eine String-Darstellung der Ausnahme zurück, die die erwartete und die tatsächlich gegebene Eingabe enthält.
     *
     * @return eine String-Darstellung der Ausnahme
     */
    @Override
    public String toString() {
        return super.toString() + " |Erwartete Eingabe: " + erwarteteEingabe + " |Gegbene Eingabe: " + gegbeneEingabe;
    }
}

