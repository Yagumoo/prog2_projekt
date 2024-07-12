package eshop.common.exceptions;

public class FalscheEingabeException extends Exception {
    private String erwarteteEingabe;
    private String gegbeneEingabe;
    public FalscheEingabeException(String message, String erwarteteEingabe, String gegbeneEingabe) {
        super(message);
        this.erwarteteEingabe = erwarteteEingabe;
        this.gegbeneEingabe = gegbeneEingabe;
    }

    public String getErwarteteEingabe() {
        return erwarteteEingabe;
    }

    public String getGegbeneEingabe() {
        return gegbeneEingabe;
    }

    @Override
    public String toString() {
        return super.toString() + " |Erwartete Eingabe: " + erwarteteEingabe + " |Gegbene Eingabe: " + gegbeneEingabe;
    }
}

