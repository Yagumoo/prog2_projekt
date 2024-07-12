package eshop.common.exceptions;

public class IstLeerException extends Exception {
    public IstLeerException() {
        super("Der Warenkorb enthält zurzeit keine Artikel");
    }
}
