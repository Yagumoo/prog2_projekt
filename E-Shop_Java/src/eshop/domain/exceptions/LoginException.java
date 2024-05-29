package eshop.domain.exceptions;

import eshop.enitities.*;
import eshop.domain.*;

public class LoginException extends Exception {
    public LoginException() {
        super("Falsches Passwort oder Username. Bitte erneut anmelden");
    }
}
