package eshop.common.exceptions;

public class MinusZahlException extends Exception {
    public MinusZahlException() {
        super("Ein Wert kleiner als 0 ist nicht möglich");
    }

}
