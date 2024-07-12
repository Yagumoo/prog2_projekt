package eshop.common.exceptions;

public class ZuVieleFehlversucheException extends Exception {

    public ZuVieleFehlversucheException() {
        super("Sie haben zu oft das falsche Passwort oder den Username verwendet. Versuchen Sie es später erneut");
    }
}
