package eshop.domain.exceptions;

import eshop.enitities.*;
import eshop.domain.*;

public class DoppelteIdException extends Exception {

    public DoppelteIdException(int id) {
        super("Die ID " + id + " ist bereits vergeben");
    }
}