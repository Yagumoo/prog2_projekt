package eshop.domain.exceptions;

public class DoppelteIdException extends Exception {

    public DoppelteIdException(int id) {
        super("Die ID " + id + " ist bereits vergeben");
    }
}