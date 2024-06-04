package eshop.domain.exceptions;

public class LoginException extends Exception {
    public LoginException() {
        super("Falsches Passwort oder Username. Bitte erneut anmelden");
    }
}
